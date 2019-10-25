import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
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
		int k = sc.nextInt();
		out.println(solve(a, k));

		out.close();
		sc.close();
	}

	static int solve(int[] a, int k) {
		if (k == 1) {
			return Arrays.stream(a).max().getAsInt();
		}

		int result = -1;
		int lower = 0;
		int upper = Arrays.stream(a).max().getAsInt();
		while (lower <= upper) {
			int middle = (lower + upper) / 2;

			if (check(a, k, middle)) {
				result = middle;

				upper = middle - 1;
			} else {
				lower = middle + 1;
			}
		}

		return result;
	}

	static boolean check(int[] a, int k, int time) {
		return Arrays.stream(a).map(ai -> divideToCeil(Math.max(0, ai - time), k - 1)).asLongStream().sum() <= time;
	}

	static int divideToCeil(int x, int y) {
		return x / y + (x % y == 0 ? 0 : 1);
	}
}
