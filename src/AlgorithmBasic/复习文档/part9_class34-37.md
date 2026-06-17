# 算法面试复习资料 Part9（class34 - class37）

本部分覆盖：资源限制类题目方法论（ReadMe）、AVL 树、Size-Balanced Tree（SB 树）、跳表，以及三种平衡有序表结构的对比测试与有序表的若干经典应用（区间和计数、滑动窗口中位数、按下标增删查的高效 List）。

---

## class34 资源限制类题目方法论

### class34 / ReadMe.java —— 资源限制类题目（无代码）

**题目描述**：本文件没有任何算法实现，只是一段说明。它解释为什么本章不放代码：资源限制类题目（例如「给定多少内存/多少机器/多少次访问限制，如何完成某个统计或查找」）的输入条件极多、工程实现量巨大，在面试里这类题目通常只需要和面试官口头交流解法思路，不会真的要求写出完整代码。

**核心思路（方法论总结）**：资源限制类题目的核心是「在约束下做权衡」，常见套路与思考维度：
1. 哈希函数把大数据均匀分流到多个文件/机器（分而治之），相同 key 一定进同一份，分别处理后再汇总。
2. 用 BitMap / 布隆过滤器 / HyperLogLog 等概率或位运算结构，用极小空间换取「存在性、去重、基数估计」等功能（可接受一定误判率）。
3. 用堆 / 外排序处理 TopK、全局排序，内存放不下就分块排序再多路归并。
4. 用「并查集 + 哈希分流」「位图分段统计 + 二分定位」等组合，在受限内存里定位「出现一次的数」「中位数」「未出现的最小数」等。
5. 时间与空间互换、精确与近似互换、单机与分布式互换——明确题目优先优化哪一项。

**关键代码 / 步骤**：无代码。记忆要点——面试时主动说清：数据量级、可用内存、是否允许误差、是否分布式，再据此选结构。

**复杂度**：取决于具体方案，常见为分流 O(N) + 各块内部处理。

**易错点 / 面试提示**：
- 不要上来写代码，先和面试官确认资源约束（内存大小、机器数、误判容忍度）。
- 布隆过滤器只会「误判存在」不会「漏判不存在」，要清楚其单向性与三个参数（n、p、k、位数组长度 m）的计算公式。
- 哈希分流的前提是相同 key 落到同一文件，这样才能分别独立处理。

---

## class35 AVL 树（高度平衡的有序表）

### class35 / Code01_AVLTreeMap.java —— AVL 平衡搜索树实现的有序表

**题目描述**：实现一个泛型有序表 `AVLTreeMap<K extends Comparable<K>, V>`，要求所有操作（put / get / remove / containsKey / firstKey / lastKey / floorKey / ceilingKey / size）在最坏情况下都达到 O(logN)。key 唯一（重复 put 视为改 value）。

**核心思路**：AVL 树是「高度平衡」的搜索二叉树——每个节点记录子树高度 h，要求任意节点左右子树高度差不超过 1。每次 add/delete 沿递归回溯路径更新 h 并调用 `maintain` 检查，若失衡（左右高度差 > 1）按四种情况旋转修复：
- LL（左孩子的左边过高）：对当前节点右旋；
- RR（右孩子的右边过高）：对当前节点左旋；
- LR（左孩子的右边过高）：先对左孩子左旋，再对当前节点右旋；
- RL（右孩子的左边过高）：先对右孩子右旋，再对当前节点左旋。
有序表查询用三个辅助方法实现：`findLastIndex`（命中或最后一个比较节点）、`findLastNoSmallIndex`（>=key 的最小，用于 ceiling）、`findLastNoBigIndex`（<=key 的最大，用于 floor）。

**关键代码 / 步骤**：
```java
private AVLNode maintain(AVLNode cur) {
    int lh = h(cur.l), rh = h(cur.r);
    if (Math.abs(lh - rh) > 1) {
        if (lh > rh) { // 左重
            if (h(cur.l.l) >= h(cur.l.r)) cur = rightRotate(cur);      // LL
            else { cur.l = leftRotate(cur.l); cur = rightRotate(cur); } // LR
        } else {       // 右重
            if (h(cur.r.r) >= h(cur.r.l)) cur = leftRotate(cur);       // RR
            else { cur.r = rightRotate(cur.r); cur = leftRotate(cur); } // RL
        }
    }
    return cur;
}
```
删除时若节点左右都有孩子，找右子树最左节点（后继）顶上来，再递归从右子树删后继。

