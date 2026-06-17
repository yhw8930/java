# 算法面试复习资料 Part6：动态规划核心（class19 - class23）

> 本部分是动态规划的核心。绝大多数文件都遵循 **暴力递归（尝试模型）→ 记忆化搜索 → 严格表结构 DP → 进一步优化（枚举优化 / 斜率优化 / 空间压缩 / 位运算）** 的递进路线。
> 掌握 DP 的关键不在于背表，而在于：先写出**正确的暴力递归（可变参数即为表的维度）**，再机械地改写成填表。

---

## class19 从尝试到动态规划（背包 / 字符串解码 / 贴纸 / 最长公共子序列）

### class19 / Code01_Knapsack.java —— 0-1 背包（基础尝试模型）

**题目描述**：给定货物重量数组 `w[]`、价值数组 `v[]`（无负数，长度相等），以及背包载重 `bag`。每件货物只能选或不选，要求在总重量不超过 `bag` 的前提下，返回能获得的最大价值。

**核心思路**：这是从「尝试」推导到「动态规划」的入门模板。
- 可变参数两个：`index`（当前考察第几号货物，0~N）、`rest`（背包剩余容量），故对应**二维表**。
- 状态定义：`process(index, rest)` = 从 index 号货物开始往后自由选择、剩余容量为 rest 时能得到的最大价值。
- 转移：对每件货物有两种决策——不要（`p1 = process(index+1, rest)`）；要（`p2 = v[index] + process(index+1, rest - w[index])`，前提是装得下）。取较大者。
- base case：`index == N` 返回 0；`rest < 0` 返回 -1 表示非法（这种容量越界的判断用 -1 标记，回溯时丢弃）。
- 严格表 DP（`dp` 方法）：表 `dp[N+1][bag+1]`，依赖关系 `dp[index][rest]` 来自 `dp[index+1][...]`，所以 `index` 从 N-1 倒推到 0，`rest` 从 0 到 bag 正序填。最终答案 `dp[0][bag]`。

**关键代码 / 步骤**：
```java
for (int index = N - 1; index >= 0; index--) {
    for (int rest = 0; rest <= bag; rest++) {
        int p1 = dp[index + 1][rest];           // 不要 index
        int p2 = 0;
        int next = rest - w[index] < 0 ? -1 : dp[index + 1][rest - w[index]];
        if (next != -1) p2 = v[index] + next;   // 要 index
        dp[index][rest] = Math.max(p1, p2);
    }
}
return dp[0][bag];
```

**复杂度**：
- 暴力递归：时间 O(2^N)，空间 O(N)（递归栈）。
- 严格 DP：时间 O(N × bag)，空间 O(N × bag)。

**易错点 / 面试提示**：
1. 暴力递归用 `rest < 0` 返回 -1 标记非法，DP 中改为「越界则不取该决策」。
2. 这是无后效性问题的范例：相同 `(index, rest)` 结果固定，因此能改 DP。
3. 可进一步用一维滚动数组压缩到 O(bag)，但本文件未实现。

---

### class19 / Code02_ConvertToLetterString.java —— 数字串转字母（解码方法数，LeetCode 91）

**题目描述**：给定只含数字字符 0~9 的字符串 `str`。1→'A', 2→'B', ..., 26→'Z'。求把整个数字串转化成字母串的方法总数。

**核心思路**：典型「从左往右」一维 DP。
- 可变参数一个：`i`（当前要从 str[i] 开始转化），对应**一维表**。
- 状态定义：`process(i)` = str[i...] 的转化方法数。
- 转移（在 str[i] != '0' 前提下）：
  - i 单独转一个字符：`ways = process(i+1)`；
  - i 与 i+1 合起来转（前提是两位数 < 27，即 10~26）：`ways += process(i+2)`。
- base case：`i == len` 返回 1（一种完整方案）；若 `str[i] == '0'` 返回 0（0 不能单独成字母，前面的决策有误）。
- 严格 DP：`dp[N+1]`，`dp[N] = 1`，i 从 N-1 倒推到 0，答案 `dp[0]`。

**关键代码 / 步骤**：
```java
dp[N] = 1;
for (int i = N - 1; i >= 0; i--) {
    if (str[i] != '0') {
        int ways = dp[i + 1];
        if (i + 1 < N && (str[i]-'0')*10 + str[i+1]-'0' < 27)
            ways += dp[i + 2];
        dp[i] = ways;
    }
}
```

**复杂度**：暴力递归 O(2^N)；严格 DP 时间 O(N)，空间 O(N)（可压缩为 O(1) 三变量滚动）。

**易错点 / 面试提示**：
1. '0' 必须依附前一位（如 "10","20"），单独的 '0' 无效返回 0。
2. 两位数判断条件是 `< 27`（即 10~26 合法），27 及以上不能合并。
3. base case `dp[N]=1` 表示「恰好用完所有字符」是一种合法方案。

---

### class19 / Code03_StickersToSpellWord.java —— 贴纸拼词（LeetCode 691）

**题目描述**：给定贴纸数组 `stickers`（每种贴纸有无穷张，每张是一个小写字母字符串）和目标串 `target`。每次可用一张贴纸，剪下其中的字母来拼 target。求拼出 target 所需的**最少贴纸张数**，无法拼出返回 -1。

