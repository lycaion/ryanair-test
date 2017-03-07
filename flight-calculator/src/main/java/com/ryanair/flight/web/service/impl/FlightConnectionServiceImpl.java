package com.ryanair.flight.web.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.ryanair.flight.web.pojo.response.Interconnection;
import com.ryanair.flight.web.pojo.response.Leg;
import com.ryanair.flight.web.pojo.route.Route;
import com.ryanair.flight.web.pojo.schedule.Day;
import com.ryanair.flight.web.pojo.schedule.Flight;
import com.ryanair.flight.web.pojo.schedule.Schedule;
import com.ryanair.flight.web.service.DataServiceException;
import com.ryanair.flight.web.service.FlightConnectionService;
import com.ryanair.flight.web.service.dao.RoutesDAO;
import com.ryanair.flight.web.service.dao.ScheduleDAO;

@Component
public class FlightConnectionServiceImpl implements FlightConnectionService{
	
	private final static Logger log = LogManager.getLogger();
	
	private RoutesDAO routesDAO;
	private ScheduleDAO scheduleDAO;
	
	
	// ~------------------------------------------------------------------------- Constructor
	@Autowired
	public FlightConnectionServiceImpl(RoutesDAO routesDAO, ScheduleDAO scheduleDAO) {
		this.routesDAO = routesDAO;
		this.scheduleDAO = scheduleDAO;
	}
	
	
	//~-------------------------------------------------------------------- Public Methods
	/*
	 * (non-Javadoc)
	 * @see com.ryanair.flight.web.service.FlightConnectionService#getByDateWihtOneLayover(java.lang.String, java.lang.String, java.time.LocalDate, java.time.LocalDate)
	 */
	@Override
	public List<Interconnection> getByDateWihtOneLayover(final String departure, final String arrival, 
														 final LocalDateTime departureTime, final LocalDateTime arrivalTime)
														 throws DataServiceException {
		validate(departure, arrival, departureTime, arrivalTime);
		log.debug("Calculate flights from {}  to {} between {} and {}", departure, arrival,departureTime, arrivalTime);
		
		//logic
		List<Interconnection> interconnections = new ArrayList<>();
		
		List<Route> routes = this.routesDAO.getAllByIATACode();
		
		//first we calculate the direct connections
		List<Interconnection> direct = this.getAllSchedulesBetweenDates(departure, arrival, departureTime, arrivalTime);
		interconnections.addAll(direct);
		
		//now we calculate the connections if any 
		Map<String, List<String>> connectedAirport = routes.parallelStream().collect(
																Collectors.groupingBy(Route::getAirportFrom, 
																						Collectors.mapping(Route::getAirportTo, Collectors.toList())));
		
		List<String> connectedAirports = connectedAirport.get(departure);
		
		for(String candidate : connectedAirports) {
			//if any candidate to be lavoyer connects to arrival airport
			if(connectedAirport.get(candidate).contains(arrival)) {
				log.debug("{} has a connection with {} (origin is {} )", candidate, arrival, departure);
				//Combining Interconnection with the given restrictions
				List<Interconnection> layovers = this.getAllSchedulesBetweenDates(departure, candidate, arrival, departureTime, arrivalTime);
				interconnections.addAll(layovers);
			}
		}
		Collections.sort(interconnections);
		return interconnections;
	}
	
	
	
