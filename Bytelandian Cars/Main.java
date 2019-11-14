import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		sc.nextLine();
		String[] queries = new String[N];
		for (int i = 0; i < queries.length; i++) {
			queries[i] = sc.nextLine();
		}
		out.println(solve(queries));

		out.close();
		sc.close();
	}

	static String solve(String[] queries) {
		Queue<Long> queue = new LinkedList<>();
		Map<Long, Integer> valueToCount = new HashMap<>();
		List<Integer> result = new ArrayList<>();
		long sum = 0;
		for (String query : queries) {
			String[] fields = query.split(" ");

			if (fields[0].equals("+")) {
				long value = Long.parseLong(fields[1]);

				sum += value;
				queue.offer(value);
				valueToCount.put(value, valueToCount.getOrDefault(value, 0) + 1);
			} else if (fields[0].equals("-")) {
				long value = queue.poll();

				sum -= value;
				valueToCount.put(value, valueToCount.get(value) - 1);
			} else {
				result.add((sum % queue.size() == 0) ? valueToCount.getOrDefault(sum / queue.size(), 0) : 0);
			}
		}

		return result.stream().map(String::valueOf).collect(Collectors.joining("\n"));
	}
}
