//import dpackage.*;
import java.util.ArrayList;

import logic.object_source.*;
import logic.tools.*;

public class Test {
   
   // for stage 1
   static DGeneralClass cg;
   static DInterface i;
   static DClass c;
   static DAbstractClass ac;
   static DMethod m;
   static DProperty p;
   static DConstructor con;
   
   // for stage 2
   static DProperty num; 
   static DProperty taste;
   static DProperty withPancake;
   static DProperty jakeMadeIt;
   static DMethod getEaten;
   static DMethod setEaten;
   static int cardinality;
   
   public static void main( String[] args) {
      init();
      
      // TO TEST SOMETHÝNG, UNCOMMENT THE NECESSARY LINE BELOW:
      //dMethodTest();
      dClassTest();
      //dAbstractTest();
      dExtractTest();
   }
   
   // Test Constructor
   public static void testConstructor()
   {
      
   }
   
   public static void init()
   {
      cg = new DGeneralClass("GeneralClass");
      i = new DInterface("Interactable");
      c = new DClass("Bacon");
      ac = new DAbstractClass("E");
      m = new DMethod("isEven", "int");
      p = new DProperty("dassein", "Dassein");
      con = new DConstructor( c);  
   } 
   
   public static void dMethodTest()
   {
      num = new DProperty( "number", "int");
      m.addParameter(num);
      
      System.out.println("{DMethod} "         +  m);
      System.out.println(); 
   }
   
   public static void dClassTest()
   {
      taste = new DProperty( "taste", "Taste");
      withPancake = new DProperty( "withPancake", "boolean");
      jakeMadeIt = new DProperty( "jakeMadeIt", "boolean");
      getEaten = new DMethod( "getEaten", "boolean");
      setEaten = new DMethod( "setEaten", "void");
      
      ArrayList<DProperty> forBacon = new ArrayList<DProperty>();
      forBacon.add(taste);
      forBacon.add(withPancake);
      
      c.addProperties( forBacon);
      
      ac.addProperty(jakeMadeIt);
      ac.addMethod(getEaten);
      c.addProperty(jakeMadeIt);
      c.addMethod(getEaten);
      c.addMethod(setEaten);
      
      c.addConstructor();
      
      System.out.println("{DClass} "          +  c);
      System.out.println();
      
      for(String param: c.classToString())
      {
         System.out.println(param);
      }
   }
   
   public static void dAbstractTest()
   {
      cardinality = 5;
      for ( int k = 0; k < cardinality; k++) {
         ac.addProperty( new DProperty( "element"+k, "Number"));
      }
      
      System.out.println("{DAbstractClass} "  + ac);
      System.out.println();
   }
   
   public static void dExtractTest()
   {
      for( int k = 0; k < c.extract().size(); k++)
         System.out.println( c.extract().get(k));
      
      for( int k = 0; k < ac.extract().size(); k++)
         System.out.println( ac.extract().get(k));
   }
   
   public static void translateTest()
   {
      
   }
}