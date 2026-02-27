#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <errno.h>
#include <unistd.h>
#include <fcntl.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>

const int row = 500;
const int col = 2;

int main(int argc,char *argv[]) {
    //printf("in finder");

    int numbers[row][col];
    int numbersIndex= 0;

    if(mkfifo("FinderToPlacer",0777)==-1){
        if(errno != EEXIST){
            //printf("FinderToPlacer doesn't exist ?");
        }
    }

    int toFinderfd = open("toFinder", O_RDONLY);
    if (toFinderfd == -1){
        //printf("cant open toFinder pipe\n");
    }


    char tempChar;
    int sw = 1;

    while(1){

        if (read(toFinderfd, &tempChar, sizeof(char)) <= 0) {
            sw = 0;
            //break;
        }
        if(sw==0)break;

        //length of the number is 20 at max
        char numberChar[20];
        int numberCharIndex = 0;
        while(tempChar != ' '){
            numberChar[numberCharIndex] = tempChar;
            numberCharIndex++;
            if(read(toFinderfd,&tempChar,sizeof(char)) <= 0){
                sw=0;
                break;
            }
        }

        if(sw==0)break;

        sscanf(numberChar,"%d",&numbers[numbersIndex][0]);

        numberCharIndex= 0;
        //clears array
        memset(numberChar,0,10);

        read(toFinderfd,&tempChar,sizeof(char));
        while(tempChar != '$'){
            numberChar[numberCharIndex] = tempChar;
            numberCharIndex++;
            if(read(toFinderfd,&tempChar,sizeof(char)) <= 0){
                sw=0;
                break;
            }

        }

        if(sw==0)break;


        sscanf(numberChar,"%d",&numbers[numbersIndex][1]);

        numbersIndex++;

    }


    FILE *outFile = fopen("foundFile","wt");
    if(!outFile){
        //printf("can't open foundFile\n");
    }

    int DTFfd = open("DecoderToFinder", O_RDONLY);
    if(DTFfd == -1){
        //printf("cant open DTF pipe\n");
    }

    int FTPfd = open("FinderToPlacer", O_WRONLY);
    if(FTPfd == -1){
        //printf("cant open FTP pipe in finder\n");
    }



    int wordIndex = 0;
    char temp;
    int decoderIndex = 0;
    int size = numbersIndex;
    numbersIndex = 0;



    while(numbersIndex < size){

        if(read(DTFfd,&temp,sizeof(char)) <= 0) {
            //rintf("cant read char while 1\n");
            break;
        }
        if(decoderIndex == numbers[numbersIndex][0]){
            char word[numbers[numbersIndex][1]];
            word[wordIndex] = temp;
            wordIndex++;
            decoderIndex++;
            for(int i=0;i<numbers[numbersIndex][1]-1;i++){
                if(read(DTFfd,&word[wordIndex],sizeof(char)) <= 0){
                    //printf("cant read char in for loop\n");
                }
                wordIndex++;
                decoderIndex++;
            }

            for (int p = 0; p < numbers[numbersIndex][1]; p++) {
                if(write(FTPfd,&word[p], sizeof(char)) <= 0){
                    //printf("cant write word\n");
                }
            }

            char t = '\n';
            if(write(FTPfd,&t, sizeof(char)) <= 0){
                //printf("cant write enter\n");
            }

            for(int i=0;i<numbers[numbersIndex][1];i++){
                fprintf(outFile,"%c",word[i]);
            }
            fprintf(outFile,"%c",t);

            numbersIndex++;
            wordIndex = 0;

        } else decoderIndex++;
    }



    fclose(outFile);
    close(toFinderfd);
    close(DTFfd);
    close(FTPfd);



    return 0;
}




