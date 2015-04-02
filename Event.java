package comp2100.ass1;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Event implements Serializable{
	private String event;
	private int date;
	private int month;
	private int year;

	private Calendar calendar;
	
	public Event(String event, String date, String month, String year) {
		this.event = event;
		this.date = Integer.parseInt(date);
		this.month = Integer.parseInt(month);
		this.year = Integer.parseInt(year);
		
		calendar = new GregorianCalendar(this.year, (this.month-1), this.date);
		
	}
	
	public Calendar getCalendar() {
		return calendar;
	}
	
	@Override
	public String toString() {
		return this.year + ", " + this.month + ", " + this.date + ", " + event;
	}
	
	public String getYear() {
		return Integer.toString(year);
	}
	
	public String getMonth() {
		return Integer.toString(month);
	}
	
	public String getDate() {
		return Integer.toString(date);
	}
	
	public String getEvent() {
		return event;
	}
}
