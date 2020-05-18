package logic.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import logic.object_source.DAbstractClass;
import logic.object_source.DClass;
import logic.object_source.DInterface;
import logic.object_source.DObject;

public class DProject {

	ArrayList<DObject> objects;
	String projectName;

	public DProject()
	{
		objects = new ArrayList<DObject>();
		projectName = "New_Project";
	}

	public DProject( String name)
	{
		objects = new ArrayList<DObject>();
		projectName = name;
	}

	public DProject( ArrayList<String> projectText)
	{
		DProject p = ProjectManager.textToProject(projectText);
		this.objects = p.objects;
		this.projectName = p.projectName;
	}

	public void setName(String name)
	{
		projectName = name;
	}

	public String getName()
	{
		return projectName;
	}

	public ArrayList<String> projectToText()
	{
		ArrayList<String> projectText = new ArrayList<String>();
		projectText.add("PRO: " + getName());
		projectText.add("");
		for (DObject o : objects)
		{
			if (o instanceof DClass)
			{
				DClass c = (DClass) o;
				projectText.addAll(c.classToString());
				projectText.add("");
			}

			else if (o instanceof DInterface)
			{
				DInterface i = (DInterface) o;
				projectText.addAll(i.classToString());
				projectText.add("");
			}

			else if (o instanceof DAbstractClass)
			{
				DAbstractClass a = (DAbstractClass) o;
				projectText.addAll(a.classToString());
				projectText.add("");
			}
		}

		projectText.add("END");

		return projectText;
	}

	public void addObject( DObject d)
	{
		objects.add(d);
	}

	public void removeObject( DObject d)
	{
		objects.remove(d);
	}

	public void extract( String path)
	{
		File f = new File(projectName);
		f.mkdirs();
		for (DObject o : objects)
		{
			try {
				FileWriter oFile = new FileWriter( projectName + "/" + o.getName() + ".txt");
				for(String line : o.extract())
				{
					oFile.write(line);
					oFile.write("\n");
				}
				oFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
