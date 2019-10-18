import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int N = sc.nextInt();
		Medicine[] medicines = new Medicine[N];
		for (int i = 0; i < medicines.length; i++) {
			int A = sc.nextInt();
			int B = sc.nextInt();

			medicines[i] = new Medicine(A, B);
		}
		int W = sc.nextInt();
		out.println(solve(medicines, W));

		out.close();
		sc.close();
	}

	static int solve(Medicine[] medicines, int W) {
		Arrays.sort(medicines, (m1, m2) -> Integer.compare(m2.A, m1.A));

		int result = 0;
		for (Medicine medicine : medicines) {
			int unit = Math.min(W, medicine.B);
			result += medicine.A * unit;

			W -= unit;
		}

		return result;
	}
}

class Medicine {
	int A;
	int B;

	Medicine(int A, int B) {
		this.A = A;
		this.B = B;
	}
}