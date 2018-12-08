package org.foobar.iot.common;

public class ConfigConst {

	private static final String SECTION_SEPARATOR = ".";
	public static final String DEFAULT_CONFIG_FILE_NAME = "/Users/xingli/Desktop/neu/class/2018fall/CSYE6530connectDevices/csye6530_old/iot-device/data/ConnectedDevicesConfig.props";

	// Option
	public static final String CLOUD = "cloud";
	public static final String MQTT = "mqtt";
	public static final String COAP = "coap";
	public static final String SMTP = "smtp";
	public static final String GATEWAY_DEVICE = "gateway";
	public static final String CONSTRAINED_DEVICE = "device";
	public static final String UBIDOTS = "ubidots";

	// Section name
	public static final String UBIDOTS_CLOUD_SECTION = UBIDOTS + SECTION_SEPARATOR + CLOUD;
	public static final String SMTP_CLOUD_SECTION = SMTP + SECTION_SEPARATOR + CLOUD;
	public static final String MQTT_CLOUD_SECTION = MQTT + SECTION_SEPARATOR + CLOUD;
	public static final String COAP_CLOUD_SECTION = COAP + SECTION_SEPARATOR + CLOUD;
	public static final String MQTT_GATEWAY_SECTION = MQTT + SECTION_SEPARATOR + GATEWAY_DEVICE;
	public static final String COAP_GATEWAY_SECTION = COAP + SECTION_SEPARATOR + GATEWAY_DEVICE;
	public static final String MQTT_DEVICE_SECTION = MQTT + SECTION_SEPARATOR + CONSTRAINED_DEVICE;
	public static final String COAP_DEVICE_SECTION = COAP + SECTION_SEPARATOR + CONSTRAINED_DEVICE;

	// Cloud
	public static final String CLOUD_API_KEY = "apiKey";
	public static final String CLOUD_MQTT_BROKER = "mqttBroker";
	public static final String CLOUD_MQTT_PORT = "mqttPort";
	public static final String CLOUD_COAP_HOST = "coapHost";
	public static final String CLOUD_COAP_PORT = "coapPort";

	// Address related
	public static final String FROM_ADDRESS_KEY = "fromAddr";
	public static final String TO_ADDRESS_KEY = "toAddr";
	public static final String TO_MEDIA_ADDRESS_KEY = "toMediaAddr";
	public static final String TO_TXT_ADDRESS_KEY = "toTxtAddr";

	// Host and Port
	public static final String HOST_KEY = "host";
	public static final String PORT_KEY = "port";
	public static final String SECURE_PORT_KEY = "securePort";

	// User
	public static final String USER_NAME_TOKEN_KEY = "userNameToken";
	public static final String USER_AUTH_TOKEN_KEY = "authToken";

	// Enable
	public static final String ENABLE_AUTH_KEY = "enableAuth";
	public static final String ENABLE_CRYPT_KEY = "enableCrypt";
	public static final String ENABLE_EMULATOR_KEY = "enableEmulator";
	public static final String ENABLE_LOGGING_KEY = "enableLogging";
	public static final String POLL_CYCLES_KEY = "pollCycleSecs";

	// device
	public static final String NOMINAL_TEMP_KEY = "nominalTemp";
	
	//MQTT
	public static final String DEFAULT_HOST = "test.mosquitto.org";
	public static final String DEFAULT_MQTT_PROTOCOL = "tcp";
	public static final String DEFAULT_MQTT_SERVER = "test.mosquitto.org";
	public static final int DEFAULT_MQTT_PORT = 1883;
	public static final String DEFAULT_MQTT_USERNAME = "A1E-c9o4I3GWRx8ow9ctDl3c3qiFD0OLXH";
	public static final String DEFAULT_MQTT_AUTHTOKEN = null;
	public static final String DEFAULT_MQTT_PEMFILENAME = "/Users/xingli/Desktop/neu/class/2018fall/CSYE6530connectDevices/lecture/lecture8/industrial.pem";
	public static final String UBIDOTS_HOST = "things.ubidots.com";
	
	//COAP
	public static final String DEFAULT_COAP_PROTOCOL = "coap";
	public static final String DEFAULT_COAP_SERVER = "localhost";//"192.168.1.14";
	public static final int DEFAULT_COAP_PORT = 5683;
	public static final String 	SECURE_COAP_SERVER = "californium.eclipse.org";
	public static final String SECURE_COAP_PROTOCOL = "coap";
	public static final int SECURE_COAP_PORT = 5683;
}
