import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
	static final int A_LIMIT = 360_000_000;

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		int M = sc.nextInt();
		int[] R = new int[N];
		int[] D = new int[N];
		for (int i = 0; i < N; i++) {
			R[i] = sc.nextInt();
			D[i] = sc.nextInt();
		}
		int[] A = new int[M];
		for (int i = 0; i < A.length; i++) {
			A[i] = sc.nextInt();
		}
		int is = sc.nextInt() - 1;
		int js = sc.nextInt() - 1;
		int it = sc.nextInt() - 1;
		int jt = sc.nextInt() - 1;
		out.println(solve(R, D, A, is, js, it, jt));

		out.close();
		sc.close();
	}

	static double solve(int[] R, int[] D, int[] A, int is, int js, int it, int jt) {
		double result = Double.MAX_VALUE;
		for (int i = 0; i < R.length; i++) {
			int angleDiff = Integer.MAX_VALUE;
			if (D[i] != -1) {
				angleDiff = Math.min(angleDiff, (A[jt] - A[js] + A_LIMIT) % A_LIMIT);
			}
			if (D[i] != 1) {
				angleDiff = Math.min(angleDiff, (A[js] - A[jt] + A_LIMIT) % A_LIMIT);
			}

			result = Math.min(result,
					Math.abs(R[is] - R[i]) + 2 * Math.PI * R[i] * angleDiff / A_LIMIT + Math.abs(R[it] - R[i]));
		}

		return result;
	}
}
