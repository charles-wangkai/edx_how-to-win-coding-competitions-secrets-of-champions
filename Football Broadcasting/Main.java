import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
	static final int[] X_OFFSETS = { -1, 0, 1, 0 };
	static final int[] Y_OFFSETS = { 0, 1, 0, -1 };

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int W = sc.nextInt();
		int H = sc.nextInt();
		int N = sc.nextInt();
		int[] x1 = new int[N];
		int[] y1 = new int[N];
		int[] x2 = new int[N];
		int[] y2 = new int[N];
		for (int i = 0; i < N; i++) {
			x1[i] = sc.nextInt();
			y1[i] = sc.nextInt();
			x2[i] = sc.nextInt();
			y2[i] = sc.nextInt();
		}
		out.println(solve(W, H, x1, y1, x2, y2));

		out.close();
		sc.close();
	}

	static String solve(int W, int H, int[] x1, int[] y1, int[] x2, int[] y2) {
		int N = x1.length;

		boolean[][][] p = new boolean[N + 1][W + 1][H + 1];
		for (int x = 1; x <= W; x++) {
			for (int y = 1; y <= H; y++) {
				p[0][x][y] = true;
			}
		}

		for (int i = 1; i <= N; i++) {
			for (int x = 1; x <= W; x++) {
				for (int y = 1; y <= H; y++) {
					if (!(x >= x1[i - 1] && x <= x2[i - 1] && y >= y1[i - 1] && y <= y2[i - 1])) {
						for (int j = 0; j < X_OFFSETS.length; j++) {
							int prevX = x + X_OFFSETS[j];
							int prevY = y + Y_OFFSETS[j];

							if (prevX >= 1 && prevX <= W && prevY >= 1 && prevY <= H && p[i - 1][prevX][prevY]) {
								p[i][x][y] = true;
							}
						}
					}
				}
			}
		}

		Point[] result = new Point[N];
		result[result.length - 1] = find(p[N]);
		if (result[result.length - 1] == null) {
			return "Impossible";
		}

		for (int i = result.length - 2; i >= 0; i--) {
			for (int j = 0;; j++) {
				int x = result[i + 1].x + X_OFFSETS[j];
				int y = result[i + 1].y + Y_OFFSETS[j];

				if (x >= 1 && x <= W && x >= 1 && y <= H && p[i + 1][x][y]) {
					result[i] = new Point(x, y);

					break;
				}
			}
		}

		return Arrays.stream(result).map(point -> String.format("%d %d", point.x, point.y))
				.collect(Collectors.joining("\n"));
	}

	static Point find(boolean[][] cells) {
		for (int x = 1; x < cells.length; x++) {
			for (int y = 1; y < cells[x].length; y++) {
				if (cells[x][y]) {
					return new Point(x, y);
				}
			}
		}

		return null;
	}
}

class Point {
	int x;
	int y;

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}