package main;

public class Triangle 
{
	public int v1;
	public int v2;
	public int v3;
	public boolean used;
	
	public Triangle(int v1, int v2, int v3)
	{
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		used = false;
	}
	
	@Override
	public String toString()
	{
		return "["+v1+", "+v2+", "+v3+"]";
	}
}
