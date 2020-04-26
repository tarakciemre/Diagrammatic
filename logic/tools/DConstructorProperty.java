//package com.company;
package logic.tools;

import java.util.ArrayList;
import logic.object_source.*;
import logic.interfaces.*;

public class DConstructorProperty
{
   private DProperty property;
   private boolean included;
   
   //constructor with two parameters given
   public DConstructorProperty( DProperty property, boolean included)
   {
      this.property = property;
      this.included = included;
   }
   
   //Getters and setters
   public DProperty getProperty()
   {
      return property;  
   }
   
   public boolean isIncluded()
   {
      return included;
   }
   
   public int setProperty(DProperty property)
   {
      this.property = property;
      return 1; //should return something else if empty
   }
   
   public int setIncluded(boolean included)
   {
      this.included = included;
      return 1; //should return something else if empty
   }
   
   public ArrayList<String> extract() {
      return null;
   }
}