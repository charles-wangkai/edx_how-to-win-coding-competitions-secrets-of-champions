import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
	static final int[] R_OFFSETS = { -1, 0, 1, 0 };
	static final int[] C_OFFSETS = { 0, 1, 0, -1 };

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int n = sc.nextInt();
		char[][] cells = new char[n][n];
		for (int r = 0; r < n; r++) {
			String line = sc.next();
			for (int c = 0; c < n; c++) {
				cells[r][c] = line.charAt(c);
			}
		}
		out.println(solve(cells));

		out.close();
		sc.close();
	}

	static String solve(char[][] cells) {
		int n = cells.length;

		Point start = find(cells, 'A');

		int[][] prevs = new int[n][n];
		for (int r = 0; r < n; r++) {
			Arrays.fill(prevs[r], -1);
		}
		prevs[start.r][start.c] = -2;

		Queue<Point> queue = new LinkedList<>();
		queue.offer(start);

		while (true) {
			Point head = queue.poll();

			if (cells[head.r][head.c] == 'B') {
				int r = head.r;
				int c = head.c;
				while (r != start.r || c != start.c) {
					if (cells[r][c] == 'N') {
						cells[r][c] = 'P';
					}

					int nextR = r + R_OFFSETS[prevs[r][c]];
					int nextC = c + C_OFFSETS[prevs[r][c]];

					r = nextR;
					c = nextC;
				}

				return Arrays.stream(cells).map(String::new).collect(Collectors.joining("\n"));
			}

			for (int i = 0; i < R_OFFSETS.length; i++) {
				int adjR = head.r + R_OFFSETS[i];
				int adjC = head.c + C_OFFSETS[i];

				if (adjR >= 0 && adjR < n && adjC >= 0 && adjC < n && cells[adjR][adjC] != 'W'
						&& prevs[adjR][adjC] == -1) {
					prevs[adjR][adjC] = (i + 2) % R_OFFSETS.length;
					queue.offer(new Point(adjR, adjC));
				}
			}
		}
	}

	static Point find(char[][] cells, char target) {
		for (int r = 0;; r++) {
			for (int c = 0; c < cells[r].length; c++) {
				if (cells[r][c] == target) {
					return new Point(r, c);
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