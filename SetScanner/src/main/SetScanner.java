package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class SetScanner 
{
	public static PrintWriter out = null;

	public static void main(String[] args)
	{
		String s = "SET/MetalHarborSET.txt";
		File file = new File(s);

		try
		{
			out = new PrintWriter("Output/MetalHarborObjects.txt", "UTF-8");
			out.println("#Generated Objects");
			Scanner input = new Scanner(file);

			while (input.hasNextLine())
			{
				String type = input.next();
				float xrot = input.nextFloat()/182f;
				float yrot = input.nextFloat()/182f;
				float zrot = input.nextFloat()/182f;
				float x = input.nextFloat();
				float y = input.nextFloat();
				float z = input.nextFloat();
				float var1 = input.nextFloat();
				float var2 = input.nextFloat();
				float var3 = input.nextFloat();
				input.nextLine();
				
				//parseTwinkleParkAct2 (type, xrot, yrot, zrot, x, y, z, var1, var2, var3);
				//parseEmeraldCoastAct2(type, xrot, yrot, zrot, x, y, z, var1, var2, var3);
				//parseSpeedHighwayAct1(type, xrot, yrot, zrot, x, y, z, var1, var2, var3);
				parseMetalHarbor     (type, xrot, yrot, zrot, x, y, z, var1, var2, var3);
			}
			
			input.close();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found?");
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void parseTwinkleParkAct1(String type, 
			  float xrot, float yrot, float zrot,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		y+=7000;
		
		if (type.equals("0") || type.equals("55"))    //single ring
		{         
			outSingleRing(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1")) //line of rings
		{
			outRingGroupSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("4") || type.equals("5") || type.equals("6")) //Spring
		{
			outSpring(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("7")) //Dashpad
		{
			outDashpad(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("9")) //Spikeball
		{
			outSpikeballSpin(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("F") || type.equals("10")) //Item box
		{
			outItemCapsuleSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
	}
	
	public static void parseTwinkleParkAct2(String type, 
										  float xrot, float yrot, float zrot,
										  float x,    float y,    float z,
										  float var1, float var2, float var3)
	{
		x*=1.1f;
		y*=1.1f;
		z*=1.1f;
		z-=20000;
		y+=200;
		
		if (type.equals("0") || type.equals("55"))    //single ring
		{         
			outSingleRing(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1")) //line of rings
		{
			outRingGroupSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("4") || type.equals("5") || type.equals("6")) //Spring
		{
			outSpring(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("7")) //Dashpad
		{
			outDashpad(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("9")) //Spikeball
		{
			outSpikeballSpin(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("47") || type.equals("48")) //Floating Pad
		{
			out.println("50 "+x+" "+(y+6)+" "+z+" "+yrot);
		}
		else if (type.equals("F") || type.equals("10")) //Item box
		{
			outItemCapsuleSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
	}
	
	public static void parseEmeraldCoastAct1(String type, 
			  float xrot, float yrot, float zrot,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		x*=1.1f;
		y*=1.1f;
		z*=1.1f;
		
		if (type.equals("0"))    //single ring
		{         
			outSingleRing(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("15")) //line of rings
		{
			outRingGroupSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1") || type.equals("2")) //Spring
		{
			outSpring(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("3")) //Dashpad
		{
			outDashpad(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("4")) //Spikeball Spinnin
		{
			outSpikeballSpin(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("27")) //Water Platform
		{
			out.println("50 "+x+" "+(y+6)+" "+z+" "+yrot);
		}
		else if (type.equals("31")) //Horizontal Palmtree
		{
			out.println("50 "+x+" "+(y+6)+" "+z+" "+yrot);
		}
		else if (type.equals("42")) //Big Rock
		{
			if (x > 2714 && x < 6163)
				outBigRock(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("24")) //Rock Platform
		{
			outRockPlatform(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("37") || type.equals("38")) //Seagull
		{
			outSeagull(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("D") || type.equals("52")) //Item Box
		{
			outItemCapsuleSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
	}
	
	public static void parseEmeraldCoastAct2(String type, 
			  float xrot, float yrot, float zrot,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		x*=1.1f;
		y*=1.1f;
		z*=1.1f;
		
		z+=7000;
		
		if (type.equals("0"))    //single ring
		{         
			outSingleRing(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("15")) //line of rings
		{
			outRingGroupSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1") || type.equals("2")) //Spring
		{
			outSpring(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("3")) //Dashpad
		{
			outDashpad(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("4")) //Spikeball Spinnin
		{
			outSpikeballSpin(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("27")) //Water Platform
		{
			out.println("50 "+x+" "+(y+6)+" "+z+" "+yrot);
		}
		else if (type.equals("31")) //Horizontal Palmtree
		{
			out.println("50 "+x+" "+(y+6)+" "+z+" "+yrot);
		}
		else if (type.equals("42")) //Big Rock
		{
			outBigRock(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("24")) //Rock Platform
		{
			outRockPlatform(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("37") || type.equals("38")) //Seagull
		{
			outSeagull(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("D") || type.equals("52")) //Item Box
		{
			outItemCapsuleSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
	}
	
	public static void parseSpeedHighwayAct1(String type, 
			  float xrot, float yrot, float zrot,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		x*=1.4f;
		y*=1.4f;
		z*=1.4f;
		y+=5500;
		
		if (type.equals("68")) //line of rings
		{
			outRingGroupSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1") || type.equals("2") || type.equals("10")) //Spring
		{
			outSpring(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("3")) //Dashpad
		{
			if (x < 300 || x > 2000)
				outDashpad(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("4")) //Spikeball Spin
		{
			outSpikeballSpin(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("5C")) //Spinner
		{
			outSpinner(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("20")) //TurnAsi
		{
			outTurnAsi(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("D") || type.equals("74")) //Item Box
		{
			outItemCapsuleSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
	}
	
	public static void parseMetalHarbor(String type, 
			  float xrot, float yrot, float zrot,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		x = x*1.25f;
		y = y*1.25f;
		z = z*1.25f;
		
		y = y+409.7f;
		
		if (type.equals("0")) //single ring
		{
			outSingleRing(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1")) //line of rings
		{
			outRingLineSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("2")) //Ring circle
		{
			outRingCircleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("3") || type.equals("4") || type.equals("5")) //Spring
		{
			outSpring(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("7")) //Dashpad
		{
			outDashpadSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("38") || type.equals("39") || type.equals("3A") || type.equals("3B")) //Beetle, Gold, Moving beetle, gun hawk
		{
			outSpinner(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("37")) //Spikeball Spin
		{
			outSpikeballSpin(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("A") || type.equals("B")) //Item Box
		{
			outItemCapsuleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("53")) //Seagull
		{
			outSeagull(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
	}
	
	public static void parseWeaponsBed(String type, 
			  float xrot, float yrot, float zrot,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		x = x*1.1f;
		y = y*1.1f;
		z = z*1.1f;
		
		y = y+362;
		
		yrot+=270;
		
		if (type.equals("0")) //single ring
		{
			outSingleRing(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1")) //line of rings
		{
			outRingLineSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("2")) //Ring circle
		{
			outRingCircleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("3") || type.equals("4") || type.equals("5")) //Spring
		{
			outSpring(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("7")) //Dashpad
		{
			outDashpadSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("38")) //Beetle
		{
			outSpinner(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("37")) //Spikeball Spin
		{
			outSpikeballSpin(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("A") || type.equals("B")) //Item Box
		{
			outItemCapsuleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
	}
	
	public static void parseGreenHill(String type, 
			  float xrot, float yrot, float zrot,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		x = x*1.5f;
		y = y*1.5f;
		z = z*1.5f;
		
		y = y+2280+8;
		
		yrot+=270;
		
		if (type.equals("0")) //single ring
		{
			outSingleRing(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1")) //line of rings
		{
			outRingLineSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("2")) //Ring circle
		{
			outRingCircleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("3") || type.equals("4") || type.equals("5")) //Spring
		{
			outSpring(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("7")) //Dashpad
		{
			outDashpadSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("38") || type.equals("39") || type.equals("3A") || type.equals("3B")) //Beetle
		{
			outSpinner(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("37")) //Spikeball Spin
		{
			outSpikeballSpin(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("A") || type.equals("B")) //Item Box
		{
			outItemCapsuleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("46")) //Rock
		{
			y+=0.5;
			out.println("16 "+x+" "+y+" "+z+" "+yrot);
		}
		else if (type.equals("47")) //Tree
		{
			out.println("17 "+x+" "+y+" "+z+" "+yrot+" "+(1.2+0.35*(Math.random()-0.5)));
		}
		else if (type.equals("42")) //Sunflower
		{
			out.println("18 "+x+" "+y+" "+z+" "+yrot);
		}
		else if (type.equals("43")) //purple flower
		{
			out.println("19 "+x+" "+y+" "+z+" "+yrot);
		}
		else if (type.equals("49")) //totem blank
		{
			out.println("20 "+x+" "+y+" "+z+" "+yrot);
		}
		else if (type.equals("48")) //totem wings
		{
			out.println("21 "+x+" "+y+" "+z+" "+yrot);
		}
		else if (type.equals("45")) //emerald grass
		{
			out.println("22 "+x+" "+y+" "+z+" "+yrot);
		}
		else if (type.equals("4A")) //floating platform
		{
			out.println("24 "+x+" "+y+" "+z+" "+yrot);
		}
		else if (type.equals("4B")) //floating platform
		{
			out.println("23 "+x+" "+y+" "+z+" "+yrot);
		}
	}
	
	public static void outSingleRing(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.println("0"+" "+x+" "+y+" "+z);
	}
	
	public static void outRingLineSA2(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		//20 separation actually means 30 separation
		//5 means 15
		//y-=10;
		int numRings = (int)Math.floor(var3);
		float sep = var1+10f;
		float radius = sep*numRings;
			
		Vector3f pos = new Vector3f(0, 0, radius);
		Vector3f rotLine = new Vector3f(0, -radius, 0);
		rotateAboutXAxis(pos, rotX);
		rotateAboutZAxis(pos, rotZ);
		rotateAboutXAxis(rotLine, rotX);
		rotateAboutZAxis(rotLine, rotZ);
		Vector3f result = rotatePoint(
				0,         0,         0,
				rotLine.x, rotLine.y, rotLine.z,
				pos.x,     pos.y,     pos.z,
				rotY);
		pos.x = result.x;
		pos.y = result.y;
		pos.z = result.z;
		Vector3f o = new Vector3f(pos.x/2, pos.y/2, pos.z/2);
		pos.x+=x;
		pos.y+=y;
		pos.z+=z;
		out.println("3 "+(x-o.x)+" "+(y-o.y)+" "+(z-o.z)+" "+(pos.x-o.x)+" "+(pos.y-o.y)+" "+(pos.z-o.z)+" "+numRings);
	}
	
	public static void outRingCircleSA2(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		int numRings = (int)Math.floor(var3);
		float radius = var1+10;
		out.println("4"+" "+x+" "+y+" "+z+" "+" "+radius+" "+numRings);
	}
	
	public static void outRingGroupSADX(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		if (var3 != 0) //Circle
		{
			out.println("4"+" "+x+" "+y+" "+z+" "+" "+var2+" "+((int)var1));
		}
		else //Line
		{
			float radius = ((int)var1)*var2;
			Vector3f pos = new Vector3f(0, 0, radius);
			Vector3f rotLine = new Vector3f(0, -radius, 0);
			rotateAboutXAxis(pos, rotX);
			rotateAboutZAxis(pos, rotZ);
			rotateAboutXAxis(rotLine, rotX);
			rotateAboutZAxis(rotLine, rotZ);
			Vector3f result = rotatePoint(
					0,         0,         0,
					rotLine.x, rotLine.y, rotLine.z,
					pos.x,     pos.y,     pos.z,
					rotY);
			pos.x = result.x;
			pos.y = result.y;
			pos.z = result.z;
			Vector3f o = new Vector3f(pos.x/2, pos.y/2, pos.z/2);
			pos.x+=x;
			pos.y+=y;
			pos.z+=z;
			out.println("3 "+(x-o.x)+" "+(y-o.y)+" "+(z-o.z)+" "+(pos.x-o.x)+" "+(pos.y-o.y)+" "+(pos.z-o.z)+" "+(((int)var1)+1));
		}
	}
	
	public static void outSpring(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		Vector3f look = new Vector3f(0, 10, 0);
		
		rotateAboutXAxis(look, rotX);
		rotateAboutZAxis(look, rotZ);
		
		float angH = (float)Math.toDegrees(Math.atan2(-look.z, look.x));
		float angV = (float)Math.toDegrees(Math.atan2(look.y, Math.sqrt(look.x*look.x+look.z*look.z)));
		
		out.println("1 "+x+" "+y+" "+z+" "+(angH)+" "+(angV)+" "+(Math.max(var2, 8))+" "+"40");
	}
	
	public static void outDashpad(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		Vector3f look = endPointSADX(rotX, rotY, rotZ, 10);
		
		float angH = (float)Math.toDegrees(Math.atan2(-look.z, look.x));
		float angV = (float)Math.toDegrees(Math.atan2(look.y, Math.sqrt(look.x*look.x+look.z*look.z)));
		
		out.println("2 "+x+" "+(y+2)+" "+z+" "+(angH)+" "+(angV)+" "+(13)+" "+(angH)+" "+"20");
	}
	
	public static void outDashpadSA2(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		Vector3f look = endPointSADX(rotX, rotY, rotZ, 10);
		
		float angH = (float)Math.toDegrees(Math.atan2(-look.z, look.x));
		float angV = (float)Math.toDegrees(Math.atan2(look.y, Math.sqrt(look.x*look.x+look.z*look.z)));
		
		out.println("2 "+x+" "+(y+2)+" "+z+" "+(angH)+" "+(angV)+" "+(var1)+" "+(angH)+" "+Math.round(var2));
	}
	
	public static void outSpinner(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.println("28 "+x+" "+(y)+" "+z);
	}
	
	public static void outSpikeballSpin(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.println("47 "+x+" "+(y)+" "+z+" "+( var1*5+35)+" "+rotY);
	}
	
	public static void outTurnAsi(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.println("55 "+x+" "+y+" "+z+" "+(rotY+90));
	}
	
	public static void outBigRock(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		String type = "0";
		if (var2 == 1.0f)
		{
			type = "1";
		}
		float scale = (float)(1.1-Math.random()*0.2);
		out.println("56 "+x+" "+y+" "+z+" "+(rotX)+" "+(rotY+1369)+" "+rotZ+" "+scale+" "+type);
	}
	
	public static void outRockPlatform(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.println("57 "+x+" "+y+" "+z+" "+(rotY));
	}
	
	public static void outSeagull(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		float flyRad      = (float)(225+75*Math.random());
		float flySpeed    = (float)((0.09+0.04*Math.random())*(300/flyRad));
		
		float radAmp      = (float)(15+15*Math.random());
		float radDelta    = (float)(0.05+0.2*Math.random());
		
		float heightAmp   = (float)(15+15*Math.random());
		float heightDelta = (float)(0.03+0.1*Math.random());
		
		out.println("58 "+x+" "+(y+20)+" "+z+" "+(rotY)+" "+flySpeed+" "+flyRad+" "+radAmp+" "+radDelta+" "+heightAmp+" "+heightDelta);
	}
	
	public static void outItemCapsuleSADX(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		rotZ+=90;
		out.println("27 "+x+" "+(y)+" "+z+" "+(rotY)+" "+rotZ+" "+(int)(var1));
	}
	
	public static void outItemCapsuleSA2(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		rotZ+=90;
		
		int type = (int)(var1);
		
		switch (type)
		{
			//Speed Shoes
			case 0: type = 0; break;
				
			//5 Rings
			case 1: type = 2; break;
			
			//Extra Life
			case 2: type = 6; break;
				
			//10 Rings
			case 3: type = 3; break;
				
			//20 Rings
			case 4: type = 4; break;
				
			//Green Shield
			case 5: type = 5; break;
				
			//Bomb
			case 6: type = 7; break;
				
			//Health
			case 7: type = 6; break;
				
			//Electric Shield
			case 8: type = 8; break;
				
			//Nothing
			case 9: type = 6; break;
				
			//Invincible
			case 10:
			default: type = 1; break;
		}
		
		out.println("27 "+x+" "+(y)+" "+z+" "+(rotY)+" "+rotZ+" "+type);
	}
	
	public static Vector3f endPointSADX(float rotX, float rotY, float rotZ, float radius)
	{
		Vector3f pos = new Vector3f(0, 0, radius);
		Vector3f rotLine = new Vector3f(0, -radius, 0);
		rotateAboutXAxis(pos, rotX);
		rotateAboutZAxis(pos, rotZ);
		rotateAboutXAxis(rotLine, rotX);
		rotateAboutZAxis(rotLine, rotZ);
		Vector3f result = rotatePoint(
				0,         0,         0,
				rotLine.x, rotLine.y, rotLine.z,
				pos.x,     pos.y,     pos.z,
				rotY);
		
		return result;
	}
	
	//ang in degrees
	public static void rotateAboutYAxis(Vector3f point, float ang)
	{
		ang = (float)Math.toRadians(ang);
		float origX = point.x;
		float origZ = point.z;
		
		point.x =  (float)(origX*Math.cos(ang)-origZ*Math.sin(ang));
		point.z = -(float)(origZ*Math.cos(ang)+origX*Math.sin(ang));
	}
	
	//ang in degrees
	public static void rotateAboutZAxis(Vector3f point, float ang)
	{
		ang = (float)Math.toRadians(ang);
		float origX = point.x;
		float origY = point.y;
		
		point.x = (float)(origX*Math.cos(ang)-origY*Math.sin(ang));
		point.y = (float)(origY*Math.cos(ang)+origX*Math.sin(ang));
	}
	
	//ang in degrees
	public static void rotateAboutXAxis(Vector3f point, float ang)
	{
		ang = -(float)Math.toRadians(ang);
		float origZ = point.z;
		float origY = point.y;
		
		point.z = (float)(origZ*Math.cos(ang)-origY*Math.sin(ang));
		point.y = (float)(origY*Math.cos(ang)+origZ*Math.sin(ang));
	}
	
	//Equation from https://sites.google.com/site/glennmurray/Home/rotation-matrices-and-formulas
	//Point that axis goes through,
	//direction of axis,
	//point to rotate, 
	//angle of rotation
	public static Vector3f rotatePoint(
		double a, double b, double c,
		double u, double v, double w,
		double x, double y, double z,
		double degrees)
	{
		Vector3f result = new Vector3f(0,0,0);
		
		double l = Math.sqrt(u*u + v*v + w*w);

		double l2 = l*l;
		
		double theta = -Math.toRadians(degrees);

		double u2 = u*u;
		double v2 = v*v;
		double w2 = w*w;
		double cosT = Math.cos(theta);
		double oneMinusCosT = 1 - cosT;
		double sinT = Math.sin(theta);

		result.x = (float)(((a*(v2 + w2) - u*(b*v + c*w - u*x - v*y - w*z)) * oneMinusCosT
			+ l2*x*cosT
			+ l*(-c*v + b*w - w*y + v*z)*sinT) / l2);

		result.y = (float)(((b*(u2 + w2) - v*(a*u + c*w - u*x - v*y - w*z)) * oneMinusCosT
			+ l2*y*cosT
			+ l*(c*u - a*w + w*x - u*z)*sinT) / l2);

		result.z = (float)(((c*(u2 + v2) - w*(a*u + b*v - u*x - v*y - w*z)) * oneMinusCosT
			+ l2*z*cosT
			+ l*(-b*u + a*v - v*x + u*y)*sinT) / l2);
		
		return result;
	}
}