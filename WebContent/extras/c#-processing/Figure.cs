
using System.Collections.Generic;

namespace shape_detect
{
    public class Figure
    {
        public const double MAX_MISSMATCH = 10000.0;

        private List<Stroke> strokes;
        private List<Curve> curves;

        public int Length
        {
            get { return strokes.Count; }
        }

        public Stroke this[int index]
        {
            get { return strokes[index]; }
        }

        public Figure()
        {
            strokes = new List<Stroke>();
            curves = new List<Curve>();
        }

        public void Add(Stroke stroke)
        {
            strokes.Add(stroke);
        }

        public void CurveLastStroke()
        {
            if (strokes.Count > 0)
            {
                Curve curve = new Curve(strokes[strokes.Count - 1]);
                curve.Downsample();
                curve.Segment();
                curves.Add(curve);
            }
        }

        public Curve GetCurve(int index)
        {
            if (index < curves.Count)
                return curves[index];
            else return null;
        }

        public void Clear()
        {
            strokes.Clear();
            curves.Clear();
        }

        public double Match(Figure other)
        {
            if (curves.Count == other.curves.Count)
            {
                double match = 0.0;
                for (int i = 0; i < curves.Count; i++)
                {
                    match += curves[i].Match(other.curves[i]);
                }
                return match / curves.Count;
            }
            else
            {
                return MAX_MISSMATCH;
            }
        }
    }
}