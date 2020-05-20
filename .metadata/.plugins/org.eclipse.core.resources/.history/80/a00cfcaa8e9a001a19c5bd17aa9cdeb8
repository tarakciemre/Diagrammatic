package gui.tools;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import gui.*;

public class DashedComplexLine extends ComplexLine {

	public DashedComplexLine( double x1, double y1, double x2, double y2, Element elementFrom, Element elementTo)
	{
		super( x1, y1, x2, y2, elementFrom, elementTo);
	}

	@Override
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
			l.getStrokeDashArray().addAll(25d, 15d);
			lines.add(l);
		}
		getChildren().addAll(lines);
	}


	public void updateLP()
	{
		getChildren().removeAll(getChildren());

		lines.removeAll(lines);
		for(int i = 0; i < points.size() - 1; i++)
		{
			Line l = new Line( points.get(i).getX(), points.get(i).getY(), points.get(i + 1).getX(), points.get(i + 1).getY());
			l.setStrokeWidth(3);
			l.getStrokeDashArray().addAll(25d, 15d);
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
}
