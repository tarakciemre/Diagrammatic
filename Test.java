//import dpackage.*;
import java.util.ArrayList;

import logic.object_source.*;
import logic.tools.*;

public class Test {
   
   public static void main( String[] args) {
      // VARIABLES
       // for stage 1
      DGeneralClass cg;
      DInterface i;
      DClass c;
      DAbstractClass ac;
      DMethod m;
      DProperty p;
      DConstructor con;
       // for stage 2
      DProperty num; 
      DProperty taste;
      DProperty withPancake;
      DProperty jakeMadeIt;
      DMethod getEaten;
      DMethod setEaten;
      int cardinality;
      
      // PROGRAM CODE
      
       // stage 1
      cg = new DGeneralClass("GeneralClass");
      i = new DInterface("Interactable");
      c = new DClass("Bacon");
      ac = new DAbstractClass("E");
      m = new DMethod("isEven", "int");
      p = new DProperty("dassein", "Dassein");
      con = new DConstructor( c);
      
      System.out.println("{DGeneralClass} "   + cg);
      System.out.println("{DInterface} "      +  i);
      System.out.println("{DClass} "          +  c);
      System.out.println("{DAbstractClass} "  + ac);
      System.out.println("{DMethod} "         +  m);
      System.out.println("{DProperty} "       +  p);
      System.out.println();
      
       // stage 2
      
        // stage 2.1: DMethod
      num = new DProperty( "number", "int");
      m.addParameter(num);
      
      System.out.println("{DMethod} "         +  m);
      System.out.println();
      
        // stage 2.2: DClass
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
      
      c.updateConstructor();
      
      System.out.println("{DClass} "          +  c);
      System.out.println();
      
        // stage 2.3: DAbstractClass
      cardinality = 5;
      for ( int k = 0; k < cardinality; k++) {
         ac.addProperty( new DProperty( "element"+k, "Number"));
      }
      
      System.out.println("{DAbstractClass} "  + ac);
      System.out.println();
      
      for( int k = 0; k < c.extract().size(); k++)
         System.out.println( c.extract().get(k));
      
      for( int k = 0; k < ac.extract().size(); k++)
         System.out.println( ac.extract().get(k));
      
        // stage 2.4: DConstructor
      
       // stage 3: Extracting
   }
   
}