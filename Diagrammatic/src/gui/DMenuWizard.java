package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import gui.tools.ArrowHead;
import gui.tools.ComplexLine;
import gui.tools.DashedComplexLine;
import gui.tools.Element;
import gui.tools.InterfaceElement;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.object_source.DAbstractClass;
import logic.object_source.DClass;
import logic.object_source.DGeneralClass;
import logic.object_source.DInterface;
import logic.object_source.DObject;
import logic.tools.DConstructor;
import logic.tools.DConstructorProperty;
import logic.tools.DMethod;
import logic.tools.DProject;
import logic.tools.DProperty;
import logic.tools.ProjectManager;

/**
 * DMenuWizard class which controls menu events and has methods about it.
 * @author  Group 2F
 * @version 20.05.2020
 */
public class DMenuWizard {
	/**
	 * This method opens a new project for program
	 * @param d
	 */
	public void openProject( DObject d) {

		// sometimes foreshadowing is relatively obvious

	}

	/**
	 * This method displays the object options in the menu to create classes, methods or properties etc.
	 */
	public static void displayClassOptions() {
		Button create;
		TextField name;

		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("New Class");
		window.setMinWidth(400);

		Label messageName = new Label( "Name of the class:");
		name = new TextField();

		CheckBox abstractCheck = new CheckBox("Abstract Class");

		Label messageInh = new Label( "This class extends:");
		ChoiceBox<String> choiceBox = new ChoiceBox<>();

		Label messageInh2 = new Label( "This class implements:");
		ArrayList<CheckBox> interfaces = new ArrayList<>();



		// Going to add the elements in project
		choiceBox.getItems().add("Object");
		for ( DObject obj : DApp.project.getObjects())
		{
			if (obj instanceof DGeneralClass)
			{
				choiceBox.getItems().add( obj.getName());
			}
		}


		for ( DObject obj : DApp.project.getObjects())
		{
			if (obj instanceof DInterface)
			{
				interfaces.add( new CheckBox(obj.getName()));
			}

		}
		HBox interfaceBox = new HBox();
		for ( CheckBox c : interfaces)
			interfaceBox.getChildren().add(c);
		if ( interfaces.isEmpty())
			interfaceBox.getChildren().add( new Label("\t\t\t\t\tno interfaces in project"));

		create = new Button("Create a new class");

		create.setOnAction( e -> {
			try {
				String className = name.getText();
				if (ProjectManager.isSuitableName(className))
				{
					boolean taken = false;
					for (DObject objects : DApp.project.getObjects())
					{
						if (objects.getName().equals(className))
							taken = true;
					}
					if( !taken)
					{
						boolean isAbstract = abstractCheck.isSelected();
						getInheritanceChoice( choiceBox, createClass( name, interfaces, isAbstract));
					}
					else
					{
						window.close();
						displayErrorMessage("This name is already used by another class.");
					}
				}
				else
				{
					window.close();
					displayErrorMessage("Enter a suitable name.");
				}
			} catch (NullPointerException n) {
				n.printStackTrace();
				window.close();
			}
			window.close();
		});


		VBox layout = new VBox();
		layout.getChildren().addAll(  messageName, name, messageInh, choiceBox, messageInh2, interfaceBox, abstractCheck, create);
		layout.setAlignment( Pos.CENTER);

		window.setScene( new Scene( layout));
		window.showAndWait();
	}

	public static void displayInterfaceOptions() {
		Button create;
		TextField name;


		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("New Interface");
		window.setMinWidth(400);

		Label messageName = new Label( "Name of the interface:");
		name = new TextField();

		Label messageInh = new Label( "This interface extends:");
		ChoiceBox<String> choiceBox = new ChoiceBox<>();

		// Going to add the elements in project
		for ( DObject obj : DApp.project.getObjects())
		{
			if (obj instanceof DInterface)
				choiceBox.getItems().add( obj.getName());
		}


		create = new Button("Create Interface");

		create.setOnAction( e -> {
			try {
				String interfaceName = name.getText();
				if (ProjectManager.isSuitableName(interfaceName))
				{
					boolean taken = false;
					for (DObject objects : DApp.project.getObjects())
					{
						if (objects.getName().equals(interfaceName))
							taken = true;
					}
					if( !taken)
					{
						getInheritanceChoice( choiceBox, createInterface( name, choiceBox));
					}
					else
					{
						window.close();
						displayErrorMessage("This name is already used by another class.");
					}
				}
				else
				{
					window.close();
					displayErrorMessage("Enter a suitable name.");
				}
			} catch (NullPointerException n) {
				n.printStackTrace();
				window.close();
			}
			window.close();
		});



		VBox layout = new VBox();
		layout.getChildren().addAll(  messageName, name, messageInh, choiceBox, create);
		layout.setAlignment( Pos.CENTER);

		window.setScene( new Scene( layout));
		window.showAndWait();
	}

	/**
	 * This method allows user to choose parent class for new class
	 * @param cb
	 * @param child
	 */
	public static void getInheritanceChoice( ChoiceBox<String> cb, DObject child) {
		for ( DObject o : DApp.project.getObjects() ) {
			if ( cb.getValue() != null && cb.getValue().equals(o.getName()))
			{
				setInheritance(o , child);
			}

		}
	}

