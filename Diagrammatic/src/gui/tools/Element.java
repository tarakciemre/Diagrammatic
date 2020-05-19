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
import misc.Resize;
import java.util.ArrayList;
import logic.*;
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

	ArrayList<Label> props = new ArrayList<Label>();
	ArrayList<Label> meths = new ArrayList<Label>();
	HBox contentsH;
	HBox contentsH2;
	HBox contentsH3;
	VBox contentsV;
	Label l;
	Label l2;
	Label l3;

    public Element( boolean listener) {
    	if (listener) {
            setOnMousePressed(me -> {
            	Resize.select(this);
                Resize.srBnd.fireEvent(me);
                me.consume();
            });
            setOnMouseDragged(me -> Resize.srBnd.fireEvent(me));
            setOnMouseReleased(me -> Resize.srBnd.fireEvent(me));
            boundsInParentProperty().addListener((v, o, n) -> Resize.updateOverlay());
    	}
    }
    public Element(double x, double y, double width, double height, Paint fill, boolean listener) {
        widthProperty.addListener((v, o, n) -> { rectangle.setWidth(n.doubleValue()); });
        heightProperty.addListener((v, o, n) -> { rectangle.setHeight(n.doubleValue()); });

        setLayoutX(x);
        setLayoutY(y);

        widthProperty.set(width);
        heightProperty.set(height);
        rectangle.setFill(Color.BLACK);
        rectangle.setFill(Color.BISQUE);
        rectangle.setFill(fill);
        rectangle.setArcHeight(20.0d);
        rectangle.setArcWidth(20.0d);
        l = new Label( nameOfClass+ "\n--------------\n");
        l2 = new Label( properties.toUpperCase()+ "\n--------------\n");
        l3 = new Label( methods.toUpperCase() + "\n--------------\n");
        if (dObject != null) {
        	for ( int i = 0; i < dObject.getProperties().size(); i++)
        		props.add( new Label( dObject.getProperties().get(i).toString()));

        	for ( int i = 0; i < dObject.getMethods().size(); i++)
        		meths.add( new Label( dObject.getProperties().get(i).toString()));
        }
        contentsH = new HBox();
        contentsH2 = new HBox();
        contentsH3 = new HBox();
        contentsV = new VBox();
        contentsH.getChildren().addAll(l);
        contentsH2.getChildren().addAll(l2);
        contentsH3.getChildren().addAll(l3);
        contentsV.getChildren().addAll( contentsH, contentsH2, contentsH3);

        //setPickOnBounds(true);
        getChildren().addAll(rectangle, contentsV);
        if(listener) {
        	setOnMousePressed(me -> {
        		Resize.select(this);
        		Resize.srBnd.fireEvent(me);
                me.consume();
                System.out.println("S: " + toString());
            });
            setOnMouseDragged(me -> Resize.srBnd.fireEvent(me));
            setOnMouseReleased(me -> Resize.srBnd.fireEvent(me));
            boundsInParentProperty().addListener((v, o, n) -> Resize.updateOverlay());
        }

    }

    public DoubleProperty widthProperty() { return widthProperty; }

    public DoubleProperty heightProperty() { return heightProperty; }

    @Override
    public String toString() {
    	return "[" + getLayoutX() + ", " + getLayoutY() + ", " + widthProperty.get() + ", " + heightProperty.get() + "]";
    }

    public void updateSize() {
    	for( Node n : contentsH.getChildren())
    		((Label) n).setPrefWidth(((Label) n).getText().length() * 7);
    	for( Node n : contentsV.getChildren()) {
    		for(Node m : ((HBox)n).getChildren())
    			((Label) m).setPrefHeight( 15);
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

    public void addField( DProperty prop) {

    }

    public void addMethod( DMethod meth) {


    }

    public void updateObject() {

    	Label lp = null;
    	for (int i = 0; i < getChildren().size(); i++) {
    		if ( getChildren().get(i) instanceof HBox)
    			getChildren().remove(i);
    	}

    	if (dObject != null) {

    		lp = new Label( dObject.getName()+ "\n--------------\n");
            Label l2p = new Label( properties.toUpperCase()+ "\n--------------\n");
            Label l3p = new Label( methods.toUpperCase() + "\n--------------\n");

    		/*
    		for ( int i = 0; i < dObject.getProperties().size(); i++)
        		props.add( new Label( dObject.getProperties().get(i).toString()));

        	for ( int i = 0; i < dObject.getMethods().size(); i++)
        		meths.add( new Label( dObject.getProperties().get(i).toString()));
        	 */
    	}

    	contentsH.getChildren().addAll(lp);
    	//contentsH2.getChildren().addAll(l2p);
    	for ( Label l : props)
    		contentsH2.getChildren().addAll(l);

    	//contentsH3.getChildren().addAll(l3);
        for ( Label l : meths)
        	contentsH3.getChildren().addAll(l);
    }

    public DObject getObject() {
    	return dObject;
    }

}

