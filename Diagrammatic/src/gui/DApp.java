package gui;

//import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import logic.object_source.*;

import gui.tools.ArrowHead;
import gui.tools.ComplexLine;
import gui.tools.DashedComplexLine;
import gui.tools.Element;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import logic.object_source.DClass;
import logic.object_source.DObject;
import logic.tools.DMethod;
import logic.tools.DProject;
import logic.tools.DProperty;
import gui.tools.pannablecanvas.*;

public class DApp extends Application {

	// App Properties

	// Helpers
    static double a = 6, a2 = a / 2, lX, lY, sX, sY, sWidth, sHeight;
    double gridSize = -1, lastX, lastY;
    static int offset = 5000;

    // Members
    public static final double INDICATOR_RADIUS = 7;
    public static Slider slider1, slider2;
    public static CheckBox checkBox;
    public static ScrollPane scrollPane;
    static Group group;
	static Group overlay = null;
    static Pane zoomPane;
    public static Rectangle srBnd, srNW, srN, srNE, srE, srSE, srS, srSW, srW;
    static Circle closest;
    static Element selectedElement;
    public static ArrayList<Element> elements;
    public static Rectangle2D area; // sets the borders for moving objects
    public static DProject project;

    // Colors
    public static Paint lineColor;
    public static Paint backgroundColor;
    Paint bg1 = Paint.valueOf("linear-gradient(from 0.0% 0.0% to 0.0% 100.0%, 0x90c1eaff 0.0%, 0x5084b0ff 100.0%)");
    BackgroundFill backgroundFill1 = new BackgroundFill(bg1, null, null);
    // canvas & sp
    Canvas canvas = new Canvas();
    SnapshotParameters sp = new SnapshotParameters();

    public static void main( String[] args)
    {
        launch(args);
    }

