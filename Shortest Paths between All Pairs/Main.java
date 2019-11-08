import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		int[][] A = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				A[i][j] = sc.nextInt();
			}
		}
		out.println(solve(A));

		out.close();
		sc.close();
	}

	static String solve(int[][] A) {
		int N = A.length;

		int[][] distances = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				distances[i][j] = A[i][j];
			}
		}

		for (int k = 0; k < N; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (distances[i][k] != -1 && distances[k][j] != -1
							&& (distances[i][j] == -1 || distances[i][k] + distances[k][j] < distances[i][j])) {
						distances[i][j] = distances[i][k] + distances[k][j];
					}
				}
			}
		}

		return IntStream.range(0, N).mapToObj(i -> IntStream.range(0, N).mapToObj(j -> String.valueOf(distances[i][j]))
				.collect(Collectors.joining(" "))).collect(Collectors.joining("\n"));
	}
}
