package com.scrbl.logic;

import java.util.List;

import org.apache.log4j.Logger;

public class CosineSimilarity {
	
	Logger logger = Logger.getLogger(getClass());

	public double calculateCosineSimilarity(List<Double> vecA, List<Double> vecB) {
		double dotProduct = DotProduct(vecA, vecB);
		double magnitudeOfA = Magnitude(vecA);
		double magnitudeOfB = Magnitude(vecB);

		return dotProduct / (magnitudeOfA * magnitudeOfB);
	}

	private double DotProduct(List<Double> vectorA, List<Double> vectorB) {
		logger.info("Initial VECTOR's size : "+vectorA.size()+" : Current VECTOR's size : "+vectorB.size());
		double dotProduct = 0;
		for (int i = 0; i < vectorA.size() - 1; i++) {
			if(vectorB.get(i) != null) {
				//logger.info("LEMGTGH LESS STILL COMES IN WTF");
				dotProduct += (vectorA.get(i) * vectorB.get(i));
			}
		}
		return dotProduct;
	}
	
	private double Magnitude(List<Double> vector)
	{
		return Math.sqrt(DotProduct(vector, vector));
	}
	
	public CosineSimilarity() {
		
	}
	
}
