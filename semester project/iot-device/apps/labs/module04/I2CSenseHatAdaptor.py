'''
Created on Oct 13, 2018

@author: xingli
'''
import os,sys
sys.path.append('/home/pi/Desktop/xing/iot-device/apps')
import smbus
import threading
from time import sleep
from labs.common import ConfigUtil
from labs.common import ConfigConst
i2cBus = smbus.SMBus(1) # Use I2C bus No.1 on Raspberry Pi3 +
enableControl = 0x2D
enableMeasure = 0x08
accelAddr = 0x1C # address for IMU (accelerometer)
magAddr = 0x6A # address for IMU (magnetometer)
pressAddr = 0x5C # address for pressure sensor
humidAddr = 0x5F # address for humidity sensor
begAddr =0x28
totBytes = 6

DEFAULT_RATE_IN_SEC = 5
class I2CSenseHatAdaptor(threading.Thread):
    rateInSec = DEFAULT_RATE_IN_SEC
    accelData = None
    magData = None
    pressData = None
    humidData = None
    enableEmulator = False
    
    #Constructor
    def __init__(self):
        super(I2CSenseHatAdaptor, self).__init__()
        self.config = ConfigUtil.ConfigUtil(ConfigConst.DEFAULT_CONFIG_FILE_NAME)
        self.config.loadConfig()
        print('Configuration data...\n' + str(self.config))
        self.initI2CBus()
        
        #set the data from i2cBus to local variables
        self.accelData = i2cBus.read_i2c_block_data(accelAddr, begAddr, totBytes)  
        self.magData = i2cBus.read_i2c_block_data(magAddr, begAddr, totBytes)
        self.pressData = i2cBus.read_i2c_block_data(pressAddr, begAddr, totBytes)
        self.humidData = i2cBus.read_i2c_block_data(humidAddr, begAddr, totBytes)
    
    #Initializing I2C bus and enabling I2C addresses  
    def initI2CBus(self):
        print("Initializing I2C bus and enabling I2C addresses...")
        i2cBus.write_byte_data(accelAddr, enableControl, enableMeasure)
        i2cBus.write_byte_data(magAddr, enableControl, enableMeasure)
        i2cBus.write_byte_data(pressAddr, enableControl, enableMeasure)
        i2cBus.write_byte_data(humidAddr, enableControl, enableMeasure)
        # TODO: do the same for the magAddr, pressAddr, and humidAddr
        # NOTE: Reading data from the sensor will look like the following:
        
    def displayAccelerometerData(self):
        print(self.accelData)
        
    def displayMagnetometerData(self):
        print(self.magData)
        
    def displayPressureData(self):
        print(self.pressData)
        
    def displayHumidityData(self):
        print(self.humidData)  
    
    #display messages     
    def run(self):
        while True:   
            self.displayAccelerometerData()
            self.displayMagnetometerData()
            self.displayPressureData()
            self.displayHumidityData() 
            sleep(self.rateInSec)