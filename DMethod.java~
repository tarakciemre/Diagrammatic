package com.company;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class DMethod {
 
   //properties
   private String name;
   private String returnType;
   
   private LinkedHashMap<String, String> parameters;
   
   //constructor
   
   public DMethod() {
      parameters = new LinkedHashMap<String, String>();
   }
   
   //methods
   
   public String getReturnType() {
      return returnType;
   }
   
   public String getParameter( int order) {
      Iterator<String> itr = parameters.keySet().iterator();
      for (int i = 0; i < order; i++) {
         itr.next();
      }
      return itr.next();
   }
   
   public void addParameter( String name, String type) {
      parameters.put( name, type);
   } 
   
   public void removeParameter( String name) {
      parameters.remove( name);
   }
   
   public void removeParameter( int order) {
      Iterator<String> itr = parameters.keySet().iterator();
      for (int i = 0; i < order; i++) {
         itr.next();
      }
      parameters.remove(itr.next());
   }
   
}