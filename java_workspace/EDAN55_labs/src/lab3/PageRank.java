package lab3_pagerank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

public class PageRank {
	private int[][] matrix;
	private int[] degrees;
	private int[] visits;
	private Random random;
	private static final double ALPHA = 0.85;
	private static final String COMMA = ", ";
	private HashMap<String, Double> H;
	private double[] D;
	
	public PageRank(int[][] matrix, int[] degrees) {
		this.matrix = matrix;
		this.degrees = degrees;
		visits = new int[matrix.length];
		random = new Random();
	}
	
	public void constructHandD() {
		H = new HashMap<String, Double>();
		D = new double[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			if (degrees[i] == 0) {
				D[i] = 1.0 / D.length;
			} else {
				D[i] = 0.0;
			}
			for (int j = 0; j < matrix[i].length; j++) {
				double prob = (double) matrix[i][j] / degrees[i];
				if (prob > 0) {
					H.put(i + COMMA + j, prob);
				}
			}
		}
	}
	
	public void runLinAlg(int iterations, double[] initVector, double precision) {
		for (int i = 0; i < iterations; i++) {
			double[] result = runLinearAlgebra(initVector);
			boolean closeEnough = true;
			for (int j = 0; j < result.length; j++) {
				if (Math.abs(result[j] - initVector[j]) / Math.max(result[j], initVector[j]) > precision) {
					closeEnough = false;
					break;
				}
			}
			initVector = result;
			if (closeEnough) {
				System.out.println("close enough! iterations needed: " + i);
				break;
			}
		}
		print(initVector);
	}
	
	public void printP(int power) {
		double aNumber = (1 - ALPHA) / D.length;
		double[][] P = new double[D.length][D.length];
		for (int i = 0; i < P.length; i++) {
			for (int j = 0; j < P.length; j++) {
				if (H.containsKey(i + COMMA + j)) {
					P[i][j] = ALPHA * (D[i] + H.get(i + COMMA + j)) + aNumber;
				} else
					P[i][j] = ALPHA * (D[i]) + aNumber;
			}
		}
		
		for (int k = 1; k < power; k++) {
			double[][] pNew = new double[P.length][P.length];
			for (int i = 0; i < P.length; i++) {
				for (int j = 0; j < P[i].length; j++) {
//					pNew[i][i] += P[i][j] * P[j][i];
					for(int l = 0; l < P.length; l++){
						pNew[i][j] += P[i][l] * P[l][j];
					}
				}
			}
			P = pNew;
		}
		
		for (int i = 0; i < P.length; i++) {
			for (int j = 0; j < P.length; j++) {
				System.out.print(P[i][j] + " & ");
			}
			System.out.println();
		}
	}
	
	public double[] runLinearAlgebra(double[] initVector) {
		constructHandD();

		double aNumber = (1 - ALPHA) / initVector.length;
		double alphapD = 0.0;
		for (int i = 0; i < initVector.length; i++) {
			alphapD += ALPHA * initVector[i] * D[i];
		}
		double[] pP = new double[initVector.length];
		double[] alphaPH = new double[initVector.length];
		for (int i = 0; i < initVector.length; i++) {
			for (int j = 0; j < initVector.length; j++) {
				if (H.containsKey(j + COMMA + i)) {									// make class with i, j, prob -> store in list -> for each
					alphaPH[i] += ALPHA * initVector[j] * H.get(j + COMMA + i);
				}
			}
			pP[i] = alphaPH[i] + alphapD + aNumber;
		}
		
		return pP;
//		System.out.println("1 - alpha genom n:");
//		System.out.println(aNumber);
//		System.out.println("alphapD");
//		System.out.println(alphapD);
//		System.out.println("alphaPH");
//		print(alphaPH);
//		print(pP);
	}
	
	private void print(double[] v) {
		HashMap<Integer, Double> map = new HashMap<Integer, Double>();

		for (int k = 0; k < 10; k++) {
			
			double max = -1.0;
			int index = -1;
			for (int i = 0; i < v.length; i++) {
				if (v[i] > max && !map.containsKey(i)) {
					max = v[i];
					index = i;
				}
			}
			if (max == -1)
				break;
			System.out.println(index + ": " + max);
			map.put(index, max);
		}
//		for (int i = 0; i < v.length; i++) {
//			System.out.println(v[i]);
//		}
	}
	
	
	public void runSimulation(int iterations, double precision) {
//		boolean[] closeEnough = new boolean[visits.length];
		int location = 0;							// start at 0
		for (int i = 0; i < iterations; i++) {
			if (degrees[location] != 0 && random.nextDouble() < ALPHA) {		// pick edge
				double edge = random.nextDouble();
				for (int k = 0; k < matrix[location].length; k++) {
					double probability = (double) matrix[location][k] / degrees[location];
					//					System.out.println(edge);
					//					System.out.println(probability);
					if (edge < probability) {
						//						System.out.println();
						location = k;
						break;
					} else {
						edge -= probability;
					}
				}
			} else {								// pick random node
				location = random.nextInt(matrix.length);
				//				System.out.println("Rand: " + location);
			}
//			if ((visits[location] /  - visits[location] + 1))
			visits[location]++;
		}
		printLargest(iterations);
	}
	
	private void printLargest(int iterations) {
		HashMap<Integer, Double> map = new HashMap<Integer, Double>();

		for (int k = 0; k < 10; k++) {
			
			double max = -1.0;
			int index = -1;
			for (int i = 0; i < visits.length; i++) {
				double freq = (double) visits[i] / iterations;
				if (freq > max && !map.containsKey(i)) {
					max = freq;
					index = i;
				}
			}
			System.out.println(index + ": " + max);
			map.put(index, max);
		}
	}
//	
//	private class Helement {
//		private ArrayList<ArrayList<Double>> l;
//		
//		public double get(int i, int j) {
//			l.get(i).get(j)
//		}
//	}

}
