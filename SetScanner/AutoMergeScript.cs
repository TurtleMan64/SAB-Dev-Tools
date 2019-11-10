using System;
using System.Diagnostics;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using System.Collections;
using System.Drawing;
using System.Collections.Generic;
using System.IO;

public class AutoMergeScript
{
	//When sorting, what should we sort by
	public static int organizeType = 0;
	
	//How to display parameters
	public static int dispID = 1;
	public static int dispRot = 0;
	public static int dispPos = 2;
	public static int dispVar = 2;
	
	//List of the objects we are working with
	public static List<SETObject> objList = new List<SETObject>();
	
    public static void Main()
    {
		convertLevel("SABsetbin/set0003_s.bin", "SABsetbin/set0003_u.bin", "SET/GreenForestSABSET.txt");
		convertLevel("SABsetbin/set0006_s.bin", "SABsetbin/set0006_u.bin", "SET/SkyRailSABSET.txt");
		convertLevel("SABsetbin/set0010_s.bin", "SABsetbin/set0010_u.bin", "SET/MetalHarborSABSET.txt");
		convertLevel("SABsetbin/set0013_s.bin", "SABsetbin/set0013_u.bin", "SET/CityEscapeSABSET.txt");
		convertLevel("SABsetbin/set0014_s.bin", "SABsetbin/set0014_u.bin", "SET/RadicalHighwaySABSET.txt");
		convertLevel("SABsetbin/set0031_s.bin", "SABsetbin/set0031_u.bin", "SET/GreenHillSABSET.txt");
	}
	
	public static void convertLevel(string fileS, string fileU, string fileOut)
	{
		objList.Clear();
		addObjectsFromBinFile(fileS);
		addObjectsFromBinFile(fileU);
		organizeByID();
		exportObjectsToTxtFile(fileOut);
		objList.Clear();
	}
	
	public static void organizeByID()
	{
		organizeType = 0;
		objList.Sort();
	}
	
	public static void exportObjectsToTxtFile(string exportFileName)
	{
		using (System.IO.StreamWriter file = 
			new System.IO.StreamWriter(exportFileName))
		{				
			foreach (SETObject o in objList)
			{
				file.WriteLine(o.toFullString());
			}
		}
	}
	
	public static void addObjectsFromBinFile(string fileName)
	{
		try
		{
			byte[] buf = System.IO.File.ReadAllBytes(fileName);
			
			addObjectsFromRawBytes(buf);
		}
		catch
		{
			MessageBox.Show("Trouble loading file '"+fileName+"'");
		}
	}
	
