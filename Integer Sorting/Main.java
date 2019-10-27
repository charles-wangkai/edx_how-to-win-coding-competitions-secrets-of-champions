import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int n = sc.nextInt();
		int m = sc.nextInt();
		int[] A = readArray(sc, n);
		int[] B = readArray(sc, m);
		out.println(solve(A, B));

		out.close();
		sc.close();
	}

	static int[] readArray(Scanner sc, int size) {
		int[] result = new int[size];
		for (int i = 0; i < result.length; i++) {
			result[i] = sc.nextInt();
		}

		return result;
	}

	static long solve(int[] A, int[] B) {
		int n = A.length;
		int m = B.length;

		int[] C = new int[n * m];
		for (int i = 0; i < C.length; i++) {
			C[i] = A[i / m] * B[i % m];
		}

		sort(C);

		long result = 0;
		for (int i = 0; i < C.length; i += 10) {
			result += C[i];
		}

		return result;
	}

	static void sort(int[] C) {
		for (int x = 0; x < 4; x++) {
			int[] bucketSizes = new int[256];

			for (int ci : C) {
				int bucketIndex = (ci >> (8 * x)) & 255;

				bucketSizes[bucketIndex]++;
			}

			int[][] buckets = new int[256][];
			for (int i = 0; i < buckets.length; i++) {
				buckets[i] = new int[bucketSizes[i]];

				bucketSizes[i] = 0;
			}

			for (int ci : C) {
				int bucketIndex = (ci >> (8 * x)) & 255;

				buckets[bucketIndex][bucketSizes[bucketIndex]] = ci;
				bucketSizes[bucketIndex]++;
			}

			int index = 0;
			for (int i = 0; i < buckets.length; i++) {
				for (int j = 0; j < bucketSizes[i]; j++) {
					C[index] = buckets[i][j];
					index++;
				}
			}

			System.gc();
		}
	}
}
