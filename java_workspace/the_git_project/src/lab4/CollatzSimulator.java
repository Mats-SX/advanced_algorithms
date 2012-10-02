package lab4;

import java.util.HashSet;

public class CollatzSimulator {
	private HashSet<Long> distinctValues;
	private long lengthOfSequence;
	private long maximumValue;
	private long counter;
	
	public static long nextInSequence(long current) {
		if (current == 1)
			return current;
		if (current % 2 == 0) {			// even
			return current / 2;
		}
		return 3 * current + 1;
	}
	
	public CollatzSimulator() {
		distinctValues = new HashSet<Long>();
	}
	
	/**
	 * For algorithms before D
	 * @param N
	 * @param inSequence
	 */
	public void createSequencesUpTo(int N, int inSequence) {
		resetFields();
		long current = 1;							// we start with sequence 1
		long sequence = 1;
		while (true) {
//			System.out.println("A new step in sequence " + sequence);
//			System.out.println("Current is " + current);
			distinctValues.add(current);			// add number to set of distincts (dictionary?)
			lengthOfSequence++;						// increment length
			if (current > maximumValue) {			// new maximum
				maximumValue = current;
//				System.out.println("Found new maximum: " + maximumValue);
			}
			if (current == inSequence) {			// for quadratic time algorithm
				counter++;							// we've found another of the i:s
//				System.out.println("Found " + inSequence + " in sequence " + N);
				return;	
			}
			if (current == 1) {						// we reached the end of the previous subsequence
				sequence++;							// move on to the next subsequence
				if (sequence > N) {						// we've reached the end of the whole sequence
//					System.out.println("Reached end!");
					return;								// end
				}
				current = sequence;
//				System.out.println("We've reached the end of this subsequence. Starting new subsequence at " + sequence);
				continue;
			}
			current = CollatzSimulator.nextInSequence(current);		// get the next number in current sequence
		}
	}
	
	public int getDistinctValues() {
		return distinctValues.size();
	}

	public long getLengthOfSequence() {
		return lengthOfSequence;
	}

	public long getMaximumValue() {
		return maximumValue;
	}

	public long getCounter() {
		return counter;
	}

	private void resetFields() {
		distinctValues.clear();
		lengthOfSequence = maximumValue = counter = 0;
	}

}
