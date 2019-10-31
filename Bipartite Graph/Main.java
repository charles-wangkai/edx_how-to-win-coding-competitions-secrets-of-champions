import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		int M = sc.nextInt();
		int[] nodes1 = new int[M];
		int[] nodes2 = new int[M];
		for (int i = 0; i < M; i++) {
			nodes1[i] = sc.nextInt();
			nodes2[i] = sc.nextInt();
		}
		out.println(solve(N, nodes1, nodes2) ? "YES" : "NO");

		out.close();
		sc.close();
	}

	static boolean solve(int N, int[] nodes1, int[] nodes2) {
		@SuppressWarnings("unchecked")
		List<Integer>[] adjLists = new List[N + 1];
		for (int i = 1; i < adjLists.length; i++) {
			adjLists[i] = new ArrayList<>();
		}

		for (int i = 0; i < nodes1.length; i++) {
			adjLists[nodes1[i]].add(nodes2[i]);
			adjLists[nodes2[i]].add(nodes1[i]);
		}

		int[] colors = new int[N + 1];
		for (int i = 1; i < colors.length; i++) {
			if (colors[i] == 0 && !search(adjLists, colors, i, 1)) {
				return false;
			}
		}

		return true;
	}

	static boolean search(List<Integer>[] adjLists, int[] colors, int node, int color) {
		if (colors[node] != 0) {
			return colors[node] == color;
		}

		colors[node] = color;

		for (int adj : adjLists[node]) {
			if (!search(adjLists, colors, adj, -color)) {
				return false;
			}
		}

		return true;
	}
}
