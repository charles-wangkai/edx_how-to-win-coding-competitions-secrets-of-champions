import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
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
		@SuppressWarnings("unchecked")
		List<Integer>[] adjLists = new List[N];
		for (int i = 0; i < adjLists.length; i++) {
			adjLists[i] = new ArrayList<>();
		}

		for (int i = 0; i < S.length; i++) {
			adjLists[S[i] - 1].add(T[i] - 1);
		}

		List<Integer> sortedIndices = topologicalSort(adjLists);

		List<Integer>[] reversedAdjLists = reverseGraph(adjLists);

		int K = 0;
		int[] C = new int[N];
		for (int index : sortedIndices) {
			if (C[index] == 0) {
				K++;
				dfs2(reversedAdjLists, C, K, index);
			}
		}

		return String.format("%d\n%s", K, Arrays.stream(C).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
	}

	static void dfs2(List<Integer>[] reversedAdjLists, int[] C, int K, int node) {
		C[node] = K;

		for (int adj : reversedAdjLists[node]) {
			if (C[adj] == 0) {
				dfs2(reversedAdjLists, C, K, adj);
			}
		}
	}

	static List<Integer>[] reverseGraph(List<Integer>[] adjLists) {
		@SuppressWarnings("unchecked")
		List<Integer>[] reversedAdjLists = new List[adjLists.length];
		for (int i = 0; i < reversedAdjLists.length; i++) {
			reversedAdjLists[i] = new ArrayList<>();
		}

		for (int i = 0; i < adjLists.length; i++) {
			for (int adj : adjLists[i]) {
				reversedAdjLists[adj].add(i);
			}
		}

		return reversedAdjLists;
	}

	static List<Integer> topologicalSort(List<Integer>[] adjLists) {
		List<Integer> sortedIndices = new ArrayList<>();
		boolean[] visited = new boolean[adjLists.length];
		for (int i = 0; i < visited.length; i++) {
			if (!visited[i]) {
				dfs1(adjLists, sortedIndices, visited, i);
			}
		}

		Collections.reverse(sortedIndices);

		return sortedIndices;
	}

	static void dfs1(List<Integer>[] adjLists, List<Integer> sortedIndices, boolean[] visited, int node) {
		visited[node] = true;

		for (int adj : adjLists[node]) {
			if (!visited[adj]) {
				dfs1(adjLists, sortedIndices, visited, adj);
			}
		}

		sortedIndices.add(node);
	}
}
