import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		int M = sc.nextInt();
		int start = sc.nextInt();
		int[] S = new int[M];
		int[] T = new int[M];
		for (int i = 0; i < M; i++) {
			S[i] = sc.nextInt();
			T[i] = sc.nextInt();
		}
		out.println(solve(N, S, T, start));

		out.close();
		sc.close();
	}

	static String solve(int N, int[] S, int[] T, int start) {
		@SuppressWarnings("unchecked")
		List<Integer>[] reversedAdjLists = new List[N];
		for (int i = 0; i < reversedAdjLists.length; i++) {
			reversedAdjLists[i] = new ArrayList<>();
		}

		for (int i = 0; i < S.length; i++) {
			reversedAdjLists[T[i] - 1].add(S[i] - 1);
		}

		List<Integer> sortedIndices = topologicalSort(reversedAdjLists);

		boolean[] firstWins = new boolean[N];
		for (int index : sortedIndices) {
			if (!firstWins[index]) {
				for (int adj : reversedAdjLists[index]) {
					firstWins[adj] = true;
				}
			}
		}

		return firstWins[start - 1] ? "Alice" : "Bob";
	}

	static List<Integer> topologicalSort(List<Integer>[] reversedAdjLists) {
		int N = reversedAdjLists.length;

		List<Integer> sortedIndices = new ArrayList<>();
		boolean[] visited = new boolean[N];
		for (int i = 0; i < visited.length; i++) {
			if (!visited[i]) {
				search(reversedAdjLists, sortedIndices, visited, i);
			}
		}

		Collections.reverse(sortedIndices);

		return sortedIndices;
	}

	static void search(List<Integer>[] reversedAdjLists, List<Integer> sortedIndices, boolean[] visited, int node) {
		visited[node] = true;

		for (int adj : reversedAdjLists[node]) {
			if (!visited[adj]) {
				search(reversedAdjLists, sortedIndices, visited, adj);
			}
		}

		sortedIndices.add(node);
	}
}
