import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int A = sc.nextInt();
		int B = sc.nextInt();
		out.println(solve(A, B));

		out.close();
		sc.close();
	}

	static int solve(int A, int B) {
		return A + B;
	}
}
