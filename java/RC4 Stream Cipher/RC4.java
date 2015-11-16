/**
 * RC4 Stream Cipher
 *
 * @file RC4.java
 * @author Sai Kishore
 * @notes:
 * 		KSA - Key Scheduling Algorithm
 * 		SGA - Subkey Generation Algorithm
 * */
package com.saikishoremv.coen350.assignments.a2;

/**
 * Key Scheduling Algorithm and Subkey Generation Algorithm
 */

class RC4KeyGen {

	private byte[] S = new byte[256];
	private byte[] K;
	private int l = 1;

	// SGA Parameters
	int i = 0;
	int j = 0;
	int u = 0;

	public RC4KeyGen(byte[] key, int keylength) {

		// Set key and key length specified by user
		l = keylength;
		K = key;

		// Initialize
		init();
	}

	// Initialization - KSA
	// Initial Permutation - KSA
	private void init() {
		for (int i = 0; i <= 255; ++i) {
			S[i] = (byte) i;
		}
		int j = 0;
		for (int i = 0; i <= 255; ++i) {
			// byte in java is unsigned
			j = (j + (S[i] & 0xFF) + (K[i % l] & 0xFF)) % 256;
			// Swap
			byte temp = S[i];
			S[i] = S[j];
			S[j] = temp;
		}
	}

	// Subkey Generation Algorithm - Geneartion Loop
	public int nextSubKey() {
		u++;
		i = (i + 1) % 256;
		j = (j + (S[i] & 0xFF)) % 256;
		byte temp = S[i];
		S[i] = S[j];
		S[j] = temp;
		return S[((S[i] & 0xFF) + (S[j] & 0xFF)) % 256] & 0xFF;
	}
}

public class RC4 {

	/**
	 * @param args
	 *            RC4 Stream Cipher Entry Point
	 */
	public static void main(String[] args) {

		byte[] key;

		// Run 1: K(i) = 0 for 0<=i<=255 and l = 256
		key = new byte[256];

		for (int i = 0; i <= 255; ++i) {
			key[i] = 0;
		}

		RC4KeyGen rc4_1 = new RC4KeyGen(key, 256);
		// Generate and pring 20 sub keys
		printDecorator();
		System.out.println("Run 1 :: First 20 bytes of key stream");
		printDecorator();
		for (int i = 0; i < 20; ++i) {
			System.out.println(rc4_1.nextSubKey());
		}

		// Run 2 : K = [15,202,33,6,8] and l = 5
		key = new byte[5];
		// Initialize
		key[0] = (byte) 15;
		key[1] = (byte) 202;
		key[2] = (byte) 33;
		key[3] = (byte) 6;
		key[4] = (byte) 8;
		RC4KeyGen rc4_2 = new RC4KeyGen(key, 5);
		// Generate and pring 20 sub keys
		printDecorator();
		System.out.println("Run 2 :: First 20 bytes of key stream");
		printDecorator();
		for (int i = 0; i < 20; ++i) {
			System.out.println(rc4_2.nextSubKey());
		}
	}

	public static void printDecorator() {
		for (int i = 0; i < 40; ++i) {
			System.out.print("=");
		}
		System.out.println();
	}
}