**核心思路**：状态是「当前还剩下的目标串」，无法用规整的 int 维度表示，因此用 **记忆化（HashMap）** 而非严格表 DP。文件给出三版递进：
- **process1（基础）**：状态是剩余 target 字符串。枚举第一张用哪种贴纸，用 `minus(target, sticker)` 减去该贴纸能覆盖的字母得到剩余串 rest；若 rest 长度变短（说明这张贴纸有用）则递归。`min + 1`。无法拼出返回 MAX。
- **process2（词频 + 剪枝优化）**：把每张贴纸预处理成 26 长度词频表 `counts[i][26]`，避免反复扫描字符串。**关键剪枝/贪心**：只尝试那些「含有 target 第一个字符」的贴纸（`sticker[target[0]-'a'] > 0`）。因为 target[0] 终归要被某张贴纸覆盖，固定它能极大削减分支。
- **process3（记忆化）**：在 process2 基础上加 `HashMap<String,Integer> dp` 缓存每个剩余串的最优解，`dp.put("", 0)` 为 base case。

**关键代码 / 步骤**：
```java
if (sticker[target[0] - 'a'] > 0) {          // 剪枝：必须覆盖 target 首字符
    StringBuilder builder = new StringBuilder();
    for (int j = 0; j < 26; j++)
        if (tcounts[j] > 0)
            for (int k = 0; k < tcounts[j] - sticker[j]; k++)
                builder.append((char)(j + 'a'));
    String rest = builder.toString();
    min = Math.min(min, process3(stickers, rest, dp));
}
return min + (min == Integer.MAX_VALUE ? 0 : 1);
```

**复杂度**：状态数随剩余串组合指数级，难给精确多项式上界。
- process1：最慢，每步都要构造串且无缓存。
- process2：词频化 + 首字符剪枝，大幅加速但仍可能重复计算。
- process3：加记忆化，相同剩余串只算一次，实际最快。

**易错点 / 面试提示**：
1. 核心剪枝：只用包含 target 首字符的贴纸（贪心思想 + 正确性保证）。
2. 用 26 长度词频表代替字符串比较，避免反复扫描。
3. `min == MAX` 时不能 `+1`（否则溢出且语义错误），需特判返回 -1。

---

### class19 / Code04_LongestCommonSubsequence.java —— 最长公共子序列（LeetCode 1143）

**题目描述**：给定两个字符串 `s1`、`s2`，返回它们最长公共子序列（不要求连续，但相对顺序一致）的长度。

**核心思路**：两个样本各做一个可变参数，**二维样本对应模型**。
- 状态定义：`process1(i, j)` = s1[0..i] 与 s2[0..j] 的最长公共子序列长度（注意本实现下标从末尾的「以 i、j 结尾的前缀」理解）。
- 转移分四类：
  - `p1 = process(i-1, j)`：不考虑 s1[i]；
  - `p2 = process(i, j-1)`：不考虑 s2[j]；
  - `p3 = str1[i]==str2[j] ? 1 + process(i-1, j-1) : 0`：同时用上 s1[i] 和 s2[j]（仅当字符相等）。
  - 取三者最大。
- base case：`i==0 && j==0` 看首字符是否相等；`i==0` 或 `j==0` 单行/单列要顺着比对填（因为前缀里只要出现过相等就为 1）。
- 严格 DP（`longestCommonSubsequence2`）：表 `dp[N][M]`，先填第一行第一列，再双层正序循环，答案 `dp[N-1][M-1]`。

**关键代码 / 步骤**：
```java
for (int i = 1; i < N; i++)
    for (int j = 1; j < M; j++) {
        int p1 = dp[i - 1][j];
        int p2 = dp[i][j - 1];
        int p3 = str1[i] == str2[j] ? (1 + dp[i - 1][j - 1]) : 0;
        dp[i][j] = Math.max(p1, Math.max(p2, p3));
    }
```

**复杂度**：暴力递归 O(2^(N+M))；严格 DP 时间 O(N×M)，空间 O(N×M)。

**易错点 / 面试提示**：
1. base case 的第一行/第一列不是简单赋 0/1，要「一旦匹配成 1 就保持」`dp[0][j] = str1[0]==str2[j] ? 1 : dp[0][j-1]`。
2. `p3` 只有字符相等时才有意义，相等时才能 `+1`。
3. 这是子序列问题的范式，后面回文子序列直接复用它。

---

## class20 区间 DP / 棋盘 DP / 贪心结合动态规划

### class20 / Code01_PalindromeSubsequence.java —— 最长回文子序列（LeetCode 516）

**题目描述**：给定字符串 `s`，返回它的最长回文子序列的长度。

