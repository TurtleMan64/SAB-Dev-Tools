package main;

public class Material 
{
	public String name;
	public String fileName;
	public String shineDamper;
	public String reflectivity;
	public String transparency;
	public String fakeLighting;
	public String glowAmount;
	public String scrollX;
	public String scrollY;
	
	public Material(String name)
	{
		this.name = name;
		fileName     = "map_Kd temp.png";
		shineDamper  = "Ns 20";
		reflectivity = "Ni 0";
		transparency = "Tr 1";
		fakeLighting = "d 1";
		glowAmount   = "glow 0";
		scrollX      = "scrollX 0";
		scrollY      = "scrollY 0";
	}
}
