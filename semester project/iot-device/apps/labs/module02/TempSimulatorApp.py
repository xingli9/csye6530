'''
Created on 2018年9月15日

@author: xingli
'''

from time           import sleep
from labs.module02 import TempSensorEmulator

sysPerfEmulator = TempSensorEmulator.TempSensorEmulator()
sysPerfEmulator.daemon = True




print("Starting system performance app daemon thread...")
sysPerfEmulator.setEnableEmulatorFlag(True)#set enableEmulator to true
sysPerfEmulator.start()#start the run function in TempSensorEmulator

#keep running
while (True):
    sleep(5)
    pass