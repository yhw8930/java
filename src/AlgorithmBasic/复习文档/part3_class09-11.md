# 算法面试复习资料 Part3：class09 - class11

> 覆盖范围：链表进阶问题、链表相交问题、二叉树遍历与序列化、二叉树相关经典题。
> 每个 .java 文件一节，按「题目描述 / 核心思路 / 关键代码 / 复杂度 / 易错点」整理。

---

## class09 链表进阶问题（中点、回文、分区、随机指针拷贝）

### class09 / Code01_LinkedListMid.java —— 链表中点的四种定义与快慢指针求法

**题目描述**：给定单链表头节点 head，按照四种不同的「中点」定义返回对应节点：
1. `midOrUpMidNode`：奇数返回中点，偶数返回上中点（前一个）。
2. `midOrDownMidNode`：奇数返回中点，偶数返回下中点（后一个）。
3. `midOrUpMidPreNode`：奇数返回中点的前一个，偶数返回上中点的前一个。
4. `midOrDownMidPreNode`：奇数返回中点的前一个，偶数返回下中点的前一个。
要求用 O(1) 额外空间。代码里 `right1~right4` 是用数组装下所有节点的对数器实现。

**核心思路**：经典「快慢指针」。快指针一次走两步、慢指针一次走一步，快指针到尾时慢指针约在中点。四种定义的区别全靠 **slow / fast 的初始位置** 与 **while 循环的终止条件** 微调。比如 `midOrUpMidNode` 让 `slow=head.next, fast=head.next.next`，并单独处理 0/1/2 个节点的边界。对数器思路简单（数组下标算中点）但用 O(n) 空间，仅用于验证。

**关键代码 / 步骤**：
```java
// 奇数返回中点，偶数返回上中点
Node slow = head.next;
Node fast = head.next.next;
while (fast.next != null && fast.next.next != null) {
    slow = slow.next;       // 慢一步
    fast = fast.next.next;  // 快两步
}
return slow;
```

**复杂度**：快慢指针 时间 O(n)，空间 O(1)；数组对数器 时间 O(n)，空间 O(n)。

**易错点 / 面试提示**：
- 极易在边界（0、1、2 个节点）翻车，每种定义都要单独 `if` 处理短链表。
- 初始位置和循环条件必须配套记忆，建议用「小样本枚举」推导而非死记。
- 面试官常追问「为什么不用记录长度再走一半」——快慢指针只遍历一遍，更优雅。

---

### class09 / Code02_IsPalindromeList.java —— 判断单链表是否回文

**题目描述**：给定单链表头节点，判断其值序列是否为回文（正读反读相同）。给出三种空间复杂度的方法。

**核心思路**：
1. 方法一：把所有节点压栈，再从头遍历与出栈值比对，O(n) 空间。
2. 方法二：只把右半部分压栈（快慢指针找中点），与左半比对，O(n/2) 空间。
3. 方法三（推荐）：O(1) 空间。快慢指针找到中点后，**把右半部分链表原地反转**，让左右两端向中间逼近逐一比对；比对完成后**再把链表反转回原样**恢复结构。

**关键代码 / 步骤**：
```java
// 找中点 n1
while (n2.next != null && n2.next.next != null) { n1 = n1.next; n2 = n2.next.next; }
// 反转右半部分
n2 = n1.next; n1.next = null;
while (n2 != null) { n3 = n2.next; n2.next = n1; n1 = n2; n2 = n3; }
// n1 现在是原尾节点，双指针向中间比对
n3 = n1; n2 = head;
while (n1 != null && n2 != null) { if (n1.value != n2.value) { res = false; break; } n1=n1.next; n2=n2.next; }
// 反转恢复
```

**复杂度**：方法一/二 时间 O(n)，空间 O(n) / O(n/2)；方法三 时间 O(n)，空间 O(1)。

