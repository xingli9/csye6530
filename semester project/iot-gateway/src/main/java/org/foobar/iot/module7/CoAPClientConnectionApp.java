package org.foobar.iot.module7;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author xingli
 *
 */
public class CoAPClientConnectionApp {
	
	private static final Logger _Logger = 
			Logger.getLogger(CoAPClientConnectionApp.class.getName());
	private static CoAPClientConnectionApp _App = null;
	
	/**
	 * default constructor
	 */
	public CoAPClientConnectionApp() {
		super();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			_App = new CoAPClientConnectionApp();
			_App.start();
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Bad staff ", e);
			e.printStackTrace();
			System.exit(1);
		}
	}

	//public methods
	/**
	 * Connect to the Coap client, and run the runTests() function to commnication with the server using RESTful API
	 */
	public void start() {
		CoAPClientConnection clientConn = new CoAPClientConnection();
		clientConn.runTests("temp");	
	}
}
