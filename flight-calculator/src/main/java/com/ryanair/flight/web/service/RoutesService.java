package com.ryanair.flight.web.service;

import java.util.List;

import com.ryanair.flight.web.pojo.route.Route;

/**
 * <p>This service retrieves information about {@link Route}</p>
 * @author angel
 *
 */
public interface RoutesService {

	/**
	 * <p>Return <b>all</b> available routes based on the IATA codes</p>
	 * @return {@link List} of {@link Route} without any filter
	 * @throws DataServiceException if any expcetion ocurrs
	 */
	List<Route> getAllByIATACode() throws DataServiceException;
}
