# 算法面试复习资料 Part 7（class24 - class28）

主题覆盖：滑动窗口最大/最小值更新结构、单调栈、矩阵快速幂、KMP、Manacher。

---

## class24 滑动窗口及其更新结构的应用

滑动窗口最大/最小值更新结构（用双端队列维护单调性）是本章的核心工具，反复在多道题中复用。

### class24 / Code01_SlidingWindowMaxArray.java —— 滑动窗口最大值数组

**题目描述**：给定整型数组 `arr` 和窗口大小 `w`（1 ≤ w ≤ arr.length）。窗口从左向右每次滑动一格，求每个窗口内的最大值，输出长度为 `arr.length - w + 1` 的结果数组。

**核心思路**：用一个双端队列 `qmax` 存放下标，维护"队头到队尾对应的值从大到小"的单调递减结构。新元素入队时，从队尾弹出所有不大于它的下标（它们不可能再成为最大值），保证单调；队头永远是当前窗口最大值的下标。每滑动一步，检查队头是否过期（下标等于 `R - w`，即已滑出窗口左边界）。当 `R >= w - 1` 时开始收集队头对应的值。

**关键代码 / 步骤**：
```java
for (int R = 0; R < arr.length; R++) {
    while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[R]) qmax.pollLast();
    qmax.addLast(R);
    if (qmax.peekFirst() == R - w) qmax.pollFirst(); // 队头过期
    if (R >= w - 1) res[index++] = arr[qmax.peekFirst()];
}
```

**复杂度**：时间 O(N)（每个下标最多进队、出队各一次），空间 O(w)。

**易错点 / 面试提示**：
- 队列里存的是**下标**而非值，这样才能判断过期。
- 弹出条件用 `<=`（相等也弹），便于过期判断唯一化。
- 收集结果的时机判断 `R >= w - 1`，否则前几个不完整窗口会被错误输出。

---

### class24 / Code02_AllLessNumSubArray.java —— 达标子数组的数量

**题目描述**：给定数组 `arr` 和整数 `sum`。若一个子数组满足 `max - min <= sum`（最大值减最小值不超过 sum），称为达标子数组。求达标子数组的总个数。

**核心思路**：关键性质——若子数组 `[L, R]` 达标，则其任意子区间也达标；若 `[L, R]` 不达标，则任意包含它的区间也不达标。因此用滑动窗口：固定左边界 L，找到从 L 出发能向右扩到的最远不越界的 R，则以 L 开头的达标子数组有 `R - L` 个。同时维护两个双端队列分别求窗口最大值（递减队列）和最小值（递增队列）。L 右移时把过期的队头弹掉。R 单调不回退，所以总体线性。

**关键代码 / 步骤**：
```java
for (int L = 0; L < N; L++) {
    while (R < N) {
        // 更新 maxWindow（递减）、minWindow（递增），加入 R
        if (arr[maxWindow.peekFirst()] - arr[minWindow.peekFirst()] > sum) break;
        else R++;
    }
    count += R - L;
    if (maxWindow.peekFirst() == L) maxWindow.pollFirst();
    if (minWindow.peekFirst() == L) minWindow.pollFirst();
}
```

**复杂度**：时间 O(N)，空间 O(N)。

**易错点 / 面试提示**：
- 核心要先证明"达标具有单调性"，否则无法用窗口。
- R 在内层循环里不回退是 O(N) 的关键，不要把 R 重置回 L。
- L 右移时只有当队头下标恰等于 L 时才弹出。

---

### class24 / Code03_GasStation.java —— 加油站环形出发点

**题目描述**：LeetCode gas station。环形路上有 N 个加油站，`gas[i]` 是站 i 的油量，`cost[i]` 是从站 i 到站 i+1 的耗油。从某站出发顺时针走一圈，要求全程油箱不为负。返回能走完整圈的出发站下标（保证唯一），不存在返回 -1。

