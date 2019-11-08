import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int S = Integer.parseInt(st.nextToken()) - 1;
		int F = Integer.parseInt(st.nextToken()) - 1;
		int[][] A = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		out.println(solve(A, S, F));

		out.close();
		br.close();
	}

	static long solve(int[][] A, int S, int F) {
		int N = A.length;

		long[] distances = new long[N];
		Arrays.fill(distances, -1);
		distances[S] = 0;
		boolean[] used = new boolean[N];

		while (true) {
			int vertex = -1;
			for (int i = 0; i < distances.length; i++) {
				if (!used[i] && distances[i] != -1 && (vertex == -1 || distances[i] < distances[vertex])) {
					vertex = i;
				}
			}

			if (vertex == -1) {
				break;
			}

			used[vertex] = true;

			for (int i = 0; i < N; i++) {
				if (!used[i] && A[vertex][i] != -1
						&& (distances[i] == -1 || distances[vertex] + A[vertex][i] < distances[i])) {
					distances[i] = distances[vertex] + A[vertex][i];
				}
			}
		}

		return distances[F];
	}
}
