import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int n = sc.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < a.length; i++) {
			a[i] = sc.nextInt();
		}
		int m = sc.nextInt();
		for (int tc = 0; tc < m; tc++) {
			int target = sc.nextInt();

			out.println(solve(a, target));
		}

		out.close();
		sc.close();
	}

	static String solve(int[] a, int target) {
		int firstIndex = find(a, target, (tgt, x) -> x >= tgt);
		if (firstIndex == -1) {
			return "-1 -1";
		}

		int lastIndex = find(a, target, (tgt, x) -> x > tgt);

		return String.format("%d %d", firstIndex + 1, lastIndex + 1);
	}

	static int find(int[] a, int target, Checker checker) {
		int result = -1;
		int lowerIndex = 0;
		int upperIndex = a.length - 1;
		while (lowerIndex <= upperIndex) {
			int middleIndex = (lowerIndex + upperIndex) / 2;
			if (a[middleIndex] == target) {
				result = middleIndex;
			}

			if (checker.shouldMoveLeft(target, a[middleIndex])) {
				upperIndex = middleIndex - 1;
			} else {
				lowerIndex = middleIndex + 1;
			}
		}

		return result;
	}
}

interface Checker {
	boolean shouldMoveLeft(int target, int x);
}