# 算法面试复习资料 Part 8 (class29 - class33)

涵盖主题：BFPRT 选择算法 / 蓄水池抽样、Morris 遍历、线段树 (Segment Tree)、树状数组 (Index Tree / BIT)、AC 自动机、哈希函数。

---

## class29 BFPRT 与蓄水池抽样

### class29 / Code01_FindMinKth.java —— 无序数组中第 k 小的数

**题目描述**：给定一个无序整型数组 `arr` 和正整数 `k`（1 <= k <= N），返回数组中第 k 小的数（k 从 1 开始计数，即第 1 小就是最小值）。提供三种解法并用对数器交叉验证。

**核心思路**：
- 解法一（`minKth1`）：用一个大小为 k 的大根堆。前 k 个数入堆，之后每个数若比堆顶小就替换堆顶，最终堆顶就是第 k 小。时间 O(N·logK)。
- 解法二（`minKth2`）：改写快排的 partition（荷兰国旗三向切分）做随机快速选择。每次随机选 pivot，partition 后得到等于区 `[range[0], range[1]]`，若目标 index 落在等于区直接返回，否则只递归一侧。期望 O(N)，最坏 O(N²)。
- 解法三（`minKth3`）：BFPRT 算法。把随机选 pivot 改成「中位数的中位数」(`medianOfMedians`)：每 5 个一组组内排序取中位数，组成 mArr，再递归对 mArr 求其中位数作为 pivot。这保证 pivot 能切掉至少约 3/10 的数，从而最坏也是 O(N)。

**关键代码 / 步骤**：
```java
public static int bfprt(int[] arr, int L, int R, int index) {
    if (L == R) return arr[L];
    int pivot = medianOfMedians(arr, L, R);   // 中位数的中位数做轴
    int[] range = partition(arr, L, R, pivot); // 三向切分，返回等于区[左,右]
    if (index >= range[0] && index <= range[1]) return arr[index];
    else if (index < range[0]) return bfprt(arr, L, range[0]-1, index);
    else return bfprt(arr, range[1]+1, R, index);
}
// medianOfMedians: 每5个一组getMedian，组成mArr，再bfprt求mArr中位数
```

**复杂度**：堆法 时间 O(N·logK)，空间 O(K)；随机快选 期望时间 O(N)，最坏 O(N²)；BFPRT 最坏时间 O(N)，空间 O(logN) 递归栈。

**易错点 / 面试提示**：
- BFPRT 的最坏 O(N) 来自「中位数的中位数」选轴策略，普通随机快选只有期望 O(N)，这是面试高频考点。
- partition 用三向（less/more/cur）能把所有等于 pivot 的数聚成一块，避免大量重复值退化。
- 「第 k 小」要把 k 转成下标 `k-1` 再传入。

---

### class29 / Code02_MaxTopK.java —— 返回数组中最大的 k 个数（有序）

**题目描述**：给定无序数组 `arr` 和 `k`，返回数组中最大的 k 个数，且结果按从大到小排好序。若 k > N 则取 N 个。三种实现交叉验证。

**核心思路**：
- 方法一（`maxTopK1`）：整体排序后取末尾 k 个，倒序收集。O(N·logN)。
- 方法二（`maxTopK2`）：原地建大根堆（自底向上 heapify，O(N)），然后弹 k 次堆顶放到数组末尾，再倒着收集前 k 大。O(N + K·logN)。
- 方法三（`maxTopK3`）：先用快速选择求出第 (N-k) 小的数 `num`（即第 k 大的分界值），扫描数组把所有严格大于 num 的放入答案、不足部分用 num 补齐，最后对 k 个排序倒置。O(N + K·logK)，是最优解。

**关键代码 / 步骤**：
```java
int num = minKth(arr, N - k);        // O(N) 快速选择求分界值（第N-k小）
for (int i = 0; i < N; i++)
    if (arr[i] > num) ans[index++] = arr[i];  // 严格大于的先收集
for (; index < k; index++) ans[index] = num;  // 等于分界值的补满到k个
Arrays.sort(ans);                    // O(K*logK)
// 再左右交换实现降序
```

