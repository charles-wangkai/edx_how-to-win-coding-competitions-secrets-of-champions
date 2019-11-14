import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import mooc.EdxIO;

public class Main {
	public static void main(String[] args) {
		try (EdxIO io = EdxIO.create()) {
			int n = io.nextInt();
			int[][] edges = new int[n - 1][2];
			for (int i = 0; i < edges.length; i++) {
				edges[i][0] = io.nextInt() - 1;
				edges[i][1] = io.nextInt() - 1;
			}

			io.println(solve(edges));
		}
	}

	static int solve(int[][] edges) {
		int n = edges.length + 1;

		int[] parents = buildParents(edges);

		int[] remains = new int[n];
		for (int parent : parents) {
			if (parent != -1) {
				remains[parent]++;
			}
		}

		int[] subtreeLeafNums = new int[n];
		int leafCount = 0;
		Queue<Integer> queue = new ArrayDeque<>();
		for (int i = 0; i < remains.length; i++) {
			if (remains[i] == 0) {
				subtreeLeafNums[i] = 1;
				leafCount++;
				queue.offer(i);
			}
		}

		while (!queue.isEmpty()) {
			int head = queue.poll();

			if (parents[head] != -1) {
				subtreeLeafNums[parents[head]] += subtreeLeafNums[head];

				remains[parents[head]]--;
				if (remains[parents[head]] == 0) {
					queue.offer(parents[head]);
				}
			}
		}

		int result = 0;
		for (int subtreeLeafNum : subtreeLeafNums) {
			result = Math.max(result, Math.min(subtreeLeafNum, leafCount - subtreeLeafNum));
		}

		return result;
	}

	static int[] buildParents(int[][] edges) {
		int n = edges.length + 1;

		int[] degrees = new int[n];
		for (int[] edge : edges) {
			degrees[edge[0]]++;
			degrees[edge[1]]++;
		}

		int[][] adjLists = new int[n][];
		for (int i = 0; i < adjLists.length; i++) {
			adjLists[i] = new int[degrees[i]];
		}

		for (int[] edge : edges) {
			degrees[edge[0]]--;
			adjLists[edge[0]][degrees[edge[0]]] = edge[1];

			degrees[edge[1]]--;
			adjLists[edge[1]][degrees[edge[1]]] = edge[0];
		}

		int[] parents = new int[n];
		Arrays.fill(parents, -1);

		boolean[] used = new boolean[n];

		Queue<Integer> queue = new ArrayDeque<>();
		queue.offer(0);

		while (!queue.isEmpty()) {
			int head = queue.poll();
			if (used[head]) {
				continue;
			}

			used[head] = true;

			for (int adj : adjLists[head]) {
				if (!used[adj]) {
					parents[adj] = head;
					queue.offer(adj);
				}
			}
		}

		return parents;
	}
}
