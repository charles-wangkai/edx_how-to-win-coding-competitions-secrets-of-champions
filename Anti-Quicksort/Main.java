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
		out.println(solve(n));

		out.close();
		sc.close();
	}

	static String solve(int n) {
		int[] originalIndices = IntStream.range(0, n).toArray();
		for (int endIndex = n - 1; endIndex >= 2; endIndex--) {
			int middleIndex = endIndex / 2;

			int temp = originalIndices[endIndex];
			originalIndices[endIndex] = originalIndices[middleIndex];
			originalIndices[middleIndex] = temp;
		}

		int[] result = new int[n];
		for (int i = 0; i < originalIndices.length; i++) {
			result[originalIndices[i]] = i + 1;
		}

		return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
	}
}
