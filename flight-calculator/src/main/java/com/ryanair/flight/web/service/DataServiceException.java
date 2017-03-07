package com.ryanair.flight.web.service;

/**
 * <p> This class encapsulates any {@link Exception} from the data layer</p>
 * @author angel
 *
 */
public class DataServiceException extends Exception {

	
	private static final long serialVersionUID = -1315521757113036893L;


	public DataServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataServiceException(String message) {
		super(message);
	}

	public DataServiceException(Throwable cause) {
		super(cause);
	}
	
}
