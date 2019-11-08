import java.io.File;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		int M = sc.nextInt();
		int S = sc.nextInt() - 1;
		Edge[] edges = new Edge[M];
		for (int i = 0; i < edges.length; i++) {
			int from = sc.nextInt() - 1;
			int to = sc.nextInt() - 1;
			long weight = sc.nextLong();

			edges[i] = new Edge(from, to, weight);
		}
		out.println(solve(N, edges, S));

		out.close();
		sc.close();
	}

	static String solve(int N, Edge[] edges, int S) {
		BigInteger[] distances = new BigInteger[N];
		distances[S] = BigInteger.ZERO;

		for (int i = 0; i < N - 1; i++) {
			for (Edge edge : edges) {
				if (distances[edge.from] != null && (distances[edge.to] == null || distances[edge.from]
						.add(BigInteger.valueOf(edge.weight)).compareTo(distances[edge.to]) < 0)) {
					distances[edge.to] = distances[edge.from].add(BigInteger.valueOf(edge.weight));
				}
			}
		}

		boolean[] hasNegativeCycles = new boolean[N];
		for (int i = 0; i < N; i++) {
			for (Edge edge : edges) {
				if (hasNegativeCycles[edge.from]
						|| (distances[edge.from] != null && (distances[edge.to] == null || distances[edge.from]
								.add(BigInteger.valueOf(edge.weight)).compareTo(distances[edge.to]) < 0))) {
					hasNegativeCycles[edge.to] = true;
				}
			}
		}

		return IntStream.range(0, N).mapToObj(i -> {
			if (distances[i] == null) {
				return "*";
			} else if (hasNegativeCycles[i]) {
				return "-";
			} else {
				return distances[i].toString();
			}
		}).collect(Collectors.joining("\n"));
	}
}

class Edge {
	int from;
	int to;
	long weight;

	Edge(int from, int to, long weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
}