**易错点 / 面试提示**：
- 方法三必须**恢复链表**，否则破坏原数据结构，是面试官最爱考的点。
- 比对完成后即使中途 break 也要把右半反转回来，恢复逻辑不能漏。
- 空链表、单节点直接 true。

---

### class09 / Code03_SmallerEqualBigger.java —— 链表的荷兰国旗分区（小于、等于、大于）

**题目描述**：给定链表头和一个枢值 pivot，调整链表使所有小于 pivot 的节点在前、等于的在中、大于的在后。方法二要求各区域内**保持原有相对顺序（稳定）**。

**核心思路**：
1. 方法一：把节点放进数组，对数组做荷兰国旗三向 partition（small/big 指针），再重新串成链表。简单但 O(n) 空间且**不稳定**。
2. 方法二（推荐）：准备「小于/等于/大于」三组各自的头尾指针（sH/sT、eH/eT、mH/mT），遍历一次把每个节点挂到对应区域尾部，最后把三段首尾相连。天然 **稳定**且 O(1) 额外空间。

**关键代码 / 步骤**：
```java
while (head != null) {
    next = head.next; head.next = null;
    if (head.value < pivot)      { /* 挂到 small 区尾 */ }
    else if (head.value == pivot){ /* 挂到 equal 区尾 */ }
    else                         { /* 挂到 big 区尾 */ }
    head = next;
}
// 连接：小尾->等头，等尾->大头（注意区域可能为空）
if (sT != null) { sT.next = eH; eT = eT == null ? sT : eT; }
if (eT != null) { eT.next = mH; }
return sH != null ? sH : (eH != null ? eH : mH);
```

**复杂度**：方法一 时间 O(n)，空间 O(n)；方法二 时间 O(n)，空间 O(1)。

**易错点 / 面试提示**：
- 连接三段时**任何区域都可能为空**，需用 `eT` 兜底逻辑（「谁去连大于区，谁就成 eT」）。
- 返回头节点要按 小→等→大 的优先级选第一个非空区域头。
- 数组解法不稳定，是与方法二的关键区别，面试官常拿稳定性追问。

---

### class09 / Code04_CopyListWithRandom.java —— 复制带随机指针的链表（LeetCode 138）

**题目描述**：链表每个节点除 next 外还有 rand 指针，指向链表中任意节点或 null。要求深拷贝整个链表，返回新链表头。

**核心思路**：
1. 方法一：用 `HashMap<老节点, 新节点>`。第一遍只建新节点存映射，第二遍用 map 设置新节点的 next 和 rand（`新.next = map.get(老.next)`）。O(n) 空间。
2. 方法二（推荐）：不用哈希表，O(1) 额外空间。**把每个克隆节点插到原节点后面**（1->1'->2->2'），此时 `cur.next` 就是克隆节点，rand 可通过 `cur.rand.next` 定位；最后把两条链拆分开，恢复原链表。

**关键代码 / 步骤**：
```java
// 1) 克隆节点插在原节点后：1 -> 1' -> 2 -> 2'
while (cur != null) { next = cur.next; cur.next = new Node(cur.value); cur.next.next = next; cur = next; }
// 2) 设克隆节点的 rand
while (cur != null) { curCopy = cur.next; curCopy.rand = cur.rand != null ? cur.rand.next : null; cur = cur.next.next; }
// 3) 拆分两条链，恢复原链表
```

**复杂度**：方法一 时间 O(n)，空间 O(n)；方法二 时间 O(n)，空间 O(1)。

**易错点 / 面试提示**：
- 方法二设置 rand 时，原节点 rand 为 null 要特判，否则 `cur.rand.next` 空指针。
- 拆分阶段要正确恢复原链表结构（`cur.next = next`），且新链表 `curCopy.next = next.next`，末尾判空。
- 面试官几乎必问「能否不用哈希表做到 O(1) 空间」——即方法二。

---

## class10 链表相交问题 + 二叉树遍历基础

### class10 / Code01_FindFirstIntersectNode.java —— 两个可能有环链表的第一个相交节点

