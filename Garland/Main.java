import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	static final int ITERATION_NUM = 100;

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int n = sc.nextInt();
		double A = sc.nextDouble();
		out.println(solve(n, A));

		out.close();
		sc.close();
	}

	static double solve(int n, double A) {
		double[] h = new double[n];
		h[0] = A;

		double result = -1;
		double h1Lower = 0;
		double h1Upper = A;
		for (int iter = 0; iter < ITERATION_NUM; iter++) {
			double h1Middle = (h1Lower + h1Upper) / 2;

			h[1] = h1Middle;
			for (int i = 2; i < h.length; i++) {
				h[i] = (h[i - 1] + 1) * 2 - h[i - 2];
			}

			if (Arrays.stream(h).allMatch(x -> x >= 0)) {
				result = h[h.length - 1];

				h1Upper = h1Middle;
			} else {
				h1Lower = h1Middle;
			}
		}

		return result;
	}
}
