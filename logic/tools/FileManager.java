import java.io.File;  
import java.io.FileWriter;   
import java.io.IOException; 
import logic.object_source.*;

public class FileManager
{
   // Properties
   
   // Constructors
   public FileManager()
   {
      
   }
   
   // Methods
   // major methods, which will be called
   /**
    * A method to extract String to a .java file
    * @param ArrayList<String> input is the code to be extracted
    * @returns 1 if successful, 0 if a file with the same name already exists
    **/
   public int extractJavaFile()
   {
      return 1;
   }
   
   public int extractToTxt(DObject input)
   {
      String path;
      
      path = "Exports/" + input.getName() + ".txt";
      createFile(path);
      
      try {
         FileWriter myWriter = new FileWriter( path);
         for( String line: input.extract())
         {
            myWriter.write(line);
         }
         myWriter.close();
         return 1;
      } catch (IOException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
         return 0;
      }
   }
   
   /**
    * @param takes a String Path in the format
    *  C:/Projects/File.txt
    *     or
    *  Projects\File.txt -- In which case, it creates the file in the Diagrammatic workspace
    * @returns 1 if successful, 0 if unsuccessful
    **/
   public int createFile(String path)
   {
      try {
         // Use relative path for Unix systems
         File f = new File(path);
         
         f.getParentFile().mkdirs(); 
         f.createNewFile();
         return 1;
      } catch (IOException e) {
         e.printStackTrace();
         return 0;
      }
   }
}