package main;

import java.util.ArrayList;

public class Main
{
	public static String fileFolder = "";
	public static String fileName = "Tutorial";
	
	public static ArrayList<String> materialsToRemove = new ArrayList<String>();
	
	public static void main(String[] args)
	{
    	if ((fileName.toLowerCase()+".dae").endsWith(".dae"))
    	{    		
    		System.out.println("Starting.");
    		ArrayList<String> DAEFile = Input.openFile(fileFolder + fileName + ".dae");
    		Converter.replaceTabsAndTrim(DAEFile);
    		ArrayList<String> OBJFile = new ArrayList<String>();
    		ArrayList<String> MTLFile = new ArrayList<String>();
    		
    		System.out.println("Converting from dae to obj");
    		Converter.convert(DAEFile, OBJFile, MTLFile, fileName);
    		
    		Fixer.fixMaterials(OBJFile, MTLFile);
    		Fixer.organizeOBJ(OBJFile);
    		Fixer.organizeOBJ(OBJFile);
    		Fixer.optimizePositions(OBJFile);
    		Fixer.optimizeUVs(OBJFile);
    		
    		//for (String matToRem : materialsToRemove)
    		{
    			//System.out.println("Removing "+matToRem);
        		//Fixer.removeMaterial(OBJFile, MTLFile, matToRem);
    		}
    		
    		System.out.println("Done. Writing out...");
    		Output.writeFile(OBJFile, fileFolder+fileName+".obj");
    		Output.writeFile(MTLFile, fileFolder+fileName+".mtl");
    		System.out.println("Done");
    	}
	}
}
