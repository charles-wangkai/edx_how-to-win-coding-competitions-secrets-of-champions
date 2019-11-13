import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		int M = sc.nextInt();
		int S = sc.nextInt();
		int T = sc.nextInt();
		Edge[] edges = new Edge[M];
		for (int i = 0; i < edges.length; i++) {
			int vertex1 = sc.nextInt();
			int vertex2 = sc.nextInt();
			int cost = sc.nextInt();

			edges[i] = new Edge(vertex1, vertex2, cost);
		}
		int A = sc.nextInt();
		int B = sc.nextInt();
		out.println(solve(N, edges, S, T, A, B));

		out.close();
		sc.close();
	}

	static long solve(int N, Edge[] edges, int S, int T, int A, int B) {
		@SuppressWarnings("unchecked")
		List<Edge>[] adjLists = new List[N + 1];
		for (int i = 1; i < adjLists.length; i++) {
			adjLists[i] = new ArrayList<>();
		}

		for (Edge edge : edges) {
			if (edge.vertex1 != edge.vertex2) {
				adjLists[edge.vertex1].add(edge);
				adjLists[edge.vertex2].add(edge);
			}
		}

		long[] distances1 = computeDistances(adjLists, S, edge -> edge.cost <= A);
		long[] distances2 = computeDistances(adjLists, T, edge -> edge.cost >= B);

		long result = -1;
		for (int i = 1; i <= N; i++) {
			if (distances1[i] != -1 && distances2[i] != -1
					&& (result == -1 || distances1[i] + distances2[i] < result)) {
				result = distances1[i] + distances2[i];
			}
		}

		return result;
	}

	static long[] computeDistances(List<Edge>[] adjLists, int source, EdgeChecker edgeChecker) {
		long[] distances = new long[adjLists.length];
		Arrays.fill(distances, -1);

		PriorityQueue<Element> pq = new PriorityQueue<>((e1, e2) -> Long.compare(e1.distance, e2.distance));
		pq.offer(new Element(source, 0));

		while (!pq.isEmpty()) {
			Element head = pq.poll();
			if (distances[head.vertex] != -1) {
				continue;
			}

			distances[head.vertex] = head.distance;

			for (Edge edge : adjLists[head.vertex]) {
				if (edgeChecker.check(edge)) {
					int adj = (head.vertex == edge.vertex1) ? edge.vertex2 : edge.vertex1;

					if (distances[adj] == -1) {
						pq.offer(new Element(adj, head.distance + edge.cost));
					}
				}
			}
		}

		return distances;
	}
}

class Element {
	int vertex;
	long distance;

	Element(int vertex, long distance) {
		this.vertex = vertex;
		this.distance = distance;
	}
}

class Edge {
	int vertex1;
	int vertex2;
	int cost;

	Edge(int vertex1, int vertex2, int cost) {
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.cost = cost;
	}
}

interface EdgeChecker {
	boolean check(Edge edge);
}
