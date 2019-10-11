import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = Integer.parseInt(br.readLine());
		String[] commands = new String[N];
		for (int i = 0; i < commands.length; i++) {
			commands[i] = br.readLine();
		}
		out.println(solve(commands));

		out.close();
		br.close();
	}

	static String solve(String[] commands) {
		List<Integer> result = new ArrayList<>();
		Queue<Integer> queue = new LinkedList<>();
		Deque<Integer> deque = new LinkedList<>();

		for (String command : commands) {
			char type = command.charAt(0);

			if (type == '+') {
				int value = Integer.parseInt(command.substring(2));

				queue.offer(value);

				while (!deque.isEmpty() && value < deque.peekLast()) {
					deque.pollLast();
				}
				deque.offerLast(value);
			} else if (type == '-') {
				int value = queue.poll();

				if (value == deque.peekFirst()) {
					deque.pollFirst();
				}
			} else {
				result.add(deque.peekFirst());
			}
		}

		return result.stream().map(String::valueOf).collect(Collectors.joining("\n"));
	}
}
