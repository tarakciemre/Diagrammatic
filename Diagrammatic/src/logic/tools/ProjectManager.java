package logic.tools;

import java.util.ArrayList;

import gui.DApp;
import gui.tools.ComplexLine;
import gui.tools.Element;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import logic.object_source.DAbstractClass;
import logic.object_source.DClass;
import logic.object_source.DGeneralClass;
import logic.object_source.DInterface;
import logic.object_source.DObject;

/*
 * DFileManager class for methods related to project/file translations.
 * @version 30.04.2020
 */

public class ProjectManager
{

	public final static String PROTECTED = "protected";
    public final static String PUBLIC = "public";
    public final static String DEFAULT = "default";
    public final static String PRIVATE = "private";

    /**
     * @param lines
     * @param project
     * @return
     */
    public static DClass textToClass( ArrayList<String> lines, DProject project)
    {
        DClass dc = new DClass( "Unnamed"); //Means an error occurred while loading the class
        String line = "";
        for (int i = 0; i < lines.size(); i++)
        {
            line = lines.get(i);
            if (i == 0)
            {
                if (line.startsWith("CLA"))
                    dc.setName( line.substring(5, line.length()));
                else
                    return dc; // Line 1 doesn't start with CLASS, the object isn't class at all.
            }
            else
            {
                if( line.length() > 3)
                {
                	String lineInfo = line.substring( 4, line.length());
                    if(line.startsWith( "EXT"))
                    {
                    	for (DObject o : project.getObjects())
                    	{
                    		if (o instanceof DGeneralClass)
                    		{
                    			DGeneralClass generalClass = (DGeneralClass) o;

                    			if (o.getName().equalsIgnoreCase(lineInfo))
                    			{
                    				dc.setSuperClass(generalClass);
                    			}
                    		}

                    	}
                    }
                    else if(line.startsWith( "IMP"))
                    {
                    	String[] interfaces = lineInfo.split(",");

                    	for (DObject o : project.getObjects())
                    	{
                    		if (o instanceof DInterface)
                    		{
                    			DInterface dInterface = (DInterface) o;

                    			for (String intName : interfaces)
                        		{
                        			if (o.getName().equalsIgnoreCase(intName))
                        			{
                        				dc.addInterface(dInterface);
                        			}
                        		}
                    		}

                    	}
                    }
                    else if (line.startsWith( "PRO"))
                    {
                        String[] propInfo = lineInfo.split(",");
                        DProperty dp = new DProperty( propInfo[0], propInfo[1]);
                        dc.addProperty( dp);
                        System.out.println("added property");
                    }
                    else if(line.startsWith( "MET"))
                    {
                        String[] metInfo = lineInfo.split( " ");

                        boolean isStatic = false;
                        if( metInfo[2].equals("true"))
                            isStatic = true;

                        DMethod dm = new DMethod( metInfo[0], metInfo[1], isStatic);

                        if (metInfo.length > 3) // method has parameters
                        {
                            String[] paramInfo = metInfo[3].split("!");

                            for( int pi = 0; pi < paramInfo.length; pi++)
                            {
                                String[] propInfo = paramInfo[pi].split(",");
                                DProperty dp = new DProperty( propInfo[0], propInfo[1]);
                                dm.addParameter( dp);
                            }
                        }
                        dc.addMethod(dm);
                    }
                    else if( line.startsWith( "CON"))
                    {
                        DConstructor dcon = new DConstructor( dc);
                        String[] conInfo = lineInfo.split( " ");
                        for( int ci = 0; ci < conInfo.length; ci++)
                        {
                            boolean included = false;
                            if (conInfo[ci].equals("true"))
                                included = true;
                            dcon.getProperties().get(ci).setIncluded(included);
                        }

                        dc.addConstructor( dcon);
                    }
                    else if( line.startsWith( "ELE"))
                    {
                    	String[] eleInfo = lineInfo.split( " ");
                        Element e = new Element(Double.valueOf(eleInfo[0]), Double.valueOf(eleInfo[1]), Double.valueOf(eleInfo[2]), Double.valueOf(eleInfo[3]), Color.web(eleInfo[4], 1.0), true);
                        ProjectManager.connectElement(e, dc);
                        DApp.elements.add(e);

            			DApp.group.getChildren().add(e);
                    }
                    else if (line.startsWith( "END"))
                    {
                        return dc;
                    }
                }

            }
        }
        return dc;
    }

