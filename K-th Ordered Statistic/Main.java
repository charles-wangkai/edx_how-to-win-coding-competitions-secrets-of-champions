import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
	static Random random = new Random();

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int n = sc.nextInt();
		int k1 = sc.nextInt();
		int k2 = sc.nextInt();
		int A = sc.nextInt();
		int B = sc.nextInt();
		int C = sc.nextInt();
		int a1 = sc.nextInt();
		int a2 = sc.nextInt();
		out.println(solve(n, k1, k2, A, B, C, a1, a2));

		out.close();
		sc.close();
	}

	static String solve(int n, int k1, int k2, int A, int B, int C, int a1, int a2) {
		int[] a = new int[n];
		a[0] = a1;
		a[1] = a2;
		for (int i = 2; i < a.length; i++) {
			a[i] = A * a[i - 2] + B * a[i - 1] + C;
		}

		int value1 = findValueAt(a, 0, a.length - 1, k1 - 1);
		int value2 = findValueAt(a, 0, a.length - 1, k2 - 1);

		int countLessThanValue1 = 0;
		int countValue1 = 0;
		List<Integer> result = new ArrayList<>();
		for (int ai : a) {
			if (ai < value1) {
				countLessThanValue1++;
			} else if (ai == value1) {
				countValue1++;
			} else if (ai < value2) {
				result.add(ai);
			}
		}

		int countAfter1 = countValue1 - (k1 - 1 - countLessThanValue1);
		for (int i = 0; i < countAfter1 && result.size() != k2 - k1 + 1; i++) {
			result.add(value1);
		}
		while (result.size() != k2 - k1 + 1) {
			result.add(value2);
		}

		return result.stream().sorted().map(String::valueOf).collect(Collectors.joining(" "));
	}

	static int findValueAt(int[] a, int beginIndex, int endIndex, int targetIndex) {
		swap(a, beginIndex, beginIndex + random.nextInt(endIndex - beginIndex + 1));

		int leftIndex = beginIndex;
		int rightIndex = endIndex + 1;
		while (rightIndex - leftIndex > 1) {
			while (leftIndex + 1 <= endIndex && a[leftIndex + 1] <= a[beginIndex]) {
				leftIndex++;
			}
			while (rightIndex - 1 >= beginIndex + 1 && a[rightIndex - 1] > a[beginIndex]) {
				rightIndex--;
			}

			if (rightIndex - leftIndex > 1) {
				leftIndex++;
				rightIndex--;
				swap(a, leftIndex, rightIndex);
			}
		}

		swap(a, beginIndex, leftIndex);

		int pivotIndex = leftIndex;
		int middleIndex = (beginIndex + endIndex) / 2;
		while (pivotIndex < middleIndex && a[pivotIndex + 1] == a[pivotIndex]) {
			pivotIndex++;
		}
		while (pivotIndex > middleIndex && a[pivotIndex - 1] == a[pivotIndex]) {
			pivotIndex--;
		}

		if (targetIndex < pivotIndex) {
			return findValueAt(a, beginIndex, pivotIndex - 1, targetIndex);
		} else if (targetIndex > pivotIndex) {
			return findValueAt(a, pivotIndex + 1, endIndex, targetIndex);
		} else {
			return a[pivotIndex];
		}
	}

	static void swap(int[] a, int index1, int index2) {
		int temp = a[index1];
		a[index1] = a[index2];
		a[index2] = temp;
	}
}
