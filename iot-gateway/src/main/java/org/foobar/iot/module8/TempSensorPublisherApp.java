/**
 * 
 */
package org.foobar.iot.module8;

import java.util.logging.Logger;

import org.foobar.iot.common.ConfigConst;
import org.foobar.iot.module8.MqttClientConnector;

/**
 * @author xingli
 *
 */
public class TempSensorPublisherApp {
	// static
	private static final Logger _Logger = Logger.getLogger(TempSensorPublisherApp.class.getName());
	private static TempSensorPublisherApp _App;
	
	// parameters
	private MqttClientConnector _mqttClient;
	private String _userName = ConfigConst.DEFAULT_MQTT_USERNAME;
	private String _authToken = ConfigConst.DEFAULT_MQTT_AUTHTOKEN;
	private String _pemFileName = ConfigConst.DEFAULT_MQTT_PEMFILENAME;
	private String _host = ConfigConst.UBIDOTS_HOST;
	
	/**
	 * Default constructor
	 */
	public TempSensorPublisherApp() {
		super();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		_App = new TempSensorPublisherApp();
		try {
			_App.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// public methods
	/**
	 * Connect to the MQTT client, and publish a test message to the given topic
	 */
	public void start() {
		_mqttClient = new MqttClientConnector(_host, _userName, _pemFileName, _authToken);
		_mqttClient.connect();
		String topicName = "/v1.6/devices/GatewayForXing/tempsensor";
		String payload = "28";
		_mqttClient.publishMessage(topicName, 0, payload.getBytes());
		_mqttClient.disconnect();
	}
}