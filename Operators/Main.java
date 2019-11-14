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
		out.println(solve(a));

		out.close();
		sc.close();
	}

	static int solve(int[] a) {
		int[] maxValues = new int[a.length];
		for (int i = 0; i < maxValues.length; i++) {
			maxValues[i] = a[i] + (i == 0 ? 0 : maxValues[i - 1]);

			if (i != 0) {
				maxValues[i] = Math.max(maxValues[i], a[i] * a[i - 1] + (i == 1 ? 0 : maxValues[i - 2]));
			}
		}

		return Arrays.stream(maxValues).max().getAsInt();
	}
}
