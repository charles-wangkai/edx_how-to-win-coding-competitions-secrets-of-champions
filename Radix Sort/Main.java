import java.util.Arrays;

import mooc.EdxIO;

public class Main {
	public static void main(String[] args) {
		try (EdxIO io = EdxIO.create()) {
			int n = io.nextInt();
			int m = io.nextInt();
			int k = io.nextInt();

			char[] strings = new char[m * n];
			for (int i = 0; i < strings.length; i++) {
				strings[i] = io.nextChar();
			}

			solve(io, strings, n, m, k);
		}
	}

	static void solve(EdxIO io, char[] strings, int n, int m, int k) {
		int[] indices = new int[n];
		for (int i = 0; i < indices.length; i++) {
			indices[i] = i;
		}

		int[] sizes = new int[26];
		int[][] buckets = new int[26][1_000_000];
		for (int i = m - 1; i > m - 1 - k; i--) {
			Arrays.fill(sizes, 0);

			for (int index : indices) {
				int bucketIndex = strings[i * n + index] - 'a';

				buckets[bucketIndex][sizes[bucketIndex]] = index;
				sizes[bucketIndex]++;
			}

			int p = 0;
			for (int bucketIndex = 0; bucketIndex < buckets.length; bucketIndex++) {
				for (int j = 0; j < sizes[bucketIndex]; j++) {
					indices[p] = buckets[bucketIndex][j];
					p++;
				}
			}
		}

		for (int i = 0; i < indices.length; i++) {
			if (i != 0) {
				io.print(" ");
			}

			io.print(indices[i] + 1);
		}
	}
}
