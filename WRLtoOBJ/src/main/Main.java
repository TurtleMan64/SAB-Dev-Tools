package main;

import java.util.ArrayList;

public class Main
{
	//public static String fileWRL = "E:\\Documents\\SA2Models\\sa2stagemodelstextured\\objLandTable0013\\CE.wrl";
	//public static String fileWRL = "E:\\CWorkspace\\SA2LevelEditor\\SA2LevelEditor\\res\\Models\\GlobalObjects\\Bunchin\\BunchinSmall.wrl";
	//public static String fileWRL = "C:\\Users\\Anders\\Desktop\\MHSkybox.wrl";
	public static String fileWRL = "E:\\CWorkspace\\NewSonicThing\\NewSonicThing\\res\\Models\\Levels\\SeasideHill\\LevelChunks\\ChunkSeafloor.wrl";
	
	public static String outputFileFolder = "E:\\CWorkspace\\NewSonicThing\\NewSonicThing\\res\\Models\\Levels\\SeasideHill\\LevelChunks\\";
	//public static String outputFileFolder = "E:\\Documents\\SA2Models\\sa2stagemodelstextured\\objLandTable0018\\";
	//public static String outputFileFolder = "E:\\CWorkspace\\SA2LevelEditor\\SA2LevelEditor\\res\\Models\\GlobalObjects\\Bunchin\\";
	//public static String outputFileFolder = "C:\\Users\\Anders\\Desktop\\";
	public static String outputFileName = "ChunkSeafloor";
	
	public static ArrayList<String> materialsToRemove = new ArrayList<String>();
	
	public static void main(String[] args)
	{
    	if (fileWRL.toLowerCase().endsWith(".wrl"))
    	{
    		Converter.uvScale = 1.0f;
    		Converter.flipRB = false;
    		
    		//Converter.mirrorXMaterials.add("space256_4.png");
    		//Converter.mirrorYMaterials.add("jungle64_1.png");
    		//Converter.mirrorXYMaterials.add("space32_6.png");
    		//materialsToRemove.add("p_kage32.png");
    		
    		//setupEternalEngine();
    		
    		System.out.println("Starting.");
    		ArrayList<String> WRLFile = Input.openFile(fileWRL);
    		Converter.replaceTabs(WRLFile);
    		ArrayList<String> OBJFile = new ArrayList<String>();
    		ArrayList<String> MTLFile = new ArrayList<String>();
    		
    		System.out.println("Converting from wrl to obj");
    		Converter.convert(WRLFile, OBJFile, MTLFile, outputFileName+".mtl");
    		
    		Fixer.fixMaterials(OBJFile, MTLFile);
    		Fixer.organizeOBJ(OBJFile);
    		Fixer.organizeOBJ(OBJFile);
    		Fixer.optimizePositions(OBJFile);
    		Fixer.optimizeUVs(OBJFile);
    		
    		for (String matToRem : materialsToRemove)
    		{
    			System.out.println("Removing "+matToRem);
        		Fixer.removeMaterial(OBJFile, MTLFile, matToRem);
    		}
    		
    		System.out.println("Done. Writing out...");
    		Output.writeFile(OBJFile, outputFileFolder+outputFileName+".obj");
    		Output.writeFile(MTLFile, outputFileFolder+outputFileName+".mtl");
    		System.out.println("Done");
    	}
	}
	
	public static void setupCityEscape()
	{
		//fileWRL = "E:\\Documents\\SA2Models\\sa2stagemodelstextured\\objLandTable0013\\CE.wrl";
		//outputFileFolder = "E:\\CWorkspace\\NewSonicThing\\NewSonicThing\\res\\Models\\Levels\\CityEscape\\";
		//outputFileName = "CityEscape";
		
		fileWRL = "E:\\Documents\\SA2Models\\sa2stagemodelstextured\\objLandTable0013\\CEShadows.wrl";
		outputFileFolder = "E:\\CWorkspace\\NewSonicThing\\NewSonicThing\\res\\Models\\Levels\\CityEscape\\";
		outputFileName = "CityEscapeShadows";
		
		//Converter.uvScale = 4.0f;
		//Converter.flipRB = true;
		
		//Converter.mirrorXMaterials.add("scity256_5.png");
		//Converter.mirrorXMaterials.add("scity128_45.png");
		//Converter.mirrorXMaterials.add("scity128_44.png");
		//Converter.mirrorXMaterials.add("scity256_1.png");
		//
		//Converter.mirrorYMaterials.add("miu128_ce001.png");
		//Converter.mirrorYMaterials.add("scity_kage32.png");
		//Converter.mirrorYMaterials.add("scity64_7.png");
        //
		//Converter.mirrorXYMaterials.add("miu64_ce001.png");
		//Converter.mirrorXYMaterials.add("t_senro128.png");
		//Converter.mirrorXYMaterials.add("scity64_21.png");
		//Converter.mirrorXYMaterials.add("scity64_4.png");
		
		//materialsToRemove.add("p_kage32.png");
		//materialsToRemove.add("miu64_ce001.png");
		//materialsToRemove.add("miu128_ce012.png");
	}
	
	public static void setupEternalEngine()
	{
		//fileWRL = "E:\\Documents\\SA2Models\\sa2stagemodelstextured\\objLandTable0024_uv\\EternalEngineUV.wrl";
		fileWRL = "E:\\CWorkspace\\SA2LevelEditor\\SA2LevelEditor\\res\\Models\\Levels\\EternalEngine\\EternalEngineAll.wrl";
		outputFileFolder = "E:\\CWorkspace\\SA2LevelEditor\\SA2LevelEditor\\res\\Models\\Levels\\EternalEngine\\";
		outputFileName = "EternalEngine";
		
		Converter.uvScale = 1.0f;
		Converter.flipRB = false;
	}
}