**题目描述**：给定两个单链表头 head1、head2（各自可能有环也可能无环），返回它们第一个相交的节点；若不相交返回 null。要求 O(1) 额外空间。

**核心思路**：先用快慢指针 `getLoopNode` 求各自的**入环节点**（无环返回 null）。然后按三种情况讨论：
1. **两个都无环**（loop1、loop2 都为 null）：`noLoop`。先各自走到尾判断尾节点是否相同（不同则不相交），再用**长度差**让长链表先走差值步，之后同步前进直到相遇。
2. **一个有环一个无环**：不可能相交，返回 null。
3. **两个都有环**（`bothLoop`）：又分两种——若入环点相同（loop1==loop2），相交在入环前，转化为「无环相交」用长度差法（边界换成 loop）；若入环点不同，从 loop1 沿环走一圈看能否遇到 loop2，能则两个入环点都是相交点（返回 loop1），否则不相交。

**关键代码 / 步骤**：
```java
Node loop1 = getLoopNode(head1), loop2 = getLoopNode(head2);
if (loop1 == null && loop2 == null) return noLoop(head1, head2);
if (loop1 != null && loop2 != null) return bothLoop(head1, loop1, head2, loop2);
return null; // 一个有环一个无环

// getLoopNode: 快慢相遇后，fast 回到 head 与 slow 同速走，再相遇即入环点
fast = head;
while (slow != fast) { slow = slow.next; fast = fast.next; }
return slow;
```

**复杂度**：时间 O(n+m)，空间 O(1)。

**易错点 / 面试提示**：
- `getLoopNode` 中快慢相遇后让 fast 从头重走是 Floyd 判圈核心，需能证明。
- `bothLoop` 中入环点不同时返回 loop1（或 loop2）都对，因为两入环点都算「第一个相交节点」。
- 一个有环一个无环必不相交，是常被忽略的剪枝。

---

### class10 / Code02_RecursiveTraversalBT.java —— 二叉树递归先序/中序/后序遍历

**题目描述**：给定二叉树头节点，分别用递归方式输出先序、中序、后序遍历结果。

**核心思路**：理解「递归序」：每个节点在递归过程中会被访问 3 次（进入时、左子返回后、右子返回后）。在三个不同时机打印就分别得到先序（第 1 次）、中序（第 2 次）、后序（第 3 次）。这是所有二叉树递归题的思维基石。

**关键代码 / 步骤**：
```java
public static void f(Node head) {
    if (head == null) return;
    // 位置1：先序在此打印
    f(head.left);
    // 位置2：中序在此打印
    f(head.right);
    // 位置3：后序在此打印
}
```

**复杂度**：时间 O(N)，空间 O(H)（H 为树高，递归栈）。

**易错点 / 面试提示**：
- 核心是「递归序 = 每个节点经过 3 次」，能据此推导后续所有树形 DP。
- 空间是 O(H)，最坏退化成链表时 O(N)。

---

### class10 / Code03_UnRecursiveTraversalBT.java —— 二叉树非递归（栈）遍历

**题目描述**：用栈（迭代）实现二叉树先序、中序、后序遍历，不使用系统递归。

**核心思路**：
- **先序**：栈先压头；每次弹出打印，然后**先压右再压左**（保证左先出）。
- **中序**：一路把左边界压栈，弹出时打印并转向其右子树，循环往复。
- **后序方法一（pos1，推荐易懂）**：用两个栈。s1 按「头右左」出栈顺序压入 s2，最后倒出 s2 即「左右头」。
- **后序方法二（pos2，省空间）**：单栈，用变量 h 记录上次打印的节点，判断当前栈顶的左右子是否已处理过，决定下压还是弹出打印。

**关键代码 / 步骤**：
```java
// 先序：先右后左压栈
stack.push(head);
while (!stack.isEmpty()) {
    head = stack.pop(); print(head);
    if (head.right != null) stack.push(head.right);
    if (head.left  != null) stack.push(head.left);
}
// 中序：左边界入栈
while (!stack.isEmpty() || cur != null) {
    if (cur != null) { stack.push(cur); cur = cur.left; }
    else { cur = stack.pop(); print(cur); cur = cur.right; }
}
```

