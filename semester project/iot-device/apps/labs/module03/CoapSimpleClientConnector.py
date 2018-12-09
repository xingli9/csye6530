'''
Created on Dec 4, 2018

@author: xingli
'''
import os,sys
from coapthon.messages import response
sys.path.append('/home/pi/Desktop/xing/iot-device/apps')
from coapthon.client.helperclient import HelperClient
from labs.common import ConfigUtil 
from labs.common import ConfigConst


class CoapSimpleClientConnector(object):
    config = None
    serverAddr = None
    host = "" 
    port = 5683
       
    def __init__(self):
            print('\tHost: ' + self.host) 
            print('\tPort: ' + str(self.port))
            if not self.host or self.host.isspace(): 
                print("Using default host: " + self.host)
            if self.port < 1024 or self.port > 65535: 
                print("Using default port: " + self.port)

            
    #init the helper coap Client
    def initClient(self, newHost):
        try:
            self.host = newHost
            self.serverAddr = (self.host, self.port)
            self.url = "coap://" + self.host + ":" + str(self.port)
            self.client = HelperClient(server=(self.host, self.port))
            print("Created CoAP client ref: " + str(self.client))
            print(self.url) 
        except Exception:
            print("Failed to create CoAP helper client reference using host: " + self.host) 
            pass
    
    #handle a coap Get request
    def handleGetTest(self, resource):
        self.initClient()
        response = self.client.get(resource)
        if response: 
            print(response.pretty_print())
        else:
            print("No response received for GET using resource: " + resource)
        self.client.stop()
        
    #handle a coap post request
    def handlePostTest(self, resource, payload):
        self.initClient()
        response = self.client.post(resource, payload)
        if response: 
            print(response.pretty_print())
        else:
            print("No response received for POST using resource: " + resource)
        self.client.stop()
