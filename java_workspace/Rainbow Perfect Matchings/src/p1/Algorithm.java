package p1;

import java.math.BigInteger;
import java.util.Random;

/**
 * Representing our algorithm
 * @author dt08mr7
 *
 */
public class Algorithm {
	public static final int PRIME = 32749;		// largest prime smaller than 2^15
	private BigInteger[][] biAdjacencyMatrix;
	

	/**
	 * Sets the instance for this algorithm to solve
	 * @param graph
	 */
	public void setInstance(BigInteger[][] m) {
		biAdjacencyMatrix = m;
	}

	/**
	 * Solves the currently active instance with currently set parameters
	 */
	public void solve() {
		pieceOne();
	}

	public boolean pieceOne() {		
		Random rand = new Random();
		for (int i = 0; i < biAdjacencyMatrix.length; i++) {
			for (int j = 0; j < biAdjacencyMatrix[i].length; j++) {
				if (!biAdjacencyMatrix[i][j].equals(BigInteger.ZERO)) {
					biAdjacencyMatrix[i][j] = BigInteger.valueOf(rand.nextInt(PRIME));
				}
			}
		}
		BigInteger mG = determinant(biAdjacencyMatrix).mod(BigInteger.valueOf(PRIME));
		
		return !mG.equals(BigInteger.ZERO);
	}

	public BigInteger determinant(BigInteger[][] matrix) { 
		BigInteger result = BigInteger.ZERO; 

		if(matrix.length == 1) { 
			result = matrix[0][0]; 
			return result; 
		} 

		if(matrix.length == 2) {
			result = matrix[0][0].multiply(matrix[1][1]).subtract(matrix[0][1].multiply(matrix[1][0])); 
			return result; 
		} 

		for(int i = 0; i < matrix[0].length; i++) { 
			BigInteger temp[][] = new BigInteger[matrix.length - 1][matrix[0].length - 1]; 

			for(int j = 1; j < matrix.length; j++) { 
				System.arraycopy(matrix[j], 0, temp[j-1], 0, i); 
				System.arraycopy(matrix[j], i+1, temp[j-1], i, matrix[0].length-i-1); 
			} 

			result = result.add(matrix[0][i].multiply(determinant(temp).multiply(BigInteger.valueOf(-1L).pow(i)))); 
		} 

		return result;
	}

	public int determinant(int[][] matrix) { 
		int result = 0;

		if (matrix.length == 1) { 
			result = matrix[0][0] % PRIME;
			return result; 
		} 

		if (matrix.length == 2) {
			int part1 = (matrix[0][0] * matrix[1][1]) % PRIME;
			int part2 = (matrix[0][1] * matrix[1][0]) % PRIME;
			result = (part1 - part2) % PRIME; 
			return result; 
		} 

		int sign = 1;
		for (int i = 0; i < matrix[0].length; i++) { 
			int temp[][] = new int[matrix.length - 1][matrix[0].length - 1]; 

			for (int j = 1; j < matrix.length; j++) { 
				System.arraycopy(matrix[j], 0, temp[j-1], 0, i); 
				System.arraycopy(matrix[j], i+1, temp[j-1], i, matrix[0].length-i-1); 
			}
			
			int part1 = (matrix[0][i] * sign) % PRIME;
			sign *= -1;			// change sign
			int part2 = (part1 * determinant(temp)) % PRIME;
			result = (result + part2) % PRIME;
		} 

		return result;
	}
}