**复杂度**：时间 O(N)，空间 O(H)（后序方法一额外一个栈，O(N)）。

**易错点 / 面试提示**：
- 先序「先压右再压左」的顺序是关键，写反就成了镜像。
- 后序最难，推荐先掌握双栈法（由先序变体「头右左」逆序得到）。
- 中序循环条件是 `!stack.isEmpty() || cur != null`，缺一不可。

---

## class11 二叉树经典题（层序、序列化、宽度、后继、折纸）

### class11 / Code01_LevelTraversalBT.java —— 二叉树层序遍历（BFS）

**题目描述**：给定二叉树头节点，按层（从上到下、从左到右）输出所有节点值。

**核心思路**：标准广度优先搜索（BFS）。用队列，先入头节点；每次弹出一个打印，再把它的左右孩子（非空）入队。队列的 FIFO 特性天然保证逐层、从左到右访问。

**关键代码 / 步骤**：
```java
Queue<Node> queue = new LinkedList<>();
queue.add(head);
while (!queue.isEmpty()) {
    Node cur = queue.poll(); print(cur);
    if (cur.left != null)  queue.add(cur.left);
    if (cur.right != null) queue.add(cur.right);
}
```

**复杂度**：时间 O(N)，空间 O(N)（队列最多存一层节点，最坏 N/2）。

**易错点 / 面试提示**：
- 若题目要求「分层返回」（每层一个 list），需在循环开头记录 `size = queue.size()` 再循环这么多次。
- 头节点为空要先返回。

---

### class11 / Code02_SerializeAndReconstructTree.java —— 二叉树的序列化与反序列化

**题目描述**：把二叉树编码成字符串序列，再能用该序列重建出结构与值完全相同的树。代码实现了先序、后序、层序三种序列化/反序列化，并用百万次对数器验证三者一致。**中序无法用于序列化**（不同结构可能产生相同的补空中序）。

**核心思路**：序列化的关键是**用 null 占位**记录空节点，这样才能唯一还原结构。
- 先序：递归输出「值, 左, 右」，重建时同样按队列顺序「建头→建左→建右」递归 poll。
- 后序：序列化输出「左, 右, 值」；重建时把队列倒进栈（变成「值, 右, 左」），再按「建头→建右→建左」递归。
- 层序：BFS 时把每个节点的左右孩子（含 null）都写入；重建时用队列，每弹出一个节点就从序列连续取两个作为它的左右孩子。

**关键代码 / 步骤**：
```java
// 先序序列化
void pres(Node head, Queue<String> ans) {
    if (head == null) ans.add(null);
    else { ans.add(""+head.value); pres(head.left, ans); pres(head.right, ans); }
}
// 先序重建
Node preb(Queue<String> q) {
    String v = q.poll();
    if (v == null) return null;
    Node head = new Node(Integer.valueOf(v));
    head.left = preb(q); head.right = preb(q);
    return head;
}
```

**复杂度**：序列化/反序列化均 时间 O(N)，空间 O(N)。

**易错点 / 面试提示**：
- **中序不能序列化**（注释里的两棵树补空后中序都是 {null,1,null,2,null}），是高频考点。
- 必须显式存储 null 占位，否则结构无法唯一还原。
- 后序重建用「倒序入栈 + 先右后左」，方向要和序列化对称。

---

### class11 / Code03_EncodeNaryTreeToBinaryTree.java —— 多叉树编码为二叉树（LeetCode 431）

**题目描述**：把一棵 N 叉树编码成二叉树，并能解码还原回原 N 叉树，编解码须互逆。

