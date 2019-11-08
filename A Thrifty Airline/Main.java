import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int[][] A = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		out.println(solve(A));

		out.close();
		br.close();
	}

	static String solve(int[][] A) {
		int N = A.length;

		int[][] distances = new int[N][N];
		int[] roots = IntStream.rangeClosed(0, N).toArray();

		Flight[] candidates = IntStream.range(0, N).boxed()
				.flatMap(i -> IntStream.range(i + 1, N).mapToObj(j -> new Flight(i, j)))
				.sorted((f1, f2) -> Integer.compare(A[f1.city1][f1.city2], A[f2.city1][f2.city2]))
				.toArray(Flight[]::new);
		List<Flight> flights = new ArrayList<>();
		for (Flight candidate : candidates) {
			int root1 = roots[candidate.city1];
			int root2 = roots[candidate.city2];

			if (root1 == root2) {
				if (distances[candidate.city1][candidate.city2] != A[candidate.city1][candidate.city2]) {
					return "NO";
				}
			} else {
				flights.add(candidate);

				int[] group1 = IntStream.range(0, N).filter(i -> roots[i] == root1).toArray();
				int[] group2 = IntStream.range(0, N).filter(i -> roots[i] == root2).toArray();

				for (int member1 : group1) {
					for (int member2 : group2) {
						int distance = distances[member1][candidate.city1] + A[candidate.city1][candidate.city2]
								+ distances[candidate.city2][member2];

						distances[member1][member2] = distance;
						distances[member2][member1] = distance;
					}
				}

				for (int member2 : group2) {
					roots[member2] = root1;
				}
			}
		}

		return String.format("YES\n%d\n%s", flights.size(), flights.stream().map(
				flight -> String.format("%d %d %d", flight.city1 + 1, flight.city2 + 1, A[flight.city1][flight.city2]))
				.collect(Collectors.joining("\n")));
	}
}

class Flight {
	int city1;
	int city2;

	Flight(int city1, int city2) {
		this.city1 = city1;
		this.city2 = city2;
	}
}