**核心思路**：令 `diff[i] = gas[i] - cost[i]`。把数组扩成 2 倍模拟环形并求前缀和 `arr`。从站 i 出发能完整走完，等价于在长度为 N 的窗口 `[i, i+N-1]` 内，任意前缀和（相对于起点偏移 `offset`）都 ≥ 0，即窗口内**最小前缀和减去起点前缀偏移 ≥ 0**。用滑动窗口最小值结构（递增队列）在 2N 前缀和数组上滑动，逐个判断每个起点是否"good"。

**关键代码 / 步骤**：
```java
// arr[i] = 前缀和（diff 翻倍后）
// 维护窗口内最小前缀和下标的递增队列 w
for (offset=0, i=0, j=N; j<M; offset=arr[i++], j++) {
    if (arr[w.peekFirst()] - offset >= 0) ans[i] = true; // 起点 i 可行
    if (w.peekFirst() == i) w.pollFirst();
    // 把 j 加入递增队列
}
```

**复杂度**：时间 O(N)，空间 O(N)。

**易错点 / 面试提示**：
- 注意 `offset` 是上一个起点的前缀和，用来把窗口最小前缀和"归零"到当前起点视角。
- 这是 O(N) 的窗口解；存在更简洁的贪心 O(N) 单变量解法，但本实现展示窗口最小值结构的通用性。
- 环形问题用"数组翻倍"标准套路处理。

---

### class24 / Code04_MinCoinsOnePaper.java —— 每张钱币只能用一次的最少货币数（窗口优化背包）

**题目描述**：`arr` 是一组货币，每个元素是一张钱币的面值（**同面值可重复出现，但每张只能用一次**）。求凑出目标金额 `aim` 所需的最少张数，凑不出返回 `Integer.MAX_VALUE`（或视为无解）。

**核心思路**：本质是 0/1 背包 / 计数背包的优化。
- `process`/`dp1`：朴素 0/1 背包，每张钱币选或不选，O(N×aim)。
- `dp2`：先把货币按面值合并成 `(coins[], zhangs[])`（面值 + 张数），做"有限个数的多重背包"，枚举每种面值用几张，O(种数 × aim × 平均张数)。
- `dp3`（推荐）：在 dp2 基础上，按面值 `coin` 把 rest 按模分组（`mod, mod+coin, mod+2coin...`），同一组内转移是"窗口内最小值"问题，用单调队列把枚举张数那一维优化掉，降到 O(种数 × aim)。

**关键代码 / 步骤**（dp3 核心思想）：
```java
for (int mod = 0; mod < min(aim+1, c[i]); mod++) {
  LinkedList<Integer> w; // 维护 dp[i+1][...] + 补偿张数 的最小值
  for (int r = mod + c[i]; r <= aim; r += c[i]) {
    // 入队时比较 dp[i+1][last]+compensate vs dp[i+1][r]
    // overdue = r - c[i]*(z[i]+1) 过期（超过这种面值张数限制）
    dp[i][r] = dp[i+1][w.peekFirst()] + compensate(w.peekFirst(), r, c[i]);
  }
}
// compensate(pre, cur, coin) = (cur - pre)/coin，即从 pre 走到 cur 多用的张数
```

**复杂度**：dp1 O(N×aim)；dp2 O(种数×aim×平均张数)；dp3 O(N + 种数×aim)，空间 O(种数×aim)。

**易错点 / 面试提示**：
- 货币大量重复时 dp3 远优于 dp2；货币几乎不重复时 dp2 常数更小。
- dp3 的"过期"是按张数限制 `z[i]`：超过 `z[i]+1` 个步长的下标作废。
- 入队比较要带 `compensate` 补偿（不同位置到 r 的张数差不同），不能直接比 dp 值。

---

## class25 单调栈

单调栈用于求每个元素"左边/右边最近的比它小（或大）的元素位置"，是直方图类、子矩阵类问题的基础。

### class25 / Code01_MonotonousStack.java —— 单调栈求左右最近更小值

**题目描述**：给定数组 `arr`，对每个位置 i，求它左边最近的比 `arr[i]` 小的元素下标和右边最近的比 `arr[i]` 小的元素下标（不存在记 -1）。返回 `res[i] = {leftLessIndex, rightLessIndex}`。提供两版：无重复值版与有重复值版。

