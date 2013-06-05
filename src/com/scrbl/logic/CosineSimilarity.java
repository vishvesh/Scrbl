package com.scrbl.logic;

import java.util.List;

import com.scrbl.action.BaseAction;

public class CosineSimilarity extends BaseAction {
	
	//Logger logger = Logger.getLogger(getClass());

	/**
	 * @author Vishvesh Deshmukh
	 */
	private static final long serialVersionUID = 1L;

	public double calculateCosineSimilarity(List<Double> vectorA, List<Double> vectorB) {
		System.out.println();
		logger.info("**********************************************************************************************************");
		logger.info("Before : vectorB.size() < vectorA.size() :  Template VECTOR's size : "+vectorA.size()+" : Current VECTOR's size : "+vectorB.size());
		
		if(vectorB.size() < vectorA.size()) {
			int difference = vectorA.size() - vectorB.size();
			logger.info("Current Vector's Size is LESS THAN Template Vector's Size");
			logger.info("Difference in size of vectors : 'Template Vector Size -(MINUS)- Current Vector Size' : "+difference);
			logger.info("Ignoring last : "+difference +" : elements from the Template Vector | Comparing only First : "+vectorB.size() +" : elements");
			
			vectorA = vectorA.subList(0, vectorB.size());
			logger.info("Size of Template Vector after subList : Setting the size of Template Vector to Current Vector's Size : "+vectorA.size());

			logger.info("After : vectorB.size() < vectorA.size() :  Template VECTOR's size : "+vectorA.size()+" : Current VECTOR's size : "+vectorB.size());
			
		} else {
			logger.info("Current Vector's Size is GREATER THAN Template Vector's Size");
			logger.info("So ignoring last : "+(vectorB.size() - vectorA.size())+" : elements from Current vector");
		}
		
		logger.info("Calculating Cosine Similarity of Template Vector and Current Vector");
		logger.info("**********************************************************************************************************");
		System.out.println();
		
		double dotProduct = DotProduct(vectorA, vectorB);
		double magnitudeOfA = Magnitude(vectorA);
		double magnitudeOfB = Magnitude(vectorB);

		return dotProduct / (magnitudeOfA * magnitudeOfB);
	}

	private double DotProduct(List<Double> vectorA, List<Double> vectorB) {
		double dotProduct = 0;
		try {
			for(int i = 0; i < vectorA.size() - 1; i++) {
			    dotProduct += (vectorA.get(i) * vectorB.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
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
