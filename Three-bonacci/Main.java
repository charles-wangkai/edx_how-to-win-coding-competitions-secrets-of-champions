import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int A0 = sc.nextInt();
		int A1 = sc.nextInt();
		int A2 = sc.nextInt();
		int n = sc.nextInt();
		out.println(solve(A0, A1, A2, n));

		out.close();
		sc.close();
	}

	static int solve(int A0, int A1, int A2, int n) {
		if (n == 0) {
			return A0;
		} else if (n == 1) {
			return A1;
		} else if (n == 2) {
			return A2;
		}

		int p3 = A0;
		int p2 = A1;
		int p1 = A2;
		for (int i = 3; i <= n; i++) {
			int current = p1 + p2 - p3;

			p3 = p2;
			p2 = p1;
			p1 = current;
		}

		return p1;
	}
}