**核心思路**：本文件给两套完全不同的解法。
- **区间 DP（lpsl1 / lpsl2）**：可变参数是区间端点 `L`、`R`，对应**二维表且只用上三角**。
  - 状态：`f(L, R)` = str[L..R] 内最长回文子序列长度。
  - 转移取四种最大：`f(L+1,R-1)`、`f(L,R-1)`、`f(L+1,R)`，以及当 `str[L]==str[R]` 时 `2 + f(L+1,R-1)`。
  - base case：`L==R` 返回 1；`L==R-1`（相邻两字符）返回相等则 2 否则 1。
  - 严格 DP 填表顺序：依赖左下方向，故对角线先填，再 `L` 从大到小、`R` 从小到大。
- **转化为 LCS（longestPalindromeSubseq1）**：把 s 反转得 reverse，s 与 reverse 的最长公共子序列即为最长回文子序列长度（经典转化技巧）。
- **longestPalindromeSubseq2**：与 lpsl2 相同的区间 DP（变量名换成 i、j）。

**关键代码 / 步骤**：
```java
for (int L = N - 3; L >= 0; L--)
    for (int R = L + 2; R < N; R++) {
        dp[L][R] = Math.max(dp[L][R - 1], dp[L + 1][R]);
        if (str[L] == str[R])
            dp[L][R] = Math.max(dp[L][R], 2 + dp[L + 1][R - 1]);
    }
return dp[0][N - 1];
```

**复杂度**：暴力区间递归 O(指数级)；区间 DP 时间 O(N²)，空间 O(N²)；LCS 解法同样 O(N²)。

**易错点 / 面试提示**：
1. 区间 DP 的填表方向是核心：`dp[L][R]` 依赖 `dp[L+1][R]`（下方）、`dp[L][R-1]`（左方）、`dp[L+1][R-1]`（左下），必须保证它们先算好。
2. 实际上 `f(L+1,R-1)` 已被 `f(L,R-1)`、`f(L+1,R)` 包含，可省（DP 版即省掉了 p1）。
3. 「反转 + LCS」是把回文问题转 LCS 的高频面试技巧。

---

### class20 / Code02_HorseJump.java —— 马走日（象棋马跳到指定点的方法数）

**题目描述**：在 10×9 的中国象棋棋盘上（行 0~9，列 0~8），马从 (0,0) 出发，必须**恰好走 k 步**，求落到目标点 (a,b) 的不同走法数。每步是马的 8 个「日」字方向之一。

**核心思路**：棋盘 + 步数，**三维表**（x, y, rest）。
- 状态：`process(x, y, rest)` = 当前在 (x,y)、还剩 rest 步、最终落到 (a,b) 的方法数。
- 转移：把 8 个马步方向的 `process(x±?, y±?, rest-1)` 累加。
- base case：越界返回 0；`rest==0` 时落点正好是 (a,b) 返回 1 否则 0。
- 严格 DP：`dp[10][9][k+1]`，先设 `dp[a][b][0]=1`，按 `rest` 从 1 到 k 逐层填（每层依赖 rest-1 层）。`pick/getValue` 辅助函数处理越界返回 0。
- 文件里 `jump/process` 与 `ways/f`、`dp/waysdp` 是同一思路的两份等价实现。

**关键代码 / 步骤**：
```java
dp[a][b][0] = 1;
for (int rest = 1; rest <= k; rest++)
    for (int x = 0; x < 10; x++)
        for (int y = 0; y < 9; y++) {
            int ways = pick(dp, x+2, y+1, rest-1) + pick(dp, x+1, y+2, rest-1)
                     + pick(dp, x-1, y+2, rest-1) + pick(dp, x-2, y+1, rest-1)
                     + pick(dp, x-2, y-1, rest-1) + pick(dp, x-1, y-2, rest-1)
                     + pick(dp, x+1, y-2, rest-1) + pick(dp, x+2, y-1, rest-1);
            dp[x][y][rest] = ways;
        }
return dp[0][0][k];
```

**复杂度**：暴力递归 O(8^k)；严格 DP 时间 O(10×9×k)，空间 O(10×9×k)。

**易错点 / 面试提示**：
1. 起点和终点是「反着设」的：DP 里把目标点 (a,b) 设为 rest=0 的种子，最终读 `dp[0][0][k]`（与递归方向相反，因对称等价）。
2. 越界统一用辅助函数返回 0，避免数组下标异常。
3. 三维表填表必须按 rest 分层，保证依赖的下一层已算好。

---

### class20 / Code03_Coffee.java —— 咖啡机洗杯子（贪心 + 动态规划）

**题目描述**：`arr[i]` 是第 i 台咖啡机冲一杯咖啡的时间（串行）；有 n 人要喝咖啡；喝完瞬间完成；只有一台洗杯机，洗一个杯子耗时 a（串行）；杯子自然挥发干净耗时 b（可并行）。从时间 0 开始，求所有人喝完且所有杯子都干净的最早时间点。

