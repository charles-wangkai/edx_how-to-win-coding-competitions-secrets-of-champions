import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		int[] args1 = new int[N];
		int[] args2 = new int[N];
		for (int i = 0; i < N; i++) {
			args1[i] = sc.nextInt();
			args2[i] = sc.nextInt();
		}
		out.println(solve(args1, args2));

		out.close();
		sc.close();
	}

	static long solve(int[] args1, int[] args2) {
		int N = args1.length;

		int[] prevs = new int[N + 1];
		int[] weights = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			if (args2[i - 1] == 0) {
				prevs[i] = prevs[prevs[args1[i - 1]]];
				weights[i] = weights[prevs[args1[i - 1]]];
			} else {
				prevs[i] = args1[i - 1];
				weights[i] = weights[prevs[i]] + args2[i - 1];
			}
		}

		return Arrays.stream(weights).asLongStream().sum();
	}
}
