package p1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

/**
 * For all user interaction. Reads input, posts error messages, etc.
 * @author dt08mr7
 * @author dt08ml5
 *
 */
public class Main {
	public static final int PRIME = 32749;		// largest prime smaller than 2^15
	
	public static void main(String[] args) throws FileNotFoundException {
		// 1
		int prime = PRIME;
		
		Scanner scan = new Scanner(new FileReader(args[0]));
		int n = scan.nextInt();
		n = n / 2;
		int m = scan.nextInt();

		ArrayList<int[][]> colourMatrices = new ArrayList<int[][]>();
		for (int i = 0; i < n; i++) {
			colourMatrices.add(new int[n][n]);
		}
		
		// 2
		// additional optimization
		ArrayList<HashSet<Integer>> colourSets = new ArrayList<HashSet<Integer>>();
		// TODO: Add comments about this in report; we're not following the algorithms verbatim
		HashSet<Integer> addedColours = new HashSet<Integer>();
		Random rand = new Random();
		for (int i = 0; i < m; i++) {
			int from = scan.nextInt();
			int to = scan.nextInt();
			int colour = scan.nextInt();
			colourMatrices.get(colour)[from][to] = rand.nextInt(prime);
			
			// creates all subsets as a new colour is found
			if (!addedColours.contains(colour)){
				addedColours.add(colour);
				ArrayList<HashSet<Integer>> newColourSets = new ArrayList<HashSet<Integer>>();
				for (HashSet<Integer> set : colourSets){
					//Does not copy single elements but does not matter since they are ints.
					// TODO: Unsafe copying. We should use constructor (new HashSet<Integer>(set))
					HashSet<Integer> combination = (HashSet<Integer>) set.clone(); 
					combination.add(colour);
					newColourSets.add(combination);
				}
				HashSet<Integer> aloneColourSet = new HashSet<Integer>();
				aloneColourSet.add(colour);
				colourSets.add(aloneColourSet);
				colourSets.addAll(newColourSets);
			}
		}

		// 3
		int[][] fullMatrix = new int[n][n];			// we call it B in report
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					fullMatrix[j][k] += colourMatrices.get(i)[j][k];
				}
			}
		}
		
		// 4
		int dB = 0;
		// dB = determinant(fullMatrix);
		
		// 5
		if (dB == 0)
			System.out.println("No");
		// TODO: End here
		
		// 6
		int sum = 0;
		
		// 7
		for (HashSet<Integer> X : colourSets) {
			int[][] M = new int[n][n];
			for (int i : X) {
				for (int j = 0; j < n; j++) {
					for (int k = 0; k < n; k++) {
						// TODO: Bug! should be M, right?
						fullMatrix[j][k] += colourMatrices.get(i)[j][k];
					}
				}
			}
			sum += Math.pow(-1, n - 1 - X.size()); //* determinant(M) mod p */1;
		}
		
		// 8
		if (dB - sum == 0)
			System.out.println("No");
		else
			System.out.println("Yes");
	}

}
