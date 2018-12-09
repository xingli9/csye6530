package org.foobar.iot.common;

public class ConfigConst {
    //MQTT
	public static final String DEFAULT_HOST = "test.mosquitto.org";
	public static final String DEFAULT_MQTT_PROTOCOL = "tcp";
	public static final String DEFAULT_MQTT_SERVER = "test.mosquitto.org";
	public static final int DEFAULT_MQTT_PORT = 1883;
	public static final String DEFAULT_MQTT_USERNAME = "";
	public static final String DEFAULT_MQTT_AUTHTOKEN = null;
	public static final String DEFAULT_MQTT_PEMFILENAME = "/Users/.../industrial.pem";
	public static final String UBIDOTS_HOST = "things.ubidots.com";
	
	//COAP
	public static final String DEFAULT_COAP_PROTOCOL = "coap";
	public static final String DEFAULT_COAP_SERVER = "localhost";
	public static final int DEFAULT_COAP_PORT = 5683;
	public static final String 	SECURE_COAP_SERVER = "californium.eclipse.org";
	public static final String SECURE_COAP_PROTOCOL = "coap";
	public static final int SECURE_COAP_PORT = 5683;
}