**核心思路**：两阶段——先用**贪心**确定每个人最早喝到咖啡的时间，再用 **DP** 安排洗杯方案。
- **第一阶段（贪心，小根堆）**：把每台机器封装成 (可用时间点 timePoint, 工作耗时 workTime)，按「timePoint + workTime」排小根堆。每个人取堆顶最快机器，做完后把机器的 timePoint 累加再放回堆。得到 `drinks[]`：每个人喝完（即杯子可以开始洗）的时间点。
- **第二阶段（洗杯 DP，bestTime → bestTimeDp）**：
  - 可变参数：`index`（当前处理第几个杯子）、`free`（洗杯机下次可用时间），对应**二维表**。
  - 状态：`bestTime(index, free)` = index 及之后所有杯子都干净的最早完成时间。
  - 转移：每个杯子两种决策——
    - 用洗杯机洗：`selfClean1 = max(drinks[index], free) + wash`，后续从 free=selfClean1 出发；该分支结果取 `max(selfClean1, restClean1)`。
    - 自然挥发：`selfClean2 = drinks[index] + air`，洗杯机 free 不变；取 `max(selfClean2, restClean2)`。
    - 两分支取 min。
  - base case：`index == len` 返回 0。
- **bestTimeDp**：把 free 维度离散化，`maxFree` 为洗杯机可能的最大忙到时间。`free` 越界（selfClean1 > maxFree）就 break。`right` 是纯暴力对数器。

**关键代码 / 步骤**：
```java
for (int index = N - 1; index >= 0; index--)
    for (int free = 0; free <= maxFree; free++) {
        int selfClean1 = Math.max(drinks[index], free) + wash;
        if (selfClean1 > maxFree) break;
        int p1 = Math.max(selfClean1, dp[index + 1][selfClean1]);   // 洗
        int selfClean2 = drinks[index] + air;
        int p2 = Math.max(selfClean2, dp[index + 1][free]);         // 挥发
        dp[index][free] = Math.min(p1, p2);
    }
return dp[0][0];
```

**复杂度**：
- right 暴力：极慢（机器分配 × 洗杯分配双重指数），仅作对数器。
- minTime1：贪心 O(n log m) + 洗杯暴力递归 O(2^n)。
- minTime2：贪心 O(n log m) + 洗杯 DP 时间 O(n × maxFree)、空间 O(n × maxFree)。

**易错点 / 面试提示**：
1. 关键洞察：喝咖啡阶段的最优用贪心（小根堆，谁先空闲谁先用）可证最优；洗杯阶段才需 DP。
2. 答案是「完成时间点」，所以每一步都对子过程结果取 `max`（瓶颈时间），最后对两种决策取 `min`。
3. `free` 维度上界 maxFree 要算准，否则越界；用 break 跳过非法的大 free 值。

---

## class21 从尝试改 DP 的方法论（路径 / 三类换钱 / 概率）

### class21 / Code01_MinPathSum.java —— 矩阵最小路径和（LeetCode 64）

**题目描述**：给定非负整数矩阵 `m`，从左上角走到右下角，每次只能向右或向下，路径上数字之和最小是多少。

**核心思路**：经典棋盘 DP，本文件直接给严格表 + 空间压缩两版。
- 状态：`dp[i][j]` = 从 (0,0) 走到 (i,j) 的最小路径和。
- 转移：`dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + m[i][j]`。
- base case：`dp[0][0]=m[0][0]`；第一行只能从左来，第一列只能从上来，前缀累加。
- **minPathSum2（空间压缩）**：观察到每个 `dp[i][j]` 只依赖正上方和正左方，用一维数组 `dp[col]` 滚动。处理第 i 行时，`dp[j]`（更新前）即为上一行同列值（上方），`dp[j-1]`（已更新）即为本行左方值；先 `dp[0] += m[i][0]` 处理首列。

**关键代码 / 步骤**：
```java
int[] dp = new int[col];
dp[0] = m[0][0];
for (int j = 1; j < col; j++) dp[j] = dp[j - 1] + m[0][j];
for (int i = 1; i < row; i++) {
    dp[0] += m[i][0];
    for (int j = 1; j < col; j++)
        dp[j] = Math.min(dp[j - 1], dp[j]) + m[i][j];
}
return dp[col - 1];
```

**复杂度**：均为时间 O(row×col)；二维空间 O(row×col)，一维压缩后空间 O(col)。

**易错点 / 面试提示**：
1. 空间压缩的关键：先更新左方依赖（dp[j-1] 是本行新值），dp[j] 未更新时恰是上方旧值。
2. 第一行第一列要单独初始化（只有一个来源方向）。
3. 任何「每格只依赖上方和左方」的二维 DP 都可压成一维。

---

### class21 / Code02_CoinsWayEveryPaperDifferent.java —— 每张面值都不同的换钱方法数

**题目描述**：`arr` 中每个数代表**一张**纸币（视为各不相同的个体，即使面值相同也是不同张）。求选出若干张正好凑出目标钱数 `aim` 的方法数。

**核心思路**：本质就是 0-1 选择模型（每张选或不选）。
- 状态：`process(index, rest)` = 用 arr[index...] 凑出 rest 的方法数。
- 转移：`process(index+1, rest)`（不要 index 这张）+ `process(index+1, rest - arr[index])`（要这张）。
- base case：`rest < 0` 返回 0；`index == N` 时 `rest == 0` 返回 1 否则 0。
- 严格 DP：`dp[N+1][aim+1]`，`dp[N][0]=1`，index 倒推，rest 正序。

