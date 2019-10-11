import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
	static final int[] PRIMES = { 2, 3, 5, 7, 11, 13, 17, 19 };

	static int maxDivisorNum;
	static int minNumberWithMaxDivisorNum;

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int K = sc.nextInt();
		out.println(solve(K));

		out.close();
		sc.close();
	}

	static int solve(int K) {
		maxDivisorNum = -1;

		search(K, Integer.MAX_VALUE, 0, 1, 1);

		return K - minNumberWithMaxDivisorNum + 1;
	}

	static void search(int K, int prevExponent, int index, int divisorNum, int current) {
		if (index == PRIMES.length) {
			if (divisorNum > maxDivisorNum || (divisorNum == maxDivisorNum && current < minNumberWithMaxDivisorNum)) {
				maxDivisorNum = divisorNum;
				minNumberWithMaxDivisorNum = current;
			}

			return;
		}

		int next = current;
		for (int exponent = 0; exponent <= prevExponent && next <= K; exponent++) {
			search(K, exponent, index + 1, divisorNum * (exponent + 1), next);

			next *= PRIMES[index];
		}
	}
}
