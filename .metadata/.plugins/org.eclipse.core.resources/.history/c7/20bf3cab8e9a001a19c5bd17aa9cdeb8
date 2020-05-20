//package com.company;
package logic.tools;

import logic.interfaces.*;

public class DConstructorProperty implements Accesible
{
	private DProperty property;
	private boolean included;
	private String accesibility;

	//constructor with two parameters given
	public DConstructorProperty( DProperty property, boolean included)
	{
		this.property = property;
		this.included = included;
	}

	//Getters and setters
	public DProperty getProperty()
	{
		if (included)
			return property;
		return new DProperty( "a", "a");
	}

	public boolean isIncluded()
	{
		return included;
	}

	public void setProperty(DProperty property)
	{
		this.property = property;
	}

	public void setIncluded(boolean included)
	{
		this.included = included;
	}

	public String extract() {
		return getProperty().extract();
	}

	public String toString() { return property.getName(); }

	public String getAcccessability() {
		return accesibility;
	}


	public void setAccessability( String s) {
		if ( s.equals(ProjectManager.PROTECTED) || s.equals(ProjectManager.PUBLIC) || s.equals(ProjectManager.DEFAULT) || s.equals(ProjectManager.PRIVATE) )
			accesibility = s;

	}

}