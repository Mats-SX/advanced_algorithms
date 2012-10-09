package test;

import java.math.BigInteger;
import java.util.Random;

import p1.Algorithm;
import p1.Parser;

public class Test {
	
	public static void main(String[] args) {
//		testDeterminant(1);
//		testPieceOne();
		testNewParsingAndDeterminantTripleStructure();
	}
	
	static void testNewParsingAndDeterminantTripleStructure() {
		Parser p = new Parser("g6_2prm.txt");
		BigInteger[][][] triple = p.parseWholeStructure();
		for (int i = 0; i < triple.length; i++) {
			for (int j = 0; j < triple[i].length; j++) {
				System.out.print("[ ");
				for (int k = 0; k < triple[i][j].length; k++) {
					System.out.print(triple[i][j][k] + " ");
				}
				System.out.print("]");
			}
			System.out.println();
		}
		
		Algorithm alg = new Algorithm();
		BigInteger result = alg.determinant(triple, p.colours);
		System.out.println(result);
	}
	
	static void testDeterminant() {
		Random rand = new Random();
		int n = 10;
		BigInteger[][] matrix = new BigInteger[n][n];
		System.out.print("[");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = BigInteger.valueOf(rand.nextInt(1000));
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println(";");
		}
		System.out.println("]");
		
		Algorithm alg = new Algorithm();
//		System.out.println(alg.determinant(matrix).toString());
	}
	
	static void testDeterminant(int v) {
		Random rand = new Random();
		int n = 10;
		int[][] matrix = new int[n][n];
		System.out.print("[");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = rand.nextInt(Algorithm.PRIME);
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println("; ");
		}
		System.out.println("]");
		
		Algorithm alg = new Algorithm();
		System.out.println(alg.determinant(matrix));
	}
	
	static void testPieceOne() {
		Parser p = new Parser("g10_true.txt");
		Algorithm a = new Algorithm();
//		System.out.println(a.pieceOne(p.parseBiAdjacencyMatrixNoColours()));
	}
}
