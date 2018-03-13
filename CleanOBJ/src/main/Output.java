package main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Output 
{
	public static void writeFile(ArrayList<String> newFile, String fileName)
	{
		try 
		{
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			int size = newFile.size();
			for(int i = 0; i < size; i++)
			{
				writer.println(newFile.get(i));
			}
			writer.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found?");
			e.printStackTrace();
			System.exit(0);
		}
		catch (UnsupportedEncodingException e) 
		{
			System.out.println("Unsupported Encoding?");
			e.printStackTrace();
			System.exit(0);
		}
	}
}
