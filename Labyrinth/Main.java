import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static final int[] R_OFFSETS = { -1, 0, 1, 0 };
	static final int[] C_OFFSETS = { 0, 1, 0, -1 };

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int W = sc.nextInt();
		int H = sc.nextInt();
		char[][] cells = new char[H][W];
		for (int r = 0; r < H; r++) {
			String line = sc.next();
			for (int c = 0; c < W; c++) {
				cells[r][c] = line.charAt(c);
			}
		}
		out.println(solve(cells));

		out.close();
		sc.close();
	}

	static int solve(char[][] cells) {
		int H = cells.length;
		int W = cells[0].length;

		int[][] distances = new int[H][W];
		for (int r = 0; r < H; r++) {
			Arrays.fill(distances[r], -1);
		}
		distances[0][0] = 0;

		Queue<Point> queue = new LinkedList<>();
		queue.offer(new Point(0, 0));

		while (true) {
			Point head = queue.poll();

			for (int i = 0; i < R_OFFSETS.length; i++) {
				int nextR = head.r;
				int nextC = head.c;

				while (nextR + R_OFFSETS[i] >= 0 && nextR + R_OFFSETS[i] < H && nextC + C_OFFSETS[i] >= 0
						&& nextC + C_OFFSETS[i] < W && cells[nextR + R_OFFSETS[i]][nextC + C_OFFSETS[i]] != '1') {
					nextR += R_OFFSETS[i];
					nextC += C_OFFSETS[i];

					if (cells[nextR][nextC] == '2') {
						return distances[head.r][head.c] + 1;
					}
				}

				if (distances[nextR][nextC] == -1) {
					distances[nextR][nextC] = distances[head.r][head.c] + 1;
					queue.offer(new Point(nextR, nextC));
				}
			}
		}
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