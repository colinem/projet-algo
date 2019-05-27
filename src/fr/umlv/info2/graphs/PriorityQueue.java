package fr.umlv.info2.graphs;

public class PriorityQueue {

	private final int[] approximations;
	private Integer root;
	private PriorityQueue left;
	private PriorityQueue right;

	public PriorityQueue(int[] approximations) {
		this.approximations = approximations;
	}

	public int extractMin() {
		if (left == null) {
			int min = root;
			if (right != null) {
				root = right.root;
				left = right.left;
				right = right.right;
			}
			return min;
		}
		if (left.left == null) {
			int min = left.root;
			left = left.right;
			return min;
		}
		return left.extractMin();
	}

	public boolean isEmpty() {
		return root == null;
	}

	public void add(int element, boolean evenIfContains) {
		if (root == null) {
			root = element;
			return;
		}
		if (element == root && !evenIfContains)
			return;
		if (approximations[element] <= approximations[root]) {
			if (left == null)
				left = new PriorityQueue(approximations);
			left.add(element, evenIfContains);
		}
		else {
			if (right == null)
				right = new PriorityQueue(approximations);
			right.add(element, evenIfContains);
		}
	}

	public void print() {
		if (left != null)
			left.print();
		System.out.println(root);
		if (right != null)
			right.print();
	}

}