**复杂度**：put/get/remove/floor/ceiling 均为时间 O(logN)；空间 O(N) 存储，递归栈 O(logN)。

**易错点 / 面试提示**：
- LL/RR 判断用 `>=`（左左高度 >= 左右高度时按 LL 处理），等号归类不要写错否则可能死循环或修复无效。
- 每次旋转后必须重新计算被影响节点的高度 h，顺序是先算下沉的旧头，再算上来的新头。
- AVL 旋转条件比 SB 树严格，所以查询略快、但增删旋转更频繁。

---

## class36 SB 树 与 跳表

### class36 / Code01_SizeBalancedTreeMap.java —— Size-Balanced Tree（SB 树）有序表

**题目描述**：实现泛型有序表 `SizeBalancedTreeMap<K, V>`，除支持 AVL 同样的有序表操作外，额外支持「按排名取节点」：`getIndexKey(index)` / `getIndexValue(index)`（返回第 index 小的 key/value，index 从 0 开始）。要求各操作 O(logN)。

**核心思路**：SB 树用每个节点的 `size`（子树节点数）作为平衡指标，平衡条件是：**叔叔节点的 size 不能小于侄子节点的 size**。即每次 add 后检查四种违规并旋转：
- LL：左孩子的左孩子 size > 右孩子 size → 右旋；
- LR：左孩子的右孩子 size > 右孩子 size → 左孩子左旋后整体右旋；
- RR：右孩子的右孩子 size > 左孩子 size → 左旋；
- RL：右孩子的左孩子 size > 左孩子 size → 右孩子右旋后整体左旋。
关键特点：`maintain` 在旋转后对「size 发生变化的子树」递归再 maintain（因为孩子换了位置可能产生新的违规），形成连锁修复。本实现的 `delete` 故意不调用 maintain（删除后暂时不修复，靠后续 add 的 maintain 摊还修复，这是 SB 树常见的工程简化）。旋转时 size 的维护：新头继承旧头的 size，旧头重算为左右子树 size 之和加 1。

**关键代码 / 步骤**：
```java
// 按排名取第 kth 小（kth 从 1 计）
private SBTNode getIndex(SBTNode cur, int kth) {
    int leftSize = cur.l != null ? cur.l.size : 0;
    if (kth == leftSize + 1) return cur;
    else if (kth <= leftSize) return getIndex(cur.l, kth);
    else return getIndex(cur.r, kth - leftSize - 1);
}
```

**复杂度**：put/get/remove/floor/ceiling/getIndex 时间均 O(logN)；空间 O(N)。

**易错点 / 面试提示**：
- SB 树的平衡靠 size 而非高度，旋转后必须对 size 改变的节点链式 maintain，漏掉会破坏平衡。
- 本实现删除不 maintain，是有意为之；面试可说明「删除后不立即调整，平衡性由后续插入摊还保证，整体仍 O(logN)」。
- 旋转里 size 赋值顺序固定：先让新头继承旧 size，再重算旧头 size，反了会算错。

### class36 / Code02_SkipListMap.java —— 跳表（Skip List）实现的有序表

**题目描述**：用跳表实现泛型有序表 `SkipListMap<K, V>`，支持 put / get / remove / containsKey / firstKey / lastKey / floorKey / ceilingKey / size，期望各操作 O(logN)。

**核心思路**：跳表是「多层有序链表」：第 0 层是包含全部节点的有序链表，越往上层节点越稀疏（每层是下层的「索引」）。每个节点用 `ArrayList<SkipListNode> nextNodes` 保存它在每一层的后继。有一个 key 为 null 的固定头节点（被视为最小）。查询从最高层头节点出发，每层尽量向右走到「最后一个 key < 目标」的节点，走不动就下沉一层，最终落到第 0 层即可定位。**层数靠随机决定**：新节点用 `while (Math.random() < 0.5) level++` 抛硬币累加层数（期望增高，使整体高度约为 logN），从而无需旋转就维持期望平衡。
- floorKey：找到最右的 <key 节点 less，其后继若等于 key 返回后继，否则返回 less.key；
- ceilingKey：直接返回 less 的后继 key；
- remove 时若某高层删空只剩头节点，则把该层从头节点移除并降低 maxLevel。

