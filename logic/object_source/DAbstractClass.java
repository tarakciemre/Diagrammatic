//package com.company;
package logic.object_source;

import logic.tools.*;
import java.util.ArrayList;

public class DAbstractClass extends DGeneralClass {
   
   // Constructors
   public DAbstractClass( String name) {
      super( name);
   } 
   
   //A CONSTRUCTOR IS NEEDED HERE WITH "SUPER" PARAMETER
   
   // Methods
   @Override
   public ArrayList<String> extract() {
      //for console demos
      return null;
   }
}