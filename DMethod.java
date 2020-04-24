package com.company;

import java.util.ArrayList;

public class DMethod {
 
   //properties
   private String name;
   private String returnType;
   
   private ArrayList<DProperty> parameters;
   
   //constructor
   
   public DMethod() {
      parameters = new ArrayList<DProperty>();
	  name = "";
	  returnType = "void";
   }
   
   public DMethod(String name, String returnType) {
      parameters = new ArrayList<DProperty>();
      this.returnType = returnType;
      this.name = name;
   }
   
   //methods
   
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
   
}