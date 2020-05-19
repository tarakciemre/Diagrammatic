//package com.company;

/*
 * DConstructor class that holds constructors for DObject.
 * @version 26.04.2020
 */
package logic.tools;

import java.util.ArrayList;
import logic.object_source.*;
import logic.interfaces.*;

public class DConstructor implements Extractable, Accesible
{
	private ArrayList<DConstructorProperty> properties;
	private String className;
	private String  accesibility;

	public DConstructor(DGeneralClass c)
	{
		properties = new ArrayList<DConstructorProperty>();
		for(int i = 0; i < c.getProperties().size(); i++)
		{
			properties.add(new DConstructorProperty( c.getProperties().get(i), true) );
		}
		className = c.getName();
		accesibility = ProjectManager.DEFAULT;
	}

	public ArrayList<String> extract()
	{
		ArrayList<String> lines = new ArrayList<String>();
		String firstLine = "";
		firstLine = "public " + className + "( ";
		for( int i = 0; i < properties.size(); i++)
		{
			DConstructorProperty cp = properties.get(i);
			if( cp.isIncluded())
			{
				DProperty p = cp.getProperty();
				firstLine += p.getType() + " " + p.getName() + ", ";
			}
		}
		firstLine = firstLine.substring(0, firstLine.length() - 2);
		firstLine += ")";

		lines.add( firstLine);

		lines.add( "{");

		for( int i = 0; i < properties.size(); i++)
		{
			DConstructorProperty cp = properties.get(i);
			if( cp.isIncluded())
			{
				DProperty p = cp.getProperty();
				lines.add( "\tthis." + p.getName() + " = " + p.getName() + ";");
			}
		}

		lines.add( "}");

		return lines;
	}

	//String pName yerine DConstructorProperty gelebilir mi?
	public void setIncluded( String pName, boolean included)
	{
		for( int i = 0; i < properties.size(); i++)
		{
			if( properties.get(i).getProperty().getName().equals(pName))
				properties.get(i).setIncluded(included);
		}
	}

	public String toString()
	{
		String output;
		output = "";
		for( DConstructorProperty prop: properties)
		{
			output = output + prop.isIncluded() + " ";
		}
		output = output.substring(0, output.length() - 1);
		return output;
	}

	public ArrayList<DConstructorProperty> getProperties()
	{
		return properties;
	}


	public String getAcccessability() {
		return accesibility;
	}


	public void setAccessability( String s) {
		if ( s.equals(ProjectManager.PROTECTED) || s.equals(ProjectManager.PUBLIC) || s.equals(ProjectManager.DEFAULT) || s.equals(ProjectManager.PRIVATE) )
			accesibility = s;

	}

}



