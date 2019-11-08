// https://en.wikipedia.org/wiki/Johnson%27s_rule

import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		int[] A = readArray(sc, N);
		int[] B = readArray(sc, N);
		out.println(solve(A, B));

		out.close();
		sc.close();
	}

	static int[] readArray(Scanner sc, int size) {
		int[] result = new int[size];
		for (int i = 0; i < result.length; i++) {
			result[i] = sc.nextInt();
		}

		return result;
	}

	static String solve(int[] A, int[] B) {
		int n = A.length;

		Element[] elements = IntStream.range(0, n).boxed()
				.flatMap(i -> Stream.of(new Element(i, A[i], true), new Element(i, B[i], false)))
				.sorted((e1, e2) -> Integer.compare(e1.time, e2.time)).toArray(Element[]::new);

		Bicycle[] bicycles = new Bicycle[n];
		int leftIndex = 0;
		int rightIndex = bicycles.length - 1;
		boolean[] used = new boolean[n];
		for (Element element : elements) {
			if (used[element.index]) {
				continue;
			}
			used[element.index] = true;

			Bicycle bicyle = new Bicycle(element.index, A[element.index], B[element.index]);

			if (element.AOrB) {
				bicycles[leftIndex] = bicyle;
				leftIndex++;
			} else {
				bicycles[rightIndex] = bicyle;
				rightIndex--;
			}
		}

		long[] startAs = new long[n];
		long[] startBs = new long[n];
		long timeA = 0;
		long timeB = 0;
		for (Bicycle bicycle : bicycles) {
			startAs[bicycle.index] = timeA;
			timeA += bicycle.A;

			timeB = Math.max(timeB, timeA);
			startBs[bicycle.index] = timeB;
			timeB += bicycle.B;
		}

		return String.format("%d\n%s\n%s", timeB,
				Arrays.stream(startAs).mapToObj(String::valueOf).collect(Collectors.joining(" ")),
				Arrays.stream(startBs).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
	}
}

class Element {
	int index;
	int time;
	boolean AOrB;

	Element(int index, int time, boolean AOrB) {
		this.index = index;
		this.time = time;
		this.AOrB = AOrB;
	}
}

class Bicycle {
	int index;
	int A;
	int B;

	Bicycle(int index, int A, int B) {
		this.index = index;
		this.A = A;
		this.B = B;
	}
}