//package com.company;
package logic.object_source;

import java.util.ArrayList;
import logic.tools.*;

public class DClass extends DGeneralClass {
   
   // Properties
   private DConstructor cons; // Will be made into an ArrayList
   
   // Constructors
   public DClass( String name) {
      super( name);
      cons = new DConstructor((DClass) this);
   } 
   
   // Methods
   
   public void updateConstructor()
   {
      cons = new DConstructor( (DClass) this);
   }
   
   @Override
   public ArrayList<String> extract() {
      ArrayList<String> lines = new ArrayList<String>();
      lines.add( "public class " + getName());
      lines.add( "{");
      lines.add( "");
      
      lines.add( "\t//Properties");
      lines.add( "");
      for( int i = 0; i < getProperties().size(); i++)
      {
         lines.add( "\tprivate " + getProperties().get(i).extract()); 
      }
      lines.add( "");
      
      lines.add( "\t//Constructors");
      lines.add( "");
      
      // !EDIT! multiple constructors will be possible
      for( int i = 0; i < cons.extract().size(); i++)
      {
         lines.add( "\t" + cons.extract().get(i));
      }
      lines.add( "");
      
      lines.add( "\t//Methods");
      lines.add( "");
      for( int i = 0; i < getMethods().size(); i++)
      {
         for( int a = 0; a < getMethods().get(i).extract().size(); a++)
         {
            lines.add( "\t" + getMethods().get(i).extract().get(a));
         }
         lines.add( "");
      }
      lines.add( "");
      
      lines.add( "}");
      
      return lines;
   }
   


}