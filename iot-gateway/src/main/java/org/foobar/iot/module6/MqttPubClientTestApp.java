/**
 * 
 */
package org.foobar.iot.module6;

import java.util.logging.Logger;

/**
 * @author xingli
 *
 */
public class MqttPubClientTestApp {
	// static
	private static final Logger _Logger = Logger.getLogger(MqttPubClientTestApp.class.getName());
	private static MqttPubClientTestApp _App;
	
	// parameters
	private MqttClientConnector _mqttClient;
	
	/**
	 * Default constructor
	 */
	public MqttPubClientTestApp() {
		super();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		_App = new MqttPubClientTestApp();
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
		_mqttClient = new MqttClientConnector();
		_mqttClient.connect();
		String topicName = "xingli";
		String payload = "This is a test2.";
		_mqttClient.publishMessage(topicName, 2, payload.getBytes());
		_mqttClient.disconnect();
	}

}
