import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
	static int spell;

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		int M = sc.nextInt();
		int[] a = new int[M];
		int[] b = new int[M];
		for (int i = 0; i < M; i++) {
			a[i] = sc.nextInt();
			b[i] = sc.nextInt();
		}
		out.println(solve(N, a, b));

		out.close();
		sc.close();
	}

	static String solve(int N, int[] a, int[] b) {
		int M = a.length;

		@SuppressWarnings("unchecked")
		List<Edge>[] adjLists = new List[N + 1];
		for (int i = 1; i < adjLists.length; i++) {
			adjLists[i] = new ArrayList<>();
		}

		Edge[] edges = new Edge[M];
		for (int i = 0; i < M; i++) {
			edges[i] = new Edge(i, a[i], b[i]);

			adjLists[a[i]].add(edges[i]);
			adjLists[b[i]].add(edges[i]);
		}

		int[] roots = IntStream.rangeClosed(1, N).filter(i -> adjLists[i].size() == 1).toArray();
		if (N == 1 || (N >= 3 && roots.length >= 2)) {
			return "IMPOSSIBLE";
		}

		int root = (roots.length == 0) ? 1 : roots[0];
		spell = 1;
		search(adjLists, new boolean[N + 1], root);

		int[] result = new int[M];
		for (Edge edge : edges) {
			result[edge.index] = edge.spell;
		}

		return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining("\n"));
	}

	static void search(List<Edge>[] adjLists, boolean[] visited, int node) {
		visited[node] = true;

		for (Edge edge : adjLists[node]) {
			if (edge.spell == 0) {
				edge.spell = spell;
				spell++;
			}

			int adj = (edge.node1 == node) ? edge.node2 : edge.node1;
			if (!visited[adj]) {
				search(adjLists, visited, adj);
			}
		}
	}
}

class Edge {
	int index;
	int node1;
	int node2;
	int spell;

	Edge(int index, int node1, int node2) {
		this.index = index;
		this.node1 = node1;
		this.node2 = node2;
	}
}