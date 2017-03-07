package com.ryanair.flight.web.service.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ryanair.flight.web.pojo.route.Route;
import com.ryanair.flight.web.service.DataServiceException;
import com.ryanair.flight.web.service.dao.RoutesDAO;

/**
 * <p>Implementation for {@link RoutesDAO} based on REST-API</p>
 * @author angel
 *
 */
@Component
public class RoutesDAOImpl implements RoutesDAO {
	
	private final static Logger log = LogManager.getLogger();
	
	@Value("${iata_code_uri}")
	private String iataCodeUri;
	
	@Value("${core_endpoint}")
	private String coreEndpoint;
	
	/*
	 * (non-Javadoc)
	 * @see com.ryanair.flight.web.service.RoutesService#getAllByIATACode()
	 */
	public List<Route> getAllByIATACode() throws DataServiceException {
		try{
			final String endpoint = coreEndpoint + iataCodeUri;
			log.debug("Calling to {}", endpoint);
			RestTemplate restTemplate = new RestTemplate();
			Route[] response = restTemplate.getForObject(endpoint, Route[].class);
			log.debug("Found {} AIAT codes", response.length);
			return Arrays.asList(response);
		}catch(Exception e) {
			log.error(e);
			throw new DataServiceException(e);
		}
	}

}
