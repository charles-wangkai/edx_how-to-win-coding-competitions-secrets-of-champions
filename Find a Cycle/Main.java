import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
	static List<Integer> cycle;
	static int cycleHead;

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		int M = sc.nextInt();
		int[] froms = new int[M];
		int[] tos = new int[M];
		for (int i = 0; i < M; i++) {
			froms[i] = sc.nextInt();
			tos[i] = sc.nextInt();
		}
		out.println(solve(N, froms, tos));

		out.close();
		sc.close();
	}

	static String solve(int N, int[] froms, int[] tos) {
		@SuppressWarnings("unchecked")
		List<Integer>[] adjLists = new List[N + 1];
		for (int i = 1; i < adjLists.length; i++) {
			adjLists[i] = new ArrayList<>();
		}

		for (int i = 0; i < froms.length; i++) {
			adjLists[froms[i]].add(tos[i]);
		}

		cycle = null;
		boolean[] ins = new boolean[N + 1];
		boolean[] outs = new boolean[N + 1];
		for (int i = 1; i <= N; i++) {
			if (!ins[i]) {
				search(adjLists, ins, outs, i);
			}

			if (cycle != null) {
				break;
			}
		}

		if (cycle == null) {
			return "NO";
		} else {
			cycle.remove(cycle.size() - 1);
			Collections.reverse(cycle);

			return String.format("YES\n%s", cycle.stream().map(String::valueOf).collect(Collectors.joining(" ")));
		}
	}

	static void search(List<Integer>[] adjLists, boolean[] ins, boolean[] outs, int node) {
		ins[node] = true;

		for (int adj : adjLists[node]) {
			if (!ins[adj]) {
				search(adjLists, ins, outs, adj);
			} else if (!outs[adj]) {
				cycle = new ArrayList<>();
				cycleHead = adj;
			}

			if (cycle != null) {
				break;
			}
		}

		if (cycle != null && (cycle.isEmpty() || cycle.get(cycle.size() - 1) != null)) {
			cycle.add(node);

			if (node == cycleHead) {
				cycle.add(null);
			}
		}

		outs[node] = true;
	}
}
