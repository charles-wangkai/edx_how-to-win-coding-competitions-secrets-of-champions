import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
	static int time;

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
		Set<Integer>[] adjSets = new Set[N];
		for (int i = 0; i < adjSets.length; i++) {
			adjSets[i] = new HashSet<>();
		}

		for (int i = 0; i < S.length; i++) {
			adjSets[S[i] - 1].add(T[i] - 1);
			adjSets[T[i] - 1].add(S[i] - 1);
		}

		time = 1;
		int[] ins = new int[N];
		int[] minIns = new int[N];
		for (int i = 0; i < ins.length; i++) {
			if (ins[i] == 0) {
				dfs1(adjSets, ins, minIns, i, -1);
			}
		}

		int K = 0;
		int[] C = new int[N];
		for (int i = 0; i < C.length; i++) {
			if (C[i] == 0) {
				K++;
				dfs2(adjSets, C, K, i);
			}
		}

		return String.format("%d\n%s", K, Arrays.stream(C).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
	}

	static void dfs2(Set<Integer>[] adjSets, int[] C, int K, int node) {
		C[node] = K;

		for (int adj : adjSets[node]) {
			if (C[adj] == 0) {
				dfs2(adjSets, C, K, adj);
			}
		}
	}

	static void dfs1(Set<Integer>[] adjSets, int[] ins, int[] minIns, int node, int parent) {
		ins[node] = time;
		minIns[node] = time;
		time++;

		for (int adj : new ArrayList<>(adjSets[node])) {
			if (adj == parent) {
				continue;
			}

			if (ins[adj] == 0) {
				dfs1(adjSets, ins, minIns, adj, node);
			}

			if (minIns[adj] > ins[node]) {
				adjSets[node].remove(adj);
				adjSets[adj].remove(node);
			}

			minIns[node] = Math.min(minIns[node], minIns[adj]);
		}
	}
}
