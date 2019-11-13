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
		int[] a = new int[N];
		for (int i = 0; i < a.length; i++) {
			a[i] = sc.nextInt();
		}
		out.println(solve(a, M) ? "Yes" : "No");

		out.close();
		sc.close();
	}

	static boolean solve(int[] a, int M) {
		return check(a, M, 0, a.length - 1);
	}

	static boolean check(int[] a, int M, int beginIndex, int endIndex) {
		if (beginIndex == endIndex) {
			return true;
		}

		int middleIndex = (beginIndex + endIndex) / 2;

		if (!check(a, M, beginIndex, middleIndex) || !check(a, M, middleIndex + 1, endIndex)) {
			return false;
		}

		List<Integer> merged = new ArrayList<>();
		int leftIndex = beginIndex;
		int rightIndex = middleIndex + 1;
		while (leftIndex != middleIndex + 1 || rightIndex != endIndex + 1) {
			if (rightIndex == endIndex + 1 || (leftIndex != middleIndex + 1 && a[leftIndex] < a[rightIndex])) {
				merged.add(a[leftIndex]);
				leftIndex++;
			} else {
				if (a[rightIndex] < a[middleIndex] && a[middleIndex] + a[rightIndex] > M) {
					return false;
				}

				merged.add(a[rightIndex]);
				rightIndex++;
			}
		}

		for (int i = beginIndex; i <= endIndex; i++) {
			a[i] = merged.get(i - beginIndex);
		}

		return true;
	}
}
