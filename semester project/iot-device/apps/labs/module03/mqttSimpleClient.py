'''
Created on Dec 7, 2018

@author: xingli
'''
import os,sys
sys.path.append('/home/pi/Desktop/xing/iot-device/apps')
import paho.mqtt.client as mqttClient
from time           import sleep
from labs.module03 import TempSensorAdaptor
from labs.module03 import mqttSimpleClient

class mqttSimpleClient():
    
    def __init__(self):
        self.sysPerfAdaptor = TempSensorAdaptor.TempSensorAdaptor()
        
    def on_connect(self,clientConn, data, flags, resultCode):
        print("Client connected to server. Result: " + str(resultCode))

        clientConn.subscribe("myActuatorData") 
    def on_message(self,clientConn, data, msg):
        print("Received PUBLISH on topic {0}. Payload: {1}".format(str(msg.topic), str(msg.payload)))
        self.sysPerfAdaptor.tragerActuator(msg.payload)
    
    def run(self):
        while True:
            self.mc = mqttClient.Client()
            self.mc.on_connect = self.on_connect
            self.mc.on_message = self.on_message
            self.mc.connect("test.mosquitto.org", 1883, 60)
            self.mc.loop_forever()
        sleep(5)
            
            
