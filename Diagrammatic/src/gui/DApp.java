package gui;

import java.util.ArrayList;
import java.util.Random;

import gui.tools.ArrowHead;
import gui.tools.ClassElement;
import gui.tools.ComplexLine;
import gui.tools.DashedComplexLine;
import gui.tools.Element;
import gui.tools.InterfaceElement;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
import logic.object_source.DClass;
import logic.object_source.DGeneralClass;
import logic.object_source.DInterface;
import logic.object_source.DObject;
import logic.tools.DConstructor;
import logic.tools.DConstructorProperty;
import logic.tools.DProject;
import logic.tools.DProperty;

public class DApp extends Application {

	// App Properties

	// Helpers
	public static final String[] colors = { "0xfeff99",
			"0xffed99",
			"0xffc79a",
			"0xff999a",
			"0xeb99c7",
			"0xc69edd",
			"0xb1a1e0",
			"0xa0b3dd",
			"0x9ad6d6",
			"0x99eb99",
	"0xd9f89a"};
	static double a = 6, a2 = a / 2, lX, lY, sX, sY, sWidth, sHeight;
	static double gridSize = -1;
	double lastX;
	double lastY;
	static int offset = 5000;

	// Members
	public static final int ELEMENT_HEIGHT = 150;
	public static final int ELEMENT_WIDTH = 150;
	public static final double DEFAULT_ZOOM = 1.1606;
	public static final double DEFAULT_GRIDSIZE = 15.7377;
	public static final double INDICATOR_RADIUS = 7;
	public static final double RANDOMNESS = 250.0;
	public static Slider slider1, slider2;
	public static CheckBox checkBox;
	public static ScrollPane scrollPane;
	public static Group group;
	static Group overlay = null;
	static Pane zoomPane;
	public static Rectangle srBnd, srNW, srN, srNE, srE, srSE, srS, srSW, srW;
	static Circle closest;
	static Element selectedElement;
	public static ArrayList<Element> elements;
	public static Rectangle2D area; // sets the borders for moving objects
	public static DProject project;
	public static ArrayList<ComplexLine> lines;

	// Colors
	public static Paint lineColor;
	public static Paint backgroundColor;
	static Paint bg1 = Paint.valueOf("linear-gradient(from 0.0% 0.0% to 0.0% 100.0%, 0x90c1eaff 0.0%, 0x5084b0ff 100.0%)");
	static BackgroundFill backgroundFill1 = new BackgroundFill(bg1, null, null);
	// canvas & sp
	static Canvas canvas = new Canvas();
	static SnapshotParameters sp = new SnapshotParameters();

	public static void main( String[] args)
	{
		launch(args);
	}

