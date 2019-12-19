#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <string.h>

void dealWithFile(char* fileName);
int endsWithHorC(char* fileName);
int endsWithCPP(char* fileName);
void cleanFile(char* fileName);
//copies up to first null char (including it)
//void copyFileBuf(char* src, char* dst);
int fileIsSame(char* f1, char* f2, int numCharsToCheck);
void clearBuf(char* buf);

int MAX_FILE_SIZE = 1000000; //1MB max file size

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
            //printf("source file = %s\n", fileName);
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
    char ogFile[MAX_FILE_SIZE];
    char newFile[MAX_FILE_SIZE];
    clearBuf(ogFile);
    clearBuf(newFile);
    int fileSize = 0;
    int ogSize = 0;

    if (file == NULL)
    {
        printf("Error when reading in file '%s'\n", fileName);
        return;
    }

    char nextChar = 0;
    while (fread(&nextChar, 1, 1, file) == 1)
    {
        ogFile[ogSize] = nextChar;
        ogSize++;
        
        if (nextChar == '\t') //tab to 4 spaces
        {
            newFile[fileSize+0] = ' ';
            newFile[fileSize+1] = ' ';
            newFile[fileSize+2] = ' ';
            newFile[fileSize+3] = ' ';
            fileSize += 4;
        }
        else if (nextChar == 10) //windows newlines to unix
        {
            if (fileSize > 0)
            {
                if (newFile[fileSize-1] == 13) //windows = 13, 10.   unix = 10
                {
                    newFile[fileSize-1] = 10;
                }
                else
                {
                    newFile[fileSize] = nextChar;
                    fileSize++;
                }
                
            }
            else //first byte in the code is a newline, leave it alone
            {
                newFile[fileSize] = nextChar;
                fileSize++;
            }
        }
        else
        {
            newFile[fileSize] = nextChar;
            fileSize++;
        }
    }
    
    //change all mac to unix
    for (int i = 0; i < fileSize; i++)
    {
        if (newFile[i] == 13)
        {
            newFile[i] = 10;
        }
        
        if (newFile[i] == 0)
        {
            break;
        }
    }

    fclose(file);
    
    if (ogSize != fileSize || fileIsSame(ogFile, newFile, fileSize) == 0)
    {
        printf("cleaning source file = %s\n", fileName);

        file = fopen(fileName, "wb");  // create and/or overwrite

        if (file == NULL)
        {
            printf("Error when writing to file '%s'\n", fileName);
            return;
        }

        for (int i = 0; i < fileSize; i++)
        {
            fwrite(&newFile[i], 1, 1, file);
        }

        fclose(file);
    }
}

//void copyFileBuf(char* src, char* dst)
//{
//    for (int i = 0; i < MAX_FILE_SIZE; i++)
//    {
//        dst[i] = src[i];
//        if (dst[i] == 0)
//        {
//            break;
//        }
//    }
//}

void clearBuf(char* buf)
{
    for (int i = 0; i < MAX_FILE_SIZE; i++)
    {
        buf[i] = 0;
    }
}

int fileIsSame(char* f1, char* f2, int numCharsToCheck)
{
    for (int i = 0; i < numCharsToCheck; i++)
    {
        if (f1[i] != f2[i])
        {
            return 0;
        }
        
        if (f1[i] == 0)
        {
            break;
        }
    }
    
    return 1;
}