	public static void addObjectsFromRawBytes(byte[] buf)
	{
		//Since the object count is stored in 4 bytes, 
		// and since theres usually a "small" (less than 65k) number of objects
		// in these files, we can assume the endianess by computing
		// both counts and comparing the sizes
		
		uint endianCheck1 = (uint)0;
		endianCheck1 += (uint)(buf[0]);
		endianCheck1 += (uint)(buf[1] << 8);
		endianCheck1 += (uint)(buf[2] << 16);
		endianCheck1 += (uint)(buf[3] << 24);
		
		uint endianCheck2 = (uint)0;
		endianCheck2 += (uint)(buf[3]);
		endianCheck2 += (uint)(buf[2] << 8);
		endianCheck2 += (uint)(buf[1] << 16);
		endianCheck2 += (uint)(buf[0] << 24);
		
		if (endianCheck1 < endianCheck2) //Little endian: SADX
		{
			uint objCount = endianCheck1;
			
			for (uint i = 1; i < objCount+1; i++)
			{
				SETObject obj = new SETObject();
				
				uint off = i*32;
				
				byte id = 0;
				id = buf[off+0];
				//id += (buf[off+0] << 8); //Its only 4 bits of this?
				
				byte clip = 0;
				clip = buf[off+1]; //Only 4 bits of it?
				
				ushort rotX = 0;
				rotX += (ushort)(buf[off+2]);
				rotX += (ushort)(buf[off+3] << 8);
				
				ushort rotY = 0;
				rotY += (ushort)(buf[off+4]);
				rotY += (ushort)(buf[off+5] << 8);
				
				ushort rotZ = 0;
				rotZ += (ushort)(buf[off+6]);
				rotZ += (ushort)(buf[off+7] << 8);
				
				byte[] tmp = new byte[4];
				tmp[3] = buf[off+11];
				tmp[2] = buf[off+10];
				tmp[1] = buf[off+9];
				tmp[0] = buf[off+8];
				float x =  System.BitConverter.ToSingle(tmp, 0);
				
				tmp[3] = buf[off+15];
				tmp[2] = buf[off+14];
				tmp[1] = buf[off+13];
				tmp[0] = buf[off+12];
				float y =  System.BitConverter.ToSingle(tmp, 0);
				
				tmp[3] = buf[off+19];
				tmp[2] = buf[off+18];
				tmp[1] = buf[off+17];
				tmp[0] = buf[off+16];
				float z =  System.BitConverter.ToSingle(tmp, 0);
				
				tmp[3] = buf[off+23];
				tmp[2] = buf[off+22];
				tmp[1] = buf[off+21];
				tmp[0] = buf[off+20];
				float var1 =  System.BitConverter.ToSingle(tmp, 0);
				
				tmp[3] = buf[off+27];
				tmp[2] = buf[off+26];
				tmp[1] = buf[off+25];
				tmp[0] = buf[off+24];
				float var2 =  System.BitConverter.ToSingle(tmp, 0);
				
				tmp[3] = buf[off+31];
				tmp[2] = buf[off+30];
				tmp[1] = buf[off+29];
				tmp[0] = buf[off+28];
				float var3 =  System.BitConverter.ToSingle(tmp, 0);
				
				obj.id = id;
				obj.clip = clip;
				obj.x = x;
				obj.y = y;
				obj.z = z;
				obj.rotX = rotX;
				obj.rotY = rotY;
				obj.rotZ = rotZ;
				obj.var1 = var1;
				obj.var2 = var2;
				obj.var3 = var3;
				
				obj.genDisp();
				
				objList.Add(obj);
			}
		}
		else //Big endian : SA2
		{
			uint objCount = endianCheck2;
			
			for (uint i = 1; i < objCount+1; i++)
			{
				SETObject obj = new SETObject();
				
				uint off = i*32;
				
				byte id = 0;
				id = buf[off+1];
				//id += (buf[off+0] << 8); //Its only 4 bits of this?
				
				byte clip = 0;
				clip = buf[off+0]; //Only 4 bits of it?
				
				ushort rotX = 0;
				rotX += (ushort)(buf[off+3]);
				rotX += (ushort)(buf[off+2] << 8);
				
				ushort rotY = 0;
				rotY += (ushort)(buf[off+5]);
				rotY += (ushort)(buf[off+4] << 8);
				
				ushort rotZ = 0;
				rotZ += (ushort)(buf[off+7]);
				rotZ += (ushort)(buf[off+6] << 8);
				
				byte[] tmp = new byte[4];
				tmp[0] = buf[off+11];
				tmp[1] = buf[off+10];
				tmp[2] = buf[off+9];
				tmp[3] = buf[off+8];
				float x =  System.BitConverter.ToSingle(tmp, 0);
				
				tmp[0] = buf[off+15];
				tmp[1] = buf[off+14];
				tmp[2] = buf[off+13];
				tmp[3] = buf[off+12];
				float y =  System.BitConverter.ToSingle(tmp, 0);
				
				tmp[0] = buf[off+19];
				tmp[1] = buf[off+18];
				tmp[2] = buf[off+17];
				tmp[3] = buf[off+16];
				float z =  System.BitConverter.ToSingle(tmp, 0);
				
				tmp[0] = buf[off+23];
				tmp[1] = buf[off+22];
				tmp[2] = buf[off+21];
				tmp[3] = buf[off+20];
				float var1 =  System.BitConverter.ToSingle(tmp, 0);
				
				tmp[0] = buf[off+27];
				tmp[1] = buf[off+26];
				tmp[2] = buf[off+25];
				tmp[3] = buf[off+24];
				float var2 =  System.BitConverter.ToSingle(tmp, 0);
				
				tmp[0] = buf[off+31];
				tmp[1] = buf[off+30];
				tmp[2] = buf[off+29];
				tmp[3] = buf[off+28];
				float var3 =  System.BitConverter.ToSingle(tmp, 0);
				
				obj.id = id;
				obj.clip = clip;
				obj.x = x;
				obj.y = y;
				obj.z = z;
				obj.rotX = rotX;
				obj.rotY = rotY;
				obj.rotZ = rotZ;
				obj.var1 = var1;
				obj.var2 = var2;
				obj.var3 = var3;
				
				obj.genDisp();
				
				objList.Add(obj);
			}
		}
	}
	
