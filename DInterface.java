package com.company;

import java.util.ArrayList;

public class DInterface extends DObject {
   
   // Properties
   private ArrayList<DMethod> methodCollector;
   private String name;
   
   // Constructors
   public DInterface( String name) {
      this.name = name;
      methodCollector = new ArrayList<DMethod>();
   }

   // Methods
   @Override
   public String toString() {
      String str = "Interface " + name + " with methods:\n";
      
      for ( int i = 0; i < methodCollector.size() - 1; i++)
         str += methodCollector.get(i) + ", ";
      if ( methodCollector.size() > 0)
         str += methodCollector.get(methodCollector.size() - 1);
      
      return str;
   }
   
   public void addMethods( DMethod... methods) {
      for ( int i = 0; i < methods.length; i++)
         addMethod( methods[i]);
   }
   
   public void addMethod( DMethod m) {
      methodCollector.add(m);
   }
   
   public void removeMethod( DMethod m) {
      methodCollector.remove(m);
   }
   
   @Override
   public void extract() {
      //for console demos
      System.out.println( this);
   }
}