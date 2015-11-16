/**
 * Blum Blum Shub (BBS) Pseudo Random Bit Generator (PRBG) algorithm
 * Also include Euclidean Algorithm for computing gcd
 *
 * @file BBSPRBG.java
 * @author Sai Kishore
 * */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * Euclidean Algorithm to compute GCD
 *
 */
class GCD {
	private int a, b;
	private ArrayList<Integer> r = new ArrayList<Integer>();

	GCD() {
		//do nothing
	}

	public int computeGCD(int a, int b){

		if(a <= b) {
			this.a = a;
			this.b = b;
		} else {
			//can handle differently or throw exception
			this.a = b;
			this.b = a;
		}

		r.add(0, a); //r0 = a
		r.add(1, b); //r1 = b;
		int i = 1;
		while(r.get(i) != 0) {
			r.add(i+1, r.get(i-1) % r.get(i)); //r_i+1 = r_i-1 mod r_i
			i++;
		}
		return r.get(i-1);
	}

}

public class BBSPRBG {

	/**
	 * BBSPRBG runs
	 */
	public static void main(String[] args) {

		printDecorator();
		System.out.println("Run 1: p = 3, q=7");
		printDecorator();
		run(3,7);
		printDecorator();

		printDecorator();
		System.out.println("Run 2: p = 7, q=11");
		printDecorator();
		run(7,11);
		printDecorator();
		printDecorator();
		System.out.println("Run 3: p = 19, q=23");
		printDecorator();
		run(19,23);
		printDecorator();
	}

	/**
	 * BBSPRBG algorithm
	 */
	public static void run(int p,int q) {
		int s = 1;

		int n = p*q;
		int l = n-1;
		GCD g = new GCD();
		LinkedHashSet<Integer> x0 = new LinkedHashSet<Integer>(); //maintain unique X0's
		Set<HashSet> distinct = new HashSet<HashSet>();

		for (s=1; s<= (n-1)/2; s++) {
			if(g.computeGCD(s, n) == 1) {
				ArrayList<Integer> x = new ArrayList<Integer>();
				ArrayList<Integer> b = new ArrayList<Integer>();
				LinkedHashSet<Integer> cycle = new LinkedHashSet<Integer>();
				x.add(0,(s*s)%n); //distinct x0 in each loop
				if(!x0.contains(x.get(0))) {
					for(int i=1; i<=l; ++i ) {
						x.add(i,((x.get(i-1))*(x.get(i-1)))%n);
						b.add(i-1,(x.get(i))%2);
						cycle.add(x.get(i));
					}
					distinct.add(cycle);
					System.out.println("For X0 = "+ x.get(0));
					System.out.println("Integers in cycle: "+ cycle);
					System.out.println("Period T: "+ cycle.size());
					System.out.print("First 20 bits in Sequence: ");
					//First 20 bit sequence chars
					for(int k = 0; k <20; k++) {
						System.out.print(b.get(k)+" ");
					}
					System.out.println();
					System.out.println("*****************************************");
					x0.add(x.get(0));
				}

			}
		}
		//Distinct Cycles
		System.out.println("There are \""+distinct.size()+"\" different cycles for this pair: "+ distinct);


	}

	public static void printDecorator() {
		for (int i = 0; i < 80; ++i) {
			System.out.print("=");
		}
		System.out.println();
	}

}
