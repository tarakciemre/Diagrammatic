//package com.company;
package logic.tools;

import java.util.ArrayList;
import logic.object_source.*;

public class DMethod {
 
   // Properties
   private String name;
   private String returnType;
   private boolean isStatic;
   private ArrayList<DProperty> parameters;

   
   // Constructors  
   public DMethod() {
      name = "";
      returnType = "void";
      parameters = new ArrayList<DProperty>();
   }
   
   public DMethod(String name, String returnType, boolean isStatic) {
      parameters = new ArrayList<DProperty>();
      this.returnType = returnType.trim();
      this.name = name.trim();
      this.isStatic = isStatic;
   }
   
   // Methods
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
   
   public String getName() {
      return name;
   }
   
   public String getReturnType() {
      return returnType;
   }
   
   public boolean getStatic()
   {
      return isStatic;
   }
   
   public void setStatic( boolean isStatic)
   {
      this.isStatic = isStatic;
   }
   
   public void setName( String name) {
      this.name = name;
   }
   
   public void setReturnType( String returnType) {
      this.returnType = returnType;
   }
   
   public DProperty getParameter( int order) {
      return parameters.get( order);
   }
   
   public ArrayList<DProperty> getParameters() {
      return parameters;
   }
   
   public void addParameter( String name, String type) {
      DProperty dp = new DProperty( name, type);
      parameters.add( dp);
   }
   
   public void addParameter( DProperty parameter) {
      parameters.add( parameter);
   } 
   
   public void removeParameter( DProperty dp) {
      parameters.remove( dp);
   }
   
   public void removeParameter( String name) {
      for( int i = 0; i < parameters.size(); i++) {
         if (parameters.get(i).getName().equals(name)) {
            parameters.remove( name);
         }
      }
   }
   
   public void removeParameter( int order) {
      parameters.remove( parameters.get(order));
   }
   
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
   
}