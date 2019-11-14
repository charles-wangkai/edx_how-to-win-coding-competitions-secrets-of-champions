import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
	static final int SIZE = 26;

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		sc.nextInt();
		int m = sc.nextInt();
		String s = sc.next();
		char[] froms = new char[m];
		char[] tos = new char[m];
		int[] costs = new int[m];
		for (int i = 0; i < m; i++) {
			froms[i] = sc.next().charAt(0);
			tos[i] = sc.next().charAt(0);
			costs[i] = sc.nextInt();
		}
		out.println(solve(s, froms, tos, costs));

		out.close();
		sc.close();
	}

	static long solve(String s, char[] froms, char[] tos, int[] costs) {
		int[][] distances = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				distances[i][j] = (i == j) ? 0 : -1;
			}
		}

		for (int i = 0; i < froms.length; i++) {
			int from = froms[i] - 'a';
			int to = tos[i] - 'a';

			if (distances[from][to] == -1 || costs[i] < distances[from][to]) {
				distances[from][to] = costs[i];
			}
		}

		for (int k = 0; k < SIZE; k++) {
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					if (distances[i][k] != -1 && distances[k][j] != -1
							&& (distances[i][j] == -1 || distances[i][k] + distances[k][j] < distances[i][j])) {
						distances[i][j] = distances[i][k] + distances[k][j];
					}
				}
			}
		}

		long result = -1;
		for (int i = 1; i * i <= s.length(); i++) {
			if (s.length() % i == 0) {
				for (int partLength : new int[] { i, s.length() / i }) {
					if (partLength != s.length()) {
						long totalCost = computeTotalCost(s, distances, partLength);

						if (totalCost != -1 && (result == -1 || totalCost < result)) {
							result = totalCost;
						}
					}
				}
			}
		}

		return result;
	}

	static long computeTotalCost(String s, int[][] distances, int partLength) {
		long result = 0;
		int partNum = s.length() / partLength;

		for (int i = 0; i < partLength; i++) {
			long minCost = Long.MAX_VALUE;
			for (int target = 0; target < SIZE; target++) {
				long cost = 0;
				for (int j = 0; j < partNum; j++) {
					int distance = distances[s.charAt(j * partLength + i) - 'a'][target];
					if (distance == -1) {
						cost = -1;

						break;
					}

					cost += distance;
				}

				if (cost != -1) {
					minCost = Math.min(minCost, cost);
				}
			}

			if (minCost == Long.MAX_VALUE) {
				return -1;
			}

			result += minCost;
		}

		return result;
	}
}
