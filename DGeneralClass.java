import java.util.ArrayList;
   
public abstract class DGeneralClass extends DObject
{
   // properties
   DConstructor cons;
   ArrayList<DMethod> meths;
   ArrayList<DProperty> props;
   
   // constructors
   public DGeneralClass()
   {
      cons = new DConstructor( this);
      meths = new ArrayList<DMethod>();
      props = new ArrayList<DProperty>();
      
   } 
   
   // methods
   
   
}