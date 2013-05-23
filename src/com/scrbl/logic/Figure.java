package com.scrbl.logic;

public class Figure
{
	public static final double MAX_MISSMATCH = 10000.0;

	private java.util.ArrayList<Stroke> strokes;
	private java.util.ArrayList<Curve> curves;
	
	public int getCurvesLength() {
		return curves.size();
	}

	public final int getLength()
	{
		return strokes.size();
	}

	public final Stroke getItem(int index)
	{
		return strokes.get(index);
	}

	public Figure()
	{
		strokes = new java.util.ArrayList<Stroke>();
		curves = new java.util.ArrayList<Curve>();
	}

	public final void Add(Stroke stroke)
	{
		strokes.add(stroke);
	}

	public final void CurveLastStroke()
	{
		if (strokes.size() > 0)
		{
			System.out.println("STROKES SIZE : "+strokes.size());
			Curve curve = new Curve(strokes.get(strokes.size() - 1));
			curve.Downsample();
			curve.Segment();
			curves.add(curve);
		}
	}

	public final Curve GetCurve(int index)
	{
		if (index < curves.size())
		{
			return curves.get(index);
		}
		else
		{
			return null;
		}
	}

	public final void Clear()
	{
		strokes.clear();
		curves.clear();
	}

	public final double Match(Figure other)
	{
		if (curves.size() == other.curves.size())
		{
			double match = 0.0;
			for (int i = 0; i < curves.size(); i++)
			{
				match += curves.get(i).Match(other.curves.get(i));
			}
			return match / curves.size();
		}
		else
		{
			return MAX_MISSMATCH;
		}
	}
}