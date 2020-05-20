package logic.object_source;

import java.util.ArrayList;
import logic.tools.*;

public class DInterface extends DObject {

	ArrayList<DInterface> superInterfaces;

	// Properties

	// Constructors
	/**
	 * @param name
	 */
	public DInterface( String name) {
		this.name = name;
		superInterfaces = new ArrayList<DInterface>();
		setMethods(new ArrayList<DMethod>());
	}

	//A CONSTRUCTOR IS NEEDED HERE WITH "SUPER" PARAMETER

	// Methods
	/**
	 *
	 */
	@Override
	public String toString() {
		// !EDIT THIS!
		String str = "Interface " + name + " with methods:\n";

		for ( int i = 0; i < getMethods().size() - 1; i++)
			str += getMethods().get(i) + ", ";
		if ( getMethods().size() > 0)
			str += getMethods().get(getMethods().size() - 1);

		return str;
	}

	/**
	 * @param methods
	 */
	public void addMethods( DMethod... methods) {
		for ( int i = 0; i < methods.length; i++)
			addMethod( methods[i]);
	}

	/**
	 * @param m
	 */
	public void addMethod( DMethod m) {
		getMethods().add(m);
	}

	/**
	 * @param m
	 */
	public void removeMethod( DMethod m) {
		getMethods().remove(m);
	}

	/**
	 * @param di
	 */
	public void addSuperInterface( DInterface di)
	{
		superInterfaces.add(di);
	}

	/**
	 * @return
	 */
	public ArrayList<DInterface> getSuperInterface() {
		return superInterfaces;
	}
	/**
	 * @param di
	 */
	public void removeSuperInterface( DInterface di)
	{
		superInterfaces.remove(di);
	}

	/**
	 *
	 */
	@Override
	public ArrayList<String> extract() {
		ArrayList<String> lines = new ArrayList<String>();
		String first = "public interface " + this.getName();
		if (superInterfaces.size() > 0)
		{
			first += "extends ";
			for (DInterface di : superInterfaces)
			{
				first += di.getName() + ", ";

			}
			first = first.substring(0, first.length() - 2);
		}

		lines.add(first);
		lines.add("{");
		lines.add("");

		lines.add( "\t// Methods");
		lines.add( "");
		for( int i = 0; i < getMethods().size(); i++)
		{
			String line = "";
			line += "\tpublic abstract" + getMethods().get(i).extract().get(0).substring( 6) + "{};";

			lines.add( line);
		}

		lines.add("");
		lines.add( "}");
		return lines;
	}

	/**
	 * @return
	 */
	public ArrayList<String> classToString()
	{
		ArrayList<String> output;

		output = new ArrayList<String>();
		output.add("INT: " + getName());
		output.add("");
		output.add( "EXT");
		output.add( "");
		if (getMethods().size() > 0)
		{
			for( DMethod meth: getMethods())
			{
				output.add("MET " + meth);
			}
			output.add("");
		}

		output.add("END");

		return output;
	}



}