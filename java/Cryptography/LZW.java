/**
 * Slim LZW Algorithm
 * Compression and Decompression Algorithms
 * @file LZW.java
 * @author Sai Kishore
 * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;


public class LZW {

	public static HashMap<Integer, String> dictionary;

	public static void main(String[] args) {

		String encodedString = null;

		printDecorator();
		System.out.println("Example 1: Compression of ACBBAAC");
		encodedString = encode("ACBBAAC");
		printDecorator();
		printDecorator();
		System.out.println("Example 3 : Decompression of Example 1");
		decode(encodedString);
		printDecorator();
		printDecorator();
		System.out.println("Example 2 : Compression of AAABAABBBB");
		encodedString = encode("AAABAABBBB");
		printDecorator();
		printDecorator();
		System.out.println("Example 4 : Decompression of Example 4");
		decode(encodedString);
		printDecorator();

	}

	/*Compression Algorithm
	 * */
	public static String encode(String input){
		//Initialize
		initDict();
		ArrayList<Integer> compressedString = new ArrayList<Integer>();
		String szCompressedString = "";
		String word = "";
		int indexCount = 0;
		int newPos = dictionary.size();
		for(int i=0; i<input.length(); ++i) {
			char x = input.charAt(i);
			if(dictionary.containsValue(word+x)) {
				word = word + x;
			} else {
				indexCount++;
				//System.out.print(findIndex(word) + " ");
				compressedString.add(findIndex(word));
				dictionary.put(newPos,word+x);
				word = Character.toString(x);
				newPos++;
			}
		}
		indexCount++;
		//System.out.print(findIndex(word)  + " ");
		compressedString.add(findIndex(word));

		//Print compressed string - Index representation
		System.out.print("Compressed String (Index representation): ");
		for(int z=0; z<compressedString.size(); ++z) {
			System.out.print(compressedString.get(z)+" ");
			szCompressedString += compressedString.get(z)+" ";
		}
		System.out.println();

		//Print compressed string - ASCII representation
		System.out.print("Compressed String (ASCII representation): ");
		for(int z=0; z<compressedString.size(); ++z) {
			System.out.print(dictionary.get(compressedString.get(z))+" ");
		}
		System.out.println();


		System.out.println("New Dictionary: "+dictionary);
		//Java: integers :16bits for each character in string
		float compressionRatio = (float)(indexCount*16)/(float)(input.length()*16);

		System.out.println("Compression Ratio: "+ compressionRatio);

		return szCompressedString;
	}

	/*Decompression Algorithm
	 * input string is delimited by ' '
	 * */
	public static void decode(String input){
		//Initialize
		initDict();
		String[] compressedString =  input.split(" ");
		int y =  Integer.parseInt(compressedString[0]); //null check
		String element = new String(dictionary.get(y));
		System.out.println(element);
		String word = new String(element);
		int newPos = dictionary.size();

		for(int i=1; i<compressedString.length; ++i) {
			y = Integer.parseInt(compressedString[i]);
			System.out.println(y);
			if(dictionary.get(y) == null) {
				element = word + word.charAt(0);
			}
			System.out.println(element);
			dictionary.put(newPos, word+element.charAt(0));
			newPos++;
			word = element;
		}
	}

	/**
	 * Initialize Dictionary
	 */

	public static void initDict(){
		dictionary =  new LinkedHashMap<Integer, String>();
		for(int i = 0; i<256; i++) {
			if(i<=31) dictionary.put(i, Integer.toString(0));
			else dictionary.put(i, Character.toString((char)i));
		}
	}

	/**
	 * Returns first index of match
	 */
	public static int findIndex(String x) {
		for(int i=0; i<dictionary.size(); ++i) {
			if(dictionary.get(i).compareTo(x) == 0) {
				return i;
			}
		}
		return -1;
	}

	public static void printDecorator() {
		for (int i = 0; i < 80; ++i) {
			System.out.print("=");
		}
		System.out.println();
	}

}
