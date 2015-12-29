/* Percolation.java*/
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

	private boolean[][] pixel;
	private int length;
	private int size;
	private int top;
	private int bottom;
	private WeightedQuickUnionUF WQUUF, extraUF;


	public Percolation(int N) {
		// create N-by-N grid, with all sites blocked
		if (N <= 0) {
			throw new java.lang.IllegalArgumentException();
		} else {
			length = N;
			size   = N * N;
			WQUUF  = new WeightedQuickUnionUF(3 + size);
			extraUF = new WeightedQuickUnionUF(2 + size);
			pixel  = new boolean[length][length];
			top    = size + 1;
			bottom = size + 2;

			for (int i = 0; i < length; i++) {
				for (int j = 0; j < length; j++) {
					pixel[i][j] = false;
				}
			}
		}
	}              

	public void open(int i, int j) {
		// open site (row i, column j) if it is not open already
		int num = (i - 1) * (length) + j;

		if (isValid(i, j)) {
			pixel[i - 1][j - 1] = true;
			if (i == 1) {
				WQUUF.union(num, top);
				extraUF.union(num, top);
			}
			if (i == (length)) {
				WQUUF.union(num, bottom);
			}
			if ((i - 1) >= 1) {
				if (isOpen(i - 1, j)) {
					WQUUF.union(num, num - (length));
					extraUF.union(num, num - (length));
				}
			}
			if ((i + 1) <= (length)) {
				if (isOpen(i + 1, j)) {
					WQUUF.union(num, num + (length));
					extraUF.union(num, num + (length));
				}
			}
			if ((j - 1) >= 1) {
				if (isOpen(i, j - 1)) {
					WQUUF.union(num, num - 1);
					extraUF.union(num, num - 1);
				}
			}
			if ((j + 1) <= (length)) {
				if (isOpen(i, j + 1)) {
					WQUUF.union(num, num + 1);
					extraUF.union(num, num + 1);
				}
			}
		}
	}

	public boolean isOpen(int i, int j) {
		// is site (row i, column j) open?

		if (isValid(i, j)) {
			return pixel[i - 1][j - 1];
		} else {
			return false;
		}
	}

	public boolean isFull(int i, int j) {
		// is site (row i, column j) full?
		int num = (i - 1) * (length) + j;

		if (isValid(i, j)) {
			return extraUF.connected(num, top);
		} else {
			return false;
		}
	}    

	public boolean percolates() {
		// does the system percolate?
		return WQUUF.connected(top, bottom);
	}

	private boolean isValid(int i, int j) {
		if (i <= 0 || i > (length)) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		if (j <= 0 || j > (length)) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		return true;
	}

}