**复杂度**：方法三 时间 O(N + K·logK)，空间 O(K)。

**易错点 / 面试提示**：
- 用「大于 num 的先收集 + 用 num 补满」处理重复值，避免分界值数量不确定带来的越界。
- 只对最终的 k 个排序而非全数组排序，是 O(N+K·logK) 优于 O(N·logN) 的关键。

---

### class29 / Code03_ReservoirSampling.java —— 蓄水池抽样

**题目描述**：数据流不断吐出元素，事先不知道总量。要求始终在内存中保留 N 个样本，使得任意时刻每个已出现的元素被保留的概率都相等（等概率抽样）。

**核心思路**：维护容量为 N 的蓄水池。前 N 个元素直接入袋；从第 i 个（i > N）开始，以概率 N/i 决定是否「入袋」，若入袋则随机等概率地替换掉袋中某一个位置。可以证明任意时刻每个元素留在袋中的概率均为 N/(当前总数)。main 中用大量重复实验统计各球出现次数，验证分布近似均匀。

**关键代码 / 步骤**：
```java
public void add(int num) {
    count++;
    if (count <= N) {
        bag[count - 1] = num;            // 前N个直接放入
    } else {
        if (rand(count) <= N) {          // 以 N/count 概率决定入袋
            bag[rand(N) - 1] = num;      // 随机替换袋中一个位置
        }
    }
}
```

**复杂度**：每个元素 O(1) 处理，整体 时间 O(总数)，空间 O(N)。

**易错点 / 面试提示**：
- 关键概率有两个：第 i 个元素「以 N/i 概率被选中」，被选中后「等概率 1/N 替换某位置」，两者缺一不可。
- 数学归纳证明「每个元素最终留存概率 = N/总数」是面试常被追问的点。
- 适用于流式数据、内存受限、总量未知的随机抽样场景。

---

## class30 Morris 遍历

### class30 / Code01_MorrisTraversal.java —— Morris 遍历（O(1) 空间二叉树遍历）

**题目描述**：用 O(1) 额外空间（不用递归栈、不用显式栈）实现二叉树的先序、中序、后序遍历，并附带判断是否为搜索二叉树 (BST)。

**核心思路**：Morris 遍历利用叶子节点空闲的右指针建立「回链」(thread)。对当前节点 cur：若有左子树，找到左子树最右节点 mostRight；① 若 mostRight.right 为空，说明第一次到达 cur，建立回链 `mostRight.right = cur`，然后 `cur = cur.left`；② 若 mostRight.right 已指向 cur，说明第二次到达 cur，拆掉回链，`cur = cur.right`。无左子树则直接 `cur = cur.right`。由此每个有左子树的节点会被访问两次，无左子树节点一次。
- **先序**：第一次到达节点时打印（含无左子树节点）。
- **中序**：在 `cur = cur.right` 之前打印（即第二次到达或唯一一次到达时）。
- **后序**：只在「第二次到达」时，逆序打印该节点左子树的右边界；遍历结束后逆序打印整棵树的右边界。`printEdge` 通过反转链表实现逆序打印再反转回来，保持 O(1) 空间。
- **isBST**：在中序框架下记录前驱 pre，若出现 `pre >= cur.value` 则不是 BST。

**关键代码 / 步骤**：
```java
while (cur != null) {
    mostRight = cur.left;
    if (mostRight != null) {
        while (mostRight.right != null && mostRight.right != cur)
            mostRight = mostRight.right;     // 找左子树最右节点
        if (mostRight.right == null) {       // 第一次到达
            mostRight.right = cur;           // 建回链
            cur = cur.left; continue;
        } else {                             // 第二次到达
            mostRight.right = null;          // 拆回链（后序在此 printEdge(cur.left)）
        }
    }
    cur = cur.right;                         // 中序在此之前打印
}
```

**复杂度**：时间 O(N)（找最右边界的总开销摊还为线性），空间 O(1)。