**核心思路**：经典「**左孩子右兄弟**」表示法。把某节点的所有孩子链表，挂到二叉树中：**第一个孩子放在 left，其余兄弟依次通过 right 串联**。即二叉树节点的 left 指向「第一个孩子」，right 指向「下一个兄弟」。解码时反过来：沿二叉树 left 走是孩子，沿 right 走是兄弟，递归收集成 children 列表。

**关键代码 / 步骤**：
```java
// encode: 把孩子列表串成 左孩子右兄弟
private TreeNode en(List<Node> children) {
    TreeNode head = null, cur = null;
    for (Node child : children) {
        TreeNode t = new TreeNode(child.val);
        if (head == null) head = t; else cur.right = t; // 兄弟接 right
        cur = t;
        cur.left = en(child.children);                  // 孩子接 left
    }
    return head;
}
// decode: 沿 right 取兄弟，沿 left 递归取孩子
List<Node> de(TreeNode root) {
    List<Node> children = new ArrayList<>();
    while (root != null) { children.add(new Node(root.val, de(root.left))); root = root.right; }
    return children;
}
```

**复杂度**：编码/解码均 时间 O(N)，空间 O(H)。

**易错点 / 面试提示**：
- 核心记忆点：**left=第一个孩子，right=下一个兄弟**。
- 根节点没有兄弟，所以 encode 的根 `head.right` 始终为 null，孩子全挂在 `head.left` 一支上。
- 编解码必须严格对称，否则无法还原。

---

### class11 / Code04_PrintBinaryTree.java —— 直观打印二叉树结构

**题目描述**：把二叉树以横向、带层级缩进的方式打印到控制台，便于调试时直观看出结构（这是一个工具/对数器类，而非算法题）。

**核心思路**：基于**中序遍历的变体**：先递归打印右子树，再打印当前节点，最后递归打印左子树（右子在上、左子在下，整体顺时针旋转 90 度看即为正常树）。用 `height * len` 控制缩进表示层级；节点值两侧用 `H`（头）、`v`（右孩子，箭头朝下）、`^`（左孩子，箭头朝上）标记其相对父节点的位置。

**关键代码 / 步骤**：
```java
void printInOrder(Node head, int height, String to, int len) {
    if (head == null) return;
    printInOrder(head.right, height + 1, "v", len);   // 右子在上
    String val = to + head.value + to;                 // to: H/v/^
    // 居中补空格，前面再加 height*len 个空格表示层级
    System.out.println(getSpace(height * len) + 居中(val));
    printInOrder(head.left, height + 1, "^", len);    // 左子在下
}
```

**复杂度**：时间 O(N)，空间 O(H)。

**易错点 / 面试提示**：
- 这是调试利器，建议背下来放工具箱，面试做题时手画/打印树很有用。
- `len=17` 固定列宽，值过长会错位；标记符 `^ v H` 帮助判断节点是左孩子、右孩子还是头。

---

### class11 / Code05_TreeMaxWidth.java —— 求二叉树最大宽度（每层节点数最大值）

**题目描述**：给定二叉树，返回所有层中节点数最多的那一层的节点数（最大宽度，这里按实际节点计数，不含空位）。

**核心思路**：层序 BFS 统计每层节点数取最大。两种实现：
1. `maxWidthUseMap`：用 `HashMap<Node,层号>` 记录每个节点所在层，遇到层号变化就结算上一层宽度。
2. `maxWidthNoMap`（推荐）：不用哈希表，O(1) 额外变量。维护 `curEnd`（当前层最右节点）和 `nextEnd`（下一层最右节点）。BFS 中每入队一个孩子就更新 nextEnd；当弹出的节点 == curEnd 说明本层结束，结算并把 curEnd 切到 nextEnd。

**关键代码 / 步骤**：
```java
Node curEnd = head, nextEnd = null;
int max = 0, curLevelNodes = 0;
while (!queue.isEmpty()) {
    Node cur = queue.poll();
    if (cur.left != null)  { queue.add(cur.left);  nextEnd = cur.left;  }
    if (cur.right != null) { queue.add(cur.right); nextEnd = cur.right; }
    curLevelNodes++;
    if (cur == curEnd) {                 // 本层走完
        max = Math.max(max, curLevelNodes);
        curLevelNodes = 0;
        curEnd = nextEnd;
    }
}
```

