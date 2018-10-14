'''
Created on 2018年9月15日

@author: xingli
'''

from time           import sleep
from labs.module02 import TempSensorEmulator

sysPerfEmulator = TempSensorEmulator.TempSensorEmulator(True,0,30,0,True)

print("Starting system performance app daemon thread...")
#sysPerfAdaptor.setEnableAdaptorFlag(True)
sysPerfEmulator.start()

while (True):
    sleep(5)
    pass