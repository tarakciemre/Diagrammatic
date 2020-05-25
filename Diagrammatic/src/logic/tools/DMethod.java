package logic.tools;

import java.util.ArrayList;
import logic.interfaces.Accessible;

public class DMethod implements Accessible{

	// Properties
	private String name;
	private String returnType;
	private boolean isStatic;
	private ArrayList<DProperty> parameters;

	private String accesibility;

	// Constructors
	/**
	 *
	 */
	public DMethod() {
		name = "";
		returnType = "void";
		parameters = new ArrayList<DProperty>();
	}

	/**
	 * @param name
	 * @param returnType
	 * @param isStatic
	 */
	public DMethod(String name, String returnType, boolean isStatic) {
		parameters = new ArrayList<DProperty>();
		this.returnType = returnType.trim();
		this.name = name.trim();
		this.isStatic = isStatic;
		accesibility = ProjectManager.DEFAULT;
	}

	// Methods
	/**
	 *
	 */
	public String toString() {

		String output;
		output = name + " " + returnType + " " + isStatic + " ";
		for( DProperty param: parameters)
		{
			output = output + param + "!";
		}
		output = output.substring(0, output.length() - 1);
		return output;

		//IDK WHat this is
		/*String str;
      String rtn;

      if ( returnType.equals("void"))
         rtn = "no return function";
      else
         rtn = "returns type " + returnType;

      str = name + " " + rtn + " " + " taking parameters : ";

      for ( int i = 0; i < parameters.size() - 1; i++)
         str += parameters.get(i) + ", ";
      if ( parameters.size() > 0)
         str += parameters.get(parameters.size() - 1);

      return str;
		 */
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @return
	 */
	public boolean getStatic()
	{
		return isStatic;
	}

	/**
	 * @param isStatic
	 */
	public void setStatic( boolean isStatic)
	{
		this.isStatic = isStatic;
	}

	/**
	 * @param name
	 */
	public void setName( String name) {
		this.name = name;
	}

	/**
	 * @param returnType
	 */
	public void setReturnType( String returnType) {
		this.returnType = returnType;
	}

	/**
	 * @param order
	 * @return
	 */
	public DProperty getParameter( int order) {
		return parameters.get( order);
	}

	/**
	 * @return
	 */
	public ArrayList<DProperty> getParameters() {
		return parameters;
	}

	/**
	 * @param name
	 * @param type
	 */
	public void addParameter( String name, String type) {
		DProperty dp = new DProperty( name, type);
		parameters.add( dp);
	}

	/**
	 * @param parameter
	 */
	public void addParameter( DProperty parameter) {
		parameters.add( parameter);
	}

	/**
	 * @param dp
	 */
	public void removeParameter( DProperty dp) {
		parameters.remove( dp);
	}

	/**
	 * @param name
	 */
	public void removeParameter( String name) {
		for( int i = 0; i < parameters.size(); i++) {
			if (parameters.get(i).getName().equals(name)) {
				parameters.remove( parameters.get(i));
			}
		}
	}

	/**
	 * @param order
	 */
	public void removeParameter( int order) {
		parameters.remove( parameters.get(order));
	}

	/**
	 * @return
	 */
	public ArrayList<String> extract()
	{
		ArrayList<String> lines = new ArrayList<String>();
		String firstLine = "";
		firstLine = "public ";
		if( isStatic)
			firstLine += "static ";
		firstLine += returnType + " " + name + "( ";
		for( int i = 0; i < parameters.size(); i++)
		{
			DProperty p = parameters.get(i);
			firstLine += p.getType() + " " + p.getName() + ", ";
		}
		if( firstLine.contains(","))
			firstLine = firstLine.substring(0, firstLine.length() - 2);
		else
			firstLine = firstLine.substring(0, firstLine.length() - 1);
		firstLine += ")";

		lines.add( firstLine);
		lines.add( "{");
		if( returnType.equals( "void"))
			lines.add( "\treturn;");
		else
			lines.add( "\treturn null;");

		lines.add( "}");

		return lines;
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