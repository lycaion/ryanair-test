package com.ryanair.flight.web.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * <p>This is the base of all Pojos</p>
 * <p>All pojos must be {@link Serializable} and ovverides a ToString Method</p>
 * @author angel
 *
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BasePojo implements Serializable{
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public abstract String toString();

}