	/**
	 * This method allows user to set the inheritance of the class
	 * @param parent
	 * @param child
	 */
	public static void setInheritance( DObject parent, DObject child) {
		Element lastAdded = null, selected = null;

		for ( Node n : DApp.group.getChildren()) {
			if ( n instanceof Element && ((Element) n).hasObject()){
				if ( (((Element) n).getObject().getName()).equals( child.getName()) )
					lastAdded = (Element)n;
			}
		}
		for ( Node n : DApp.group.getChildren()) {
			if ( n instanceof Element && ((Element) n).hasObject()){
				if ( (((Element) n).getObject().getName()).equals( parent.getName()) )
					selected = (Element)n;
			}
		}

		if (child instanceof DGeneralClass && parent instanceof DGeneralClass)
		{
			DGeneralClass childClass= (DGeneralClass) child;
			DGeneralClass parentClass = (DGeneralClass) parent;
			childClass.setSuperClass(parentClass);
		}

		if (child instanceof DInterface && parent instanceof DInterface)
		{
			((DInterface)child).addSuperInterface( (DInterface) parent);
		}

		DApp.drawCenteredLine( selected, lastAdded);
		DApp.select(selected);
		DApp.select(lastAdded);
	}

	/**
	 * This method creates new object in the program
	 * @param name
	 * @return DObject
	 */
	public static DGeneralClass createClass( TextField name, ArrayList<CheckBox> interfaces, boolean isAbstract) {
		Element r;
		DGeneralClass object = null;

		// ini DObjects
		if (isAbstract)
			object = new DAbstractClass( name.getText());
		else
			object = new DClass( name.getText());
		object.setName(name.getText());

		// ini element
		Random rand = new Random();
		if ( DApp.selectedElement != null)
			r = new Element(  DApp.selectedElement.getLayoutX() + 30 + rand.nextInt(7) , DApp.selectedElement.getLayoutY()+ 30 + rand.nextInt(7),
								DApp.ELEMENT_WIDTH, DApp.ELEMENT_WIDTH,
								Color.web(DApp.colors[0].substring(0, 1) + DApp.colors[rand.nextInt(10)].substring(1).toUpperCase(), 1.0), true);
		else
			r = new Element( DApp.offset + 0 - Math.random()*DApp.RANDOMNESS, DApp.offset + 0 - Math.random()*DApp.RANDOMNESS,
								DApp.ELEMENT_WIDTH, DApp.ELEMENT_WIDTH,
								Color.web(DApp.colors[0].substring(0, 1) + DApp.colors[rand.nextInt(10)].substring(1).toUpperCase(), 1.0), true);

		// update project and application
		ProjectManager.connectElement(r, object);
		DApp.elements.add(r);
		DApp.project.addObject(object);
		DApp.group.getChildren().add(r);

		// draw relations to interface for each interface implemented
		for ( int i = 0; i < interfaces.size(); i++) {
			if ( interfaces.get(i).isSelected()) {
				Element lastAdded = null, selected = null;

				// determine for each
				for ( Node n : DApp.group.getChildren()) {
					if ( n instanceof Element && ((Element) n).hasObject()){
						if ( (((Element) n).getObject().getName()).equals( object.getName()) )
							lastAdded = (Element)n;
					}
				}
				for ( Node n : DApp.group.getChildren()) {
					if ( n instanceof Element && ((Element) n).hasObject()) {
						if ( (((Element) n).getObject().getName()).equals(interfaces.get(i).getText()) )
						{
							selected = (Element)n;
							DInterface di = (DInterface) selected.getObject();
							object.addInterface(di);
						}
					}
				}

				// draw the line
				DApp.drawCenteredDashedLine( selected,  lastAdded);
				DApp.select(selected);
				DApp.select(lastAdded);
				DApp.select(r);
				DApp.updateZoomPane();
			}

		}


		return object;
	}

	public static void editObjectOptions() {
		if (DApp.selectedElement != null) {
			if (DApp.selectedElement.getObject() instanceof DGeneralClass) {
				editClassOptions();
			}
			else if (DApp.selectedElement.getObject() instanceof DInterface) {
				editInterfaceOptions();
			}
		}
		else {
			displayErrorMessage("No Selected class");
		}
	}

