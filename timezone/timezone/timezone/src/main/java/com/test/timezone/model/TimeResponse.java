package com.test.timezone.model;


public class TimeResponse {
	private String abbreviation;
	private String timezone;
	private String datetime;

	public TimeResponse(String abbreviation, String timezone, String datetime) {
		this.abbreviation = abbreviation;
		this.timezone = timezone;
		this.datetime = datetime;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

}
