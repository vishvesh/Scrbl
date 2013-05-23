package com.scrbl.logic;

import com.scrbl.model.Point;
import com.scrbl.model.RefObject;

public class Stroke
{
	private java.util.ArrayList<Point> stroke;

	public final int getLength()
	{
		return stroke.size();
	}

	public final Point getItem(int index)
	{
		return stroke.get(index);
	}

	public Stroke()
	{
		stroke = new java.util.ArrayList<Point>();
	}

	public final void Add(Point point)
	{
		if (stroke.size() > 0)
		{
			Point last = stroke.get(stroke.size() - 1);
			if (last.getX() == point.getX() && last.getY() == point.getY())
			{
				return;
			}
		}
		stroke.add(point);
	}

	public final void Average(int start, int length, RefObject<Double> x, RefObject<Double> y)
	{
		x.argvalue = (double) stroke.get(start).getX();
		y.argvalue = (double) stroke.get(start).getY();
		int end = start + length;
		for (int i = start + 1; i < end; i++)
		{
			x.argvalue += stroke.get(i).getX();
			y.argvalue += stroke.get(i).getY();
		}
		x.argvalue /= length;
		y.argvalue /= length;
	}
}