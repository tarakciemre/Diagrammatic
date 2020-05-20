package gui.tools;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import gui.*;

import logic.object_source.*;
import logic.tools.*;

public class Element extends Group {

	DObject dObject;
	Rectangle rectangle = new Rectangle();
	String nameOfClass = new String("NameOfClass");
	String methods = new String("Methods");
	String properties = new String("Properties");
	DoubleProperty widthProperty = new SimpleDoubleProperty();
	DoubleProperty heightProperty = new SimpleDoubleProperty();
	public ArrayList<ComplexLine> startLines = new ArrayList<ComplexLine>();
	public ArrayList<ComplexLine> endLines = new ArrayList<ComplexLine>();
	public boolean listener;

	ArrayList<Label> props = new ArrayList<Label>();
	ArrayList<Label> meths = new ArrayList<Label>();
	HBox contentsH;
	HBox contentsH2;
	HBox contentsH3;
	VBox proper;
	VBox metho;
	VBox contentsV;
	Label l;
	Label l2;
	Label l3;

	public Element( boolean listener) {
		if (listener) {
			this.listener = listener;
			setOnMousePressed(me -> {
				DApp.select(this);
				DApp.srBnd.fireEvent(me);
				me.consume();
			});
			setOnMouseDragged(me -> DApp.srBnd.fireEvent(me));
			setOnMouseReleased(me -> DApp.srBnd.fireEvent(me));
			boundsInParentProperty().addListener((v, o, n) -> DApp.updateOverlay());
		}
	}
	public Element(double x, double y, double width, double height, Paint fill, boolean listener) {
		widthProperty.addListener((v, o, n) -> { rectangle.setWidth(n.doubleValue()); });
		heightProperty.addListener((v, o, n) -> { rectangle.setHeight(n.doubleValue()); });

		setLayoutX(x);
		setLayoutY(y);

		this.listener = listener;

		widthProperty.set(width);
		heightProperty.set(height);

		rectangle.setFill(fill);
		rectangle.setStrokeWidth(4.0);
		rectangle.setStroke( Color.BLACK);
		rectangle.setArcHeight(20.0d);
		rectangle.setArcWidth(20.0d);

		l = new Label(  "   " + nameOfClass + "\n");
		l2 = new Label(  "   " + properties.toUpperCase()+ "\n");
		l3 = new Label(  "   " + methods.toUpperCase() + "\n");
		/*
        final int fontSize = 9;
        final String fontType = "Arial";
        final Font font = new Font(fontType, fontSize);

        l.setFont(font);
        l2.setFont(font);
        l3.setFont(font);
		 */

		if (dObject != null) {
			for ( int i = 0; i < dObject.getProperties().size(); i++)
				props.add( new Label( dObject.getProperties().get(i).toString()));

			for ( int i = 0; i < dObject.getMethods().size(); i++)
				meths.add( new Label( dObject.getProperties().get(i).toString()));
		}
		contentsH = new HBox();

		contentsH2 = new HBox();
		proper = new VBox();

		contentsH3 = new HBox();
		metho = new VBox();

		contentsV = new VBox();

		contentsH.getChildren().addAll(l);
		contentsH2.getChildren().addAll(l2);
		contentsH3.getChildren().addAll(l3);
		contentsV.getChildren().addAll( contentsH, contentsH2, proper, contentsH3, metho);

		//setPickOnBounds(true);
		getChildren().addAll(rectangle, contentsV);
		if(listener) {
			setOnMousePressed(me -> {
				DApp.select(this);
				DApp.srBnd.fireEvent(me);
				me.consume();
				System.out.println("S: " + toString());
			});
			setOnMouseDragged(me -> DApp.srBnd.fireEvent(me));
			setOnMouseReleased(me -> DApp.srBnd.fireEvent(me));
			boundsInParentProperty().addListener((v, o, n) -> DApp.updateOverlay());
		}

	}

	public DoubleProperty widthProperty() { return widthProperty; }

	public DoubleProperty heightProperty() { return heightProperty; }

	@Override
	public String toString() {
		return "[" + getLayoutX() + ", " + getLayoutY() + ", " + widthProperty.get() + ", " + heightProperty.get() + "]";
	}