	public class SETObject : IComparable
	{
		public string dispString;
		public int index;
		
		public byte id;
		public byte clip;
		public float x;
		public float y;
		public float z;
		public ushort rotX;
		public ushort rotY;
		public ushort rotZ;
		public float var1;
		public float var2;
		public float var3;
		
		public SETObject() {}
		
		public void genDisp()
		{
			dispString = "";
			
			switch (dispID)
			{
				case 0:  dispString+=fixString(""+id, 5)+"  "; break; //Base 10
				
				default: dispString+=fixString(id.ToString("X"), 5)+"  "; break; //Hex
			}
			
			switch (dispRot)
			{
				case 0:  dispString+=fixString(""+rotX, 8);
						 dispString+=fixString(""+rotY, 8);
						 dispString+=fixString(""+rotZ, 8)+"   "; break; //Base 10
						 
				default: dispString+=fixString(rotX.ToString("X2"), 8);
						 dispString+=fixString(rotY.ToString("X2"), 8);
						 dispString+=fixString(rotZ.ToString("X2"), 8)+"   "; break; //Hex
			}
			
			byte[] xBytes = BitConverter.GetBytes(x);
			byte[] yBytes = BitConverter.GetBytes(y);
			byte[] zBytes = BitConverter.GetBytes(z);
			
			uint xInt = (uint)(xBytes[0]+(xBytes[1]<<8)+(xBytes[2]<<16)+(xBytes[3]<<24));
			uint yInt = (uint)(yBytes[0]+(yBytes[1]<<8)+(yBytes[2]<<16)+(yBytes[3]<<24));
			uint zInt = (uint)(zBytes[0]+(zBytes[1]<<8)+(zBytes[2]<<16)+(zBytes[3]<<24));
			
			switch (dispPos)
			{
				case 0:  dispString+=fixString(""+xInt, 11);
						 dispString+=fixString(""+yInt, 11);
						 dispString+=fixString(""+zInt, 11)+"    "; break; //Base 10
				
				case 1:  dispString+=fixString(""+xInt.ToString("X4"), 11);
						 dispString+=fixString(""+yInt.ToString("X4"), 11);
						 dispString+=fixString(""+zInt.ToString("X4"), 11)+"    "; break; //Hex
				
				default: dispString+=fixFloatString(x, 11);
						 dispString+=fixFloatString(y, 11);
						 dispString+=fixFloatString(z, 11)+"    "; break; //Float
			}
			
			byte[] var1Bytes = BitConverter.GetBytes(var1);
			byte[] var2Bytes = BitConverter.GetBytes(var2);
			byte[] var3Bytes = BitConverter.GetBytes(var3);
			
			uint var1Int = (uint)(var1Bytes[0]+(var1Bytes[1]<<8)+(var1Bytes[2]<<16)+(var1Bytes[3]<<24));
			uint var2Int = (uint)(var2Bytes[0]+(var2Bytes[1]<<8)+(var2Bytes[2]<<16)+(var2Bytes[3]<<24));
			uint var3Int = (uint)(var3Bytes[0]+(var3Bytes[1]<<8)+(var3Bytes[2]<<16)+(var3Bytes[3]<<24));
			
			switch (dispVar)
			{
				case 0:  dispString+=fixString(""+var1Int, 11);
						 dispString+=fixString(""+var2Int, 11);
						 dispString+=fixString(""+var3Int, 11); break; //Base 10
				
				case 1:  dispString+=fixString(""+var1Int.ToString("X4"), 11);
						 dispString+=fixString(""+var2Int.ToString("X4"), 11);
						 dispString+=fixString(""+var3Int.ToString("X4"), 11); break; //Hex
				
				default: dispString+=fixFloatString(var1, 11);
						 dispString+=fixFloatString(var2, 11);
						 dispString+=fixFloatString(var3, 11); break; //Float
			}
		}
		
