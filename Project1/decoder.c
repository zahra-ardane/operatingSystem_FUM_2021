#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <errno.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <ctype.h>

char caesar(char toBeChanged){
    if(((int)toBeChanged<=90&&(int)toBeChanged>=68)||
       ((int)toBeChanged<=123&&(int)toBeChanged>=100)){
        return ((int)toBeChanged-3);
    }
    else if((int)toBeChanged<=99&&(int)toBeChanged>=97){
        return (120+toBeChanged%97);
    }
    else if((int)toBeChanged<=67&&(int)toBeChanged>=65){
        return (88+toBeChanged%65);
    }

}

int main(int argc,char *argv[]) {

    if(mkfifo("DecoderToFinder",0777)==-1){
        if(errno != EEXIST){
            //printf("code does not work :((((((");
            return 1;
        }
    }

    char charRead='a';
    int toDecoderfd= open("toDecoder", O_RDONLY);
    int DecoderToFinderfd= open("DecoderToFinder", O_WRONLY);

    FILE *outFile;
    outFile= fopen("decodedFile","w+");
    if(!outFile){
        //printf("can't open file");
        return 1;
    }
    while (1) {
        if(read(toDecoderfd, &charRead, sizeof(charRead))<=0){
            //printf("can't read charrr\n");
            break;
        }
        if(isspace(charRead)==0) {
            charRead = caesar(charRead);
            fprintf(outFile, "%c", charRead);
            write(DecoderToFinderfd,&charRead,sizeof (charRead));
        }
    }
    fclose(outFile);
    close(toDecoderfd);
    close(DecoderToFinderfd);
    return 0;
}
