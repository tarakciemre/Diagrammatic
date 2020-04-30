//package com.company;
package logic.object_source;

import java.util.ArrayList;
import logic.tools.*;

public class DClass extends DGeneralClass {
   // Properties
   private ArrayList<DConstructor> constructors;
   
   // Constructors
   public DClass( String name) {
      super( name);
      constructors = new ArrayList<DConstructor>();
   } 
   
   // Methods   
   public void addConstructor()
   {
      constructors.add(new DConstructor((DClass) this));
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
      // !EDIT! MUST BE EDITED TO MAKE COMPATIBLE WITH ARRAYLISTS
      for( int i = 0; i < constructors.get(0).extract().size(); i++)
      {
         lines.add( "\t" + constructors.get(0).extract().get(i));
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
   
   public ArrayList<String> classToString()
   {
      ArrayList<String> output;
      
      output = new ArrayList<String>();
      output.add("CLASS: " + getName());
      output.add("");
      for( DProperty prop: getProperties())
      {
         output.add("PROP " + prop);
      }
      output.add("");
      for( DMethod meth: getMethods())
      {
         output.add("METH " + meth);
      }
      output.add("");
      for( DConstructor cons: constructors)
      {
         output.add("CONS " + cons);
      }
      output.add("");
      output.add("END");
      
      return output;
   }
}