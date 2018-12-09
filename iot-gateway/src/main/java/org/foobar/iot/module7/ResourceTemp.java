/**
 * 
 */
package org.foobar.iot.module7;

import java.util.logging.Logger;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

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
		String responseMsg =  "here is my response to:" + super.getName();
		ce.respond(ResponseCode.VALID, responseMsg);
		_Logger.info("Handling GET:" + responseMsg);
		_Logger.info(ce.getRequestCode().toString() + ": " + ce.getRequestText());
	}
	
	@Override
	public void handlePOST(CoapExchange exchange) {
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
