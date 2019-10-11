import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Stack;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		char[] elements = new char[N];
		for (int i = 0; i < elements.length; i++) {
			elements[i] = sc.next().charAt(0);
		}
		out.println(solve(elements));

		out.close();
		sc.close();
	}

	static int solve(char[] elements) {
		Stack<Integer> stack = new Stack<>();
		for (char element : elements) {
			if (Character.isDigit(element)) {
				stack.push(element - '0');
			} else {
				int operand2 = stack.pop();
				int operand1 = stack.pop();

				if (element == '+') {
					stack.push(operand1 + operand2);
				} else if (element == '-') {
					stack.push(operand1 - operand2);
				} else {
					stack.push(operand1 * operand2);
				}
			}
		}

		return stack.peek();
	}
}
