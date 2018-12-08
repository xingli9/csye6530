/**
 * 
 */
package org.foobar.iot.module8;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.foobar.iot.common.ConfigConst;

import org.foobar.iot.module6.MqttPubClientTestApp;
import org.json.JSONObject;


/**
 * @author xingli
 *
 */
public class MqttClientConnector implements MqttCallback {
	// static
	private static final Logger _Logger = Logger.getLogger(MqttClientConnector.class.getName());

	// parameters
	private String _protocol;
	private String _host;
	private int _port;
	private MqttClient _mqttClient;
	private String _clientID;
	private String _brokerAddr;
	private String _serverAddr;
	private boolean _isInitialized;
	private String _userName;
	private String _password;
	private String _pemFileName;
	private Boolean _isSecureConn;

	public String payloadValue;

	// constructors
	/**
	 * Default.
	 *
	 */
	public MqttClientConnector() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param host: The name of the broker to connect.
	 * @param isSecure Currently unused.
	 */
	public MqttClientConnector(String host) {
		super();
		if (host != null && host.trim().length() > 0) {
			_host = host;
			_port = 1883;
			_protocol = "tcp";
		}
		// NOTE: URL does not have a protocol handler for "tcp",
		// so we need to construct the URL manually
		_clientID = MqttClient.generateClientId();
		_Logger.info("Using client ID for broker conn: " + _clientID);
		_brokerAddr = _protocol + "://" + _host + ":" + _port;
		_Logger.info("Using URL for broker conn: " + _brokerAddr);
	}

	/**
	 * Constructor.
	 *
	 * @param host: The name of the broker to connect.
	 * @param userName: token for the ubidots account
	 * @param pemFileName The name of the certificate file to use. If null / invalid, ignored.
	 *                    
	 */
	public MqttClientConnector(String host, String userName, String pemFileName, String password) {
		super();
		if (host != null && host.trim().length() > 0) {
			_host = host;
		}
		if (userName != null && userName.trim().length() > 0) {
			_userName = userName;
		}
		if (password != null && password.trim().length() > 0) {
			_password = password;
		}
		if (pemFileName != null) {
			File file = new File(pemFileName);
			if (file.exists()) {
				_protocol = "ssl";
				_port = 8883;
				_pemFileName = pemFileName;
				_isSecureConn = true;
				_Logger.info("PEM file valid. Using secure connection: " + _pemFileName);
			} else {
				_Logger.warning("PEM file invalid. Using insecure connection: " + pemFileName);
			}
		}
		_clientID = MqttClient.generateClientId();
		_brokerAddr = _protocol + "://" + _host + ":" + _port;
		_Logger.info("Using URL for broker conn: " + _brokerAddr);
	}
	
	// public methods
	/**
	 * Connect to MqttClient
	 */
	public void connect() {
		if (_mqttClient == null) {
			MemoryPersistence persistence = new MemoryPersistence();
			try {
				//System.out.println("clientid:++ " + _clientID);
				_mqttClient = new MqttClient(_brokerAddr, _clientID, persistence);
				MqttConnectOptions connOpts = new MqttConnectOptions();
				connOpts.setCleanSession(true);
				if (_userName != null) {
					connOpts.setUserName(_userName);
				}
				if (_password != null) {
					connOpts.setPassword(_password.toCharArray());
				}
				if (_isSecureConn) {
					initSecureConnection(connOpts);
				}
				_mqttClient.setCallback(this);//receive new messages that published to the subscribed topic
				_mqttClient.connect(connOpts);
				_Logger.info("Connected to broker: " + _brokerAddr);
			} catch (MqttException e) {
				_Logger.log(Level.SEVERE, "Failed to connect to broker: " + _brokerAddr, e);
			}
		}
	}

	/**
	 * disconnect to MqttClient
	 */
	public void disconnect() {
		try {
			_mqttClient.disconnect();
			_Logger.info("Disconnected from broker: " + _brokerAddr);
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to disconnect from broker: " + _brokerAddr, e);
		}
	}

