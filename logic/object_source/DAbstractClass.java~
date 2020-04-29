package logic.object_source;


import logic.tools.*;
   
public class DAbstractClass extends DGeneralClass {
   
   // Conlinesuctors
   public DAbstractClass( String name) {
      super( name);
   } 
   
   //A CONSTRUCTOR IS NEEDED HERE WITH "SUPER" PARAMETER
   
   // Methods
   
   @Override
   public ArrayList<String> extract() {
      lines = new ArrayList<String>();
      
      lines.add("public abstract class " + this.getName());
      lines.add("{");
      lines.add("");
      lines.add( "\t// Properties");
      for ( int i = 0; i < this.getProperties(); i++)
         lines.add( "\tprivate " + this.getProperties().get(i).extract();
      lines.add("");
      lines.add( "\t// Methods");
      lines.add( "");
      for( int i = 0; i < getMethods().size(); i++)
      {
         for( int a = 0; a < getMethods().get(i).extract().size(); a++)
         {
            lines.add( "\t" + " public abstract" + getMethods().get(i).extract().get(a).substring( 6));
         }
         lines.add( "");
      }
      lines.add( "");
      lines.add( "}");
      
      return lines;

   }
}