**关键代码 / 步骤**：
```java
dp[N][0] = 1;
for (int index = N - 1; index >= 0; index--)
    for (int rest = 0; rest <= aim; rest++)
        dp[index][rest] = dp[index + 1][rest]
            + (rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : 0);
return dp[0][aim];
```

**复杂度**：暴力 O(2^N)；DP 时间 O(N×aim)，空间 O(N×aim)。

**易错点 / 面试提示**：
1. 与下一题（无限张）的区别：这里每张只能用一次，故只有「要/不要」两种决策，没有枚举张数的循环。
2. 重复面值的不同纸币算作不同方案（与「面值相同纸币不可区分」语义不同，见 Code04）。

---

### class21 / Code03_CoinsWayNoLimit.java —— 面值无限张的换钱方法数（完全背包）

**题目描述**：`arr` 是去重的面值数组，每种面值**张数无限**。求凑出 `aim` 的方法数。

**核心思路**：完全背包模型，重点是从「枚举张数」优化到「O(1) 转移」。
- 状态：`process(index, rest)` = 用 arr[index...] 任意张凑 rest 的方法数。
- **暴力/dp1（枚举张数）**：对 index 号面值枚举用 0,1,2,... 张，累加 `process(index+1, rest - zhang*arr[index])`。多了一层 zhang 循环。
- **dp2（枚举优化 / 斜率优化）**：观察 `dp[index][rest]` 与 `dp[index][rest - arr[index]]` 的展开式只差一项 `dp[index+1][rest]`，于是：
  `dp[index][rest] = dp[index+1][rest] + dp[index][rest - arr[index]]`，去掉了 zhang 循环。

**关键代码 / 步骤**：
```java
// dp2：枚举优化
dp[index][rest] = dp[index + 1][rest];
if (rest - arr[index] >= 0)
    dp[index][rest] += dp[index][rest - arr[index]];
```

**复杂度**：
- 暴力/dp1：时间 O(N × aim × (aim/最小面值))（多一层枚举），空间 O(N×aim)。
- dp2：时间 O(N×aim)，空间 O(N×aim)。

**易错点 / 面试提示**：
1. 完全背包的优化核心：用「本行左侧已算好的格子」`dp[index][rest-arr]` 替代整列枚举。
2. dp2 中 `dp[index][rest-arr[index]]` 用的是**当前行**（index 不变），代表「这种面值可继续用」；这正是与 0-1 背包（用 index+1 行）的本质区别。

---

### class21 / Code04_CoinsWaySameValueSamePapper.java —— 面值有限张的换钱方法数（多重背包）

**题目描述**：`arr` 中可能有重复面值，同面值的纸币视为不可区分。先统计成 (面值 coins[]、张数 zhangs[])，求凑出 `aim` 的方法数（每种面值最多用其拥有张数）。

**核心思路**：多重背包，先 `getInfo` 把数组压成「面值 + 张数」。
- 状态：`process(index, rest)` = 用 coins[index...] 凑 rest，第 index 种最多用 zhangs[index] 张。
- **暴力/dp1（枚举张数）**：枚举 zhang 从 0 到 `min(rest/coins, zhangs[index])`，累加。
- **dp2（枚举优化，含减法修正）**：在完全背包公式 `dp[index+1][rest] + dp[index][rest-coins]` 基础上，由于张数受限，要**减去多算的部分**——即用了「zhangs+1 张」的非法方案：
  `if (rest - coins*(zhangs+1) >= 0) dp[index][rest] -= dp[index+1][rest - coins*(zhangs+1)]`。

**关键代码 / 步骤**：
```java
dp[index][rest] = dp[index + 1][rest];
if (rest - coins[index] >= 0)
    dp[index][rest] += dp[index][rest - coins[index]];
if (rest - coins[index] * (zhangs[index] + 1) >= 0)
    dp[index][rest] -= dp[index + 1][rest - coins[index] * (zhangs[index] + 1)];
```

**复杂度**：
- dp1：时间 O(N × aim × 平均张数)，空间 O(N×aim)。
- dp2：时间 O(N×aim)，空间 O(N×aim)。

**易错点 / 面试提示**：
1. dp2 的减法项是多重背包的精髓：完全背包会把「用超过 zhangs 张」的方案也算进去，需精确减掉那个「恰好多用了 zhangs+1 张后剩余」的子状态（注意减的是 `index+1` 行）。
2. 必须先用 HashMap 去重并统计张数（getInfo），否则模型不对。

---

### class21 / Code05_BobDie.java —— Bob 存活概率（随机游走仍在棋盘内的概率）

**题目描述**：在 N×M 棋盘上，Bob 从 (row,col) 出发，每步等概率向上/下/左/右走一格，共走 k 步。每一步走出棋盘就死。求走完 k 步后仍在棋盘内（存活）的概率。

**核心思路**：先求「存活的情况数」，再除以总情况数 `4^k`。
- 状态：`process(row, col, rest)` = 在 (row,col)、还剩 rest 步，走完后仍在棋盘内的「生存点数」（合法路径条数）。
- 转移：四个方向 `process(r±1/c±1, rest-1)` 求和。
- base case：越界返回 0；`rest==0` 且仍在盘内返回 1。
- 严格 DP：`dp[N][M][k+1]`，先把 rest=0 层全置 1（每个盘内格子都是 1 条），再按 rest 逐层填，越界用 `pick` 返回 0。最终概率 = `dp[row][col][k] / 4^k`。