	/**
	 * Publishes the given payload to broker directly to topic 'topic'.
	 *
	 * @param topic: destination topic that the message direct to
	 * @param qosLevel: 0: at most once, 1: at least once, 2: exactly once
	 * @param payload
	 * @return true if publish successful, false if publish failed
	 */
	public boolean publishMessage(String topic, int qosLevel, byte[] payload) {
		boolean success = false;
		try {
			_Logger.info("Publishing message to topic: " + topic);
			MqttMessage msg = new MqttMessage(payload);//create a new MqttMessage, pass 'payload' to the constructor
			msg.setQos(qosLevel);//set the QoS on the message to qosLevel
			msg.setRetained(true);//Sending a message with retained 
			_mqttClient.publish(topic, msg);//call 'publish' on the MQTT client, passing the 'topic' and MqttMessage instance
			_Logger.info("Published message " + msg.getId() + " to " + topic);//log the result - include the ID from the message
			success = true;
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to publish MQTT message: " + e.getMessage());
		}
		return success;
	}

	/**
	 * subscribe to all topics
	 * @return true if subscribe to all topics successfully, false if failed
	 */
	public boolean subscribeToAll() {
		try {
			_mqttClient.subscribe("$SYS/#");
			_Logger.log(Level.INFO, "Subscribe to all successfully.");
			return true;
		} catch (MqttException e) {
			_Logger.log(Level.WARNING, "Failed to subscribe to all topics.", e);
		}
		return false;
	}

	/**
	 * 
	 * @param topic: subscribe to the special topic
	 * @return true if subscribe the topic successfully, false if failed
	 */
	public boolean subscribeToTopic(String topic) {
		try {
			_mqttClient.subscribe(topic);
			_Logger.log(Level.INFO, "Subscribe to topic " + topic + " successfully.");
			return true;
		} catch (MqttException e) {
			_Logger.log(Level.WARNING, "Failed to subscribe to topic " + topic, e);
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @param topic: unsubscribe to the special topic
	 * @return true if unsubscribe the topic successfully, false if failed
	 */
	public boolean unSubscribeToTopic(String topic) {
		try {	
			_mqttClient.unsubscribe(topic);
			_Logger.log(Level.INFO, "Unsubscribe to topic " + topic + " successfully.");
			return true;
		} catch (MqttException e) {
			_Logger.log(Level.WARNING, "Failed to unsubscribe to topic " + topic, e);
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.
	 * Throwable)
	 */
	public void connectionLost(Throwable t) {
		_Logger.log(Level.WARNING, "Connection to broker lost. Will retry soon.", t);
		this.connect();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho
	 * .client.mqttv3.IMqttDeliveryToken)
	 */
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO: what else should you do here?
		try {
			_Logger.info("Delivery complete: " + token.getMessageId() + " - " + token.getResponse() + " - "
					+ token.getMessage());
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to retrieve message from token.", e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String,
	 * org.eclipse.paho.client.mqttv3.MqttMessage)
	 */
	public void messageArrived(String data, MqttMessage msg) throws Exception {
		// TODO: should you analyze the message or just log it?
		String payload = msg.toString();
		JSONObject msgJson = new JSONObject(payload);
		String result = "";
		if (!msgJson.isNull("value")) {			
			Double tempValue = msgJson.getDouble("value");
			System.out.println(tempValue);
			result = String.valueOf(tempValue);
		}

		//publish actuator data to gateway broker
		MqttPubClientTestApp pubClient = new MqttPubClientTestApp();
		pubClient.publish(result);
		
		_Logger.info("Message arrived: " + data + ", " + msg.getId());
		_Logger.info("Message plyload: "+ result);
	}

	/**
	 * initiate the secure connection for mqtt connection
	 * @param connOpts: the mqtt connection option that need to be set a secure ssl context
	 */
	private void initSecureConnection(MqttConnectOptions connOpts) {
		try {
			_Logger.info("Configuring TLS...");
			SSLContext sslContext = SSLContext.getInstance("SSL");
			KeyStore keyStore = readCertificate();
			TrustManagerFactory trustManagerFactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(keyStore);
			sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
			connOpts.setSocketFactory(sslContext.getSocketFactory());
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to initialize secure MQTT connection.", e);
		}
	}

	/**
	 * 
	 * @return 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	private KeyStore readCertificate()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream fis = new FileInputStream(_pemFileName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		ks.load(null);
		while (bis.available() > 0) {
			Certificate cert = cf.generateCertificate(bis);
			ks.setCertificateEntry("Xing_store" + bis.available(), cert);
		}
		return ks;
	}

}