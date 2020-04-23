package com.company;
import java.util.ArrayList;
   
public class DGeneralClass extends DObject
{
   // properties
   private String name;
   private DConstructor cons;
   private ArrayList<DMethod> meths;
   private ArrayList<DProperty> props;
   
   // constructors
   public DGeneralClass( String name)
   {
      this.name = name;
      cons = new DConstructor((DClass) this);
      meths = new ArrayList<DMethod>();
      props = new ArrayList<DProperty>();
   } 
   
   // methods
   
   @Override
   public void extract() {

   }

   public ArrayList<DProperty> getProperties() {
      return props;
   }
}