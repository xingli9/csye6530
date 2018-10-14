'''
Created on 2018年9月15日

@author: xingli
'''
import os,sys
sys.path.append('/home/pi/Desktop/xing/iot-device/apps')
from email.mime.multipart import MIMEMultipart
from labs.common import ConfigUtil
from labs.common import ConfigConst
from email.mime.text import MIMEText
import smtplib

class SmtpClientConnector(object):
    '''
    classdocs
    '''
    def __init__(self):
        self.config = ConfigUtil.ConfigUtil('')
        self.config.loadConfig()
        print('Configuration data...\n' + str(self.config))
        
    def publishMessage(self, topic, data):
        host    = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.HOST_KEY)
        port    = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.PORT_KEY)
        fromAddr    = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.FROM_ADDRESS_KEY)
        toAddr = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.TO_ADDRESS_KEY)
        authToken   = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY)
        nominalTemp = self.config.getProperty(ConfigConst.CONSTRAINED_DEVICE, ConfigConst.NOMINAL_TEMP_KEY)
        
        msg = MIMEMultipart()
        msg['From'] = fromAddr
        msg['To'] = toAddr 
        msg['Subject'] = topic 
        msgBody = str(data)
        
        msg.attach(MIMEText(msgBody))

        msgText = msg.as_string()
        
        # send e-mail notification 
        smtpServer = smtplib.SMTP_SSL(host, port) 
        smtpServer.ehlo() 
        smtpServer.login(fromAddr, authToken) 
        smtpServer.sendmail(fromAddr, toAddr, msgText) 
        smtpServer.close()