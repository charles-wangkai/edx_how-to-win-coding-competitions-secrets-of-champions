import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int n = sc.nextInt();
		int[] a = new int[n];
		int[] b = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = sc.nextInt();
			b[i] = sc.nextInt();
		}
		out.println(solve(a, b));

		out.close();
		sc.close();
	}

	static int solve(int[] a, int[] b) {
		int[] indices = IntStream.range(0, a.length).boxed()
				.sorted((i1, i2) -> (a[i1] != a[i2]) ? Integer.compare(a[i1], a[i2]) : Integer.compare(b[i1], b[i2]))
				.mapToInt(x -> x).toArray();

		Map<Integer, Integer> valueToMaxLength = new HashMap<>();
		for (int index : indices) {
			valueToMaxLength.put(b[index], Math.max(valueToMaxLength.getOrDefault(b[index], 0),
					valueToMaxLength.getOrDefault(a[index], 0) + 1));
		}

		return valueToMaxLength.values().stream().mapToInt(x -> x).max().getAsInt();
	}
}
