package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class AlgorithmR {
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 2) {
			System.err.println("Must supply exactly 1 input file and one integer");
			System.exit(0);
		}
		int nbrOfIts = Integer.parseInt(args[1]);

		int grandTotalCut = 0;
		for (int l = 0; l < nbrOfIts; l++) {
			Scanner scan = new Scanner(new FileReader(new File(args[0])));
			int n = scan.nextInt();
			int m = scan.nextInt();
			HashSet<Integer> a = new HashSet<Integer>();
			int maxCut = 0;
			Random random = new Random();
			
			ArrayList<Boolean> dice = new ArrayList<Boolean>();
			int k = (int) Math.ceil((Math.log(n + 1) / Math.log(2)));
			for (int i = 0; i < k; i++) {
				dice.add(random.nextBoolean());
			}
			
			HashSet<HashSet<Integer>> subsets = new HashSet<HashSet<Integer>>();
			HashSet<Integer> subset = new HashSet<Integer>();
			subset.add(1);
			subsets.add(subset);
			for (int i = 2; i <= k; i++) {
				subset = new HashSet<Integer>();
				subset.add(i);
				HashSet<HashSet<Integer>> snapshot = new HashSet<HashSet<Integer>>(subsets);
				for (HashSet<Integer> s : snapshot) {
					HashSet<Integer> combo = new HashSet<Integer>();
					combo.addAll(s);
					combo.addAll(subset);
					subsets.add(combo);
				}
				subsets.add(subset);
			}
			int i = 1;
			for (HashSet<Integer> s : subsets) {
				if (i <= n) {
					boolean b = false;
					for (int j : s) {
						if (b == dice.get(j-1)) {
							b = false;
						} else
							b = true;
					}
					if (b)
						a.add(i);
					i++;
				} else
					break;
			}
			for (i = 0; i < m; i++) {
				int from = scan.nextInt();
				int to = scan.nextInt();
				int weight = scan.nextInt();
				if ((a.contains(from) || a.contains(to)) && !(a.contains(from) && a.contains(to))) {
					maxCut += weight;
				}
			}
			if (maxCut == 0) {
				System.out.println(a.toString());
				System.out.println(dice);
			}
			System.out.println(maxCut);
			grandTotalCut += maxCut;
		}
		System.out.println("The average maximum cut is: " + ((double) grandTotalCut) / nbrOfIts);
	}
}
