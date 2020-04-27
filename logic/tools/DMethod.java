//package com.company;
package logic.tools;

import java.util.ArrayList;
import logic.object_source.*;

public class DMethod {
 
   // Properties
   private String name;
   private String returnType;
   private ArrayList<DProperty> parameters;
   private boolean isStatic;
   
   // Constructors  
   public DMethod() {
      name = "";
      returnType = "void";
      parameters = new ArrayList<DProperty>();
   }
   
   public DMethod(String name, String returnType) {
      parameters = new ArrayList<DProperty>();
      this.returnType = returnType.trim();
      this.name = name.trim();
   }
   
   // Methods
   public String toString() {
      String str;
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
   }
   
   public String getName() {
      return name;
   }
   
   public String getReturnType() {
      return returnType;
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
   
   public ArrayList<String> extract() {
      return null;
   }
   
   public void setStatic( boolean st) {
      isStatic = st;
   }
}