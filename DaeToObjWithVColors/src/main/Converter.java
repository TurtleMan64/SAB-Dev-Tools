package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Converter
{
	public static void replaceTabsAndTrim(ArrayList<String> dae)
	{
		for (int i = 0; i < dae.size(); i++)
		{
			String line = dae.get(i);
			line = line.replaceAll("\t", " ");
			line = line.trim();
			dae.set(i, line);
		}
	}
	
	enum Library
	{
		Collada,
		Images,
		Effects,
		Materials,
		Geometries,
		Scenes;
	}
	
	public static void convert(ArrayList<String> dae, ArrayList<String> outOBJ, ArrayList<String> outMTL, String fileName)
	{
		HashMap<String, String> libraryImages = new HashMap<String, String>();
		HashMap<String, String> libraryEffects = new HashMap<String, String>();
		//HashMap<String, String> libraryMaterials = new HashMap<String, String>();
		HashMap<String, Mesh> libraryGeometries = new HashMap<String, Mesh>();
		HashMap<String, SceneNode> libraryNodes = new HashMap<String, SceneNode>();
		
		//material name to material
		HashMap<String, Material> materialSet = new HashMap<String, Material>();
		
		Library currentRoot = Library.Collada;
		
		String savedString = "";
		
		for (int i = 0; i < dae.size(); i++)
		{
			String line = dae.get(i);
			//System.out.println(line);
			if (line.equals("<library_images>"))
			{
				currentRoot = Library.Images;
			}
			else if (line.equals("<library_effects>"))
			{
				currentRoot = Library.Effects;
			}
			else if (line.equals("<library_materials>"))
			{
				currentRoot = Library.Materials;
			}
			else if (line.equals("<library_geometries>"))
			{
				currentRoot = Library.Geometries;
			}
			else if (line.equals("<library_visual_scenes>"))
			{
				currentRoot = Library.Scenes;
			}
			else
			{
				switch (currentRoot)
				{
					case Images:
					{
						if (line.startsWith("<image "))
						{
							int idxId = line.indexOf("id");
							int idxQuote1 = line.indexOf("\"", idxId);
							int idxQuote2 = line.indexOf("\"", idxQuote1+1);
							String imageId = line.substring(idxQuote1+1, idxQuote2);
							
							String nextLine = dae.get(i+1);
							int idx1 = nextLine.indexOf(">");
							int idx2 = nextLine.indexOf("</");
							String imageFileName = nextLine.substring(idx1+1, idx2);
							
							libraryImages.put(imageId,  imageFileName);
							
							i+=1;
						}
						break;
					}
					
					case Effects:
					{
						if (line.startsWith("<effect "))
						{
							int idxId = line.indexOf("id");
							int idxQuote1 = line.indexOf("\"", idxId);
							int idxQuote2 = line.indexOf("\"", idxQuote1+1);
							String effectId = line.substring(idxQuote1+1, idxQuote2);
							savedString = effectId;
						}
						else if (line.startsWith("<init_from>"))
						{
							int idx1 = line.indexOf(">");
							int idx2 = line.indexOf("</");
							String imageId = line.substring(idx1+1, idx2);
							
							libraryEffects.put(savedString,  imageId);
						}
						break;
					}
					
					case Materials:
					{
						//if (line.startsWith("<material "))
						//{
						//	int idxId = line.indexOf("id");
						//	int idxQuote1 = line.indexOf("\"", idxId);
						//	int idxQuote2 = line.indexOf("\"", idxQuote1+1);
						//	String effectId = line.substring(idxQuote1+1, idxQuote2);
						//	savedString = effectId;
						//}
						//else if (line.startsWith("<instance_effect "))
						//{
						//	int idx1 = line.indexOf(">");
						//	int idx2 = line.indexOf("</");
						//	String imageId = line.substring(idx1+1, idx2);
						//	
						//	libraryEffects.put(savedString,  imageId);
						//}
						break;
					}
					
					case Geometries:
					{
						if (line.startsWith("<geometry "))
						{
							int idxId = line.indexOf("id");
							int idxQuote1 = line.indexOf("\"", idxId);
							int idxQuote2 = line.indexOf("\"", idxQuote1+1);
							String geometryId = line.substring(idxQuote1+1, idxQuote2);
							savedString = geometryId;
							libraryGeometries.put(geometryId, new Mesh());
						}
						else if (line.startsWith("<float_array "))
						{
							Mesh mesh = libraryGeometries.get(savedString);
							
							int idx1 = line.indexOf(">");
							int idx2 = line.indexOf("</");
							String floatArray = line.substring(idx1+1, idx2);
							String[] floats = floatArray.split(" ");
							
							ArrayList<Float> nextArray = null;
							if (mesh.positionsRAW == null)
							{
								mesh.positionsRAW = new ArrayList<Float>();
								nextArray = mesh.positionsRAW;
							}
							else if (mesh.normalsRAW == null)
							{
								mesh.normalsRAW = new ArrayList<Float>();
								nextArray = mesh.normalsRAW;
							}
							else if (mesh.uvsRAW == null)
							{
								mesh.uvsRAW = new ArrayList<Float>();
								nextArray = mesh.uvsRAW;
							}
							else if (mesh.colorsRAW == null)
							{
								mesh.colorsRAW = new ArrayList<Float>();
								nextArray = mesh.colorsRAW;
							}
							
							for (String val : floats)
							{
								float f = Float.parseFloat(val);
								nextArray.add(f);
							}
						}
						else if (line.startsWith("<p>"))
						{
							Mesh mesh = libraryGeometries.get(savedString);
							
							int idx1 = line.indexOf(">");
							int idx2 = line.indexOf("</");
							String intArray = line.substring(idx1+1, idx2);
							String[] indices = intArray.split(" ");
							
							ArrayList<Integer> indicesArray = new ArrayList<Integer>();
							mesh.indices = indicesArray;
							
							for (String val : indices)
							{
								int f = Integer.parseInt(val);
								indicesArray.add(f);
							}
						}
						break;
					}
					
					case Scenes:
					{
						if (line.startsWith("<node "))
						{
							int idxId = line.indexOf("id");
							int idxQuote1 = line.indexOf("\"", idxId);
							int idxQuote2 = line.indexOf("\"", idxQuote1+1);
							String nodeId = line.substring(idxQuote1+1, idxQuote2);
							savedString = nodeId;
							SceneNode node = new SceneNode();
							node.name = nodeId;
							libraryNodes.put(nodeId, node);
						}
						else if (line.startsWith("<matrix "))
						{
							SceneNode node = libraryNodes.get(savedString);
							
							int idx1 = line.indexOf(">");
							int idx2 = line.indexOf("</");
							String floatArray = line.substring(idx1+1, idx2);
							String[] floats = floatArray.split(" ");
							
							node.transform = new Matrix4x4();
							double[] vals = new double[16];
							
							for (int c = 0; c < 16; c++)
							{
								float f = Float.parseFloat(floats[c]);
								vals[c] = (double)f;
							}
							
							node.transform.setColumnFirst(vals);
							node.transform.rotateX(-Math.PI/2);
						}
						else if (line.startsWith("<instance_geometry "))
						{
							SceneNode node = libraryNodes.get(savedString);
							
							int idxId = line.indexOf("url");
							int idxQuote1 = line.indexOf("\"", idxId);
							int idxQuote2 = line.indexOf("\"", idxQuote1+1);
							String meshId = line.substring(idxQuote1+2, idxQuote2); //+2 to get rid of the #
							
							node.mesh = libraryGeometries.get(meshId);
						}
						else if (line.startsWith("<instance_material "))
						{
							SceneNode node = libraryNodes.get(savedString);
							
							int idxId = line.indexOf("symbol");
							int idxQuote1 = line.indexOf("\"", idxId);
							int idxQuote2 = line.indexOf("\"", idxQuote1+1);
							String materialId = line.substring(idxQuote1+1, idxQuote2);
							
							int idxPNG = materialId.indexOf("_png");
							String filename = materialId.substring(0, idxPNG);
							Material mat = new Material(filename);
							mat.fileName = filename+".png";
							if (!materialSet.containsKey(mat.name))
							{
								materialSet.put(mat.name, mat);
							}
							node.material = mat;
						}
						break;
					}
					
					default:
						break;
				}
			}
		}
		
		ArrayList<String> v  = new ArrayList<String>();
		ArrayList<String> vt = new ArrayList<String>();
		ArrayList<String> vn = new ArrayList<String>();
		ArrayList<String> f  = new ArrayList<String>();
		
        for (Map.Entry<String, SceneNode> entry : libraryNodes.entrySet())
        {
            SceneNode node = entry.getValue();
            Mesh mesh = node.mesh;
            
            int vOffset  = v .size()+1;
            int vtOffset = vt.size()+1;
            int vnOffset = vn.size()+1;
            
            for (int c = 0; c < mesh.positionsRAW.size(); c+=3)
            {
            	Vertex vertex = new Vertex(mesh.positionsRAW.get(c), mesh.positionsRAW.get(c+1), mesh.positionsRAW.get(c+2));
            	
            	Vector3f positionTransformed = node.transform.transformPoint(vertex.x, vertex.y, vertex.z);
            	vertex.x = positionTransformed.x;
            	vertex.y = positionTransformed.y;
            	vertex.z = positionTransformed.z;
        		
            	mesh.vertices.add(vertex);
            }
            
            for (int c = 0; c < mesh.normalsRAW.size(); c+=3)
            {
            	Vector3f normal = new Vector3f(mesh.normalsRAW.get(c), mesh.normalsRAW.get(c+1), mesh.normalsRAW.get(c+2));
            	
        		Matrix4x4 noRot = new Matrix4x4(node.transform);
        		noRot.removeTranslation();
        		Vector3f normalTransformed = noRot.transformPoint(normal.x, normal.y, normal.z);
        		normalTransformed.normalize();
            	normal = normalTransformed;
        		
            	mesh.normals.add(normal);
            }
            
            for (int c = 0; c < mesh.uvsRAW.size(); c+=2)
            {
            	Vector2f uv = new Vector2f(mesh.uvsRAW.get(c), mesh.uvsRAW.get(c+1));
            	mesh.uvs.add(uv);
            }
            
            //go through and assign colors to vertices
            if (mesh.colorsRAW != null)
            {
            	for (int tri = 0; tri < mesh.indices.size(); tri+=12)
                {
            		float  r1 = mesh.colorsRAW.get(3*mesh.indices.get(tri +  3) + 0);
            		float  g1 = mesh.colorsRAW.get(3*mesh.indices.get(tri +  3) + 1);
            		float  b1 = mesh.colorsRAW.get(3*mesh.indices.get(tri +  3) + 2);
            		float  r2 = mesh.colorsRAW.get(3*mesh.indices.get(tri +  7) + 0);
            		float  g2 = mesh.colorsRAW.get(3*mesh.indices.get(tri +  7) + 1);
            		float  b2 = mesh.colorsRAW.get(3*mesh.indices.get(tri +  7) + 2);
            		float  r3 = mesh.colorsRAW.get(3*mesh.indices.get(tri + 11) + 0);
            		float  g3 = mesh.colorsRAW.get(3*mesh.indices.get(tri + 11) + 1);
            		float  b3 = mesh.colorsRAW.get(3*mesh.indices.get(tri + 11) + 2);
            		
            		mesh.vertices.get(mesh.indices.get(tri + 0)).r = r1;
            		mesh.vertices.get(mesh.indices.get(tri + 0)).g = g1;
            		mesh.vertices.get(mesh.indices.get(tri + 0)).b = b1;
            		mesh.vertices.get(mesh.indices.get(tri + 4)).r = r2;
            		mesh.vertices.get(mesh.indices.get(tri + 4)).g = g2;
            		mesh.vertices.get(mesh.indices.get(tri + 4)).b = b2;
            		mesh.vertices.get(mesh.indices.get(tri + 8)).r = r3;
            		mesh.vertices.get(mesh.indices.get(tri + 8)).g = g3;
            		mesh.vertices.get(mesh.indices.get(tri + 8)).b = b3;
                }
            }
            
            //print the vertices, normals, uv's, and faces to the master obj file lists
            
            for (Vertex vertex: mesh.vertices)
            {
            	v.add("v "+vertex.x+" "+vertex.y+" "+vertex.z+" "+vertex.r+" "+vertex.g+" "+vertex.b);
            }
            
            for (Vector2f uv: mesh.uvs)
            {
            	vt.add("vt "+uv.x+" "+uv.y);
            }
            
            for (Vector3f normal: mesh.normals)
            {
            	vn.add("vn "+normal.x+" "+normal.y+" "+normal.z);
            }
            
            f.add("usemtl "+node.material.name);
            
            if (mesh.colorsRAW != null)
            {
	            for (int tri = 0; tri < mesh.indices.size()/12; tri++)
	            {
	            	f.add("f "+
	            			(mesh.indices.get(12*tri + 0)+vOffset)+"/"+(mesh.indices.get(12*tri +  2)+vtOffset)+"/"+(mesh.indices.get(12*tri + 1)+vnOffset)+" "+
	            			(mesh.indices.get(12*tri + 4)+vOffset)+"/"+(mesh.indices.get(12*tri +  6)+vtOffset)+"/"+(mesh.indices.get(12*tri + 5)+vnOffset)+" "+
	            			(mesh.indices.get(12*tri + 8)+vOffset)+"/"+(mesh.indices.get(12*tri + 10)+vtOffset)+"/"+(mesh.indices.get(12*tri + 9)+vnOffset));
	            }
            }
            else
            {
            	for (int tri = 0; tri < mesh.indices.size()/9; tri++)
	            {
	            	f.add("f "+
	            			(mesh.indices.get(9*tri + 0)+vOffset)+"/"+(mesh.indices.get(9*tri + 2)+vtOffset)+"/"+(mesh.indices.get(9*tri + 1)+vnOffset)+" "+
	            			(mesh.indices.get(9*tri + 3)+vOffset)+"/"+(mesh.indices.get(9*tri + 5)+vtOffset)+"/"+(mesh.indices.get(9*tri + 4)+vnOffset)+" "+
	            			(mesh.indices.get(9*tri + 6)+vOffset)+"/"+(mesh.indices.get(9*tri + 8)+vtOffset)+"/"+(mesh.indices.get(9*tri + 7)+vnOffset));
	            }
            }
        }
		
		outOBJ.clear();
		outMTL.clear();
		
		outOBJ.add("mtllib " + fileName + ".mtl");
		for (String str : v)
		{
			outOBJ.add(str);
		}
		for (String str : vt)
		{
			outOBJ.add(str);
		}
		for (String str : vn)
		{
			outOBJ.add(str);
		}
		for (String str : f)
		{
			outOBJ.add(str);
		}
		
		for (Map.Entry<String, Material> entry : materialSet.entrySet())
        {
			outMTL.add("newmtl "+entry.getValue().name);
			outMTL.add("Ns 20");
			outMTL.add("Ni 0");
			outMTL.add("map_Kd "+entry.getValue().fileName);
			outMTL.add("");
        }
	}
}
