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
		// done interfaces array
		ArrayList<DInterface> doneInt = new ArrayList<DInterface>();
		// done classes array
		ArrayList<DClass> doneCla = new ArrayList<DClass>();
		ArrayList<DAbstractClass> doneAbCla = new ArrayList<DAbstractClass>();

		ArrayList<String> projectText = new ArrayList<String>();
		projectText.add("PRO: " + getName());
		projectText.add("");
		for (DObject o : objects)
		{

			// interfaces without supers
			if (o instanceof DInterface)
			{
				if ( ((DInterface)o).getSuperInterface().isEmpty() )
				{
					DInterface i = (DInterface) o;
					doneInt.add(i);
					projectText.addAll(i.classToString());
					projectText.add("");
				}
				// interfaces with done interfaces
				else {
					if ( doneInt.contains(((DInterface)o).getSuperInterface()) );
					{
						DInterface i = (DInterface) o;
						doneInt.add(i);
						projectText.addAll(i.classToString());
						projectText.add("");
					}
				}
			}


			// classes without supers

			else if (o instanceof DClass)
			{
				if ( ((DClass)o).getSuperClass() == null )
				{
					DClass i = (DClass) o;
					doneCla.add(i);
					projectText.addAll(i.classToString());
					projectText.add("");
				}
				// classes with done supers
				else {
					if ( doneCla.contains(((DClass)o).getSuperClass()) );
					{
						DClass i = (DClass) o;
						doneCla.add(i);
						projectText.addAll(i.classToString());
						projectText.add("");
					}
				}
			}

			else if (o instanceof DAbstractClass)
			{
				if ( ((DAbstractClass)o).getSuperClass() == null )
				{
					DAbstractClass i = (DAbstractClass) o;
					doneAbCla.add(i);
					projectText.addAll(i.classToString());
					projectText.add("");
				}
				// classes with done supers
				else {
					if ( doneAbCla.contains(((DAbstractClass) o).getSuperClass()) );
					{
						DAbstractClass i = (DAbstractClass) o;
						doneAbCla.add(i);
						projectText.addAll(i.classToString());
						projectText.add("");
					}
				}
			}
		}

		projectText.add("END");

		return projectText;
	}

	public ArrayList<DObject> getObjects() {
		return objects;
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
