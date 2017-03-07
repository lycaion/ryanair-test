package com.ryanair.flight.web.pojo.schedule;

import java.time.LocalTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ryanair.flight.web.pojo.BasePojo;
import com.ryanair.flight.web.service.util.StringToLocalTimeJacksonConverter;

public class Flight extends BasePojo{

	private static final long serialVersionUID = 7039451399357670341L;
	
	
	private Integer number;
	
	@JsonDeserialize(using = StringToLocalTimeJacksonConverter.class)
	private LocalTime departureTime;
	
	@JsonDeserialize(using = StringToLocalTimeJacksonConverter.class)
	private LocalTime arrivalTime;
	
	//~---------------------------------------------- Getter and Setter
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public LocalTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}
	public LocalTime getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	
	//~----------------------------------------------- ToString
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Flight [number=%s, departureTime=%s, arrivalTime=%s]", number, departureTime, arrivalTime);
	}
	
}
