import java.util.Arrays;
import java.util.stream.Collectors;

import mooc.EdxIO;

public class Main {
	static int[] sorted = new int[200005];

	public static void main(String[] args) {
		try (EdxIO io = EdxIO.create()) {
			int n = io.nextInt();
			int[] a = new int[n];
			for (int i = 0; i < a.length; i++) {
				a[i] = io.nextInt();
			}

			solve(io, a);
		}
	}

	static void solve(EdxIO io, int[] a) {
		mergeSort(io, a, 0, a.length - 1);

		io.println(String.format("%s", Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(" "))));
	}

	static void mergeSort(EdxIO io, int[] a, int beginIndex, int endIndex) {
		if (beginIndex == endIndex) {
			return;
		}

		int middleIndex = (beginIndex + endIndex) / 2;

		mergeSort(io, a, beginIndex, middleIndex);
		mergeSort(io, a, middleIndex + 1, endIndex);

		int leftIndex = beginIndex;
		int rightIndex = middleIndex + 1;
		int sortedIndex = beginIndex;
		while (!(leftIndex == middleIndex + 1 && rightIndex == endIndex + 1)) {
			if (rightIndex == endIndex + 1 || (leftIndex != middleIndex + 1 && a[leftIndex] <= a[rightIndex])) {
				sorted[sortedIndex] = a[leftIndex];
				sortedIndex++;

				leftIndex++;
			} else {
				sorted[sortedIndex] = a[rightIndex];
				sortedIndex++;

				rightIndex++;
			}
		}

		for (int i = beginIndex; i <= endIndex; i++) {
			a[i] = sorted[i];
		}

		io.println(String.format("%d %d %d %d", beginIndex + 1, endIndex + 1, a[beginIndex], a[endIndex]));
	}
}
