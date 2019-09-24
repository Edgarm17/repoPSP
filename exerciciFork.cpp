#include <stdio.h>
#include <unistd.h>
#include <iostream>  // cin, cout
#include <stdlib.h>  // per al rand

using namespace std;

int main(int argc, char **argv)
{
    printf("--inici del programa\n");
    std::cout<<"PID Actual: "<<getpid()<<std::endl;
    int comptador = 0;
    int randomTime;
    pid_t pid = fork(); //tipus de data per a PID
    //int status = system("firefox");
    if (pid == 0)
    {
        // procés fill
	std::cout<<"PID Actual fill: "<<getpid()<<std::endl;
        int i = 0;
        for (i = 0; i < 5; ++i)
        {
	    randomTime = rand() %5 + 1;
            printf("procés fill: comptador=%d\n", ++comptador);
	    sleep(1);
        }
        int status = system("firefox");
    } else if (pid > 0) {
        // procés pare
	std::cout<<"PID Actual pare: "<<getpid()<<std::endl;
        int j = 0;
        for (j = 0; j < 5; ++j)
        {
	    randomTime = rand() %5 + 1;
            printf("procés pare: comptador=%d\n", ++comptador);
	    sleep(2);
        }
    } else {
        // fork ha fallat
        printf("fork() ha fallat!\n");
        return 1;
    }

    printf("--fi del programa--\n");

    return 0;
}
