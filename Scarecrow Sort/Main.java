import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
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
		out.println(solve(a, k) ? "YES" : "NO");

		out.close();
		sc.close();
	}

	static boolean solve(int[] a, int k) {
		@SuppressWarnings("unchecked")
		List<Integer>[] valueLists = new List[k];
		for (int i = 0; i < valueLists.length; i++) {
			valueLists[i] = new ArrayList<>();
		}

		for (int i = 0; i < a.length; i++) {
			valueLists[i % k].add(a[i]);
		}

		int[] modified = new int[a.length];
		for (int i = 0; i < valueLists.length; i++) {
			Collections.sort(valueLists[i]);

			for (int j = 0; j < valueLists[i].size(); j++) {
				modified[j * k + i] = valueLists[i].get(j);
			}
		}

		return IntStream.range(0, modified.length - 1).allMatch(i -> modified[i] <= modified[i + 1]);
	}
}