**核心思路**：维护一个"从栈底到栈顶值递增"的栈，存下标。当遍历到 `arr[i]` 比栈顶小，弹出栈顶 j：此时 i 就是 j 右边最近更小值，弹出后新的栈顶就是 j 左边最近更小值。无重复值用单值栈即可；有重复值时栈里每个位置存一个"相同值的下标链表"，弹出时整条链表共享同一对左右答案，而左边界取该链表里最后一个下标。

**关键代码 / 步骤**：
```java
while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
    int j = stack.pop();
    int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
    res[j][0] = leftLessIndex; res[j][1] = i;
}
stack.push(i);
// 遍历完，栈中剩余元素右边无更小值，置 -1
```

**复杂度**：时间 O(N)，空间 O(N)。

**易错点 / 面试提示**：
- 有重复值时，左边界必须取相同值组里"最后压入"的那个下标（即 `stack.peek().get(size-1)`），否则结果错误。
- 弹空后栈为空说明左边无更小值，记 -1。
- 遍历结束后还要清算栈中残留元素（它们右边无更小值）。

---

### class25 / Code02_AllTimesMinToMax.java —— 子数组(累加和 × 最小值)的最大值

**题目描述**：给定**正数**数组 `arr`，对每个子数组计算"子数组累加和 × 子数组最小值"，求所有子数组中这个指标的最大值。

**核心思路**：枚举每个位置 `arr[j]` 作为"最小值"。用单调栈求出以 `arr[j]` 为最小值能向左右扩张的最大范围（左右最近的更小值之间），该范围内累加和用前缀和快速求出，乘以 `arr[j]` 即为以它为最小值的最优指标。对所有 j 取最大。因为是正数，扩得越宽和越大，所以最优范围就是最大扩张范围。

**关键代码 / 步骤**：
```java
while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
    int j = stack.pop();
    int sum = stack.isEmpty() ? sums[i-1] : sums[i-1] - sums[stack.peek()];
    max = Math.max(max, sum * arr[j]); // arr[j] 是该范围最小值
}
stack.push(i);
```

**复杂度**：时间 O(N)，空间 O(N)。

**易错点 / 面试提示**：
- 仅对**正数数组**成立（区间越大和越大）。
- 弹出用 `>=`（相等也弹）不会漏解，因为相等元素只是边界归属问题，最终都会被正确计算。
- 累加和用前缀和数组 `sums`，注意空栈时左边界处理。

---

### class25 / Code03_LargestRectangleInHistogram.java —— 直方图最大矩形面积

**题目描述**：LeetCode 84。给定非负整数数组 `height` 表示直方图各柱高度（每柱宽 1），求能勾勒出的最大矩形面积。

**核心思路**：枚举每根柱子 `height[j]` 作为矩形的高，矩形能向左右扩张的范围由"左右最近的更矮柱子"界定。用单调栈（值递增）维护，弹出 j 时：右边界是当前 i，左边界是弹出后的新栈顶 k，宽度 `i - k - 1`，面积 `(i-k-1)*height[j]`。提供 Stack 版和数组模拟栈版（更快）。

**关键代码 / 步骤**：
```java
while (!stack.isEmpty() && height[i] <= height[stack.peek()]) {
    int j = stack.pop();
    int k = stack.isEmpty() ? -1 : stack.peek();
    maxArea = Math.max(maxArea, (i - k - 1) * height[j]);
}
stack.push(i);
// 收尾时右边界用 height.length
```

**复杂度**：时间 O(N)，空间 O(N)。

**易错点 / 面试提示**：
- 宽度公式 `i - k - 1`（开区间两端都是更矮的，不计入）。
- 弹出用 `<=` 保证相等柱也被处理，最终面积仍正确（相等柱会在后续位置算到完整宽度）。
- 数组模拟栈（`largestRectangleArea2`）省去 Stack 装箱开销，面试可提常数优化。

---

### class25 / Code04_MaximalRectangle.java —— 0/1 矩阵最大全 1 矩形

**题目描述**：LeetCode 85。给定字符矩阵 `map`（'0'/'1'），求其中只含 '1' 的最大矩形面积。

