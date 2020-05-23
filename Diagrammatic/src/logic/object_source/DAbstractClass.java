package logic.object_source;

import logic.tools.*;
import java.util.ArrayList;

public class DAbstractClass extends DGeneralClass {

	private ArrayList<DConstructor> constructors;

	// Constructors
	/**
	 * @param name
	 */
	public DAbstractClass( String name) {
		super( name);
	}

	// Methods

	/**
	 *
	 */
	@Override
	public ArrayList<String> extract() {
		ArrayList<String> lines = new ArrayList<String>();

		lines.add("public abstract class " + this.getName());
		lines.add("{");
		lines.add("");
		lines.add( "\t// Properties");
		lines.add("");
		for ( int i = 0; i < this.getProperties().size(); i++)
			lines.add( "\tprivate " + this.getProperties().get(i).extract());
		lines.add("");
		lines.add( "\t// Methods");
		lines.add( "");
		for( int i = 0; i < getMethods().size(); i++)
		{
			String line = "";
			line += "\tpublic abstract" + getMethods().get(i).extract().get(0).substring( 6) + "{};";

			lines.add( line);
		}
		lines.add( "");
		lines.add( "}");

		return lines;
	}

	/**
	 * @param dcon
	 */
	public void addConstructor( DConstructor dcon)
	{
		constructors.add( dcon);
	}

	/**
	 * @return
	 */
	@Override
	public ArrayList<String> classToString()
	{
		ArrayList<String> output;

		output = new ArrayList<String>();
		output.add("ABS: " + getName());
		output.add("");
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

		output.add(getElement().elementToString());
		output.add("");

		output.add("END");

		return output;
	}
}