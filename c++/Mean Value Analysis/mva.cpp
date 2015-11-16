//============================================================================
// Name        : mva.cpp
// Author      : Venkata Sai Kishore Modalavalasa
// Description : Mean Value Analysis Algorithm
//============================================================================

#include <iostream>

using namespace std;

//Declarations

void mva(int N, int Z, int M, double* S_i, double* V_i );

/**
 * Main Routine
 */

int main() {

	int N = 20;
	int Z = 4;
	int M = 3;
	double S_i[3] = {0.125,0.3,0.2};
	double V_i[3] = {16,3,1};

	mva(N,Z,M, S_i, V_i);

}

/**
 * Runs Mean Value Analysis Algorithm
 *
 * Inputs:
 * N: Number of users : Integer
 * Z: Think time : Integer
 * M : No of devices: Integer
 * S_i: Service times array : S[0] for CPU S[i] : i-th device
 * V_i : Number of visits array: V[0] for CPU V[i]: i-th device
 */

void mva(int N, int Z, int M, double* S_i, double* V_i ) {

	//Output variables
	double X; //System throughput
	double* Q_i; //Average number of jobs at i-th device
	double* R_i; //Average response time at i-th device
	double R; //System Response time
	double* U_i; //Utilization of i-th device
	double* X_i; //Device throughputs


	//Initialization step
	Q_i = new double[M]; // 0 for CPU i for ith device
	//Set all Q's to 0
	std::fill(Q_i, Q_i + M, 0);

	//Memory Allocations
	R_i = new double[M];
	U_i = new double[M];
	X_i = new double[M];

	//Iterations
	for(int n=1; n <= N; n++) {

		cout << "Iteration: " << n << endl;

		R = 0;

		for(int i=0; i < M; i++) {

			R_i[i] = S_i[i] * (1 + Q_i[i]);
			R += (R_i[i] * V_i[i]);

			cout << "R " << i << " " << R_i[i] << endl;
		}

		X = n/(Z+R);

		cout << "R " << R << endl;
		cout << "X " << X << endl;


        for (int i=0; i < M; i++) {
        	Q_i[i] = X * V_i[i] * R_i[i];

        	cout << "Q " << i << " " << Q_i[i] << endl;
        }


	}

	//Cleanup - Memory

}
