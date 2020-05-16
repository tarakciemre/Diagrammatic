package gui.tools;

import java.util.LinkedList;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


public class ComplexLine extends Group
{

	LinkedList<Line> lines;
	LinkedList<Point2D> points;
	LinkedList<Circle> circles;

	public ComplexLine()
	{
		super();
		lines = new LinkedList<Line>();
		points = new LinkedList<Point2D>();
		circles = new LinkedList<Circle>();
		getChildren().addAll(lines);
		getChildren().addAll(circles);
	}

	// Creates one line
	public ComplexLine( double x1, double y1, double x2, double y2)
	{
		super();
		lines = new LinkedList<Line>();
		circles = new LinkedList<Circle>();
		points = new LinkedList<Point2D>();
		points.add(new Point2D(x1, y1));
		points.add(new Point2D(x2, y2));
		updateLP();
	}

	public void updateLP()
	{
		getChildren().removeAll(getChildren());

		lines.removeAll(lines);
		for(int i = 0; i < points.size() - 1; i++)
		{
			Line l = new Line( points.get(i).getX(), points.get(i).getY(), points.get(i + 1).getX(), points.get(i + 1).getY());
			lines.add(l);
		}

		circles.removeAll(circles);
		for (Point2D p : points)
		{
			Circle c = new Circle( p.getX(), p.getY(), 10);
			circles.add(c);
		}

		getChildren().addAll(lines);
		getChildren().addAll(circles);
	}

	public void addPoint( Point2D point, int index)
	{
		points.add(index, point);
		updateLP();
	}

	public void setPoint( Point2D point, int index)
	{
		points.set(index, point);
		updateLP();
	}

	public void setStartPoint( Point2D point)
	{
		setPoint(point, 0);
	}

	public void setEndPoint( Point2D point)
	{
		setPoint(point, points.size() - 1);
	}

	public int getLineCount()
	{
		return lines.size();
	}

	public int getPointCount()
	{
		return points.size();
	}

	public LinkedList<Line> getLines()
	{
		return lines;
	}

	public int getLineIndex( Line l)
	{
		for (int i = 0; i < lines.size(); i++)
		{
			if ( lines.get(i).equals(l))
				return i;
		}
		return -1;
	}


}

