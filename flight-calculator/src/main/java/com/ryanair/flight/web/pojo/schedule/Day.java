package com.ryanair.flight.web.pojo.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ryanair.flight.web.pojo.BasePojo;

public class Day extends BasePojo{

	private static final long serialVersionUID = -5217486915536751078L;
	
	
	private String day;
	private List<Flight> flights;
	
	// ~----------------------------------------- Getter and Setters
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public List<Flight> getFlights() {
		if(this.flights == null) {
			this.flights = new ArrayList<>();
		}
		return flights;
	}
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
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
        if (!(obj instanceof Day)) {
            return false;
        }
        Day other = (Day) obj;
        return Objects.equals(day, other.day);
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.day, this.flights);
	}
	
	//~--------------------------------------------- ToString
	/*
	 * (non-Javadoc)
	 * @see com.ryanair.flight.web.pojo.BasePojo#toString()
	 */
	@Override
	public String toString() {
		return String.format("Day [day=%s, flights=%s]", day, flights);
	}
	
	
}
