import mooc.EdxIO;

public class Main {
	static final int LIMIT = 1_000_005;

	static int[] sequences = new int[LIMIT];
	static int[] values = new int[LIMIT];
	static int[] sequenceToIndex = new int[LIMIT];
	static int priorityQueueSize = 0;

	public static void main(String[] args) {
		try (EdxIO io = EdxIO.create()) {
			int n = io.nextInt();

			solve(io, n);
		}
	}

	static void solve(EdxIO io, int n) {
		for (int i = 0; i < n; i++) {
			String command = io.next();

			if (command.equals("A")) {
				int x = io.nextInt();

				sequences[priorityQueueSize] = i + 1;
				values[priorityQueueSize] = x;
				priorityQueueSize++;

				sequenceToIndex[i + 1] = priorityQueueSize - 1;

				siftUp(priorityQueueSize - 1);
			} else if (command.equals("X")) {
				if (priorityQueueSize == 0) {
					io.println("*");
				} else {
					io.println(values[0]);

					swap(0, priorityQueueSize - 1);

					priorityQueueSize--;

					siftDown(0);
				}
			} else {
				int x = io.nextInt();
				int y = io.nextInt();

				int index = sequenceToIndex[x];
				values[index] = y;

				siftUp(index);
			}
		}
	}

	static void siftUp(int index) {
		if (index != 0) {
			int parentIndex = (index - 1) / 2;
			if (values[index] < values[parentIndex]) {
				swap(index, parentIndex);

				siftUp(parentIndex);
			}
		}
	}

	static void siftDown(int index) {
		int leftChildIndex = index * 2 + 1;
		int rightChildIndex = index * 2 + 2;

		if (leftChildIndex < priorityQueueSize) {
			int minChildIndex;
			if (rightChildIndex == priorityQueueSize || values[leftChildIndex] <= values[rightChildIndex]) {
				minChildIndex = leftChildIndex;
			} else {
				minChildIndex = rightChildIndex;
			}

			if (values[index] > values[minChildIndex]) {
				swap(index, minChildIndex);

				siftDown(minChildIndex);
			}
		}
	}

	static void swap(int index1, int index2) {
		swapArray(sequences, index1, index2);
		swapArray(values, index1, index2);

		sequenceToIndex[sequences[index1]] = index1;
		sequenceToIndex[sequences[index2]] = index2;
	}

	static void swapArray(int[] a, int index1, int index2) {
		int temp = a[index1];
		a[index1] = a[index2];
		a[index2] = temp;
	}
}
