package test;

import java.math.BigInteger;
import java.util.Random;

import p1.Algorithm;

public class Test {
	
	public static void main(String[] args) {
		testDeterminant();
	}
	
	static void testDeterminant() {
		Random rand = new Random();
		int n = 6;
		BigInteger[][] matrix = new BigInteger[n][n];
		System.out.print("{");
		for (int i = 0; i < n; i++) {
			System.out.print("{");
			for (int j = 0; j < n; j++) {
				matrix[i][j] = BigInteger.valueOf(rand.nextInt(1000));
				System.out.print(matrix[i][j] + ",");
			}
			System.out.println("},");
		}
		System.out.println("}");
		
		Algorithm alg = new Algorithm();
		System.out.println(alg.determinant(matrix).toString());
	}
}
