import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int n = sc.nextInt();
		int k = sc.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < a.length; i++) {
			a[i] = sc.nextInt();
		}
		out.println(solve(a, k));

		out.close();
		sc.close();
	}

	static int solve(int[] a, int k) {
		sort(a);

		int result = -1;
		int lower = 0;
		int upper = Arrays.stream(a).min().getAsInt();
		while (lower <= upper) {
			int middle = (lower + upper) / 2;

			if (check(a, k, middle)) {
				result = middle;

				lower = middle + 1;
			} else {
				upper = middle - 1;
			}
		}

		return result;
	}

	static void sort(int[] a) {
		int[] sorted = Arrays.stream(a).boxed().sorted().mapToInt(x -> x).toArray();

		System.arraycopy(sorted, 0, a, 0, a.length);
	}

	static boolean check(int[] a, int k, int x) {
		int colored = 0;
		int uncolored = k;
		for (int ai : a) {
			int colorCount = Math.max(ai - colored, x);

			if (uncolored < colorCount) {
				return false;
			}

			colored += colorCount;
			uncolored -= colorCount;
		}

		return true;
	}
}
