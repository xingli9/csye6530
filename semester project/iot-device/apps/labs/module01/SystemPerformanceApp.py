'''
Created on 2018年9月9日

@author: xingli
'''
from time           import sleep
from labs.module01 import SystemPerformanceAdaptor

sysPerfAdaptor = SystemPerformanceAdaptor.SystemPerformanceAdaptor()
sysPerfAdaptor.daemon = True

print("Starting system performance app daemon thread...")
sysPerfAdaptor.setEnableAdaptorFlag(True) #set enableAdaptor to true
sysPerfAdaptor.start() #start the run function in SystemPerformanceAdaptor

#keep running
while (True):
    sleep(5)
    pass