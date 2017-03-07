package com.ryanair.flight.web.service;

import java.util.List;

import com.ryanair.flight.web.pojo.schedule.Schedule;

/**
 * <p>This service retrieves information about the Schedule of the flights</p>
 * @author angel
 *
 */
public interface ScheduleService {

	/**
	 * <p>Return the Schedule from the flights between the airports in the year and month</p>
	 * @param departure IATA code outgoing airport
	 * @param arrival IATA code incoming airport
	 * @param month 
	 * @param year
	 * @return
	 * @throws DataServiceException
	 */
	public List<Schedule> getScheduleBetweenDates(String departure, String arrival, String month, String year) throws DataServiceException;
	
}
