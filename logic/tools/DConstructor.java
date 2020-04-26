//package com.company;
package logic.tools;

import java.util.ArrayList;
import logic.object_source.*;

public class DConstructor
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


}

