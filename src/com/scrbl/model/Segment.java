package com.scrbl.model;

public class Segment
{
	private double privateTheta;
	public final double getTheta()
	{
		return privateTheta;
	}
	public final void setTheta(double value)
	{
		privateTheta = value;
	}
	private double privateST;
	public final double getST()
	{
		return privateST;
	}
	public final void setST(double value)
	{
		privateST = value;
	}

	public Segment(double theta, double st)
	{
		setTheta(theta);
		setST(st);
	}

	@Override
	public String toString()
	{
		return "theta=" + String.format("%0.4f", getTheta()) + ", st=" + String.format("%0.4f", getST());
	}
}