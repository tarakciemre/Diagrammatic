//package com.company;
package logic.tools;

import logic.interfaces.*;

/**
 * An object for the properties of classes
 * to extract each item
 * @version 26.04.2020
 */
public class DProperty implements Accesible{

	// Properties
	private String name;
	private String type;
	private String accesibility;

	// Constructors
	public DProperty(String name, String type) {
		this.type = type;
		this.name = name.trim();
	}

	// Methods
	public String toString() {
		return name + "," + type;
	}

	//Getters and Setters
	public String getName() {
		return name;
	}

	public String getType()  {
		return type;
	}

	public int setName(String name) {
		this.name = name;
		return 1; //should return something else if empty
	}

	public int setType(String type) {
		this.type = type;
		return 1; //should return something else if empty
	}

	public String extract()
	{
		return type + " " + name + ";";
	}


	public String getAcccessability() {
		return accesibility;
	}


	public void setAccessability( String s) {
		if ( s.equals(ProjectManager.PROTECTED) || s.equals(ProjectManager.PUBLIC) || s.equals(ProjectManager.DEFAULT) || s.equals(ProjectManager.PRIVATE) )
			accesibility = s;

	}

}