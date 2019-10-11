import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		int[] a = new int[N];
		for (int i = 0; i < a.length; i++) {
			a[i] = sc.nextInt();
		}
		out.println(solve(a));

		out.close();
		sc.close();
	}

	static String solve(int[] a) {
		Stack<Element> stack = new Stack<>();
		for (int ai : a) {
			if (ai == 0) {
				if (stack.empty()) {
					stack.push(new Element(1, 1));
				} else {
					Element element = stack.pop();
					element.count--;

					if (!stack.empty() && stack.peek().height == element.height + 1) {
						stack.peek().count++;
					} else {
						stack.push(new Element(element.height + 1, 1));
					}

					if (element.count != 0) {
						stack.push(element);
					}
				}
			} else {
				if (!stack.empty() && stack.peek().height == 1) {
					stack.peek().count++;
				} else {
					stack.push(new Element(1, 1));
				}
			}
		}

		return String.format("%d\n%s", stack.size(),
				stack.stream().map(element -> String.format("%d %d", element.height, element.count))
						.collect(Collectors.joining("\n")));
	}
}

class Element {
	int height;
	int count;

	Element(int height, int count) {
		this.height = height;
		this.count = count;
	}
}