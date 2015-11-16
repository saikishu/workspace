//============================================================================
// Name        : main.cpp
// Author      : Sai Kishore
// Description : Single Server Queue Simulation
//============================================================================

#include <iostream>
#include <cmath>
#include <ctime>
#include <vector>
#include <string>

using namespace std;

//Constants
#define ARRIVALTIME_SEED 727633698
#define SERVICETIME_SEED 1434868289
#define AVG_CUST_INTERARRIVAL_TIME 200
#define AVG_CUST_SERVICE_TIME 100

//Global Variables
long double Wavg = 0L; //Average wait time
long double Aavg = 0L; //Average Interarrival time
long double Savg = 0l; //Average Service time

//Declarations
long double getNextRandomNumber(long double n);
void simulateServer(long double nCustomers);

/*
 * Main Simulation routine.
 * Preconditions: Runs simulations upto 10^10 iterations
 * Postconditions: Simulation results are output to console.
 */

int main() {

	clock_t t1, t2;
	float diff;
	float seconds;
	//Simulations to run
	vector<pair<string, long double> > simulations;
	simulations.push_back(make_pair("10", 10L));
	simulations.push_back(make_pair("10^2", 100L));
	simulations.push_back(make_pair("10^3", 1000L));
	simulations.push_back(make_pair("10^4", 10000L));
	simulations.push_back(make_pair("10^5", 100000L));
	simulations.push_back(make_pair("10^6", 1000000L));
	simulations.push_back(make_pair("10^7", 10000000L));
	simulations.push_back(make_pair("10^8", 100000000L));
	simulations.push_back(make_pair("10^9", 1000000000L));
	simulations.push_back(make_pair("10^10", 10000000000L));

	cout << "#Customers\t" << "A_Average" << "\t" << "S_Average" << "\t"
			<< "W_Average" << "\t" << "Utilization" << "\t" << "Time (s)"
			<< endl;
	//Run simulations
	//Using C++ 11 feature
	for (auto& x : simulations) {
		t1 = clock();
		simulateServer(x.second);
		cout << x.first << "\t\t" << Aavg << "\t\t" << Savg << "\t\t" << Wavg
				<< "\t\t" << Savg / Aavg << "\t\t";
		t2 = clock();
		diff = ((float) t2 - (float) t1);
		seconds = diff / CLOCKS_PER_SEC;
		std::cout << seconds << std::endl;
	}

	return 0;
}

/*
 * Gets next random number. Uses Linear Congruential Generator
 * Precondition: Current number
 * Postcondition: Next random number
 */
long double getNextRandomNumber(long double n) {
	//LCG: 7^5*n*(2^31-1) // 7^5 = 16807; 2^31-1 = 2147483647
	return fmod(16807L * n, 2147483647L);
}

/**
 * Simulates wait times and services times
 * Precondition: No of simulations
 * Postcondition: Stores simulation results
 */

void simulateServer(long double nCustomers) {
	long double m = 2147483647L;

	long double x_prev = ARRIVALTIME_SEED;
	long double y_prev = SERVICETIME_SEED;
	long double w_prev = 0L; // w1 = 0

	//Initialize sums
	long double A = 0L;
	long double S = 0L;
	long double W = 0L;

	for (long double i = 0L; i < nCustomers; ++i) {

		//Interarrival times
		long double x_current = getNextRandomNumber(x_prev);
		long double u_current = x_current / m;
		long double a_current = (-1L) * AVG_CUST_INTERARRIVAL_TIME
				* log(u_current);
		A += a_current;

		//Service times
		long double y_current = getNextRandomNumber(y_prev);
		u_current = y_current / m;
		long double s_current = (-1L) * AVG_CUST_SERVICE_TIME * log(u_current);
		S += s_current;

		//Wait times
		//First customer it's 0 anyway
		long double w_current = w_prev + s_current - a_current;
		w_current = (0L < w_current) ? w_current : 0L;

		W += w_current;

		//Update vars
		w_prev = w_current;
		x_prev = x_current;
		y_prev = y_current;
	}

	Savg = S / nCustomers;
	Aavg = A / nCustomers;
	Wavg = W / nCustomers;
}
