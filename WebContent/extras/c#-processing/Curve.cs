
using System;
using System.Collections.Generic;
using System.Drawing;

namespace shape_detect
{
    public class Segment
    {
        public double Theta { get; set; }
        public double ST { get; set; }

        public Segment(double theta, double st)
        {
            Theta = theta;
            ST = st;
        }

        public override string ToString()
        {
            return "theta=" + Theta.ToString("f4") + ", st=" + ST.ToString("f4");
        }
    }

    public class Curve
    {
        private const double TWO_PI = 2.0 * Math.PI;

        private const int STROKE_WINDOW_SIZE = 8;
        private const int DIRECTIONS = 4;
        private const double MAX_LOW_PASS_THRESHOLD = 0.1;
        private const int MAX_EXCLUSIONS = 2;

        private List<double> curve;
        private List<Segment> segments;

        public int Length
        {
            get { return curve.Count; }
        }

        public double this[int t]
        {
            get { return curve[t]; }
        }

        public Curve(Stroke stroke)
        {
            curve = new List<double>();
            int end = stroke.Length - 2 * STROKE_WINDOW_SIZE;
            for (int i = 0; i < end; i++)
            {
                double x1, y1, x2, y2;
                stroke.Average(i, STROKE_WINDOW_SIZE, out x1, out y1);
                stroke.Average(i + STROKE_WINDOW_SIZE, STROKE_WINDOW_SIZE, out x2, out y2);
                double dx = x2 - x1;
                double dy = y2 - y1;
                double len = Math.Sqrt(dx * dx + dy * dy);
                double theta = Math.Acos(dx / len);
                if (dy > 0) theta = TWO_PI - theta;
                curve.Add(theta / TWO_PI);
            }
        }

        public void Downsample()
        {
            double shift = 1.0 / (2 * DIRECTIONS);
            for (int i = 0; i < curve.Count; i++)
            {
                double x = curve[i] + shift;
                if (x >= 1.0) x -= 1.0;
                curve[i] = Math.Floor(x * DIRECTIONS) / DIRECTIONS;
            }
        }

        public void Segment()
        {
            if (curve.Count == 0) return;
            segments = new List<Segment>();
            double last_theta = curve[0];
            int st = 0;
            for (int i = 1; i < curve.Count; i++)
            {
                st++;
                if (curve[i] != last_theta)
                {
                    segments.Add(new Segment(last_theta, st));
                    last_theta = curve[i];
                    st = 0;
                }
            }
            segments.Add(new Segment(last_theta, st));
            NormalizeSegments();
            LowPassSegments();
            //PrintSegments();
        }

        public void NormalizeSegments()
        {
            if (segments == null) return;
            double total_s = 0.0;
            for (int i = 0; i < segments.Count; i++)
            {
                total_s += segments[i].ST;
            }
            if (total_s == 0.0) return;
            total_s = 1.0 / total_s;
            for (int i = 0; i < segments.Count; i++)
            {
                segments[i].ST *= total_s;
            }
        }

        public void LowPassSegments()
        {
            List<Segment> old_segments = segments;
            double lpt = LowPassThreshold();
            segments = new List<Segment>();
            for (int i = 0; i < old_segments.Count; i++)
            {
                if (old_segments[i].ST > lpt)
                {
                    if (segments.Count > 0 &&
                        segments[segments.Count - 1].Theta == old_segments[i].Theta)
                    {
                        segments[segments.Count - 1].ST += old_segments[i].ST;
                    }
                    else
                    {
                        segments.Add(old_segments[i]);
                    }
                }
            }
            NormalizeSegments();
        }

        private double LowPassThreshold()
        {
            double lpt = 0.0;
            for (int i = 0; i < segments.Count; i++)
            {
                lpt += segments[i].ST;
            }
            lpt /= (2 * segments.Count);
            if (lpt > MAX_LOW_PASS_THRESHOLD)
                lpt = MAX_LOW_PASS_THRESHOLD;
            return lpt;
        }

        public double MatchSimple(Curve other)
        {
            double match = 0.0;
            int end = Math.Min(segments.Count, other.segments.Count);
            for (int i = 0; i < end; i++)
            {
                if (segments[i].Theta == other.segments[i].Theta)
                {
                    match += Math.Abs(segments[i].ST - other.segments[i].ST);
                }
                else
                {
                    match += segments[i].ST + other.segments[i].ST;
                }
            }
            // skips remainder
            return match;
        }

        public double Match(Curve other)
        {
            if (Math.Abs(segments.Count - other.segments.Count) > MAX_EXCLUSIONS)
                return Figure.MAX_MISSMATCH;
            if (segments.Count == 0) return Figure.MAX_MISSMATCH;
            double best_match = Figure.MAX_MISSMATCH;
            int[] map = new int[segments.Count];
            for (int i = 0; i < map.Length; i++) map[i] = -1;
            while (IncrementMap(map, other.segments.Count))
            {
                if (ValidMap(map))
                {
                    double match = Match(other, map);
                    if (match < best_match)
                        best_match = match;
                }
            }
            return best_match;
        }

        private bool ValidMap(int[] map)
        {
            int exclusion_count = 0;
            for (int i = 1; i < map.Length; i++)
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

        private bool IncrementMap(int[] map, int other_segment_length)
        {
            int index = map.Length - 1;
            while (IncrementMap(map, other_segment_length, index))
            {
                index--;
                if (index < 0) return false;
            }
            return true;
        }

        private bool IncrementMap(int[] map, int other_segment_length, int index)
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
            for (int i = 0; i < map.Length; i++)
            {
                if (map[i] != -1)
                {
                    if (segments[i].Theta == other.segments[map[i]].Theta)
                    {
                        match += Math.Abs(segments[i].ST - other.segments[map[i]].ST);
                    }
                    else
                    {
                        match += segments[i].ST + other.segments[map[i]].ST;
                    }
                }
                else
                {
                    match += segments[i].ST;
                }
            }
            bool[] reverse_map = new bool[other.segments.Count];
            for (int i = 0; i < other.segments.Count; i++) reverse_map[i] = false;
            for (int i = 0; i < map.Length; i++)
            {
                if (map[i] >= 0)
                    reverse_map[map[i]] = true;
            }
            for (int i = 0; i < other.segments.Count; i++)
            {
                if (!reverse_map[i])
                {
                    match += other.segments[i].ST;
                }
            }
            return match;
        }

        public void PrintSegments()
        {
            if (segments == null) return;
            for (int i = 0; i < segments.Count; i++)
            {
                Console.WriteLine(segments[i]);
            }
            Console.WriteLine();
        }
    }
}