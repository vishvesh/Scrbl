package com.scrbl.model;

import org.apache.log4j.Logger;

public class Point {
	
	Logger logger = Logger.getLogger(getClass());
	
	private double x;
	private double y;
	private double t;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(double x, double y, double t) {
		this.x = x;
		this.y = y;
		this.t = t;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getX() {
		return x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getY() {
		return y;
	}
	
	public void setT(double t) {
		this.t = t;
	}
	
	public double getT() {
		return t;
	}

	@Override
	public String toString() {
		String returnString = "Point [x=" + x + ", y=" + y + ", t=" + t + "]";
		logger.info(returnString);
		return returnString;
	}

}
