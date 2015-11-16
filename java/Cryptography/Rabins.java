/**
 * Miller-Selfridge-Rabin's primality test algorithm
 *
 * @file Rabins.java
 * @author Sai Kishore
 * @category COEN 250 Assignment 3 - 2
 * Reference: https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test
 * */

import java.util.ArrayList;

public class Rabins {

	public static void main(String[] args) {
		printDecorator();
		System.out.println("Rabin's Primality between 3 and N");
		printDecorator();
		System.out.println("Run 1: N = 100");
		printDecorator();
		run(100);
		printDecorator();
		System.out.println("Run 2: N = 1,000");
		printDecorator();
		run(1000);
		printDecorator();
		System.out.println("Run 3: N = 10,000");
		printDecorator();
		run(10000);
		printDecorator();
	}

	/**
	 * Main algorithm
	 * @param input integer
	 */

	public static void run(int N) {
		//Lists
		ArrayList<Integer> primes = new ArrayList<Integer>();
		ArrayList<Integer> composites = new ArrayList<Integer>();

		//skip even numbers - algorithm requires odd integer
		//print between 3 to N
		for(int n=3; n<=N; n=n+2) {
			//Step 1: Find k,q
			int q = n-1;
			int k = 0;
			while(q % 2 == 0 ) {
				k++;
				q /= 2;
			}

			//Step 2 : Set a = 2 - In general select random number 1 < a < (n-1)
			int a = 2;
			//Calculate c
			long c = modPow(a,q,n);
			for (int j = 0; j < k; j++) {
	            long cnew = (c*c)%n;
	            if ((cnew == 1) && !(c == 1) && !(c == n-1))
	                primes.add(n);
	            c = cnew;
	        }
	        if(c==1) {
	        	primes.add(n);
	        } else {
	        	composites.add(n);
	        }
		}
		//Printing probable primes
		for(int i=0; i<primes.size(); ++i) {
			System.out.print(primes.get(i)+"\t\t");
		}
		System.out.println();
		System.out.println("Size:" +primes.size() );
	}

	/* Calculates (a^b)%c */
	public static long modPow(long a, long b, long c) {
		long result = 1;
		for(int i= 0; i <b; ++i) {
			result *= a;
			result %= c;
		}
		return result % c;
	}

	public static void printDecorator() {
		for (int i = 0; i < 40; ++i) {
			System.out.print("=");
		}
		System.out.println();
	}
}
