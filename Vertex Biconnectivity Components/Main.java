import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
	static int time;
	static int K;

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		int M = sc.nextInt();
		int[] S = new int[M];
		int[] T = new int[M];
		for (int i = 0; i < M; i++) {
			S[i] = sc.nextInt();
			T[i] = sc.nextInt();
		}
		out.println(solve(N, S, T));

		out.close();
		sc.close();
	}

	static String solve(int N, int[] S, int[] T) {
		int M = S.length;

		@SuppressWarnings("unchecked")
		List<Edge>[] adjLists = new List[N];
		for (int i = 0; i < adjLists.length; i++) {
			adjLists[i] = new ArrayList<>();
		}

		Edge[] edges = new Edge[M];
		for (int i = 0; i < edges.length; i++) {
			edges[i] = new Edge(i, S[i] - 1, T[i] - 1);

			adjLists[edges[i].node1].add(edges[i]);
			adjLists[edges[i].node2].add(edges[i]);
		}

		int[] ins = new int[N];
		int[] minIns = new int[N];
		int[] C = new int[M];
		time = 1;
		K = 0;
		for (int i = 0; i < ins.length; i++) {
			if (ins[i] == 0) {
				search(adjLists, ins, minIns, C, i, -1);
			}
		}

		return String.format("%d\n%s", K, Arrays.stream(C).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
	}

	static void search(List<Edge>[] adjLists, int[] ins, int[] minIns, int[] C, int node, int parent) {
		ins[node] = time;
		minIns[node] = time;
		time++;

		int childCount = 0;
		for (Edge edge : adjLists[node]) {
			int adj = (node == edge.node1) ? edge.node2 : edge.node1;

			if (adj == parent) {
				continue;
			}

			if (ins[adj] == 0) {
				childCount++;

				search(adjLists, ins, minIns, C, adj, node);

				if ((parent == -1 && childCount >= 2) || (parent != -1 && minIns[adj] >= ins[node])) {
					K++;
					C[edge.index] = K;
					fill(adjLists, ins, C, adj);
				}
			}

			minIns[node] = Math.min(minIns[node], minIns[adj]);
		}

		if (parent == -1 && adjLists[node].stream().anyMatch(edge -> C[edge.index] == 0)) {
			K++;
			fill(adjLists, ins, C, node);
		}
	}

	static void fill(List<Edge>[] adjLists, int[] ins, int[] C, int node) {
		for (Edge edge : adjLists[node]) {
			int adj = (node == edge.node1) ? edge.node2 : edge.node1;

			if (C[edge.index] == 0) {
				C[edge.index] = K;

				if (ins[adj] > ins[node]) {
					fill(adjLists, ins, C, adj);
				}
			}
		}
	}
}

class Edge {
	int index;
	int node1;
	int node2;

	Edge(int index, int node1, int node2) {
		this.index = index;
		this.node1 = node1;
		this.node2 = node2;
	}
}