**关键代码 / 步骤**：
```java
// 在 level 层从 cur 出发，返回最后一个 key < 目标的节点
private SkipListNode mostRightLessNodeInLevel(K key, SkipListNode cur, int level) {
    SkipListNode next = cur.nextNodes.get(level);
    while (next != null && next.isKeyLess(key)) { cur = next; next = cur.nextNodes.get(level); }
    return cur;
}
// put 时决定新节点层数
int newNodeLevel = 0;
while (Math.random() < PROBABILITY) newNodeLevel++;
```

**复杂度**：put/get/remove/floor/ceiling 期望时间 O(logN)（依赖随机层数，非最坏保证）；空间期望 O(N)（额外索引指针期望 O(N)）。

**易错点 / 面试提示**：
- 头节点 key 为 null 被定义为「最小」，`isKeyLess` / `isKeyEqual` 对 null 的处理要专门写清，否则比较逻辑出错。
- 跳表是「期望」平衡而非最坏 O(logN)，但实现简单、无旋转、并发友好，是 Redis ZSet 的底层结构。
- 插入要从高层到低层一路记录每层的「前驱」并在 level <= newNodeLevel 的层把新节点接进去；删除同理每层断链。

---

## class37 三种平衡结构对比 与 有序表经典应用

### class37 / Compare.java —— AVL / SB 树 / 跳表 / TreeMap 对数器对比

**题目描述**：测试文件。以 JDK 的 `TreeMap` 作为标准答案（对数器），对 class35 的 AVLTreeMap、class36 的 SizeBalancedTreeMap、SkipListMap 三种自实现有序表做功能正确性验证与性能对比。

**核心思路**：`functionTest` 做百万次随机 put/remove/query，逐一比对四种结构的 containsKey、get、floorKey、ceilingKey、firstKey、lastKey、size 是否一致，不一致就打印 "Oops"。`performanceTest` 在「顺序递增 / 顺序递减 / 随机」三种插入删除模式下分别计时，对比四者耗时——其中顺序递增/递减正是会让普通 BST 退化成链的最坏输入，用来检验平衡性。

**关键代码 / 步骤**：
```java
treeMap.put(addK, addV); avl.put(addK, addV); sbt.put(addK, addV); skip.put(addK, addV);
if (treeMap.containsKey(q) != avl.containsKey(q) || ...) { System.out.println("Oops"); break; }
```

**复杂度**：测试框架本身 O(testTime × logN)。

**易错点 / 面试提示**：
- 对数器思想：用一个绝对正确（哪怕慢）的实现校验自己写的复杂结构，是手写数据结构的标准验证手法。
- 顺序递增插入是平衡树的「试金石」——能在该输入下保持 O(logN) 才说明平衡机制正确。

### class37 / Code01_CountofRangeSum.java —— 区间和的个数（LeetCode 327）

**题目描述**：给定整数数组 `nums` 和上下界 `lower`、`upper`，求有多少个连续子数组的元素和落在 `[lower, upper]` 区间内（闭区间）。返回该计数。

**核心思路**：转成前缀和问题：以 i 结尾、和在 `[lower,upper]` 的子数组个数 = 之前的前缀和中，落在 `[sum-upper, sum-lower]` 的个数（sum 为到 i 的前缀和）。文件给了两种解法：
1. `countRangeSum1`：归并排序版。对前缀和数组做归并，合并时用双指针统计左半每个 sums[i] 对应右半中满足 `lower <= sums[j]-sums[i] <= upper` 的数量（窗口 [k, j)）。
2. `countRangeSum2`：可计数的 SB 树版（`SizeBalancedTreeSet`，节点带 `size`=不同 key 数、`all`=含重复的总数）。边遍历边把前缀和加入树，每步用 `lessKeySize(x)` 查询「严格小于 x 的前缀和个数」，用 a-b 得到落在区间内的个数（a=小于 `sum-lower+1` 的，b=小于 `sum-upper` 的）。初始先 add(0) 表示空前缀。

**关键代码 / 步骤**：
```java
treeSet.add(0);
for (int i = 0; i < nums.length; i++) {
    sum += nums[i];
    long a = treeSet.lessKeySize(sum - lower + 1);
    long b = treeSet.lessKeySize(sum - upper);
    ans += a - b;
    treeSet.add(sum);
}
```

**复杂度**：两种解法均时间 O(N logN)，空间 O(N)。

