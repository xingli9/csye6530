'''
Created on Sep 28, 2018

@author: xingli
'''
BOARD = 0
BCM = 1
LOW = 0
HIGH = 1
IN = 0
OUT = 1
DEFAULT_PIN = 17

rotateDeg = 270
clearFlag = False
mode = BCM
curPin = DEFAULT_PIN
curDir = IN

def clear():
    clearFlag = True

def output(pin = DEFAULT_PIN, direction = IN):

    curPin = pin
    curDir = direction

def set_rotation(rotateDeg):

    rotateDeg = rotateDeg

def setmode(mode = BCM):
    mode = mode

def setup(pin = DEFAULT_PIN, direction = IN):

    curPin = pin;
    curDir = direction;