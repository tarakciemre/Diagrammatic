package misc;

import java.util.ArrayList;

import gui.tools.ComplexLine;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.canvas.*;
import javafx.scene.transform.*;
import javafx.scene.image.*;
import javafx.beans.property.*;
import javafx.geometry.*;

public class Resize extends Application {

    double a = 6, a2 = a / 2;
    double gridSize = -1;
    double lastX, lastY;
    double lX, lY, sX, sY, sWidth, sHeight;
    Slider slider1, slider2;
    CheckBox checkBox;
    ScrollPane scrollPane;
    Group group, overlay = null;
    Pane zoomPane;
    Rectangle srBnd, srNW, srN, srNE, srE, srSE, srS, srSW, srW;
    Element selectedElement;
    Rectangle2D area; // sets the borders for moving objects
    Paint bg1 = Paint.valueOf("linear-gradient(from 0.0% 0.0% to 0.0% 100.0%, 0x90c1eaff 0.0%, 0x5084b0ff 100.0%)");
    BackgroundFill backgroundFill1 = new BackgroundFill(bg1, null, null);
    Canvas canvas = new Canvas();
    SnapshotParameters sp = new SnapshotParameters();
    Circle closest;

    public static void main( String[] args)
    {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
    	area = new Rectangle2D(0, 0, 2000, 2000); // sets the borders for moving objects
        BorderPane layout = new BorderPane();
        stage.setScene(new Scene(layout, 500, 300));
        Element r1 = new Element( 0, 0, 300, 300, Color.GOLD, true);
        Element r2 = new Element( 500, 500, 200, 200, Color.SEASHELL, true);
        Element r3 = new Element( 700, 700, 200, 200, Color.LIME, true);
        Element r4 = new Element( 900, 900, 200, 200, Color.GREEN, true);
        Element r5 = new Element( 1100, 1100, 200, 200, Color.RED, true);

        group = new Group();

        closest = new Circle(10);

        drawCenteredLine ( r1, r2);
        drawCenteredLine ( r1, r3);
        drawCenteredLine ( r1, r4);
        drawCenteredLine ( r2, r5);
        group.getChildren().addAll(r1, r2, r3, r4, r5, closest);


        zoomPane = new Pane(group);

        zoomPane.setOnMousePressed(me -> {
        	Point2D mouseP = new Point2D( me.getX(), me.getY());
        	showClosest( mouseP);

        	Point2D cP = new Point2D( closest.getCenterX(), closest.getCenterY());

        	Line l = getClosest(mouseP);
        	ComplexLine cl = getComplex(l);

        	if (l != null)
        	{
        		cl.addPoint(cP, cl.getLineIndex(l) + 1);

        	}
        	select(null);
        });


        zoomPane.setOnMouseMoved(me -> {
            showClosest(new Point2D(me.getX(), me.getY()));
            me.consume();
        });

        Scale scale = new Scale();
        group.getTransforms().add(scale);
        slider1 = new Slider(.1, 5, 1);
        slider1.setMinWidth(150);
        slider1.setMaxWidth(150);
        slider1.setPadding(new Insets(6));
        slider1.setTooltip(new Tooltip("Zoom"));
        slider1.valueProperty().addListener((v, o, n) -> {
            scale.setX(n.doubleValue());
            scale.setY(n.doubleValue());
            updateGrid();
            updateZoomPane();
            updateOverlay();
        });
        slider2 = new Slider(5, 35, 15);
        slider2.setMinWidth(150);
        slider2.setMaxWidth(150);
        slider2.setPadding(new Insets(6));
        slider2.setTooltip(new Tooltip("Grid size"));
        slider2.valueProperty().addListener((v, o, n) -> {
            updateGrid();
        });
        checkBox = new CheckBox();
        checkBox.setPadding(new Insets(6));
        checkBox.setTooltip(new Tooltip("Show grid"));
        checkBox.selectedProperty().addListener((v, o, n) -> {
            slider2.setDisable(!n.booleanValue());
            updateGrid();
        });
        checkBox.setSelected(true);
        scrollPane = new ScrollPane(new Group(zoomPane));
        scrollPane.viewportBoundsProperty().addListener((v, o, n) -> {
            updateZoomPane();
            Platform.runLater(() -> zoomPane.requestLayout());
        });
        layout.setCenter(scrollPane);
        layout.setBottom(new FlowPane(slider1, checkBox, slider2));
        //stage.setOnCloseRequest(e -> System.out.println(group.getChildren().toString()));
        stage.show();
        scrollPane.setPannable(true);
    }

