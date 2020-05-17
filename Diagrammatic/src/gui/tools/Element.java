package gui.tools;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import misc.Resize;

public class Element extends Group {

    Rectangle rectangle = new Rectangle();
    DoubleProperty widthProperty = new SimpleDoubleProperty();
    DoubleProperty heightProperty = new SimpleDoubleProperty();
    public ArrayList<ComplexLine> startLines = new ArrayList<ComplexLine>();
	public ArrayList<ComplexLine> endLines = new ArrayList<ComplexLine>();

	HBox contentsH;
	HBox contentsH2;
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
        rectangle.setFill(fill);
        l = new Label("This is a test.");
        l2 = new Label("This is another test.");
        l3 = new Label("Just a test.");
        contentsH = new HBox();
        contentsH2 = new HBox();
        contentsV = new VBox();
        contentsH.getChildren().addAll(l,l2);
        contentsH2.getChildren().addAll(l3);
        contentsV.getChildren().addAll( contentsH, contentsH2);
        getChildren().addAll(rectangle,contentsV);
        //setPickOnBounds(true);
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
    @Override public String toString() { return "[" + getLayoutX() + ", " + getLayoutY() + ", " + widthProperty.get() + ", " + heightProperty.get() + "]"; }

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
}

