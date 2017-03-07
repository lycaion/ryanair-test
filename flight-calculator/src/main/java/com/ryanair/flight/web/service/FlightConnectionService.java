package com.ryanair.flight.web.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ryanair.flight.web.pojo.response.Interconnection;

/**
 * <p>Service that calculates the interconnection between the given dates and the airports</p>
 * @author angel santos
 *
 */
public interface FlightConnectionService {

	/**
	 * <p>Calculate the interconnection with one layover at the most between the given dates</p>
	 * @param departure outcoming airport
	 * @param arrival airport incoming airport
	 * @param departureTime
	 * @param arrivalTime
	 * @return
	 * @throws DataServiceException
	 */
	List<Interconnection> getByDateWihtOneLayover(String departure, String arrival, LocalDateTime departureTime, LocalDateTime arrivalTime) throws DataServiceException;
	
}
