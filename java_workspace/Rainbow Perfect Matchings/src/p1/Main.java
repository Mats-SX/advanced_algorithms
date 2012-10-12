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
 * 
 * @author dt08mr7
 * @author dt08ml5
 * 
 */
public class Main {
	public static final int PRIME = 32749; // largest prime smaller than 2^15

	public static void main(String[] args) throws FileNotFoundException {
		// 1
		int prime = PRIME;

		Scanner scan = new Scanner(new FileReader(args[0]));
		int n = scan.nextInt();
		init_inverses(prime);
		int m = scan.nextInt();

		ArrayList<HashSet<Integer>> colourSets = new ArrayList<HashSet<Integer>>();
		ArrayList<int[][]> colourMatrices = new ArrayList<int[][]>();
		HashSet<Integer> allColours = new HashSet<Integer>();
		for (int i = 0; i < n; i++) {
			colourMatrices.add(new int[n][n]);
			
			//needs to be done for every colour even if not mentioned in input file
			ArrayList<HashSet<Integer>> newColourSets = new ArrayList<HashSet<Integer>>();
			for (HashSet<Integer> set : colourSets) {
				HashSet<Integer> combination = new HashSet<Integer>(set);
				combination.add(i);
				newColourSets.add(combination);
			}
			HashSet<Integer> aloneColourSet = new HashSet<Integer>();
			aloneColourSet.add(i);
			allColours.add(i);
			colourSets.add(aloneColourSet);
			colourSets.addAll(newColourSets);
		}
		// need to investigate if this works
		colourSets.remove(allColours);

		// 2
		HashSet<Integer> addedColours = new HashSet<Integer>();
		Random rand = new Random();
		for (int i = 0; i < m; i++) {
			int from = scan.nextInt();
			int to = scan.nextInt();
			int colour = scan.nextInt();
			//should be exclusive p as well see andreas comments on the java implementation of det.
			colourMatrices.get(colour)[from][to] = rand.nextInt(prime-1) + 1;
		}
		// TODO: This is really awkward. Is addedColours.equals(<whatever is in there>) == true?
		//But addedColours does not always contain the full set
		colourSets.remove(addedColours); //Removes the full set.

		// 3
		int[][] fullMatrix = new int[n][n]; // we call it B in report
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					fullMatrix[j][k] = (fullMatrix[j][k] + colourMatrices.get(i)[j][k]) % PRIME;
				}
			}
		}

		// 4
		// TODO: Should we perhaps take dB = det(B) mod p ?
		// We already do since that is how the determinant function works :)
		int dB = 0;
		dB = determinant(fullMatrix, prime);

		// 5
		if (dB == 0) {
			System.out.println("No");
			return;
		}

		// 6
		int sum = 0;

		// 7
		for (HashSet<Integer> X : colourSets) {
			int[][] M = new int[n][n];
			for (int i : X) {
				for (int j = 0; j < n; j++) {
					for (int k = 0; k < n; k++) {
						// Fixed before, got merge conflicts grr.
						M[j][k] = (M[j][k] + colourMatrices.get(i)[j][k]) % PRIME;
					}
				}
			}
			// Here is were all our problems lie, not sure we can remove the full set...
			// Signs will be strange
			int detM = determinant(M, prime);
			System.out.println("detM: " + detM);
			sum += Math.pow(-1, n - 1 - X.size()) * detM;
		}

		// 8
		sum = sum % PRIME;
		System.out.println(dB + " - " + sum);
		if (dB - sum == 0)
			System.out.println("No");
		else
			System.out.println("Yes");
	}

	
	
	// Andreas det methods.
	static int inv[];

	public static int modexp(int a, int e, int p) {
		if (e == 1) {
			return a;
		} else {
			int sq = modexp(a, e / 2, p);
			sq = (sq * sq) % p;
			if ((e & 1) == 1) {
				sq = (sq * a) % p;
			}
			return sq;
		}
	}

	public static void init_inverses(int p) {
		int i;
		inv = new int[p];
		for (i = 1; i < p; i++) {
			inv[i] = modexp(i, p - 2, p);
		}
	}

	public static int determinant(int[][] A, int p) {
		int n = A[0].length;
		int i, j, k;
		int rs[], vis[];
		int d = 1;
		int sgn = 0;
		rs = new int[n];
		vis = new int[n];
		for (i = 0; i < n; i++) {
			rs[i] = 0;
		}
		for (i = 0; i < n; i++) {
			int who = -1;
			for (j = 0; j < n; j++)
				if (rs[j] == 0 && A[j][i] > 0) {
					rs[j] = i + 1;
					who = j;
					d = (d * A[j][i]) % p;
					break;
				}
			if (who == -1) {
				d = 0;
				break;
			}
			for (j = 0; j < n; j++)
				if (rs[j] == 0 && A[j][i] > 0) {
					d = (d * inv[A[who][i]]) % p;

					for (k = n - 1; k >= i; k--) {
						A[j][k] = (A[who][i] * A[j][k] - A[j][i] * A[who][k] + p
								* p)
								% p;
					}
				}
		}
		if (d > 0) {
			sgn = n;
			for (i = 0; i < n; i++) {
				vis[i] = 0;
			}
			for (i = 0; i < n; i++) {
				if (vis[i] == 0) {
					sgn--;
					j = i;
					while (vis[j] == 0) {
						vis[j] = 1;
						j = rs[j] - 1;
					}
				}
			}

			if ((sgn & 1) == 1)
				d = p - d;
		}
		return d;
	}

}
