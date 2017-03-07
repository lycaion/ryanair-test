package com.ryanair.flight.web.pojo.route;

import com.ryanair.flight.web.pojo.BasePojo;

/**
 * <p>This pojo represents the result of a call to https://api.ryanair.com/core/3/routes/</p>
 * @author angel santos
 *
 */
public class Route extends BasePojo {

	private static final long serialVersionUID = -6036632528815626276L;
	
	private String airportFrom;
	private String airportTo;
	private Boolean newRoute;
	private Boolean seasonalRoute;
	private String connectingAirpor;
	
	
	//~----------------------------------------------- Getter and Setter
	public String getAirportFrom() {
		return airportFrom;
	}
	public void setAirportFrom(String airportFrom) {
		this.airportFrom = airportFrom;
	}
	public String getAirportTo() {
		return airportTo;
	}
	public void setAirportTo(String airportTo) {
		this.airportTo = airportTo;
	}
	public Boolean getNewRoute() {
		return newRoute;
	}
	public void setNewRoute(Boolean newRoute) {
		this.newRoute = newRoute;
	}
	public Boolean getSeasonalRoute() {
		return seasonalRoute;
	}
	public void setSeasonalRoute(Boolean seasonalRoute) {
		this.seasonalRoute = seasonalRoute;
	}
	public String getConnectingAirpor() {
		return connectingAirpor;
	}
	public void setConnectingAirpor(String connectingAirpor) {
		this.connectingAirpor = connectingAirpor;
	}
	
	//~----------------------------------------------------- ToString
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Route [airportFrom=%s, airportTo=%s, newRoute=%s, seasonalRoute=%s, connectingAirpor=%s]",
				airportFrom, airportTo, newRoute, seasonalRoute, connectingAirpor);
	}
	
}
