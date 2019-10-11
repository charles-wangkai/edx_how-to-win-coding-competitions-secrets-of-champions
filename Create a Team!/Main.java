import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
	static final int SIZE = 3;

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int[][] table = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				table[i][j] = sc.nextInt();
			}
		}
		out.println(solve(table));

		out.close();
		sc.close();
	}

	static double solve(int[][] table) {
		double result = -1;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (j != i) {
					for (int k = 0; k < SIZE; k++) {
						if (k != i && k != j) {
							result = Math.max(result, Math.sqrt(
									table[0][i] * table[0][i] + table[1][j] * table[1][j] + table[2][k] * table[2][k]));
						}
					}
				}
			}
		}

		return result;
	}
}
