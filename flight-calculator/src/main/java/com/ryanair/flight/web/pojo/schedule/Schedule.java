package com.ryanair.flight.web.pojo.schedule;

import java.util.List;
import java.util.Objects;

import com.ryanair.flight.web.pojo.BasePojo;

public class Schedule extends BasePojo{

	private static final long serialVersionUID = -2915192778114348734L;
	
	
	private String month;
	private List<Day> days;
	
	// ~-------------------------------------------- Getter and Setter
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public List<Day> getDays() {
		return days;
	}
	public void setDays(List<Day> days) {
		this.days = days;
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
        Schedule other = (Schedule) obj;
        return Objects.equals(month, other.month);
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.month, this.days);
	}
	//	~------------------------------------------------- ToString
	
	
	@Override
	public String toString() {
		return String.format("Schedule [month=%s, days=%s]", month, days);
	}
	
}