**核心思路**：逐行处理，把每行作为"地基"，维护以该行为底的直方图高度数组 `height`：若当前位置是 '1' 则 `height[j]++`，是 '0' 则置 0。对每一行得到的直方图调用"直方图最大矩形面积"（Code03 的方法）。所有行的结果取最大。本质是把二维问题降维成 N 次一维直方图问题。

**关键代码 / 步骤**：
```java
for (int i = 0; i < map.length; i++) {
    for (int j = 0; j < map[0].length; j++)
        height[j] = map[i][j] == '0' ? 0 : height[j] + 1;
    maxArea = Math.max(maxRecFromBottom(height), maxArea); // 单调栈
}
```

**复杂度**：时间 O(行×列)，空间 O(列)。

**易错点 / 面试提示**：
- 关键转化：以每行为底构建累积高度直方图，遇 0 清零。
- `height` 数组跨行复用，不要每行重新分配。
- `maxRecFromBottom` 即直方图最大矩形单调栈解法。

---

### class25 / Code05_CountSubmatricesWithAllOnes.java —— 全 1 子矩阵的数量

**题目描述**：LeetCode 1504。给定 0/1 矩阵 `mat`，统计其中所有元素都为 1 的子矩阵（含所有大小）的总数量。

**核心思路**：同样逐行构建以当前行为底的直方图 `height`（遇 0 清零）。难点在于"以当前行为底边"的全 1 子矩阵计数 `countFromBottom`。仍用单调栈，弹出柱 `cur` 时只在 `height[cur] > height[i]` 时结算（避免重复计数）：在宽度 `n = i - left - 1` 内，高度从 `down+1` 到 `height[cur]` 的每一层，宽为 n 的连续段贡献 `num(n) = n*(n+1)/2` 个矩形，乘以高度层数 `(height[cur]-down)`。`down` 取左右更矮值的较大者，保证只统计"以本行为底、之前未统计过"的那部分高度。

**关键代码 / 步骤**：
```java
while (si != -1 && height[stack[si]] >= height[i]) {
    int cur = stack[si--];
    if (height[cur] > height[i]) {
        int left = si == -1 ? -1 : stack[si];
        int n = i - left - 1;
        int down = Math.max(left == -1 ? 0 : height[left], height[i]);
        nums += (height[cur] - down) * num(n); // num(n)=n*(n+1)/2
    }
}
// num(n) 是宽度 n 的一行里"连续子段"个数
```

**复杂度**：时间 O(行×列)，空间 O(列)。

**易错点 / 面试提示**：
- 只在 `height[cur] > height[i]` 时结算，且 `down = max(左更矮值, height[i])`，这是不重不漏计数的关键。
- `num(n)=n(n+1)/2` 表示宽度 n 内所有连续宽度的子矩形（一层）数量。
- 必须以"每行为底"分别统计，避免与上方行重复。

---

## class26 矩阵快速幂（线性递推加速）

凡是"严格的 k 阶常系数线性递推"，都可写成矩阵乘法，用快速幂把 O(N) 加速到 O(logN)。

### class26 / Code01_SumOfSubarrayMinimums.java —— 所有子数组最小值之和

**题目描述**：LeetCode 907。给定数组 `arr`，对每个子数组取其最小值，求所有子数组最小值的总和，结果对 1e9+7 取模。

**核心思路**：对每个 `arr[i]`，统计它作为最小值出现在多少个子数组中：设 `left[i]` 为左边最近"小于等于"它的下标，`right[i]` 为右边最近"严格小于"它的下标。则以 i 为最小值的子数组数为 `(i-left[i]) * (right[i]-i)`，贡献 `arr[i] * 该数量`。左右采用"一边取等、一边严格"的不对称定义，正好避免相等元素重复计数。`left/right` 用单调栈 O(N) 求出。

**关键代码 / 步骤**：
```java
for (int i = 0; i < arr.length; i++) {
    long start = i - left[i];   // 左边可选起点数
    long end = right[i] - i;    // 右边可选终点数
    ans += start * end * (long) arr[i];
    ans %= 1000000007;
}
// nearLessEqualLeft: 从右往左单调栈，弹出条件 arr[i] <= arr[peek]
// nearLessRight:     从左往右单调栈，弹出条件 arr[peek] >  arr[i]
```

