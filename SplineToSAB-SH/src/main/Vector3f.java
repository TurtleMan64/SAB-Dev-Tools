package main;

public class Vector3f
{
	float x;
	float y;
	float z;
	
	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f cross(Vector3f other)
	{
		float x_ = y*other.z - z*other.y;
		float y_ = z*other.x - x*other.z;
		float z_ = x*other.y - y*other.x;
		
		return new Vector3f(x_, y_, z_);
	}
	
	public void negate()
	{
		x*=-1;
		y*=-1;
		z*=-1;
	}
	
	public void normalise()
	{
		float len = (float)Math.sqrt(x*x+y*y+z*z);
		x/=len;
		y/=len;
		z/=len;
	}
}
