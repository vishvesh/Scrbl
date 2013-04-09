
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;

namespace shape_detect
{
    public class MainWin : Form
    {
        public static void Run()
        {
            MainWin win = new MainWin();
            Application.Run(win);
        }

        private Figure figure;
        private Stroke stroke;
        private Figure template;

        private Label match_label;

        private MainWin()
        {
            figure = new Figure();
            stroke = null;
            template = null;

            Text = "ShapeDetect";
            WindowState = FormWindowState.Maximized;

            Button clear_button = new Button();
            clear_button.Text = "Clear";
            clear_button.Location = new Point(10, 10);
            clear_button.Click += clear_button_Click;
            Controls.Add(clear_button);

            Button template_button = new Button();
            template_button.Text = "Template";
            template_button.Location = new Point(150, 10);
            template_button.Click += template_button_Click;
            Controls.Add(template_button);

            Button match_button = new Button();
            match_button.Text = "Match";
            match_button.Location = new Point(300, 10);
            match_button.Click += match_button_Click;
            Controls.Add(match_button);

            match_label = new Label();
            match_label.Location = new Point(600, 10);
            match_label.Size = new Size(300, 30);
            Controls.Add(match_label);

            MouseMove += MainWin_MouseMove;
            Paint += MainWin_Paint;
        }

        private void clear_button_Click(object sender, EventArgs e)
        {
            figure = new Figure();
            stroke = null;
            Invalidate();
        }

        private void template_button_Click(object sender, EventArgs e)
        {
            template = figure;
        }

        private void match_button_Click(object sender, EventArgs e)
        {
            match_label.Text = template.Match(figure).ToString();
            //Console.WriteLine("Match = " + template.Match(figure));
        }

        private void MainWin_Paint(object sender, PaintEventArgs args)
        {
            Graphics g = args.Graphics;
            int half_height = Height / 2;
            g.DrawLine(Pens.Red, 0, half_height, Width, half_height);
            for (int i = 0; i < figure.Length; i++)
            {
                Stroke stroke = figure[i];
                for (int j = 1; j < stroke.Length; j++)
                {
                    g.DrawLine(Pens.Black, stroke[j - 1], stroke[j]);
                }
                Curve curve = figure.GetCurve(i);
                if (curve != null)
                {
                    int x0 = stroke[0].X;
                    int y0 = Height - half_height / 5;
                    int h = half_height / 2;
                    for (int t = 1; t < curve.Length; t++)
                    {
                        g.DrawLine(Pens.Blue, x0 + t - 1, y0 - (int)(h * curve[t - 1]), x0 + t, y0 - (int)(h * curve[t]));
                    }
                }
            }
        }

        private void MainWin_MouseMove(object sender, MouseEventArgs args)
        {
            Graphics g = Graphics.FromHwnd(Handle);
            if (args.Button == MouseButtons.Left)
            {
                if (stroke == null)
                {
                    stroke = new Stroke();
                    figure.Add(stroke);
                }
                stroke.Add(new Point(args.X, args.Y));
                if (stroke.Length > 1)
                {
                    g.DrawLine(Pens.Black, stroke[stroke.Length - 2], stroke[stroke.Length - 1]);
                }
            }
            else
            {
                if (stroke != null)
                {
                    stroke = null;
                    figure.CurveLastStroke();
                    Invalidate();
                }
            }
        }
    }
}