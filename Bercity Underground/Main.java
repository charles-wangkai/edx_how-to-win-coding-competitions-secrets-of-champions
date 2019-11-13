import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int n = sc.nextInt();
		int m = sc.nextInt();
		int k = sc.nextInt();
		Edge[] edges = new Edge[m];
		for (int i = 0; i < edges.length; i++) {
			int vertex1 = sc.nextInt();
			int vertex2 = sc.nextInt();
			int type = sc.nextInt();

			edges[i] = new Edge(vertex1, vertex2, type);
		}
		out.println(solve(n, edges, k));

		out.close();
		sc.close();
	}

	static String solve(int n, Edge[] edges, int k) {
		@SuppressWarnings("unchecked")
		List<Edge>[] edgeLists = new List[k + 1];
		for (int i = 1; i < edgeLists.length; i++) {
			edgeLists[i] = new ArrayList<>();
		}

		for (Edge edge : edges) {
			edgeLists[edge.type].add(edge);
		}

		return IntStream.rangeClosed(1, k).mapToObj(i -> String.valueOf(computeTrainNum(edgeLists[i])))
				.collect(Collectors.joining("\n"));
	}

	static int computeTrainNum(List<Edge> edgeList) {
		Map<Integer, Node> vertexToNode = edgeList.stream().flatMap(edge -> Stream.of(edge.vertex1, edge.vertex2))
				.distinct().collect(Collectors.toMap(Function.identity(), v -> new Node()));

		for (Edge edge : edgeList) {
			vertexToNode.get(edge.vertex1).adjList.add(edge.vertex2);
			vertexToNode.get(edge.vertex2).adjList.add(edge.vertex1);
		}

		int result = 0;
		for (int vertex : vertexToNode.keySet()) {
			if (!vertexToNode.get(vertex).visited) {
				search(vertexToNode, vertex);

				result++;
			}
		}

		return result;
	}

	static void search(Map<Integer, Node> vertexToNode, int vertex) {
		Node node = vertexToNode.get(vertex);

		node.visited = true;

		for (int adj : node.adjList) {
			if (!vertexToNode.get(adj).visited) {
				search(vertexToNode, adj);
			}
		}
	}
}

class Edge {
	int vertex1;
	int vertex2;
	int type;

	Edge(int vertex1, int vertex2, int type) {
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.type = type;
	}
}

class Node {
	List<Integer> adjList = new ArrayList<>();
	boolean visited;
}