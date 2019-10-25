import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
	static final int ITERATION_NUM = 30;

	public static void main(String[] args) throws Throwable {
		BufferedReader br = new BufferedReader(new FileReader("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[] v = new int[n];
		int[] w = new int[n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			v[i] = Integer.parseInt(st.nextToken());
			w[i] = Integer.parseInt(st.nextToken());
		}
		out.println(solve(v, w, k));

		out.close();
		br.close();
	}

	static String solve(int[] v, int[] w, int k) {
		int n = v.length;

		int[] result = new int[k];
		double lower = IntStream.range(0, n).mapToDouble(i -> (double) v[i] / w[i]).min().getAsDouble();
		double upper = IntStream.range(0, n).mapToDouble(i -> (double) v[i] / w[i]).max().getAsDouble();
		for (int iter = 0; iter < ITERATION_NUM; iter++) {
			double middle = (lower + upper) / 2;

			Element[] elements = new Element[n];
			for (int i = 0; i < elements.length; i++) {
				elements[i] = new Element(i, v[i] - w[i] * middle);
			}
			Arrays.sort(elements, (e1, e2) -> -Double.compare(e1.diff, e2.diff));

			double diffSum = 0;
			for (int i = 0; i < k; i++) {
				diffSum += elements[i].diff;
			}

			if (diffSum >= 0) {
				for (int i = 0; i < result.length; i++) {
					result[i] = elements[i].index + 1;
				}

				int sumV = 0;
				int sumW = 0;
				for (int i = 0; i < k; i++) {
					sumV += v[elements[i].index];
					sumW += w[elements[i].index];
				}
				lower = (double) sumV / sumW;
			} else {
				upper = middle;
			}
		}

		return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
	}
}

class Element {
	int index;
	double diff;

	public Element(int index, double diff) {
		super();
		this.index = index;
		this.diff = diff;
	}
}