	@Override
    public void start( Stage stage) {
		lines = new ArrayList<ComplexLine>();
    	area = new Rectangle2D(0, 0, 10000, 10000); // sets the borders for moving objects
        BorderPane layout = new BorderPane();

        stage.setTitle("Diagrammatic 0.2");
        stage.setScene(new Scene(layout, 500, 300));
        //set the location of the window
        stage.setX(50);
        stage.setY(50);
        //set the size of the window
        stage.setWidth(1820);
        stage.setHeight(900);

        Image icon = new Image("file:icon.png");
        //Image albaniaIcon = new Image("file:albaniaicon.jpg");
        //stage.getIcons().add(albaniaIcon);
        stage.getIcons().add(icon);

        // init project
        project = new DProject();

        // init prj objects
        DObject albanian = new DClass("Albanian");
        DObject albanianable = new DInterface("Albanianable");
        DObject kosovan = new DClass("Kosovan");
        ((DClass) kosovan).setSuperClass((DClass) albanian);


        //testing prop
        ((DClass) kosovan).addProperty( new DProperty( "isBased","boolean"));
        ((DClass) kosovan).addProperty( new DProperty( "hatesSerbs", "boolean"));

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
        //layout.setBottom(new FlowPane(slider1, checkBox, slider2));
        //stage.setOnCloseRequest(e -> System.out.println(group.getChildren().toString()));

        // Layout
        VBox leftLayout = new VBox(10);
        VBox rightLayout = new VBox(10);
        BorderPane parentLayout = new BorderPane();

        // File Menu
        Menu fileMenu = new Menu("File");
        MenuItem newProject = new MenuItem( "New Project");
        newProject.setOnAction(e -> {
        	 DMenuWizard.displayProjectOptions();
        });
        MenuItem openProject = new MenuItem( "Open");
        MenuItem saveProject = new MenuItem( "Save");
        MenuItem saveProjectAs = new MenuItem( "Save As...");

        openProject.setOnAction(e -> {
        	DMenuWizard.displayLoadOptions();
        });

        saveProject.setOnAction(e -> {
        	DMenuWizard.displaySaveOptions();
        });

        saveProjectAs.setOnAction(e -> {
        	DMenuWizard.displaySaveAsOptions();
        });


        fileMenu.getItems().addAll(newProject, openProject, saveProject, saveProjectAs);

        // Add Menu
        Menu addMenu = new Menu("Add");
        MenuItem newObject = new MenuItem("New Class");
        newObject.setOnAction(e -> {
        	 DMenuWizard.displayClassOptions();
        });
        MenuItem newInterface = new MenuItem("New Interface");
        newInterface.setOnAction(e -> {
        	 DMenuWizard.displayInterfaceOptions();
        });
        MenuItem newField = new MenuItem("New Property");
        newField.setOnAction( e -> {
        	if ( selectedElement != null)
        		 DMenuWizard.displayFieldOptions( selectedElement);
        	else
        		 DMenuWizard.displayErrorMessage("No class selected");
        });
        MenuItem newMethod = new MenuItem("New Method");
        newMethod.setOnAction( e -> {
        	if ( selectedElement != null)
        		 DMenuWizard.displayMethodOptions( selectedElement);
        	else
        		 DMenuWizard.displayErrorMessage("No Class Selected");
        });
        MenuItem newConstr = new MenuItem("New Constructor");
        newConstr.setOnAction( e -> {
        	if ( selectedElement != null)
        		 DMenuWizard.displayConstructorMakerWindow( selectedElement);
        	else
        		 DMenuWizard.displayErrorMessage("No Class Selected");
        });

        addMenu.getItems().addAll( newObject, newInterface, newField, newMethod, newConstr);

        //Help menu
        Menu helpMenu = new Menu("Help");

        CheckMenuItem autoSave = new CheckMenuItem("Enable Autosave");
        autoSave.setSelected(true);
        helpMenu.getItems().add(autoSave);

        //Difficulty RadioMenuItems
        Menu extractMenu = new Menu("Extract...");
        MenuItem extractAll = new MenuItem( "Extract All");
        MenuItem showCode = new MenuItem( "Show Code");


        extractAll.setOnAction(e ->  DMenuWizard.extractAll(e, project) );
        showCode.setOnAction(e ->  DMenuWizard.showCode(selectedElement));


        extractMenu.getItems().addAll(extractAll, showCode);

        // most important menu
        //Menu ytpMenu = new Menu("YTP modes");

        // Preferences
        Menu preferencesMenu = new Menu("Preferences");
        MenuItem lightMode = new MenuItem( "Light Mode");
        MenuItem darkMode = new MenuItem( "Dark Mode");
        MenuItem sepyaMode = new MenuItem( "Sepya Mode");
        preferencesMenu.getItems().addAll(lightMode, darkMode, sepyaMode);

        lightMode.setOnAction( e -> {
            setColorMode("Light");
        });
        darkMode.setOnAction( e -> {
            setColorMode("Dark");
        });
        sepyaMode.setOnAction( e -> {
            setColorMode("Sepya");
        });

        // edit menu
        Menu editMenu = new Menu("Edit");
		MenuItem editRelations = new MenuItem("Edit Relations");
		MenuItem editClass = new MenuItem("Edit Class");
		MenuItem removeClass = new MenuItem("Remove Class");

		editRelations.setOnAction(e -> {
			//displayObjectOptions();
		});

		editClass.setOnAction(e -> {
			DMenuWizard.editObjectOptions();
		});

		removeClass.setOnAction(e -> {
			if (selectedElement != null)
				 DMenuWizard.displayRemoveOptions( selectedElement);
			else
				 DMenuWizard.displayErrorMessage( "No Selected Class");
		});
		editMenu.getItems().addAll( editRelations, editClass, removeClass);

        //Main menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, addMenu, helpMenu, extractMenu, editMenu, preferencesMenu);

        parentLayout.setCenter( layout);
        parentLayout.setLeft( leftLayout );
        parentLayout.setRight( rightLayout );
        parentLayout.setTop(menuBar);

        Scene scene = new Scene( parentLayout, 300, 250);

		//scene.setUserAgentStylesheet("Viper.css");
        stage.setScene( scene);

        stage.show();
        scrollPane.setPannable(true);
        //scrollPane.setTranslateX(100);
        //scrollPane.setTranslateY(100);

        addElements( group);

        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setHvalue(offset + 300);
        scrollPane.setVvalue(offset + 300);

        group.getChildren().addAll( cornerNE, cornerNW, cornerSE, cornerSW, closest);

        for (int i = 0; i < 3; i++)
        {
        	DMenuWizard.removeObject(elements.get(0));
        }

        setColorMode("Light");
        scrollPane.setPannable(true);
    }
	void iniElements( DProject prj) {
		elements = new ArrayList<Element>();
		Random random = new Random();
		for ( int i = 0; i < prj.getObjects().size(); i++) {
			if ( prj.getObjects().get(i) instanceof DClass)
				elements.add( new ClassElement( offset + 300 - Math.random()*500, offset + 300 - Math.random()*500,
						ELEMENT_WIDTH, ELEMENT_HEIGHT, Color.web(colors[0].substring(0, 1) + colors[random.nextInt(10)].substring(1).toUpperCase(), 1.0), true));
			else if  (prj.getObjects().get(i) instanceof DInterface)
				elements.add( new InterfaceElement( offset + 300 - Math.random()*500, offset + 300 - Math.random()*500,
						ELEMENT_WIDTH, ELEMENT_HEIGHT, Color.web(colors[0].substring(0, 1) + colors[random.nextInt(10)].substring(1).toUpperCase(), 1.0), true));
		}


		for ( int i = 0; i < prj.getObjects().size(); i++) {
			elements.get(i).setObject( prj.getObjects().get(i));
			elements.get(i).updateObject();
			elements.get(i).iniObject();
		}
	}