	public static void editClassOptions()
	{

		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.initStyle(StageStyle.DECORATED);
		window.setTitle("Edit Class");
		window.setMinWidth(200);
		window.setMinHeight(160);

		Button removePropertyButton = new Button("Remove Selected Properties");
		Button addPropertyButton = new Button("Add New Property");
		Button removeMethodButton = new Button("Remove Selected Methods");
		Button addMethodButton = new Button("Add New Method");

		Button editName = new Button( "Edit Name");
		editName.setOnAction( e -> {
			editNameOptions();
			window.close();
		});

		VBox propertyVBox = new VBox();
		propertyVBox.getChildren().add( new Label("Properties: "));
		for (DProperty dp : DApp.selectedElement.getObject().getProperties()) {
			propertyVBox.getChildren().add(new CheckBox( dp.getName()));
		}

		VBox methodVBox = new VBox();
		methodVBox.getChildren().add( new Label("Methods: "));
		for (DMethod dm : DApp.selectedElement.getObject().getMethods()) {
			methodVBox.getChildren().add(new CheckBox(dm.getName()));
		}

		HBox propertiesHBox = new HBox();
		propertiesHBox.getChildren().addAll(propertyVBox, removePropertyButton, addPropertyButton);

		HBox methodsHBox = new HBox();
		methodsHBox.getChildren().addAll(methodVBox, removeMethodButton, addMethodButton);

		Label editInheritanceLabel = new Label("Super Class: ");
		ComboBox<String> comboBoxSuperClass = new ComboBox<>();
		for (Element e : DApp.elements) {
			if (!DApp.selectedElement.getObject().getName().equals(e.getObject().getName()))
				comboBoxSuperClass.getItems().add(e.getObject().getName());
		}
		if (((DGeneralClass) DApp.selectedElement.getObject()).getSuperClass() != null)
		{
			comboBoxSuperClass.setPromptText(((DGeneralClass) DApp.selectedElement.getObject()).getSuperClass().getName());
			removeLine( DApp.selectedElement, false);
		}
		comboBoxSuperClass.setOnAction(event -> {
			if (((DGeneralClass) DApp.selectedElement.getObject()).getSuperClass() != null && !comboBoxSuperClass.getItems().get(0).toString().equals( ((DGeneralClass) DApp.selectedElement.getObject()).getSuperClass().getName()))
			{

				for (Element e : DApp.elements) {

					if (comboBoxSuperClass.getValue().equals(e.getObject().getName())) {
						((DGeneralClass) DApp.selectedElement.getObject()).setSuperClass((DGeneralClass) e.getObject());
						if ( DApp.selectedElement.endLines.isEmpty()) {
							DApp.drawCenteredLine( e, DApp.selectedElement);
						}

						DApp.select( e);
						DApp.updateOverlay();
						DApp.updateLines();
						DApp.updateArrow();
						DApp.updateZoomPane();
						DApp.select( null);
					}
				}
			}
		});
		HBox inheritanceHBox = new HBox();
		inheritanceHBox.getChildren().addAll(editInheritanceLabel, comboBoxSuperClass);


		VBox radioButtonVBox = new VBox();
		Label editInterfacesLabel = new Label("Edit Interfaces");

		radioButtonVBox.getChildren().add(editInterfacesLabel);
		for (Element e : DApp.elements) {
			if (e.getObject() instanceof DInterface) {
				RadioButton rb = new RadioButton(e.getObject().getName());
				rb.setOnAction(event -> {
					if (rb.isSelected()) {
						boolean contained = false;
						for ( int i = 0; i < ((DGeneralClass) DApp.selectedElement.getObject()).getInterfaces().size(); i++)
							if (((DGeneralClass) DApp.selectedElement.getObject()).getInterfaces().get(i).getName().equals(((DInterface) e.getObject()).getName())) {
								contained = true;
							}
						if ( !contained) {
							((DGeneralClass) DApp.selectedElement.getObject()).addInterface( (DInterface) e.getObject());
							DApp.drawCenteredDashedLine( e, DApp.selectedElement);
						}
					}
					else {
						((DGeneralClass) DApp.selectedElement.getObject()).removeInterface((DInterface) e.getObject());
						removeInterfaceLine( DApp.selectedElement, e);
					}
					DApp.updateOverlay();
					DApp.updateLines();
					DApp.updateArrow();
					DApp.updateZoomPane();

				});

				rb.setSelected( ( (DGeneralClass) DApp.selectedElement.getObject()).getInterfaces().contains(e.getObject()));
				radioButtonVBox.getChildren().add(rb);
			}
		}

		addPropertyButton.setOnAction( e -> {
			displayFieldOptions( DApp.selectedElement);
			window.close();
			editClassOptions();
		});

		removePropertyButton.setOnAction( e -> {
			for ( Node n : propertyVBox.getChildren()) {
				if ( n instanceof CheckBox) {
					CheckBox cb = (CheckBox)n;
					if ( cb.isSelected()) {
						for ( int i = 0; i < DApp.selectedElement.getObject().getProperties().size(); i++) {
							if ( DApp.selectedElement.getObject().getProperties().get(i).getName().equals(cb.getText()))
							{
								DApp.selectedElement.getObject().getProperties().remove(i);
								DApp.selectedElement.heightProperty.set(DApp.selectedElement.heightProperty.get() - DApp.size);
								DApp.selectedElement.updateObject();
							}
						}
					}

				}
			}
			DApp.selectedElement.updateObject();
			window.close();
			editClassOptions();
			DApp.selectedElement.updateObject();
		});

		addMethodButton.setOnAction(e -> {
			displayMethodOptions( DApp.selectedElement);
			window.close();
			editClassOptions();
		});

		removeMethodButton.setOnAction( e -> {
			for ( Node n : methodVBox.getChildren()) {
				if ( n instanceof CheckBox) {
					CheckBox cb = (CheckBox)n;

					if ( cb.isSelected()) {
						for ( int i = 0; i < DApp.selectedElement.getObject().getMethods().size(); i++) {
							if ( DApp.selectedElement.getObject().getMethods().get(i).getName().equals(cb.getText()))
							{
								DApp.selectedElement.getObject().getMethods().remove(i);
								DApp.selectedElement.heightProperty.set(DApp.selectedElement.heightProperty.get() - DApp.size);
							}
						}
					}

				}
			}
			DApp.selectedElement.updateObject();
			window.close();
			editClassOptions();
		});

		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.getChildren().addAll( editName, propertiesHBox, methodsHBox, inheritanceHBox, radioButtonVBox);

		Scene scene = new Scene( vbox);
		window.setScene( scene);
		window.show();
	}


