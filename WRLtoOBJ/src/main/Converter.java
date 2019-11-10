package main;

import java.util.ArrayList;
import java.util.HashSet;

public class Converter
{
	public static HashSet<String> mirrorXMaterials  = new HashSet<String>();
	public static HashSet<String> mirrorYMaterials  = new HashSet<String>();
	public static HashSet<String> mirrorXYMaterials = new HashSet<String>();
	public static float uvScale = 1.0f;
	public static boolean flipRB = false;
	
	public static void replaceTabs(ArrayList<String> wrl)
	{
		for (int i = 0; i < wrl.size(); i++)
		{
			String line = wrl.get(i);
			line = line.replaceAll("\t", " ");
			line = line.trim();
			wrl.set(i, line);
		}
	}
	
	public static void convert(ArrayList<String> wrl, ArrayList<String> obj, ArrayList<String> mtl, String mtlFilename)
	{
		//ArrayList<String> obj = new ArrayList<String>();
		//ArrayList<String> mtl = new ArrayList<String>();
		HashSet<String> materialSet = new HashSet<String>();
		int gVO = 1; //global vertex index offset
		int gTO = 1; //global texture index offset
		
		ArrayList<String> v = new ArrayList<String>();
		ArrayList<String> vt = new ArrayList<String>();
		ArrayList<String> vn = new ArrayList<String>();
		ArrayList<String> f = new ArrayList<String>();
		
		boolean keepGoing = true;
		
		boolean doPrint = false;
		
		obj.add("mtllib "+mtlFilename);
		
		vn.add("vn 0 1 0");
		
		for (int i = 0; i < wrl.size(); i++)
		{
			String line = wrl.get(i);
			if (doPrint) System.out.println(line);
			String[] parts = line.split(" ");
			
			if (parts.length >= 2 && parts[1].equals("'Shape_IndexedFaceSet.324'"))
			{
				doPrint = false;
			}
			
			if (parts.length >= 1 && parts[0].equals("Shape")) //found a new object
			{
				if (doPrint) System.out.println("Found a new shape!");
				ArrayList<Float> coord = new ArrayList<Float>();
				ArrayList<Float> color = new ArrayList<Float>();
				ArrayList<Float> texCoord = new ArrayList<Float>();
				ArrayList<Integer> texCoordIndex = new ArrayList<Integer>();
				ArrayList<Integer> coordIndex = new ArrayList<Integer>();
				
				String textureFilename = "ERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR";
				
				keepGoing = true;
				
				for (i = i+1; i < wrl.size(); i++) //look for a texture
				{
					if (doPrint) System.out.println("looking for texture...");
					line = wrl.get(i);
					if (doPrint) System.out.println(line);
					parts = line.split(" ");
					if (parts.length >= 2 && parts[0].equals("texture") || //found a new texture
						parts.length >= 2 && parts[0].equals("material")) //not a textured image, but will be caught by the ImageTexture check
					{
						if (!parts[1].equals("ImageTexture")) //if the texture doesnt use an image, then it is collision, so skip this object and start looking for the next one
						{
							keepGoing = false;
							break;
						}
						
						//read the image url
						i++;
						line = wrl.get(i);
						if (doPrint) System.out.println(line);
						parts = line.split(" ");
						if (!(parts.length >= 5 && parts[0].equals("url")))
						{
							System.out.println("problem with url");
							keepGoing = false;
							break;
						}
						
						textureFilename = parts[2];
						textureFilename = textureFilename.substring(1, textureFilename.length()-1); //remove quotes
						materialSet.add(textureFilename);
						break;
					}
				}
				
				if (!keepGoing)
				{
					continue;
				}
				
				//look for coordinates
				for (i = i+1; i < wrl.size(); i++)
				{
					if (doPrint) System.out.println("looking for coords...");
					line = wrl.get(i);
					if (doPrint) System.out.println(line);
					parts = line.split(" ");
					if (parts.length >= 2 && parts[0].equals("coord")) //found the coords
					{
						i++;
						line = wrl.get(i);
						if (doPrint) System.out.println(line);
						//System.out.println("about to print out coords");
						parts = line.split(" ");
						if (parts.length >= 2 && parts[0].equals("point"))
						{
							for (int c = 2; c < parts.length-1; c++)
							{
								//System.out.println(parts[c]);
								coord.add(Float.valueOf(parts[c]));
							}
							break;
						}
						else
						{
							System.out.println("problem with coords");
							keepGoing = false;
							break;
						}
					}
				}
				
				if (!keepGoing)
				{
					continue;
				}
				
				//look for color
				boolean colorPerVertex = true;
				for (i = i+1; i < wrl.size(); i++)
				{
					if (doPrint) System.out.println("looking for color...");
					
					line = wrl.get(i);
					if (doPrint) System.out.println(line);
					parts = line.split(" ");
					if (parts.length >= 2 && parts[0].equals("colorPerVertex"))
					{
						if (parts[1].equals("FALSE")) //there arent the same number of vertex colors as there are vertex, deal with this later TODO
						{
							//System.out.println("colorPerVertex was FALSE on a texture '"+textureFilename+"'");
							colorPerVertex = false;
						}
					
						i++;
						line = wrl.get(i);
						if (doPrint) System.out.println(line);
						parts = line.split(" ");
						if (parts.length >= 2 && parts[0].equals("color") && parts[1].equals("Color")) //found the color
						{
							i++;
							line = wrl.get(i);
							if (doPrint) System.out.println(line);
							parts = line.split(" ");
							if (parts.length >= 2 && parts[0].equals("color"))
							{
								for (int c = 2; c < parts.length-1; c++)
								{
									color.add(Float.valueOf(parts[c]));
								}
								break;
							}
							else
							{
								System.out.println("problem with color");
								keepGoing = false;
								break;
							}
						}
						else
						{
							System.out.println("problem with color 2");
							keepGoing = false;
							break;
						}
					}
				}
				
				if (!keepGoing)
				{
					continue;
				}
				
				//look for texCoord
				for (i = i+1; i < wrl.size(); i++)
				{
					line = wrl.get(i);
					if (doPrint) System.out.println(line);
					parts = line.split(" ");
					if (parts.length >= 2 && parts[0].equals("texCoord") && parts[1].equals("TextureCoordinate")) //found the texCoord
					{
						i++;
						line = wrl.get(i);
						if (doPrint) System.out.println(line);
						parts = line.split(" ");
						if (parts.length >= 2 && parts[0].equals("point"))
						{
							for (int c = 2; c < parts.length-1; c++)
							{
								texCoord.add(Float.valueOf(parts[c]));
							}
							break;
						}
						else
						{
							System.out.println("problem with texCoord");
							keepGoing = false;
							break;
						}
					}
				}
				
				if (!keepGoing)
				{
					continue;
				}
				
				//look for texCoordIndex
				for (i = i+1; i < wrl.size(); i++)
				{
					line = wrl.get(i);
					if (doPrint) System.out.println(line);
					parts = line.split(" ");
					if (parts.length >= 3 && parts[0].equals("texCoordIndex")) //found the texCoordIndex
					{
						for (int c = 2; c < parts.length-1; c++)
						{
							texCoordIndex.add(Integer.valueOf(parts[c])); c++;
							texCoordIndex.add(Integer.valueOf(parts[c])); c++;
							texCoordIndex.add(Integer.valueOf(parts[c])); c++;
						}
						break;
					}
				}
				
				if (!keepGoing)
				{
					continue;
				}
				
				//look for coordIndex
				for (i = i+1; i < wrl.size(); i++)
				{
					line = wrl.get(i);
					if (doPrint) System.out.println(line);
					parts = line.split(" ");
					if (parts.length >= 3 && parts[0].equals("coordIndex")) //found the coordIndex
					{
						for (int c = 2; c < parts.length-1; c++)
						{
							coordIndex.add(Integer.valueOf(parts[c])); c++;
							coordIndex.add(Integer.valueOf(parts[c])); c++;
							coordIndex.add(Integer.valueOf(parts[c])); c++;
						}
						break;
					}
				}
				
				if (!keepGoing)
				{
					continue;
				}
				
				//System.out.println("coord size = "+coord.size());
				//System.out.println("color size = "+color.size());
				//System.out.println("texCoord size = "+texCoord.size());
				//System.out.println("coordIndex size = "+coordIndex.size());
				//System.out.println("texCoordIndex size = "+texCoordIndex.size());
				//System.out.println("\n");
				
				//print positions and colors
				for (int n = 0; n < coord.size(); n+=3)
				{
					float vcol1 = 1.0f;
					float vcol2 = 1.0f;
					float vcol3 = 1.0f;
					if (!colorPerVertex)
					{
						if (flipRB)
						{
							vcol1 = color.get(2); //swap red and blue
							vcol2 = color.get(1);
							vcol3 = color.get(0);
						}
						else
						{
							vcol1 = color.get(0);
							vcol2 = color.get(1);
							vcol3 = color.get(2);
						}
					}
					else if (n+2 < color.size())
					{
						if (flipRB)
						{
							vcol1 = color.get(n+2); //swap red and blue
							vcol2 = color.get(n+1);
							vcol3 = color.get(n);
						}
						else
						{
							vcol1 = color.get(n);
							vcol2 = color.get(n+1);
							vcol3 = color.get(n+2);
						}
					}
					else
					{
						System.out.println("Warning: Vertex color index out of bounds. Using white instead.");
					}
					
					v.add("v "+coord.get(n)+" "+coord.get(n+1)+" "+coord.get(n+2)+" "+vcol1+" "+vcol2+" "+vcol3);
				}
				
				//print uv
				for (int n = 0; n < texCoord.size(); n+=2)
				{
					float x = texCoord.get(n);
					float y = texCoord.get(n+1);
					//translate y down
					y = y-1;
					//scale the uv to be correct
					x*=uvScale;
					y*=uvScale;
					//translate y back up
					y = y+1;
					if (mirrorXMaterials.contains(textureFilename))
					{
						x*=0.5f;
					}
					else if (mirrorYMaterials.contains(textureFilename))
					{
						y*=0.5f;
					}
					else if (mirrorXYMaterials.contains(textureFilename))
					{
						x*=0.5f;
						y*=0.5f;
					}
					vt.add("vt "+x+" "+y);
				}
				
				//print normal
				//for (int n = 0; n < texCoord.size(); n+=2)
				{
					//vn.add("vn 0 1 0");
				}
				
				f.add("usemtl "+textureFilename);
				
				//print faces
				for (int n = 0; n < coordIndex.size(); n+=3)
				{
					int vI1 = Integer.valueOf(coordIndex.get(n))+gVO;
					int vI2 = Integer.valueOf(coordIndex.get(n+1))+gVO;
					int vI3 = Integer.valueOf(coordIndex.get(n+2))+gVO;
					int tI1 = Integer.valueOf(texCoordIndex.get(n))+gTO;
					int tI2 = Integer.valueOf(texCoordIndex.get(n+1))+gTO;
					int tI3 = Integer.valueOf(texCoordIndex.get(n+2))+gTO;
					f.add("f "+vI1+"/"+tI1+"/1"+" "+vI2+"/"+tI2+"/1"+" "+vI3+"/"+tI3+"/1");
				}
				
				gVO+=coord.size()/3;
				gTO+=texCoord.size()/2;
			}
		}
		
		for (String mat : materialSet)
		{
			mtl.add("newmtl "+mat);
			mtl.add("Ns 20");
			mtl.add("Ni 0.0");
			mtl.add("illum 2");
			mtl.add("map_Kd "+mat);
			mtl.add("");
		}
		
		//Output.writeFile(mtl, "materials.mtl");
		
		for (String s : v)
		{
			obj.add(s);
		}
		for (String s : vt)
		{
			obj.add(s);
		}
		for (String s : vn)
		{
			obj.add(s);
		}
		for (String s : f)
		{
			obj.add(s);
		}
		
		//return obj;
	}
}
