package com.ryanair.flight.web.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ryanair.flight.web.pojo.response.Interconnection;
import com.ryanair.flight.web.service.DataServiceException;
import com.ryanair.flight.web.service.FlightConnectionService;
/**
 * <p>Controller for {@code /interconnections} related REST-API</p>
 * 
 * @author angel santos
 *
 */
@RestController("/interconnections")
public class InterconnetionsController {

	private final static Logger log = LogManager.getLogger(InterconnetionsController.class);
	
	@Autowired
	private FlightConnectionService flightConnectionService;
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Interconnection> filterFlightsBetweenDates(@RequestParam(value="departure")String departure, 
												@RequestParam(value="arrival")String arrival,
												@RequestParam(value="departureDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime departureDateTime,
												@RequestParam(value="arrivalDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime arrivalDateTime)
																throws DataServiceException {
		log.info("[-> Calling... filterFlightsBetweenDates ('departure': {}, 'arrival': {}, 'departureDateTime': {}, 'arrivalDateTime': {})]"
																			,departure, arrival, departureDateTime, arrivalDateTime);
		
		List<Interconnection> interconnections = this.flightConnectionService.getByDateWihtOneLayover(departure, arrival, departureDateTime, arrivalDateTime);
		log.info("[<- Found {} internconnection]", interconnections.size());
		return interconnections;
		
	}
	
}
