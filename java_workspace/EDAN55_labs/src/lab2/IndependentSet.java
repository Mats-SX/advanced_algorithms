package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class IndependentSet {
	private int result;
	private int nbrOfRecursiveCalls;
	private int singleNeighbor;		//	helper for case 2 and 3
	private int doubleNeighbor;		// 	helper case 2
	
	private static final int NOT_FOUND = -1;
	private static final int R1 = 1;
	private static final int R2 = 2;

	public static void main(String[] args) throws FileNotFoundException {
//		long start = System.currentTimeMillis();
		IndependentSet alg = new IndependentSet(args[0], args[1]);
		System.out.println(alg.result);
//		System.out.print("Running time: ");
//		System.out.println(System.currentTimeMillis() - start);
		System.out.print("# recursive calls: ");
		System.out.println(alg.nbrOfRecursiveCalls);
	}

	public IndependentSet(String file, String algorithm) throws FileNotFoundException {
		Scanner scan = new Scanner(new FileReader(new File(file)));
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();
		int n = scan.nextInt();
		for (int i = 0; i < n; i++) {
			ArrayList<Integer> row = new ArrayList<Integer>();
			for (int j = 0; j < n; j++) {
				row.add(scan.nextInt());
			}
			matrix.add(row);
		}
		result = recursion(new HashSet<Integer>(), matrix, Integer.parseInt(algorithm));
	}

	private int recursion(HashSet<Integer> removedVertices, ArrayList<ArrayList<Integer>> matrix, int algorithm) {
		nbrOfRecursiveCalls++;
		int index = NOT_FOUND;

		// case 1
		if (removedVertices.size() == matrix.size()) {		// we've removed all vertices
			return 0;
		}

		// case 2
		if (algorithm == R2) {
			index = findDoubleNeighborVertex(removedVertices, matrix);
			if (index != NOT_FOUND) {
				removedVertices.add(index);
				removedVertices.add(singleNeighbor);
				removedVertices.add(doubleNeighbor);
				if (matrix.get(singleNeighbor).get(doubleNeighbor) != 1) {		// no connection u-w
					ArrayList<Integer> z = new ArrayList<Integer>();
					for (int i = 0; i < matrix.size(); i++) {		// add z
						if (i == index) {							// for v
							z.add(0);
							matrix.get(i).add(0);
						} else if (matrix.get(singleNeighbor).get(i) == 1 || matrix.get(doubleNeighbor).get(i) == 1) {
							if (!removedVertices.contains(i)) {
								z.add(1);
							} else
								z.add(0);				// z connected to i
							matrix.get(i).add(1);		// i connected to z
						} else {
							z.add(0);					// z not connected to i
							matrix.get(i).add(0);		// i not connected to z
						}
					}
					z.add(0);							// not connected to itself
					matrix.add(z);
				}
				return 1 + recursion(removedVertices, matrix, algorithm);
			}
		}

		// case 3
		if (algorithm == R1 || algorithm == R2) {
			index = findSingleNeighborVertex(removedVertices, matrix);
			if (index != NOT_FOUND) {
				removedVertices.add(index);
				removedVertices.add(singleNeighbor);
				return 1 + recursion(removedVertices, matrix, algorithm);
			}
		}


		// case 4
		index = findLonely(removedVertices, matrix);
		if (index != NOT_FOUND) {
			// found vertex with no neighbors
			// remove it and return. size goes up 1
			removedVertices.add(index);
			return 1 + recursion(removedVertices, matrix, algorithm);
		}

		// case 5
		index = highDegVertex(removedVertices, matrix);
		removedVertices.add(index);
		HashSet<Integer> noNeighborhood = new HashSet<Integer>(removedVertices);
		for (int i = 0; i < matrix.size(); i++) {
			if (matrix.get(index).get(i) == 1) {
				noNeighborhood.add(i);
				// could check whether degree nodes added
			}
		}
		ArrayList<ArrayList<Integer>> matrixPrime = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < matrix.size(); i++) {
			ArrayList<Integer> row = new ArrayList<Integer>(matrix.get(i));
			matrixPrime.add(row);
		}
		return Math.max(1 + recursion(noNeighborhood, matrix, algorithm), 
							recursion(removedVertices, matrixPrime, algorithm));
	}

	//helpers

	private int findDoubleNeighborVertex(HashSet<Integer> removedVertices, ArrayList<ArrayList<Integer>> matrix) {
		for (int i = 0; i < matrix.size(); i++) {
			if (!removedVertices.contains(i)) {
				int degree = 0;
				for (int j = 0; j < matrix.get(i).size(); j++) {
					if (!removedVertices.contains(j)) {
						degree += matrix.get(i).get(j);
						if (degree > 2) {
							break;
						} else if (matrix.get(i).get(j) == 1) {
							doubleNeighbor = singleNeighbor;
							singleNeighbor = j;
						}
					}
				}
				if (degree == 2) {
					return i;
				}
			}
		}
		return NOT_FOUND;
	}

	private int findSingleNeighborVertex(HashSet<Integer> removedVertices, ArrayList<ArrayList<Integer>> matrix) {
		for (int i = 0; i < matrix.size(); i++) {
			if (!removedVertices.contains(i)) {
				int degree = 0;
				for (int j = 0; j < matrix.get(i).size(); j++) {
					if (!removedVertices.contains(j)) {
						degree += matrix.get(i).get(j);
						if (degree > 1) {
							break;
						} else if (matrix.get(i).get(j) == 1) {
							singleNeighbor = j;
						}
					}
				}
				if (degree == 1) {
					return i;
				}
			}
		}
		return NOT_FOUND;
	}

	private int findLonely(HashSet<Integer> removedVertices, ArrayList<ArrayList<Integer>> matrix) {
		for (int i = 0; i < matrix.size(); i++) {
			if (!removedVertices.contains(i)) {		// only for non-removed vertices
				boolean lonely = true;
				for (int j = 0; j < matrix.get(i).size(); j++) {
					if (!removedVertices.contains(j)) {
						if (matrix.get(i).get(j) == 1) {
							lonely = false;		// we found a neighbor
						}
					}
				}
				if (lonely) {
					return i;
				}
			}
		}
		return NOT_FOUND;
	}

	private int highDegVertex(HashSet<Integer> removedVertices, ArrayList<ArrayList<Integer>> matrix) {
		int vertex = NOT_FOUND;
		int degree = -1;
		for (int i = 0; i < matrix.size(); i++) {
			if (!removedVertices.contains(i)) {
				int thisDeg = -1;
				for (int j = 0; j < matrix.get(i).size(); j++) {
					if (!removedVertices.contains(j)) {
						thisDeg += matrix.get(i).get(j);
					}
				}
				if (thisDeg > degree) {
					degree = thisDeg;
					vertex = i;
				}
			}
		}
		return vertex;
	}
}
