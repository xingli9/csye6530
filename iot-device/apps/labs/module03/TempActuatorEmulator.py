'''
Created on Sep 28, 2018

@author: xingli
'''
import os,sys
sys.path.append('/home/pi/Desktop/xing/iot-device/apps')
from labs.common.ActuatorData import ActuatorData
from labs.module03.SenseHatLedActivator import SenseHatLedActivator
from labs.module03.SimpleLedActivator import SimpleLedActivator

class TempActuatorEmulator():
    actuatorData = None
    senseHatLedActivator = None
    simpleLedActivator = None



    def __init__(self):
        '''
        Constructor
        '''
        self.actuatorData = ActuatorData()
        self.senseHatLedActivator = SenseHatLedActivator()
        self.simpleLedActivator = SimpleLedActivator()
        
    def processMessage(self, ActuatorData):
        #print('processMessage...')
        self.actuatorData.updateData(ActuatorData)
        self.simpleLedActivator.setEnableLedFlag(True)
        if self.actuatorData.getCommand() == 0:
            #print('create msg-------')
            msg = "Temperature is " + str(abs(self.actuatorData.getValue())) + " degree lower than nominal temperature, open the cool function" 
            #msg = str(abs(self.actuatorData.getValue()))
        if self.actuatorData.getCommand() == 1:
            msg = "Temperature is " + str(self.actuatorData.getValue()) + " degree higher than nominal temperature, open the heat function" 
        self.senseHatLedActivator.setEnableLedFlag(True)
        self.senseHatLedActivator.setDisplayMessage(msg)
        #print('before run')
        self.senseHatLedActivator.run()
        #print('after run')