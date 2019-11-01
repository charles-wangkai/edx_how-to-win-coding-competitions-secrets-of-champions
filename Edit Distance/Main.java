import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		String A = sc.next();
		String B = sc.next();
		out.println(solve(A, B));

		out.close();
		sc.close();
	}

	static int solve(String A, String B) {
		int[][] distances = new int[A.length() + 1][B.length() + 1];
		for (int i = 0; i <= A.length(); i++) {
			for (int j = 0; j <= B.length(); j++) {
				if (i == 0) {
					distances[i][j] = j;
				} else if (j == 0) {
					distances[i][j] = i;
				} else if (A.charAt(i - 1) == B.charAt(j - 1)) {
					distances[i][j] = distances[i - 1][j - 1];
				} else {
					distances[i][j] = Math.min(Math.min(distances[i - 1][j], distances[i][j - 1]),
							distances[i - 1][j - 1]) + 1;
				}
			}
		}

		return distances[A.length()][B.length()];
	}
}