    @Override
    public void start( Stage stage) {
    	area = new Rectangle2D(0, 0, 10000, 10000); // sets the borders for moving objects
        BorderPane layout = new BorderPane();

        setLineColor(Color.DIMGREY);
        setBackgroundColor(Color.DARKGRAY);

        stage.setTitle("Diagrammatic 0.1.3");
        stage.setScene(new Scene(layout, 500, 300));
        //set the location of the window
        stage.setX(50);
        stage.setY(50);
        //set the size of the window
        stage.setWidth(1820);
        stage.setHeight(900);

        Image icon = new Image("file:icon.png");
        Image albaniaIcon = new Image("file:albaniaicon.jpg");
        //stage.getIcons().add(albaniaIcon);
        stage.getIcons().add(icon);

        // init project
        project = new DProject();
        project.setName("GloriousAlbania");


        // init prj objects
        DObject albanian = new DClass("Albanian");
        DObject albanianable = new DClass("Albanianable");
        DObject kosovan = new DClass("Kosovan");
        ((DClass) kosovan).setSuperClass((DClass) albanian);

        // adding prj objects to prj
        project.addObject(albanian);
        project.addObject(kosovan);
        project.addObject(albanianable);


        // init elements
        iniElements(project);

        Element cornerNW = new Element( -100, -100, 1, 1, Color.GOLD, false);
        Element cornerNE = new Element( 10000, -100, 1, 1, Color.GOLD, false);
        Element cornerSW = new Element( -100, 10000, 1, 1, Color.GOLD, false);
        Element cornerSE = new Element( 10000, 10000, 1, 1, Color.GOLD, false);

        group = new Group();

        closest = new Circle(3);
        closest.setFill(Color.RED);

        zoomPane = new Pane(group);

        zoomPane.setOnMousePressed(me -> {
        	boolean onCircle = false;
        	Point2D mouseLoc = new Point2D(me.getX(), me.getY());
        	for (Node n : group.getChildren())
        	{
        		if (n instanceof ComplexLine)
        		{
        			ComplexLine cl = (ComplexLine) n;
        			for (Point2D clp : cl.getPoints())
        			{
        				if (mouseLoc.distance(clp) <= 5 )
        				{
        					onCircle = true;
        				}
        			}
        		}
        	}
        	Point2D mouseP = new Point2D( me.getX(), me.getY());

        	Point2D cP = new Point2D( closest.getCenterX(), closest.getCenterY());

        	Line l = getClosest(mouseP);
        	ComplexLine cl = getComplex(l);

        	if (l != null && !onCircle)
        	{
        	    if (me.getButton() == MouseButton.PRIMARY)
        		    cl.addPoint(cP, cl.getLineIndex(l) + 1);
        	}

        	if (cl != null && onCircle)
        	{
                if (me.getButton() == MouseButton.SECONDARY)
                {
                    cl.removePoint( cP);
                }
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

        // Layout
        VBox leftLayout = new VBox(10);
        VBox rightLayout = new VBox(10);
        BorderPane parentLayout = new BorderPane();
        
        // File Menu
        Menu fileMenu = new Menu("File");
        MenuItem openProject = new MenuItem( "Open");
        MenuItem saveProject = new MenuItem( "Save");
        MenuItem saveProjectAs = new MenuItem( "Save As...");
        
        fileMenu.getItems().addAll(openProject, saveProject, saveProjectAs);

        // Add Menu
        Menu addMenu = new Menu("Add");
        MenuItem newObject = new MenuItem("New Object");
        newObject.setOnAction(e -> {
            displayObjectOptions();
        });
        MenuItem newField = new MenuItem("New Property");
        newField.setOnAction( e -> {
        	if ( selectedElement != null)
        		displayFieldOptions( selectedElement);
        	else
        		displayErrorMessage("No class selected");
        });
        MenuItem newMethod = new MenuItem("New Method");
        newMethod.setOnAction( e -> {
        	if ( selectedElement != null)
        		displayMethodOptions( selectedElement);
        	else
        		displayErrorMessage("No Class Selected");
        });
        MenuItem newConstr = new MenuItem("New Constructor");
        newConstr.setOnAction( e -> {
        	if ( selectedElement != null)
        		displayConstructorMakerWindow( selectedElement);
        	else
        		displayErrorMessage("No Class Selected");
        });

        addMenu.getItems().addAll( newObject, newField, newMethod, newConstr);

        //Help menu
        Menu helpMenu = new Menu("Help");

        CheckMenuItem autoSave = new CheckMenuItem("Enable Autosave");
        autoSave.setSelected(true);
        helpMenu.getItems().add(autoSave);

        //Difficulty RadioMenuItems
        Menu extractMenu = new Menu("Extract...");
        MenuItem extractAll = new MenuItem( "Extract All");
        MenuItem extractMethods = new MenuItem( "Extract Methods");
        MenuItem extractFields = new MenuItem( "Extract Fields");


        extractAll.setOnAction(e -> extractAll(e, project) );
        extractMethods.setOnAction(e -> extractMethods(e));
        extractFields.setOnAction(e -> extractFields(e));


        extractMenu.getItems().addAll(extractAll, extractMethods, extractFields);

        // most important menu
        Menu ytpMenu = new Menu("YTP modes");
        
        // Preferences
        Menu preferencesMenu = new Menu("Preferences");
        MenuItem themes = new MenuItem( "Themes");
        
        preferencesMenu.getItems().addAll(themes);

        // edit menu
        Menu editMenu = new Menu("Edit");
		MenuItem editRelations = new MenuItem("Edit Relations");
		MenuItem editClass = new MenuItem("Edit Class");
		MenuItem removeClass = new MenuItem("Remove Class");

		editRelations.setOnAction(e -> {
			//displayObjectOptions();
		});

		editClass.setOnAction(e -> {
			//displayObjectOptions();
		});

		removeClass.setOnAction(e -> {
			if (selectedElement != null)
				displayRemoveOptions( selectedElement);
			else
				displayErrorMessage( "No Selected Class");
		});
		editMenu.getItems().addAll( editRelations, editClass, removeClass);

        //Main menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, addMenu, helpMenu, extractMenu, editMenu, preferencesMenu, ytpMenu);

        parentLayout.setCenter( layout);
        parentLayout.setLeft( leftLayout );
        parentLayout.setRight( rightLayout );
        parentLayout.setTop(menuBar);

        Scene scene = new Scene( parentLayout, 300, 250);

        stage.setScene( scene);


        stage.show();
        scrollPane.setPannable(true);

        drawHierarchy( project);

        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setHvalue(offset + 300);
        scrollPane.setVvalue(offset + 300);
        group.getChildren().addAll( cornerNE, cornerNW, cornerSE, cornerSW, closest);
        addElements( group);
        scrollPane.setPannable(true);
    }
    void iniElements( DProject prj) {
        elements = new ArrayList<Element>();
        for ( int i = 0; i < prj.getObjects().size(); i++) {
            elements.add( new Element( offset + 0 - Math.random()*1000, offset + 0 - Math.random()*1000, 300, 300, Color.color(Math.random(), Math.random(), Math.random()), true));
        }


        for ( int i = 0; i < prj.getObjects().size(); i++) {
            elements.get(i).setObject( prj.getObjects().get(i));
            elements.get(i).updateObject();
        }


    }

    static void drawHierarchy( DProject prj) {
    	ArrayList<DObject> bunch = new ArrayList<DObject>();
        Element from = null, to = null;

        for ( DObject o : prj.getObjects()){
            if ( o instanceof DGeneralClass && ((DGeneralClass) o).getSuperClass() != null){
                if ( o instanceof DClass && ((DGeneralClass) o).getSuperClass() instanceof DClass) {
                	for ( int j = 0; j < elements.size(); j++) {
                		if ( elements.get(j).getObject().getName().equals( ((DGeneralClass) o).getSuperClass().getName()) )
                			from = elements.get(j);
                		else if ( elements.get(j).getObject().getName().equals( ((DGeneralClass) o).getName()) )
                			to = elements.get(j);
                	}

                }
            }
            if ( from != null && to != null)
            	drawCenteredLine(from,to);
        }

        //drawCenteredLine( elements.get(0), elements.get(1));
        drawCenteredDashedLine( elements.get(0), elements.get(2));
    }

    static void addElements( Group group) {
        for ( int i = 0; i < elements.size(); i++)
            group.getChildren().add( elements.get(i));
    }

    void updateGrid() {
        double size = slider1.getValue() * slider2.getValue(); // changes the look of the grid
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


    public static void updateZoomPane() {
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
        Paint paint =  lineColor;
        gc.setStroke( paint);

        gc.strokeLine(0, 0, 0, size);
        gc.strokeLine(1, 0, size, 0);
        //setStroke(Color.RED);
        sp.setFill(backgroundColor);
        WritableImage image = canvas.snapshot(sp, null);
        return new ImagePattern(image, 0, 0, size, size, false);
    }


    Element createElement(double x, double y, double width, double height, Paint fill, boolean listener) {
        return new Element(x, y, width, height, fill, listener);
    }


    public static void select(Element element) {
        if (overlay == null && element != null) iniOverlay();
        if (element != selectedElement) {
            overlay.setVisible(element != null);
            if (element != null) element.toFront();
            selectedElement = element;
            updateOverlay();
        }
    }

    public static void iniOverlay() {
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

    public static void updateOverlay() {
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

    public static Rectangle srCreate(Cursor cursor) {
    	//Resize rectangle
        Rectangle rectangle = new Rectangle(a, a, Color.BLACK);
        rectangle.setCursor(cursor);
        handleMouse(rectangle);
        return rectangle;
    }

    public static void handleMouse(Node node) {
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
            scrollPane.setPannable(false);
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
                scrollPane.setPannable(true);
            }
            me.consume();
            updateLines();
        });
    }

    static double snap(double value) {
        double gridSize = slider2.getValue();
        return Math.round(value / gridSize) * gridSize;
    }

    static void setHSize(double h, boolean b) {
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

    static void setVSize(double v, boolean b) {
        double y = selectedElement.getLayoutY(), h = selectedElement.heightProperty().get(), height;
        double as = (a * 3) / slider1.getValue();
        if (v < area.getMinY()) v = area.getMinY();
        if (v > area.getMaxY()) v = area.getMaxY();
        if (b) {
            height = h + y - v;
            if (height < as + selectedElement.limitHeight()) {
            	height = as + selectedElement.limitHeight();
            	v = y + h - height;
            }
            selectedElement.setLayoutY(v);
        }
        else {
            height = v - y;
            if (height < as + selectedElement.limitHeight())
            	height = as + selectedElement.limitHeight();
        }
        selectedElement.heightProperty().set(height);
    }

    static void relocate(double x, double y) {
        double maxX = area.getMaxX() - selectedElement.widthProperty().get();
        double maxY = area.getMaxY() - selectedElement.heightProperty().get();
        if (x < area.getMinX()) x = area.getMinX();
        if (y < area.getMinY()) y = area.getMinY();
        if (x > maxX) x = maxX;
        if (y > maxY) y = maxY;
        selectedElement.setLayoutX(x);
        selectedElement.setLayoutY(y);
    }

    static void drawCenteredLine( Element first, Element second)
    {
    	double fcX = first.getLayoutX() + first.widthProperty().get() / 2;
    	double fcY = first.getLayoutY() + first.heightProperty().get() / 2;
    	double scX = second.getLayoutX() + second.widthProperty().get() / 2;
    	double scY = second.getLayoutY() + second.heightProperty().get() / 2;
        ComplexLine line = new ComplexLine(fcX, fcY, scX, scY);
        group.getChildren().add(line);
        ArrowHead a = new ArrowHead( first, line);
    	group.getChildren().addAll(a);
        first.startLines.add(line);
        second.endLines.add(line);
    }

    static void drawCenteredDashedLine( Element first, Element second)
    {
    	double fcX = first.getLayoutX() + first.widthProperty().get() / 2;
    	double fcY = first.getLayoutY() + first.heightProperty().get() / 2;
    	double scX = second.getLayoutX() + second.widthProperty().get() / 2;
    	double scY = second.getLayoutY() + second.heightProperty().get() / 2;
        DashedComplexLine line = new DashedComplexLine(fcX, fcY, scX, scY);
        group.getChildren().add(line);
        ArrowHead a = new ArrowHead( first, line);
    	group.getChildren().addAll(a);
        first.startLines.add(line);
        second.endLines.add(line);
    }

    static void updateLines()
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

    		if ( n instanceof ArrowHead) {
    			ArrowHead a = ( ArrowHead) n;
    			a.updateArrow();
    		}
    	}
    }

    public static void updateArrow() {
    	for (Node n : group.getChildren())
    	{
    		if ( n instanceof ArrowHead) {
    			ArrowHead a = ( ArrowHead) n;
    			a.updateArrow();
    		}
    	}
    }

    public static void displayPoint(Point2D point)
    {
    	Circle c = new Circle (point.getX(), point.getY(), 10);
    	group.getChildren().add(c);
    }

    public static Point2D createPoint( double x, double y)
    {
    	return new Point2D( x, y);
    }


    public static void showClosest( Point2D mouseLoc)
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
        		if (n instanceof ComplexLine)
        		{
        			ComplexLine cl = (ComplexLine) n;
        			for (Point2D clp : cl.getPoints())
        			{
        				if (mouseLoc.distance(clp) <= 5 )
        				{
        					inShape = true;
        				}
        			}
        		}
        	}


        	if (inShape)
        	{
        		closest.setRadius(0);
        	}
        	else
        	{
        		closest.setCenterX(closest2.getX() / slider1.getValue());
            	closest.setCenterY(closest2.getY() / slider1.getValue());
            	closest.setRadius(INDICATOR_RADIUS);
        	}

    	}
    	else
    		closest.setRadius(0);
    }

    public static void setIndicatorVisibility(boolean visibility)
    {
    	if(visibility)
    	{
    		closest.setRadius(INDICATOR_RADIUS);
    	}
    	else
    	{
    		closest.setRadius(0);
    	}
    }

    public static Point2D getClosestPoint(Line l, Point2D mouseLoc)
    {
    	Point2D first = new Point2D( l.getStartX(), l.getStartY());
    	Point2D second = new Point2D( l.getEndX(), l.getEndY());
    	double lambda = (mouseLoc.subtract(first)).dotProduct(second.subtract(first))
    			/ ((second.subtract(first)).dotProduct(second.subtract(first)));
    	Point2D closest2 = new Point2D (second.subtract(first).getX() * lambda, second.subtract(first).getY() * lambda).add(first);
    	return closest2;
    }

    public static ComplexLine getComplex( Line l)
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

    public static Line getClosest( Point2D mouseLoc)
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

    				if( closest2.distance(mouseLoc) < range && closest2.distance(mouseLoc) < 50 * slider1.getValue() && l.contains(closest2))
    				{
    					range = closest2.distance(mouseLoc);
    					closestLine = l;
    				}
    			}
    		}
    	}
    	return closestLine;
    }

    /*
    public static void extractAll( ActionEvent e) {
        System.out.println("extracting all...");
    }

    public static void extractMethods( ActionEvent e) {
        System.out.println("extracting methods...");
    }

    public static void extractFields( ActionEvent e) {
        System.out.println("extracting fields...");
    }*/

    // for loading from file
    public void openProject( DObject d) {

    	// sometimes foreshadowing is relatively obvious

    }

    public static void displayObjectOptions() {
        Button create;
        TextField name;


        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("New Object");
        window.setMinWidth(400);
        window.setMinHeight(400);

        Label messageName = new Label( "Name of the class:");
        name = new TextField();

        Label messageInh = new Label( "This class extends:");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        // Going to add the elements in project
        choiceBox.getItems().add("Object");
        for ( DObject obj : project.getObjects())
        	choiceBox.getItems().add( obj.getName());



        create = new Button("create object");

        create.setOnAction( e -> {

            getInheritanceChoice( choiceBox, createObject( name));
            window.close();
        });



        VBox layout = new VBox();
        layout.getChildren().addAll(  messageName, name, messageInh, choiceBox, create);
        layout.setAlignment( Pos.CENTER);

        window.setScene( new Scene( layout));
        window.showAndWait();
    }

    public static DObject createObject( TextField name) {
    	Element r;
    	DClass object = new DClass( name.getText());
        System.out.println( object);

        Random rand = new Random();
        if ( selectedElement != null)
        	r = new Element(  selectedElement.getLayoutX() + 30 + rand.nextInt(7) , selectedElement.getLayoutY()+ 30 + rand.nextInt(7), 200, 200, Color.color(Math.random(), Math.random(), Math.random()), true);
        else
        	r = new Element( offset + 0 - Math.random()*1000, offset + 0 - Math.random()*1000, 300, 300, Color.color(Math.random(), Math.random(), Math.random()), true);

        	r.setObject(object);
        elements.add(r);
        project.addObject(object);

        group.getChildren().add(r);

        return object;
    }

    public static void getInheritanceChoice( ChoiceBox<String> cb, DObject child) {
    	for ( DObject o : project.getObjects() ) {
    		if ( cb.getValue().equals(o.getName()))
    			setInheritance(o , child);
    	}
    }

    public static void setInheritance( DObject parent, DObject child) {
    	Element lastAdded = null, selected = null;

    	for ( Node n : group.getChildren()) {
			if ( n instanceof Element && ((Element) n).hasObject()){
				if ( (((Element) n).getObject().getName()).equals( child.getName()) )
					lastAdded = (Element)n;
			}
		}
    	for ( Node n : group.getChildren()) {
			if ( n instanceof Element && ((Element) n).hasObject()){
				if ( (((Element) n).getObject().getName()).equals( parent.getName()) )
					selected = (Element)n;
			}
		}

    	drawCenteredLine( selected, lastAdded);
        select(selected);
        select(lastAdded);
    }


    public static void displayFieldOptions( Element element) {
        Button create;
        TextField name, type;

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("New Property");
        window.setMinWidth(400);
        window.setMinHeight(400);

        Label messageName = new Label( "Type of the property:");
        name = new TextField();
        Label messageType = new Label( "Name of the property:");
        type = new TextField();
        create = new Button("add property");

        create.setOnAction( e -> {

        	if ( name.getText().equals("") && type.getText().equals("") ){
        		displayErrorMessage( "No name or type found");
        		displayFieldOptions( element);
        		window.close();
        	}
        	else if ( type.getText().equals("") ) {
        		displayErrorMessage( "No type found");
        		displayFieldOptions( element);
        		window.close();
        	}
            else if ( name.getText().equals("") ) {
            	displayErrorMessage( "No name found");
            	displayFieldOptions( element);
            	window.close();
            }
            else {
        		addProperty( name.getText(), type.getText(), element);
            }

            window.close();
        });



        VBox layout = new VBox();
        layout.getChildren().addAll( messageName, name, messageType, type, create);
        layout.setAlignment( Pos.CENTER);

        window.setScene( new Scene( layout));
        window.showAndWait();
    }

    public static void displayMethodOptions( Element element) {
        Button create;
        TextField name, returnType;
        CheckBox cb, cb2, cb3;
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("New Method");
        window.setMinWidth(400);
        window.setMinHeight(400);

        Label messageName = new Label( "Name of the method:");
        name = new TextField();
        Label messageType = new Label( "Return type of the method:");
        returnType = new TextField();
        //Label messageParam = new Label( "Add parameters below");
        cb = new CheckBox();
        cb2 = new CheckBox();
        cb3 = new CheckBox();
        cb.setTooltip(  new Tooltip("void"));
        cb2.setTooltip(new Tooltip("static"));
        cb3.setTooltip( new Tooltip("no parameters"));
        create = new Button("create method");

        create.setOnAction( e -> {
        	final boolean stat = cb2.isSelected();

        	if ( name.getText().equals("") && returnType.getText().equals("") ){
        		displayErrorMessage( "No name or return type found");
        		displayMethodOptions( element);
        		window.close();
        	}
        	else if ( returnType.getText().equals("") ) {
        		displayErrorMessage( "No return type found");
        		displayMethodOptions( element);
        		window.close();
        	}
            else if ( name.getText().equals("") ) {
            	displayErrorMessage( "No name found");
            	displayMethodOptions( element);
            	window.close();
            }
            else {
            	final DMethod meth = new DMethod( name.getText(), returnType.getText(), stat);
        		if ( !cb3.isSelected())
        			displayParameterOptions( meth, element);
        		else
        			addMethod( meth, element);
            }

            window.close();
        });

        VBox layout = new VBox();
        layout.getChildren().addAll( messageName, name, messageType, returnType, cb, cb2, cb3, create);
        layout.setAlignment( Pos.CENTER);

        window.setScene( new Scene( layout));
        window.showAndWait();
    }

    public static void displayParameterOptions( DMethod meth, Element element) {
    	Button addParam, addNew;

    	ArrayList<TextField> params = new ArrayList<TextField>();

        Stage window = new Stage();
        VBox layout = new VBox();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Method Parameters for " + meth.getName());
        window.setMinWidth(400);
        window.setMinHeight(400);


        Label messageParam = new Label( "Add parameters below");
        layout.getChildren().add(messageParam);



        addNew = new Button("add another");
        addNew.setOnAction( e -> {
        	final TextField tf = new TextField("tpye, name");
        	params.add(tf);
        	layout.getChildren().add( tf);
        	e.consume();
        });

        addParam = new Button("create method");

        addParam.setOnAction( e -> {

        	for ( int i = 0; i < params.size(); i++) {
        		for ( int j = 0; j < params.get(i).getText().length(); j++){
        			if ( params.get(i).getText().charAt(j) == ',') {
                        meth.addParameter( params.get(i).getText().substring(j+1), params.get(i).getText().substring(0,j) );
        			}
        		}
        	}
        	addMethod(meth, element);

            window.close();
        });

        layout.setAlignment( Pos.CENTER_LEFT);
        addNew.setAlignment(Pos.CENTER_RIGHT);
        addParam.setAlignment( Pos.BOTTOM_CENTER);

        BorderPane parentLayout = new BorderPane();
        parentLayout.setLeft( layout);
        parentLayout.setRight( addNew);
        parentLayout.setBottom( addParam);

        window.setScene( new Scene( parentLayout));
        window.showAndWait();

    }

    public static void displayErrorMessage(String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        window.setTitle("New Object");
        window.setMinWidth(200);
        window.setMinHeight(160);

        Label messageName = new Label( message);

        Button ok = new Button("OK");
        ok.setOnAction( e ->  window.close());

        VBox layout = new VBox();
        layout.getChildren().addAll( messageName, ok);
        layout.setAlignment( Pos.CENTER);

        window.setScene( new Scene( layout));
        window.showAndWait();
    }

    public static void addProperty( String name, String type, Element element) {
    	DProperty prop = new DProperty( name, type);
    	element.addField(prop);
    	((DClass)element.getObject()).addProperty(prop);
    	System.out.println( prop);
    }

    public static void addMethod( DMethod m, Element element) {
    	element.addMethod(m);
    	((DClass)element.getObject()).addMethod(m);
    	System.out.print( m);
    }

    public static void extractAll( ActionEvent e, DProject prj) {
        System.out.println("extracting all...");
        prj.extract("");
    }

    public static void extractMethods( ActionEvent e) {
        System.out.println("extracting methods...");
    }

    public static void extractFields( ActionEvent e) {
        System.out.println("extracting fields...");
    }

    static void setLineColor( Paint color)
    {
    	lineColor = color;
    }
    static void setBackgroundColor(Paint color)
    {
    	backgroundColor = color;
    	Paint bg1 = color;
        BackgroundFill backgroundFill1 = new BackgroundFill(bg1, null, null);
    }

    public static void displayConstructorMakerWindow( Element element) {
        Stage window = new Stage();
        Scene scene;
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.DECORATED);
        window.setTitle("Constructor Maker");
        window.setMinWidth(200);
        window.setMinHeight(160);
        TableView table = new TableView();

        table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("Accessibility");
        TableColumn lastNameCol = new TableColumn("Parameters");

        firstNameCol.setPrefWidth( 60);
        lastNameCol.setPrefWidth( 60);

        table.getColumns().addAll(firstNameCol, lastNameCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll( table);

        scene = new Scene(vbox);
        //((Group) scene.getRoot()).getChildren().addAll(vbox);

        window.setScene(scene);
        window.show();
    }
    public static void displayRemoveOptions( Element element) {
		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Are you sure");
		window.setMinWidth(200);
		window.setMinHeight(160);

		Label messageName = new Label( "Deleting " + selectedElement.getObject().getName() + "...\n" + "Are you sure?");

		Button ok = new Button("Yes");
		Button notOk = new Button("cancel");
		ok.setOnAction(e -> {
			select(null);

			//deleting overlay
			for ( int i =0; i < group.getChildren().size();i++) {
				if ( group.getChildren().get(i) instanceof Group && ((Group) group).getChildren().get(i) == overlay)
					group.getChildren().remove(group.getChildren().get(i));
			}
			// forgeting element
			elements.remove(element);
			// deleting element
			for ( int i =0; i < group.getChildren().size();i++) {
				if ( group.getChildren().get(i) instanceof Element && ((Element) group.getChildren().get(i)).listener &&((Element)group.getChildren().get(i)).getObject().getName().equals( element.getObject().getName()))
					group.getChildren().remove(group.getChildren().get(i));
			}
			// removing from project
			project.getObjects().remove(element.getObject());
			//deleting line from group
			for ( int i =0; i < group.getChildren().size();i++) {

				if ( group.getChildren().get(i) instanceof ComplexLine ) {

					ComplexLine cl = (ComplexLine)group.getChildren().get(i);

					for ( int j =0; j < group.getChildren().size();j++)
						if ( group.getChildren().get(j) instanceof ArrowHead ) {
							ArrowHead  a = (ArrowHead)group.getChildren().get(j);

							if (  element.getEndLines().contains( a.getComplexLine()) ||  element.getStartLines().contains( a.getComplexLine())){
								group.getChildren().remove( a);
							}
						}
					if ( element.startLines.contains(cl) || element.endLines.contains(cl))
						group.getChildren().remove(cl);

				}
				if ( group.getChildren().get(i) instanceof DashedComplexLine ) {

					DashedComplexLine cdl = (DashedComplexLine)group.getChildren().get(i);

					for ( int j =0; j < group.getChildren().size();j++)
						if ( group.getChildren().get(j) instanceof ArrowHead ) {
							ArrowHead  a = (ArrowHead)group.getChildren().get(j);

							if (  element.getEndLines().contains( a.getComplexLine()) ||  element.getStartLines().contains( a.getComplexLine())){
								group.getChildren().remove( a);
							}
						}
					if ( element.startLines.contains(cdl) || element.endLines.contains(cdl))
						group.getChildren().remove(cdl);

				}
			}

			updateArrow();
			updateLines();
			updateOverlay();
			updateZoomPane();
			//drawHierarchy(project);
			window.close();
		});
		notOk.setOnAction( e ->  window.close());

		VBox layout = new VBox();
		layout.getChildren().addAll( messageName, ok, notOk);
		layout.setAlignment( Pos.CENTER);

		window.setScene( new Scene( layout));
		window.showAndWait();
	}

}