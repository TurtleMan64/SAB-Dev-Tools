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
			File folder = new File("./Paths/Sky Rail/Paths/");
			File[] listOfFiles = folder.listFiles();
		
			for (File file : listOfFiles)
			{
				if (file.isFile() && file.getName().endsWith(".ini"))
				{
					Scanner input = new Scanner(file);
					
					String line = "";
					
					line = input.nextLine();
					
					ArrayList<Vector3f> v = new ArrayList<Vector3f>();
					ArrayList<Vector3f> n = new ArrayList<Vector3f>();
					
					double distance = 0;
					double xrot = 0;
					double zrot = 0;
					
					while (line != null)
					{
						String data[] = line.split("=");
		
						if (data.length == 2)
						{
							if (line.startsWith("Distance"))
							{
								distance = Double.parseDouble(data[1].trim());
							}
							else if (line.startsWith("XRotation"))
							{
								int raw = Integer.parseInt(data[1], 16);
								xrot = raw/182.0;
							}
							else if (line.startsWith("ZRotation"))
							{
								int raw = Integer.parseInt(data[1], 16);
								zrot = raw/182.0;
							}
							else if (line.startsWith("Position"))
							{
								String data2[] = data[1].split(",");
								
								Vector3f pos = new Vector3f(
										Double.parseDouble(data2[0].trim()), 
										Double.parseDouble(data2[1].trim()), 
										Double.parseDouble(data2[2].trim()));
										
								v.add(pos);
								
								//calc normal
								Vector3f normal = new Vector3f(0, 1, 0);
								rotateAboutXAxis(normal, xrot);
								rotateAboutZAxis(normal, zrot);
								n.add(normal);
								
								if (distance < 1.0f)
								{
									System.out.println("warning: distance is very small "+distance);
								}
								
								//reset rotation values
								distance = 0;
								xrot = 0;
								zrot = 0;
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
					
					String fname = file.getName();
					fname = fname.substring(0, fname.indexOf(".ini"));
					
					PrintWriter writer = new PrintWriter("Rail"+fname+".txt", "UTF-8");
					System.out.println("Rail"+fname+".txt");
					for (int i = 0; i < v.size(); i++)
					{
						Vector3f pos = v.get(i);
						Vector3f nrm = n.get(i);
						String nrmX = String.format("%.3f", nrm.x);
						String nrmY = String.format("%.3f", nrm.y);
						String nrmZ = String.format("%.3f", nrm.z);
						writer.println(""+(float)pos.x+" "+(float)pos.y+" "+(float)pos.z+" "+nrmX+" "+nrmY+" "+nrmZ);
					}
					writer.close();
					
					
					writer = new PrintWriter("Rail"+fname+".obj", "UTF-8");
					System.out.println("Rail"+fname+".obj");
					for (int i = 0; i < v.size(); i++)
					{
						Vector3f pos = v.get(i);
						writer.println("v "+(float)pos.x+" "+(float)pos.y+" "+(float)pos.z);
					}
					writer.close();
				}
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	//ang in degrees
	public static void rotateAboutYAxis(Vector3f point, double ang)
	{
		ang = Math.toRadians(ang);
		double origX = point.x;
		double origZ = point.z;
		
		point.x =  (origX*Math.cos(ang)-origZ*Math.sin(ang));
		point.z = -(origZ*Math.cos(ang)+origX*Math.sin(ang));
	}
	
	//ang in degrees
	public static void rotateAboutZAxis(Vector3f point, double ang)
	{
		ang = Math.toRadians(ang);
		double origX = point.x;
		double origY = point.y;
		
		point.x = (origX*Math.cos(ang) - origY*Math.sin(ang));
		point.y = (origY*Math.cos(ang) + origX*Math.sin(ang));
	}
	
	//ang in degrees
	public static void rotateAboutXAxis(Vector3f point, double ang)
	{
		ang = -Math.toRadians(ang);
		double origZ = point.z;
		double origY = point.y;
		
		point.z = (origZ*Math.cos(ang) - origY*Math.sin(ang));
		point.y = (origY*Math.cos(ang) + origZ*Math.sin(ang));
	}
}
