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
	
	public void add(Vector3f toAdd)
	{
		this.x += toAdd.x;
		this.y += toAdd.y;
		this.z += toAdd.z;
	}
	
	public void add(float x, float y, float z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public float length()
	{
		return (float)Math.sqrt(x*x + y*y + z*z);
	}
	
	public Vector3f scaleCopy(float scale)
	{
		return new Vector3f(x*scale, y*scale, z*scale);
	}
	
	public void setLength(float newLength)
	{
		float currLength = length();
		if (currLength > 0.0000001f)
		{
			float ratio = newLength/currLength;
			x *= ratio;
			y *= ratio;
			z *= ratio;
		}
		else 
		{
			//std::fprintf(stdout, "Warning: Trying to set length of a very small vector [%f %f %f]\n", x, y, z);
			float xa = Math.abs(x);
			float ya = Math.abs(y);
			float max = Math.max(xa, Math.max(ya, Math.abs(z)));
			if (xa == max)
			{
				y = 0;
				z = 0;
				if (x > 0)
				{
					x = newLength;
				}
				else
				{
					x = -newLength;
				}
			}
			else if (ya == max)
			{
				x = 0;
				z = 0;
				if (y > 0)
				{
					y = newLength;
				}
				else
				{
					y = -newLength;
				}
			}
			else
			{
				x = 0;
				y = 0;
				if (z > 0)
				{
					z = newLength;
				}
				else
				{
					z = -newLength;
				}
			}
		}
	}
}
