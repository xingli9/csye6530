'''
Created on 2018年9月9日

@author: xingli
'''
import psutil
import threading
from time import sleep

DEFAULT_FATE_IN_SEC = 5
class SystemPerformanceAdaptor(threading.Thread):
    enableAdaptor   = True
    rateInSec       = DEFAULT_FATE_IN_SEC
    
    #Constructor
    def __init__(self, rateInSec=DEFAULT_FATE_IN_SEC):
        super(SystemPerformanceAdaptor, self).__init__()
        #if rateInSec have value, set the new value 
        if rateInSec > 0:
            self.rateInSec = rateInSec
            
    def setEnableAdaptorFlag(self, flag):
        self.enableAdaptor = flag
        
    def run(self):
        while True:
            if self.enableAdaptor:
                print('\n--------------------')
                print('New system performance readings:')
                print(' ' + str(psutil.cpu_stats()))
                print(' ' + str(psutil.virtual_memory()))
                #print(' ' + str(psutil.sensors_temperatures(False)))
            sleep(self.rateInSec)
