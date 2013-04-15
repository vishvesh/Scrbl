package com.scrbl.model;

public class FigureSet
{
	private java.util.ArrayList<Figure> figures;

	public final int getSize()
	{
		return figures.size();
	}

	public final Figure getItem(int index)
	{
		return figures.get(index);
	}

	public FigureSet()
	{
		figures = new java.util.ArrayList<Figure>();
	}

	public final void AddFigure(Figure figure)
	{
		figures.add(figure);
	}
}