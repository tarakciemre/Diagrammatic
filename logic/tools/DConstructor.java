//package com.company;
package logic.tools;

import java.util.ArrayList;
import logic.interfaces.*;
import logic.object_source.*;


public class DConstructor implements Extractable
{
   private ArrayList<DConstructorProperty> properties;
   
   public DConstructor(DClass c)
   {
      properties = new ArrayList<DConstructorProperty>();
      for(int i = 0; i < c.getProperties().size(); i++)
      {
         properties.set(i, new DConstructorProperty( c.getProperties().get(i), false) );
      }
   }
   
   public ArrayList<String> extract() {
      return null;
   }
}

