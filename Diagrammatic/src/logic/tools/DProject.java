package logic.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import gui.DApp;
import gui.tools.ComplexLine;
import gui.tools.Element;
import logic.object_source.DGeneralClass;
import logic.object_source.DInterface;
import logic.object_source.DObject;

public class DProject {

	ArrayList<DObject> objects;
	String projectName;
	ArrayList<ComplexLine> complexLines;
    ArrayList<Element> elements;

	/**
	 *
	 */
	public DProject()
	{
		objects = new ArrayList<DObject>();
		projectName = "New_Project";
	}

	/**
	 * @param name
	 */
	public DProject( String name)
	{
		objects = new ArrayList<DObject>();
		projectName = name;
	}

	/**
	 * @param projectText
	 */
	public DProject( ArrayList<String> projectText)
	{
		DProject p = ProjectManager.textToProject(projectText);
		this.objects = p.objects;
		this.projectName = p.projectName;
	}

	/**
	 * @param name
	 */
	public void setName(String name)
	{
		projectName = name;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return projectName;
	}

	/**
	 * @return
	 */
	public ArrayList<String> projectToText()
	{
		// done interfaces array
		ArrayList<DObject> interfaceList = new ArrayList<DObject>();
		// done classes array
		ArrayList<DObject> classList = new ArrayList<DObject>();

		ArrayList<String> projectText = new ArrayList<String>();
		projectText.add("PRO: " + getName());
		projectText.add("");
		for (DObject o : objects)
		{
			if (o instanceof DInterface)
			{
				interfaceList.add((DInterface) o);
			}
			else if (o instanceof DGeneralClass)
			{
				classList.add((DGeneralClass) o);
			}
		}

		ArrayList<DObject> sortedList = new ArrayList<DObject>();
		sortedList.addAll(degreeSort(interfaceList));
		sortedList.addAll(degreeSort(classList));

		projectText.add("");

		for (DObject o : sortedList)
		{
			projectText.addAll(o.classToString());
			projectText.add("");
		}

		if (!DApp.lines.isEmpty())
		{
			projectText.add("COMPLEXLINES");
			for (ComplexLine cln : DApp.lines)
			{
				projectText.add(cln.lineToString());
			}
		}



		projectText.add("END");

		return projectText;
	}

	/**
	 * @return
	 */
	public ArrayList<DObject> getObjects() {
		return objects;
	}

	/**
	 * @param d
	 */
	public void addObject( DObject d)
	{
		objects.add(d);
	}

	/**
	 * @param d
	 */
	public void removeObject( DObject d)
	{
		objects.remove(d);
	}

	/**
	 * @param path
	 */
	public void extract( String path)
	{
		File f = new File(projectName);
		f.mkdirs();
		for (DObject o : objects)
		{
			try {
				FileWriter oFile = new FileWriter( projectName + "/" + o.getName() + ".java");
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

	public void addComplexLine( ComplexLine line)
    {
        complexLines.add(line);
    }

    // removes a complex line from the complexLines array
    public void removeComplexLine( ComplexLine line)
    {
        for(int i = 0; i < complexLines.size(); i++)
        {
            if(complexLines.get(i) == line)
            {
                complexLines.remove(i);
                i = complexLines.size() + 1;
            }
        }
    }

    // adds new element to elements
    public void addElement( Element element)
    {
        elements.add(element);
    }

    // removes a specific element from elements list
    public void removeElement( Element element)
    {
        for(int i = 0; i < elements.size(); i++)
        {
            if(elements.get(i) == element)
            {
                elements.remove(i);
                i = elements.size() + 1;
            }
        }
    }

    public ArrayList<DObject> degreeSort(ArrayList<DObject> list)
    {
    	boolean sorted = false;
        DObject temp;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).degree() > list.get(i+1).degree()) {
                    temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                    sorted = false;
                }
            }
        }
        return list;
    }

}