**复杂度**：两法均 时间 O(N)，空间 O(N)（NoMap 法不算队列则 O(1) 额外）。

**易错点 / 面试提示**：
- NoMap 法核心是「用每层最右节点做分层信号」，比哈希表更省空间，是面试加分实现。
- 注意 nextEnd 在遍历当前层时不断被覆盖，最终停在下一层真正的最右节点。
- LeetCode 同名题（662）按满二叉树下标算宽度（含空位），本题按实际节点数，注意区分。

---

### class11 / Code06_SuccessorNode.java —— 中序遍历的后继节点（含 parent 指针）

**题目描述**：节点结构带 parent 指针。给定某节点，返回它在中序遍历中的后继节点（紧随其后被访问的节点），不存在返回 null。要求不遍历整棵树。

**核心思路**：分两种情况：
1. **有右子树**：后继是右子树的最左节点（`getLeftMost(node.right)`）。
2. **无右子树**：沿 parent 往上走，直到「某节点是其父亲的左孩子」，该父亲即后继；若一直走到 null（说明是整棵树中序的最后一个），返回 null。

**关键代码 / 步骤**：
```java
if (node.right != null) {
    return getLeftMost(node.right);       // 右子树最左
} else {
    Node parent = node.parent;
    while (parent != null && parent.right == node) { // 一直是右孩子就向上
        node = parent; parent = node.parent;
    }
    return parent;                        // 第一次成为左孩子时的父亲
}
```

**复杂度**：时间 O(H)，空间 O(1)。

**易错点 / 面试提示**：
- 利用 parent 指针只走相关路径，O(H) 而非 O(N)，这是本题精髓。
- 中序最后一个节点（整棵树最右节点）后继为 null，注意循环出口。
- 对称题：求「前驱节点」逻辑相反（有左子树取左子树最右；否则向上找第一个「自己是右孩子」的父亲）。

---

### class11 / Code07_PaperFolding.java —— 折纸问题（凹凸折痕的中序输出）

**题目描述**：一张纸条从下往上对折 N 次后展开，从上到下打印每条折痕是「凹（down）」还是「凸（up）」。例如 N=1 打印「凹」，N=2 打印「凹 凹 凸」。

**核心思路**：把折痕想象成一棵**满二叉树**：头节点（第 1 次折）是凹；任意节点的**左孩子是凹、右孩子是凸**。第 i 次对折对应树的第 i 层，从上到下打印折痕 = 对这棵想象的树做**中序遍历**。无需真的建树，递归时直接传 `down` 标志即可。

**关键代码 / 步骤**：
```java
// i: 当前层；down: 当前折痕是否为凹
void process(int i, int N, boolean down) {
    if (i > N) return;
    process(i + 1, N, true);              // 左子树：凹
    System.out.print(down ? "凹 " : "凸 "); // 中序位置打印当前
    process(i + 1, N, false);             // 右子树：凸
}
// 入口：第一条折痕是凹
process(1, N, true);
```

**复杂度**：时间 O(2^N)（折痕总数为 2^N - 1），空间 O(N)（递归深度）。

**易错点 / 面试提示**：
- 关键洞见：左孩子恒凹、右孩子恒凸，头节点凹——把物理问题抽象成满二叉树中序遍历。
- 不用真正建树，递归参数携带 down 即可，是「想象中的树」典型例子。
- 折痕总数是 2^N - 1，N 较大时数量指数级增长。

---

> 复习建议：class09 重点是链表的「快慢指针、原地反转、三区分挂、节点穿插」四类技巧；class10 串起链表相交的完整分类讨论与遍历框架；class11 围绕二叉树「BFS 分层 / 序列化占空 / 左孩子右兄弟 / parent 找后继 / 抽象成树」展开，均为高频面试题。
