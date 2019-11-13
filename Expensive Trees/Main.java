import java.io.File;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		long N = sc.nextLong();
		out.println(solve(N));

		out.close();
		sc.close();
	}

	static BigInteger solve(long N) {
		return BigInteger.valueOf(N / 2).multiply(BigInteger.valueOf(N - N / 2));
	}
}