		public override string ToString()
		{
			return dispString;
		}
		
		public string toCompressedString()
		{
			return (id.ToString("X")+
				" "+rotX+" "+rotY+" "+rotZ+" "+
				" "+String.Format("{0:0.0}", x)+
				" "+String.Format("{0:0.0}", y)+
				" "+String.Format("{0:0.0}", z)+
				" "+String.Format("{0:0.0}", var1)+
				" "+String.Format("{0:0.0}", var2)+
				" "+String.Format("{0:0.0}", var3));
		}
		
		public string toFullString()
		{
			string disp = "";
			
			switch (dispID)
			{
				case 0:  disp+=""+id+" "; break; //Base 10
				
				default: disp+=id.ToString("X")+" "; break; //Hex
			}
			
			switch (dispRot)
			{
				case 0:  disp+=(""+rotX)+" ";
						 disp+=(""+rotY)+" ";
						 disp+=(""+rotZ)+" "; break; //Base 10
						 
				default: disp+=rotX.ToString("X2")+" ";
						 disp+=rotY.ToString("X2")+" ";
						 disp+=rotZ.ToString("X2")+" "; break; //Hex
			}
			
			byte[] xBytes = BitConverter.GetBytes(x);
			byte[] yBytes = BitConverter.GetBytes(y);
			byte[] zBytes = BitConverter.GetBytes(z);
			
			uint xInt = (uint)(xBytes[0]+(xBytes[1]<<8)+(xBytes[2]<<16)+(xBytes[3]<<24));
			uint yInt = (uint)(yBytes[0]+(yBytes[1]<<8)+(yBytes[2]<<16)+(yBytes[3]<<24));
			uint zInt = (uint)(zBytes[0]+(zBytes[1]<<8)+(zBytes[2]<<16)+(zBytes[3]<<24));
			
			switch (dispPos)
			{
				case 0:  disp+=""+xInt+" ";
						 disp+=""+yInt+" ";
						 disp+=""+zInt+" "; break; //Base 10
				
				case 1:  disp+=""+xInt.ToString("X4")+" ";
						 disp+=""+yInt.ToString("X4")+" ";
						 disp+=""+zInt.ToString("X4")+" "; break; //Hex
				
				default: disp+=""+x+" ";
						 disp+=""+y+" ";
						 disp+=""+z+" "; break; //Float
			}
			
			byte[] var1Bytes = BitConverter.GetBytes(var1);
			byte[] var2Bytes = BitConverter.GetBytes(var2);
			byte[] var3Bytes = BitConverter.GetBytes(var3);
			
			uint var1Int = (uint)(var1Bytes[0]+(var1Bytes[1]<<8)+(var1Bytes[2]<<16)+(var1Bytes[3]<<24));
			uint var2Int = (uint)(var2Bytes[0]+(var2Bytes[1]<<8)+(var2Bytes[2]<<16)+(var2Bytes[3]<<24));
			uint var3Int = (uint)(var3Bytes[0]+(var3Bytes[1]<<8)+(var3Bytes[2]<<16)+(var3Bytes[3]<<24));
			
			switch (dispVar)
			{
				case 0:  disp+=""+var1Int+" ";
						 disp+=""+var2Int+" ";
						 disp+=""+var3Int+" "; break; //Base 10
				
				case 1:  disp+=""+var1Int.ToString("X4")+" ";
						 disp+=""+var2Int.ToString("X4")+" ";
						 disp+=""+var3Int.ToString("X4")+" "; break; //Hex
				
				default: disp+=""+var1+" ";
						 disp+=""+var2+" ";
						 disp+=""+var3; break; //Float
			}
			
			return disp;
		}
		
