'''
Created on 2018年9月15日

@author: xingli
'''
import threading
from time import sleep
from random import uniform
from labs.common import SensorData
from labs.module02 import SmtpClientConnector

DEFAULT_FATE_IN_SEC = 5
class TempSensorEmulator(threading.Thread):
    rateInSec       = DEFAULT_FATE_IN_SEC
    connector = SmtpClientConnector.SmtpClientConnector()
    sensorData = SensorData.SensorData() 
    alertDiff = 5
    enableEmulator = False
    lowVal = 0
    highVal = 30
    curTemp = 0
    isPrevTempSet = True
    
    #Constructor
    def __init__(self):
        super(TempSensorEmulator, self).__init__()
    
    #set enableEmulator    
    def setEnableEmulatorFlag(self, flag):
        self.enableEmulator = flag
        
    def run(self):
        while True:
            if self.enableEmulator:
                self.curTemp = uniform(float(self.lowVal), float(self.highVal)) #get a emulated temperature
                self.sensorData.addValue(self.curTemp) #add current temperature to SensorData class
                print('\n-------------------+-') 
                print('New sensor readings:') 
                print(' ' + str(self.sensorData))
                if self.isPrevTempSet == False: #skip if this is the first temperature
                    self.prevTemp = self.curTemp 
                    self.isPrevTempSet = True 
                elif (abs(self.curTemp - self.sensorData.getAvgValue()) >= self.alertDiff): #if the current temperature is larger or less than the average temperature more than the alert value, publish the message
                    print('\n Current temp exceeds average by > ' + str(self.alertDiff) + '. Triggeringalert...')
                    self.connector.publishMessage('Exceptional sensor data [test]', str(self.sensorData))
            sleep(self.rateInSec)    