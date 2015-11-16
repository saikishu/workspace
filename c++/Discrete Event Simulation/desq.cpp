//============================================================================
// Name        : desq.cpp
// Author      : Sai Kishore
// Description : Discrete Event Simulation of Queue
//============================================================================

#include <iostream>
#include <cmath>
#include <iomanip>

using namespace std;

//Constants
#define ARRIVALTIME_SEED 46831694
#define SERVICETIME_SEED 373135028

//Declarations
long double expntl(long double t);
long double ranf();
void simulate(long double Ta, long double Ts, long double te);
void printTheoreticalResults(long double Ta, long double Ts);
void printHeader();
void printLine();

//Global
long double arrival_stream = ARRIVALTIME_SEED;
long double service_stream = SERVICETIME_SEED;
int flag = 0; // 0 for arrival time, 1 for service time

int main() {

	printLine();
		cout << "Simulation for Ta = 200 time units, Ts = 100 time units and varying te" << endl;
	printLine();
		cout << "te";
		printHeader();
	printLine();
		cout << "100";
		simulate(200L,100L,100L);
		cout << "1000";
		simulate(200L,100L,1000L);
		cout << "10000";
		simulate(200L,100L,10000L);
		cout << "100000";
		simulate(200L,100L,100000L);
		cout << "1000000";
		simulate(200L,100L,1000000L);
	printLine();
		cout << "Theoretical Values" << endl;
		printTheoreticalResults(200L,100L);
	printLine();

	cout << endl;
	cout << endl;

	printLine();
		cout << "Simulation for Ta = 200 time units, Te = 1000000 time units and varying Ts" << endl;
	printLine();
		cout << "Ts";
		printHeader();
	printLine();
		cout << "5";
		simulate(200L,5L,1000000L);
		cout << "10";
		simulate(200L,10L,1000000L);
		cout << "20";
		simulate(200L,20L,1000000L);
		cout << "40";
		simulate(200L,40L,1000000L);
		cout << "60";
		simulate(200L,60L,1000000L);
		cout << "80";
		simulate(200L,80L,1000000L);
		cout << "100";
		simulate(200L,100L,1000000L);
		cout << "120";
		simulate(200L,120L,1000000L);
		cout << "140";
		simulate(200L,140L,1000000L);
		cout << "160";
		simulate(200L,160L,1000000L);
		cout << "180";
		simulate(200L,180L,1000000L);

	printLine();
		cout << "Theoretical Values" << endl;
		cout << "5";
		printTheoreticalResults(200L,5L);
		cout << "10";
		printTheoreticalResults(200L,10L);
		cout << "20";
		printTheoreticalResults(200L,20L);
		cout << "40";
		printTheoreticalResults(200L,40L);
		cout << "60";
		printTheoreticalResults(200L,60L);
		cout << "80";
		printTheoreticalResults(200L,80L);
		cout << "100";
		printTheoreticalResults(200L,100L);
		cout << "120";
		printTheoreticalResults(200L,120L);
		cout << "140";
		printTheoreticalResults(200L,140L);
		cout << "160";
		printTheoreticalResults(200L,160L);
		cout << "180";
		printTheoreticalResults(200L,180L);
	printLine();

}


/**
 * Runs Discrete Event simulation
 * Parameters:
 * Ts, Ta,
 */

void simulate(long double Ta, long double Ts, long double te) {
	long double t1, t2, time;
	long double B,C,L,s,tb,tn,U,W,X;
	int n;

	//Initializations
	n = 0;
	t1 = 0L;
	t2 = te;
	time = 0L;
	B = 0L;
	s = 0L;
	C = 0L;
	tn = time;

	//Simulation Logic

	while(time < te) {
		if(t1 < t2) {
			//Event 1 : Arrival
			time = t1;
			s += n * (time - tn);
			n++;
			tn = time;
			flag = 0;
			t1 = time + expntl(Ta);
			if(n == 1) {
				tb  = time;
				flag = 1;
				t2 = time + expntl(Ts);
			}
		} else {
			//Event 2 : Completion
			time = t2;
			s += n * (time - tn);
			n--;
			tn = time;
			C++;
			if(n>0) {
				flag = 1;
				t2 = time + expntl(Ts);
			} else {
				t2 = te;
				B += time - tb;
			}
		}
	}

	X = C/time;
	U = B/time;
	L = s/time;
	W = L/X;

	cout << setw(20) << X << setw(30) << U << setw(30) << L << setw(30) << W << endl;

}

/*
 * Calculate theoretical results
 * Uses theoretical formulae
 * */

void printTheoreticalResults(long double Ta, long double Ts) {
		long double X = 1/Ta; // lambda
		long double U = Ts/Ta;  // lambda/mu = Ts/Ta
		long double W = Ts/(1-U);
		long double L = U/(1-U);

		cout << setw(20) << X << setw(30) << U << setw(30) << L << setw(30) << W << endl;
};


/* Exponential distribution function */
long double expntl(long double t ) {
	return (-1 * t * log10(ranf()));
}

/*
 * Random variate generator
 * Source: Raj Jain pg. 444
 * */

long double ranf() {

	long double x;

	//Different streams for random number
	if (flag == 0) x = arrival_stream;
	else x = service_stream;

	//Constants for Random Variate Generation
	long double a = 16807L; //Multiplier
	long double m = 2147483647L; //Modulus
	long double q = 127773L; //M DIV A
	long double r = 2836L;	// M MOD A

	long double x_div_q, x_mod_q, x_new;
	x_div_q = trunc(x/q);
	x_mod_q = x-q*x_div_q;
	x_new = a*x_mod_q - r*x_div_q;

	if(x_new > 0 ) {
		x = x_new;
	} else {
		x = x_new + m;
	}

	return x/m;
}

/**
 *  Utilities
 */
void printLine() {
	for(int i=0; i<115; ++i) {
		cout << "=";
	}
	cout << endl;
}
void printHeader() {
	cout << setw(20) << "Throughput (X)" << setw(30) << "Utilization (U)" << setw(30) << "Mean # in Sys (L)" << setw(30) << "Mean Residence Time (W)" << endl;
}