    /**
     * @param lines
     * @param project
     */
    public static DAbstractClass textToAbsClass( ArrayList<String> lines, DProject project)
    {
        DAbstractClass da = new DAbstractClass( "Unnamed"); //Means an error occurred while loading the class
        String line = "";
        for (int i = 0; i < lines.size(); i++)
        {
            line = lines.get(i);
            if (i == 0)
            {
                if (line.startsWith("ABS"))
                    da.setName( line.substring(5, line.length()));
                else
                    return da; // Line 1 doesn't start with CLASS, the object isn't class at all.
            }
            else
            {
                if( line.length() > 3)
                {
                    String lineInfo = line.substring( 4, line.length());
                    if(line.startsWith( "EXT"))
                    {
                    	for (DObject o : project.getObjects())
                    	{
                    		if (o instanceof DGeneralClass)
                    		{
                    			DGeneralClass generalClass = (DGeneralClass) o;

                    			if (o.getName().equalsIgnoreCase(lineInfo))
                    			{
                    				da.setSuperClass(generalClass);
                    			}
                    		}

                    	}
                    }
                    else if(line.startsWith( "IMP"))
                    {
                    	String[] interfaces = lineInfo.split(",");

                    	for (DObject o : project.getObjects())
                    	{
                    		if (o instanceof DInterface)
                    		{
                    			DInterface dInterface = (DInterface) o;

                    			for (String intName : interfaces)
                        		{
                        			if (o.getName().equalsIgnoreCase(intName))
                        			{
                        				da.addInterface(dInterface);
                        			}
                        		}
                    		}

                    	}
                    }
                    else if (line.startsWith( "PRO"))
                    {
                        String[] propInfo = lineInfo.split(",");
                        DProperty dp = new DProperty( propInfo[0], propInfo[1]);
                        da.addProperty( dp);
                    }
                    else if(line.startsWith( "MET"))
                    {
                        String[] metInfo = lineInfo.split( " ");

                        boolean isStatic = false;
                        if( metInfo[2].equals("true"))
                            isStatic = true;

                        DMethod dm = new DMethod( metInfo[0], metInfo[1], isStatic);

                        if (metInfo.length > 3) // method has parameters
                        {
                            String[] paramInfo = metInfo[3].split("!");

                            for( int pi = 0; pi < paramInfo.length; pi++)
                            {
                                String[] propInfo = paramInfo[pi].split(",");
                                DProperty dp = new DProperty( propInfo[0], propInfo[1]);
                                dm.addParameter( dp);
                            }
                        }
                        da.addMethod(dm);
                    }
                    else if( line.startsWith( "CON"))
                    {
                        DConstructor dcon = new DConstructor( da);
                        String[] conInfo = lineInfo.split( " ");
                        for( int ci = 0; ci < conInfo.length; ci++)
                        {
                            boolean included = false;
                            if (conInfo[ci].equals("true"))
                                included = true;
                            dcon.getProperties().get(ci).setIncluded(included);
                        }

                        da.addConstructor( dcon);
                    }

                    else if( line.startsWith( "ELE"))
                    {
                    	String[] eleInfo = lineInfo.split( " ");
                        Element e = new Element(Double.valueOf(eleInfo[0]), Double.valueOf(eleInfo[1]), Double.valueOf(eleInfo[2]), Double.valueOf(eleInfo[3]), Color.web(eleInfo[4], 1.0), true);
                        ProjectManager.connectElement(e, da);
                        DApp.elements.add(e);

            			DApp.group.getChildren().add(e);
                    }

                    else if (line.startsWith( "END"))
                    {
                        return da;
                    }
                }

            }
        }
        return da;
    }