**易错点 / 面试提示**：
- Morris 的本质是「用叶子的空右指针临时记忆回溯路径」，遍历后必须把回链全部拆除，否则破坏树结构。
- 中序最简单（直接利用回链顺序），后序最复杂（需逆序打印右边界）。
- 内层 while 的判断条件 `mostRight.right != cur` 用来区分有无回链，是正确性核心。

---

### class30 / Code05_MinHeight.java —— 二叉树的最小深度

**题目描述**：给定二叉树头节点，返回从根到「最近叶子节点」的最短路径上的节点数（最小深度）。叶子是左右孩子都为空的节点。提供递归与 Morris 两种解法对数器验证。

**核心思路**：
- 递归法（`minHeight1/p`）：若为叶子返回 1；否则只在存在的子树中取最小深度（不存在的子树记为 MAX，避免「单边子树」被当成叶子误算）。
- Morris 法（`minHeight2`）：用 Morris 中序框架在 O(1) 空间下完成。借助 `rightBoardSize`（左子树右边界长度）维护 `curLevel`（当前节点所在层）。每当「第二次到达」一个节点且其左孩子为空（说明刚走完一条右边界，遇到一个叶子分支）时用 curLevel 更新答案，并回退 curLevel；最后单独处理整棵树最右一条边界上的叶子。

**关键代码 / 步骤**：
```java
if (mostRight.right == null) {     // 第一次到达
    curLevel++;
    mostRight.right = cur; cur = cur.left; continue;
} else {                           // 第二次到达
    if (mostRight.left == null)    // 左子树最右节点是叶子
        minHeight = Math.min(minHeight, curLevel);
    curLevel -= rightBoardSize;    // 回退层数
    mostRight.right = null;
}
// 结束后单独沿整棵树右边界走到底，若是叶子再更新 minHeight
```

**复杂度**：递归法 时间 O(N) 空间 O(树高)；Morris 法 时间 O(N) 空间 O(1)。

**易错点 / 面试提示**：
- 递归时「只有一个孩子」的节点不是叶子，不能用 `1 + min(left, right)` 直接算（会把空子树当成 0 深度），所以缺失子树要置为 MAX。
- Morris 版用 curLevel 加减来推算每个节点的层，回退量是「左子树右边界长度」rightBoardSize，这是难点。
- 最右边界上的叶子在主循环里不会被「第二次到达」触发，必须收尾单独处理。

---

## class31 线段树 (Segment Tree)

### class31 / Code01_SegmentTree.java —— 支持区间增加 / 区间更新 / 区间求和的线段树

**题目描述**：给定数组，支持三种区间操作：`add(L,R,C)` 区间内每个数加 C；`update(L,R,C)` 区间内每个数都改成 C；`query(L,R)` 求区间累加和。用暴力 `Right` 类对数器验证。

**核心思路**：线段树用数组 `sum[]` 模拟（大小开 4N）。核心是两类懒标记：`lazy[]`（待下发的累加任务）和 `update[]/change[]`（待下发的更新任务）。任务若完全覆盖当前区间，就只更新该节点 sum 并打上懒标记直接返回（不再递归到底）；任务部分覆盖时先 `pushDown` 把懒标记分发给左右孩子再递归。`pushDown` 中**更新标记优先于累加标记**（更新会清空已有 lazy），且发给孩子时要按子区间元素个数 ln/rn 计算贡献。`pushUp` 由两个孩子的 sum 合并出父 sum。

**关键代码 / 步骤**：
```java
private void pushDown(int rt, int ln, int rn) {
    if (update[rt]) {                 // 更新优先
        update[rt<<1]=update[rt<<1|1]=true;
        change[rt<<1]=change[rt<<1|1]=change[rt];
        lazy[rt<<1]=lazy[rt<<1|1]=0;  // 更新会清掉累加任务
        sum[rt<<1]=change[rt]*ln;  sum[rt<<1|1]=change[rt]*rn;
        update[rt]=false;
    }
    if (lazy[rt]!=0) {                 // 再下发累加
        lazy[rt<<1]+=lazy[rt];  sum[rt<<1]+=lazy[rt]*ln;
        lazy[rt<<1|1]+=lazy[rt]; sum[rt<<1|1]+=lazy[rt]*rn;
        lazy[rt]=0;
    }
}
// add/update/query: 若 L<=l && r<=R 全覆盖 -> 打标记/直接返回；否则 pushDown 后递归
```