**复杂度**：时间 O(N)，空间 O(N)。

**易错点 / 面试提示**：
- 左右"等号归属"必须不对称（一边 `<=`，一边 `<`），否则相等元素会重复或遗漏。
- 用 long 累加并及时取模，防溢出。
- LeetCode 上只需提交 `sumSubarrayMins`（单调栈最优解）。注：本文件按主题归在 class26，但本质仍是单调栈题。

---

### class26 / Code02_FibonacciProblem.java —— 斐波那契及同类递推的矩阵快速幂

**题目描述**：求斐波那契数列第 n 项 `f(n)`；以及变体 `s(n)`（s1=1,s2=2,s(n)=s(n-1)+s(n-2)）和 `c(n)`（c1=1,c2=2,c3=3,c(n)=c(n-1)+c(n-3)）。要求 O(logN)。

**核心思路**：任何严格线性递推都能矩阵化。斐波那契满足 `[f(n),f(n-1)] = [f(n-1),f(n-2)] × base`，其中 `base = {{1,1},{1,0}}`，故 `f(n)` 可由 `base^(n-2)` 求出。用快速幂求矩阵幂：把指数二进制分解，`res` 初始为单位矩阵，按位累乘。三阶递推 `c(n)` 用 3×3 的 base 矩阵 `{{1,1,0},{0,0,1},{1,0,0}}`。每题都给了递归(O(2^n))、迭代(O(n))、矩阵快速幂(O(logn)) 三版作对照。

**关键代码 / 步骤**：
```java
int[][] matrixPower(int[][] m, int p) {
    int[][] res = 单位矩阵;
    int[][] t = m;
    for (; p != 0; p >>= 1) {
        if ((p & 1) != 0) res = muliMatrix(res, t);
        t = muliMatrix(t, t);
    }
    return res;
}
// f(n) = base^(n-2)，取 res[0][0]+res[1][0]
```

**复杂度**：f1/s1/c1 指数级；f2/s2/c2 O(N)；f3/s3/c3 O(logN)（矩阵规模为常数）。空间 O(1)（常数大小矩阵）。

**易错点 / 面试提示**：
- 矩阵快速幂仅适用于**严格的常系数线性递推**（无额外项）。
- base 矩阵阶数 = 递推阶数；指数是 `n - 递推阶数` 要对齐基准项。
- `res` 初始化为单位矩阵（对角线 1）是快速幂的"乘法单位元"。

---

### class26 / Code03_ZeroLeftOneStringNumber.java —— 满足"0 左边必有 1"的长度为 n 的 01 串个数

**题目描述**：求长度为 n 的 0/1 字符串中，满足"每个 0 的左边都必须紧跟一个 1（即不以 0 开头、不出现连续 00）"约束的字符串个数。

**核心思路**：用递归 `process(i, n)` 分析：当前位若放 1，下一位可自由（走到 i+1）；若放 0，则它左边必须是 1，相当于跳到 i+2。推导出递推 `f(n) = f(n-1) + f(n-2)`——本质是斐波那契。给出三版：`getNum1` 暴力递归、`getNum2` 迭代 O(n)、`getNum3` 矩阵快速幂 O(logn)。

**关键代码 / 步骤**：
```java
int process(int i, int n) {
    if (i == n - 1) return 2;
    if (i == n)     return 1;
    return process(i+1, n) + process(i+2, n); // 斐波那契式递推
}
// getNum3: base={{1,1},{1,0}}, res=base^(n-2), 返回 2*res[0][0]+res[1][0]
```

**复杂度**：getNum1 指数级；getNum2 O(N)；getNum3 O(logN)。空间 O(1)。

**易错点 / 面试提示**：
- 关键是把组合约束翻译成"放 1 走一步、放 0 走两步"的递推，识别出斐波那契。
- 矩阵快速幂版本的基准项系数（`2*res[0][0]+res[1][0]`）要按初始值对齐。

---

## class27 KMP 算法

KMP 用 next 数组（每个前缀的最长相等前后缀长度）在不回退主串指针的前提下实现 O(N+M) 子串匹配。