    /**
     * @param lines
     * @param project
     * @return
     */
    public static DInterface textToInterface( ArrayList<String> lines, DProject project)
    {
        DInterface di = new DInterface( "Unnamed"); //Means an error occurred while loading the class
        String line = "";
        for (int i = 0; i < lines.size(); i++)
        {
            line = lines.get(i);
            if (i == 0)
            {
                if (line.startsWith("INT"))
                    di.setName( line.substring(5, line.length()));
                else
                    return di; // Line 1 doesn't start with CLASS, the object isn't class at all.
            }
            else
            {
                if( line.length() > 3)
                {
                    String lineInfo = line.substring( 4, line.length());
                    if(line.startsWith( "EXT"))
                    {
                    	String[] superInterfaces = lineInfo.split(",");

                    	for (DObject o : project.getObjects())
                    	{
                    		if (o instanceof DInterface)
                    		{
                    			DInterface dInterface = (DInterface) o;

                    			for (String superInterface : superInterfaces)
                        		{
                        			if (o.getName().equalsIgnoreCase(superInterface))
                        			{
                        				di.addSuperInterface(dInterface);
                        			}
                        		}
                    		}

                    	}
                    }
                    else if(line.startsWith( "MET"))
                    {
                        String[] metInfo = lineInfo.split( " ");

                        boolean isStatic = false;
                        if( metInfo[2].equals("true"))
                            isStatic = true;

                        DMethod dm = new DMethod( metInfo[0], metInfo[1], isStatic);

                        if (metInfo.length > 3) // method has parameters
                        {
                            String[] paramInfo = metInfo[3].split("!");

                            for( int pi = 0; pi < paramInfo.length; pi++)
                            {
                                String[] propInfo = paramInfo[pi].split(",");
                                DProperty dp = new DProperty( propInfo[0], propInfo[1]);
                                dm.addParameter( dp);
                            }
                        }
                        di.addMethod(dm);
                    }

                    else if( line.startsWith( "ELE"))
                    {
                    	String[] eleInfo = lineInfo.split( " ");
                        Element e = new Element(Double.valueOf(eleInfo[0]), Double.valueOf(eleInfo[1]), Double.valueOf(eleInfo[2]), Double.valueOf(eleInfo[3]), Color.web(eleInfo[4], 1.0), true);
                        ProjectManager.connectElement(e, di);
                        DApp.elements.add(e);

            			DApp.group.getChildren().add(e);
            			System.out.println(e);
                    }

                    else if (line.startsWith( "END"))
                    {
                        return di;
                    }
                }

            }
        }
        return di;
    }

    /**
     * @param lines
     * @return
     */
    public static DProject textToProject( ArrayList<String> lines)
    {
        DProject dp = new DProject(" Unnamed");
        int i = 0;
        String line = "";
        while (i < lines.size())
        {
            line = lines.get(i);
            if (i == 0)
            {
                if (line.startsWith( "PRO"))
                    dp.setName( line.substring(5, line.length()));
                else
                    return dp; // Line 1 doesn't start with CLASS, the object isn't class at all.
                i++;
            }
            else
            {
            	if (line.startsWith( "CLA"))
            	{
            		ArrayList<String> classInfo = new ArrayList<String>();
            		while( !line.startsWith("END"))
            		{
            			line = lines.get(i);
            			classInfo.add(line);
            			i++;
            		}
            		classInfo.add("END");
            		i++;
            		DClass dc = textToClass(classInfo, dp);
            		dp.addObject(dc);
            	}
            	else if (line.startsWith( "INT"))
            	{
            		ArrayList<String> intInfo = new ArrayList<String>();
            		while( !line.startsWith("END"))
            		{
            			line = lines.get(i);
            			intInfo.add(line);
            			i++;
            		}
            		intInfo.add("END");
            		i++;
            		DInterface di = textToInterface(intInfo, dp);
            		dp.addObject(di);
            	}
            	else if (line.startsWith( "ABS"))
            	{
            		ArrayList<String> absInfo = new ArrayList<String>();
            		while( !line.startsWith("END"))
            		{
            			line = lines.get(i);
            			absInfo.add(line);
            			i++;
            		}
            		absInfo.add("END");
            		i++;
            		DAbstractClass da = textToAbsClass(absInfo, dp);
            		dp.addObject(da);
            	}

            	else if (line.startsWith( "COMPLEXLINES"))
            	{
            		DApp.drawHierarchy(dp);
            		i++;
            	}

            	else if (line.startsWith( "CLN"))
            	{
            		String[] clnInfo = line.substring( 4, line.length()).split(" ");
            		Element first = null, second = null;
            		ComplexLine cln = null;

            		for (Element e : DApp.elements)
            		{
            			if (e.getObject().getName().equals(clnInfo[0]))
            				first = e;
            			else if (e.getObject().getName().equals(clnInfo[1]))
            				second = e;
            		}

            		for (ComplexLine l : DApp.lines)
            		{
            			if (first.getStartLines().contains(l) && second.getEndLines().contains(l))
            				cln = l;
            		}

            		if (clnInfo.length >= 2)
            		{
            			for( int j = 2; j < clnInfo.length; j++)
                		{
                			String[] pointInfo = clnInfo[j].split("-");
                			Point2D point = new Point2D( Double.valueOf(pointInfo[0]), Double.valueOf(pointInfo[1]));
                			cln.addPoint(point, cln.getPointCount() - 1);
                			System.out.println(cln);
                		}
            		}

            		i++;
            	}
            	else
            	{
            		i++;
            	}
            }
        }

        return dp;
    }

    public static void connectElement( Element e, DObject o)
    {
    	e.setObject(o);
    	o.setElement(e);
    }

}