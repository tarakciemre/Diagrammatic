package com.company;

import java.util.ArrayList;

public class DInterface extends DObject {
   //properties
   //private ArrayList<DProperty> propertyCollector;
   private ArrayList<DMethod> methodCollector;
   private String name;
   
   //constructor
   public DInterface( String name) {
      this.name = name;
    //  propertyCollector = new ArrayList<DProperty>();
      methodCollector = new ArrayList<DMethod>();
   }

   //methods

   @Override
   public void extract() {
   }
}