	public static void editInterfaceOptions() {

		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.initStyle(StageStyle.DECORATED);
		window.setTitle("Edit Interface");
		window.setMinWidth(200);
		window.setMinHeight(160);


		Button removeMethodButton = new Button( "Remove Method");
		Button updateHierarchy = new Button( "Update Hierarchy");

		VBox methodVBox = new VBox();
		methodVBox.getChildren().add( new Label("Methods: "));
		for (DMethod dm : DApp.selectedElement.getObject().getMethods()) {
			methodVBox.getChildren().add(new Label(dm.getName()));
		}

		HBox methodsHBox = new HBox();
		methodsHBox.getChildren().addAll(methodVBox, removeMethodButton);

		Label editInheritanceLabel = new Label("Super Interfaces: ");
		ArrayList<CheckBox> otherInterfaces = new ArrayList<CheckBox>();

		for ( Element e : DApp.elements) {
			if ( e instanceof InterfaceElement
					&&	!DApp.selectedElement.getObject().getName().equals(e.getObject().getName())) {
				CheckBox cb = new CheckBox(e.getObject().getName());
				if ( e.getObject() instanceof DInterface) {
					cb.setSelected( ( (DInterface)DApp.selectedElement.getObject()).containsSuperInterface((DInterface)e.getObject()) );
				}
				otherInterfaces.add( cb);
			}
		}

		VBox interfacesVBox = new VBox();
		interfacesVBox.getChildren().add(editInheritanceLabel);
		for ( CheckBox c : otherInterfaces)
			interfacesVBox.getChildren().add(c);

		updateHierarchy.setOnAction( e -> {
			// reseting
			removeLine( DApp.selectedElement, true);
			( (DInterface)DApp.selectedElement.getObject()).getSuperInterfaces().clear();

			// reconfiguring
			for ( Node n : interfacesVBox.getChildren()) {

				if ( n instanceof CheckBox) {

					for (Element element : DApp.elements) {
						CheckBox c = (CheckBox)n;
						if ( element.getObject() instanceof DInterface && c.getText().equals(element.getObject().getName())) {
							if (c.isSelected()) {
								DApp.drawCenteredLine(element, DApp.selectedElement);
								( (DInterface)DApp.selectedElement.getObject()).getSuperInterfaces().add((DInterface)element.getObject());
							}

						}
					}

				}
			}
		});

		HBox inheritanceHBox = new HBox();
		inheritanceHBox.getChildren().addAll(interfacesVBox, updateHierarchy);


		VBox radioButtonVBox = new VBox();

		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.getChildren().addAll( methodsHBox, inheritanceHBox, radioButtonVBox);

		Scene scene = new Scene( vbox);
		window.setScene( scene);
		window.show();

	}

	public static void editNameOptions() {

		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.initStyle(StageStyle.DECORATED);
		window.setTitle("Edit Interface");
		window.setMinWidth(100);
		window.setMinHeight(80);

		TextField nameSlot = new TextField();
		Button changeName = new Button("Change Name");
		changeName.setOnAction( e -> {
			if ( ProjectManager.isSuitableName(nameSlot.getText()))
				DApp.selectedElement.getObject().setName(nameSlot.getText());
			else
				displayErrorMessage("invalid class name");
			DApp.selectedElement.updateObject();
			window.close();
		});

		FlowPane layout = new FlowPane();
		layout.getChildren().addAll( nameSlot, changeName);

		window.setScene(new Scene( layout));

		window.showAndWait();
	}

	public static DObject createInterface( TextField name, ChoiceBox<String> cb) {
		InterfaceElement r;
		DInterface object = new DInterface( name.getText());
		object.setName(name.getText());

		Random rand = new Random();
		if ( DApp.selectedElement != null)
			r = new InterfaceElement(  DApp.selectedElement.getLayoutX() + 30 + rand.nextInt(7) , DApp.selectedElement.getLayoutY()+ 30 + rand.nextInt(7),
					150, 150, Color.web(DApp.colors[0].substring(0, 1) + DApp.colors[rand.nextInt(10)].substring(1).toUpperCase(), 1.0), true);
		else
			r = new InterfaceElement( DApp.offset + 0 - Math.random()*DApp.RANDOMNESS, DApp.offset + 0 - Math.random()*DApp.RANDOMNESS, 150, 150,
					Color.web(DApp.colors[0].substring(0, 1) + DApp.colors[rand.nextInt(10)].substring(1).toUpperCase(), 1.0), true);

		ProjectManager.connectElement(r, object);
		DApp.elements.add(r);
		DApp.project.addObject(object);

		DApp.group.getChildren().add(r);

		DApp.select(r);
		DApp.updateZoomPane();

		return object;
	}



