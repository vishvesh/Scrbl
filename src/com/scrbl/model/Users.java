package com.scrbl.model;

import java.util.List;

import org.apache.log4j.Logger;

public class Users {
	
	Logger logger = Logger.getLogger(getClass());

	private Long id;
	private String email;
	private String firstName;
	private String lastName;
	private List<Point> templateData;
	private List<Point> matchData;
	private double cosineValue;
	private double shapeValue;
	private String ageGroup;
	private String ipAddress;
	private String latitude;
	private String longitude;
	
	@Override
	public String toString() {
		String returnString = "User : \nid = " + id + ", \nemail = " + email + ", \nfirstName = "
				+ firstName + ", \nlastName = " + lastName + ", \ntemplateData = "
				+ templateData + ", \nmatchData = " + matchData + ", \ncosineValue = "
				+ cosineValue + ", \nshapeValue = " + shapeValue + ", \nageGroup = "
				+ ageGroup + ", \nipAddress = " + ipAddress + ", \nlatitude = "
				+ latitude + ", \nlongitude = " + longitude;
		logger.info(returnString);
		return returnString;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<Point> getTemplateData() {
		return templateData;
	}
	public void setTemplateData(List<Point> templateData) {
		this.templateData = templateData;
	}
	public List<Point> getMatchData() {
		return matchData;
	}
	public void setMatchData(List<Point> matchData) {
		this.matchData = matchData;
	}
	public double getCosineValue() {
		return cosineValue;
	}
	public void setCosineValue(double cosineValue) {
		this.cosineValue = cosineValue;
	}
	public double getShapeValue() {
		return shapeValue;
	}
	public void setShapeValue(double shapeValue) {
		this.shapeValue = shapeValue;
	}
	public String getAgeGroup() {
		return ageGroup;
	}
	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
