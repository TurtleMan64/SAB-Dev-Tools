#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <string.h>

void dealWithFile(char* fileName);
int endsWithHorC(char* fileName);
int endsWithCPP(char* fileName);
void cleanFile(char* fileName);

int main(void)
{
    dealWithFile(".");

    return 0;
}

void dealWithFile(char* fileName)
{
    DIR* d = opendir(fileName);

    struct dirent* dir;

    if (d != NULL) //its a directory
    {
        while ((dir = readdir(d)) != NULL)
        {
            int lenOld = strlen(fileName);
            int lenNew = strlen(dir->d_name);

            if (lenNew == 1)
            {
                if (dir->d_name[0] == '.')
                {
                    continue;
                }
            }
            else if (lenNew == 2)
            {
                if (dir->d_name[0] == '.' && 
                    dir->d_name[1] == '.')
                {
                    continue;
                }
            }
            
            char* fullRelativeName = malloc(lenOld+1+lenNew+1); //+1 for new /, +1 for null terminator

            strcpy(fullRelativeName, fileName);
            fullRelativeName[lenOld] = '/';
            strcpy(&fullRelativeName[lenOld+1], dir->d_name);

            dealWithFile(fullRelativeName);

            free(fullRelativeName);
        }
        closedir(d);
    }
    else //its not a directory
    {
        if (endsWithHorC(fileName) == 1 ||
            endsWithCPP(fileName) == 1) //its a source file
        {
            printf("source file = %s\n", fileName);
            cleanFile(fileName);
        }
    }   
}

int endsWithHorC(char* fileName)
{
    int len = strlen(fileName);
    if (len >= 3)
    {
        if (fileName[len-2] == '.')
        {
            if (fileName[len-1] == 'C' ||
                fileName[len-1] == 'c' ||
                fileName[len-1] == 'H' ||
                fileName[len-1] == 'h')
            {
                return 1;
            }
        }
    }

    return 0;
}

int endsWithCPP(char* fileName)
{
    int len = strlen(fileName);
    if (len >= 5)
    {
        if (fileName[len-4] == '.')
        {
            if (fileName[len-3] == 'C' ||
                fileName[len-3] == 'c')
            {
                if (fileName[len-2] == 'P' ||
                    fileName[len-2] == 'p')
                {
                    if (fileName[len-1] == 'P' ||
                        fileName[len-1] == 'p')
                    {
                        return 1;
                    }
                }
            }
        }
    }

    return 0;
}

void cleanFile(char* fileName)
{
    FILE* file = fopen(fileName, "rb");
    char ogFile[1000000]; //1MB max file size
    int fileSize = 0;

    if (file == NULL)
    {
        printf("Error when reading in file '%s'\n", fileName);
        return;
    }

    char nextChar = 0;
    while (fread(&nextChar, 1, 1, file) == 1)
    {
        if (nextChar == '\t') //tab to 4 spaces
        {
            ogFile[fileSize+0] = ' ';
            ogFile[fileSize+1] = ' ';
            ogFile[fileSize+2] = ' ';
            ogFile[fileSize+3] = ' ';
            fileSize += 4;
        }
        else if (nextChar == '\n') //windows newlines to unix
        {
            if (fileSize > 0)
            {
                if (ogFile[fileSize-1] == '\r')
                {
                    ogFile[fileSize-1] = '\n';
                }
                else
                {
                    ogFile[fileSize] = nextChar;
                    fileSize++;
                }
                
            }
            else //first byte in the code is a newline, leave it alone
            {
                ogFile[fileSize] = nextChar;
                fileSize++;
            }
        }
        else
        {
            ogFile[fileSize] = nextChar;
            fileSize++;
        }
    }

    fclose(file);

    file = fopen(fileName, "wb");  // create and/or overwrite

    if (file == NULL)
    {
        printf("Error when writing to file '%s'\n", fileName);
        return;
    }

    for (int i = 0; i < fileSize; i++)
    {
        fwrite(&ogFile[i], 1, 1, file);
    }

    fclose(file);
}
