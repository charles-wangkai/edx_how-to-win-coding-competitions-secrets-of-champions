import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int n = sc.nextInt();
		int k = sc.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < a.length; i++) {
			a[i] = sc.nextInt();
		}
		out.println(solve(a, k));

		out.close();
		sc.close();
	}

	static String solve(int[] a, int k) {
		sort(a);

		int beginIndex = 0;
		int endIndex = beginIndex + k - 1;
		for (int i = 1; i + k - 1 < a.length; i++) {
			if (a[i + k - 1] - a[i] < a[endIndex] - a[beginIndex]) {
				beginIndex = i;
				endIndex = i + k - 1;
			}
		}

		return IntStream.rangeClosed(beginIndex, endIndex).mapToObj(i -> String.valueOf(a[i]))
				.collect(Collectors.joining(" "));
	}

	static void sort(int[] a) {
		int[] sorted = Arrays.stream(a).boxed().sorted().mapToInt(x -> x).toArray();

		System.arraycopy(sorted, 0, a, 0, a.length);
	}
}
