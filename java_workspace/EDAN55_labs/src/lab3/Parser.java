package lab3_pagerank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	private Scanner scan;
	private int[][] matrix;
	private int[] degrees;
	private static final Pattern ARC_PATTERN = Pattern.compile("(\\d)\\s(\\d)");
	private Matcher matcher;
	
	public Parser(String filename) {
		try {
			scan = new Scanner(new FileReader(new File(filename)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int[][] getMatrix() {
		return matrix;
	}
	
	public int[] getDegrees() {
		return degrees;
	}
	
	public void parse() {
		int n = scan.nextInt();
		matrix = new int[n][n];
		degrees = new int[n];
		while (scan.hasNext()) {
			matcher = ARC_PATTERN.matcher(scan.nextLine());
			while (matcher.find()) {
				int from = Integer.parseInt(matcher.group(1));
				int to = Integer.parseInt(matcher.group(2));
				matrix[from][to]++;
				degrees[from]++;
			}
		}
	}
	
	public void printMatrix() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		}
	}

	public int getN() {
		return matrix.length;
	}

}
