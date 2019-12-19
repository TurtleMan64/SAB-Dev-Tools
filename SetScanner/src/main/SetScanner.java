package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SetScanner 
{
	public static ArrayList<String> out = new ArrayList<String>();

	public static void main(String[] args)
	{
		String level = "SkyRailSAB";
		
		String s = "SET/"+level+"SET.txt";
		File file = new File(s);

		try
		{
			PrintWriter writer = new PrintWriter("Output/"+level+"Objects.txt", "UTF-8");
			writer.println("######## Generated Objects ########");
			writer.println();
			Scanner input = new Scanner(file);

			while (input.hasNextLine())
			{
				String type = input.next();
				int xrot = input.nextInt();
				int yrot = input.nextInt();
				int zrot = input.nextInt();
				float xrotDeg = xrot/182.04444444444f;
				float yrotDeg = yrot/182.04444444444f;
				float zrotDeg = zrot/182.04444444444f;
				float x = input.nextFloat();
				float y = input.nextFloat();
				float z = input.nextFloat();
				float var1 = input.nextFloat();
				float var2 = input.nextFloat();
				float var3 = input.nextFloat();
				input.nextLine();
				
				//parseTwinkleParkAct2  (type, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
				//parseEmeraldCoastAct1 (type, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
				//parseSpeedHighwayAct1 (type, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
				//parseSpeedHighwayTails(type, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
				//parseMetalHarbor      (type, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
				//parseRadicalHighway   (type, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
				//parseWeaponsBed       (type, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
				//parseGreenHill        (type, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
				//parsePyramidCave      (type, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
				parseSkyRail          (type, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
				//parseCityEscape       (type, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
				//parseGreenForest      (type, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
				//parseWildCanyon       (type, xrot, yrot, zrot, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
				//parseDryLagoon        (type, xrot, yrot, zrot, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
				//parseMeteorHerd       (type, xrot, yrot, zrot, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
				//parsePumpkinHill      (type, xrot, yrot, zrot, xrotDeg, yrotDeg, zrotDeg, x, y, z, var1, var2, var3);
			}
			
			input.close();
			
			ArrayList<SABObject> objects = new ArrayList<SABObject>();
			for (String line : out)
			{
				SABObject o = new SABObject(line);
				objects.add(o);
			}
			
			Collections.sort(objects);
			
			for (SABObject o : objects)
			{
				writer.println(o.getLine());
			}
			
			writer.close();
		}
		catch (FileNotFoundException e)
		{
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
			//outSingleRing(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1")) //line of rings
		{
			//outRingGroupSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("4") || type.equals("5") || type.equals("6")) //Spring
		{
			//outSpringSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("7")) //Dashpad
		{
			//outDashpadSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("9")) //Spikeball
		{
			//outSpikeballSpinSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("F") || type.equals("10")) //Item box
		{
			//outItemCapsuleSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("15")) //checkpoint
		{
			outCheckpointSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
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
			//outSingleRing(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1")) //line of rings
		{
			//outRingGroupSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("4") || type.equals("5") || type.equals("6")) //Spring
		{
			//outSpringSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("7")) //Dashpad
		{
			//outDashpadSADX(x, y, z, xrot, yrot, zrot, var1, 40, var3);
		}
		else if (type.equals("9")) //Spikeball
		{
			//outSpikeballSpinSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("47") || type.equals("48")) //Floating Pad
		{
			//out.add("50 "+x+" "+(y+6)+" "+z+" "+yrot);
		}
		else if (type.equals("F") || type.equals("10")) //Item box
		{
			//outItemCapsuleSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("15")) //checkpoint
		{
			outCheckpointSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
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
			//outSingleRing(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("15")) //line of rings
		{
			//outRingGroupSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1") || type.equals("2")) //Spring
		{
			//outSpringSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("3")) //Dashpad
		{
			//outDashpadSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("4")) //Spikeball Spinnin
		{
			//outSpikeballSpinSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("27")) //Water Platform
		{
			//out.add("50 "+x+" "+(y+6)+" "+z+" "+yrot);
		}
		else if (type.equals("31")) //Horizontal Palmtree
		{
			//out.add("50 "+x+" "+(y+6)+" "+z+" "+yrot);
		}
		else if (type.equals("42")) //Big Rock
		{
			//if (x > 2714 && x < 6163)
				//outBigRock(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("24")) //Rock Platform
		{
			//outRockPlatform(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("37") || type.equals("38")) //Seagull
		{
			//outSeagull(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("D") || type.equals("52")) //Item Box
		{
			//outItemCapsuleSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("13")) //checkpoint
		{
			outCheckpointSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
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
		
		z+=17000;
		
		if (type.equals("0"))    //single ring
		{         
			//outSingleRing(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("15")) //line of rings
		{
			//outRingGroupSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1") || type.equals("2")) //Spring
		{
			//outSpringSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("3")) //Dashpad
		{
			//outDashpadSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("4")) //Spikeball Spinnin
		{
			//outSpikeballSpinSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("27")) //Water Platform
		{
			//out.add("50 "+x+" "+(y+6)+" "+z+" "+yrot);
		}
		else if (type.equals("31")) //Horizontal Palmtree
		{
			//out.add("50 "+x+" "+(y+6)+" "+z+" "+yrot);
		}
		else if (type.equals("42")) //Big Rock
		{
			//outBigRock(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("24")) //Rock Platform
		{
			//outRockPlatform(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("37") || type.equals("38")) //Seagull
		{
			//outSeagull(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("D") || type.equals("52")) //Item Box
		{
			//outItemCapsuleSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("13")) //checkpoint
		{
			outCheckpointSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
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
			outSpringSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("3")) //Dashpad
		{
			if (x < 300 || x > 2000)
				outDashpadSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("4")) //Spikeball Spin
		{
			outSpikeballSpinSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("5C") || type.equals("5D") || type.equals("5E") || type.equals("5F") || type.equals("60") || type.equals("61")) //Spinner attack, Spinner float, Spinner electric, cop, unidusA, unidusB
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
		else if (type.equals("43"))
		{
			outLamppost(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("49")) //Traffic Cone A
		{
			out.add("67 "+x+" "+y+" "+z+" "+xrot+" "+yrot+" "+zrot+" 0");
		}
		else if (type.equals("4A")) //Traffic Cone B
		{
			out.add("67 "+x+" "+y+" "+z+" "+xrot+" "+yrot+" "+zrot+" 1");
		}
		else if (type.equals("21")) //Searchlight
		{
			out.add("68 "+x+" "+y+" "+z+" "+xrot+" "+yrot+" "+zrot);
		}
		else if (type.equals("28")) //Platform
		{
			out.add("88 "+x+" "+y+" "+z+" "+yrot+" 1");
		}
		else if (type.equals("26")) //Platform small
		{
			out.add("88 "+x+" "+y+" "+z+" "+yrot+" 0.4");
		}
		else if (type.equals("13")) //checkpoint
		{
			outCheckpointSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
	}
	
	public static void parseSpeedHighwayTails(String type, 
			  float xrot, float yrot, float zrot,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		x*=1.4f;
		y*=1.4f;
		z*=1.4f;
		y+=5500;
		
		if (x < 6338 || z < 6495)
		{
			return;
		}
		
		if (type.equals("68")) //line of rings
		{
			outRingGroupSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1") || type.equals("2") || type.equals("10")) //Spring
		{
			outSpringSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("3")) //Dashpad
		{
			outDashpadSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("4")) //Spikeball Spin
		{
			outSpikeballSpinSADX(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("5C") || type.equals("5D") || type.equals("5E") || type.equals("5F") || type.equals("60") || type.equals("61")) //Spinner attack, Spinner float, Spinner electric, cop, unidusA, unidusB
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
		else if (type.equals("43"))
		{
			outLamppost(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("49")) //Traffic Cone A
		{
			out.add("67 "+x+" "+y+" "+z+" "+xrot+" "+yrot+" "+zrot+" 0");
		}
		else if (type.equals("4A")) //Traffic Cone B
		{
			out.add("67 "+x+" "+y+" "+z+" "+xrot+" "+yrot+" "+zrot+" 1");
		}
		else if (type.equals("21")) //Searchlight
		{
			out.add("68 "+x+" "+y+" "+z+" "+xrot+" "+yrot+" "+zrot);
		}
		else if (type.equals("28")) //Platform
		{
			out.add("88 "+x+" "+y+" "+z+" "+yrot+" 1");
		}
		else if (type.equals("13")) //checkpoint
		{
			outCheckpointSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
	}
	
	public static void parseMetalHarbor(String type, 
			  float xrot, float yrot, float zrot,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		//x = x*1.25f;
		//y = y*1.25f;
		//z = z*1.25f;
		
		//y = y+409.7f;
		
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
		else if (type.equals("3") || type.equals("4")) //Spring
		{
			//if (x < 2782 || z < -3435)
			{
				outSpringSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
			}
		}
		else if (type.equals("5")) //3 Springs
		{
			outTripleSpringSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("6")) //BigJump
		{
			outSpeedRampSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("7")) //Dashpad
		{
			outDashpadSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("15")) //Rocket
		{
			outRocket(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("38") || type.equals("39") || type.equals("3A") || type.equals("3B")) //Beetle, Gold, Moving beetle, gun hawk
		{
			outSpinner(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("37")) //Spikeball Spin
		{
			//outSpikeballSpinSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("A") || type.equals("B") || type.equals("C")) //Item Box
		{
			int boxType = 0;
			if (type.equals("A"))
			{
				boxType = 0;
			}
			else if (type.equals("B"))
			{
				boxType = 1;
			}
			else if (type.equals("C"))
			{
				boxType = 1;
			}
			outItemCapsuleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3, boxType);
		}
		else if (type.equals("53")) //Seagull
		{
			//outSeagull(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("8")) //Checkpoint
		{
			outCheckpointSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("E")) //Goal Ring
		{
			//outGoalRingSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
	}
	
	public static void parseRadicalHighway(String type, 
			  float xrot, float yrot, float zrot,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		//x = x*1.35f;
		//y = y*1.35f;
		//z = z*1.35f;
		
		//y = y+11000f;
		
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
		else if (type.equals("3") || type.equals("4")) //Spring
		{
			//if (x < 2782 || z < -3435)
			{
				outSpringSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
			}
		}
		else if (type.equals("5")) //3 Springs
		{
			outTripleSpringSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("6")) //BigJump
		{
			outSpeedRampSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("7")) //Dashpad
		{
			outDashpadSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("15")) //Rocket
		{
			outRocket(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("38") || type.equals("39") || type.equals("3E") || type.equals("58")) //Beetle, Hunter, Jet, Gold
		{
			outSpinner(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("37")) //Spikeball Spin
		{
			outSpikeballSpinSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("A") || type.equals("B") || type.equals("C")) //Item Box
		{
			int boxType = 0;
			if (type.equals("A"))
			{
				boxType = 0;
			}
			else if (type.equals("B"))
			{
				boxType = 1;
			}
			else if (type.equals("C"))
			{
				boxType = 1;
			}
			outItemCapsuleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3, boxType);
		}
		else if (type.equals("3A")) //Trick Ramp
		{
			out.add("85 "+x+" "+y+" "+z+" "+(yrot-90)+" "+(-xrot));
		}
		else if (type.equals("8")) //Checkpoint
		{
			outCheckpointSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
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
		
		//yrot+=270;
		
		if (type.equals("0")) //single ring
		{
			outSingleRing(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1")) //line of rings
		{
			//A rotation of all 0 should result in a line going in the -z direciton.
			//Not sure why that isnt the case... some math is going wrong somewhere.
			//I can simply add 180 to the yrot for weapons bed but that might be wrong later on...
			outRingLineSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("2")) //Ring circle
		{
			outRingCircleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("3") || type.equals("4")) //Spring
		{
			outSpringSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("5")) //3 Springs
		{
			outTripleSpringSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
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
			outSpikeballSpinSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("A") || type.equals("B") || type.equals("C")) //Item Box
		{
			int boxType = 0;
			if (type.equals("A"))
			{
				boxType = 0;
			}
			else if (type.equals("B"))
			{
				boxType = 1;
			}
			else if (type.equals("C"))
			{
				boxType = 1;
			}
			outItemCapsuleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3, boxType);
		}
		else if (type.equals("8")) //Checkpoint
		{
			outCheckpointSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
	}
	
	public static void parseGreenHill(String type, 
			  float xrot, float yrot, float zrot,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		//x = x*1.5f;
		//y = y*1.5f;
		//z = z*1.5f;
		
		//y = y+2280+8;
		
		//yrot+=270;
		
		if (type.equals("0")) //single ring
		{
			//if (z < -5222 && z > -5693)
			//{
			//	y += 4;
			//}
			//else
			//{
			//	y -= 6;
			//}
			outSingleRing(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1")) //line of rings
		{
			//y -= 6;
			//yrot-=270;
			outRingLineSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("2")) //Ring circle
		{
			outRingCircleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("3") || type.equals("4")) //Spring
		{
			//y += 4;
			//yrot-=270;
			outSpringSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("5")) //3 Springs
		{
			//y += 4;
			//yrot-=270;
			outTripleSpringSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("6")) //BigJump
		{
			outSpeedRampSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("7")) //Dashpad
		{
			//yrot-=270;
			outDashpadSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("38") || type.equals("39") || type.equals("3A") || type.equals("3B")) //Beetle
		{
			outSpinner(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("37")) //Spikeball Spin
		{
			outSpikeballSpinSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("A") || type.equals("B") || type.equals("B")) //Item Box
		{
			//yrot-=270;
			int boxType = 0;
			if (type.equals("A"))
			{
				boxType = 0;
			}
			else if (type.equals("B"))
			{
				boxType = 1;
			}
			else if (type.equals("C"))
			{
				boxType = 1;
			}
			outItemCapsuleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3, boxType);
		}
		else if (type.equals("46")) //Rock
		{
			//y+=0.25;
			//out.add("16 "+x+" "+y+" "+z+" "+yrot);
		}
		else if (type.equals("47")) //Tree
		{
			//out.add("17 "+x+" "+y+" "+z+" "+yrot+" "+(1.2+0.35*(Math.random()-0.5)));
		}
		else if (type.equals("42")) //Sunflower
		{
			//out.add("18 "+x+" "+y+" "+z+" "+yrot);
		}
		else if (type.equals("43")) //purple flower
		{
			//out.add("19 "+x+" "+y+" "+z+" "+yrot);
		}
		else if (type.equals("49")) //totem blank
		{
			//out.add("20 "+x+" "+y+" "+z+" "+yrot);
		}
		else if (type.equals("48")) //totem wings
		{
			//out.add("21 "+x+" "+y+" "+z+" "+yrot);
		}
		else if (type.equals("45")) //emerald grass
		{
			//out.add("22 "+x+" "+y+" "+z+" "+yrot);
		}
		else if (type.equals("4A")) //floating platform
		{
			//y += 16;
			//out.add("24 "+x+" "+y+" "+z+" "+yrot);
		}
		else if (type.equals("4B")) //falling platform
		{
			//out.add("23 "+x+" "+y+" "+z+" "+yrot);
		}
		else if (type.equals("8")) //Checkpoint
		{
			//yrot-=270;
			outCheckpointSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
	}
	
	public static void parseCityEscape(String type, 
			  float xrot, float yrot, float zrot,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
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
		else if (type.equals("3") || type.equals("4")) //Spring
		{
			outSpringSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("5")) //3 Springs
		{
			outTripleSpringSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("7")) //Dashpad
		{
			//outDashpadSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("38") || type.equals("39") || type.equals("5B") || type.equals("5D")) //Beetle
		{
			outSpinner(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("37")) //Spikeball Spin
		{
			outSpikeballSpinSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("A") || type.equals("B") || type.equals("C")) //Item Box
		{
			int boxType = 0;
			if (type.equals("A"))
			{
				boxType = 0;
			}
			else if (type.equals("B"))
			{
				boxType = 1;
			}
			else if (type.equals("C"))
			{
				boxType = 1;
			}
			outItemCapsuleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3, boxType);
		}
		else if (type.equals("8")) //Checkpoint
		{
			outCheckpointSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
	}
	
	public static void parseWildCanyon(String type, 
			  int xrot,   int yrot,   int zrot,
			  float xrotDeg, float yrotDeg, float zrotDeg,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		x = x*1.3f;
		y = y*1.3f;
		z = z*1.3f;
		
		if (type.equals("F") || type.equals("54")) //Emerald Piece / Moving Piece
		{
			outEmeraldPiece(x, y, z, xrot, yrot, zrot, var1, var2, var3, false);
		}
		else if (type.equals("0")) //single ring
		{
			outSingleRing(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("1")) //line of rings
		{
			outRingLineSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("2")) //Ring circle
		{
			outRingCircleSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("3") || type.equals("4")) //Spring
		{
			outSpringSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("5")) //3 Springs
		{
			outTripleSpringSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("7")) //Dashpad
		{
			outDashpadSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("38") || type.equals("39") || type.equals("3E") || type.equals("49")) //Gun Hawk, Beetle, Rhino, Gold Beetle
		{
			outSpinner(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("37")) //Spikeball Spin
		{
			outSpikeballSpinSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("A") || type.equals("B") || type.equals("C")) //Item Box
		{
			int boxType = 0;
			if (type.equals("A"))
			{
				boxType = 0;
			}
			else if (type.equals("B"))
			{
				boxType = 1;
			}
			else if (type.equals("C"))
			{
				boxType = 1;
			}
			outItemCapsuleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3, boxType);
		}
		else if (type.equals("48"))
		{
			outWarpPanel(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1*1.3f, var2*1.3f, var3*1.3f);
		}
	}
	
	public static void parseDryLagoon(String type, 
			  int xrot,   int yrot,   int zrot,
			  float xrotDeg, float yrotDeg, float zrotDeg,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		x = x*1.2f;
		y = y*1.2f;
		z = z*1.2f;
		
		if (z > 800)
		{
			y = y-222;
		}
		
		if (type.equals("F") || type.equals("5E")) //Emerald Piece / Moving Piece
		{
			outEmeraldPiece(x, y, z, xrot, yrot, zrot, var1, var2, var3, false);
		}
		else if (type.equals("0")) //single ring
		{
			outSingleRing(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("1")) //line of rings
		{
			outRingLineSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("2")) //Ring circle
		{
			float newVar1 = ((var1+10)*1.2f)-10;
			if (z > 170 && z < 320)
			{
				y += 4;
			}
			outRingCircleSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, newVar1, var2, var3);
		}
		else if (type.equals("3") || type.equals("4")) //Spring
		{
			outSpringSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("5")) //3 Springs
		{
			outTripleSpringSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("7")) //Dashpad
		{
			outDashpadSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg,  var1, var2, var3);
		}
		else if (type.equals("38") || type.equals("39") || type.equals("3A") || type.equals("3B")) //Beetle, Gold, Moving beetle, gun hawk
		{
			outSpinner(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("37")) //Spikeball Spin
		{
			outSpikeballSpinSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("A") || type.equals("B") || type.equals("C")) //Item Box
		{
			int boxType = 0;
			if (type.equals("A"))
			{
				boxType = 0;
			}
			else if (type.equals("B"))
			{
				boxType = 1;
			}
			else if (type.equals("C"))
			{
				boxType = 1;
			}
			outItemCapsuleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3, boxType);
		}
		else if (type.equals("61") || type.equals("45")) //wap panrl
		{
			outWarpPanel(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1*1.2f, var2*1.2f, var3*1.2f);
		}
	}
	
	public static void parseMeteorHerd(String type, 
			  int xrot,   int yrot,   int zrot,
			  float xrotDeg, float yrotDeg, float zrotDeg,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		x = x*1.2f;
		y = y*1.2f;
		z = z*1.2f;
		
		y += 4000;
		
		if (type.equals("F")) //Emerald Piece
		{
			outEmeraldPiece(x, y, z, xrot, yrot, zrot, var1, var2, var3, true);
		}
		else if (type.equals("0")) //single ring
		{
			outSingleRing(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("1")) //line of rings
		{
			outRingLineSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("2")) //Ring circle
		{
			outRingCircleSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("3") || type.equals("4")) //Spring
		{
			outSpringSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("5")) //3 Springs
		{
			outTripleSpringSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("7")) //Dashpad
		{
			outDashpadSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg,  var1, var2, var3);
		}
		else if (type.equals("38") || type.equals("39") || type.equals("3A") || type.equals("3B") || type.equals("3C")) //Beetle, Gold, Moving beetle, gun hawk
		{
			outSpinner(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("37")) //Spikeball Spin
		{
			outSpikeballSpinSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("A") || type.equals("B") || type.equals("C")) //Item Box
		{
			int boxType = 0;
			if (type.equals("A"))
			{
				boxType = 0;
			}
			else if (type.equals("B"))
			{
				boxType = 1;
			}
			else if (type.equals("C"))
			{
				boxType = 1;
			}
			outItemCapsuleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3, boxType);
		}
	}
	
	public static void parsePumpkinHill(String type, 
			  int xrot,   int yrot,   int zrot,
			  float xrotDeg, float yrotDeg, float zrotDeg,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		x = x*1.3f;
		y = y*1.3f;
		z = z*1.3f;
		
		y += 2040;
		
		if (type.equals("F") || type.equals("4E")) //Emerald Piece and moving piece
		{
			outEmeraldPiece(x, y, z, xrot, yrot, zrot, var1, var2, var3, true);
		}
		else if (type.equals("0")) //single ring
		{
			outSingleRing(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("11")) //line of rings
		{
			outRingLineSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("12")) //Ring circle
		{
			outRingCircleSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("1") || type.equals("2")) //Spring
		{
			outSpringSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("13")) //3 Springs
		{
			outTripleSpringSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("3")) //Dashpad
		{
			outDashpadSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg,  var1, var2, var3);
		}
		else if (type.equals("51") || type.equals("52") || type.equals("B") || type.equals("1E")) //Moving Beetle, Gold, gun hawk, ghost
		{
			outSpinner(x, y+5, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("1D")) //Rhino enemy
		{
			outRhinoTank(x, y+3, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("8")) //Spikeball Spin
		{
			outSpikeballSpinSA2(x, y, z, xrotDeg, yrotDeg, zrotDeg, var1, var2, var3);
		}
		else if (type.equals("6") || type.equals("7") || type.equals("D")) //Item Box
		{
			int boxType = 0;
			if (type.equals("6"))
			{
				boxType = 0;
			}
			else if (type.equals("7"))
			{
				boxType = 1;
			}
			else if (type.equals("D"))
			{
				boxType = 1;
			}
			outItemCapsuleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3, boxType);
		}
	}
	
	public static void parsePyramidCave(String type, 
			  float xrot, float yrot, float zrot,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		x = x*1.45f;
		y = y*1.45f;
		z = z*1.45f;
		
		y = y+7500.0f;
		
		if (type.equals("0") || type.equals("F")) //single ring
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
		else if (type.equals("3") || type.equals("4")) //Spring
		{
			outSpringSA2(x, y+5, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("5")) //3 Springs
		{
			outTripleSpringSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("6")) //Speed Ramp
		{
			outSpeedRampSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("7")) //Dashpad
		{
			outDashpadSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("38") || type.equals("39") || type.equals("3A") || type.equals("3B") || type.equals("3C")) //Beetle, Gold, Moving beetle, gun hawk
		{
			outSpinner(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("37")) //Spikeball Spin
		{
			outSpikeballSpinSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("A") || type.equals("B") || type.equals("C")) //Item Box
		{
			int boxType = 0;
			if (type.equals("A"))
			{
				boxType = 0;
			}
			else if (type.equals("B"))
			{
				boxType = 1;
			}
			else if (type.equals("C"))
			{
				boxType = 1;
			}
			outItemCapsuleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3, boxType);
		}
	}
	
	public static void parseSkyRail(String type, 
			  float xrot, float yrot, float zrot,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		if (type.equals("0")) //single ring
		{
			outSingleRing(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("11")) //line of rings
		{
			outRingLineSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("12")) //Ring circle
		{
			outRingCircleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1") || type.equals("2")) //Spring
		{
			outSpringSA2(x, y+5, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("13")) //3 Springs
		{
			outTripleSpringSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("9")) //Speed Ramp
		{
			outSpeedRampSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("A")) //Rocket
		{
			outRocket(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("3")) //Dashpad
		{
			//outDashpadSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("4")) //Checkpoint
		{
			outCheckpointSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("B") || type.equals("1E") || type.equals("26") || type.equals("1D")) //Beetle, Gold, Moving beetle, gun hawk
		{
			outSpinner(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("8")) //Spikeball Spin
		{
			outSpikeballSpinSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("6") || type.equals("7") || type.equals("D")) //Item Box
		{
			int boxType = 0;
			if (type.equals("6"))
			{
				boxType = 0;
			}
			else if (type.equals("7"))
			{
				boxType = 1;
			}
			else if (type.equals("D"))
			{
				boxType = 1;
			}
			outItemCapsuleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3, boxType);
		}
	}
	
	public static void parseGreenForest(String type, 
			  float xrot, float yrot, float zrot,
			  float x,    float y,    float z,
			  float var1, float var2, float var3)
	{
		if (type.equals("0")) //single ring
		{
			outSingleRing(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("22")) //line of rings
		{
			outRingLineSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("23")) //Ring circle
		{
			outRingCircleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("1") || type.equals("2")) //Spring
		{
			outSpringSA2(x, y+5, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("24")) //3 Springs
		{
			outTripleSpringSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("A")) //Speed Ramp
		{
			outSpeedRampSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("3")) //Dashpad
		{
			//outDashpadSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("4")) //Checkpoint
		{
			outCheckpointSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("C") || type.equals("4A") || type.equals("1C") || type.equals("1D")) //Beetle,  Moving beetle/ gun hawk, rhino, hunter
		{
			outSpinner(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("8")) //Spikeball Spin
		{
			outSpikeballSpinSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3);
		}
		else if (type.equals("6") || type.equals("7") || type.equals("1E")) //Item Box
		{
			int boxType = 0;
			if (type.equals("6"))
			{
				boxType = 0;
			}
			else if (type.equals("7"))
			{
				boxType = 1;
			}
			else if (type.equals("1E"))
			{
				boxType = 1;
			}
			outItemCapsuleSA2(x, y, z, xrot, yrot, zrot, var1, var2, var3, boxType);
		}
	}
	
	public static void outEmeraldPiece(float x, float y, float z, int rotX, int rotY, int rotZ, float var1, float var2, float var3, boolean allowDig)
	{
		int ID = 0;//((rotX & 0xFF) << 8);
		ID    += ((rotX & 0xFF00) >> 8);
		
		int num = 0;
		
		if (ID == 1 ||
			ID == 3)
		{
			num = 1;
		}
		else if (ID == 0 ||
				 ID == 2 ||
				 ID == 5 ||
				 ID == 0x0A)
		{
			num = 2;
		}
		else if (ID == 4 ||
				 ID == 7 ||
				 ID == 8)
		{
			num = 3;
		}
		else
		{
			//System.out.add(String.format("0x%02X", rotX & 0xFF)+"     "+String.format("0x%02X", (rotX & 0xFF00) >> 8));
		}
		
		if (y < 3281 && num == 3)
		{
			//System.out.add(""+x+"\t"+y+"\t"+z+"\t"+rotX+"\t"+rotY+"\t"+rotZ+"\t"+var1+"\t"+var2+"\t"+var3);
		}
		
		int dig = 0;
		float xs = 0;
		float ys = 0;
		float zs = 0;
		if (allowDig)
		{
			if (ID == 4 ||
				ID == 5 ||
				ID == 6)
			{
				dig = 1;
				xs = var1;
				ys = var2;
				zs = var3;
			}
		}
		
		if (num != 0 && ID != 7) //ID = 7 is moving pieces
		{
			out.add("73"+" "+x+" "+y+" "+z+" "+num+" "+dig+" "+xs+" "+ys+" "+zs+" 0");
		}
	}
	
	public static void outSingleRing(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.add("0"+" "+x+" "+y+" "+z);
	}
	
	public static void outRingLineSA2(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		//20 separation actually means 30 separation
		//5 means 15
		//y-=10;
		//int numRings = (int)Math.floor(var3);
		//float sep = var1+10f;
		//float radius = sep*numRings;
			
		//Vector3f pos = new Vector3f(0, 0, radius);
		//Vector3f rotLine = new Vector3f(0, -radius, 0);
		//rotateAboutXAxis(pos, rotX);
		//rotateAboutZAxis(pos, rotZ);
		//rotateAboutXAxis(rotLine, rotX);
		//rotateAboutZAxis(rotLine, rotZ);
		//Vector3f result = rotatePoint(
		//		0,         0,         0,
		//		rotLine.x, rotLine.y, rotLine.z,
		//		pos.x,     pos.y,     pos.z,
		//		rotY);
		//pos.x = result.x;
		//pos.y = result.y;
		//pos.z = result.z;
		//Vector3f o = new Vector3f(pos.x/2, pos.y/2, pos.z/2);
		//pos.x+=x;
		//pos.y+=y;
		//pos.z+=z;
		//out.add("3 "+(x-o.x)+" "+(y-o.y)+" "+(z-o.z)+" "+(pos.x-o.x)+" "+(pos.y-o.y)+" "+(pos.z-o.z)+" "+numRings);
		
		
		
		
		//20 separation actually means 30 separation
		//5 means 15
		//y-=10;
		//int numRings = (int)Math.floor(var3);
		//float sep = var1+10f;
		//float radius = sep*numRings;
		
		//Vector3f pos = new Vector3f(0, 0, radius);
		//rotateAboutXAxis(pos, -rotX);
		//rotateAboutYAxis(pos, rotY);
		//pos.x+=x;
		//pos.y+=y;
		//pos.z+=z;
		
		//if ((xrot != 0 && yrot == 0) || (xrot == 0 && yrot != 0))
		{
			//out.add("98 "+x+" "+y+" "+z+" "+pos.x+" "+pos.y+" "+pos.z+" "+numRings);
			//System.out.add(x+" "+y+" "+z+" "+rotX+" "+rotY+" "+rotZ+" "+var1+" "+var2+" "+var3);
			//System.out.add(rotX+" "+rotY+" "+rotZ+"        "+var1+" "+var2+" "+var3);
		}
		
		int numRings = (int)Math.floor(var3);
		numRings = Math.min(numRings, 8);
		numRings = Math.max(numRings, 1);
		float ringDelta = var1+10f;
		
		Vector3f ringDirection = new Vector3f(0, 0, -1);
		
		Vector3f relativeUp = new Vector3f(0, 1, 0);
		float angleDelta = 0.0f;
		if (numRings >= 2)
	    {
	        angleDelta = 1.0f/(numRings-1);
	    }
		
		Vector3f xAxis = new Vector3f(1, 0, 0);
		Vector3f yAxis = new Vector3f(0, 1, 0);
		Vector3f zAxis = new Vector3f(0, 0, 1);
		
		ringDirection = rotatePoint(ringDirection, xAxis, Math.toRadians(rotX));
		ringDirection = rotatePoint(ringDirection, yAxis, Math.toRadians(rotY));
		ringDirection = rotatePoint(ringDirection, zAxis, Math.toRadians(rotZ));
		ringDirection.setLength(ringDelta);
		
		relativeUp = rotatePoint(relativeUp, xAxis, Math.toRadians(rotX));
	    relativeUp = rotatePoint(relativeUp, yAxis, Math.toRadians(rotY));
	    relativeUp = rotatePoint(relativeUp, zAxis, Math.toRadians(rotZ));
		
	    if (Math.abs(var2) > 0.01f)
	    {
	    	for (int i = 0; i < numRings; i++)
	        {
	            Vector3f currOffsetLine = ringDirection.scaleCopy((float)i);
	            Vector3f currOffsetCurve = relativeUp.scaleCopy((float)(var2*Math.sin((i*angleDelta)*(Math.PI/2))));
	            Vector3f ringPos = new Vector3f(x, y, z);
	            ringPos.add(currOffsetLine);
	            ringPos.add(currOffsetCurve);
	            out.add("0 "+ringPos.x+" "+ringPos.y+" "+ringPos.z);
	        }
	    }
	    else
	    {
			int i = numRings-1;
			Vector3f ringPos = ringDirection.scaleCopy((float)i);
			
			ringPos.x+=x;
			ringPos.y+=y;
			ringPos.z+=z;
			out.add("98 "+x+" "+y+" "+z+" "+ringPos.x+" "+ringPos.y+" "+ringPos.z+" "+numRings);
	    }
	}
	
	public static void outRingCircleSA2(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		int numRings = (int)Math.floor(var3);
		float radius = var1+10;
		out.add("99"+" "+x+" "+y+" "+z+" "+" "+radius+" "+numRings);
	}
	
	public static void outRingGroupSADX(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		if (var3 != 0) //Circle
		{
			out.add("99"+" "+x+" "+y+" "+z+" "+" "+var2+" "+((int)var1));
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
			out.add("98 "+(x-o.x)+" "+(y-o.y)+" "+(z-o.z)+" "+(pos.x-o.x)+" "+(pos.y-o.y)+" "+(pos.z-o.z)+" "+(((int)var1)+1));
		}
	}
	
	public static void outSpringSADX(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		Vector3f look = new Vector3f(0, 10, 0);
		
		rotateAboutXAxis(look, rotX);
		rotateAboutZAxis(look, rotZ);
		
		float angH = (float)Math.toDegrees(Math.atan2(-look.z, look.x));
		float angV = (float)Math.toDegrees(Math.atan2(look.y, Math.sqrt(look.x*look.x+look.z*look.z)));
		
		float time  = Math.max(40, var1);
		float power = Math.max(5.5f, var2);
		
		out.add("12 "+x+" "+y+" "+z+" "+(angH)+" "+(angV)+" "+power+" "+time);
	}
	
	public static void outSpringSA2(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		//Vector3f look = new Vector3f(0, 10, 0);
		
		//rotateAboutXAxis(look, rotX);
		//rotateAboutZAxis(look, rotZ);
		
		//float angH = (float)Math.toDegrees(Math.atan2(-look.z, look.x));
		//float angV = (float)Math.toDegrees(Math.atan2(look.y, Math.sqrt(look.x*look.x+look.z*look.z)));
		
		//x+=look.x*0.5;
		//y+=look.y*0.5;
		//z+=look.z*0.5;
		
		//out.add("12 "+x+" "+y+" "+z+" "+(angH)+" "+(angV)+" "+(Math.max(var2, 5.5f))+" "+"40");
		
		
	    Vector3f dir = new Vector3f(0, 1, 0);
	    Vector3f xAxis = new Vector3f(1, 0, 0);
	    Vector3f zAxis = new Vector3f(0, 0, 1);
	    dir = rotatePoint(dir, xAxis, Math.toRadians(rotX));
	    dir = rotatePoint(dir, zAxis, Math.toRadians(rotZ));
	    
	    float power = (var2+5.0f)*60.0f;
	    float controlLockTime = var1/60.0f;
	    int cam = Math.round(var3);
	    if (cam != 1)
	    {
	    	cam = 0;
	    }
	    //controlLockTime = 0.3f;
	    
	    out.add("12 "+x+" "+y+" "+z+" "+(dir.x)+" "+(dir.y)+" "+(dir.z)+" "+(power)+" "+(controlLockTime)+" "+cam);
	}
	
	public static void outTripleSpringSA2(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		Vector3f dir = new Vector3f(0, 0, 1);
		Vector3f yAxis = new Vector3f(0, 1, 0);
	    dir = rotatePoint(dir, yAxis, Math.toRadians(rotY));
	    
	    float power = (var1+5.0f)*60.0f;
	    float controlLockTime = var2/60.0f;
		
		out.add("13 "+x+" "+y+" "+z+" "+(dir.x)+" "+(dir.z)+" "+(power)+" "+(controlLockTime));
	}
	
	public static void outWarpPanel(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.add("86"+" "+x+" "+y+" "+z+" "+" "+(rotY-90)+" "+var1+" "+var2+" "+var3);
	}
	
	public static void outCheckpointSA2(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.add("10"+" "+x+" "+y+" "+z+" "+" "+(rotY-90));
	}
	
	public static void outDashpadSADX(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		//Vector3f look = endPointSADX(rotX, rotY, rotZ, 10);
		
		//float angH = (float)Math.toDegrees(Math.atan2(-look.z, look.x));
		//float angV = (float)Math.toDegrees(Math.atan2(look.y, Math.sqrt(look.x*look.x+look.z*look.z)));
		
		float power = var1;
		float time = var2;
		if (power <= 0)
		{
			power = 14;
		}
		
		if (time <= 0)
		{
			time = 40;
		}
		if (time > 40)
		{
			time = 40;
		}
		
		//out.add("2 "+x+" "+(y+2)+" "+z+" "+(angH)+" "+(angV)+" "+(power)+" "+(angH)+" "+time);
		
		out.add("8 "+x+" "+(y+1)+" "+z+" "+(rotX)+" "+(rotY-90)+" "+rotZ+" "+(power)+" "+(rotY-90)+" "+time);
		
	}
	
	public static void outDashpadSA2(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		//Vector3f look = endPointSADX(rotX, rotY, rotZ, 10);
		
		//float angH = (float)Math.toDegrees(Math.atan2(-look.z, look.x));
		//float angV = (float)Math.toDegrees(Math.atan2(look.y, Math.sqrt(look.x*look.x+look.z*look.z)));
		
		float power = var1;
		//if (power <= 0.0f)
		{
			//power = 14.0f;
		}
		
		float timeLock = var2;
		//if (timeLock <= 0.0f)
		{
			//timeLock = 60.0f;
		}
		
		//Vector3f dir = new Vector3f(0, 0, 1);
	    //Vector3f xAxis = new Vector3f(1, 0, 0);
	    //Vector3f yAxis = new Vector3f(0, 1, 0);
	    //Vector3f zAxis = new Vector3f(0, 0, 1);
	    //dir = rotatePoint(dir, yAxis, Math.toRadians(rotY));
	    //dir = rotatePoint(dir, xAxis, Math.toRadians(rotX));
	    //dir = rotatePoint(dir, zAxis, Math.toRadians(rotZ));
		
		out.add("8 "+x+" "+(y)+" "+z+" "+(power*60)+" "+(timeLock/60.0f)+" "+rotX+" "+rotY+" "+rotZ);
	}
	
	public static void outSpeedRampSA2(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		Vector3f dir = new Vector3f(1, 0, 0);
	    Vector3f yAxis = new Vector3f(0, 1, 0);
	    Vector3f zAxis = new Vector3f(0, 0, 1);
	    dir = rotatePoint(dir, zAxis, Math.toRadians(rotZ));
	    dir = rotatePoint(dir, yAxis, Math.toRadians(rotY));
	    
		out.add("11 "+x+" "+y+" "+z+" "+dir.x+" "+dir.y+" "+dir.z+" "+(var1*60)+" "+(var2/60));
	}
	
	public static void outSpinner(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.add("28 "+x+" "+(y)+" "+z);
	}
	
	public static void outCameraBoxSADX(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		//out.add("84 "+x+" "+y+" "+z+" "+rotY+" "+);
	}
	
	public static void outRhinoTank(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.add("11 "+x+" "+(y)+" "+z);
	}
	
	public static void outSpikeballSpinSADX(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.add("47 "+x+" "+(y)+" "+z+" "+( var1*5+35)+" "+rotY);
	}
	
	public static void outSpikeballSpinSA2(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		float radius = var1;
		float speed = var2;
		if (Math.round(var1) == -1) //-1 means stationary
		{
			speed = 0;
			radius = 0;
		}
		out.add("47 "+x+" "+(y)+" "+z+" "+radius+" "+speed);
	}
	
	public static void outTurnAsi(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.add("55 "+x+" "+y+" "+z+" "+(rotY+90));
	}
	
	public static void outBigRock(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		String type = "0";
		if (var2 == 1.0f)
		{
			type = "1";
		}
		float scale = (float)(1.1-Math.random()*0.2);
		out.add("56 "+x+" "+y+" "+z+" "+(rotX)+" "+(rotY+1369)+" "+rotZ+" "+scale+" "+type);
	}
	
	public static void outRockPlatform(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.add("57 "+x+" "+y+" "+z+" "+(rotY));
	}
	
	public static void outSeagull(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		float flyRad      = (float)(225+75*Math.random());
		float flySpeed    = (float)((0.09+0.04*Math.random())*(300/flyRad));
		
		float radAmp      = (float)(15+15*Math.random());
		float radDelta    = (float)(0.05+0.2*Math.random());
		
		float heightAmp   = (float)(15+15*Math.random());
		float heightDelta = (float)(0.03+0.1*Math.random());
		
		out.add("58 "+x+" "+(y+20)+" "+z+" "+(rotY)+" "+flySpeed+" "+flyRad+" "+radAmp+" "+radDelta+" "+heightAmp+" "+heightDelta);
	}
	
	public static void outGoalRingSA2(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.add("69 "+x+" "+y+" "+z);
	}
	
	public static void outItemCapsuleSADX(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		rotZ+=90;
		out.add("27 "+x+" "+(y)+" "+z+" "+(rotY)+" "+rotZ+" "+(int)(var1));
	}
	
	public static void outRocket(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.add("97 "+x+" "+y+" "+z+" "+var1+" "+var2+" "+var3);
	}
	
	public static void outItemCapsuleSA2(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3, int capsuleType)
	{
		//order = z, y
		//0 rotation points up
		Vector3f dir = new Vector3f(0, 1, 0);
	    Vector3f yAxis = new Vector3f(0, 1, 0);
	    Vector3f zAxis = new Vector3f(0, 0, 1);
	    dir = rotatePoint(dir, zAxis, Math.toRadians(rotZ));
	    dir = rotatePoint(dir, yAxis, Math.toRadians(rotY));
		
		int type = (int)(var1);
		
		//switch (type)
		//{
			//Speed Shoes
			//case 0: type = 0; break;
				
			//5 Rings
			//case 1: type = 2; break;
			
			//Extra Life
			//case 2: type = 6; break;
				
			//10 Rings
			//case 3: type = 3; break;
				
			//20 Rings
			//case 4: type = 4; break;
				
			//Green Shield
			//case 5: type = 5; break;
				
			//Bomb
			//case 6: type = 7; break;
				
			//Health
			//case 7: type = 6; break;
				
			//Electric Shield
			//case 8: type = 8; break;
				
			//Nothing
			//case 9: type = 6; break;
				
			//Invincible
			//case 10:
			//default: type = 1; break;
		//}
		
		out.add("27 "+x+" "+y+" "+z+" "+(dir.x)+" "+(dir.y)+" "+(dir.z)+" "+type+" "+capsuleType);
	}
	
	public static void outLamppost(float x, float y, float z, float rotX, float rotY, float rotZ, float var1, float var2, float var3)
	{
		out.add("66 "+x+" "+y+" "+z+" "+rotX+" "+rotY+" "+rotZ);
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
	
	//dont use this
	public static Vector3f rotatePointHelper(
			double a, double b, double c,
			double u, double v, double w,
			double x, double y, double z,
			double theta)
	{
		Vector3f result = new Vector3f(0,0,0);
		
		double l = Math.sqrt(u*u + v*v + w*w);
		double l2 = l*l;

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
	
	//theta in radians
	public static Vector3f rotatePoint(
			Vector3f pointToRotate,
			Vector3f axisOfRotation,
			double theta)
	{
		return rotatePointHelper(
				0, 0, 0, 
				axisOfRotation.x, 
				axisOfRotation.y, 
				axisOfRotation.z, 
				pointToRotate.x, 
				pointToRotate.y, 
				pointToRotate.z, 
				theta);
	}
}