**复杂度**：build O(N)，单次 add/update/query 时间 O(logN)，空间 O(4N)。

**易错点 / 面试提示**：
- 懒标记数组必须开 **4N** 大小（保证完全二叉树编号不越界）。
- pushDown 中必须先处理 update 再处理 lazy，且 update 会清空孩子的 lazy；顺序写反会出错。
- 下发时要乘以子区间元素个数 `ln`/`rn`，区间约定从 1 开始（`arr[0]` 不用）。

---

### class31 / Code02_FallingSquares.java —— 掉落的方块 (LeetCode 699)

**题目描述**：二维平面上从左往右依次掉落若干正方形，`positions[i] = [left, sideLength]`。每落一个方块后返回当前所有已落方块叠成的图形的最高高度，返回一个高度列表。

**核心思路**：把方块占据的 x 区间映射为线段树下标——先用 TreeSet 离散化所有出现过的左右端点坐标（`index` 方法），把稀疏坐标压成 `1..N` 的连续下标。线段树这里维护**区间最大值**并支持**区间更新**（用 max + update/change 懒标记）。对每个方块：先 query 它落点区间内当前最大高度 h，则它落下后顶端高度 = h + 边长，用这个高度去 update 该区间（区间赋值为新顶高），同时维护全局 max 加入答案。

**关键代码 / 步骤**：
```java
for (int[] arr : positions) {
    int L = map.get(arr[0]);
    int R = map.get(arr[0] + arr[1] - 1);          // 离散化后的区间下标
    int height = segmentTree.query(L,R,1,N,1) + arr[1]; // 区间当前最高 + 边长
    max = Math.max(max, height);
    res.add(max);
    segmentTree.update(L, R, height, 1, N, 1);     // 把该区间整体抬到 height
}
```

**复杂度**：离散化 O(M·logM)，每个方块查询+更新 O(logN)，总 时间 O(M·logM)，空间 O(N)。

**易错点 / 面试提示**：
- 必须离散化坐标，否则线段树范围爆炸；用右端点 `left + side - 1`（闭区间）避免相邻方块边界重叠误判。
- 此处线段树维护的是「区间最大值 + 区间赋值」，与求和版本不同（update 直接把 max 设为 change，无需乘个数）。
- 答案是「至今全局最高」，要用 running max，而不是单个方块的高度。

---

## class32 树状数组与 AC 自动机

### class32 / Code01_IndexTree.java —— 树状数组（单点更新 + 前缀和）

**题目描述**：实现支持「单点增加 `add(index,d)`」和「前缀和查询 `sum(index)`」的数据结构，下标从 1 开始。用暴力 `Right` 类对数器验证。

**核心思路**：树状数组 (Binary Indexed Tree / Fenwick Tree)。核心是 `lowbit(x) = x & (-x)`，取出 x 二进制最低位的 1 所代表的值。`tree[i]` 管辖以 i 结尾、长度为 lowbit(i) 的区间和。查询前缀和时不断 `index -= lowbit(index)` 向左跳累加；更新时不断 `index += lowbit(index)` 向上更新所有覆盖该点的节点。

**关键代码 / 步骤**：
```java
public int sum(int index) {           // 前缀和 [1..index]
    int ret = 0;
    while (index > 0) { ret += tree[index]; index -= index & -index; }
    return ret;
}
public void add(int index, int d) {   // 单点加
    while (index <= N) { tree[index] += d; index += index & -index; }
}
```

