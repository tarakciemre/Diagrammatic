public class DProperty
{
   private String name;
   private String type;
   
   public DProperty(String name, String type)
   {
      this.type = type;
      this.name = name;
   }
   
   //Getters and setters
   public String getName()
   {
      return name;  
   }
   
   public String getType() 
   {
      return type;
   }
   
   public int setName(String name)
   {
      this.name = name;
      return 1; //should return something else if empty
   }
   
   public int setType(String type)
   {
      this.type = type;
      return 1; //should return something else if empty
   }
}