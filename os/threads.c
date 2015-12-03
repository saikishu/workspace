/*Problem illustrating concurrency issues*/
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <pthread.h>
#include "common.h"

int nLoops;
volatile int counter = 0; //global counter - multithreading - volatile

void *worker(void *arg) {
    for(int i=0; i<nLoops; ++i) {
      counter++;
    }
    return NULL;
}

int
main(int argc, char *argv[]) {

  if(argc != 2) {
    fprintf(stderr, "Invalid arguments. Usage: threads <no of loops>\n");
    exit(1);
  }

  nLoops = atoi(argv[1]);
  pthread_t p1, p2;
  int rc;

  rc = pthread_create(&p1, NULL, worker, NULL);
  assert(rc == 0);
  rc = pthread_create(&p2, NULL, worker, NULL);
  assert(rc == 0);
  rc = pthread_join(p1, NULL);
  assert(rc == 0);
  rc = pthread_join(p2, NULL);
  assert(rc == 0);
  printf("Final Counter Value: %d\n", counter);
  return 0;

}