		public string fixString(string old, int size)
		{
			while (old.Length < size)
			{
				old = old+" ";
			}
			
			return old;
		}
		
		public string fixFloatString(float val, int size)
		{
			string old = String.Format("{0:0.00}", val);
			
			while (old.Length < size)
			{
				old = old+" ";
			}
			return old;
		}
		
		public bool isEqualTo(SETObject other)
		{
			return (
				other.id   == id   &&
				other.clip == clip &&
				other.x    == x    &&
				other.y    == y    &&
				other.z    == z    &&
				other.rotX == rotX &&
				other.rotY == rotY &&
				other.rotZ == rotZ &&
				other.var1 == var1 &&
				other.var2 == var2 &&
				other.var3 == var3);
		}
		
		public int CompareTo(object obj)
		{
			SETObject orderToCompare = obj as SETObject;
			
			//Always organize by ID first
			if (orderToCompare.id < id)
			{
				return 1;
			}
			else if (orderToCompare.id > id)
			{
				return -1;
			}
			
			switch (organizeType)
			{
				//Just organize by ID
				case 0:
					break;
				
				//Organize by X Rotation
				case 1:
					if (orderToCompare.rotX < rotX)
					{
						return 1;
					}
					else if (orderToCompare.rotX > rotX)
					{
						return -1;
					}
					break;
					
				//Organize by Y Rotation
				case 2:
					if (orderToCompare.rotY < rotY)
					{
						return 1;
					}
					else if (orderToCompare.rotY > rotY)
					{
						return -1;
					}
					break;
					
				//Organize by Z Rotation
				case 3:
					if (orderToCompare.rotZ < rotZ)
					{
						return 1;
					}
					else if (orderToCompare.rotZ > rotZ)
					{
						return -1;
					}
					break;
					
				//Organize by X
				case 4:
					if (orderToCompare.x < x)
					{
						return 1;
					}
					else if (orderToCompare.x > x)
					{
						return -1;
					}
					break;
					
				//Organize by Y
				case 5:
					if (orderToCompare.y < y)
					{
						return 1;
					}
					else if (orderToCompare.y > y)
					{
						return -1;
					}
					break;
					
				//Organize by Z
				case 6:
					if (orderToCompare.z < z)
					{
						return 1;
					}
					else if (orderToCompare.z > z)
					{
						return -1;
					}
					break;
					
				//Organize by Variable 1
				case 7:
					if (orderToCompare.var1 < var1)
					{
						return 1;
					}
					else if (orderToCompare.var1 > var1)
					{
						return -1;
					}
					break;
					
				//Organize by Variable 2
				case 8:
					if (orderToCompare.var2 < var2)
					{
						return 1;
					}
					else if (orderToCompare.var2 > var2)
					{
						return -1;
					}
					break;
					
				//Organize by Variable 3
				case 9:
					if (orderToCompare.var3 < var3)
					{
						return 1;
					}
					else if (orderToCompare.var3 > var3)
					{
						return -1;
					}
					break;
					
				default:
					break;
			}
			
			//Still a tie? At least make it stable
			if (orderToCompare.index < index)
			{
				return 1;
			}
			
			return -1;
		}
	}
}