**复杂度**：单次 add / sum 时间 O(logN)，空间 O(N)。区间和 = sum(R) - sum(L-1)。

**易错点 / 面试提示**：
- 下标必须从 1 开始，0 会让 `index & -index` 死循环。
- `lowbit = index & (-index)`（补码取最低位 1）是核心，sum 向下减、add 向上加方向不能搞反。
- 相比线段树，树状数组代码极短、常数小，但原生只支持单点改+前缀和（区间改需配合差分技巧）。

---

### class32 / Code02_IndexTree2D.java —— 二维树状数组（LeetCode 308 二维区域和检索-可变）

**题目描述**：给定可变矩阵，支持单点更新 `update(row,col,val)` 和子矩阵区域和查询 `sumRegion(r1,c1,r2,c2)`。（LeetCode 308，提交时类名改为 NumMatrix。）

**核心思路**：在二维上嵌套树状数组。`tree[i][j]` 用两层 lowbit 跳跃维护一个矩形区域和。`sum(row,col)` 返回左上角 (0,0) 到 (row,col) 的矩形前缀和（行、列各用一层 `i -= i&-i` 循环）。`update` 用 `add = val - 旧值` 做增量，行列各用 `i += i&-i` 向上更新。区域和用二维前缀和的容斥：`sum(r2,c2) - sum(r1-1,c2) - sum(r2,c1-1) + sum(r1-1,c1-1)`。

**关键代码 / 步骤**：
```java
private int sum(int row, int col) {   // (0,0)~(row,col) 矩形前缀和
    int s = 0;
    for (int i = row+1; i > 0; i -= i & -i)
        for (int j = col+1; j > 0; j -= j & -j)
            s += tree[i][j];
    return s;
}
public int sumRegion(int r1,int c1,int r2,int c2) {
    return sum(r2,c2) + sum(r1-1,c1-1) - sum(r1-1,c2) - sum(r2,c1-1);
}
```

**复杂度**：单次 update / sum 时间 O(logN·logM)，空间 O(N·M)。

**易错点 / 面试提示**：
- 树状数组下标从 1 开始，所以内部用 `row+1 / col+1` 偏移，外部接口用 0 基坐标。
- update 必须用增量 `val - nums[row][col]`，并同步更新 `nums` 缓存原值。
- 区域和的二维容斥公式（加对角、减两条边）是经典考点。

---

### class32 / Code03_AC1.java —— AC 自动机（统计文章命中的敏感词种类数）

**题目描述**：给定若干匹配串（模式串）和一篇文章，统计文章中一共命中了多少个不同的匹配串（每种匹配串只算一次）。

**核心思路**：AC 自动机 = 前缀树 (Trie) + KMP 的 fail 指针。① `insert` 把每个模式串建进 Trie，结尾节点 `end++`。② `build` 用 BFS 设置每个节点的 fail 指针：节点 X 的 fail 指向「X 的父 fail 链上第一个有相同字符孩子的那个孩子」，找不到则指向 root。fail 指针表示「当前匹配失败时，能复用的最长后缀」。③ `containNum` 沿文章逐字符走：当前字符走不通就沿 fail 跳；到达节点后沿 fail 链「向上收集」所有 end 标记（每个匹配串结尾），用 `end = -1` 去重避免重复统计。

**关键代码 / 步骤**：
```java
// build: BFS 设置 fail
cur.nexts[i].fail = root;
cfail = cur.fail;
while (cfail != null) {
    if (cfail.nexts[i] != null) { cur.nexts[i].fail = cfail.nexts[i]; break; }
    cfail = cfail.fail;
}
// match:
while (cur.nexts[index]==null && cur!=root) cur = cur.fail; // 失配跳fail
cur = cur.nexts[index]!=null ? cur.nexts[index] : root;
follow = cur;
while (follow != root) {                 // 沿 fail 链收集所有命中
    if (follow.end == -1) break;         // 已统计过的剪枝
    ans += follow.end; follow.end = -1;  // 去重
    follow = follow.fail;
}
```