**关键代码 / 步骤**：
```java
for (int i = 0; i < N; i++)
    for (int j = 0; j < M; j++)
        dp[i][j][0] = 1;
for (int rest = 1; rest <= k; rest++)
    for (int r = 0; r < N; r++)
        for (int c = 0; c < M; c++)
            dp[r][c][rest] = pick(dp,N,M,r-1,c,rest-1) + pick(dp,N,M,r+1,c,rest-1)
                           + pick(dp,N,M,r,c-1,rest-1) + pick(dp,N,M,r,c+1,rest-1);
return (double) dp[row][col][k] / Math.pow(4, k);
```

**复杂度**：暴力递归 O(4^k)；严格 DP 时间 O(N×M×k)，空间 O(N×M×k)。

**易错点 / 面试提示**：
1. 概率 = 生存点数 / 4^k；分子用 DP 求情况数，避免浮点误差累积。
2. 用 `long` 存计数（路径数会很大），最后再转 double 除以 `4^k`。
3. base case：rest=0 时所有盘内格子都贡献 1 条存活路径。

---

## class22 含数学优化的动态规划（概率 / 完全背包最少张 / 整数拆分）

### class22 / Code01_KillMonster.java —— 砍死怪兽的概率

**题目描述**：怪兽有 N 点血，每次攻击造成 `[0, M]` 范围内等概率的整数伤害，共攻击 K 次。求 K 次内把怪兽砍死（血量 ≤ 0）的概率。

**核心思路**：求砍死的情况数，再除以总情况数 `(M+1)^K`。
- 状态：`process(times, hp)` = 还能砍 times 次、怪兽剩 hp 血，砍死的情况数。
- 转移：枚举本次伤害 i ∈ [0, M]，累加 `process(times-1, hp-i)`。
- base case：`times==0` 时 `hp<=0` 返回 1 否则 0；`hp<=0`（已死）则剩下 times 次任意打，返回 `(M+1)^times`（每次都有 M+1 种可能，全算死）。
- **dp1（直接填表）**：`dp[K+1][N+1]`，`dp[times][0] = (M+1)^times`，枚举 i 累加；hp-i 越界（已死）的部分补 `(M+1)^(times-1)`。
- **dp2（枚举优化 / 前缀和优化）**：`dp[times][hp]` 与 `dp[times][hp-1]` 的求和窗口只差两端，得：
  `dp[times][hp] = dp[times][hp-1] + dp[times-1][hp]`，再减去滑出窗口的项 `dp[times-1][hp-1-M]`（越界则减 `(M+1)^(times-1)`）。

**关键代码 / 步骤**：
```java
// dp2：滑动窗口/前缀和优化
dp[times][hp] = dp[times][hp - 1] + dp[times - 1][hp];
if (hp - 1 - M >= 0)
    dp[times][hp] -= dp[times - 1][hp - 1 - M];
else
    dp[times][hp] -= Math.pow(M + 1, times - 1);
```

**复杂度**：暴力 O((M+1)^K)；dp1 时间 O(K×N×M)；dp2 时间 O(K×N)，空间 O(K×N)。

**易错点 / 面试提示**：
1. 关键是「已死之后剩余攻击仍要计入全部情况数」`(M+1)^times`，否则概率算错。
2. dp2 的枚举优化把对 i 的求和转成相邻列相减（窗口滑动），是数学优化的典型。
3. 用 `long` 存计数防溢出，最后转 double 除以总数。

---

### class22 / Code02_MinCoinsNoLimit.java —— 凑钱的最少张数（完全背包求最小）

**题目描述**：`arr` 是去重面值，每种无限张。求凑出 `aim` 的**最少纸币张数**，无法凑出返回（标记为）Integer.MAX_VALUE。

**核心思路**：完全背包求最小值版，与 Code03（求方法数）结构一致，只是聚合从「求和」变「取 min」。
- 状态：`process(index, rest)` = 用 arr[index...] 凑 rest 的最少张数，凑不出返回 MAX。
- **暴力/dp1（枚举张数）**：枚举 zhang，`ans = min(ans, zhang + process(index+1, rest - zhang*arr))`，注意 next 为 MAX 时不能 +zhang。
- **dp2（枚举优化）**：`dp[index][rest] = min(dp[index+1][rest], dp[index][rest-arr[index]] + 1)`，即「不用这种面值」与「用一张这种面值后再凑剩下」二者取小。

**关键代码 / 步骤**：
```java
dp[index][rest] = dp[index + 1][rest];
if (rest - arr[index] >= 0 && dp[index][rest - arr[index]] != Integer.MAX_VALUE)
    dp[index][rest] = Math.min(dp[index][rest], dp[index][rest - arr[index]] + 1);
```

**复杂度**：暴力/dp1 时间 O(N × aim × 枚举张数)；dp2 时间 O(N×aim)，空间 O(N×aim)。

