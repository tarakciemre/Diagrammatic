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

	/**
	 * @param listener
	 */
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
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param fill
	 * @param listener
	 */
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
		l2 = new Label(  "   Properties   \n");
		l3 = new Label(  "   Methods   \n");
		l2.setUnderline(true);
		l3.setUnderline(true);
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

	/**
	 * @return
	 */
	public DoubleProperty widthProperty() { return widthProperty; }

	/**
	 * @return
	 */
	public DoubleProperty heightProperty() { return heightProperty; }

	/**
	 *
	 */
	@Override
	public String toString() {
		return "[" + getLayoutX() + ", " + getLayoutY() + ", " + widthProperty.get() + ", " + heightProperty.get() + "]";
	}

	/**
	 *
	 */
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

	/**
	 * @return
	 */
	public double limitWidth() {
		updateSize();
		return contentsH.prefWidth(heightProperty.getValue());
	}

	/**
	 * @return
	 */
	public double limitHeight() {
		updateSize();
		return contentsV.prefHeight(widthProperty.getValue());
	}

	/**
	 * @param dc
	 */
	public void setObject( DObject dc) {
		nameOfClass = dc.getName();
		this.dObject = dc;

		updateObject();
	}

	/**
	 * @return
	 */
	public boolean hasObject() {
		return dObject != null;
	}
	/**
	 * @param prop
	 */
	public void addField( DProperty prop) {
		String out = new String("   -"+prop.getName() + ": ");
		out += prop.getType();
		props.add( new Label(out));
		updateObject();
	}

	/**
	 * @param meth
	 */
	public void addMethod( DMethod meth) {
		String out = new String( "   +"+ meth.getName() + "(");

		if ( !meth.getParameters().isEmpty()) {
			for ( DProperty param : meth.getParameters()) {
				out += param.getType() + ", ";

			}
			out = out.substring(0,out.length() - 2);
		}
		out += ")";

		out += ": "+ meth.getReturnType();

		getObject().getMethods().add(meth);
		meths.add( new Label(out));
		updateObject();
	}

	/**
	 *
	 */
	public void iniObject() {
		if (dObject != null) {
			if ( dObject instanceof DGeneralClass) {
				System.out.println(dObject.getProperties().size());
				for ( int i = 0; i < dObject.getProperties().size(); i++) {
					if ( !props.contains( dObject.getProperties().get(i)))
					{
						addField(dObject.getProperties().get(i));
					}
				}
				for ( int i = 0; i < dObject.getMethods().size(); i++) {
					if ( !meths.contains( dObject.getMethods().get(i)))
					{
						addMethod( dObject.getMethods().get(i));
					}
				}
			}
			else if ( dObject instanceof DInterface) {
				for ( int i = 0; i < dObject.getMethods().size(); i++) {
					if ( !meths.contains( dObject.getMethods().get(i)))
					{
						addMethod( dObject.getMethods().get(i));
					}
				}
			}
		}
	}

	/**
	 *
	 */
	public void updateObject() {

		// updating name
		for ( int i = 0; i < contentsH.getChildren().size(); i++)
			if ( contentsH.getChildren().get(i) instanceof Label)
			{
				if( this.getObject() instanceof DInterface)
				{
					contentsH.getChildren().set( i, new Label( "   " + "<<interface>>\n" + "   " + dObject.getName() + "\n\n"));
				}
				else if ( this.getObject() instanceof DAbstractClass)
				{
					contentsH.getChildren().set( i, new Label( "   " + "<<abstract>>\n" + "   " + dObject.getName() + "\n\n"));
				}
				else
				{
					contentsH.getChildren().set( i, new Label( "   " + dObject.getName() + "\n\n"));
				}
			}

		// updating properties
		 // adding absent
		for ( Label label : props) {
			if (!proper.getChildren().contains(label))
				proper.getChildren().add(label);
		}
		 // removing depricated
		boolean contained  = false;

		for ( Label label : props) {
			for ( int i = 0; i < getObject().getProperties().size(); i++) {
				if ( label.getText().equals( getObject().getProperties().get(i).getName()) )
					contained = true;
			}
			if ( !contained)
				proper.getChildren().remove(label);
			contained = false;
		}

		// updating methods
		 // adding absent
		for ( Label label : meths) {
			if (!metho.getChildren().contains(label))
				metho.getChildren().addAll(label);
		}
		 // removing depricated
		contained  = false;

		for ( Label label : meths) {
			for ( int i = 0; i < getObject().getMethods().size(); i++) {
				if ( label.getText().equals( getObject().getMethods().get(i).getName()) )
					contained = true;
			}
			if ( !contained)
				metho.getChildren().remove(label);
			contained = false;
		}

	}

	/**
	 * @return
	 */
	public ArrayList<Label> getMethodLabels() {
		return meths;
	}

	/**
	 * @return
	 */
	public ArrayList<Label> getPropertyLabels() {
		return props;
	}
	/**
	 * @return
	 */
	public ArrayList<ComplexLine> getStartLines() {
		return startLines;
	}

	/**
	 * @return
	 */
	public ArrayList<ComplexLine> getEndLines() {
		return endLines;
	}
	/**
	 * @return
	 */
	public DObject getObject() {
		return this.dObject;
	}

	/**
	 * @return
	 */
	public String elementToString()
	{
		String line = "ELE" +
				" " + getLayoutX() +
				" " + getLayoutY() +
				" " + widthProperty().doubleValue() +
				" " + heightProperty().doubleValue() +
				" " + rectangle.getFill();
		return line;
	}

	public String getColor()
	{
		return rectangle.getFill().toString();
	}

}

