package main;

public class SABObject implements Comparable<SABObject>
{
	private int id;
	private String line;
	
	public SABObject(String line)
	{
		this.line = line;
		String[] split = line.split(" ");
		id = Integer.parseInt(split[0]);
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getLine()
	{
		return line;
	}
	
	@Override
    public int compareTo(SABObject other)
	{
		if (id > other.getId())
		{
			return 1;
		}
		else if (id < other.getId())
		{
			return -1;
		}
		
        return 0;
    }
}
