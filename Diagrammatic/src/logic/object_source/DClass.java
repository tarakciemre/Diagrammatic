package logic.object_source;

import java.util.ArrayList;
import logic.tools.*;

public class DClass extends DGeneralClass {

	// Properties
	private ArrayList<DConstructor> constructors;

	// Constructors
	/**
	 * @param name
	 */
	public DClass( String name) {
		super( name);
		constructors = new ArrayList<DConstructor>();
	}

	// Methods
	/**
	 * @param dcon
	 */
	public void addConstructor( DConstructor dcon)
	{
		constructors.add( dcon);
	}

	/**
	 *
	 */
	@Override
	public ArrayList<String> extract() {
		ArrayList<String> lines = new ArrayList<String>();
		String first = "public class " + getName();
		if (superClass != null)
			first += " extends " + superClass.getName();

		if (interfaces.size() > 0)
		{
			first += " implements ";
			for (DInterface di : interfaces)
			{
				first += di.getName() + ", ";
			}
			first = first.substring(0, first.length() - 2);
		}

		lines.add( first);
		lines.add( "{");
		lines.add( "");

		lines.add( "\t//Properties");
		lines.add( "");
		for( int i = 0; i < getProperties().size(); i++)
		{
			lines.add( "\tprivate " + getProperties().get(i).extract());
		}
		lines.add( "");

		lines.add( "\t//Constructors");
		lines.add( "");


		for( int i = 0; i < constructors.size(); i++)
		{
			for ( int j = 0; j < constructors.get(i).extract().size(); j++) {
				lines.add( "\t" + constructors.get(i).extract().get(j));
			}
			lines.add( "");
		}
		lines.add( "");

		lines.add( "\t//Methods");
		lines.add( "");
		for( int i = 0; i < getMethods().size(); i++)
		{
			for( int a = 0; a < getMethods().get(i).extract().size(); a++)
			{
				lines.add( "\t" + getMethods().get(i).extract().get(a));
			}
			lines.add( "");
		}
		lines.add( "");

		lines.add( "}");

		return lines;
	}

	/**
	 * @return
	 */
	@Override
	public ArrayList<String> classToString()
	{
		ArrayList<String> output;

		output = new ArrayList<String>();
		output.add( "CLA: " + getName());
		output.add( "");
		if (superClass != null)
		{
			output.add( "EXT " + getSuperClass().getName());
			output.add( "");
		}
		if (!interfaces.isEmpty())
		{
			String interfaceLine = "IMP ";
			for (DInterface di : interfaces)
			{
				interfaceLine += di.getName() + ",";
			}
			interfaceLine = interfaceLine.substring(0, interfaceLine.length() - 1);
			output.add(interfaceLine);
			output.add( "");
		}

		if (!getProperties().isEmpty())
		{
			for( DProperty prop: getProperties())
			{
				output.add("PRO " + prop);
			}
			output.add("");
		}
		if (!getMethods().isEmpty())
		{
			for( DMethod meth: getMethods())
			{
				output.add("MET " + meth);
			}
			output.add("");
		}

		if (!constructors.isEmpty())
		{
			for( DConstructor cons: constructors)
			{
				output.add("CON " + cons);
			}
			output.add("");
		}

		output.add(getElement().elementToString());
		output.add("");

		output.add("END");

		return output;
	}

	/**
	 * @return
	 */
	public ArrayList<DConstructor> getConstructors() {
		return constructors;
	}

}