'''
Created on 2018年9月15日

@author: xingli
'''
import os,sys
sys.path.append('/home/pi/Desktop/xing/iot-device/apps')
from time           import sleep
from labs.module03 import TempSensorAdaptor
from labs.module04 import I2CSenseHatAdaptor

sysPerfAdaptor = TempSensorAdaptor.TempSensorAdaptor()
i2cSenseHat = I2CSenseHatAdaptor.I2CSenseHatAdaptor()
print("Starting system performance app daemon thread...")
#sysPerfAdaptor.setEnableAdaptorFlag(True)
#sysPerfAdaptor.start()
i2cSenseHat.run()

#keep running
while (True):
    sleep(5)
    pass