**易错点 / 面试提示**：
1. MAX_VALUE 是「凑不出」的哨兵，做加法前务必判断 `!= MAX`，否则 `MAX+1` 溢出成负数导致 min 出错。
2. dp2 用「+1」而非「+zhang」：因为 `dp[index][rest-arr]` 已经允许继续用该面值，自然覆盖多张情形。
3. base：`dp[N][0]=0`，`dp[N][其它]=MAX`。

---

### class22 / Code03_SplitNumber.java —— 整数的拆分方法数（划分数）

**题目描述**：给定正数 n，把它拆成若干个正整数之和（拆出的数**非递减排列**，即不计顺序），求拆分的方法数。例如 4 = 4 = 1+3 = 2+2 = 1+1+2 = 1+1+1+1。

**核心思路**：用「上一个拆出的数」做约束保证非递减，避免重复计数。
- 状态：`process(pre, rest)` = 上一个拆出的数是 pre、还剩 rest 要拆，且后续每个数都 ≥ pre 的拆分方法数。
- 转移：枚举本次拆出的数 first（从 pre 到 rest），累加 `process(first, rest - first)`。
- base case：`rest==0` 返回 1（完成）；`pre > rest` 返回 0（剩不够再拆出 ≥pre 的数）。
- **dp1（枚举优化前）**：`dp[n+1][n+1]`，初始化 `dp[pre][0]=1`、`dp[pre][pre]=1`，pre 倒推、rest 正推，内层枚举 first 累加。
- **dp2（枚举优化）**：`dp[pre][rest] = dp[pre+1][rest] + dp[pre][rest-pre]`。`dp[pre+1][rest]` 表示「本次不拆 pre、起步数更大」；`dp[pre][rest-pre]` 表示「本次拆出一个 pre，剩下继续以 pre 起步」。消去 first 循环。

**关键代码 / 步骤**：
```java
// dp2：枚举优化
for (int pre = n - 1; pre >= 1; pre--)
    for (int rest = pre + 1; rest <= n; rest++) {
        dp[pre][rest] = dp[pre + 1][rest];
        dp[pre][rest] += dp[pre][rest - pre];
    }
return dp[1][n];
```

**复杂度**：暴力 O(指数级)；dp1 时间 O(n³)（三重枚举）；dp2 时间 O(n²)，空间 O(n²)。

**易错点 / 面试提示**：
1. 用「pre 单调不减」消除排列重复，是划分数问题的关键设计。
2. dp2 的枚举优化与完全背包同理：`dp[pre][rest-pre]` 用当前行（pre 可重复用），`dp[pre+1][rest]` 用下一行（pre 不再用）。
3. 答案是 `dp[1][n]`（从最小起步数 1 开始拆整个 n）；注意 `dp[pre][pre]=1` 这条对角线 base case。

---

## class23 子集划分 / N 皇后（位运算加速）

### class23 / Code01_SplitSumClosed.java —— 把数组分两堆使和最接近

**题目描述**：给定非负数组 `arr`，把它分成两个子集，使两子集的累加和尽可能接近，返回**较小那堆的和**（即 ≤ sum/2 的前提下最接近 sum/2 的和）。

**核心思路**：等价于「容量为 sum/2 的 0-1 背包，求能装到的最大和」。
- 状态：`process(i, rest)` = 从 arr[i...] 自由选择，累加和 ≤ rest 且最接近 rest 时的和。
- 转移：不要 arr[i]（`p1 = process(i+1, rest)`）；要 arr[i]（仅当 `arr[i] <= rest`，`p2 = arr[i] + process(i+1, rest-arr[i])`）。取较大。
- base case：`i == N` 返回 0。
- 严格 DP：`dp[N+1][sum/2+1]`，i 倒推，rest 正推，答案 `dp[0][sum/2]`。

**关键代码 / 步骤**：
```java
for (int i = N - 1; i >= 0; i--)
    for (int rest = 0; rest <= sum; rest++) {
        int p1 = dp[i + 1][rest];
        int p2 = 0;
        if (arr[i] <= rest) p2 = arr[i] + dp[i + 1][rest - arr[i]];
        dp[i][rest] = Math.max(p1, p2);
    }
return dp[0][sum];   // 此处 sum 已被赋为 sum/2
```

**复杂度**：暴力 O(2^N)；严格 DP 时间 O(N × sum/2)，空间同。

**易错点 / 面试提示**：
1. 目标容量取 `sum/2`（整数除法向下取整），求出较小堆和后，另一堆即 `总sum - 该值`，二者最接近。
2. 这就是子集和问题的最大化版本，注意 rest 维度上界是 sum/2 而非 sum。

---

### class23 / Code02_SplitSumClosedSizeHalf.java —— 分两堆且个数相等（或差一个）使和最接近

**题目描述**：在 Code01 基础上**多一个约束**：两堆元素个数必须相等（数组长度为偶数时各 N/2 个）；长度为奇数时两堆个数差 1（即 N/2 与 N/2+1 都试）。返回较小堆的和（≤ sum/2 下最接近）。

