package p1;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;

/**
 * Representing our algorithm
 * @author dt08mr7
 *
 */
public class Algorithm {
	public static final int PRIME = 32749;		// largest prime smaller than 2^15
	public static final Random RAND = new Random();

	public boolean pieceOne(BigInteger[][][] m, HashSet<Integer> colours) {
//		BigInteger[][] biAdjacencyMatrix = m;
//		Random rand = new Random();
//		for (int i = 0; i < biAdjacencyMatrix.length; i++) {
//			for (int j = 0; j < biAdjacencyMatrix[i].length; j++) {
//				if (!biAdjacencyMatrix[i][j].equals(BigInteger.ZERO)) {
//					biAdjacencyMatrix[i][j] = BigInteger.valueOf(rand.nextInt(PRIME));
//				}
//			}
//		}
//		BigInteger mG = determinant(biAdjacencyMatrix).mod(BigInteger.valueOf(PRIME));
		
		BigInteger mG = determinant(m, colours).mod(BigInteger.valueOf(PRIME));
		
		return !mG.equals(BigInteger.ZERO);
	}

	
	// works fine for n = 10
	/**
	 * Computes the determinant of an integer matrix
	 * @param matrix
	 * @return
	 */
	public BigInteger determinant(BigInteger[][][] matrix, HashSet<Integer> colours) {
		BigInteger result = BigInteger.ZERO;
		
		if (matrix.length == 1) {
			for (int colour : colours) 
				result = result.add(matrix[0][0][colour]);
			return result;
		}
		
		if (matrix.length == 2) {
			BigInteger nollNoll = BigInteger.ZERO;
			BigInteger ettEtt = BigInteger.ZERO;
			BigInteger nollEtt = BigInteger.ZERO;
			BigInteger ettNoll = BigInteger.ZERO;
			for (int colour : colours) {
				nollNoll = nollNoll.add(matrix[0][0][colour]);
				ettEtt = nollNoll.add(matrix[1][1][colour]);
				nollEtt = nollNoll.add(matrix[0][1][colour]);
				ettNoll = nollNoll.add(matrix[1][0][colour]);
			}
			result = nollNoll.multiply(ettEtt).subtract(nollEtt.multiply(ettNoll));
			return result;
		}
		
		for (int i = 0; i < matrix[0].length; i++) {
			BigInteger[][][] temp = new BigInteger[matrix.length - 1][matrix[0].length - 1][matrix[0][0].length];		// decrease first two dimensions
			
			for (int j = 1; j < matrix.length; j++) {
				System.arraycopy(matrix[j], 0, temp[j-1], 0, i);
				System.arraycopy(matrix[j], i+1, temp[j-1], i, matrix[0].length-i-1);
			}
			BigInteger pivot = BigInteger.ZERO;
			for (int colour : colours) {
				pivot = pivot.add(matrix[0][i][colour]);
			}
			result = result.add(pivot.multiply(determinant(temp, colours).multiply(BigInteger.valueOf(-1L).pow(i))));
		}
		return result;
	}

	
	// doesn't work yet (some overflow)
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
			
			int part1 = matrix[0][i] * sign;
			sign *= -1;			// change sign
			int part2 = (part1 * determinant(temp)) % PRIME;
			result = (result + part2) % PRIME;
		} 

		return result;
	}
	
	static class Edge {
		private int colour;
		private int from;
		private int to;
		private BigInteger randomNbr;
		
		public Edge(int from, int to, int colour) {
			this.from = from;
			this.to = to;
			this.colour = colour;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + colour;
			result = prime * result + from;
			result = prime * result + to;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Edge other = (Edge) obj;
			if (colour != other.colour)
				return false;
			if (from != other.from)
				return false;
			if (to != other.to)
				return false;
			return true;
		}
	}
}
