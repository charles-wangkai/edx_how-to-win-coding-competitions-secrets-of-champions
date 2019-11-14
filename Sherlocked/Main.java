import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
	static final int[] R_OFFSETS = { -1, 0, 1, 0 };
	static final int[] C_OFFSETS = { 0, 1, 0, -1 };

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		int M = sc.nextInt();
		int[][] A = new int[N][M];
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < M; c++) {
				A[r][c] = sc.nextInt();
			}
		}
		out.println(solve(A));

		out.close();
		sc.close();
	}

	static int solve(int[][] A) {
		int N = A.length;
		int M = A[0].length;

		int[][] lengths = new int[N][M];
		for (int r = 0; r < lengths.length; r++) {
			Arrays.fill(lengths[r], -1);
		}

		List<Point> points = IntStream.range(0, N).boxed()
				.flatMap(r -> IntStream.range(0, M).mapToObj(c -> new Point(r, c)))
				.sorted((p1, p2) -> Integer.compare(A[p1.r][p1.c], A[p2.r][p2.c])).collect(Collectors.toList());
		for (Point point : points) {
			lengths[point.r][point.c] = 1;

			for (int i = 0; i < R_OFFSETS.length; i++) {
				int adjR = point.r + R_OFFSETS[i];
				int adjC = point.c + C_OFFSETS[i];

				if (adjR >= 0 && adjR < N && adjC >= 0 && adjC < M && A[point.r][point.c] == A[adjR][adjC] + 1) {
					lengths[point.r][point.c] = Math.max(lengths[point.r][point.c], lengths[adjR][adjC] + 1);
				}
			}
		}

		return Arrays.stream(lengths).mapToInt(row -> Arrays.stream(row).max().getAsInt()).max().getAsInt();
	}
}

class Point {
	int r;
	int c;

	Point(int r, int c) {
		this.r = r;
		this.c = c;
	}
}