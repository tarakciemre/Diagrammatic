import java.util.ArrayList;

public class DInterface extends DObject {
   //properties
   private ArrayList<DProperty> propertyCollector;
   private ArrayList<DMethod> methodCollector;
   
   //constructor
   public DInterface( String name) {
      super( name);
      propertyCollector = new ArrayList<DProperty>;
      methodCollector = new ArrayList<DMethod>;
   }
}