**核心思路**：在 0-1 背包上增加「已选个数」维度，**三维表**。
- 状态：`process(i, picks, rest)` = 从 arr[i...] 中**恰好再挑 picks 个**、累加和 ≤ rest 且最接近 rest 的和；挑不出合法方案返回 -1。
- 转移：不要 arr[i]（`p1 = process(i+1, picks, rest)`）；要 arr[i]（picks-1，rest-arr[i]，需 `arr[i] <= rest` 且 next != -1）。取较大。
- base case：`i == N` 时 `picks == 0` 返回 0，否则 -1（个数没凑够，非法）。
- **right**：偶数长度调 `process(0, N/2, sum/2)`；奇数长度取 `max(process(...,N/2,...), process(...,N/2+1,...))`。
- **dp（标准三维填表，倒推）**：`dp[N+1][M+1][sum/2+1]` 全初始化 -1，`dp[N][0][rest]=0`，按 i 倒推填，picks/rest 正推；答案按奇偶取对应 `dp[0][...][sum]`。
- **dp2（正推 + 维度优化版）**：把 picks 含义改为「前 i 个里选了 j 个」的正向 DP，用 `Integer.MIN_VALUE` 标记非法，`dp[i][0][k]=0`、`dp[0][1][k]` 单独初始化，最后取 `max(dp[N-1][M][sum], dp[N-1][N-M][sum])`。
- 文件中注释掉的 right/process/dp1 是另一种「picks 从 0 往上加」的等价写法（保留作对照）。

**关键代码 / 步骤**：
```java
// 三维倒推 dp
int p1 = dp[i + 1][picks][rest];
int p2 = -1, next = -1;
if (picks - 1 >= 0 && arr[i] <= rest)
    next = dp[i + 1][picks - 1][rest - arr[i]];
if (next != -1) p2 = arr[i] + next;
dp[i][picks][rest] = Math.max(p1, p2);
```

**复杂度**：暴力 O(2^N)；三维 DP 时间 O(N × N/2 × sum/2) = O(N² × sum)，空间同。

**易错点 / 面试提示**：
1. 多出的「个数」维度是核心，base case 必须检查 `picks == 0`（恰好选够），用 -1 区分「合法和为 0」与「非法」。
2. 奇数长度要试两种个数划分（N/2 与 N/2+1）取较优，不能只试一种。
3. dp 与 dp2 是「倒推填 picks 为剩余数」与「正推填 picks 为已选数」两种坐标系，注意 base case 与答案读取位置随之不同。

---

### class23 / Code03_NQueens.java —— N 皇后问题（含位运算加速）

**题目描述**：在 N×N 棋盘上放 N 个皇后，要求两两不能同行、同列、同对角线，求合法摆放的总方案数。

**核心思路**：本题不是表 DP，而是**深度优先 + 剪枝**，重点是位运算加速。
- **num1 / process1（普通版）**：逐行放皇后，`record[i]=j` 记第 i 行皇后在第 j 列。第 i 行枚举每一列 j，用 `isValid` 检查与之前所有行不冲突（列相同或 `|行差|==|列差|` 即对角线冲突），合法则递归下一行。`i==n` 时方案 +1。
- **num2 / process2（位运算加速版，n ≤ 32）**：用三个整数的二进制位表示限制——
  - `colLim`：被占用的列；
  - `leftDiaLim`：左下对角线限制（每深入一行左移一位）；
  - `rightDiaLim`：右下对角线限制（每深入一行无符号右移一位）。
  - `pos = limit & (~(colLim | leftDiaLim | rightDiaLim))`：当前行所有可放位置（1 表示可放）。
  - 用 `mostRightOne = pos & (~pos + 1)` 逐个取出最低位的 1 进行尝试，并在递归时更新三个限制（注意左右对角线的移位方向）。
  - base case：`colLim == limit`（所有列填满）返回 1。
  - `limit` 是低 n 位为 1 的掩码（n==32 时取 -1 即全 1）。

**关键代码 / 步骤**：
```java
int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
int mostRightOne = 0, res = 0;
while (pos != 0) {
    mostRightOne = pos & (~pos + 1);           // 取最低位的 1
    pos = pos - mostRightOne;
    res += process2(limit,
        colLim | mostRightOne,
        (leftDiaLim | mostRightOne) << 1,      // 左对角线下移
        (rightDiaLim | mostRightOne) >>> 1);   // 右对角线下移（无符号右移）
}
return res;
```

**复杂度**：两版本本质都是 O(N!) 级别的搜索；但 num2 用位运算把每行的「合法位置计算 + 冲突检测」降到 O(1) 的常数操作，**实测快一个数量级以上**（main 中 15 皇后对比可见）。

**易错点 / 面试提示**：
1. 对角线冲突判断：普通版用 `Math.abs(record[k]-j) == Math.abs(i-k)`；位运算版靠左移/右移自动维护两条对角线限制。
2. `pos & (~pos + 1)` 是提取「最右侧的 1」的标准位运算技巧（即 `pos & -pos`）。
3. 右对角线限制必须用**无符号右移 `>>>`**，避免符号位填充导致错误；位运算版限制 n ≤ 32（int 位宽）。
4. N 皇后是「常数项优化」的经典案例——算法复杂度量级不变，但位运算把常数压到极低。
