package logic.tools;

import java.util.ArrayList;
import logic.object_source.DClass;
import logic.object_source.DInterface;
import logic.object_source.DAbstractClass;

/*
 * DFileManager class for methods related to project/file translations.
 * @version 30.04.2020
 */

public class ProjectManager
{

    public static DClass textToClass( ArrayList<String> lines)
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

                    }
                    else if(line.startsWith( "IMP"))
                    {

                    }
                    else if (line.startsWith( "PRO"))
                    {
                        String[] propInfo = lineInfo.split(",");
                        DProperty dp = new DProperty( propInfo[0], propInfo[1]);
                        dc.addProperty( dp);
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
                    else if (line.startsWith( "END"))
                    {
                        return dc;
                    }
                }

            }
        }
        return dc;
    }

    public static DAbstractClass textToAbsClass( ArrayList<String> lines)
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

                    }
                    else if(line.startsWith( "IMP"))
                    {

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
                    else if (line.startsWith( "END"))
                    {
                        return da;
                    }
                }

            }
        }
        return da;
    }

    public static DInterface textToInterface( ArrayList<String> lines)
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

                    else if (line.startsWith( "END"))
                    {
                        return di;
                    }
                }

            }
        }
        return di;
    }

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
            		DClass dc = textToClass(classInfo);
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
            		DInterface di = textToInterface(intInfo);
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
            		DAbstractClass da = textToAbsClass(absInfo);
            		dp.addObject(da);
            	}
            	else
            	{
            		i++;
            	}
            }
        }

        return dp;
    }
}