	public static void drawHierarchy( DProject prj) {

		for (int j = 0; j < prj.getObjects().size(); j++)
		{
			DObject o = prj.getObjects().get(j);

			if (o instanceof DGeneralClass)
			{
				DGeneralClass gc = (DGeneralClass) o;
				if (gc.getSuperClass() != null)
				{
					drawCenteredLine(gc.getSuperClass().getElement(), gc.getElement());
				}
				if (!gc.getInterfaces().isEmpty())
				{
					for (DInterface superInt : gc.getInterfaces())
					{
						drawCenteredDashedLine( gc.getElement(), superInt.getElement());
					}
				}
			}
			if (o instanceof DInterface)
			{
				DInterface i = (DInterface) o;
				if (!i.getSuperInterfaces().isEmpty())
				{
					for (DInterface superInt : i.getSuperInterfaces())
					{
						drawCenteredLine(superInt.getElement(), i.getElement());
					}
				}
			}
		}

	}

	static void addElements( Group group) {
		for ( int i = 0; i < elements.size(); i++)
			group.getChildren().add( elements.get(i));
	}

	static void updateGrid() {
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


	static ImagePattern patternTransparent(double size) {
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
			closest.setRadius(0);
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
				closest.setRadius(10);
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
		ComplexLine line = new ComplexLine(fcX, fcY, scX, scY, first, second);
		group.getChildren().add(line);
		ArrowHead a = new ArrowHead( first, line);
		group.getChildren().addAll(a);
		first.startLines.add(line);
		second.endLines.add(line);
		lines.add(line);
	}

	static void drawCenteredDashedLine( Element first, Element second)
	{
		double fcX = first.getLayoutX() + first.widthProperty().get() / 2;
		double fcY = first.getLayoutY() + first.heightProperty().get() / 2;
		double scX = second.getLayoutX() + second.widthProperty().get() / 2;
		double scY = second.getLayoutY() + second.heightProperty().get() / 2;
		DashedComplexLine line = new DashedComplexLine(fcX, fcY, scX, scY, first, second);
		group.getChildren().add(line);
		ArrowHead a = new ArrowHead( first, line);
		group.getChildren().addAll(a);
		first.startLines.add(line);
		second.endLines.add(line);
		lines.add(line);
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
		{
			closest.setRadius(0);
		}

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

	static void setLineColor( Paint color)
	{
		lineColor = color;
	}
	static void setBackgroundColor(Paint color)
	{
		backgroundColor = color;
	}

	public static void displayConstructorMakerWindow( Element element) {
		Stage window = new Stage();
		Scene scene;
		window.initModality(Modality.APPLICATION_MODAL);
		window.initStyle(StageStyle.DECORATED);
		window.setTitle("Constructor Maker");
		window.setMinWidth(200);
		window.setMinHeight(160);
		ArrayList<DConstructorProperty> dConstructorProperties = new ArrayList<DConstructorProperty>();
		ArrayList<Label> labels = new ArrayList<Label>();
		/* TableView table = new TableView();
		 */

		//table
		/*table.setEditable(false);

        TableColumn<DConstructor, String> accessibility = new TableColumn("Accessibility");
        TableColumn<DConstructor, ArrayList<DConstructorProperty>> parameters = new TableColumn("Parameters");

        accessibility.setPrefWidth( 120);
        parameters.setPrefWidth( 120);

        accessibility.setCellValueFactory(new PropertyValueFactory<>( "accessibility"));
        parameters.setCellValueFactory(new PropertyValueFactory<>( "properties"));*/



		/*table.getColumns().addAll( accessibility, parameters);*/

		//buttons
		Button addButton = new Button("New Constructor");
		addButton.setOnAction(e -> {
			DConstructor dc = new DConstructor( (DClass) selectedElement.getObject());
			for (DConstructorProperty p : dConstructorProperties){
				if (p.isIncluded()) {
					dc.addProperty(p);
					labels.add(new Label(p.getProperty().getAcccessability() + " | " + p.getProperty().getType() + " | " + p.getProperty().getName()));
				}
			}
			//table.getItems().add( dc);
			( (DClass) selectedElement.getObject()).addConstructor( dc);
		});
		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction(e -> {
		});

		//hbox
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(10,10,10,10));
		hBox.setSpacing(10);
		hBox.getChildren().addAll( addButton, deleteButton);

		//radio buttons
		VBox vbox2 = new VBox();

		for (DProperty p : selectedElement.getObject().getProperties()) {
			RadioButton rb = new RadioButton( p.getName());
			DConstructorProperty consProp = new DConstructorProperty( p, false);

			vbox2.getChildren().add( rb);
			rb.setOnAction( event -> {
				consProp.setIncluded( rb.isSelected());
				if ( rb.isSelected())
					if (!dConstructorProperties.contains(consProp)) {
						dConstructorProperties.add(consProp);
					}
					else
						dConstructorProperties.remove( consProp);
			});
		}

		//vbox for labels
		VBox vlabel = new VBox();
				vlabel.setSpacing(5);
				vlabel.setPadding(new Insets(10, 10, 10, 10));
				for (Label l : labels) {
					vlabel.getChildren().add( l);
				}

				//vbox
				VBox vbox = new VBox();
				vbox.setSpacing(5);
				vbox.setPadding(new Insets(10, 10, 10, 10));
				vbox.getChildren().addAll(vlabel, vbox2, hBox);

				scene = new Scene(vbox);
				//((Group) scene.getRoot()).getChildren().addAll(vbox);

				window.setScene(scene);
				window.show();
	}

	public static void cleanProjectView() {
		select(null);

		// forgeting all elements
		elements.clear();

		// removing from project
		project.getObjects().clear();

		//deleting line from group
		for ( int i =0; i < group.getChildren().size();i++) {

			if ( group.getChildren().get(i) instanceof Element && !((Element) group.getChildren().get(i)).listener) {

			}else {
				group.getChildren().remove( i);
			}
		}

		updateArrow();
		updateLines();
		updateOverlay();
		updateZoomPane();
	}

	/**
     * Changes the background color theme
     * @param mode
     */
    public static void setColorMode( String mode)
    {
        if(mode.equals("Sepya"))
        {
            setLineColor(Color.DARKSALMON );
            setBackgroundColor( Color.CORNSILK);
        }
        else if(mode.equals("Light"))
        {
            setLineColor(Color.GRAY );
            setBackgroundColor( Color.GHOSTWHITE);
        }
        else if( mode.equals("Dark"))
        {
            setLineColor(Color.LIGHTGREY );
            setBackgroundColor( Color.LIGHTSLATEGRAY);
        }
        double size = slider1.getValue() * slider2.getValue(); // changes the look of the grid
        Paint bg2 = patternTransparent(size);
        BackgroundFill backgroundFill2 = new BackgroundFill(bg2, null, null);
        zoomPane.setBackground(new Background(backgroundFill1, backgroundFill2));
    }



}