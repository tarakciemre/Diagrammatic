package logic.object_source;

import logic.tools.*;
import logic.interfaces.*;
import java.util.ArrayList;

public class DObject implements Extractable{

   // Properties
   protected String name;
   private ArrayList<DMethod> methodCollector;
   private ArrayList<DProperty> propertyCollector;
   private ArrayList<DConstructor> constructorCollector;

   // Methods
   public ArrayList<String> extract()
   {
      return null;
   }

   public  String toString()
   {
      return null;
   }

   public ArrayList<DMethod> getMethods()
   {
      return methodCollector;
   }

   public ArrayList<DProperty> getProperties()
   {
      return propertyCollector;
   }

   public void setMethods( ArrayList<DMethod> methodCollector)
   {
      this.methodCollector = methodCollector;
   }

   public void setProperties( ArrayList<DProperty> propertyCollector)
   {
      this.propertyCollector = propertyCollector;
   }

   public String getName()
   {
      return name;
   }

   public void setName( String name)
   {
      this.name = name;
   }

	public ArrayList<DConstructor> getConstructorCollector() {
		return constructorCollector;
	}

	public void setConstructorCollector(ArrayList<DConstructor> constructorCollector) {
		this.constructorCollector = constructorCollector;
	}
}