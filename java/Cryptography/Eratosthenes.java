/**
 * Sieve of Eratosthenes Algorithm
 *
 * @file Eratosthenes.java
 * @author Sai Kishore
 * */

import java.util.BitSet;

public class Eratosthenes {

	public static void main(String[] args) {
		printDecorator();
		System.out.println("Sieve of Erathosthenes between 2 and N");
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

	/*
	 * Main Algorithm Input: Integer n
	 */
	public static void run(int n) {
		int sqrt_n = (int) Math.sqrt(n);
		BitSet flags = new BitSet(n + 1); // 0 is not crossed, 1 is crossed out
		int j = 1;
		while (j <= sqrt_n) {
			j++;
			if (!flags.get(j)) {
				// repeated addition till it reaches upperbound
				for (int k = 2 * j; k <= n + 1; k += j) {
					flags.set(k);
				}
			}
		}
		// Print primes
		int count = 0;
		for (int i = 2; i <= n; ++i) {
			if (!flags.get(i)) {
				System.out.print(i+"\t\t");
				count++;
				//if(count % 25 == 0) System.out.println();//break every 25th number
			}
		}
		System.out.println();
		printDecorator();
		System.out.println("No of primes in this run: " + count);
		printDecorator();
	}

	public static void printDecorator() {
		for (int i = 0; i < 40; ++i) {
			System.out.print("=");
		}
		System.out.println();
	}

}
