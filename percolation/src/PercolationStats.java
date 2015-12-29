/* PercolationStats.java */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {

    private double[] fraction;
    private int size;
    private int testTime, X, Y;
    private double mean, stddev;


	public PercolationStats(int N, int T) {
	// perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        } else {
            testTime = T;
            size = N * N;
            fraction = new double[testTime];

            for (int i = 0; i < testTime; i++) {
                Percolation test = new Percolation(N);
                int count = 0;
                while(!test.percolates()) {
                    X = StdRandom.uniform(N) + 1;
                    Y = StdRandom.uniform(N) + 1;
                    if (!test.isOpen(X, Y)) {
                        test.open(X, Y);
                        count++;
                    }
                }
                fraction[i] = count * 1.0 / size;
            }
        }
   	}
   	
   	public double mean() {
        mean = StdStats.mean(fraction);
        return mean;
   	}
   	                      // sample mean of percolation threshold
   	public double stddev() {
        stddev = StdStats.stddev(fraction);
        return stddev;
   	}
   	                    // sample standard deviation of percolation threshold
   	public double confidenceLo() {
        return (mean() - 1.96 * stddev() / Math.sqrt(testTime));
   	}
   	              // low  endpoint of 95% confidence interval
   	public double confidenceHi() {
        return (mean() + 1.96 * stddev() / Math.sqrt(testTime));
   	}
   	              // high endpoint of 95% confidence interval
   	public static void main(String[] args) {

        PercolationStats newTest = new PercolationStats(20, 10);
        System.out.println("stddev = " + newTest.stddev());
        System.out.println("confidenceLo = " + newTest.confidenceLo());
        System.out.println("mean = " + newTest.mean());
        System.out.println("confidenceHi = " + newTest.confidenceHi());
        System.out.println("confidenceLo = " + newTest.confidenceLo());
        //System.out.println(newTest.fraction.length);


   	}    // test client (described below)
}