    void updateGrid() {
        double size = slider1.getValue() * slider2.getValue();
        if (!checkBox.isSelected() || size < 4) size = 0;
        if (gridSize != size) {
            if (size <= 0) {
                zoomPane.setBackground(new Background(backgroundFill1));
            } else {
                Paint bg2 = patternTransparent(size);
                BackgroundFill backgroundFill2 = new BackgroundFill(bg2, null, null);
                zoomPane.setBackground(new Background(backgroundFill1, backgroundFill2));
            }
            gridSize = size;
        }
    }

    void updateZoomPane() {
        zoomPane.setPrefWidth(Math.max(scrollPane.getViewportBounds().getWidth(), group.getBoundsInParent().getMaxX()));
        zoomPane.setPrefHeight(Math.max(scrollPane.getViewportBounds().getHeight(), group.getBoundsInParent().getMaxY()));
    }

    ImagePattern patternTransparent(double size) {
        canvas.setHeight(size);
        canvas.setWidth(size);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, size, size);
        gc.setFill(Color.BLACK);
        //gc.setLineWidth(2);
        gc.strokeLine(0, 0, 0, size);
        gc.strokeLine(1, 0, size, 0);
        sp.setFill(Color.TRANSPARENT);
        WritableImage image = canvas.snapshot(sp, null);
        return new ImagePattern(image, 0, 0, size, size, false);
    }

    Element createElement(double x, double y, double width, double height, Paint fill, boolean listener) {
        return new Element(x, y, width, height, fill, listener);
    }

    class Element extends Group {

        Rectangle rectangle = new Rectangle();
        DoubleProperty widthProperty = new SimpleDoubleProperty();
        DoubleProperty heightProperty = new SimpleDoubleProperty();
        ArrayList<ComplexLine> startLines = new ArrayList<ComplexLine>();
    	ArrayList<ComplexLine> endLines = new ArrayList<ComplexLine>();

    	HBox contentsH;
    	HBox contentsH2;
    	VBox contentsV;
    	Label l;
    	Label l2;
    	Label l3;

        Element( boolean listener) {
        	if (listener) {
                setOnMousePressed(me -> {
                    select(this);
                    srBnd.fireEvent(me);
                    me.consume();
                });
                setOnMouseDragged(me -> srBnd.fireEvent(me));
                setOnMouseReleased(me -> srBnd.fireEvent(me));
                boundsInParentProperty().addListener((v, o, n) -> updateOverlay());
        	}
        }
        Element(double x, double y, double width, double height, Paint fill, boolean listener) {
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
                    select(this);
                    srBnd.fireEvent(me);
                    me.consume();
                    System.out.println("S: " + toString());
                });
                setOnMouseDragged(me -> srBnd.fireEvent(me));
                setOnMouseReleased(me -> srBnd.fireEvent(me));
                boundsInParentProperty().addListener((v, o, n) -> updateOverlay());
            }
        }
        DoubleProperty widthProperty() { return widthProperty; }
        DoubleProperty heightProperty() { return heightProperty; }
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

    void select(Element element) {
        if (overlay == null && element != null) iniOverlay();
        if (element != selectedElement) {
            overlay.setVisible(element != null);
            if (element != null) element.toFront();
            selectedElement = element;
            updateOverlay();
        }
    }

    void iniOverlay() {
        overlay = new Group();
        //overlay.setVisible(false);
        srBnd = new Rectangle();
        srBnd.setStroke(Color.BLACK);
        srBnd.setStrokeType(StrokeType.INSIDE);
        srBnd.setStrokeWidth(1);
        srBnd.getStrokeDashArray().addAll(2d, 4d);
        srBnd.setFill(Color.TRANSPARENT);
        handleMouse(srBnd);
        srNW = srCreate(Cursor.NW_RESIZE);
        srN = srCreate(Cursor.N_RESIZE);
        srNE = srCreate(Cursor.NE_RESIZE);
        srE = srCreate(Cursor.E_RESIZE);
        srSE = srCreate(Cursor.SE_RESIZE);
        srS = srCreate(Cursor.S_RESIZE);
        srSW = srCreate(Cursor.SW_RESIZE);
        srW = srCreate(Cursor.W_RESIZE);
        overlay.getChildren().addAll(srBnd, srNW, srN, srNE, srE, srSE, srS, srSW, srW);
        zoomPane.getChildren().add(overlay);
    }

    void updateOverlay() {
        if (selectedElement != null) {
            double zoom = slider1.getValue();
            srBnd.setX(selectedElement.getLayoutX() * zoom);
            srBnd.setY(selectedElement.getLayoutY() * zoom);
            srBnd.setWidth(selectedElement.widthProperty().get() * zoom);
            srBnd.setHeight(selectedElement.heightProperty().get() * zoom);
            srNW.setX(selectedElement.getLayoutX() * zoom);
            srNW.setY(selectedElement.getLayoutY() * zoom);
            srN.setX((selectedElement.getLayoutX() + selectedElement.widthProperty().get() / 2) * zoom - a2);
            srN.setY(selectedElement.getLayoutY() * zoom);
            srNE.setX((selectedElement.getLayoutX() + selectedElement.widthProperty().get()) * zoom - a);
            srNE.setY(selectedElement.getLayoutY() * zoom);
            srE.setX((selectedElement.getLayoutX() + selectedElement.widthProperty().get()) * zoom - a);
            srE.setY((selectedElement.getLayoutY() + selectedElement.heightProperty().get() / 2) * zoom - a2);
            srSE.setX((selectedElement.getLayoutX() + selectedElement.widthProperty().get()) * zoom - a);
            srSE.setY((selectedElement.getLayoutY() + selectedElement.heightProperty().get()) * zoom - a);
            srS.setX((selectedElement.getLayoutX() + selectedElement.widthProperty().get() / 2) * zoom - a2);
            srS.setY((selectedElement.getLayoutY() + selectedElement.heightProperty().get()) * zoom - a);
            srSW.setX(selectedElement.getLayoutX() * zoom);
            srSW.setY((selectedElement.getLayoutY() + selectedElement.heightProperty().get()) * zoom - a);
            srW.setX(selectedElement.getLayoutX() * zoom);
            srW.setY((selectedElement.getLayoutY() + selectedElement.heightProperty().get() / 2) * zoom - a2);
        }
    }

    Rectangle srCreate(Cursor cursor) {
    	//Resize rectangle
        Rectangle rectangle = new Rectangle(a, a, Color.BLACK);
        rectangle.setCursor(cursor);
        handleMouse(rectangle);
        return rectangle;
    }

    void handleMouse(Node node) {
        node.setOnMousePressed(me -> {
            lX = me.getX();
            lY = me.getY();
            sX = selectedElement.getLayoutX();
            sY = selectedElement.getLayoutY();
            sWidth = selectedElement.widthProperty().get();
            sHeight = selectedElement.heightProperty().get();
            me.consume();
        });
        node.setOnMouseDragged(me -> {
            double zoom = slider1.getValue();
            double dx = (me.getX() - lX) / zoom;
            double dy = (me.getY() - lY) / zoom;
            Object source = me.getSource();
            if (source == srBnd) relocate(sX + dx, sY + dy);
            else if (source == srNW) { setHSize(sX + dx, true); setVSize(sY + dy, true); }
            else if (source == srN) setVSize(sY + dy, true);
            else if (source == srNE) { setHSize(sX + sWidth + dx, false); setVSize(sY + dy, true); }
            else if (source == srE) setHSize(sX + sWidth + dx, false);
            else if (source == srSE) { setHSize(sX + sWidth + dx, false); setVSize(sY + sHeight + dy, false); }
            else if (source == srS) setVSize(sY + sHeight + dy, false);
            else if (source == srSW) { setHSize(sX + dx, true); setVSize(sY + sHeight + dy, false); }
            else if (source == srW) setHSize(sX + dx, true);
            updateZoomPane();
            updateLines();
            me.consume();
        });
        node.setOnMouseReleased(me -> { //snap to grid
            if (checkBox.isSelected() && slider2.getValue() > 0) {
                Object source = me.getSource();
                if (source == srBnd) relocate(snap(selectedElement.getLayoutX()), snap(selectedElement.getLayoutY()));
                else {
                    if (source == srNW || source == srN || source == srNE) setVSize(snap(selectedElement.getLayoutY()), true);
                    else if (source == srSW || source == srS || source == srSE) setVSize(snap(selectedElement.getLayoutY() + selectedElement.heightProperty().get()), false);
                    if (source == srNW || source == srW || source == srSW) setHSize(snap(selectedElement.getLayoutX()), true);
                    else if (source == srNE || source == srE || source == srSE) setHSize(snap(selectedElement.getLayoutX() + selectedElement.widthProperty().get()), false);
                }
                updateZoomPane();
                updateLines();
            }
            me.consume();
        });
    }

    double snap(double value) {
        double gridSize = slider2.getValue();
        return Math.round(value / gridSize) * gridSize;
    }

    void setHSize(double h, boolean b) {
        double x = selectedElement.getLayoutX(), w = selectedElement.widthProperty().get(), width;
        double as = (a * 3) / slider1.getValue();
        if (h < area.getMinX()) h = area.getMinX();
        if (h > area.getMaxX()) h = area.getMaxX();

        if (b) {
            width = w + x - h;
            if (width < as + selectedElement.limitWidth()) { width = as + selectedElement.limitWidth(); h = x + w - width; }
            selectedElement.setLayoutX(h);
        } else {
            width = h - x;
            if (width < as + selectedElement.limitWidth()) width = as + selectedElement.limitWidth();
        }
        selectedElement.widthProperty().set(width);
    }

    void setVSize(double v, boolean b) {
        double y = selectedElement.getLayoutY(), h = selectedElement.heightProperty().get(), height;
        double as = (a * 3) / slider1.getValue();
        if (v < area.getMinY()) v = area.getMinY();
        if (v > area.getMaxY()) v = area.getMaxY();
        if (b) {
            height = h + y - v;
            if (height < as + selectedElement.limitHeight()) { height = as + selectedElement.limitHeight(); v = y + h - height; }
            selectedElement.setLayoutY(v);
        } else {
            height = v - y;
            if (height < as + selectedElement.limitHeight()) height = as + selectedElement.limitHeight();
        }
        selectedElement.heightProperty().set(height);
    }

    void relocate(double x, double y) {
        double maxX = area.getMaxX() - selectedElement.widthProperty().get();
        double maxY = area.getMaxY() - selectedElement.heightProperty().get();
        if (x < area.getMinX()) x = area.getMinX();
        if (y < area.getMinY()) y = area.getMinY();
        if (x > maxX) x = maxX;
        if (y > maxY) y = maxY;
        selectedElement.setLayoutX(x);
        selectedElement.setLayoutY(y);
    }

    void drawCenteredLine( Element first, Element second)
    {
    	double fcX = first.getLayoutX() + first.widthProperty().get() / 2;
    	double fcY = first.getLayoutY() + first.heightProperty().get() / 2;
    	double scX = second.getLayoutX() + second.widthProperty().get() / 2;
    	double scY = second.getLayoutY() + second.heightProperty().get() / 2;
        ComplexLine line = new ComplexLine(fcX, fcY, scX, scY);
        group.getChildren().add(line);
        first.startLines.add(line);
        second.endLines.add(line);
    }

    void updateLines()
    {
    	for (Node n : group.getChildren())
    	{
    		if ( n instanceof Element)
    		{
    			Element e = (Element) n;
    	    	double cX = e.getLayoutX() + e.widthProperty().get() / 2;
    	    	double cY = e.getLayoutY() + e.heightProperty().get() / 2;
    			for (ComplexLine sl : e.startLines)
    			{
    				sl.setStartPoint(new Point2D( cX, cY));
    			}

    			for (ComplexLine el : e.endLines)
    			{
    				el.setEndPoint(new Point2D( cX, cY));
    			}

    		}
    	}
    }

    void displayPoint(Point2D point)
    {
    	Circle c = new Circle (point.getX(), point.getY(), 10);
    	group.getChildren().add(c);
    }

    Point2D createPoint( double x, double y)
    {
    	return new Point2D( x, y);
    }


    void showClosest( Point2D mouseLoc)
    {
    	Line l = getClosest(mouseLoc);
    	if (l != null)
    	{
    		Point2D first = new Point2D( l.getStartX(), l.getStartY());
        	Point2D second = new Point2D( l.getEndX(), l.getEndY());
        	double lambda = (mouseLoc.subtract(first)).dotProduct(second.subtract(first))
        			/ ((second.subtract(first)).dotProduct(second.subtract(first)));
        	Point2D closest2 = new Point2D (second.subtract(first).getX() * lambda, second.subtract(first).getY() * lambda).add(first);

        	boolean inShape = false;
        	for (Node n : group.getChildren())
        	{
        		if (n instanceof Element)
        		{
        			Element e = (Element) n;
        			if ( e.contains(closest2))
        			{
        				inShape = true;
        				System.out.println(closest2);
        			}
        		}
        	}

        	if (inShape)
        	{
        		closest.setRadius(0);
        	}
        	else
        	{
        		closest.setCenterX(closest2.getX());
            	closest.setCenterY(closest2.getY());
            	closest.setRadius(10);
        	}

    	}
    	else
    		closest.setRadius(0);

    }

    Point2D getClosestPoint(Line l, Point2D mouseLoc)
    {
    	Point2D first = new Point2D( l.getStartX(), l.getStartY());
    	Point2D second = new Point2D( l.getEndX(), l.getEndY());
    	double lambda = (mouseLoc.subtract(first)).dotProduct(second.subtract(first))
    			/ ((second.subtract(first)).dotProduct(second.subtract(first)));
    	Point2D closest2 = new Point2D (second.subtract(first).getX() * lambda, second.subtract(first).getY() * lambda).add(first);
    	return closest2;
    }

    ComplexLine getComplex( Line l)
    {
    	for (Node n : group.getChildren())
    	{
    		if (n instanceof ComplexLine)
    		{
    			ComplexLine cl = (ComplexLine) n;
    			if (cl.getLineIndex(l) != -1)
    				return cl;
    		}
    	}
    	return null;
    }

    Line getClosest( Point2D mouseLoc)
    {
    	double range = 99999999999.0;
    	Line closestLine = null;

    	for (Node n : group.getChildren())
    	{
    		if (n instanceof ComplexLine)
    		{
    			ComplexLine cl = (ComplexLine) n;
    			for (Line l : cl.getLines())
    			{
    				Point2D closest2 = getClosestPoint(l, mouseLoc);

    				if( closest2.distance(mouseLoc) < range && closest2.distance(mouseLoc) < 50 && l.contains(closest2))
    				{
    					range = closest2.distance(mouseLoc);
    					closestLine = l;
    				}
    			}
    		}
    	}
    	return closestLine;
    }


}