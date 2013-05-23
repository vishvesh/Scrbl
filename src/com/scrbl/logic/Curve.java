package com.scrbl.logic;

import org.apache.log4j.Logger;

import com.scrbl.model.RefObject;
import com.scrbl.model.Segment;

public class Curve
{
	Logger logger = Logger.getLogger(getClass());
	
	private static final double TWO_PI = 2.0 * Math.PI;

	private static final int STROKE_WINDOW_SIZE = 8;
	private static final int DIRECTIONS = 4;
	private static final double MAX_LOW_PASS_THRESHOLD = 0.1;
	private static final int MAX_EXCLUSIONS = 2;

	private java.util.ArrayList<Double> curve;
	private java.util.ArrayList<Segment> segments;

	public final int getLength()
	{
		return curve.size();
	}

	public final double getItem(int t)
	{
		return curve.get(t);
	}

	public Curve(Stroke stroke)
	{
		curve = new java.util.ArrayList<Double>();
		int end = stroke.getLength() - 2 * STROKE_WINDOW_SIZE;
		logger.info("END Inside CURVE : "+end);
		
		for (int i = 0; i < end; i++)
		{
			double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
			RefObject<Double> tempRef_x1 = new RefObject<Double>(x1);
			RefObject<Double> tempRef_y1 = new RefObject<Double>(y1);
			stroke.Average(i, STROKE_WINDOW_SIZE, tempRef_x1, tempRef_y1);
			x1 = tempRef_x1.argvalue;
			y1 = tempRef_y1.argvalue;
			RefObject<Double> tempRef_x2 = new RefObject<Double>(x2);
			RefObject<Double> tempRef_y2 = new RefObject<Double>(y2);
			stroke.Average(i + STROKE_WINDOW_SIZE, STROKE_WINDOW_SIZE, tempRef_x2, tempRef_y2);
			x2 = tempRef_x2.argvalue;
			y2 = tempRef_y2.argvalue;
			
			//logger.info("x1 : "+x1 +" : y1 : "+y1 +" : x2 : "+x2+" : y2 : "+y2);
			
			double dx = x2 - x1;
			double dy = y2 - y1;
			double len = Math.sqrt(dx * dx + dy * dy);
			double theta = Math.acos(dx / len);
			if (dy > 0)
			{
				theta = TWO_PI - theta;
			}
			logger.info("THETA : "+theta);
			curve.add(theta / TWO_PI);
		}
	}

	public final void Downsample()
	{
		double shift = 1.0 / (2 * DIRECTIONS);
		for (int i = 0; i < curve.size(); i++)
		{
			double x = curve.get(i) + shift;
			if (x >= 1.0)
			{
				x -= 1.0;
			}
			curve.set(i, Math.floor(x * DIRECTIONS) / DIRECTIONS);
		}
		logger.info("Inside CURVE DOWN SAMPLE : SIZE of Curve : "+curve.size());
	}

	public final void Segment()
	{
		if (curve.isEmpty())
		{
			logger.info("CURVE IS EMPTY");
			return;
		}
		segments = new java.util.ArrayList<Segment>();
		double last_theta = curve.get(0);
		int st = 0;
		for (int i = 1; i < curve.size(); i++)
		{
			st++;
			if (curve.get(i) != last_theta)
			{
				segments.add(new Segment(last_theta, st));
				last_theta = curve.get(i);
				st = 0;
				logger.info("SEGMENT SIZE : "+segments.size());
			}
		}
		segments.add(new Segment(last_theta, st));
		NormalizeSegments();
		LowPassSegments();
		//PrintSegments();
	}

	public final void NormalizeSegments()
	{
		if (segments == null)
		{
			return;
		}
		double total_s = 0.0;
		for (int i = 0; i < segments.size(); i++)
		{
			total_s += segments.get(i).getST();
		}
		if (total_s == 0.0)
		{
			return;
		}
		total_s = 1.0 / total_s;
		for (int i = 0; i < segments.size(); i++)
		{
			segments.get(i).setST(segments.get(i).getST() * total_s);
		}
	}

