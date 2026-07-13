package AlgorithmBasic.class12;

public class Code04_IsFull {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	/**
	 * 解法一：利用满二叉树的节点数公式 N = 2^H - 1。
	 * h 单独递归计算树高 H，n 再单独递归统计节点数 N，
	 * 最后检查二者是否满足公式。空树高度和节点数都为 0，视为满二叉树。
	 * 该实现使用 int 保存节点数并计算 1 << height，因此假设树高较小，
	 * 否则会受 Java int 溢出和位移距离取模规则影响。
	 *
	 * 时间复杂度：O(N)，但会完整遍历树两次。
	 * 额外空间复杂度：O(H)，H 为树高，来自递归调用栈。
	 */
	public static boolean isFull1(Node head) {
		if (head == null) {
			return true;
		}
		int height = h(head);
		int nodes = n(head);
		return (1 << height) - 1 == nodes;
	}

	public static int h(Node head) {
		if (head == null) {
			return 0;
		}
		return Math.max(h(head.left), h(head.right)) + 1;
	}

	public static int n(Node head) {
		if (head == null) {
			return 0;
		}
		return n(head.left) + n(head.right) + 1;
	}

	/**
	 * 解法二：用树形 DP 在一次后序遍历中同时收集子树高度和节点数。
	 * process 的返回约定是：height 表示当前子树高度，nodes 表示当前子树节点数；
	 * 父节点用左右子树信息合并出自己的 Info。根节点最终仍通过 N = 2^H - 1 判断是否为满二叉树。
	 * 相比 isFull1，该方法只完整遍历一次，但同样受 int 节点数和 1 << height 的取值范围限制。
	 *
	 * 时间复杂度：O(N)。
	 * 额外空间复杂度：O(H)，H 为树高，来自递归调用栈。
	 */
	public static boolean isFull2(Node head) {
		if (head == null) {
			return true;
		}
		Info all = process(head);
		return (1 << all.height) - 1 == all.nodes;
	}

	public static class Info {
		public int height;
		public int nodes;

		public Info(int h, int n) {
			height = h;
			nodes = n;
		}
	}

	public static Info process(Node head) {
		if (head == null) {
			return new Info(0, 0);
		}
		Info leftInfo = process(head.left);
		Info rightInfo = process(head.right);
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		int nodes = leftInfo.nodes + rightInfo.nodes + 1;
		return new Info(height, nodes);
	}

	// for test
	public static Node generateRandomBST(int maxLevel, int maxValue) {
		return generate(1, maxLevel, maxValue);
	}

	// for test
	public static Node generate(int level, int maxLevel, int maxValue) {
		if (level > maxLevel || Math.random() < 0.5) {
			return null;
		}
		Node head = new Node((int) (Math.random() * maxValue));
		head.left = generate(level + 1, maxLevel, maxValue);
		head.right = generate(level + 1, maxLevel, maxValue);
		return head;
	}

	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (isFull1(head) != isFull2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
