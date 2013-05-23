package com.scrbl.logic;

import java.util.List;

import org.apache.log4j.Logger;

import com.scrbl.model.Point;

public class VelocityVector {
	
	Logger logger = Logger.getLogger(getClass());

	public List<Double> calculateVelocityVector(List<Point> listOfPoints, String[] timeSplit, List<Double> velocityVector) {
		for(int i = 0; i < listOfPoints.size() - 1; i++) {	
			double x2 = listOfPoints.get(i+1).getX() - listOfPoints.get(i).getX();
	    	double y2 = listOfPoints.get(i+1).getY() - listOfPoints.get(i).getY();
	    	double t2 = Double.valueOf(timeSplit[i + 1]) - Double.valueOf(timeSplit[i]);
	    	
	    	double velocity = Math.sqrt(Math.pow(x2, 2) + Math.pow(y2, 2)) / (t2); 
	    	velocityVector.add(velocity);
	    	//logger.info("X2 - X1 : "+x2 + " : Y2 - Y1 : "+y2 +" : T2 - T1 : "+t2 + " : Velocity : "+velocity);
	    	
	    	logger.info("Velocity : "+velocity);
		  }
		return velocityVector;
	}
	
	public VelocityVector() {
		
	}
	
}
