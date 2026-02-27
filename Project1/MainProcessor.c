#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <errno.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <wait.h>



int main(int argc,char *argv[]) {

    FILE* infile= fopen("text2.txt","r+t");
    char characterReadFromFile;
    char characterReadFromPipe;



    pid_t pidDecoder=fork();


    if(pidDecoder<0){
        //printf("can't fork process A");
    }
    if(pidDecoder==0){

        char* args[]={"./decoder",NULL};
        execv(args[0],args);
    }

    pid_t pidPlacer=fork();
    if(pidPlacer<0){
        //printf("can't fork process A");
    }

    pid_t pidFinder=fork();
    if(pidFinder<0){
        //("can't fork process C");
    }
    if(pidFinder==0){
        char* args[]={"./finder",NULL};
        execv(args[0],args);
    }

    if(pidPlacer==0){
        char* args[]={"./placer",NULL};
        execv(args[0],args);
    }
    if(mkfifo("toDecoder",0777)==-1){
        if(errno != EEXIST){
            //printf("code does not work :((((((");
            return 1;
        }

    }
    if(mkfifo("toFinder",0777)==-1){
        if(errno != EEXIST){
            //printf("code does not work :((((((");
            return 1;
        }

    }
    if(mkfifo("toPlacer",0777)==-1){

        if(errno != EEXIST){
            //printf("code does not work :((((((");
            return 1;
        }

    }


    int toDecoderfd= open("toDecoder", O_WRONLY);
    int toFinderfd= open("toFinder", O_WRONLY);
    int toPlacerfd= open("toPlacer", O_WRONLY);


    while(!feof(infile)){

        fscanf(infile,"%c",&characterReadFromFile);

        while(!feof(infile)&&characterReadFromFile!='#'){
            write(toDecoderfd, &characterReadFromFile, sizeof(characterReadFromFile));
            fscanf(infile,"%c",&characterReadFromFile);
        }
        //get two more "#"
        if(!feof(infile))fscanf(infile,"%c",&characterReadFromFile);
        if(!feof(infile))fscanf(infile,"%c",&characterReadFromFile);
        //\n
        if(!feof(infile))fscanf(infile,"%c",&characterReadFromFile);

        //write to Finder
        if(!feof(infile))
            fscanf(infile,"%c",&characterReadFromFile);
        while(!feof(infile)&&characterReadFromFile!='#'){
            write(toFinderfd, &characterReadFromFile, sizeof(characterReadFromFile));
            fscanf(infile,"%c",&characterReadFromFile);
        }
        //#
        if(!feof(infile))fscanf(infile,"%c",&characterReadFromFile);
        if(!feof(infile))fscanf(infile,"%c",&characterReadFromFile);
        //\n
        if(!feof(infile))fscanf(infile,"%c",&characterReadFromFile);

        //write to Placer
        fscanf(infile,"%c",&characterReadFromFile);
        while(!feof(infile)&&characterReadFromFile!='#'){
            write(toPlacerfd,&characterReadFromFile, sizeof(characterReadFromFile));
            fscanf(infile,"%c",&characterReadFromFile);
        }

    }
    close(toPlacerfd);
    close(toDecoderfd);
    close(toFinderfd);

    toPlacerfd= open("toPlacer", O_RDONLY);
    if(toPlacerfd == -1)
        printf("cant open placer pipe in parent\n");
//
//    //read what placer sends to parent and display it
    while (1) {
        if(read(toPlacerfd, &characterReadFromPipe, sizeof(characterReadFromPipe))<=0){
            //printf("can't read char in placer to parent while\n");
            break;
        }
        printf("%c",characterReadFromPipe);

    }



    fclose(infile);

   close(toPlacerfd);

    wait(NULL);
    wait(NULL);
    wait(NULL);

//    printf("wtf\n");
    return 0;
}
