public class DConstructor
{
   DConstructorProperty[] properties;
   
   public DConstructor(DClass c)
   {
      properties = new DConstructorProperty[20];
      for(int i = 0; i < getProperties().length; i++)
      {
         properties[i] = new DConstructorProperty( getProperties()[i], false);
      }
   }
}

