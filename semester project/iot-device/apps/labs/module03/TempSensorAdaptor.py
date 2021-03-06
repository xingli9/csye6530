'''
Created on Sep 28, 2018

@author: xingli
'''
import os, sys
sys.path.append('/home/pi/Desktop/xing/iot-device/apps')
import threading
from time import sleep
from labs.common import SensorData
from labs.module02 import SmtpClientConnector
from labs.common import ConfigUtil
from labs.common import ConfigConst
from labs.common.ActuatorData import ActuatorData
from labs.module03.TempActuatorEmulator import TempActuatorEmulator
from labs.module03 import CoapSimpleClientConnector
from sense_hat import SenseHat

DEFAULT_FATE_IN_SEC = 10
class TempSensorAdaptor(threading.Thread):
    enableAdaptor = False
    actuatorData = None
    tempActuatorEmulator = None
    connector = SmtpClientConnector.SmtpClientConnector()
    sensorData = SensorData.SensorData() 
    alertDiff = 1
    rateInSec = DEFAULT_FATE_IN_SEC
    enableEmulator = False
    lowVal = 0
    highVal = 30
    curTemp = 0
    isPrevTempSet = True
    
    
    # Constructor
    def __init__(self):
        super(TempSensorAdaptor, self).__init__()    
        self.config = ConfigUtil.ConfigUtil('')
        self.config.loadConfig()
        self.actuatorData = ActuatorData()
        self.tempActuatorEmulator = TempActuatorEmulator()
        self.senseHat = SenseHat()
        self.coapConnector = CoapSimpleClientConnector.CoapSimpleClientConnector()
    
    # set enableEmulator      
    def setEnableAdaptorFlag(self, flag):
        self.enableAdaptor = flag
        
    def run(self):
        self.nominalTemp = int(self.config.getProperty(ConfigConst.CONSTRAINED_DEVICE, ConfigConst.NOMINAL_TEMP_KEY))  # get the value of nominal temperature from ConfigConst class
        while True:
            if self.enableAdaptor:
                self.curTemp = self.senseHat.get_temperature() # get a emulated temperature
                self.sensorData.addValue(self.curTemp)  # add current temperature to SensorData class
                print('\n--------------------') 
                print('New sensor readings:') 
                self.coapConnector.initClient("")
                self.coapConnector.handleGetTest("myHomeTemperature")
                self.coapConnector.handlePostTest("myHomeTemperature", str(self.curTemp))
                if self.isPrevTempSet == False:  # skip if this is the first temperature
                    self.prevTemp = self.curTemp
                    self.isPrevTempSet = True   
                else:
                    self.sendEmail()
            sleep(self.rateInSec)    
    
    # if the current temperature is larger or less than the average temperature more than the alert value, publish the message
    def sendEmail(self):
        if (abs(self.curTemp - self.sensorData.getAvgValue()) >= self.alertDiff):  
            print('\n Current temp exceeds average by > ' + str(self.alertDiff) + '. Triggeringalert...')
            self.connector.publishMessage('Exceptional sensor data [test]', str(self.sensorData))
    
    # if the current temperature is larger or less than the nominal temperature more than the alert level, run the actuator to adjust the temperature
    def tragerActuator(self, data):
        self.tempActuatorEmulator.processMessage(data)
        
