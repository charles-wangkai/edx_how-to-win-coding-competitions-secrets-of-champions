import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static final int LIMIT = 100005;

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(new File("input.txt"));
		PrintStream out = new PrintStream("output.txt");

		int n = sc.nextInt();
		int m = sc.nextInt();
		int k = sc.nextInt();
		int[] lefts = readArray(sc, n);
		int[] rights = readArray(sc, m);
		int[] crosses = readArray(sc, k);
		int f = sc.nextInt();
		out.println(solve(lefts, rights, crosses, f));

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

	static int solve(int[] lefts, int[] rights, int[] crosses, int f) {
		List<Range> leftRanges = buildRanges(lefts);
		List<Range> rightRanges = buildRanges(rights);

		@SuppressWarnings("unchecked")
		List<Integer>[] leftToRightIndices = new List[leftRanges.size()];
		for (int i = 0; i < leftToRightIndices.length; i++) {
			leftToRightIndices[i] = new ArrayList<>();
		}

		@SuppressWarnings("unchecked")
		List<Integer>[] rightToLeftIndices = new List[rightRanges.size()];
		for (int i = 0; i < rightToLeftIndices.length; i++) {
			rightToLeftIndices[i] = new ArrayList<>();
		}

		for (int cross : crosses) {
			int leftIndex = find(leftRanges, cross);
			int rightIndex = find(rightRanges, cross);

			leftToRightIndices[leftIndex].add(rightIndex);
			rightToLeftIndices[rightIndex].add(leftIndex);
		}

		int[] leftDistances = new int[leftRanges.size()];
		Arrays.fill(leftDistances, -1);
		leftDistances[0] = 0;

		int[] rightDistances = new int[rightRanges.size()];
		Arrays.fill(rightDistances, -1);

		Queue<Element> queue = new LinkedList<>();
		queue.offer(new Element(true, 0));

		while (true) {
			Element head = queue.poll();

			if (head.leftOrRight) {
				for (int nextLeftIndex : new int[] { head.index - 1, head.index + 1 }) {
					if (nextLeftIndex >= 0 && nextLeftIndex < leftRanges.size() && leftDistances[nextLeftIndex] == -1) {
						leftDistances[nextLeftIndex] = leftDistances[head.index] + 1;
						queue.offer(new Element(true, nextLeftIndex));
					}
				}

				for (int nextRightIndex : leftToRightIndices[head.index]) {
					if (rightDistances[nextRightIndex] == -1) {
						rightDistances[nextRightIndex] = leftDistances[head.index] + 1;
						queue.offer(new Element(false, nextRightIndex));
					}
				}
			} else {
				Range range = rightRanges.get(head.index);

				if (f >= range.begin && f <= range.end) {
					return rightDistances[head.index];
				}

				for (int nextRightIndex : new int[] { head.index - 1, head.index + 1 }) {
					if (nextRightIndex >= 0 && nextRightIndex < rightRanges.size()
							&& rightDistances[nextRightIndex] == -1) {
						rightDistances[nextRightIndex] = rightDistances[head.index] + 1;
						queue.offer(new Element(false, nextRightIndex));
					}
				}

				for (int nextLeftIndex : rightToLeftIndices[head.index]) {
					if (leftDistances[nextLeftIndex] == -1) {
						leftDistances[nextLeftIndex] = rightDistances[head.index] + 1;
						queue.offer(new Element(true, nextLeftIndex));
					}
				}
			}
		}
	}

	static int find(List<Range> ranges, int target) {
		int lowerIndex = 0;
		int upperIndex = ranges.size() - 1;
		while (true) {
			int middleIndex = (lowerIndex + upperIndex) / 2;

			if (target < ranges.get(middleIndex).begin) {
				upperIndex = middleIndex - 1;
			} else if (target > ranges.get(middleIndex).end) {
				lowerIndex = middleIndex + 1;
			} else {
				return middleIndex;
			}
		}
	}

	static List<Range> buildRanges(int[] values) {
		values = Arrays.stream(values).boxed().distinct().sorted().mapToInt(x -> x).toArray();

		List<Range> ranges = new ArrayList<>();
		int begin = 0;
		for (int value : values) {
			ranges.add(new Range(begin, value));

			begin = value + 1;
		}
		ranges.add(new Range(begin, LIMIT));

		return ranges;
	}
}

class Element {
	boolean leftOrRight;
	int index;

	Element(boolean leftOrRight, int index) {
		this.leftOrRight = leftOrRight;
		this.index = index;
	}
}

class Range {
	int begin;
	int end;

	Range(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}
}