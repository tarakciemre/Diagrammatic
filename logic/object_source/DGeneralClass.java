//package com.company;
package logic.object_source;

import java.util.ArrayList;
import logic.tools.*;
   
public class DGeneralClass extends DObject {
   // Properties
   private String name;
   private ArrayList<DMethod> meths;
   private ArrayList<DProperty> props;
   
   // Constructors 
   public DGeneralClass( String name) {
      this.name = name.trim();
      meths = new ArrayList<DMethod>();
      props = new ArrayList<DProperty>();
   } 
   
   // Methods 
   public String toString() {
      String str = "public class " + name ;
      
      str += " \n Fields: " ;
      for ( int i = 0; i < props.size() - 1; i++)
         str += props.get(i) + " | ";
      if ( props.size() > 0)
         str += props.get( props.size() - 1) + " ";
      
      str += "\n Methods: ";
      for ( int i = 0; i < meths.size() - 1; i++)
         str += meths.get(i) + " | ";
      if ( meths.size() > 0)
         str += meths.get( meths.size() - 1) + " ";
      
      return str;
   }
   
   @Override
   public void extract() {
      //for console demos
      System.out.println( this);
   }

   public ArrayList<DProperty> getProperties() {
      return props;
   }
   
   public void addProperty( String name, String type) {
      DProperty n = new DProperty( name, type);
      props.add(n);
   }
   
   public void addProperty( DProperty p) {
      props.add(p);
   }
   
   public void addProperties( ArrayList<DProperty> ps) {
      for ( DProperty p : ps)
        props.add(p); 
   }
   
   public void removeProperty( String name) {
      for ( DProperty p : props) {
         if ( p.getName().equals(name))
            props.remove(p);
      }
   }
   
   public void removeProperty( DProperty p) {
      props.remove(p);
      
   }
   
   public void removeProperties( ArrayList<DProperty> ps) {
      for ( DProperty p : ps) {
         for ( int i = 0; i < props.size(); i++) {
            if ( props.get(i).getName().equals(p.getName()) && props.get(i).getType().equals(p.getType()))
               props.remove( props.get(i));
         }
      }
   }
   
   public void removeAllFieldsType( String type) {
      for ( DProperty p : props) {
         if ( p.getType().equals(type))
            props.remove(p);
      }
   }
   
   public void addMethod( DMethod m) {
      meths.add(m);
   }
   
   public void addMethods( ArrayList<DMethod> ms) {
      for ( DMethod m : ms)
         meths.add(m);
   }
   
   public void removeAllMethodsNamed( String name) {
      for (DMethod m : meths) {
         if ( m.getName().equals(name))
            meths.remove(m);
      }
   }
   
   public void removeMethod( String name, String returnType, ArrayList<DProperty> parameters) {
      for ( DMethod m : meths) {
         if ( m.getName().equals(name) && m.getReturnType().equals(returnType) && m.getParameters().equals(parameters))
            meths.remove(m);
      }
   }
   
   public void removeMethod( DMethod m) {
      meths.remove(m);
   }
   
}