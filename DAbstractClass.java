package dpackage;

public class DAbstractClass extends DGeneralClass {
   
   // Constructors
   public DAbstractClass( String name) {
      super( name);
   } 
   
   // Methods
   
   @Override
   public void extract() {
      //for console demos
      System.out.println(this);
   }
   
}