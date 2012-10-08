package p1;

import java.math.BigInteger;
import java.util.Random;

/**
 * Representing our algorithm
 * @author dt08mr7
 *
 */
public class Algorithm {
	private static final int PRIME = 1073741789;		// largest prime smaller than 2^30

	/**
	 * Sets the instance for this algorithm to solve
	 * @param graph
	 */
	public void setInstance(Object graph) {

	}

	/**
	 * Solves the currently active instance with currently set parameters
	 */
	public void solve() {

	}

	private boolean pieceOne() {
		BigInteger[][] biAdjacencyMatrix = null;
		
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

	public BigInteger determinant(BigInteger[][] mat) { 
		BigInteger result = BigInteger.ZERO; 

		if(mat.length == 1) { 
			result = mat[0][0]; 
			return result; 
		} 

		if(mat.length == 2) { 
			result = mat[0][0].multiply(mat[1][1]).subtract(mat[0][1].multiply(mat[1][0])); 
			return result; 
		} 

		for(int i = 0; i < mat[0].length; i++) { 
			BigInteger temp[][] = new BigInteger[mat.length - 1][mat[0].length - 1]; 

			for(int j = 1; j < mat.length; j++) { 
				System.arraycopy(mat[j], 0, temp[j-1], 0, i); 
				System.arraycopy(mat[j], i+1, temp[j-1], i, mat[0].length-i-1); 
			} 

			result = result.add(mat[0][i].multiply(determinant(temp).multiply(BigInteger.valueOf(-1L).pow(i)))); 
		} 

		return result;
	}
}
