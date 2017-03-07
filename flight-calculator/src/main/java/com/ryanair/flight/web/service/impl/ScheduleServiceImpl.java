package com.ryanair.flight.web.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ryanair.flight.web.pojo.schedule.Schedule;
import com.ryanair.flight.web.service.DataServiceException;
import com.ryanair.flight.web.service.ScheduleService;

/**
 * <p>Implementation for {@link ScheduleService} based on REST-API</p>
 * @author angel
 *
 */
@Component
public class ScheduleServiceImpl implements ScheduleService{

	private final static Logger log = LogManager.getLogger();
	
	@Value("${schedule_between_dates_uri}")
	private String scheduleBetweenDatesUri;
	@Value("${time_table_endpoint}")
	private String timetableEndpoint;
	
	/*
	 * (non-Javadoc)
	 * @see com.ryanair.flight.web.service.ScheduleService#getScheduleBetweenDates(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<Schedule> getScheduleBetweenDates(String departure, String arrival, String month, String year)throws DataServiceException {
		try{
			Map<String,String> vars = new HashMap<>();
			vars.put("departure", departure);
			vars.put("arrival", arrival);
			vars.put("month", month);
			vars.put("year", year);
			
			RestTemplate template = new RestTemplate();
			Schedule[] response = template.getForObject(timetableEndpoint + scheduleBetweenDatesUri, Schedule[].class, vars);
			return Arrays.asList(response);
		}catch(Exception e){
			log.error(e);
			throw new DataServiceException(e);
		}
	}

}
