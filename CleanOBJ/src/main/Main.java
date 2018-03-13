package main;

import java.util.ArrayList;


public class Main 
{
	public static String objFile = "frame.obj";
	public static String mtlFile = "frame.mtl";
	
	public static void main(String[] args)
	{
    	if (objFile.toLowerCase().endsWith(".obj") &&
    		mtlFile.toLowerCase().endsWith(".mtl"))
    	{
    		ArrayList<String> OBJFile = Input.openFile(objFile);
    		ArrayList<String> MTLFile = Input.openFile(mtlFile);
    		System.out.println("Getting rid of textures with identical .png's");
    		Fixer.fixMaterials(OBJFile, MTLFile);
    		System.out.println("Grouping usemtl groups in obj file");
    		Fixer.organizeOBJ(OBJFile);
    		System.out.println("Done. Writing out...");
    		Output.writeFile(OBJFile, objFile);
    		Output.writeFile(MTLFile, mtlFile);
    		System.out.println("Done");
    	}
	}
}