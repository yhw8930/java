package AlgorithmBasic.class13;

import java.util.ArrayList;
import java.util.List;

public class Code04_MaxHappy {

	public static class Employee {
		public int happy;
		public List<Employee> nexts;

		public Employee(int h) {
			happy = h;
			nexts = new ArrayList<>();
		}

	}

	/**
	 * 解法一：暴力递归枚举每位员工来或不来的选择，并用 up 表示当前员工的直接上级是否参加。
	 * 若上级参加，当前员工不能参加，只能累加所有下级在“上级不来”状态下的最优结果。
	 * 若上级不参加，则比较两种可能：当前员工参加时，所有直接下级都不能参加；
	 * 当前员工不参加时，每个下级都可以继续自由选择。两种方案取较大快乐值。
	 * 该方法没有缓存“员工 + up 状态”的结果，同一子树会在不同状态下被重复计算。
	 *
	 * 时间复杂度：最坏为指数级。
	 * 额外空间复杂度：O(H)，H 为公司层级树的高度，来自递归调用栈。
	 */
	public static int maxHappy1(Employee boss) {
		if (boss == null) {
			return 0;
		}
		return process1(boss, false);
	}

	// 当前来到的节点叫cur，
	// up表示cur的上级是否来，
	// 该函数含义：
	// 如果up为true，表示在cur上级已经确定来，的情况下，cur整棵树能够提供最大的快乐值是多少？
	// 如果up为false，表示在cur上级已经确定不来，的情况下，cur整棵树能够提供最大的快乐值是多少？
	public static int process1(Employee cur, boolean up) {
		if (up) { // 如果cur的上级来的话，cur没得选，只能不来
			int ans = 0;
			for (Employee next : cur.nexts) {
				ans += process1(next, false);
			}
			return ans;
		} else { // 如果cur的上级不来的话，cur可以选，可以来也可以不来
			int p1 = cur.happy;
			int p2 = 0;
			for (Employee next : cur.nexts) {
				p1 += process1(next, true);
				p2 += process1(next, false);
			}
			return Math.max(p1, p2);
		}
	}

	/**
	 * 解法二：树形 DP，每位员工只处理一次。process 为以 x 为根的部门返回两项信息：
	 * no 表示 x 不参加时整个部门的最大快乐值，yes 表示 x 参加时整个部门的最大快乐值。
	 * x 不参加时，每个直接下级可以参加也可以不参加，因此累加 max(nextInfo.no, nextInfo.yes)；
	 * x 参加时，所有直接下级必须不参加，因此只能累加 nextInfo.no。
	 * 最后 boss 可以参加也可以不参加，返回 allInfo.yes 和 allInfo.no 的较大值。
	 *
	 * 时间复杂度：O(N)。
	 * 额外空间复杂度：O(H)，H 为公司层级树的高度，来自递归调用栈。
	 */
	public static int maxHappy2(Employee head) {
		Info allInfo = process(head);
		return Math.max(allInfo.no, allInfo.yes);
	}

	public static class Info {
		public int no;
		public int yes;

		public Info(int n, int y) {
			no = n;
			yes = y;
		}
	}

	public static Info process(Employee x) {
		if (x == null) {
			return new Info(0, 0);
		}
		int no = 0;
		int yes = x.happy;
		for (Employee next : x.nexts) {
			Info nextInfo = process(next);
			no += Math.max(nextInfo.no, nextInfo.yes);
			yes += nextInfo.no;

		}
		return new Info(no, yes);
	}

	// for test
	public static Employee genarateBoss(int maxLevel, int maxNexts, int maxHappy) {
		if (Math.random() < 0.02) {
			return null;
		}
		Employee boss = new Employee((int) (Math.random() * (maxHappy + 1)));
		genarateNexts(boss, 1, maxLevel, maxNexts, maxHappy);
		return boss;
	}

	// for test
	public static void genarateNexts(Employee e, int level, int maxLevel, int maxNexts, int maxHappy) {
		if (level > maxLevel) {
			return;
		}
		int nextsSize = (int) (Math.random() * (maxNexts + 1));
		for (int i = 0; i < nextsSize; i++) {
			Employee next = new Employee((int) (Math.random() * (maxHappy + 1)));
			e.nexts.add(next);
			genarateNexts(next, level + 1, maxLevel, maxNexts, maxHappy);
		}
	}

	public static void main(String[] args) {
		int maxLevel = 4;
		int maxNexts = 7;
		int maxHappy = 100;
		int testTimes = 100000;
		for (int i = 0; i < testTimes; i++) {
			Employee boss = genarateBoss(maxLevel, maxNexts, maxHappy);
			if (maxHappy1(boss) != maxHappy2(boss)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
