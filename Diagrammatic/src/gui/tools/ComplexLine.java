package gui.tools;

import java.util.LinkedList;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import gui.*;

public class ComplexLine extends Group
{

	LinkedList<Line> lines;
	LinkedList<Point2D> points;
	LinkedList<Circle> circles;
	Element elementFrom;
	Element elementTo;

	/**
	 * @param elementFrom
	 * @param elementTo
	 */
	public ComplexLine( Element elementFrom, Element elementTo)
	{
		super();
		lines = new LinkedList<Line>();
		points = new LinkedList<Point2D>();
		circles = new LinkedList<Circle>();
		getChildren().addAll(lines);
		getChildren().addAll(circles);
		this.elementFrom = elementFrom;
		this.elementTo = elementTo;
	}

	// Creates one line
	/**
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param elementFrom
	 * @param elementTo
	 */
	public ComplexLine( double x1, double y1, double x2, double y2, Element elementFrom, Element elementTo)
	{
		super();
		lines = new LinkedList<Line>();
		circles = new LinkedList<Circle>();
		points = new LinkedList<Point2D>();
		points.add(new Point2D(x1, y1));
		points.add(new Point2D(x2, y2));
		this.elementFrom = elementFrom;
		this.elementTo = elementTo;
		updateLP();
	}

	/**
	 *
	 */
	public void updateL()
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			if (getChildren().get(i) instanceof Line)
			{
				getChildren().remove(i);
			}
		}

		lines.removeAll(lines);
		for(int i = 0; i < points.size() - 1; i++)
		{
			Line l = new Line( points.get(i).getX(), points.get(i).getY(), points.get(i + 1).getX(), points.get(i + 1).getY());
			l.setStrokeWidth(3);
			lines.add(l);
		}
		getChildren().addAll(lines);
	}

	/**
	 *
	 */
	public void updateLP()
	{
		getChildren().removeAll(getChildren());

		lines.removeAll(lines);
		for(int i = 0; i < points.size() - 1; i++)
		{
			Line l = new Line( points.get(i).getX(), points.get(i).getY(), points.get(i + 1).getX(), points.get(i + 1).getY());
			l.setStrokeWidth(3);
			lines.add(l);
		}

		circles.removeAll(circles);
		for (int i = 0; i < points.size(); i++)
		{
			Point2D p = points.get(i);
			Circle c = new Circle( p.getX(), p.getY(), 5);
			circles.add(c);
			final int FINALINDEX = i;

			c.setOnMouseDragged(me -> {
				if (me.getButton() == MouseButton.PRIMARY)
				{
					double x = me.getX();
					double y = me.getY();
					if (x < 5)
						me.consume();
					if (y < 5)
						me.consume();

					if (x > DApp.area.getMaxX())
						me.consume();
					if (y > DApp.area.getMaxY())
						me.consume();

					if( !me.isConsumed())
					{
						c.setCenterX(x);
						c.setCenterY(y);
						points.set(FINALINDEX, new Point2D(me.getX(), me.getY()));
						updateL();
						DApp.updateOverlay();
						DApp.updateZoomPane();
						DApp.updateArrow();
						DApp.scrollPane.setPannable(false);
					}
				}
				else
					me.consume();
			});

			c.setOnMousePressed(event ->
			{
				if (event.getButton() == MouseButton.SECONDARY)
				{
					removePoint( p);
					updateLP();
					DApp.updateArrow();
					event.consume();
				}

			});

			c.setOnMouseReleased(me -> {
				updateLP();
				DApp.scrollPane.setPannable(true);
			});
		}

		getChildren().addAll(lines);
		getChildren().addAll(circles);
	}

	/**
	 * @param point
	 * @param index
	 */
	public void addPoint( Point2D point, int index)
	{
		points.add(index, point);
		updateLP();
	}

	/**
	 * @param point
	 * @param index
	 */
	public void setPoint( Point2D point, int index)
	{
		points.set(index, point);
		updateLP();
	}

	/**
	 * @param point
	 */
	public void setStartPoint( Point2D point)
	{
		setPoint(point, 0);
	}

	/**
	 * @param point
	 */
	public void setEndPoint( Point2D point)
	{
		setPoint(point, points.size() - 1);
	}

	/**
	 * @return
	 */
	public int getLineCount()
	{
		return lines.size();
	}

	/**
	 * @return
	 */
	public int getPointCount()
	{
		return points.size();
	}

	/**
	 * @return
	 */
	public LinkedList<Line> getLines()
	{
		return lines;
	}

	/**
	 * @param l
	 * @return
	 */
	public int getLineIndex( Line l)
	{
		for (int i = 0; i < lines.size(); i++)
		{
			if ( lines.get(i).equals(l))
				return i;
		}
		return -1;
	}

	/**
	 * @return
	 */
	public LinkedList<Point2D> getPoints()
	{
		return points;
	}

	/**
	 * @param point
	 */
	public void removePoint( Point2D point)
	{
		points.remove( point);
	}

	public Element elementFrom()
	{
		return elementFrom;
	}

	public Element elementTo()
	{
		return elementTo;
	}

	/**
	 * @return
	 */
	public String lineToString()
	{
		String line = "CLN " + elementFrom.getObject().getName() + " " + elementTo.getObject().getName() + " ";
		if (points.size() > 2)
		{
			for (int i = 1; i < points.size() - 1; i++)
			{
				Point2D point = points.get(i);
				line = line + point.getX() + "-" + point.getY();
				if (i != points.size() - 1)
					line = line + " ";
			}
		}

		return line;

	}

	public String toString()
    {
        String output;

        output = "CLN ";
        output = output + elementFrom.getObject().getName() + " " + elementTo.getObject().getName();
        if(points.size() >= 3)
        {

            for( int i = 1; i < points.size() - 1; i++)
            {
                output = output + " " + points.get(i).getX() + "-" + points.get(i).getY();
            }

        }
        return output;
    }
}

