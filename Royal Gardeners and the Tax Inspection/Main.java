import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int B = sc.nextInt();
		int N = sc.nextInt();
		int[] D = new int[N];
		int[] L = new int[N];
		int[] R = new int[N];
		for (int i = 0; i < N; i++) {
			D[i] = sc.nextInt();
			L[i] = sc.nextInt();
			R[i] = sc.nextInt();
		}
		int M = sc.nextInt();
		int[] X = new int[M];
		int[] Y = new int[M];
		for (int i = 0; i < M; i++) {
			X[i] = sc.nextInt();
			Y[i] = sc.nextInt();
		}
		out.println(solve(B, D, L, R, X, Y));

		out.close();
		sc.close();
	}

	static String solve(int B, int[] D, int[] L, int[] R, int[] X, int[] Y) {
		SortedMap<Integer, Integer> posToDelta = new TreeMap<>();
		for (int i = 0; i < D.length; i++) {
			updateMap(posToDelta, L[i], D[i]);
			updateMap(posToDelta, R[i] + 1, -D[i]);
		}

		List<Range> ranges = new ArrayList<>();
		int leftPos = 1;
		int unit = 0;
		for (int pos : posToDelta.keySet()) {
			int delta = posToDelta.get(pos);

			if (pos != leftPos) {
				ranges.add(new Range(leftPos, pos - 1, unit));

				leftPos = pos;
			}

			unit += delta;
		}
		if (leftPos <= B) {
			ranges.add(new Range(leftPos, B, unit));
		}

		long[] beforeSums = new long[ranges.size()];
		long beforeSum = 0;
		for (int i = 0; i < beforeSums.length; i++) {
			beforeSums[i] = beforeSum;

			Range range = ranges.get(i);
			beforeSum += (range.rightPos - range.leftPos + 1L) * range.unit;
		}

		return IntStream.range(0, X.length)
				.mapToObj(i -> String.valueOf(
						computePrefixSum(ranges, beforeSums, Y[i]) - computePrefixSum(ranges, beforeSums, X[i] - 1)))
				.collect(Collectors.joining("\n"));
	}

	static void updateMap(SortedMap<Integer, Integer> posToDelta, int pos, int offset) {
		posToDelta.put(pos, posToDelta.getOrDefault(pos, 0) + offset);
	}

	static long computePrefixSum(List<Range> ranges, long[] beforeSums, int pos) {
		if (pos == 0) {
			return 0;
		}

		int index = find(ranges, pos);
		Range range = ranges.get(index);

		return beforeSums[index] + (pos - range.leftPos + 1L) * range.unit;
	}

	static int find(List<Range> ranges, int pos) {
		int lowerIndex = 0;
		int upperIndex = ranges.size() - 1;
		while (true) {
			int middleIndex = (lowerIndex + upperIndex) / 2;
			Range range = ranges.get(middleIndex);

			if (pos < range.leftPos) {
				upperIndex = middleIndex - 1;
			} else if (pos > range.rightPos) {
				lowerIndex = middleIndex + 1;
			} else {
				return middleIndex;
			}
		}
	}
}

class Range {
	int leftPos;
	int rightPos;
	int unit;

	Range(int leftPos, int rightPos, int unit) {
		this.leftPos = leftPos;
		this.rightPos = rightPos;
		this.unit = unit;
	}
}