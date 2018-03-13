package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Input 
{
	//Find the obj file and set the name of it in the Main field
	//Returns an array list of the lines of the file
	public static ArrayList<String> findOBJFile()
	{
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		File file = new File(s);
		String[] fileList = file.list();
		ArrayList<String> rawFile = null;
		
		for(String currFile : fileList)
		{
			String fileExtension = currFile.substring(currFile.lastIndexOf(".") + 1, currFile.length());
			if(fileExtension.equalsIgnoreCase("obj"))
			{
				String fileName = currFile.substring(0, currFile.lastIndexOf("."));
				rawFile = new ArrayList<String>();
				Main.objFile = fileName;
				
				try
				{
					Scanner scan = new Scanner(new File(currFile));
					while(scan.hasNextLine())
					{
						rawFile.add(scan.nextLine());
					}
					scan.close();
				} 
				catch (FileNotFoundException e)
				{
					System.out.println("File not found?");
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
		
		if(rawFile == null)
		{
			System.out.println("Error: No OBJ File Found.");
			System.exit(0);
		}
		return rawFile;
	}
	
	//Find the mtl file and set the name of it in the Main field
	//Returns an array list of the lines of the file
	public static ArrayList<String> findMTLFile()
	{
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		File file = new File(s);
		String[] fileList = file.list();
		ArrayList<String> rawFile = null;
		
		for(String currFile : fileList)
		{
			String fileExtension = currFile.substring(currFile.lastIndexOf(".") + 1, currFile.length());
			if(fileExtension.equalsIgnoreCase("mtl"))
			{
				String fileName = currFile.substring(0, currFile.lastIndexOf("."));
				rawFile = new ArrayList<String>();
				Main.mtlFile = fileName;
				
				try
				{
					Scanner scan = new Scanner(new File(currFile));
					while(scan.hasNextLine())
					{
						rawFile.add(scan.nextLine());
					}
					scan.close();
				} 
				catch (FileNotFoundException e)
				{
					System.out.println("File not found?");
					System.exit(0);
				}
			}
		}
		
		if(rawFile == null)
		{
			System.out.println("Error: No MTL File Found.");
			System.exit(0);
		}
		return rawFile;
	}
	
	
	public static ArrayList<String> openFile(String fullpath)
	{
		ArrayList<String> rawFile = new ArrayList<String>();
		try
		{
			Scanner scan = new Scanner(new File(fullpath));
			while(scan.hasNextLine())
			{
				rawFile.add(scan.nextLine());
			}
			scan.close();
		} 
		catch (FileNotFoundException e)
		{
			System.out.println("File not found?");
			e.printStackTrace();
			return null;
		}
		
		return rawFile;
	}
	
	public static ArrayList<String> openFile(File theFile)
	{
		ArrayList<String> rawFile = new ArrayList<String>();
		try
		{
			Scanner scan = new Scanner(theFile);
			while(scan.hasNextLine())
			{
				rawFile.add(scan.nextLine());
			}
			scan.close();
		} 
		catch (FileNotFoundException e)
		{
			System.out.println("File not found?");
			e.printStackTrace();
			return null;
		}
		
		return rawFile;
	}
}