	/**
	 * This method gives options to the user to demonstrate a field in a class
	 * @param element
	 */
	public static void displayFieldOptions( Element element) {
		Button create;
		TextField name, type;

		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("New Property");
		window.setMinWidth(400);
		window.setMinHeight(400);

		Label messageName = new Label( "Name of the property:");
		name = new TextField();
		Label messageType = new Label( "Type of the property:");
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
			else if ( element.getObject() instanceof DInterface)
			{
				displayErrorMessage( "Cannot add a property to an Interface.");
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

	/**
	 * This method gives options to the user to demonstrate a method in a class
	 * @param element
	 */
	public static void displayMethodOptions( Element element) {
		Button create;
		TextField name, returnType;
		CheckBox cb2, cb3;
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
		cb2 = new CheckBox("static");
		cb3 = new CheckBox("no parameters");
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
		layout.getChildren().addAll( messageName, name, messageType, returnType, cb2, cb3, create);
		layout.setAlignment( Pos.CENTER);

		window.setScene( new Scene( layout));
		window.showAndWait();
	}

	/**
	 * This method displays the parameter options
	 * @param meth
	 * @param element
	 */
	public static void displayParameterOptions( DMethod meth, Element element) {
		Button addParam, addNew;
		ArrayList<Label> fields = null;
		ArrayList<TextField> params = new ArrayList<TextField>();

		Stage window = new Stage();
		VBox layout = new VBox();

		if (element.getObject() instanceof DGeneralClass)
		{
			fields = new ArrayList<Label>();
			for ( int i = 0; i < element.getObject().getProperties().size(); i++) {
				fields.add( new Label( element.getObject().getProperties().get(i).getType() + ":"
						+ element.getObject().getProperties().get(i).getName()));
			}
			for ( Label l : fields) {
				HBox h = new HBox();
				h.getChildren().add( l);
				h.getChildren().add( new CheckBox());
				layout.getChildren().add(h);
			}
		}

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

			for ( int i = 0; i < layout.getChildren().size(); i++) {

				if ( layout.getChildren().get(i) instanceof HBox) {
					HBox currentRow = (HBox)layout.getChildren().get(i) ;

					for ( int j = 0; j < currentRow.getChildren().size(); j++) {

						if ( currentRow.getChildren().get(j) instanceof CheckBox) {

							if ( ((CheckBox)currentRow.getChildren().get(j)).isSelected() ){

								for ( int k = 0; k <((Label)currentRow.getChildren().get(j-1)).getText().length(); k++){

									if ( ((Label)currentRow.getChildren().get(j-1)).getText().charAt(k) == ':') {

										meth.addParameter( ((Label)currentRow.getChildren().get(j-1)).getText().substring(k+1),
												((Label)currentRow.getChildren().get(j-1)).getText().substring(0,k));
									}
								}
							}
						}

					}
				}
			}

			addMethod(meth, element);
			window.close();
		});

		if (element.getObject() instanceof DGeneralClass)
		{
			for ( Label l : fields) {
				HBox h = new HBox();
				h.getChildren().add( l);
				h.getChildren().add( new CheckBox());
				layout.getChildren().add(h);
			}
		}


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

	/**
	 * This method displays error message in a different window
	 * @param message
	 */
	public static void displayErrorMessage(String message) {
		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.initStyle(StageStyle.UNDECORATED);
		window.setTitle("Error");
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

	/**
	 * This method adds property to the specified element
	 * @param name
	 * @param type
	 * @param element
	 */
	public static void addProperty( String name, String type, Element element) {
		DProperty prop = new DProperty( name, type);

		if( element.getObject() instanceof DGeneralClass)
		{
			if ( ((DGeneralClass)element.getObject()).getProperties().isEmpty()) {
				((DGeneralClass)element.getObject()).addProperty(prop);
				element.addField(prop);
			}
			else {
				boolean same = false;
				boolean taken = false;
				for ( int i = 0; i < ((DGeneralClass)element.getObject()).getProperties().size(); i++)
				{
					DProperty p = ((DGeneralClass)element.getObject()).getProperties().get(i);
					if (p.getName().equals(prop.getName())){
						taken = true;
					}
				}
				for ( int i = 0; i < ((DGeneralClass)element.getObject()).getProperties().size(); i++)
				{
					DProperty p = ((DGeneralClass)element.getObject()).getProperties().get(i);
					if ( prop.equals(p)) {
						same = true;
					}
				}
				if (same || taken)
					displayErrorMessage( "this variable already exists!");
				else {
					((DGeneralClass)element.getObject()).addProperty(prop);
					element.addField(prop);
				}
			}
		}

		if( element.getObject() instanceof DInterface)
		{
			displayErrorMessage( "Cannot add a property to an Interface.");
			//displayFieldOptions(element);
		}
	}

	/**
	 * This method adds methods to the specified element.
	 * @param m
	 * @param element
	 */
	public static void addMethod( DMethod m, Element element) {

		if ( element.getObject() instanceof DGeneralClass) {
			if ( ((DGeneralClass)element.getObject()).getProperties().isEmpty()) {
				element.getObject().addMethod(m);
				element.addMethod(m);
			}
			else {
				boolean same = false;

				for ( int i = 0; i < ((DGeneralClass)element.getObject()).getMethods().size(); i++)
				{
					DMethod meth = element.getObject().getMethods().get(i);
					if ( m.equals(meth)) {
						same = true;
					}
				}

				if (same )
					displayErrorMessage( "this method already exists!");
				else {
					element.getObject().addMethod(m);
					element.addMethod(m);
				}
			}
		}
		else if ( element.getObject() instanceof DInterface) {
			if ( ((DInterface)element.getObject()).getProperties().isEmpty()) {
				element.getObject().addMethod(m);
				element.addMethod(m);
			}
			else {
				boolean same = false;

				for ( int i = 0; i < ((DInterface)element.getObject()).getMethods().size(); i++)
				{
					DMethod meth = element.getObject().getMethods().get(i);
					if ( m.equals(meth)) {
						same = true;
					}
				}

				if (same )
					displayErrorMessage( "this method already exists!");
				else {
					element.getObject().addMethod(m);
					element.addMethod(m);
				}
			}
		}

	}

	/**
	 * This method extracts the all java code from uml scheme which made by user
	 * @param e
	 * @param prj
	 */
	public static void extractAll( ActionEvent e, DProject prj) {
		displayErrorMessage( "Project extracted successfully.");
		prj.extract("");
	}

	/**
	 * This method extracts the java code of only methods from uml scheme which made by user
	 * @param element
	 */
	public static void showCode( Element element) {
		Stage window = new Stage();

		DObject object = element.getObject();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Code of the class: " + object.getName());
		window.setMinWidth(200);
		window.setMinHeight(800);

		TextArea textArea = new TextArea();

		String code = "";
		for (String line : object.extract())
		{
			code = code + line + "\n";
		}

		textArea.setText(code);
		textArea.setPrefHeight(700);

		VBox layout = new VBox();
		layout.getChildren().addAll( textArea);
		layout.setAlignment( Pos.CENTER);

		window.setScene( new Scene( layout));
		window.showAndWait();
	}


	/**
	 * This method sets the line color for DApp
	 * @param color
	 */
	static void setLineColor( Paint color)
	{
		DApp.lineColor = color;
	}
	/**
	 * This method sets the background color for the program
	 * @param color
	 */
	static void setBackgroundColor(Paint color)
	{
		DApp.backgroundColor = color;
	}

	/**
	 * This method displays the constructor maker window
	 * @param element
	 */
	public static void displayConstructorMakerWindow( Element element) {
		Stage window = new Stage();
		Scene scene;
		window.initModality(Modality.APPLICATION_MODAL);
		window.initStyle(StageStyle.DECORATED);
		window.setTitle("Constructor Maker");
		window.setMinWidth(200);
		window.setMinHeight(160);

		ArrayList<HBox> consBoxes = new ArrayList<HBox>();
		if ( element.getObject() instanceof DClass) {
			String properties = new String(  " " + element.getObject().getName() + "(");
			HBox currentRow;
			if ( ((DClass) element.getObject()).getConstructors() != null) {
				for (DConstructor dcon : ((DClass) element.getObject()).getConstructors()) {

					currentRow = new HBox();

					for (DConstructorProperty p : dcon.getIncludedProperties()) {
						properties = properties + p.getProperty().getType() + " " + p.getProperty().getName() + ", ";
					}

					if ( dcon.getIncludedProperties().isEmpty())
						properties = properties + ")";
					else
						properties = properties.substring(0, properties.length() - 2) + ")";
					currentRow.getChildren().add(new Label(dcon.getAcccessability() + properties));
					properties = " " + element.getObject().getName() + "(";
					consBoxes.add(currentRow);
				}
			}
		}
		else {
			displayErrorMessage("Cannot add constructors to abstract classes or interfaces!");
			window.close();
		}
		//include properties & button part
		VBox propsVBox = new VBox();
		Button newConstructorButton = new Button( "Create Constructor");
		propsVBox.getChildren().add( new Label("Include Properties:"));
		ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
		for ( DProperty prop: element.getObject().getProperties()) {
			CheckBox checkBox = new CheckBox( prop.toString());
			checkBoxArrayList.add( checkBox);
			propsVBox.getChildren().add( checkBox);
		}
		newConstructorButton.setOnAction( event -> {
			DConstructor dConstructor = new DConstructor( (DClass) element.getObject());
			for (CheckBox cb : checkBoxArrayList) {
				if (cb.isSelected()) {
					for (int i = 0; i < element.getObject().getProperties().size(); i++) {
						if (element.getObject().getProperties().get(i).toString().equals( cb.getText()) ) {
							dConstructor.include( element.getObject().getProperties().get(i));
						}
					}
				}
			}
			((DClass) element.getObject()).getConstructors().add( dConstructor);
			window.close();
			displayConstructorMakerWindow( element);
		});
		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		for ( int i = 0; i < consBoxes.size(); i++)
			vbox.getChildren().add( consBoxes.get(i));

		vbox.getChildren().addAll( propsVBox, newConstructorButton);
		scene = new Scene(vbox);
		//((Group) scene.getRoot()).getChildren().addAll(vbox);

		window.setScene(scene);
		window.show();
	}
	/**
	 * This method displays remove options
	 * @param element
	 */
	public static void displayRemoveOptions( Element element) {
		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Are you sure");
		window.setMinWidth(200);
		window.setMinHeight(160);

		Label messageName = new Label( "Deleting " + DApp.selectedElement.getObject().getName() + "...\n" + "Are you sure?");

		Button ok = new Button("Yes");
		Button notOk = new Button("cancel");
		ok.setOnAction(e -> {
			removeObject(element);
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

	public static void removeObject(Element element)
	{
		DApp.select(null);

		//deleting overlay
		for ( int i =0; i < DApp.group.getChildren().size();i++) {
			if ( DApp.group.getChildren().get(i) instanceof Group && ((Group) DApp.group).getChildren().get(i) == DApp.overlay)
				DApp.group.getChildren().remove(DApp.group.getChildren().get(i));
		}
		// forgeting element
		DApp.elements.remove(element);
		// deleting element
		for ( int i =0; i < DApp.group.getChildren().size();i++) {
			if ( DApp.group.getChildren().get(i) instanceof Element && ((Element) DApp.group.getChildren().get(i)).interactable &&((Element)DApp.group.getChildren().get(i)).getObject().getName().equals( element.getObject().getName()))
				DApp.group.getChildren().remove(DApp.group.getChildren().get(i));
		}

		// removing from the classes that extended the removed class
		if ( element.getObject() instanceof DGeneralClass) {
			for ( DObject child : DApp.project.getObjects()) {
				if ( child instanceof DGeneralClass) {
					if ( ((DGeneralClass)child).getSuperClass() != null)  {
						if ( ((DGeneralClass)child).getSuperClass().getName().equals(((DGeneralClass)element.getObject()).getName())) {
							((DGeneralClass) child).setSuperClass(null);
						}
					}
				}
			}
		}
		else if ( element.getObject() instanceof DInterface) {
			for ( DObject child : DApp.project.getObjects()) {
				if ( child instanceof DInterface) {
					if ( ((DInterface)child).getSuperInterfaces().size() > 0)  {
						for ( int i = 0; i < ((DInterface)child).getSuperInterfaces().size(); i++) {
							DInterface superInterface = ((DInterface)child).getSuperInterfaces().get(i);
							if (  superInterface.getName().equals(((DInterface)element.getObject()).getName())) {
								((DInterface)child).getSuperInterfaces().remove(superInterface);
							}
						}
					}
				}
			}
		}

		// removing from project
		DApp.project.getObjects().remove(element.getObject());
		//deleting line from group
		removeLine( element, true);
		DApp.updateArrow();
		DApp.updateLines();
		DApp.updateOverlay();
		DApp.updateZoomPane();
	}

	public static void removeLine(Element element, boolean all)
	{
		for ( int i = 0; i < DApp.group.getChildren().size(); i++) {

			if ( DApp.group.getChildren().get(i) instanceof ComplexLine) {

				ComplexLine cl = (ComplexLine)DApp.group.getChildren().get(i);

				for ( int j =0; j < DApp.group.getChildren().size(); j++)
					if ( DApp.group.getChildren().get(j) instanceof ArrowHead ) {
						ArrowHead a = (ArrowHead)DApp.group.getChildren().get(j);

						if (  all && element.getEndLines().contains( a.getComplexLine()) )
						{
							DApp.group.getChildren().remove( a);
						}
						if ( all && element.getStartLines().contains( a.getComplexLine()) )
						{
							DApp.group.getChildren().remove( a);
						}
					}
				if ( element.startLines.contains(cl) )
				{
					DApp.group.getChildren().remove(cl);
				}
				if ( element.endLines.contains(cl) && all)
				{
					DApp.group.getChildren().remove(cl);
				}
				if ( DApp.project.getComplexLines().contains(cl))
				{
					DApp.project.removeComplexLine(cl);
				}
			}
			else if ( DApp.group.getChildren().get(i) instanceof DashedComplexLine && all ) {

				DashedComplexLine cdl = (DashedComplexLine)DApp.group.getChildren().get(i);

				for ( int j =0; j < DApp.group.getChildren().size();j++)
					if ( DApp.group.getChildren().get(j) instanceof ArrowHead ) {
						ArrowHead  a = (ArrowHead)DApp.group.getChildren().get(j);

						if (  element.getEndLines().contains( a.getComplexLine()) ||  element.getStartLines().contains( a.getComplexLine())){
							DApp.group.getChildren().remove( a);
						}
					}
				if ( element.startLines.contains(cdl) || element.endLines.contains(cdl))
					DApp.group.getChildren().remove(cdl);
				if ( DApp.project.getComplexLines().contains(cdl))
				{
					DApp.project.removeComplexLine(cdl);
				}
			}
		}
	}

	// used for editClassOptions removal of implemented interface
	public static void removeInterfaceLine(Element object, Element inf)
	{
		for ( int i =0; i < DApp.group.getChildren().size();i++) {
			if ( DApp.group.getChildren().get(i) instanceof DashedComplexLine) {

				DashedComplexLine cdl = (DashedComplexLine)DApp.group.getChildren().get(i);

				for ( int j =0; j < DApp.group.getChildren().size(); j++){
					if ( DApp.group.getChildren().get(j) instanceof ArrowHead ) {
						ArrowHead  a = (ArrowHead)DApp.group.getChildren().get(j);

						if (  inf.getEndLines().contains( a.getComplexLine()) ){
							DApp.group.getChildren().remove( a);
						}
					}
					if ( inf.endLines.contains(cdl))
						DApp.group.getChildren().remove(cdl);

				}
			}
		}
	}

	// used for editInterfaceOptions removal of super interface lines
	// Hopefully, we wont need this in the future!
	public static void removeSuperInterfaceLine(Element child, Element parent) {
		for ( int i =0; i < DApp.group.getChildren().size();i++) {
			if ( DApp.group.getChildren().get(i) instanceof ComplexLine) {
				ComplexLine cl = (ComplexLine)DApp.group.getChildren().get(i);

				for ( int j =0; j < DApp.group.getChildren().size(); j++){
					if ( DApp.group.getChildren().get(j) instanceof ArrowHead ) {
						ArrowHead  a = (ArrowHead)DApp.group.getChildren().get(j);

						if (  parent.getEndLines().contains( a.getComplexLine()) ){
							DApp.group.getChildren().remove( a);
						}
					}
					if ( parent.endLines.contains(cl))
						DApp.group.getChildren().remove(cl);

				}
			}
		}
	}

	/**
	 * This method displays the project options
	 */
	public static void saveProject() {

		try {

			if ( DApp.project.getName() != null && DApp.project.getSaveFile() != null)
			{
				saveProject(DApp.project.getSaveFile());
				displayErrorMessage("project saved!");
			}
			else
			{
				Stage window = new Stage();
				TextField t = new TextField();
				t.setText("New Project");

				window.initModality(Modality.APPLICATION_MODAL);
				window.setTitle("New Project");
				window.setMinWidth(200);
				window.setMinHeight(160);

				Label messageName = new Label( "Enter Project Name");

				Button save = new Button("Save Project");

				save.setOnAction(e -> {
					FileChooser fileChooser = new FileChooser();

					if (DApp.project.getName() == null)
						DApp.project.setName(t.getText());

					fileChooser.setInitialFileName(DApp.project.getName());
					fileChooser.getExtensionFilters().add(
							new FileChooser.ExtensionFilter("Diagrammatic Project File", "*.diag")
							);
					File selectedFile = fileChooser.showSaveDialog(window);
					if (selectedFile != null)
					{
						saveProject(selectedFile);
						DApp.project.setSaveFile(selectedFile);
						displayErrorMessage("project saved!");
					}

					window.close();
				});

				VBox layout = new VBox();
				if (DApp.project.getName() == null)
					layout.getChildren().addAll( messageName, t);
				layout.getChildren().add(save);
				layout.setAlignment( Pos.CENTER);

				window.setScene( new Scene( layout));
				window.showAndWait();

			}


		}
		catch (Exception exception) {
			exception.printStackTrace();
			displayErrorMessage("Error:Project wasn't saved successully");

		}

	}

	public static void displaySaveAsOptions() {
		Stage window = new Stage();
		TextField t = new TextField();
		t.setText("New Project");

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("New Project");
		window.setMinWidth(200);
		window.setMinHeight(160);

		Label messageName = new Label( "Enter Project Name");

		Button save = new Button("Save Project As");

		save.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();

			if (DApp.project.getName() == null)
				DApp.project.setName(t.getText());

			fileChooser.setInitialFileName(DApp.project.getName());
			fileChooser.getExtensionFilters().add(
					new FileChooser.ExtensionFilter("Diagrammatic Project File", "*.diag")
					);
			File selectedFile = fileChooser.showSaveDialog(window);
			if (selectedFile != null)
			{
				saveProject(selectedFile);
				DApp.project.setSaveFile(selectedFile);
				displayErrorMessage("project saved!");
			}

			window.close();
		});

		VBox layout = new VBox();
		if (DApp.project.getName() == null)
			layout.getChildren().addAll( messageName, t);
		layout.getChildren().add(save);
		layout.setAlignment( Pos.CENTER);

		window.setScene( new Scene( layout));
		window.showAndWait();
	}

	public static void displayProjectOptions() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Save your project");
		alert.setHeaderText("Do you want to save your current project?");
		alert.setContentText("You will lose unsaved progress");

		ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
		ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(okButton, noButton, cancelButton);

		//Optional<ButtonType> result = alert.showAndWait();

		alert.showAndWait().ifPresent(type -> {
			if (type.getButtonData() == ButtonData.YES) {
				saveProject();
			}
			else if (type.getButtonData() == ButtonData.NO) {

			}
		});

		DApp.project = new DProject();
		cleanProjectView();

	}

	/**
	 * This method allows user to save the project
	 */
	static void saveProject(File f) {
		try {
			FileWriter oFile = new FileWriter( f);
			for(String line : DApp.project.projectToText())
			{
				oFile.write(line);
				oFile.write("\n");
			}
			oFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			displayErrorMessage("INVALID PROJECT NAME");
		}

		DApp.project.setSaveFile(f);
	}

	private static void loadProject(File f) {

		ArrayList<String> projectLines = new ArrayList<String>();
		try {
			Scanner myReader = new Scanner(f);
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				projectLines.add(line);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		DProject loadedProject = ProjectManager.textToProject(projectLines);
		loadedProject.setSaveFile(f);
		DApp.project = loadedProject;

	}

	/**
	 * This method clears te view of the project by removing them
	 */
	public static void cleanProjectView() {
		DApp.select(null);

		// forgeting all elements
		DApp.elements.clear();

		// removing from project
		DApp.project.getObjects().clear();

		Element cornerNW = new Element( -100, -100, 1, 1, Color.GOLD, false);
		Element cornerNE = new Element( 10000, -100, 1, 1, Color.GOLD, false);
		Element cornerSW = new Element( -100, 10000, 1, 1, Color.GOLD, false);
		Element cornerSE = new Element( 10000, 10000, 1, 1, Color.GOLD, false);

		DApp.group.getChildren().removeAll(DApp.group.getChildren());

		DApp.group.getChildren().addAll( cornerNE, cornerNW, cornerSE, cornerSW, DApp.closest);

		DApp.updateArrow();
		DApp.updateLines();
		DApp.updateOverlay();
		DApp.updateZoomPane();

	}

	public static void displayLoadOptions() {
		Button load;

		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Open Project");
		window.setMinWidth(400);

		load = new Button("Load Project");

		load.setOnAction( e -> {
			try {
				FileChooser fileChooser = new FileChooser();
				File selectedFile = fileChooser.showOpenDialog(window);
				cleanProjectView();
				loadProject(selectedFile);
				DApp.updateArrow();
				DApp.updateLines();
				DApp.updateOverlay();
				DApp.updateZoomPane();
				for ( int i = 0; i < DApp.elements.size(); i++)
					DApp.elements.get(i).updateObject();
				displayErrorMessage("Project Loaded Successfully!");

			} catch (NullPointerException n) {
				n.printStackTrace();
				displayErrorMessage("Project Wasn't Loaded Successfully!");
			}
			window.close();
		});



		VBox layout = new VBox();
		layout.getChildren().addAll(load);
		layout.setAlignment( Pos.CENTER);

		window.setScene( new Scene( layout));
		window.showAndWait();
	}

}
