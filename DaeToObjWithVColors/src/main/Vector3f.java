package main;

public class Vector3f
{
	public float x;
	public float y;
	public float z;
	
	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void normalize()
	{
		float length = (float)Math.sqrt(x*x + y*y + z*z);
		x = x/length;
		y = y/length;
		z = z/length;
	}
}
