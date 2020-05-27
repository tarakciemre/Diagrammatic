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

	public ArrayList<DInterface> getInterfaces()
	{
		return interfaces;
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
		return name;
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