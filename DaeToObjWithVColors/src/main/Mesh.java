package main;

import java.util.ArrayList;

public class Mesh
{
	public ArrayList<Float> positionsRAW = null;
	public ArrayList<Float> normalsRAW = null;
	public ArrayList<Float> uvsRAW = null;
	public ArrayList<Float> colorsRAW = null;
	public ArrayList<Integer> indices = null;
	
	public ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
	public ArrayList<Vector2f> uvs = new ArrayList<Vector2f>();
}
