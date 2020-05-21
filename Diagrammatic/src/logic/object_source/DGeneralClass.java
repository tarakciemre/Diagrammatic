package logic.object_source;

import java.util.ArrayList;

import logic.tools.DMethod;
import logic.tools.DProperty;

public class DGeneralClass extends DObject {

	ArrayList<DInterface> interfaces;
	DGeneralClass superClass;

	// Properties

	// Constructors
	/**
	 * @param name
	 */
	public DGeneralClass( String name) {
		this.name = name.trim();
		interfaces = new ArrayList<DInterface>();
		setMethods(new ArrayList<DMethod>());
		setProperties(new ArrayList<DProperty>());
	}

	//A CONSTRUCTOR IS NEEDED HERE WITH "SUPER" PARAMETER

	// Methods


	/**
	 *
	 */
	public ArrayList<String> extract()
	{
		return null;
	}

	/**
	 * @param name
	 * @param type
	 */
	public void addProperty( String name, String type) {
		DProperty n = new DProperty( name, type);
		getProperties().add(n);
	}

	/**
	 * @param p
	 */
	public void addProperty( DProperty p) {
		getProperties().add(p);
	}

	/**
	 * @param ps
	 */
	public void addProperties( ArrayList<DProperty> ps) {
		for ( DProperty p : ps)
			getProperties().add(p);
	}

	/**
	 * @param name
	 */
	public void removeProperty( String name) {
		for ( DProperty p : getProperties()) {
			if ( p.getName().equals(name))
				getProperties().remove(p);
		}
	}

	/**
	 * @param p
	 */
	public void removeProperty( DProperty p) {
		getProperties().remove(p);

	}

	/**
	 * @param ps
	 */
	public void removeProperties( ArrayList<DProperty> ps) {
		for ( DProperty p : ps) {
			for ( int i = 0; i < getProperties().size(); i++) {
				if ( getProperties().get(i).getName().equals(p.getName()) && getProperties().get(i).getType().equals(p.getType()))
					getProperties().remove( getProperties().get(i));
			}
		}
	}

	/**
	 * @param type
	 */
	public void removeAllFieldsType( String type) {
		for ( DProperty p : getProperties()) {
			if ( p.getType().equals(type))
				getProperties().remove(p);
		}
	}

	/**
	 * @param m
	 */
	public void addMethod( DMethod m) {
		getMethods().add(m);
	}

	/**
	 * @param ms
	 */
	public void addMethods( ArrayList<DMethod> ms) {
		for ( DMethod m : ms)
			getMethods().add(m);
	}

	/**
	 * @param name
	 */
	public void removeAllMethodsNamed( String name) {
		for (DMethod m : getMethods()) {
			if ( m.getName().equals(name))
				getMethods().remove(m);
		}
	}

	/**
	 * @param name
	 * @param returnType
	 * @param parameters
	 */
	public void removeMethod( String name, String returnType, ArrayList<DProperty> parameters) {
		for ( DMethod m : getMethods()) {
			if ( m.getName().equals(name) && m.getReturnType().equals(returnType) && m.getParameters().equals(parameters))
				getMethods().remove(m);
		}
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
	public void addInterface(DInterface di)
	{
		interfaces.add(di);
	}

	/**
	 * @param di
	 */
	public void removeInterface(DInterface di)
	{
		interfaces.remove(di);
	}

	/**
	 * @param dg
	 */
	public void setSuperClass( DGeneralClass dg)
	{
		superClass = dg;
	}

	/**
	 * @return
	 */
	public DGeneralClass getSuperClass() {
		return superClass;
	}

	/**
	 *
	 */
	public String toString()
	{
		return "You shouldn't have created such an object";
	}

	public ArrayList<String> classToString() {
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("You shouldn't have created such an object");
		return lines;
	}

	//Best recursive method
	@Override
	public int degree()
	{
		if (superClass == null)
			return 0;
		else
			return 1 + superClass.degree();
	}
}