**易错点 / 面试提示**：
- 前缀和可能溢出，用 long；本题正解之一就是「前缀和 + 有序结构计数」。
- SB 树版的关键是节点同时维护 `size`（去重后 key 数，用于平衡）和 `all`（含重复总数，用于计数），旋转时两者都要更新，`lessKeySize` 用 all 来累加。
- 归并版的双指针 k、j、t 都只增不减，保证合并 O(N)。

### class37 / Code02_SlidingWindowMedian.java —— 滑动窗口中位数（LeetCode 480）

**题目描述**：给定数组 `nums` 和窗口大小 `k`，窗口从左滑到右，每次返回当前窗口内 k 个数的中位数（窗口大小为偶数时取中间两数平均），输出长度为 `nums.length-k+1` 的 double 数组。

**核心思路**：用支持「按排名取第 index 小」的 SB 树维护当前窗口。由于数组可能有重复值，用 `Node(index, value)` 包装并以「value 优先、index 次之」比较，保证每个元素是唯一可比较 key（既能去重又能精确删除指定元素）。窗口每右移一位：加入新元素，按当前 size 奇偶用 `getIndexKey` 取中位（奇数取第 size/2 个，偶数取第 size/2-1 与 size/2 两个求平均），再删除最左元素。

**关键代码 / 步骤**：
```java
map.add(new Node(i, nums[i]));
if (map.size() % 2 == 0) {
    Node up = map.getIndexKey(map.size()/2 - 1);
    Node down = map.getIndexKey(map.size()/2);
    ans[idx++] = ((double)up.value + down.value) / 2;
} else {
    ans[idx++] = map.getIndexKey(map.size()/2).value;
}
map.remove(new Node(i - k + 1, nums[i - k + 1])); // 移出窗口最左
```

**复杂度**：时间 O(N logK)，空间 O(K)。

**易错点 / 面试提示**：
- 必须用 (value, index) 复合 key 处理重复值，否则相同值无法区分、删不准。
- 用 double 求平均防溢出（两个 Integer.MAX 相加会溢出 int）。
- 这是「有序表按下标查」的典型应用，普通堆做中位数难以高效删除任意元素，SB 树的 getIndex 是关键。

### class37 / Code03_AddRemoveGetIndexGreat.java —— 用 SB 树实现高性能 List（按下标增删查）

**题目描述**：实现一个 `SbtList<V>`，支持按任意下标 `add(index, value)` 插入、`get(index)` 读取、`remove(index)` 删除、`size()`，目标是让这三个操作都达到 O(logN)，优于 ArrayList/LinkedList 的 O(N)。

**核心思路**：用 SB 树充当「线性表」——这里的树**不按 value 大小排序，而是按下标位置组织**：节点在中序遍历中的位置就是它的逻辑下标。靠每个节点的 `size` 来定位下标：要在 index 处插入，就比较 index 与「左子树 size + 1」决定往左还是往右（往右时 index 要减去左子树和头）。插入/删除后照常用 SB 树的 `maintain` 旋转保持平衡。这样把数组的「按位置随机插入删除」从 O(N) 降到 O(logN)。

**关键代码 / 步骤**：
```java
private SBTNode add(SBTNode root, int index, SBTNode cur) {
    if (root == null) return cur;
    root.size++;
    int leftAndHead = (root.l != null ? root.l.size : 0) + 1;
    if (index < leftAndHead) root.l = add(root.l, index, cur);
    else root.r = add(root.r, index - leftAndHead, cur);
    return maintain(root);
}
private SBTNode get(SBTNode root, int index) {
    int leftSize = root.l != null ? root.l.size : 0;
    if (index < leftSize) return get(root.l, index);
    else if (index == leftSize) return root;
    else return get(root.r, index - leftSize - 1);
}
```
main 中用 ArrayList 做对数器校验功能，并对比 50 万次操作的插入/读取/删除耗时，证明 SbtList 在大量随机位置增删时优于 ArrayList。

**复杂度**：add/get/remove 时间均 O(logN)，空间 O(N)。

**易错点 / 面试提示**：
- 这里的 SB 树「不靠 key 排序，而靠 size 充当下标索引」，平衡仍用 size 违规判断与旋转，是 SB 树最巧妙的用途之一。
- 往右子树递归时下标要减去「左子树 size + 1」（扣掉左子树和当前头节点）。
- get 用 leftSize（不含头），add 用 leftAndHead（含头），两处 +1 的取舍要分清，写错会偏移一位。
- 面试亮点：ArrayList 中间插入/删除是 O(N) 搬移，LinkedList 定位是 O(N)，而本结构三操作都 O(logN)。
