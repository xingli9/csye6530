'''
Created on 2018年9月15日

@author: xingli
'''

import os
from datetime import datetime

class SensorData:
    timeStamp = None
    name = 'Not set'
    #newVal = 0
    curValue = 0
    avgValue = 0
    minValue = 30
    maxValue = 0
    totValue = 0
    sampleCount = 0
    
    #Constructor
    def __init__(self):
        self.timeStamp = str(datetime.now())
    
    #add new value, and reset the other values
    def addValue(self, newVal):
        self.sampleCount += 1
        self.timeStamp = str(datetime.now())
        self.totValue += newVal
        self.curValue = newVal
        
        if (self.curValue < self.minValue):
            self.minValue = self.curValue
        if (self.curValue > self.maxValue):
            self.maxValue = self.curValue
        if (self.totValue != 0 and self.sampleCount > 0):
            self.avgValue = self.totValue / self.sampleCount
    
    #get the average value
    def getAvgValue(self):
        return self.avgValue
    
    #get the max value
    def getMaxValue(self):
        return self.maxValue
    
    #get the min value
    def getMinValue(self):
        return self.minValue
    
    #get the current value
    def getValue(self):
        return self.curValue
    
    #set name
    def setName(self, name):
        self.name = name
        
    #put all values into a string sentence
    def __str__(self):
        customStr = \
            str(self.name + ':'        + \
            os.linesep + '\tTime: '    + self.timeStamp + \
            os.linesep + '\tCurrent: ' + str(self.curValue) + \
            os.linesep + '\tAverage: ' + str(self.avgValue) + \
            os.linesep + '\tSamples: ' + str(self.sampleCount) + \
            os.linesep + '\tMin: '     + str(self.minValue) + \
            os.linesep + '\tMax: '     + str(self.maxValue))
        return customStr
