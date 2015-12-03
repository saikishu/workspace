//Simple program that accesses memory
//To see in action : disable address space randomization of os
//Ref: https://en.wikipedia.org/wiki/Address_space_layout_randomization

#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include "common.h"

int
main(int argc, char* argv[]) {

  int *p = malloc(sizeof(int));
  assert(p != NULL);
  //print address
  int pid = getpid();
  printf("Process: (%d) :: Memory address of p: %08x\n", pid, (unsigned) p);
  *p = 0;
  for(;;) {
    Spin(1);
    (*p)++;
    printf("Process: (%d) :: Contents: %d\n", pid, *p);
  }

  return 0;

}