	public void updateSize() {
		//for( Node n : contentsH.getChildren())
		//((Label) n).setPrefWidth(((Label) n).getText().length() * 7);
		for( Node n : contentsV.getChildren()) {
			if ( n instanceof HBox) {
				//for(Node m : ((HBox)n).getChildren())
				//((Label) m).setPrefHeight( 15);

			}
		}
	}

	public double limitWidth() {
		updateSize();
		return contentsH.prefWidth(heightProperty.getValue());
	}

	public double limitHeight() {
		updateSize();
		return contentsV.prefHeight(widthProperty.getValue());
	}

	public void setObject( DObject dc) {
		nameOfClass = dc.getName();
		this.dObject = dc;

		updateObject();
	}

	public boolean hasObject() {
		return dObject != null;
	}
	public void addField( DProperty prop) {
		String out = new String("   -"+prop.getName() + ": ");
		out += prop.getType();


		props.add( new Label(out));
		updateObject();
	}

	public void addMethod( DMethod meth) {
		String out = new String( "   +"+meth.getName() + "(");

		if ( meth.getParameters() != null) {
			for ( DProperty param : meth.getParameters()) {
				out += param.getType() + ", ";

			}
			out = out.substring(0,out.length()-2);
		}
		out += ")";

		out += ": "+ meth.getReturnType();

		meths.add( new Label(out));
		updateObject();
	}

	public void iniObject() {
		if (dObject != null) {
			if ( dObject instanceof DClass) {
				for ( int i = 0; i < dObject.getProperties().size(); i++) {
					if ( !props.contains( dObject.getProperties().get(i)))
						addField(dObject.getPrSoperties().get(i));
				}
				for ( int i = 0; i < dObject.getMethods().size(); i++) {
					if (	 !meths.contains( dObject.getMethods().get(i)))
						addMethod( dObject.getMethods().get(i));
				}
			}
			else if ( dObject instanceof DInterface) {
				for ( int i = 0; i < dObject.getMethods().size(); i++) {
					if (	 !meths.contains( dObject.getMethods().get(i)))
						addMethod( dObject.getMethods().get(i));
				}
			}
		}
	}

	public void updateObject() {
		/*
    	 for ( int i = 0; i < elements.size(); i++){
         	for ( int j = 0; j < elements.get(i).getPropertyLabels().size(); i++ ){
         		if ( .contains(elements.get(i).getPropertyLabels().get(j)))
         	}

         	for ( int j = 0; j < elements.get(i).getMethodLabels().size(); i++ ) {

         	}

         }*/


		/*for (int i = 0; i < contentsV.getChildren().size(); i++) {
    		if ( getChildren().get(i) instanceof HBox)
    			getChildren().remove(i);
    	}*/


		/*if (dObject != null) {


    		for ( int i = 0; i < dObject.getProperties().size(); i++)
        		props.add( new Label( dObject.getProperties().get(i).toString()));

        	for ( int i = 0; i < dObject.getMethods().size(); i++)
        		meths.add( new Label( dObject.getProperties().get(i).toString()));


    	}*/
		/*for ( int i = 0; i < contentsV.getChildren().size(); i++)
    		if ( contentsV.getChildren().get(i) instanceof VBox)
    			contentsH.getChildren().add( new Label( dObject.getName() + "\n--------------\n"));
		 */

		// updating name
		for ( int i = 0; i < contentsH.getChildren().size(); i++)
			if ( contentsH.getChildren().get(i) instanceof Label)
				contentsH.getChildren().set( i, new Label( "   " + dObject.getName() + "\n\n"));

		// updating properties
		for ( Label label : props) {
			if (!proper.getChildren().contains(label))
				proper.getChildren().add(label);
		}


		// updating methods
		for ( Label label : meths) {
			if (!metho.getChildren().contains(label))
				metho.getChildren().addAll(label);
		}


	}

	public ArrayList<Label> getMethodLabels() {
		return meths;
	}

	public ArrayList<Label> getPropertyLabels() {
		return props;
	}
	public ArrayList<ComplexLine> getStartLines() {
		return startLines;
	}

	public ArrayList<ComplexLine> getEndLines() {
		return endLines;
	}
	public DObject getObject() {
		return this.dObject;
	}

	public String elementToString()
	{
		String line = "CLA: " + widthProperty().doubleValue() + " " + heightProperty().doubleValue() + " " + getLayoutX() + " " +getLayoutY() + " " + rectangle.getFill();
		return line;
	}

}

