package gui.tools;

import java.util.LinkedList;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

public class ArrowHead extends Group{
	// properties
	Element e;
	ComplexLine l;
	
	double x1;    
	double y1;
	double x2;      
	double y2;
	LinkedList<Point2D> linePoints;

	double rx;
	double ry;
	double rw;
	double rh;

	Polygon polygon;
	
	// constructors
	public ArrowHead( Element element, ComplexLine line) {
		e = element;
		l = line;
		
		x1 = l.getPoints().get(0).getX();
		y1 = l.getPoints().get(0).getY(); 
		x2 = l.getPoints().get(1).getX();
		y2 = l.getPoints().get(1).getY();
		
		rx = e.getLayoutX();
		ry = e.getLayoutY();
		rw = e.widthProperty().doubleValue(); 
		rh = e.heightProperty().doubleValue();
		
		polygon = new Polygon();
		lineRect( x1, y1, x2, y2, rx, ry, rw, rh);

	}
	// LINE/RECTANGLE
	boolean lineRect(double x1, double y1, double x2, double y2, double rx, double ry, double rw, double rh) {

		// check if the line has hit any of the rectangle's sides
		// uses the Line/Line function below
		boolean left = false;
		boolean right = false;
		boolean top = false;
		boolean bottom = false;
		
		left = lineLine(x1,y1,x2,y2, rx,ry,rx, ry+rh);
		if (!left)
			right =  lineLine(x1,y1,x2,y2, rx+rw,ry, rx+rw,ry+rh);
		if (!right && !left)
			top =    lineLine(x1,y1,x2,y2, rx,ry, rx+rw,ry);
		if (!left && !right && !top)
			bottom = lineLine(x1,y1,x2,y2, rx,ry+rh, rx+rw,ry+rh);


		// if ANY of the above are true, the line
		// has hit the rectangle
		if (left || right || top || bottom) {
			return true;
		}
		return false;
	}


	// LINE/LINE
	boolean lineLine(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {

		// calculate the direction of the lines
		double uA = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));
		double uB = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

		// if uA and uB are between 0-1, lines are colliding
		if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {

			// optionally, draw a circle where the lines meet
			double intersectionX = x1 + (uA * (x2-x1));
			double intersectionY = y1 + (uA * (y2-y1));
			
			Double degree;
			if (y2 > y1)
				degree = Math.toDegrees( Math.atan(-(x2-x1)/(y2-y1) ) );
			else
				degree = Math.toDegrees( Math.atan(-(x2-x1)/(y2-y1) ) - Math.PI );

			polygon.getPoints().addAll(new Double[]{
					(double) intersectionX, (double) intersectionY,
					(double) intersectionX - 10, (double) intersectionY + 25,
					(double) intersectionX + 10, (double) intersectionY + 25 });

			polygon.setFill(Color.WHITE);
			polygon.setStroke(Color.BLACK);
			polygon.getTransforms().add(new Rotate(degree,intersectionX,intersectionY));
			getChildren().add( polygon);
			return true;
		}
		return false;
	}
	
	public void updateArrow() {
		x1 = l.getPoints().get(0).getX();
		y1 = l.getPoints().get(0).getY(); 
		x2 = l.getPoints().get(1).getX();
		y2 = l.getPoints().get(1).getY();
		
		rx = e.getLayoutX();
		ry = e.getLayoutY();
		rw = e.widthProperty().doubleValue(); 
		rh = e.heightProperty().doubleValue();
		
		getChildren().removeAll(getChildren());
		polygon = new Polygon();
		lineRect( x1, y1, x2, y2, rx, ry, rw, rh);
	}
}
