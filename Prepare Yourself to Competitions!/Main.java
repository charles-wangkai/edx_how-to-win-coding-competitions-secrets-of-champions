import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int n = sc.nextInt();
		int[] p = readArray(sc, n);
		int[] t = readArray(sc, n);
		out.println(solve(p, t));

		out.close();
		sc.close();
	}

	static int[] readArray(Scanner sc, int size) {
		int[] result = new int[size];
		for (int i = 0; i < result.length; i++) {
			result[i] = sc.nextInt();
		}

		return result;
	}

	static int solve(int[] p, int[] t) {
		int maxSum = 0;
		boolean chosenP = false;
		boolean chosenT = false;
		int minDiff = Integer.MAX_VALUE;
		for (int i = 0; i < p.length; i++) {
			if (p[i] >= t[i]) {
				maxSum += p[i];
				chosenP = true;
				minDiff = Math.min(minDiff, p[i] - t[i]);
			} else {
				maxSum += t[i];
				chosenT = true;
				minDiff = Math.min(minDiff, t[i] - p[i]);
			}
		}

		return maxSum - ((chosenP && chosenT) ? 0 : minDiff);
	}
}
