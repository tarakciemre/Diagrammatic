package logic.object_source;

import java.util.ArrayList;
import logic.tools.*;

public class DGeneralClass extends DObject {

	ArrayList<DInterface> interfaces;
	DGeneralClass superClass;

	// Properties

	// Constructors
	public DGeneralClass( String name) {
		this.name = name.trim();
		interfaces = new ArrayList<DInterface>();
		setMethods(new ArrayList<DMethod>());
		setProperties(new ArrayList<DProperty>());
	}

	//A CONSTRUCTOR IS NEEDED HERE WITH "SUPER" PARAMETER

	// Methods


	public ArrayList<String> extract()
	{
		return null;
	}

	public void addProperty( String name, String type) {
		DProperty n = new DProperty( name, type);
		getProperties().add(n);
	}

	public void addProperty( DProperty p) {
		getProperties().add(p);
	}

	public void addProperties( ArrayList<DProperty> ps) {
		for ( DProperty p : ps)
			getProperties().add(p);
	}

	public void removeProperty( String name) {
		for ( DProperty p : getProperties()) {
			if ( p.getName().equals(name))
				getProperties().remove(p);
		}
	}

	public void removeProperty( DProperty p) {
		getProperties().remove(p);

	}

	public void removeProperties( ArrayList<DProperty> ps) {
		for ( DProperty p : ps) {
			for ( int i = 0; i < getProperties().size(); i++) {
				if ( getProperties().get(i).getName().equals(p.getName()) && getProperties().get(i).getType().equals(p.getType()))
					getProperties().remove( getProperties().get(i));
			}
		}
	}

	public void removeAllFieldsType( String type) {
		for ( DProperty p : getProperties()) {
			if ( p.getType().equals(type))
				getProperties().remove(p);
		}
	}

	public void addMethod( DMethod m) {
		getMethods().add(m);
	}

	public void addMethods( ArrayList<DMethod> ms) {
		for ( DMethod m : ms)
			getMethods().add(m);
	}

	public void removeAllMethodsNamed( String name) {
		for (DMethod m : getMethods()) {
			if ( m.getName().equals(name))
				getMethods().remove(m);
		}
	}

	public void removeMethod( String name, String returnType, ArrayList<DProperty> parameters) {
		for ( DMethod m : getMethods()) {
			if ( m.getName().equals(name) && m.getReturnType().equals(returnType) && m.getParameters().equals(parameters))
				getMethods().remove(m);
		}
	}

	public void removeMethod( DMethod m) {
		getMethods().remove(m);
	}

	public void addInterface(DInterface di)
	{
		interfaces.add(di);
	}

	public void removeInterface(DInterface di)
	{
		interfaces.remove(di);
	}

	public void setSuperClass( DGeneralClass dg)
	{
		superClass = dg;
	}

	public String toString()
	{
		return "You shouldn't have created such an object";
	}
}