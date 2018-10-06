'''
Created on 2018年9月15日

@author: xingli
'''
from time import sleep
from random import uniform
from labs.common import SensorData
from labs.module02 import SmtpClientConnector
class TempSensorEmulator():
    '''
    classdocs
    '''
    connector = SmtpClientConnector.SmtpClientConnector()
    sensorData = SensorData.SensorData()
    
    alertDiff = 5
    
    
    def __init__(self, enableEmulator, lowVal, highVal, curTemp, isPrevTempSet):
        '''
        Constructor
        '''
        self.enableEmulator = enableEmulator
        self.curTemp = curTemp
        self.lowVal = lowVal
        self.highVal = highVal
        self.isPrevTempSet = isPrevTempSet
        
    def run(self):
        while True:
            if self.enableEmulator:
                self.curTemp = uniform(float(self.lowVal), float(self.highVal))
                self.sensorData.addValue(self.curTemp, "first sencor")
                print('\n--------------------') 
                print('New sensor readings:') 
                print(' ' + str(self.sensorData))

                if self.isPrevTempSet == False:

                    self.prevTemp = self.curTemp 
                    self.isPrevTempSet = True 
                else:

                    if (abs(self.curTemp - self.sensorData.getAverage()) >= self.alertDiff):

                        print('\n Current temp exceeds average by > ' + str(self.alertDiff) + '. Triggeringalert...')

                        self.connector.publishMessage('Exceptional sensor data [test]', str(self.sensorData))

            sleep(5)    