### class27 / Code01_KMP.java —— KMP 字符串匹配

**题目描述**：在主串 `s1` 中查找模式串 `s2` 第一次出现的起始下标，不存在返回 -1（等价于 `indexOf`）。

**核心思路**：先求 `s2` 的 next 数组：`next[i]` 表示 `s2[0..i-1]` 这个前缀的"最长相等前缀后缀"的长度（也就是失配时模式串指针应跳回的位置）。匹配时主串指针 x 永不回退：字符相等则 x、y 同进；失配且 `next[y] != -1` 则把 y 跳到 `next[y]`（复用已匹配前缀）；失配且 `next[y]==-1`（即 y=0）则 x 前进一格。next 数组构造本身也用"自我匹配"：`cn` 记录当前最长前后缀长度，匹配成功则 next[i]=++cn，失配则 cn 回跳 `next[cn]`。

**关键代码 / 步骤**：
```java
// 构造 next
next[0]=-1; next[1]=0; int i=2, cn=0;
while (i < next.length) {
    if (str2[i-1]==str2[cn]) next[i++]=++cn;
    else if (cn>0) cn=next[cn];
    else next[i++]=0;
}
// 匹配
while (x<str1.length && y<str2.length) {
    if (str1[x]==str2[y]) { x++; y++; }
    else if (next[y]==-1) x++;
    else y=next[y];
}
return y==str2.length ? x-y : -1;
```

**复杂度**：构造 next O(M)，匹配 O(N)，总 O(N+M)。空间 O(M)。

**易错点 / 面试提示**：
- `next[0]=-1` 是哨兵，专门用于"y=0 仍失配"时让主串前进。
- 构造 next 时失配回跳 `cn=next[cn]`，是 KMP 最精妙也最易写错处。
- next[i] 的含义是 `s2[0..i-1]` 的最长相等前后缀长度，理解这点才能解释为什么失配可以跳。

---

### class27 / Code02_TreeEqual.java —— 二叉树是否包含另一棵树（子树匹配）

**题目描述**：给定二叉树 `big` 和 `small`，判断 small 是否是 big 的子树（值与结构都完全一致的子树）。small 为 null 视为 true。

**核心思路**：两种解法。
- `containsTree1`（暴力）：以 big 每个节点为根尝试 `isSameValueStructure` 完全匹配，O(N×M)。
- `containsTree2`（KMP，推荐）：把 big 和 small 都按"前序遍历 + null 占位"序列化成字符串数组，则 small 是 big 子树 ⟺ small 的序列是 big 序列的子串（含 null 的序列化保证唯一性）。然后对两个 String[] 跑 KMP（与 Code01 同构，只是比较单元从 char 换成带 null 判断的 String），O(N+M)。

**关键代码 / 步骤**：
```java
void pres(Node head, ArrayList<String> ans) {
    if (head == null) ans.add(null);
    else { ans.add(""+head.value); pres(head.left, ans); pres(head.right, ans); }
}
// 序列化后用 KMP（isEqual 处理 null）匹配
return getIndexOf(bigSerial, smallSerial) != -1;
```

**复杂度**：containsTree1 O(N×M)；containsTree2 O(N+M)（序列化 + KMP）。空间 O(N+M)。

**易错点 / 面试提示**：
- 前序序列化**必须带 null 占位**，否则不同结构可能得到相同序列，匹配会误判。
- KMP 的比较单元变成 String，要用 `isEqual` 正确处理 null 相等。
- 这是"树问题转字符串匹配"的经典套路。

---

### class27 / Code03_IsRotation.java —— 判断两字符串是否互为旋转词

**题目描述**：给定两字符串 a、b，判断 b 是否是 a 的旋转词（把 a 在某处切开、前后两段交换得到 b）。长度不同直接 false。

**核心思路**：经典结论——b 是 a 的旋转词 ⟺ a 是 `b+b` 的子串（且长度相等）。a 的任何旋转都出现在 b 拼接自身后的串中。于是构造 `b2 = b + b`，用 KMP 判断 a 是否为 b2 的子串即可。

