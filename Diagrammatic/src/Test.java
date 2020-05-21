//import dpackage.*;
import java.util.ArrayList;

import logic.object_source.*;
import logic.tools.*;

public class Test {

	// for stage 1
	static DProject project;
	static DGeneralClass cg;
	static DInterface i;
	static DInterface i2;
	static DInterface i3;
	static DClass c;
	static DClass c2;
	static DClass c3;
	static DAbstractClass ac;
	static DAbstractClass ac2;
	static DAbstractClass ac3;
	static DMethod m;
	static DProperty p;
	static DConstructor con;

	// for stage 2
	static DProperty num;
	static DProperty taste;
	static DProperty withPancake;
	static DProperty jakeMadeIt;
	static DMethod getEaten;
	static DMethod setEaten;
	static int cardinality;

	/**
	 * main method
	 * @param args
	 */
	public static void main( String[] args) {
		init();

		// TO TEST SOMETHING, UNCOMMENT THE NECESSARY LINE BELOW:
		//dMethodTest();
		//dClassTest();
		//dAbstractTest();
		//dExtractTest();
		//translateTest();
		dProjectTest();
	}

	// Test Constructor
	/**
	 * test method for constructor
	 */
	public static void testConstructor()
	{

	}

	/**
	 * This method initializes the objects in the program
	 */
	public static void init()
	{
		project = new DProject("TestProject");
		i = new DInterface("Interactable");
		c = new DClass("Bacon");
		ac = new DAbstractClass("AbstractTest");
		i2 = new DInterface("Interactable2");
		c2 = new DClass("Bacon2");
		ac2 = new DAbstractClass("AbstractTest2");
		i3 = new DInterface("Interactable3");
		c3 = new DClass("Bacon3");
		ac3 = new DAbstractClass("AbstractTest3");
		c.addInterface(i);
		c.addInterface(i2);
		m = new DMethod("isEven", "int", false);
		p = new DProperty("dassein", "Dassein");

		c.setSuperClass(c2);
		c2.setSuperClass(ac);

		ac2.setSuperClass(ac3);

		i.addSuperInterface(i2);
		i.addSuperInterface(i3);

		project.addObject(i);
		project.addObject(i2);
		project.addObject(i3);
		project.addObject(c);
		project.addObject(c2);
		project.addObject(c3);
		project.addObject(ac);
		project.addObject(ac2);
		project.addObject(ac3);
	}

	/**
	 * this method tests the dmethods
	 */
	public static void dMethodTest()
	{
		num = new DProperty( "number", "int");
		m.addParameter(num);

		System.out.println("{DMethod} "         +  m);
		System.out.println();
	}

	/**
	 * test method for dclasses
	 */
	public static void dClassTest()
	{
		taste = new DProperty( "taste", "Taste");
		withPancake = new DProperty( "withPancake", "boolean");
		jakeMadeIt = new DProperty( "jakeMadeIt", "boolean");
		getEaten = new DMethod( "getEaten", "boolean", false);
		setEaten = new DMethod( "setEaten", "void", false);
		setEaten.addParameter(taste);
		setEaten.addParameter(withPancake);

		ArrayList<DProperty> forBacon = new ArrayList<DProperty>();
		forBacon.add(taste);
		forBacon.add(withPancake);

		c.addProperties( forBacon);

		ac.addProperty(jakeMadeIt);
		ac.addMethod(getEaten);
		c.addProperty(jakeMadeIt);
		c.addMethod(getEaten);
		c.addMethod(setEaten);

		c.addConstructor( new DConstructor(c));

	}

	/**
	 *
	 */
	public static void dAbstractTest()
	{
		cardinality = 5;
		for ( int k = 0; k < cardinality; k++) {
			ac.addProperty( new DProperty( "element"+k, "Number"));
		}

	}

	/**
	 *
	 */
	public static void dInterfaceTest()
	{
		System.out.println();
		for ( int k = 0; k < cardinality; k++) {
			i.addMethod( new DMethod( "setEaten", "void", false));
		}
	}

	/**
	 *
	 */
	public static void dProjectTest()
	{
		dClassTest();
		dInterfaceTest();
		dAbstractTest();

		System.out.println(" {DProject}");
		ArrayList<String> pt = project.projectToText();

		for(int i = 0; i < pt.size(); i ++)
		{
			System.out.println(pt.get(i));
		}

		DProject project2 = ProjectManager.textToProject(pt);
		ArrayList<String> pt2 = project2.projectToText();

		for(int i = 0; i < pt2.size(); i ++)
		{
			System.out.println(pt2.get(i));
		}

		project2.extract("");
	}

	/**
	 *
	 */
	public static void dExtractTest()
	{
		for( int k = 0; k < c.extract().size(); k++)
			System.out.println( c.extract().get(k));

		for( int k = 0; k < ac.extract().size(); k++)
			System.out.println( ac.extract().get(k));
	}

	/**
	 *
	 */
	public static void translateTest()
	{
		DClass copydc = ProjectManager.textToClass( c.classToString(), project);

		for( int k = 0; k < copydc.extract().size(); k++)
			System.out.println( copydc.extract().get(k));
	}
}