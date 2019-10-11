import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Stack;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		for (int tc = 0; tc < N; tc++) {
			String sequence = sc.next();

			out.println(solve(sequence) ? "YES" : "NO");
		}

		out.close();
		sc.close();
	}

	static boolean solve(String sequence) {
		Stack<Character> stack = new Stack<>();
		for (char ch : sequence.toCharArray()) {
			if (ch == '(' || ch == '[') {
				stack.push(ch);
			} else {
				if (stack.empty() || (ch == ')' && stack.peek() != '(') || (ch == ']' && stack.peek() != '[')) {
					return false;
				}

				stack.pop();
			}
		}

		return stack.empty();
	}
}
