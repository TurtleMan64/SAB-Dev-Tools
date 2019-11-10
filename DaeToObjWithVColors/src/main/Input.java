package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Input 
{
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
