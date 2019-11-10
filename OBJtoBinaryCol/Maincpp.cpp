#include <stdlib.h> 
#include <stdio.h>
#include <iostream>
#include <vector>
#include "vector.h"

#include <fstream>
#include <string>
#include <cstring>
#include <list>

/* Parse a line and return an array of the
individual tokens. Original line is modified. 
Make sure to call free() on the returned char** */
char** split(char* line, char delim, int* length);

void doFile(std::string ogFilename);

int main(int argc, char* argv[])
{
	std::printf("hi\n");
	for (int i = 0; i < argc; i++)
	{
		std::fprintf(stdout, "'%s'\n", argv[i]);
	}

	if (argc < 2)
	{
		std::fprintf(stderr, "Please provide a file path to the .obj\n");
		return 1;
	}

	for (int i = 1; i < argc; i++)
	{
		doFile(argv[i]);
	}
}

void doFile(std::string ogFilename)
{
	std::ifstream file(ogFilename);
	if (!file.is_open())
	{
		std::fprintf(stderr, "Error: Cannot load file '%s'\n", ogFilename.c_str());
		file.close();
		return;
	}

	std::string line;

	std::string mtlname;
	std::vector<Vector3f> vertices;
	std::vector<Vector2f> textures;
	std::vector<Vector3f> normals;

	std::vector<std::string> indiceMaterials;
	std::vector<std::vector<int>> indicesGroups;
	std::vector<int> currentIndiceGroup;

	int foundFaces = 0;

	while (!file.eof())
	{
		getline(file, line);

		char lineBuf[512];
		memcpy(lineBuf, line.c_str(), line.size()+1);

		int splitLength = 0;
		char** lineSplit = split(lineBuf, ' ', &splitLength);

		if (splitLength > 0)
		{
			if (foundFaces == 0)
			{
				//Find the mtl filename
				if (strcmp(lineSplit[0], "mtllib") == 0)
				{
					mtlname = lineSplit[1];
				}
				else if (strcmp(lineSplit[0], "v") == 0)
				{
					std::string p1(lineSplit[1]);
					std::string p2(lineSplit[2]);
					std::string p3(lineSplit[3]);
					Vector3f vertex(std::stof(p1, nullptr), std::stof(p2, nullptr), std::stof(p3, nullptr));
					vertices.push_back(vertex);
				}
				else if (strcmp(lineSplit[0], "vt") == 0)
				{
					std::string t1(lineSplit[1]);
					std::string t2(lineSplit[2]);
					Vector2f texCoord(std::stof(t1, nullptr), std::stof(t2, nullptr));
					textures.push_back(texCoord);
				}
				else if (strcmp(lineSplit[0], "vn") == 0)
				{
					std::string n1(lineSplit[1]);
					std::string n2(lineSplit[2]);
					std::string n3(lineSplit[3]);
					Vector3f normal(std::stof(n1, nullptr), std::stof(n2, nullptr), std::stof(n3, nullptr));
					normals.push_back(normal);
				}
				else if (strcmp(lineSplit[0], "usemtl") == 0)
				{
					indiceMaterials.push_back(lineSplit[1]);

					if (indiceMaterials.size() > 1) //dont add a group of zero the first time you see the usemtl
					{
						indicesGroups.push_back(currentIndiceGroup);
					}
					currentIndiceGroup.clear();
				}
				else if (strcmp(lineSplit[0], "f") == 0)
				{
					int dummy = 0;
					char** vertex1 = split(lineSplit[1], '/', &dummy);
					char** vertex2 = split(lineSplit[2], '/', &dummy);
					char** vertex3 = split(lineSplit[3], '/', &dummy);

					currentIndiceGroup.push_back(std::stoi(vertex1[0], nullptr));
					currentIndiceGroup.push_back(std::stoi(vertex1[1], nullptr));
					currentIndiceGroup.push_back(std::stoi(vertex1[2], nullptr));
					currentIndiceGroup.push_back(std::stoi(vertex2[0], nullptr));
					currentIndiceGroup.push_back(std::stoi(vertex2[1], nullptr));
					currentIndiceGroup.push_back(std::stoi(vertex2[2], nullptr));
					currentIndiceGroup.push_back(std::stoi(vertex3[0], nullptr));
					currentIndiceGroup.push_back(std::stoi(vertex3[1], nullptr));
					currentIndiceGroup.push_back(std::stoi(vertex3[2], nullptr));

					free(vertex1);
					free(vertex2);
					free(vertex3);
				}
			}
		}
		free(lineSplit);
	}
	file.close();

	if (currentIndiceGroup.size() > 0) //Add the last group
	{
		indicesGroups.push_back(currentIndiceGroup);
	}


	//print into binary file

	char path[1024];
	memcpy(path, ogFilename.c_str(), ogFilename.size()+1);

	int len;
	char** filePathSplit = split(path, '\\', &len);
	char** filenameSplit = split(filePathSplit[len-1], '.', &len);
	std::string fname = filenameSplit[0];
	fname = fname+".bincol";

	FILE* f = nullptr;
	int err = fopen_s(&f, fname.c_str(), "wb" );
	if (f != nullptr && err == 0)
	{
		char col[4];
		col[0] = 'c';
		col[1] = 'o';
		col[2] = 'l';
		col[3] = 0;
		fwrite(col, sizeof(char), 4, f);

		int mtllibLength = mtlname.size();
		fwrite(&mtllibLength, sizeof(int), 1, f); //length of mtllib string
		fwrite(mtlname.c_str(), sizeof(char), mtlname.size(), f); //mtllib string

		int verticesLength = vertices.size();
		fwrite(&verticesLength, sizeof(int), 1, f); //how many vertices there are
		for (int i = 0; i < verticesLength; i++)
		{
			fwrite(&vertices[i].x, sizeof(float), 1, f); //12 bytes for each vertex
			fwrite(&vertices[i].y, sizeof(float), 1, f);
			fwrite(&vertices[i].z, sizeof(float), 1, f);
		}

        //int bytesPerIndV = 4;
        //if (verticesLength < 256)
        //{
        //    bytesPerIndV = 1;
        //}
        //else if (verticesLength < 65536)
        //{
        //    bytesPerIndV = 2;
        //}
        //else if (verticesLength < 16777216)
        //{
        //    bytesPerIndV = 3;
        //}
        //fwrite(&bytesPerIndV, sizeof(int), 1, f); //number of bytes for a vertex index

		int materialsLength = indiceMaterials.size();
		fwrite(&materialsLength, sizeof(int), 1, f); //how many materials there are
		for (int i = 0; i < materialsLength; i++)
		{
			int matnameLength = indiceMaterials[i].size();
			fwrite(&matnameLength, sizeof(int), 1, f); //length of mtlname string
			fwrite(indiceMaterials[i].c_str(), sizeof(char), indiceMaterials[i].size(), f); //mtlname string

			//now print each materials indices
            std::vector<int> indices = indicesGroups[i];
			int numFaces = indices.size()/9;
			fwrite(&numFaces, sizeof(int), 1, f); //length of indices
			
			for (int c = 0; c < numFaces; c++)
			{
				fwrite(&indices[c*9+0], sizeof(int), 1, f);
				fwrite(&indices[c*9+3], sizeof(int), 1, f);
				fwrite(&indices[c*9+6], sizeof(int), 1, f);
			}

			//std::vector<int> indices = indicesGroups[i];
			//int numFaces = indices.size()/9;
			//fwrite(&numFaces, sizeof(int), 1, f); //length of indices
			//
			//for (int c = 0; c < numFaces; c++)
			//{
			//	fwrite(&indices[c*9+0], bytesPerIndV, 1, f);
			//	fwrite(&indices[c*9+3], bytesPerIndV, 1, f);
			//	fwrite(&indices[c*9+6], bytesPerIndV, 1, f);
			//}
		}

        fclose(f);
    }
}

char** split(char* line, char delim, int* length)
{
	/* Scan through line to find the number of tokens */
	int numTokens = 0;
	int index = 0;
	int inToken = 0;

	while (line[index] != 0)
	{
		if (line[index] != delim && inToken == 0)
		{
			inToken = 1;
			numTokens += 1;
		}
		else if (line[index] == delim)
		{
			inToken = 0;
		}
		index += 1;
	}

	/* Get memory to store the data */
	char ** parsedData = (char**)malloc(sizeof(char*)*(numTokens + 1));

	/* Scan through line to fill parsedData
	and set 0 characters after tokens*/
	index = 0;
	inToken = 0;
	int tokenNum = 0;

	while (line[index] != 0)
	{
		if (line[index] != delim && inToken == 0)
		{
			parsedData[tokenNum] = &line[index];
			tokenNum += 1;
			inToken = 1;
		}
		else if (line[index] == delim)
		{
			if (inToken == 1)
			{
				line[index] = 0;
			}
			inToken = 0;
		}
		index += 1;
	}

	parsedData[numTokens] = NULL;

	*length = numTokens;

	return parsedData;
}