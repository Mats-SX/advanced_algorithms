package lab3_pagerank;

public class Main {
	
	public static void main(String[] args) {
		Parser parser = new Parser(args[0]);
		int iterations = Integer.parseInt(args[1]);
		parser.parse();
		PageRank pg = new PageRank(parser.getMatrix(), parser.getDegrees());
//		double[] p = {0.25, 0.25, 0.25, 0.25};
		double precision = 0.01;
		double[] p = new double[parser.getN()];
		for (int i = 0; i < parser.getN(); i++) {
			p[i] = 1.0 / parser.getN();
		}
		pg.runLinAlg(iterations, p, precision);
//		pg.printP(10);
//		pg.runSimulation(iterations, 0.0);
	}

}
