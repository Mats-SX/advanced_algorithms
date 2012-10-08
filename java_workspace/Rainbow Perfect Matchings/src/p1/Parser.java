package p1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Either we use this or a Java class to create instances.
 * @author dt08mr7
 *
 */
public class Parser {
	private Scanner scan;
	
	public Parser(String filename) {
		try {
			scan = new Scanner(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
