//package com.company;
package logic.object_source;

import java.util.ArrayList;
import logic.tools.*;

public class DInterface extends DObject {
   
   // Properties
   
   private String name;
   
   // Constructors
   public DInterface( String name) {
      this.name = name;
      setMethods(new ArrayList<DMethod>());
   }
   
   //A CONSTRUCTOR IS NEEDED HERE WITH "SUPER" PARAMETER

   // Methods
   @Override
   public String toString() {
      // !EDIT THIS!
      String str = "Interface " + name + " with methods:\n";
      
      for ( int i = 0; i < getMethods().size() - 1; i++)
         str += getMethods().get(i) + ", ";
      if ( getMethods().size() > 0)
         str += getMethods().get(getMethods().size() - 1);
      
      return str;
   }
   
   public void addMethods( DMethod... methods) {
      for ( int i = 0; i < methods.length; i++)
         addMethod( methods[i]);
   }
   
   public String getName()
   {
      return name;
   }
   
   public void addMethod( DMethod m) {
      getMethods().add(m);
   }
   
   public void removeMethod( DMethod m) {
      getMethods().remove(m);
   }
   
   @Override
   public ArrayList<String> extract() {
      //for console demos
      return null;
   }
   
   public ArrayList<String> classToString()
   {
      ArrayList<String> output;
      
      output = new ArrayList<String>();
      output.add("INT: " + getName());
      output.add("");
      for( DProperty prop: getProperties())
      {
         output.add("PRO " + prop);
      }
      output.add("");
      for( DMethod meth: getMethods())
      {
         output.add("MET " + meth);
      }
      output.add("");
      output.add("END");
      
      return output;
   }
}