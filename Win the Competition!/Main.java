import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	static final int LIMIT = 300 * 60;

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int n = sc.nextInt();
		int[] t = new int[n];
		for (int i = 0; i < t.length; i++) {
			t[i] = sc.nextInt();
		}
		out.println(solve(t));

		out.close();
		sc.close();
	}

	static int solve(int[] t) {
		Arrays.sort(t);

		int result = 0;
		int sum = 0;
		while (result < t.length && sum + t[result] <= LIMIT) {
			sum += t[result];
			result++;
		}

		return result;
	}
}
