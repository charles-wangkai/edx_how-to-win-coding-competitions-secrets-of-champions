import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = Integer.parseInt(br.readLine());
		String[] changes = new String[N];
		for (int i = 0; i < changes.length; i++) {
			changes[i] = br.readLine();
		}
		out.println(solve(changes));

		out.close();
		br.close();
	}

	static String solve(String[] changes) {
		Deque<Integer> leftHalf = new ArrayDeque<>();
		Deque<Integer> rightHalf = new ArrayDeque<>();

		for (String change : changes) {
			if (change.startsWith("add")) {
				int x = Integer.parseInt(change.substring("add ".length()));

				rightHalf.offerLast(x);
			} else if (change.equals("take")) {
				rightHalf.pollLast();
			} else {
				Deque<Integer> temp = leftHalf;
				leftHalf = rightHalf;
				rightHalf = temp;
			}

			if (rightHalf.size() - leftHalf.size() == 2) {
				leftHalf.offerLast(rightHalf.pollFirst());
			} else if (leftHalf.size() - rightHalf.size() == 1) {
				rightHalf.offerFirst(leftHalf.pollLast());
			}
		}

		List<Integer> result = Stream.concat(leftHalf.stream(), rightHalf.stream()).collect(Collectors.toList());

		return String.format("%d\n%s", result.size(),
				result.stream().map(String::valueOf).collect(Collectors.joining(" ")));
	}
}
