#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <errno.h>
#include <unistd.h>
#include <fcntl.h>
#include <ctype.h>
#include <stdlib.h>

int main(int argc,char *argv[]){

    int toPlacerfd = open("toPlacer", O_RDONLY);
    if(toPlacerfd == -1){
        //printf("cant open toPlacer pipe\n");
    }


    int FTPfd = open("FinderToPlacer", O_RDONLY);
    if(FTPfd == -1){
        //printf("cant open FTP pipe in placer\n");
    }


    //placer output file created
    FILE *outFile = fopen("placedFile","wt");
    if(!outFile){
        //printf("can't open foundFile\n");
    }

    char temp;

    while(1){
        if(read(toPlacerfd,&temp,sizeof(char)) <= 0){
            //printf("toPlacer finished\n");
            break;
        }
        if(temp != '$'){
            fprintf(outFile,"%c",temp);
        } else {
            char temp2;
            //inserts word
            while(1){
                if(read(FTPfd,&temp2,sizeof(char)) <= 0){
                    //printf("FTP finished\n");
                    break;
                }
                if(temp2 == '\n')
                    break;
                fprintf(outFile,"%c",temp2);
            }
        }
    }


    close(toPlacerfd);
    close(FTPfd);

    fclose(outFile);

    int toParentfd = open("toPlacer", O_WRONLY);
    if(toParentfd == -1){
        //printf("cant open toPlacer pipe in placer\n");
    }

    FILE *rFile = fopen("placedFile","rt");
    if(!rFile){
        //printf("can't open placedFile\n");
    }

    char temp3;

    while(1){
        if(feof(rFile))
            break;

        fscanf(rFile,"%c",&temp3);
        //printf("%c",temp3);
        //write(toParentfd,&temp3, sizeof(char));
        if(write(toParentfd,&temp3, sizeof(char)) <= 0)
            printf("cant write to parent\n");
    }


    close(toParentfd);

    fclose(rFile);

    return 0;
}