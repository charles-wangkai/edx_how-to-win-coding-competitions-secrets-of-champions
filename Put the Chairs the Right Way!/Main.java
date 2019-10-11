import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int length1 = sc.nextInt();
		int length2 = sc.nextInt();
		int length3 = sc.nextInt();
		out.println(solve(length1, length2, length3));

		out.close();
		sc.close();
	}

	static double solve(int length1, int length2, int length3) {
		return (length1 + length2 + length3) / 6.0;
	}
}