**关键代码 / 步骤**：
```java
public static boolean isRotation(String a, String b) {
    if (a == null || b == null || a.length() != b.length()) return false;
    String b2 = b + b;
    return getIndexOf(b2, a) != -1; // KMP 子串查找
}
```

**复杂度**：时间 O(N)（KMP），空间 O(N)（b2 与 next 数组）。

**易错点 / 面试提示**：
- 必须先判长度相等，否则 `b+b` 含子串不代表旋转。
- 用 `b+b`（拼自身）查 a 是否在其中，方向别搞反。
- 朴素 `indexOf` 也能过，但面试要点是会用 KMP 达到 O(N)。

---

## class28 Manacher 算法

Manacher 在 O(N) 内求最长回文子串，核心是回文半径数组与"对称复用"。

### class28 / Code01_Manacher.java —— 最长回文子串长度（Manacher）

**题目描述**：给定字符串 s，返回其最长回文子串的长度。

**核心思路**：先把串处理成两侧及间隔都插入 `#` 的形式（`"aba" -> "#a#b#a#"`），长度变为 `2n+1`，使所有回文统一为奇数长度，省去奇偶讨论。维护回文半径数组 `pArr`、当前最右回文边界 `R` 及其对称中心 `C`。对每个 i：
1. 若 `i < R`，利用对称点 `2C-i` 的半径，`pArr[i]` 至少为 `min(pArr[2C-i], R-i)`（在已知边界内免比对）；否则初始为 1。
2. 在此基础上继续向两侧暴力扩。
3. 若扩出的右边界超过 R，更新 R、C。
最终最大半径减 1 即原串最长回文长度。

**关键代码 / 步骤**：
```java
pArr[i] = R > i ? Math.min(pArr[2*C-i], R-i) : 1;
while (i+pArr[i]<str.length && i-pArr[i]>-1 && str[i+pArr[i]]==str[i-pArr[i]])
    pArr[i]++;
if (i+pArr[i] > R) { R = i+pArr[i]; C = i; }
max = Math.max(max, pArr[i]);
// 返回 max - 1
```

**复杂度**：时间 O(N)（R 单调右移，总扩张次数线性），空间 O(N)。

**易错点 / 面试提示**：
- 三种情形：i 在 R 外、对称点回文在 R 内（直接照搬）、对称点回文出 R（取 R-i），代码用 `min` 统一处理。
- `#` 预处理使奇偶长度回文统一，`max-1` 正好换算回原串长度。
- R 在代码里是"最右成功位置的下一个"，比较时注意边界。

---

### class28 / Code02_AddShortestEnd.java —— 在末尾添加最少字符使整体回文

**题目描述**：给定字符串 s，只能在它**末尾**添加字符，求使整个字符串成为回文串所需添加的最少字符，返回添加后的最终回文串。

**核心思路**：要在末尾加最少字符变回文，等价于找到 s 中"包含最后一个字符的最长回文后缀"——这部分不用动，前面剩下的部分逆序补到末尾即可。用 Manacher 求半径，扩展过程中一旦回文右边界 `R` 第一次到达整个处理串末尾（`R == str.length`），此刻的 `pArr[i]` 对应的回文就是"以原串结尾的最长回文后缀"，记录 `maxContainsEnd` 并跳出。然后把回文后缀之前的字符逆序拼到末尾构造答案。

**关键代码 / 步骤**：
```java
// Manacher 扩展中：
if (R == str.length) { maxContainsEnd = pArr[i]; break; } // 第一次触达末尾
// 构造答案：把前面非回文部分逆序补到末尾
char[] res = new char[s.length() - maxContainsEnd + 1];
for (int i = 0; i < res.length; i++)
    res[res.length-1-i] = str[i*2+1]; // 取原串字符（跳过 #）逆序填充
```

**复杂度**：时间 O(N)，空间 O(N)。

**易错点 / 面试提示**：
- 关键洞察：找"包含结尾的最长回文后缀"，其余前缀逆序补到尾部。
- 一旦 `R` 触达末尾即可 break，此时 `pArr[i]` 给出后缀回文长度。
- 填充答案时 `str[i*2+1]` 用于跳过 `#` 取回原字符，注意下标换算。

---

（全文完）
