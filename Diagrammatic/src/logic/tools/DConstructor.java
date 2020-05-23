//package com.company;

/*
 * DConstructor class that holds constructors for DObject.
 * @version 26.04.2020
 */
package logic.tools;

import java.util.ArrayList;
import logic.object_source.*;
import logic.interfaces.*;

public class DConstructor implements Extractable, Accessible
{
	private ArrayList<DConstructorProperty> properties;
	private String className;
	private String  accesibility;
	/**
	 * @param c
	 */
	public DConstructor(DGeneralClass c)
	{
		properties = new ArrayList<DConstructorProperty>();
		for(int i = 0; i < c.getProperties().size(); i++)
		{
			properties.add(new DConstructorProperty( c.getProperties().get(i), false) );
		}
		className = c.getName();
		accesibility = ProjectManager.DEFAULT;
	}

	/**
	 *
	 */
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
	/**
	 * @param pName
	 * @param included
	 */
	public void setIncluded( String pName, boolean included)
	{
		for( int i = 0; i < properties.size(); i++)
		{
			if( properties.get(i).getProperty().getName().equals(pName))
				properties.get(i).setIncluded(included);
		}
	}

	/**
	 *
	 */
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

	/**
	 * @return
	 */
	public ArrayList<DConstructorProperty> getIncludedProperties()
	{
		ArrayList<DConstructorProperty> out = new ArrayList<DConstructorProperty>();
		for (DConstructorProperty p : properties)
		{
			if (p.isIncluded() )
				out.add( p);
		}
		return out;
	}

	public ArrayList<DConstructorProperty> getProperties()
	{
		ArrayList<DConstructorProperty> out = new ArrayList<DConstructorProperty>();
		for (DConstructorProperty p : properties)
		{
			out.add(p);
		}
		return out;
	}

	/**
	 * @param dcp
	 */
	public void addProperty( DConstructorProperty dcp)
	{
		properties.add( dcp);
	}

	/**
	 *
	 */
	public String getAcccessability() {
		return accesibility;
	}


	/**
	 *
	 */
	public void setAccessability( String s) {
		if ( s.equals(ProjectManager.PROTECTED) || s.equals(ProjectManager.PUBLIC) || s.equals(ProjectManager.DEFAULT) || s.equals(ProjectManager.PRIVATE) )
			accesibility = s;

	}

}



