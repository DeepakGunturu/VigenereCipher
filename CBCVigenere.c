// -------------------------------------------------------------
// University of Central Florida
// Vigenere Cipher with block chaining
// Program Author: <Deepak Kumar Gunturu>
// -------------------------------------------------------------

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

// This function takes in the input for the plain text string, the key word, and the initialization vector and encrypts the plaintext string into a Vignere ciphertext
void VignereCipherEncrypt(char *plainText, char *keyWord, char *initVector)
{
    if(plainText == NULL || keyWord == NULL || initVector == NULL)
        return;
    
    int blockLen = strlen(keyWord);
    int i = 0, len = strlen(plainText), copyLen, j = 0, index = 0;
    char copyPlainText[5000], *XOR = malloc(sizeof(char)*blockLen), *vector = malloc(sizeof(char)*blockLen);
    
    strcpy(copyPlainText,plainText);

    while(copyPlainText[i] != '\0')
        i++;
    
    // Padding in the extra characters until the size of the plaintext is a multiple of the block length
    while(i % blockLen != 0)
    {
        copyPlainText[i] = 'x';
        i++;
        copyPlainText[i] = '\0';
    }

    copyLen = strlen(copyPlainText);
    
    for(i = 0; i < copyLen; i++)
    {
        if(i < blockLen)
        {
            XOR[i] = (copyPlainText[i] + initVector[i] - 2*97)%26;
            copyPlainText[i] = (XOR[i] + (keyWord[i] - 97))%26 + 97;
        }
        
        else
        {
            if(i % blockLen == 0)
            {
                index = 0;
                
                for(j = i-blockLen; j < i; j++)
                {
                    vector[index++] = copyPlainText[j];
                }
                
                index = 0;
            }
            
            XOR[index] = (copyPlainText[i] + vector[index] - 2*97)%26;
            copyPlainText[i] = (XOR[index] + (keyWord[index] - 97))%26 + 97;
            index++;
        }
    }

    for(i = 0; i < copyLen; i++)
    {
       plainText[i] = copyPlainText[i];
    }
}

// This function prints out all the characters in the string argument passed to it
void printText(char *text)
{
    if(text == NULL || strcmp(text,"") == 0 || *text == '\0')
    {
        printf("\nEmpty text\n\n");
        return;
    }
    
    int i = 0;
    
    for(i = 0; text[i] != '\0'; i++)
    {
        if(i % 80 == 0 && i != 0)
            printf("\n");
        
        printf("%c",text[i]);
    }
    
    printf("\n");
}

// Main function that takes in command line arguments for the name of the text file, the vigenere keyword, and the initialization vector
int main(int argc, char *argv[])
{
    FILE *ifp;
    int i = 0;
    char txtFile[5000], temp;
    int blockSize;
    
    ifp = fopen(argv[1],"r");

    if(ifp == NULL)
    {
        printf("The file does not exist\n");
        return 1;
    }

    while(fscanf(ifp,"%c",&temp) != EOF)
    {
        if(isalpha(temp))
        {
            temp = tolower(temp);
            txtFile[i] = temp;
            i++;
            txtFile[i] = '\0';
        }     
    }
    
    blockSize = strlen(txtFile);

    printf("\nCBC Vigenere by Deepak Kumar Gunturu\n");
    printf("Plaintext file name: %s\n",argv[1]);
    printf("Vigenere keyword: %s\n",argv[2]);
    printf("Initialization vector: %s\n",argv[3]);
    
    printf("\nClean Plaintext:\n\n");
    printText(txtFile);
    printf("\n");
    
    VignereCipherEncrypt(txtFile,argv[2],argv[3]);
    
    printf("Ciphertext:\n\n");
    printText(txtFile);
    printf("\n");
    
    printf("Number of characters in clean plaintext file: %d\n",blockSize);
    printf("Block size: %lu\n",strlen(argv[2]));
    printf("Number of pad characters added: %lu\n\n",strlen(txtFile)-blockSize);

    fclose(ifp);
    return 0;
}
