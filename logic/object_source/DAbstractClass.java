package logic.object_source;


import logic.tools.*;
import java.util.ArrayList;
   
public class DAbstractClass extends DGeneralClass {
   
   // Conlinesuctors
   public DAbstractClass( String name) {
      super( name);
   } 
   
   //A CONSTRUCTOR IS NEEDED HERE WITH "SUPER" PARAMETER
   
   // Methods
   
   @Override
   public ArrayList<String> extract() {
      ArrayList<String> lines = new ArrayList<String>();
      
      lines.add("public abstract class " + this.getName());
      lines.add("{");
      lines.add("");
      lines.add( "\t// Properties");
      lines.add("");
      for ( int i = 0; i < this.getProperties().size(); i++)
         lines.add( "\tprivate " + this.getProperties().get(i).extract());
      lines.add("");
      lines.add( "\t// Methods");
      lines.add( "");
      for( int i = 0; i < getMethods().size(); i++)
      {
         String line = "";
         line += "\tpublic abstract" + getMethods().get(i).extract().get(0).substring( 6) + "{};";

         lines.add( line);
      }
      lines.add( "");
      lines.add( "}");
      
      return lines;

   }
}