
using System.Collections.Generic;

namespace shape_detect
{
    public class FigureSet
    {
        private List<Figure> figures;

        public int Size
        {
            get { return figures.Count; }
        }

        public Figure this[int index]
        {
            get { return figures[index]; }
        }

        public FigureSet()
        {
            figures = new List<Figure>();
        }

        public void AddFigure(Figure figure)
        {
            figures.Add(figure);
        }
    }
}