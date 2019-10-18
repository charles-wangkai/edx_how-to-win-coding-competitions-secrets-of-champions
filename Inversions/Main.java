import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int n = sc.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < a.length; i++) {
			a[i] = sc.nextInt();
		}
		out.println(solve(a));

		out.close();
		sc.close();
	}

	static long solve(int[] a) {
		return computeInversionNum(a, 0, a.length - 1);
	}

	static long computeInversionNum(int[] a, int beginIndex, int endIndex) {
		if (beginIndex == endIndex) {
			return 0;
		}

		int middleIndex = (beginIndex + endIndex) / 2;

		long result = computeInversionNum(a, beginIndex, middleIndex)
				+ computeInversionNum(a, middleIndex + 1, endIndex);

		List<Integer> sorted = new ArrayList<>();
		int leftIndex = beginIndex;
		int rightIndex = middleIndex + 1;
		while (!(leftIndex == middleIndex + 1 && rightIndex == endIndex + 1)) {
			if (rightIndex == endIndex + 1 || (leftIndex != middleIndex + 1 && a[leftIndex] <= a[rightIndex])) {
				sorted.add(a[leftIndex]);
				leftIndex++;
			} else {
				sorted.add(a[rightIndex]);
				rightIndex++;

				result += middleIndex + 1 - leftIndex;
			}
		}

		for (int i = beginIndex; i <= endIndex; i++) {
			a[i] = sorted.get(i - beginIndex);
		}

		return result;
	}
}
