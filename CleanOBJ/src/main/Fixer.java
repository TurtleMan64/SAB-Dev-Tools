package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Fixer 
{
	//Removes triangles that are at the same spot
	public static ArrayList<String> fixOBJ(ArrayList<String> rawFile)
	{
		String material = "";
		ArrayList<String> verticies = new ArrayList<String>();
		ArrayList<String> textures = new ArrayList<String>();
		ArrayList<String> normals = new ArrayList<String>();
		ArrayList<String> faces = new ArrayList<String>();
		ArrayList<Triangle> triList = new ArrayList<Triangle>();
		int size = rawFile.size();
		for(int i = 0; i < size; i++)
		{
			String currLine = rawFile.get(i);
			if(currLine.startsWith("mtllib"))
			{
				material = currLine;
			}
			else if(currLine.startsWith("v "))
			{
				verticies.add(currLine);
			}
			else if(currLine.startsWith("vn"))
			{
				normals.add(currLine);
			}
			else if(currLine.startsWith("vt"))
			{
				textures.add(currLine);
			}
			else if(currLine.startsWith("f")||currLine.startsWith("usemtl"))
			{
				faces.add(currLine);
				if(currLine.startsWith("f"))
				{
					String[] arr = currLine.split(" ");
					int v1 = Integer.parseInt(arr[1].substring(0, arr[1].indexOf("/")));
					int v2 = Integer.parseInt(arr[2].substring(0, arr[2].indexOf("/")));
					int v3 = Integer.parseInt(arr[3].substring(0, arr[3].indexOf("/")));
					triList.add(new Triangle(v1, v2, v3));
				}
			}
		}
		
		
		for(int i = 0; i < faces.size(); i++)
		{
			String currLine = faces.get(i);
			if(currLine.startsWith("f"))
			{
				String[] arr = currLine.split(" ");
				int v1 = Integer.parseInt(arr[1].substring(0, arr[1].indexOf("/")));
				int v2 = Integer.parseInt(arr[2].substring(0, arr[2].indexOf("/")));
				int v3 = Integer.parseInt(arr[3].substring(0, arr[3].indexOf("/")));
				
				size = triList.size();
				for(int c = 0; c < size; c++)
				{
					Triangle checkTri = triList.get(c);
					if(v1 == checkTri.v1 &&
					   v2 == checkTri.v2 &&
					   v3 == checkTri.v3)
					{
						if(checkTri.used)
						{
							c = size;
							faces.remove(i);
							i--;
						}
						else
						{
							checkTri.used = true;
						}
					}
				}
			}
		}
		
		
		//assign all triangles using the same 3 vertex coordinates to use the same vertex index
		for (int triIdx = 0; triIdx < faces.size(); triIdx++)
		{
			String tri = faces.get(triIdx);
			if (tri.startsWith("f"))
			{
				String[] arr = tri.split(" ");
				int v1 = Integer.parseInt(arr[1].substring(0, arr[1].indexOf("/")));
				int v2 = Integer.parseInt(arr[2].substring(0, arr[2].indexOf("/")));
				int v3 = Integer.parseInt(arr[3].substring(0, arr[3].indexOf("/")));
				
				int newv1 = -1;
				int newv2 = -1;
				int newv3 = -1;
				
				String[] vertex1 = verticies.get(v1-1).split(" ");
				float x1 = Float.parseFloat(vertex1[1]);
				float y1 = Float.parseFloat(vertex1[2]);
				float z1 = Float.parseFloat(vertex1[3]);
				
				String[] vertex2 = verticies.get(v2-1).split(" ");
				float x2 = Float.parseFloat(vertex2[1]);
				float y2 = Float.parseFloat(vertex2[2]);
				float z2 = Float.parseFloat(vertex2[3]);
				
				String[] vertex3 = verticies.get(v3-1).split(" ");
				float x3 = Float.parseFloat(vertex3[1]);
				float y3 = Float.parseFloat(vertex3[2]);
				float z3 = Float.parseFloat(vertex3[3]);
				
				
				
				for (int i = 0; i < verticies.size(); i++)
				{
					String[] thisVertex = verticies.get(i).split(" ");
					float x = Float.parseFloat(thisVertex[1]);
					float y = Float.parseFloat(thisVertex[2]);
					float z = Float.parseFloat(thisVertex[3]);
					
					if (x == x1 && y == y1 && z == z1)
					{
						newv1 = i+1;
					}
					
					if (x == x2 && y == y2 && z == z2)
					{
						newv2 = i+1;
					}
					
					if (x == x3 && y == y3 && z == z3)
					{
						newv3 = i+1;
					}
				}
				
				String[] dat1 = arr[1].split("/");
				String[] dat2 = arr[2].split("/");
				String[] dat3 = arr[3].split("/");

				faces.set(triIdx, "f "+
						newv1+"/"+dat1[1]+"/"+dat1[2]+" "+
						newv2+"/"+dat2[1]+"/"+dat2[2]+" "+
						newv3+"/"+dat3[1]+"/"+dat3[2]);
			}
		}
		
		
		
		//put all unused verticies at the origin
		for (int i = 0; i < verticies.size(); i++)
		{
			int idxToLookFor = i+1;
			
			//am i used by ANY triangle?
			boolean used = false;
			for (int t = 0; t < faces.size(); t++)
			{
				String tri = faces.get(t);
				if (tri.startsWith("f"))
				{
					String[] arr = tri.split(" ");
					int v1 = Integer.parseInt(arr[1].substring(0, arr[1].indexOf("/")));
					int v2 = Integer.parseInt(arr[2].substring(0, arr[2].indexOf("/")));
					int v3 = Integer.parseInt(arr[3].substring(0, arr[3].indexOf("/")));
					
					if (v1 == idxToLookFor ||
						v2 == idxToLookFor ||
						v3 == idxToLookFor)
					{
						used = true;
						break;
					}
				}
			}
			
			if (used == false)
			{
				verticies.set(i, "v 0 0 0");
			}
		}
		
		
		ArrayList<String> newFile = new ArrayList<String>();
		newFile.add(material);
		
		while(verticies.size() > 0)
		{
			newFile.add(verticies.get(0));
			verticies.remove(0);
		}
		while(textures.size() > 0)
		{
			newFile.add(textures.get(0));
			textures.remove(0);
		}
		while(normals.size() > 0)
		{
			newFile.add(normals.get(0));
			normals.remove(0);
		}
		while(faces.size() > 0)
		{
			newFile.add(faces.get(0));
			faces.remove(0);
		}
		
		return newFile;
	}
	
	//Organize the materials so that all the triangles using the same material
	//are part of the same group
	public static void organizeOBJ(ArrayList<String> rawFile)
	{
		String materialFile = "";
		String currMaterial = "";
		ArrayList<String> verticies = new ArrayList<String>();
		ArrayList<String> textures = new ArrayList<String>();
		ArrayList<String> normals = new ArrayList<String>();
		
		HashMap<String, ArrayList<String>> groupMap = new HashMap<String, ArrayList<String>>();
		
		int size = rawFile.size();
		for(int i = 0; i < size; i++)
		{
			String currLine = rawFile.get(i);
			if(currLine.startsWith("mtllib"))
			{
				materialFile = currLine;
			}
			else if(currLine.startsWith("v "))
			{
				verticies.add(currLine);
			}
			else if(currLine.startsWith("vn"))
			{
				normals.add(currLine);
			}
			else if(currLine.startsWith("vt"))
			{
				textures.add(currLine);
			}
			else if(currLine.startsWith("usemtl"))
			{
				String[] arr = currLine.split(" ");
				
				currMaterial = arr[1];
				
				if(!groupMap.containsKey(currMaterial))
				{
					ArrayList<String> faceGroup = new ArrayList<String>();
					faceGroup.add(currLine);
					groupMap.put(currMaterial, faceGroup);
				}
			}
			else if(currLine.startsWith("f"))
			{
				groupMap.get(currMaterial).add(currLine);
			}
		}
		
		
		rawFile.clear();

		rawFile.add(materialFile);
		
		while(verticies.size() > 0)
		{
			rawFile.add(verticies.get(0));
			verticies.remove(0);
		}
		while(textures.size() > 0)
		{
			rawFile.add(textures.get(0));
			textures.remove(0);
		}
		while(normals.size() > 0)
		{
			rawFile.add(normals.get(0));
			normals.remove(0);
		}
		
		Iterator<String> keySetIterator = groupMap.keySet().iterator(); 
		while(keySetIterator.hasNext())
		{ 
			String key = keySetIterator.next(); 
			ArrayList<String> group = groupMap.get(key);
			while(group.size() > 0)
			{
				rawFile.add(group.get(0));
				group.remove(0);
			}
		}
	}
		
	//If there exist two different materials that use the same exact image for the texture, 
	//the materials will be merged into one material, and the references in the .obj will
	//be updated to use this new material
	public static void fixMaterials(ArrayList<String> rawOBJFile, ArrayList<String> rawMTLFile)
	{
		ArrayList<Material> matList = new ArrayList<Material>();
		
		//Scan through material file and match the material names
		//with their filenames
		int size = rawMTLFile.size();
		for(int i = 0; i < size; i++)
		{
			String currLine = rawMTLFile.get(i);
			if(currLine.startsWith("newmtl"))
			{
				String[] arr = currLine.split(" ");
				String name = arr[1];
				matList.add(new Material(name));
			}
			else if(currLine.indexOf("map_Kd") >= 0)
			{
				String fileName = currLine.substring(currLine.indexOf("map_Kd")+7);
				matList.get(matList.size()-1).fileName = fileName;
			}
			else if(currLine.indexOf("Ns ") >= 0)
			{
				matList.get(matList.size()-1).shineDamper = currLine;
				
				String valueString = currLine.substring(currLine.indexOf("Ns ")+3);
				valueString = valueString.trim();
				float value = Float.parseFloat(valueString);
				
				if (value <= 0)
				{
					matList.get(matList.size()-1).shineDamper = "Ns 20";
				}
			}
			else if(currLine.indexOf("Ni ") >= 0)
			{
				matList.get(matList.size()-1).reflectivity = currLine;
			}
			else if(currLine.indexOf("Tr ") >= 0)
			{
				matList.get(matList.size()-1).transparency = currLine;
			}
			else if(currLine.indexOf("d ") >= 0)
			{
				matList.get(matList.size()-1).fakeLighting = currLine;
			}
			else if(currLine.indexOf("glow ") >= 0)
			{
				matList.get(matList.size()-1).glowAmount = currLine;
			}
			else if(currLine.indexOf("scrollX ") >= 0)
			{
				matList.get(matList.size()-1).scrollX = currLine;
			}
			else if(currLine.indexOf("scrollY ") >= 0)
			{
				matList.get(matList.size()-1).scrollY = currLine;
			}
		}
		
		//filename to material
		HashMap<String, Material> materialMap = new HashMap<String, Material>();
		
		//material name to filename
		HashMap<String, String> matNameMap = new HashMap<String, String>();
		
		//Fill material map
		for (Material mat : matList)
		{
			if (materialMap.containsKey(mat.fileName) == false)
			{
				materialMap.put(mat.fileName, mat);
			}
			matNameMap.put(mat.name, mat.fileName);
		}
		
		size = rawOBJFile.size();
		for (int i = 0; i < size; i++)
		{
			String currLine = rawOBJFile.get(i);
			if (currLine.startsWith("usemtl"))
			{
				String[] arr = currLine.split(" ");
				String name = arr[1];
				String fileName = "";
				
				//search through entire matList until you find the image filename
				//associated with your material name
				fileName = matNameMap.get(name);
				
				//now that you know what file you need for the image, 
				//set your new material name to the first one in the material
				//array that uses the same image filename
				rawOBJFile.remove(i);
				rawOBJFile.add(i, "usemtl "+materialMap.get(fileName).name);
			}
		}
		
		rawMTLFile.clear();
		rawMTLFile.add("# Material Count: "+materialMap.size());
		rawMTLFile.add("");
		
		for (Map.Entry<String, Material> entry : materialMap.entrySet())
		{
			Material mat = entry.getValue();
			rawMTLFile.add("newmtl "  + mat.name);
			rawMTLFile.add(mat.shineDamper);
			rawMTLFile.add(mat.reflectivity);
			rawMTLFile.add(mat.transparency);
			rawMTLFile.add(mat.fakeLighting);
			rawMTLFile.add(mat.scrollX);
			rawMTLFile.add(mat.scrollY);
			rawMTLFile.add("map_Kd "  + mat.fileName);
			rawMTLFile.add("");
		}
	}
	
	public static ArrayList<String> removeMaterial(ArrayList<String> file, String mat)
	{
		String currMat = "";
		for(int i = 0; i < file.size(); i++)
		{
			String currLine = file.get(i);
			if(currLine.startsWith("usemtl"))
			{
				String[] arr = currLine.split(" ");
				currMat = arr[1];
			}
			else if(currLine.startsWith("f"))
			{
				if(currMat.equals(mat))
				{
					file.remove(i);
					i--;
				}
			}
		}
		
		return file;
	}
	
	//Changes material names that start with a prefix to a new name
	public static ArrayList<String> changeMaterials(ArrayList<String> file, String prefix, String newName)
	{
		String currMat = "";
		for(int i = 0; i < file.size(); i++)
		{
			String currLine = file.get(i);
			if(currLine.startsWith("usemtl"))
			{
				String[] arr = currLine.split(" ");
				currMat = arr[1];
				if(currMat.startsWith(prefix))
				{
					file.set(i, arr[0]+" "+newName);
				}
			}
		}
		
		return file;
	}
	
	//Changes material filenames that are equal to an old name to a new name
	public static ArrayList<String> changeMaterialsFilenames(ArrayList<String> mtl, String newName, String oldName)
	{
		String currMat = "";
		for (int i = 0; i < mtl.size(); i++)
		{
			String currLine = mtl.get(i);
			if (currLine.contains("map_Kd"))
			{
				String[] arr = currLine.split(" ");
				currMat = arr[1];
				if (currMat.equals(oldName))
				{
					mtl.set(i, arr[0]+" "+newName);
				}
			}
		}
		
		return mtl;
	}
	
	/*
	public static ArrayList<String> changeMaterialsName(ArrayList<String> obj, ArrayList<String> mtl, String newName, String oldName)
	{
		String currMat = "";
		for (int i = 0; i < obj.size(); i++)
		{
			String currLine = obj.get(i);
			if (currLine.startsWith("usemtl"))
			{
				String[] arr = currLine.split(" ");
				currMat = arr[1];
				
				for (int c = 0; c < mtl.size(); c++)
				{
					String mtlLine = mtl.get(c);
					String[] dat = mtlLine.split(" ");
					
					if (dat.length >= 2)
					{
						if (dat[0].equals("newmtl") && dat[1].equals(currMat))
						{
							for (c = c+1; c < mtl.size(); c++)
							{
								String mtlLine2 = mtl.get(c);
								String[] dat2 = mtlLine2.split(" ");
								
								if (dat2[0].contains("map_Kd") && dat2[1].equals(oldName))
								{
									
								}
							}
						}
					}
				}
			}
		}
		
		return file;
	}
	*/
	
	//Removes everything after and including # per line
	public static ArrayList<String> removeComments(ArrayList<String> file)
	{
		for (int i = 0; i < file.size(); i++)
		{
			String currLine = file.get(i);
			
			if (currLine.contains("#"))
			{
				String[] arr = currLine.split("#");
				
				if (currLine.indexOf("#") == 0)
				{
					file.set(i, "");
				}
				else
				{
					file.set(i, arr[0]);
				}
			}
		}
		
		return file;
	}
}