	public final void LowPassSegments()
	{
		java.util.ArrayList<Segment> old_segments = segments;
		double lpt = LowPassThreshold();
		segments = new java.util.ArrayList<Segment>();
		for (int i = 0; i < old_segments.size(); i++)
		{
			if (old_segments.get(i).getST() > lpt)
			{
				if (segments.size() > 0 && segments.get(segments.size() - 1).getTheta() == old_segments.get(i).getTheta())
				{
					segments.get(segments.size() - 1).setST(segments.get(segments.size() - 1).getST() + old_segments.get(i).getST());
				}
				else
				{
					segments.add(old_segments.get(i));
				}
			}
		}
		NormalizeSegments();
	}

	private double LowPassThreshold()
	{
		double lpt = 0.0;
		for (int i = 0; i < segments.size(); i++)
		{
			lpt += segments.get(i).getST();
		}
		lpt /= (2 * segments.size());
		if (lpt > MAX_LOW_PASS_THRESHOLD)
		{
			lpt = MAX_LOW_PASS_THRESHOLD;
		}
		return lpt;
	}

	public final double MatchSimple(Curve other)
	{
		double match = 0.0;
		int end = Math.min(segments.size(), other.segments.size());
		for (int i = 0; i < end; i++)
		{
			if (segments.get(i).getTheta() == other.segments.get(i).getTheta())
			{
				match += Math.abs(segments.get(i).getST() - other.segments.get(i).getST());
			}
			else
			{
				match += segments.get(i).getST() + other.segments.get(i).getST();
			}
		}
		// skips remainder
		return match;
	}

	public final double Match(Curve other)
	{
		if (segments.isEmpty() || Math.abs(segments.size() - other.segments.size()) > MAX_EXCLUSIONS)
		{
			return Figure.MAX_MISSMATCH;
		}
		double best_match = Figure.MAX_MISSMATCH;
		int[] map = new int[segments.size()];
		for (int i = 0; i < map.length; i++)
		{
			map[i] = -1;
		}
		while (IncrementMap(map, other.segments.size()))
		{
			if (ValidMap(map))
			{
				double match = Match(other, map);
				if (match < best_match)
				{
					best_match = match;
				}
			}
		}
		return best_match;
	}

	private boolean ValidMap(int[] map)
	{
		int exclusion_count = 0;
		for (int i = 1; i < map.length; i++)
		{
			if (map[i] == -1)
			{
				exclusion_count++;
			}
			else if (map[i] <= map[i - 1])
			{
				return false;
			}
		}
		return exclusion_count <= MAX_EXCLUSIONS;
	}

	private boolean IncrementMap(int[] map, int other_segment_length)
	{
		int index = map.length - 1;
		while (IncrementMap(map, other_segment_length, index))
		{
			index--;
			if (index < 0)
			{
				return false;
			}
		}
		return true;
	}

	private boolean IncrementMap(int[] map, int other_segment_length, int index)
	{
		map[index]++;
		if (map[index] == other_segment_length)
		{
			map[index] = -1;
			return true;
		}
		return false;
	}

	private double Match(Curve other, int[] map)
	{
		double match = 0.0;
		for (int i = 0; i < map.length; i++)
		{
			if (map[i] != -1)
			{
				if (segments.get(i).getTheta() == other.segments.get(map[i]).getTheta())
				{
					match += Math.abs(segments.get(i).getST() - other.segments.get(map[i]).getST());
				}
				else
				{
					match += segments.get(i).getST() + other.segments.get(map[i]).getST();
				}
			}
			else
			{
				match += segments.get(i).getST();
			}
		}
		boolean[] reverse_map = new boolean[other.segments.size()];
		for (int i = 0; i < other.segments.size(); i++)
		{
			reverse_map[i] = false;
		}
		for (int i = 0; i < map.length; i++)
		{
			if (map[i] >= 0)
			{
				reverse_map[map[i]] = true;
			}
		}
		for (int i = 0; i < other.segments.size(); i++)
		{
			if (!reverse_map[i])
			{
				match += other.segments.get(i).getST();
			}
		}
		return match;
	}

	public final void PrintSegments()
	{
		if (segments == null)
		{
			return;
		}
		for (int i = 0; i < segments.size(); i++)
		{
			logger.info(segments.get(i));
		}
		System.out.println();
	}
}