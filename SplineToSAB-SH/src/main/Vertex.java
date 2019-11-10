package main;

public class Vertex implements Comparable<Vertex>
{
	Vector3f pos = new Vector3f(0, 0, 0);
	Vector3f normal = new Vector3f(0, 1, 0);
	
	public Vertex(float x, float y, float z)
	{
		pos.x = x;
		pos.y = y;
		pos.z = z;
	}
	
    public int compareTo(Vertex v)
    {
    	if (this.pos.z > v.pos.z)
    	{
    		return -1;
    	}
    	else
    	{
    		return 1;
    	}
    }
    
    public String toString()
    {
    	return ""+pos.x+" "+pos.y+" "+pos.z+" "+normal.x+" "+normal.y+" "+normal.z;
    }
}
