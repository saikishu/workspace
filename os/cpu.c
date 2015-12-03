//Simple program that utilizes cpu
#include <stdio.h>
#include <stdlib.h>
#include "common.h"

int
main(int argc, char* argv[])
{
  if(argc != 2) {
    fprintf(stderr, "Invalid arguments. Usage: cpu <string>\n" );
    exit(1);
  }

  for(;;) {
    Spin(1);
    printf("%s\n", argv[1]);
  }

  return 0;
}
