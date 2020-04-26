//package com.company;
package logic.object_source;

import java.util.ArrayList;
import logic.tools.*;

public class DInterface extends DObject {
   
   // Properties
   private ArrayList<DMethod> methodCollector;
   private String name;
   protected ArrayList<DInterface> superInterfaces;
   
   // Constructors
   public DInterface( String name) {
      this.name = name;
      methodCollector = new ArrayList<DMethod>();
   }
   
   public DInterface( String name, DInterface superInterface) {
      this.name = name;
      methodCollector = new ArrayList<DMethod>();
      superInterfaces = new ArrayList<DInterface>();
      superInterfaces.add( superInterface);
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
      
   public ArrayList<DMethod> getMethods()
   {
      return methodCollector;
   }
    
   public void addSuperInterface( DInterface superInterface)
   {
      superInterfaces.add(superInterface);
   }
   
   @Override
   public void extract() {
      //for console demos
      System.out.println( this);
   }
}