package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class MaximumCut {
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 2) {
			System.err.println("Must supply exactly 1 input file and one integer");
			System.exit(0);
		}
		int nbrOfIts = Integer.parseInt(args[1]);

		int grandTotalCut = 0;
		for (int k = 0; k < nbrOfIts; k++) {
			Scanner scan = new Scanner(new FileReader(new File(args[0])));
			int n = scan.nextInt();
			int m = scan.nextInt();
			HashSet<Integer> a = new HashSet<Integer>();
			int maxCut = 0;
			Random random = new Random();

			for (int i = 1; i <= n; i++) {
				if (random.nextBoolean()) {
					a.add(i);
				}
			}
			for (int i = 0; i < m; i++) {
				int from = scan.nextInt();
				int to = scan.nextInt();
				int weight = scan.nextInt();
				if ((a.contains(from) || a.contains(to)) && !(a.contains(from) && a.contains(to))) {
					maxCut += weight;
				}
			}
			System.out.println(maxCut);
			grandTotalCut += maxCut;
		}
		System.out.println("The average maximum cut is: " + ((double) grandTotalCut) / nbrOfIts);
	}
}