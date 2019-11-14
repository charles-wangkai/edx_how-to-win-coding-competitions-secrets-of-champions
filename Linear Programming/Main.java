import java.util.ArrayList;
import java.util.List;

import mooc.EdxIO;

public class Main {
	public static void main(String[] args) {
		try (EdxIO io = EdxIO.create()) {
			int n = io.nextInt();
			int m = io.nextInt();
			int[][] inequalities = new int[m][2];
			for (int i = 0; i < inequalities.length; i++) {
				for (int j = 0; j < inequalities[i].length; j++) {
					String sign = io.next();
					int amount = io.nextInt();

					inequalities[i][j] = Integer.parseInt(String.format("%s%d", sign, amount));
				}
			}
			io.println(solve(n, inequalities) ? "YES" : "NO");
		}
	}

	static boolean solve(int n, int[][] inequalities) {
		@SuppressWarnings("unchecked")
		List<Integer>[] adjLists = new List[2 * n + 1];
		for (int i = 0; i < adjLists.length; i++) {
			adjLists[i] = new ArrayList<>();
		}

		for (int[] inequality : inequalities) {
			adjLists[convertToIndex(n, inequality[0])].add(convertToIndex(n, -inequality[1]));
			adjLists[convertToIndex(n, inequality[1])].add(convertToIndex(n, -inequality[0]));
		}

		return !hasCycle(adjLists);
	}

	static boolean hasCycle(List<Integer>[] adjLists) {
		boolean[] ins = new boolean[adjLists.length];
		boolean[] outs = new boolean[adjLists.length];
		for (int i = 0; i < adjLists.length; i++) {
			if (!ins[i] && search(adjLists, ins, outs, i)) {
				return true;
			}
		}

		return false;
	}

	static boolean search(List<Integer>[] adjLists, boolean[] ins, boolean[] outs, int node) {
		ins[node] = true;

		for (int adj : adjLists[node]) {
			if (!ins[adj]) {
				if (search(adjLists, ins, outs, adj)) {
					return true;
				}
			} else if (!outs[adj]) {
				return true;
			}
		}

		outs[node] = true;

		return false;
	}

	static int convertToIndex(int n, int x) {
		return x + n;
	}
}
