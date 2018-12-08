/**
 * 
 */
package org.foobar.iot.module7;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.observe.ObserveRelation;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.foobar.iot.module8.TempSensorPublisherApp;


/**
 * @author xingli
 *
 */
public class ResourceTemp extends CoapResource {
	//static
	private static final Logger _Logger = 
			Logger.getLogger(ResourceTemp.class.getName());
	
	//constructors
	public ResourceTemp() {
		super("default");
	}

	/**
	 * @param name
	 */
	public ResourceTemp(String name) {
		super(name);
	}

	/**
	 * @param name: the name of resource
	 * @param visible: whether this resource is visible or not
	 */
	public ResourceTemp(String name, boolean visible) {
		super(name, visible);
	}
	
	//public methods
	
	@Override
	public void handleGET(CoapExchange ce) {	
		Response response = new Response(CoAP.ResponseCode.valueOf(67));
		String responseMsg =  "here is my response to:" + super.getName();
		checkObserveRelation(ce.advanced(), response);
		ce.respond(ResponseCode.VALID, responseMsg);
		_Logger.info("Handling GET:" + responseMsg);
		
		_Logger.info(ce.getRequestCode().toString() + ": " + ce.getRequestText());
	}
	
	@Override
	public void handlePOST(CoapExchange exchange) {
		//save temp to the local file	
		String requestMsg = exchange.getRequestText();
		String address = exchange.getSourceAddress().toString();
		String filePath = "/Users/xingli/Desktop/neu/class/2018fall/CSYE6530connectDevices/datafile/sensorData.txt";
		File file = new File(filePath);
		try {
			//save the data to local file
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(requestMsg + "\r\n");
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		//publish the temperature to Ubidots
		TempSensorPublisherApp tempSensorPublisherApp = new TempSensorPublisherApp();
		tempSensorPublisherApp.publish(requestMsg);
		
		//response a message to the client
		String responseMsg =  "here is my response to:" + super.getName();
		exchange.respond(ResponseCode.VALID, responseMsg);
		_Logger.info("Handling POST:" + responseMsg);
		_Logger.info(exchange.getRequestCode().toString() + ": " + exchange.getRequestText());
	}
	
	@Override
	public void handlePUT(CoapExchange exchange) {
		String responseMsg =  "here is my response to:" + super.getName();
		exchange.respond(ResponseCode.VALID, responseMsg);
		_Logger.info("Handling PUT:" + responseMsg);
		_Logger.info(exchange.getRequestCode().toString() + ": " + exchange.getRequestText());
	}
	
	@Override
	public void handleDELETE(CoapExchange exchange) {
		String responseMsg =  "here is my response to:" + super.getName();
		exchange.respond(ResponseCode.VALID, responseMsg);
		_Logger.info("Handling DELETE:" + responseMsg);
		_Logger.info(exchange.getRequestCode().toString() + ": " + exchange.getRequestText());
	}
}
