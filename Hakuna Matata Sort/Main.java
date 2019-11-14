import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		int[] M = new int[N];
		for (int i = 0; i < M.length; i++) {
			M[i] = sc.nextInt();
		}
		out.println(solve(M));

		out.close();
		sc.close();
	}

	static String solve(int[] M) {
		int N = M.length;

		int indices[] = new int[N + 1];
		for (int i = 0; i < M.length; i++) {
			indices[M[i]] = i;
		}

		List<String> operations = new ArrayList<>();
		for (int i = 0; i < M.length; i++) {
			while (M[i] != i + 1) {
				operations.add(String.format("%d %d", indices[M[i]] + 1, indices[M[i] - 1] + 1));

				swap(M, indices, M[i], M[i] - 1);
			}
		}

		return String.format("%d\n%s", operations.size(),
				operations.stream().map(String::valueOf).collect(Collectors.joining("\n")));
	}

	static void swap(int[] M, int[] indices, int value1, int value2) {
		swapInArray(M, indices[value1], indices[value2]);
		swapInArray(indices, value1, value2);
	}

	static void swapInArray(int[] a, int index1, int index2) {
		int temp = a[index1];
		a[index1] = a[index2];
		a[index2] = temp;
	}
}
