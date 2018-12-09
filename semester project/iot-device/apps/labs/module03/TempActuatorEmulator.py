'''
Created on Sep 28, 2018

@author: xingli
'''
import os,sys
sys.path.append('/home/pi/Desktop/xing/iot-device/apps')
from labs.common.ActuatorData import ActuatorData
from labs.module03.SenseHatLedActivator import SenseHatLedActivator

class TempActuatorEmulator():
    actuatorData = None
    senseHatLedActivator = None
    simpleLedActivator = None

    #Constructor
    def __init__(self):
        self.actuatorData = ActuatorData()
        self.senseHatLedActivator = SenseHatLedActivator()
        
    #generate a message and run the activator
    def processMessage(self, ActuatorData):
        self.senseHatLedActivator.setEnableLedFlag(True)
        self.senseHatLedActivator.setDisplayMessage(ActuatorData)
        self.senseHatLedActivator.run()