	//~-------------------------------------------------------------------------- Private methods
	/**
	 * <p>Retrieves all {@link Schedule} between dates</p>
	 * @param departure incoming airport
	 * @param arrival outcoming airport
	 * @param pivotal intermediate airport
	 * @param departureTime 
	 * @param arrivalTime
	 * @return
	 * @throws DataServiceException
	 */
	private List<Interconnection> getAllSchedulesBetweenDates(String departure, String pivotal, String arrival, LocalDateTime departureTime, LocalDateTime arrivalTime) {
		List<Interconnection> interconnections =  new ArrayList<>();

		while(departureTime.isBefore(arrivalTime)) {
			String month = String.valueOf(departureTime.get(ChronoField.MONTH_OF_YEAR));
			String year = String.valueOf(departureTime.get(ChronoField.YEAR));
			//We use a simply cache to reduce calling REST
			Map<KeyCache, Schedule> cache = new HashMap<>();
			
			try {
				Schedule schedule = null;
				if(pivotal == null){
					log.debug("Call to direct flight {}, {}, {}, {}", departure, arrival, month, year);
					schedule  = this.scheduleDAO.getScheduleBetweenDates(departure, arrival, month, year);
				}else {
					log.debug("Call to indirect flight {}, {}, {}, {}", departure, arrival, month, year);
					schedule  = this.scheduleDAO.getScheduleBetweenDates(departure, pivotal, month, year);
				}
				
				
				for(Day day: schedule.getDays()) {
					for(Flight flight : day.getFlights()) {
						
						LocalDate date = LocalDate.of(Integer.parseInt(year),Integer.parseInt(month), Integer.parseInt(day.getDay()));
						Leg leg = null;
						
						if(pivotal == null) {
							leg = this.getLegFromSchedule(departure, arrival, date, flight);
						}else {
							leg = this.getLegFromSchedule(departure, pivotal, date, flight);
						}
						//check if retrieves dates is between leg dates
						
						if(leg.getArrivalDateTime().isAfter(arrivalTime) || leg.getDepartureDateTime().isBefore(departureTime)) {
							continue;
						}
						
					
						
						LocalDateTime limitTime = leg.getArrivalDateTime();
						Leg pivotalLeg = null;
						//now calculate the pivotal Leg if must
						if(pivotal != null){
							//if there is a connection
							Schedule pivotalSchedule = null;
							KeyCache key = new KeyCache(pivotal, arrival, month, year);
							if(cache.containsKey(key)) {
								log.debug("Get the cached value for {}", key);
								pivotalSchedule =cache.get(key);
								
							}else {
								log.debug("Call to tail flight {}, {}, {}, {}", pivotal, arrival, month, year);
								pivotalSchedule = this.scheduleDAO.getScheduleBetweenDates(pivotal, arrival, month, year);
								cache.put(key, pivotalSchedule);
							}
							
							Day pivotalDay = pivotalSchedule.getDays()
													.parallelStream()
													.filter(e -> e.equals(day))
													.findFirst()
													.orElse(new Day());
							//only looks for the flights which arrival time is before than 2 hours 
							Flight pivotalFlight = pivotalDay.getFlights()
														.parallelStream()
														.findFirst()
														.orElse(null);
							
							if(pivotalFlight != null) {
								//there is connection if the departure time is before than limit time plus 2 hours
								pivotalLeg = this.getLegFromSchedule(pivotal, arrival, date, pivotalFlight);
								if(pivotalLeg.getDepartureDateTime().isBefore(limitTime.plusHours(2))) {
									//do not use this leg
									pivotalLeg = null;
								}
								
							}
											
						}
						
						Interconnection interconnection = new Interconnection();
						
						//only add the interconnection if there is a flight
						if(pivotal == null || (pivotalLeg != null && pivotal != null)) {
							interconnection.getLegs().add(leg);
						}
						if(pivotalLeg != null) {
							interconnection.getLegs().add(pivotalLeg);
						}
						
						
						//only if we have legs
						if(!interconnection.getLegs().isEmpty()) {
							interconnections.add(interconnection);
						}
						
					}
				}
				
				
			} catch (DataServiceException e) {
				log.warn("No direct flies for {} {} {} {}",departure, arrival, month, year);
			}
			
		
			//add one month for the loop
			departureTime = departureTime.plusMonths(1);
		}
		
		return interconnections;
	}
	
	/**
	 * <p>Retrieves all {@link Schedule} between dates</p>
	 * @param departure incoming airport
	 * @param arrival outcoming airport
	 * @param departureTime 
	 * @param arrivalTime
	 * @return
	 * @throws DataServiceException
	 */
	private List<Interconnection> getAllSchedulesBetweenDates(String departure, String arrival, LocalDateTime departureTime, LocalDateTime arrivalTime) {
		return this.getAllSchedulesBetweenDates(departure, null, arrival, departureTime, arrivalTime);
	}

	
	
	/**
	 * <p>This method only validates the input params</p>
	 * @param departure
	 * @param arrival
	 * @param departureTime
	 * @param arrivalTime
	 */
	private void validate(final String departure, final String arrival, 
			 final LocalDateTime departureTime, final LocalDateTime arrivalTime) {
		//check conditions
		Objects.requireNonNull(departure, "departure must not be null!");
		Objects.requireNonNull(arrival, "arrival must not be null!");
		Objects.requireNonNull(departureTime, "departureTime must not be null!");
		Objects.requireNonNull(arrivalTime, "arrivalTime must not be null!");
		
		Assert.state(departureTime.isBefore(arrivalTime), "departureTime must be before than arrivalTime");
	}
	
	/**
	 * <p>Get the {@link Leg} from the {@link Schedule}</p>
	 * @param arrival
	 * @param departure
	 * @param date
	 * @param flight
	 * @return
	 */
	private Leg getLegFromSchedule(String departure, String arrival, LocalDate date, Flight flight) {
		Leg leg = new Leg();
		leg.setArrivalAirport(arrival);
		leg.setDepartureAirport(departure);
		leg.setArrivalDateTime(LocalDateTime.of(date, flight.getArrivalTime()));
		leg.setDepartureDateTime(LocalDateTime.of(date, flight.getDepartureTime()));
		
		return leg;
	}
	/**
	 * <p>Key class for the cache</p>
	 * @author angel
	 *
	 */
	private class KeyCache {
		private String pivotal, arrival, month, year;

		public KeyCache(String pivotal, String arrival, String month, String year) {
			this.pivotal = pivotal;
			this.arrival = arrival;
			this.month = month;
			this.year = year;
		}
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
	        if (!(obj instanceof KeyCache)) {
	            return false;
	        }
	        KeyCache other = (KeyCache) obj;
	        return Objects.equals(month, other.month)
	        			&& Objects.equals(year, other.year)
	        			&& Objects.equals(pivotal, other.pivotal)
	        			&& Objects.equals(arrival, other.arrival);
		}
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return Objects.hash(month, year, pivotal, arrival);
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return String.format("CacheKey [pivotal=%s, arrival=%s, month=%s, year=%s]", pivotal, arrival, month, year);
		}
		
		
		
	}
}
