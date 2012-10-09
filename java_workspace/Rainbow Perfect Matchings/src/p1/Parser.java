package p1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import p1.Algorithm.Edge;

/**
 * Either we use this or a Java class to create instances.
 * @author dt08mr7
 *
 */
public class Parser {
	private Scanner scan;
	public HashSet<Integer> colours;
	
	public Parser(String filename) {
		try {
			scan = new Scanner(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * New and good
	 * 
	 */
	public BigInteger[][][] parseWholeStructure() {
		int n = scan.nextInt();
		int m = scan.nextInt();
		
		/*
		 * Initialize biadjacency matrix with all 0s
		 */
		BigInteger[][][] matrix = new BigInteger[n/2][n/2][n/2];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				for (int k = 0; k < matrix[i][j].length; k++) {
					matrix[i][j][k] = BigInteger.ZERO;
				}
			}
		}
		
		/*
		HashSet<Integer> v1 = new HashSet<Integer>();
		HashSet<Integer> v2 = new HashSet<Integer>();
		HashSet<Edge> e = new HashSet<Edge>();
		Edge[][][] eM = new Edge[n/2][n/2][n/2];
		*/
		colours = new HashSet<Integer>();
		
		/*
		 * parsing all edges
		 */
		for (int i = 0; i < m; i++) {
			int from = scan.nextInt();
			int to = scan.nextInt();
			int colour = scan.nextInt();
			matrix[from - 1][to - n / 2 - 1][colour] = BigInteger.valueOf(Algorithm.RAND.nextInt(5) + 1);
			colours.add(colour);
		}
		
		return matrix;
	}
	
	
	// this is old and bad
	public ArrayList<BigInteger[][]> parse() {
		int n = scan.nextInt();
		int m = scan.nextInt();
		
		BigInteger[][] matrix = new BigInteger[n/2][n/2];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = BigInteger.ZERO;
			}
		}
		ArrayList<BigInteger[][]> list = new ArrayList<BigInteger[][]>();
		for (int i = 0; i < n / 2; i++) {
			list.add(Arrays.copyOf(matrix, matrix.length));
		}		
		
		for (int i = 0; i < m; i++) {
			int from = scan.nextInt();
			int to = scan.nextInt();
			int colour = scan.nextInt();
//			list.get(colour - 1)[from - 1][to - n / 2 - 1] = ;
			matrix[from - 1][to - n / 2 - 1] = BigInteger.ONE;
		}
		
		list.add(matrix);
		return list;
	}
	
	public BigInteger[][] parseBiAdjacencyMatrixNoColours() {
		int n = scan.nextInt();
		int m = scan.nextInt();
		
		BigInteger[][] matrix = new BigInteger[n/2][n/2];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = BigInteger.ZERO;
			}
		}
		for (int i = 0; i < m; i++) {
			int from = scan.nextInt();
			int to = scan.nextInt();
			int colour = scan.nextInt();
			matrix[from - 1][to - n / 2 - 1] = BigInteger.ONE;
		}
		return matrix;
	}
	
	public Object getGraph() {
		return null;
	}

}
