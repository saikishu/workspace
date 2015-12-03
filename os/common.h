/**
Common header file
**/

#ifndef __common_h__
#define __common_h__

#include <sys/time.h>
#include <assert.h>

//Get the time
double
getTime()
{
  struct timeval t;
  int rc = gettimeofday(&t, NULL);
  assert(rc == 0);
  return (double)t.tv_sec + (double)t.tv_usec/1e6;
}

// Waits for duration = timeinsecs
void
Spin(int timeinsecs)
{
  //get time
  //wait till current time - above time in secs > timeinsecs
  double t = getTime();
  while((getTime() - t)  < timeinsecs) ; //do nothing
}

//

#endif
