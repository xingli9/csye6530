'''
Created on 2018年9月15日

@author: xingli
'''

import configparser
import os

Default_CONFIG_FILE = '/home/pi/Desktop/xing/iot-device/data/ConnectedDevicesConfig.props'

class ConfigUtil():
    configfile = Default_CONFIG_FILE
    configdata = configparser.ConfigParser()
    isLoaded   = False
    
    #Constructor
    def __init__(self,configFile):
        if(configFile != ''):
            self.configfile = configFile
    
    #load the config file
    def loadConfig(self):
        #print(Default_CONFIG_FILE)
        if(os.path.exists(self.configfile)):
            self.configdata.read(self.configfile)
            self.isLoaded = True
    
    #make sure the config file has been loaded
    def getConfig(self, forceReload = False):
        if(self.isLoaded == False or forceReload):
            self.loadConfig()
        return self.configdata
    
    #get the config file
    def getConfigFile(self):
        return self.configfile

    def getProperty(self,section, key, forceReload = False):
        return self.getConfig(forceReload).get(section, key)
    
    #return whether the config file has been loaded
    def isConfigDataLoaded(self):
        return self.isLoaded
