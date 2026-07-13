package AlgorithmBasic.class12;

import java.util.ArrayList;

public class Code05_MaxSubBSTSize {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static int getBSTSize(Node head) {
		if (head == null) {
			return 0;
		}
		ArrayList<Node> arr = new ArrayList<>();
		in(head, arr);
		for (int i = 1; i < arr.size(); i++) {
			if (arr.get(i).value <= arr.get(i - 1).value) {
				return 0;
			}
		}
		return arr.size();
	}

	public static void in(Node head, ArrayList<Node> arr) {
		if (head == null) {
			return;
		}
		in(head.left, arr);
		arr.add(head);
		in(head.right, arr);
	}

	/**
	 * 解法一：枚举每棵子树的根节点，并用 getBSTSize 判断当前整棵子树是否为搜索二叉树。
	 * getBSTSize 收集子树的中序遍历结果；若序列严格递增，当前整棵子树就是 BST，
	 * 可以直接返回其节点数。否则继续在左右子树中分别寻找，取两者的较大值。
	 * 判断中使用严格递增，因此重复值不符合这里的 BST 定义。
	 *
	 * 时间复杂度：最坏 O(N^2)，因为多棵子树会被重复中序遍历。
	 * 额外空间复杂度：O(N)，用于中序序列和递归调用栈。
	 */
	public static int maxSubBSTSize1(Node head) {
		if (head == null) {
			return 0;
		}
		int h = getBSTSize(head);
		if (h != 0) {
			return h;
		}
		return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
	}

//	public static int maxSubBSTSize2(Node head) {
//		if (head == null) {
//			return 0;
//		}
//		return process(head).maxSubBSTSize;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	// 任何子树
//	public static class Info {
//		public boolean isAllBST;
//		public int maxSubBSTSize;
//		public int min;
//		public int max;
//
//		public Info(boolean is, int size, int mi, int ma) {
//			isAllBST = is;
//			maxSubBSTSize = size;
//			min = mi;
//			max = ma;
//		}
//	}
//	
//	
//	
//	
//	public static Info process(Node X) {
//		if(X == null) {
//			return null;
//		}
//		Info leftInfo = process(X.left);
//		Info rightInfo = process(X.right);
//		
//		
//		
//		int min = X.value;
//		int max = X.value;
//		
//		if(leftInfo != null) {
//			min = Math.min(min, leftInfo.min);
//			max = Math.max(max, leftInfo.max);
//		}
//		if(rightInfo != null) {
//			min = Math.min(min, rightInfo.min);
//			max = Math.max(max, rightInfo.max);
//		}
//		
//		
//		
//		
//		
//		
//
//		int maxSubBSTSize = 0;
//		if(leftInfo != null) {
//			maxSubBSTSize = leftInfo.maxSubBSTSize;
//		}
//		if(rightInfo !=null) {
//			maxSubBSTSize = Math.max(maxSubBSTSize, rightInfo.maxSubBSTSize);
//		}
//		boolean isAllBST = false;
//		
//		
//		if(
//				// 左树整体需要是搜索二叉树
//				(  leftInfo == null ? true : leftInfo.isAllBST    )
//				&&
//				(  rightInfo == null ? true : rightInfo.isAllBST    )
//				&&
//				// 左树最大值<x
//				(leftInfo == null ? true : leftInfo.max < X.value)
//				&&
//				(rightInfo == null ? true : rightInfo.min > X.value)
//				
//				
//				) {
//			
//			maxSubBSTSize = 
//					(leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
//					+
//					(rightInfo == null ? 0 : rightInfo.maxSubBSTSize)
//					+
//					1;
//					isAllBST = true;
//			
//			
//		}
//		return new Info(isAllBST, maxSubBSTSize, min, max);
//	}

	/**
	 * 解法二：树形 DP，每个节点只处理一次。process 为每棵子树返回四项信息：
	 * maxBSTSubtreeSize 是子树内最大 BST 子树的节点数，allSize 是子树总节点数，
	 * max 和 min 分别是子树的最大值和最小值。
	 * 当前答案有三种来源：p1 是左子树内的最大 BST，p2 是右子树内的最大 BST，
	 * p3 是以当前节点为根的整棵子树。只有左右子树整体都是 BST，
	 * 且左子树最大值严格小于当前值、当前值严格小于右子树最小值时，p3 才成立。
	 * maxBSTSubtreeSize == allSize 表示该子树的最大 BST 已经覆盖全部节点，即子树整体是 BST。
	 *
	 * 时间复杂度：O(N)。
	 * 额外空间复杂度：O(H)，H 为树高，来自递归调用栈。
	 */
	public static int maxSubBSTSize2(Node head) {
		if(head == null) {
			return 0;
		}
		return process(head).maxBSTSubtreeSize;
	}

	public static class Info {
		public int maxBSTSubtreeSize;
		public int allSize;
		public int max;
		public int min;

		public Info(int m, int a, int ma, int mi) {
			maxBSTSubtreeSize = m;
			allSize = a;
			max = ma;
			min = mi;
		}
	}

	public static Info process(Node x) {
		if (x == null) {
			return null;
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);
		int max = x.value;
		int min = x.value;
		int allSize = 1;
		if (leftInfo != null) {
			max = Math.max(leftInfo.max, max);
			min = Math.min(leftInfo.min, min);
			allSize += leftInfo.allSize;
		}
		if (rightInfo != null) {
			max = Math.max(rightInfo.max, max);
			min = Math.min(rightInfo.min, min);
			allSize += rightInfo.allSize;
		}
		int p1 = -1;
		if (leftInfo != null) {
			p1 = leftInfo.maxBSTSubtreeSize;
		}
		int p2 = -1;
		if (rightInfo != null) {
			p2 = rightInfo.maxBSTSubtreeSize;
		}
		int p3 = -1;
		boolean leftBST = leftInfo == null || (leftInfo.maxBSTSubtreeSize == leftInfo.allSize);
		boolean rightBST = rightInfo == null || (rightInfo.maxBSTSubtreeSize == rightInfo.allSize);
		if (leftBST && rightBST) {
			boolean leftMaxLessX = leftInfo == null || (leftInfo.max < x.value);
			boolean rightMinMoreX = rightInfo == null || (x.value < rightInfo.min);
			if (leftMaxLessX && rightMinMoreX) {
				int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
				int rightSize = rightInfo == null ? 0 : rightInfo.allSize;
				p3 = leftSize + rightSize + 1;
			}
		}
		return new Info(Math.max(p1, Math.max(p2, p3)), allSize, max, min);
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
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (maxSubBSTSize1(head) != maxSubBSTSize2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
