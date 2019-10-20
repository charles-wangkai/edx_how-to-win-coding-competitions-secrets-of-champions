import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

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

		Bicycle[] bicycles = new Bicycle[n];
		for (int i = 0; i < bicycles.length; i++) {
			bicycles[i] = new Bicycle(i, A[i], B[i]);
		}

		Arrays.sort(bicycles, (bicycle1, bicycle2) -> {
			int sign1 = Integer.signum(bicycle1.A - bicycle1.B);
			int sign2 = Integer.signum(bicycle2.A - bicycle2.B);
			if (sign1 != sign2) {
				return Integer.compare(sign1, sign2);
			}

			if (sign1 < 0) {
				if (bicycle1.A != bicycle2.A) {
					return Integer.compare(bicycle1.A, bicycle2.A);
				} else {
					return -Integer.compare(bicycle1.B, bicycle2.B);
				}
			} else if (sign1 > 0) {
				if (bicycle1.B != bicycle2.B) {
					return -Integer.compare(bicycle1.B, bicycle2.B);
				} else {
					return Integer.compare(bicycle1.A, bicycle2.A);
				}
			} else {
				return 0;
			}
		});

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