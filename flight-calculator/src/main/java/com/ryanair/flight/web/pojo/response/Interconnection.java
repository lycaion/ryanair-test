package com.ryanair.flight.web.pojo.response;

import java.util.ArrayList;
import java.util.List;

import com.ryanair.flight.web.pojo.BasePojo;

/**
 * <p>Pojo for the response</p>
 * @author angel santos
 *
 */
public class Interconnection extends BasePojo implements Comparable<Interconnection>{
	
	private static final long serialVersionUID = -6823665687861819968L;
	
	@SuppressWarnings("unused")
	//logic in getter, only for deserialization 
	private Integer stops;
	
	private List<Leg> legs;
	
	
	//~-------------------------------------------------------------- Getter and Setter

	public Integer getStops() {
		return (this.legs.size() > 0 ? (this.legs.size() -1) : 0);
	}
	
	
	public List<Leg> getLegs() {
		if(this.legs == null) {
			this.legs = new ArrayList<>();
		}
		return legs;
	}
	public void setLegs(List<Leg> legs) {
		this.legs = legs;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Interconnection o) {
		return getStops().compareTo(o.getStops());
	}


	//~--------------------------------------------------------------- ToString

	/*
	 * (non-Javadoc)
	 * @see com.ryanair.flight.web.pojo.BasePojo#toString()
	 */
	@Override
	public String toString() {
		return String.format("Interconnection [legs=%s, stops=%s]", legs, getStops());
	}

}
