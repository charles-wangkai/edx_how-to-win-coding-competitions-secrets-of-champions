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

		int W = sc.nextInt();
		int H = sc.nextInt();
		char[][] keyboard = new char[H][W];
		for (int r = 0; r < H; r++) {
			String line = sc.next();
			for (int c = 0; c < W; c++) {
				keyboard[r][c] = line.charAt(c);
			}
		}
		Template[] templates = new Template[3];
		for (int i = 0; i < templates.length; i++) {
			String language = sc.next();

			StringBuilder code = new StringBuilder();
			sc.next();
			sc.nextLine();
			while (true) {
				String line = sc.nextLine();
				if (line.equals("%TEMPLATE-END%")) {
					break;
				}

				code.append(line.replaceAll(" ", ""));
			}

			templates[i] = new Template(language, code.toString());
		}
		out.println(solve(keyboard, templates));

		out.close();
		sc.close();
	}

	static String solve(char[][] keyboard, Template[] templates) {
		Map<Character, Point> letterToPoint = new HashMap<>();
		for (int r = 0; r < keyboard.length; r++) {
			for (int c = 0; c < keyboard[0].length; c++) {
				letterToPoint.put(keyboard[r][c], new Point(r, c));
			}
		}

		int minTime = Integer.MAX_VALUE;
		String languageWithMinTime = null;
		for (Template template : templates) {
			int time = IntStream.range(0, template.code.length() - 1)
					.map(i -> computeDistance(letterToPoint.get(template.code.charAt(i)),
							letterToPoint.get(template.code.charAt(i + 1))))
					.sum();

			if (time < minTime) {
				minTime = time;
				languageWithMinTime = template.language;
			}
		}

		return String.format("%s\n%d", languageWithMinTime, minTime);
	}

	static int computeDistance(Point p1, Point p2) {
		return Math.max(Math.abs(p1.r - p2.r), Math.abs(p1.c - p2.c));
	}
}

class Template {
	String language;
	String code;

	Template(String language, String code) {
		this.language = language;
		this.code = code;
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