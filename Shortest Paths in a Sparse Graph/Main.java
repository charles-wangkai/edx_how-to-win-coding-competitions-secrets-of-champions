import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		int M = sc.nextInt();
		Edge[] edges = new Edge[M];
		for (int i = 0; i < edges.length; i++) {
			int vertex1 = sc.nextInt() - 1;
			int vertex2 = sc.nextInt() - 1;
			int weight = sc.nextInt();

			edges[i] = new Edge(vertex1, vertex2, weight);
		}
		out.println(solve(N, edges));

		out.close();
		sc.close();
	}

	static String solve(int N, Edge[] edges) {
		@SuppressWarnings("unchecked")
		List<Edge>[] adjLists = new List[N];
		for (int i = 0; i < adjLists.length; i++) {
			adjLists[i] = new ArrayList<>();
		}

		for (Edge edge : edges) {
			adjLists[edge.vertex1].add(edge);
			adjLists[edge.vertex2].add(edge);
		}

		int[] distances = new int[N];
		Arrays.fill(distances, Integer.MAX_VALUE);

		PriorityQueue<Element> pq = new PriorityQueue<>((e1, e2) -> Integer.compare(e1.distance, e2.distance));
		pq.offer(new Element(0, 0));

		while (!pq.isEmpty()) {
			Element head = pq.poll();
			if (distances[head.vertex] != Integer.MAX_VALUE) {
				continue;
			}

			distances[head.vertex] = head.distance;

			for (Edge edge : adjLists[head.vertex]) {
				int adj = (head.vertex == edge.vertex1) ? edge.vertex2 : edge.vertex1;
				if (distances[adj] == Integer.MAX_VALUE) {
					pq.offer(new Element(adj, distances[head.vertex] + edge.weight));
				}
			}
		}

		return Arrays.stream(distances).mapToObj(String::valueOf).collect(Collectors.joining(" "));
	}
}

class Element {
	int vertex;
	int distance;

	Element(int vertex, int distance) {
		this.vertex = vertex;
		this.distance = distance;
	}
}

class Edge {
	int vertex1;
	int vertex2;
	int weight;

	Edge(int vertex1, int vertex2, int weight) {
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.weight = weight;
	}
}