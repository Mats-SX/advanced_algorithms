package having.fun.with.algorithms;

import java.util.Random;
import java.util.TreeMap;

public class Solver {
	private static final long R = Integer.MAX_VALUE;
	private static final int t = 500;					// our memory size
	private static final Random RAND = new Random();
	private long r;
	private int[] randomNbrs;
	
	/**
	 * Algorithm D
	 */
	public double solve(int N) {
		initRandVector();
		TreeMap<Long, Long> map = new TreeMap<Long, Long>();
		long sequence = 1;
		long current = sequence;
		while (true) {
			long hash = hash2(current);
//			System.out.println("The hash for " + current + " is " + hash);
			if (map.size() < t) {
//				System.out.println("Putting first t numbers into map");
				map.put(hash, current);
			} else if (hash < map.lastKey() && !map.containsKey(hash)) {
//				System.out.println("Found a better hash for " + current);
				map.pollLastEntry();
				map.put(hash, current);
			}
			if (current == 1) {
//				System.out.println("Reached the end of subsequence " + sequence);
				sequence++;
				if (sequence > N) {
//					System.out.println("The end of it all!");
					double d = ((double) (map.size() * R)) / map.lastKey();
//					System.out.println("Estimated # distincts (|C_n|): " + d);
					return d;
				}
				current = sequence;
				continue;
			}
//			System.out.println("Getting new value from sequence " + sequence);
			current = CollatzSimulator.nextInSequence(current);
		}
	}
	
	void initRandVector() {
		randomNbrs = new int[64];
		for(int i = 0; i < randomNbrs.length; i++){
			randomNbrs[i] = RAND.nextInt(Integer.MAX_VALUE);
		}
	}

	long hash(long x) {
		String bits = Long.toBinaryString(x);
		long hash = 0;
		for (int i = 0; bits.length() > 0; i++) {
			int bit = Integer.parseInt(bits.substring(bits.length() - 1));
			if (bit == 1) {
				hash = hash ^ randomNbrs[i];
			}
			bits = bits.substring(0, bits.length() - 1);
		}
		return hash;
	}

	long hash2(long x) {
		long hash = 0;
		for (int i = 0; i < randomNbrs.length; i++) {
			if ((x & (1L << i)) != 0L) {
				hash = hash ^ randomNbrs[i];
			}			
		}
		return hash;
	}
}
