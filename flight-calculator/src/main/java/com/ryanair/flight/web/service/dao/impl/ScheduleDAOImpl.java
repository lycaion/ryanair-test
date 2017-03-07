package com.ryanair.flight.web.service.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ryanair.flight.web.pojo.schedule.Schedule;
import com.ryanair.flight.web.service.DataServiceException;
import com.ryanair.flight.web.service.dao.ScheduleDAO;

/**
 * <p>Implementation for {@link ScheduleDAO} based on REST-API</p>
 * @author angel
 *
 */
@Component
public class ScheduleDAOImpl implements ScheduleDAO{

	private final static Logger log = LogManager.getLogger();
	
	@Value("${schedule_between_dates_uri}")
	private String scheduleBetweenDatesUri;
	
	@Value("${time_table_endpoint}")
	private String timetableEndpoint;
	
	/*
	 * (non-Javadoc)
	 * @see com.ryanair.flight.web.service.ScheduleService#getScheduleBetweenDates(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Schedule getScheduleBetweenDates(String departure, String arrival, String month, String year)throws DataServiceException {
		try{
			final String endpoint = timetableEndpoint + scheduleBetweenDatesUri;
			Map<String,String> vars = new HashMap<>();
			vars.put("departure", departure);
			vars.put("arrival", arrival);
			vars.put("month", month);
			vars.put("year", year);
			log.debug("Calling to {} with {}", endpoint, vars);
			RestTemplate restTemplate = new RestTemplate();
			
			Schedule response = restTemplate.getForObject(endpoint, Schedule.class, vars);
			log.debug("Found {} Schedules for {} {} {} {}", response, departure, arrival, month, year);
			return response;
		}catch(Exception e){
			log.error(e);
			throw new DataServiceException(e);
		}
	}

}
