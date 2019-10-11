import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		String[] commands = new String[N];
		for (int i = 0; i < commands.length; i++) {
			commands[i] = br.readLine();
		}
		out.println(solve(commands));

		br.close();
		out.close();
	}

	static String solve(String[] commands) {
		List<Integer> result = new ArrayList<>();
		Stack<Integer> stack = new Stack<>();

		for (String command : commands) {
			if (command.charAt(0) == '+') {
				stack.push(Integer.parseInt(command.substring(2)));
			} else {
				result.add(stack.pop());
			}
		}

		return result.stream().map(String::valueOf).collect(Collectors.joining("\n"));
	}
}
