'''

@author: haolin
'''
import os,sys
sys.path.append('/home/pi/Desktop/xing/iot-device/apps')
from time           import sleep
from labs.module03 import TempSensorAdaptor
from labs.module03 import mqttSimpleClient
sysPerfAdaptor = TempSensorAdaptor.TempSensorAdaptor()


sysPerfAdaptor.setEnableAdaptorFlag(True)#set enableAdaptor to true
sysPerfAdaptor.start()#start the run function in TempSensorAdaptor

#run the mqtt client to listen to the actuator data
mqttClient = mqttSimpleClient.mqttSimpleClient()
mqttClient.run()

#keep running
while (True):
    sleep(5)
    pass