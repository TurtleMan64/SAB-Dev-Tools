package main;

import java.util.ArrayList;
import java.util.List;

public class Material 
{
	public String name;
	public String fileName;
	public List<String> fileNameOthers;
	public String shineDamper;
	public String reflectivity;
	public String transparency;
	public String fakeLighting;
	public String glowAmount;
	public String scrollX;
	public String scrollY;
	public int numImages;
	public String mixType;
	public String fogScale;
	
	public Material(String name)
	{
		this.name = name;
		fileName       = "";
		fileNameOthers = new ArrayList<String>();
		shineDamper    = "Ns 20";
		reflectivity   = "Ni 0";
		transparency   = "";
		fakeLighting   = "";
		glowAmount     = "";
		scrollX        = "";
		scrollY        = "";
		numImages      = 1;
		mixType        = "";
		fogScale       = "";
	}
}
