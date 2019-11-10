package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		try
		{
			File folder = new File("./Paths");
			File[] listOfFiles = folder.listFiles();
	
			for (File file : listOfFiles)
			{
				if (file.isFile() && file.getName().endsWith(".obj"))
				{
					Scanner input = new Scanner(file);
					
					String line = "";
					
					line = input.nextLine();
					
					int splineNum = 0;
					ArrayList<Vertex> v = new ArrayList<Vertex>();
					
					while (line != null)
					{
						String data[] = line.split(" ");
		
						if (data.length > 0)
						{
							if (line.startsWith("v "))
							{
								if (data.length >= 4)
								{
									float newX = Float.parseFloat(data[data.length-3]);
									float newY = Float.parseFloat(data[data.length-2]);
									float newZ = Float.parseFloat(data[data.length-1]);
									v.add(new Vertex(newX, newY, newZ));
								}
								else
								{
									System.out.println("Error when parsing '"+line+"'\t = ");
									
									for (String str : data)
									{
										System.out.println("'"+str+"'\t");
									}
								}
							}
							else if (line.startsWith("l "))
							{
								Vector3f up = new Vector3f(0, 1, 0);
								for (int i = 0; i < v.size()-1; i++)
								{
									Vector3f v1 = v.get(i).pos;
									Vector3f v2 = v.get(i+1).pos;
									
									Vector3f diff = new Vector3f(v2.x-v1.x, v2.y-v1.y, v2.z-v1.z);
									Vector3f perpen = up.cross(diff);
									Vector3f normal = diff.cross(perpen);
									normal.normalise();
									if (normal.y < 0)
									{
										normal.negate();
									}
									
									v.get(i).normal = normal;
									
									if (i == v.size()-2)
									{
										v.get(i+1).normal = normal;
									}
								}
								
								PrintWriter writer = new PrintWriter(file.getName().substring(0, file.getName().length()-4)+splineNum+".txt", "UTF-8");
								
								for (Vertex ver : v)
								{
									writer.println(ver.toString());
								}
								
								writer.close();
								
								v.clear();
								splineNum++;
							}
						}
						
						if (input.hasNextLine())
						{
							line = input.nextLine();
						}
						else
						{
							line = null;
						}
					}
					
					input.close();
				}
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
