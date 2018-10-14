'''
Created on Sep 28, 2018

@author: xingli
'''
import os,sys
sys.path.append('/home/pi/Desktop/xing/iot-device/apps')
import threading
from time import sleep
from random import uniform
from labs.common import SensorData
from labs.module02 import SmtpClientConnector

from labs.common import ConfigUtil
from labs.common import ConfigConst
from labs.common.ActuatorData import ActuatorData
from labs.module03.TempActuatorEmulator import TempActuatorEmulator

class TempSensorAdaptor(threading.Thread):
    '''
    classdocs
    '''
    actuatorData = None
    tempActuatorEmulator = None
    connector = SmtpClientConnector.SmtpClientConnector()
    sensorData = SensorData.SensorData() 
    alertDiff = 5
    def __init__(self, enableEmulator, lowVal, highVal, curTemp, isPrevTempSet):
        super(TempSensorAdaptor, self).__init__()
        self.enableEmulator = enableEmulator
        self.curTemp = curTemp
        self.lowVal = lowVal
        self.highVal = highVal
        self.isPrevTempSet = isPrevTempSet
        
        self.config = ConfigUtil.ConfigUtil('')
        self.config.loadConfig()
        self.actuatorData = ActuatorData()
        self.tempActuatorEmulator = TempActuatorEmulator()
    def run(self):
        nominalTemp = int(self.config.getProperty(ConfigConst.CONSTRAINED_DEVICE, ConfigConst.NOMINAL_TEMP_KEY))
        while True:
            if self.enableEmulator:
                self.curTemp = uniform(float(self.lowVal), float(self.highVal))
                #self.curTemp = 
                self.sensorData.addValue(self.curTemp)
                print('\n--------------------') 
                print('New sensor readings:') 
                #print(' ' + str(self.sensorData))

                if self.isPrevTempSet == False:

                    self.prevTemp = self.curTemp
                    self.isPrevTempSet = True 
                else:

                    if (abs(self.curTemp - self.sensorData.getAvgValue()) >= self.alertDiff):

                        print('\n Current temp exceeds average by > ' + str(self.alertDiff) + '. Triggeringalert...')

                        #self.connector.publishMessage('Exceptional sensor data [test]', str(self.sensorData))
                    
                    if (abs(self.curTemp - nominalTemp) > self.alertDiff):
                        print('\n+++++++++++++++++++') 
                        print('to the actuator:') 
                        val = self.curTemp - nominalTemp
                        self.actuatorData.setValue(val)
                        if (val > 0):
                            print('val > 0')
                            self.actuatorData.setCommand(1)
                        else:
                            print('val < 0')
                            self.actuatorData.setCommand(0)
                        self.tempActuatorEmulator.processMessage(self.actuatorData)

            sleep(1)    