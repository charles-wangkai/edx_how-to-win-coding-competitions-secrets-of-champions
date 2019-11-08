import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		boolean[][] graph = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			String line = sc.next();
			for (int j = 0; j < N; j++) {
				graph[i][j] = line.charAt(j) == '1';
			}
		}
		out.println(solve(graph));

		out.close();
		sc.close();
	}

	static String solve(boolean[][] graph) {
		int N = graph.length;

		int[] toV0s = dp(reverse(graph));
		int[] fromV0s = dp(graph);

		List<String> paths = new ArrayList<>();
		for (int A = 0; A < N; A++) {
			for (int B = 0; B < N; B++) {
				if (B != A) {
					String path = findPath(graph, toV0s, fromV0s, A, B);
					if (path != null) {
						paths.add(path);
					}
				}
			}
		}

		if (paths.isEmpty()) {
			return "No paths";
		}

		return String.join("\n", paths);
	}

	static String findPath(boolean[][] graph, int[] toV0s, int[] fromV0s, int A, int B) {
		int N = graph.length;

		for (int toMask = 0; toMask < toV0s.length; toMask++) {
			int fromMask = ((1 << (N - 1)) - 1) ^ toMask;

			if (((toV0s[toMask] & (1 << A)) != 0) && ((fromV0s[fromMask] & (1 << B)) != 0)) {
				List<Integer> vertices = buildPart(reverse(graph), toV0s, toMask, A);
				Collections.reverse(vertices);
				vertices.remove(vertices.size() - 1);
				vertices.addAll(buildPart(graph, fromV0s, fromMask, B));

				return vertices.stream().map(vertex -> String.valueOf(vertex + 1)).collect(Collectors.joining(" -> "));
			}
		}

		return null;
	}

	static List<Integer> buildPart(boolean[][] graph, int[] d, int mask, int vertex) {
		List<Integer> result = new ArrayList<>();
		while (true) {
			result.add(vertex);
			if (vertex == 0) {
				break;
			}

			int prevMask = mask ^ (1 << (vertex - 1));
			for (int i = 0;; i++) {
				if (((d[prevMask] & (1 << i)) != 0) && graph[i][vertex]) {
					mask = prevMask;
					vertex = i;

					break;
				}
			}
		}

		Collections.reverse(result);

		return result;
	}

	static boolean[][] reverse(boolean[][] graph) {
		int N = graph.length;

		boolean[][] result = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				result[i][j] = graph[j][i];
			}
		}

		return result;
	}

	static int[] dp(boolean[][] graph) {
		int N = graph.length;

		int[] g = compressGraph(graph);

		int[] result = new int[1 << (N - 1)];
		result[0] = 1;

		for (int mask = 0; mask < result.length; mask++) {
			for (int i = 1; i < N; i++) {
				if ((mask & (1 << (i - 1))) != 0) {
					int prevMask = mask ^ (1 << (i - 1));

					if ((result[prevMask] & g[i]) != 0) {
						result[mask] |= 1 << i;
					}
				}
			}
		}

		return result;
	}

	static int[] compressGraph(boolean[][] graph) {
		int N = graph.length;

		int[] g = new int[N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (graph[j][i]) {
					g[i] |= 1 << j;
				}
			}
		}

		return g;
	}
}
