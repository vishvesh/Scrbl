
using System.Drawing;
using System.Collections.Generic;

namespace shape_detect
{
    public class Stroke
    {
        private List<Point> stroke;

        public int Length
        {
            get { return stroke.Count; }
        }

        public Point this[int index]
        {
            get { return stroke[index]; }
        }

        public Stroke()
        {
            stroke = new List<Point>();
        }

        public void Add(Point point)
        {
            if (stroke.Count > 0)
            {
                Point last = stroke[stroke.Count - 1];
                if (last.X == point.X && last.Y == point.Y)
                    return;
            }
            stroke.Add(point);
        }

        public void Average(int start, int length, out double x, out double y)
        {
            x = stroke[start].X;
            y = stroke[start].Y;
            int end = start + length;
            for (int i = start + 1; i < end; i++)
            {
                x += stroke[i].X;
                y += stroke[i].Y;
            }
            x /= length;
            y /= length;
        }
    }
}