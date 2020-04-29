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
   
   public void setProperty(DProperty property)
   {
      this.property = property;
   }
   
   public void setIncluded(boolean included)
   {
      this.included = included;
   }
   
   public String extract() {
      return getProperty().extract();
   }
}