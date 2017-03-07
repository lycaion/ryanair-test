package com.ryanair.flight.test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ryanair.flight.config.SpringConfig;
import com.ryanair.flight.config.SpringMVCConfig;
import com.ryanair.flight.web.pojo.response.Interconnection;
import com.ryanair.flight.web.pojo.route.Route;
import com.ryanair.flight.web.pojo.schedule.Schedule;
import com.ryanair.flight.web.service.DataServiceException;
import com.ryanair.flight.web.service.FlightConnectionService;
import com.ryanair.flight.web.service.dao.RoutesDAO;
import com.ryanair.flight.web.service.dao.ScheduleDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class, SpringMVCConfig.class })
@WebAppConfiguration
@TestPropertySource(properties = {"catalina.home = ./"})
public class DaoTest {

	private final static Logger log = LogManager.getLogger();

	@Autowired
	private RoutesDAO routesDAO;
	
	@Autowired
	private ScheduleDAO scheduleDAO;
	
	@Autowired
	private FlightConnectionService connectionService;
	
	
	
	private final String DEPARTURE_TER = "TER";
	private final String ARRIVAL_OPO= "OPO";
	private final String DEPARTURE_MAD = "MAD";
		
	
	private final String month = "3";
	private final String year = "2017";
	private final LocalDateTime departureTime = LocalDateTime.of(2017, 4, 1, 12, 00);
	private final LocalDateTime arrivalTime = LocalDateTime.of(2017, 4, 15, 12, 00);
	

	@Test
	public void testRoutes() {
		try {
			List<Route> routes = routesDAO.getAllByIATACode();
	
			log.info("routes {} ", routes.size());
			
			Assert.assertEquals(false, routes.isEmpty());
		} catch (DataServiceException e) {
			log.error(e);
		}
	}
	
	@Test
	public void testSchedule() {
		try {
			Schedule schedule = scheduleDAO.getScheduleBetweenDates(DEPARTURE_TER, ARRIVAL_OPO, month, year);
			
			Assert.assertEquals(true, schedule != null);
		} catch (DataServiceException e) {
			log.error(e);
		}
	}
	
	@Test
	public void testSpringContext() {
		Assert.assertNotNull(this.routesDAO);
		Assert.assertNotNull(this.scheduleDAO);
	}
	
	@Test
	public void testGroupedRouted() {
		try {
			List<Route> routes = this.routesDAO.getAllByIATACode();
			//now we calculate the connections 
			Map<String, List<String>> tree = routes.stream().collect(
					Collectors.groupingBy(Route::getAirportFrom,
	                        Collectors.mapping(Route::getAirportTo, Collectors.toList()))
	                );
			
			log.info("sortedroutes {}", tree);
			
			Assert.assertEquals(tree.get("TER"), Arrays.asList("LIS","OPO"));
			
		} catch (DataServiceException e) {
			log.error(e);
		}
	}
	
	@Test
	public void testDirectConnection() {
		try {
			List<Interconnection> connection = this.connectionService.getByDateWihtOneLayover(DEPARTURE_MAD, ARRIVAL_OPO,departureTime, arrivalTime);
			
			for(Interconnection inter : connection) {
				log.info("{}", inter);
			} 
			
		} catch (DataServiceException e) {
			log.error(e);
		}
	
	}

}