**复杂度**：建树 O(所有模式串总长)，build O(节点数·字符集)，匹配 O(文章长度 + 命中次数)；空间 O(节点数·26)。

**易错点 / 面试提示**：
- fail 指针的含义是「最长可复用后缀」，build 必须用 BFS（层序），保证求子 fail 时父及更上层的 fail 已就绪。
- 匹配时每到一个节点都要沿 fail 链向上把所有「结尾节点」收完（一个位置可能同时命中多个串，如 "she" 含 "he"）。
- 用 `end = -1` 剪枝既去重又能在沿 fail 链遇到已访问节点时提前 break，避免重复遍历。

---

### class32 / Code04_AC2.java —— AC 自动机（返回文章命中的具体敏感词列表）

**题目描述**：给定若干模式串和文章，返回文章中实际出现的所有模式串（具体字符串内容，每个只返回一次）。是 Code03 的「返回内容」版本。

**核心思路**：结构与 AC1 完全一致（Trie + fail 指针 + BFS build）。差别在节点存储：用 `String end` 直接保存以该节点结尾的完整模式串（而非计数），用布尔 `endUse` 标记该串是否已加入答案以去重。匹配时沿 fail 链向上，遇到 `end != null` 且未使用过就把字符串加入结果并置 `endUse = true`；遇到已使用的节点就 break 剪枝。

**关键代码 / 步骤**：
```java
follow = cur;
while (follow != root) {
    if (follow.endUse) break;          // 已收集过，剪枝
    if (follow.end != null) {          // 该节点是某个模式串结尾
        ans.add(follow.end);
        follow.endUse = true;          // 标记已收集
    }
    follow = follow.fail;
}
```

**复杂度**：建树 O(模式串总长)，build O(节点数·26)，匹配 O(文章长度 + 命中次数)，空间 O(节点数·26)。

**易错点 / 面试提示**：
- 与 AC1 唯一区别是「收集内容 vs 计数」，end 从 int 计数改成 String 实串、再加 endUse 去重标记。
- 收集逻辑放在沿 fail 链遍历的循环里，「不同需求改这一段」即可改造成统计次数、找位置等变体。
- 同一节点 endUse 一旦置真就可以 break，是去重 + 性能双重保障。

---

## class33 哈希

### class33 / Hash.java —— 哈希函数 (MessageDigest) 演示

**题目描述**：演示如何使用 JDK 自带的 `MessageDigest` 计算字符串的哈希摘要（如 SHA / MD5），把字节摘要转成十六进制字符串输出，并打印当前环境支持的所有摘要算法。

**核心思路**：通过 `MessageDigest.getInstance(algorithm)` 获取指定算法的摘要器；`digest(bytes)` 得到字节数组摘要，再逐字节用 `String.format("%02X", b)` 拼成大写十六进制串。哈希函数的核心性质：输入相同输出必相同；输入哪怕差一个字符，输出几乎完全不同（雪崩效应）；输出在值域上近似均匀分布。main 中用 5 个仅末位不同的字符串展示输出差异巨大。

**关键代码 / 步骤**：
```java
public String hashCode(String input) {
    byte[] digest = hash.digest(input.getBytes());
    StringBuilder sb = new StringBuilder();
    for (byte b : digest)
        sb.append(String.format("%02X", b));  // 字节转两位十六进制
    return sb.toString();
}
// Security.getAlgorithms("MessageDigest") 列出所有可用算法
```

**复杂度**：时间 O(输入长度)，空间 O(摘要长度)（固定，如 SHA-1 为 20 字节）。

**易错点 / 面试提示**：
- 哈希函数三大性质：确定性、均匀分布、雪崩效应（输入微小变化导致输出大幅变化），是设计哈希表 / 一致性哈希 / 布隆过滤器的基础。
- 字节转十六进制要用 `%02X` 补零，否则小于 0x10 的字节会丢一位。
- 工程中可用「同一哈希函数对值域取模」实现把数据均匀分配到多台机器（一致性哈希、分库分表的思想来源）。

---
