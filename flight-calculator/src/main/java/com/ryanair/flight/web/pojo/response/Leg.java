package com.ryanair.flight.web.pojo.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ryanair.flight.web.pojo.BasePojo;
import com.ryanair.flight.web.service.util.LocalTimeToStringJacksonConverter;
/**
 * <p>Represents the {@code Leg}</p>
 * @author a452051
 *
 */
public class Leg extends BasePojo implements Comparable<Leg>{
	
	
	private static final long serialVersionUID = 8840595896623558494L;
	
	private String departureAirport;
	private String arrivalAirport;
	@JsonSerialize(using = LocalTimeToStringJacksonConverter.class)
	private LocalDateTime departureDateTime;
	@JsonSerialize(using = LocalTimeToStringJacksonConverter.class)
	private LocalDateTime arrivalDateTime;
	
	//~---------------------------------------------------------- Getter and Setter

	public String getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public LocalDateTime getDepartureDateTime() {
		return departureDateTime;
	}

	public void setDepartureDateTime(LocalDateTime departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	public LocalDateTime getArrivalDateTime() {
		return arrivalDateTime;
	}

	public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}
	
	@Override
	public int compareTo(Leg o) {
		return this.departureDateTime.compareTo(o.departureDateTime);
	}


	//~-------------------------------------------------------------------ToString
	/*
	 * (non-Javadoc)
	 * @see com.ryanair.flight.web.pojo.BasePojo#toString()
	 */
	@Override
	public String toString() {
		return String.format("Leg [departureAirport=%s, arrivalAirport=%s, departureDateTime=%s, arrivalDateTime=%s]",
				departureAirport, arrivalAirport, departureDateTime, arrivalDateTime);
	}

}
