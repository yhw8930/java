# 算法面试复习资料 Part4：class12 - class14（二叉树树形 DP 与贪心）

> 本部分覆盖二叉树递归套路（树形 DP）和贪心算法。树形 DP 的核心套路：
> 1. 定义 `Info` 结构体（每棵子树向父节点汇报的信息）；
> 2. 写 `process(x)` 递归：先拿到左子树 Info、右子树 Info，再加工出当前节点的 Info；
> 3. 设计 base case（空节点返回什么）；
> 4. 主函数调用 `process(head)` 取所需字段。
>
> 贪心套路：提出贪心策略，然后用对数器（暴力解法）大量随机测试验证其正确性。

---

## class12 二叉树递归套路（树形 DP 入门）

### class12 / Code01_IsCBT.java —— 判断一棵二叉树是否为完全二叉树（CBT）

**题目描述**：输入一棵二叉树的头节点 `head`。输出布尔值，判断它是否为完全二叉树（Complete Binary Tree，即除最后一层外每层都满，且最后一层节点都集中在左侧）。

**核心思路**：给了两种解法。
- 解法一（BFS 层序遍历）：用队列宽度优先遍历，维护一个 `leaf` 标记表示"是否已经遇到过左右孩子不双全的节点"。两条违规条件：(1) 某节点有右孩子但没左孩子 → 直接 false；(2) 一旦遇到过不双全节点后，后面再出现非叶节点 → false。
- 解法二（树形 DP）：`Info` 含 `isFull`（是否满二叉树）、`isCBT`（是否完全二叉树）、`height`（高度）。一个节点是 CBT 的四种情况：左满右满且等高（满树）、左 CBT 右满且左比右高 1、左满右满且左比右高 1、左满右 CBT 且等高。
- 推荐解法一，简洁直观；解法二用于熟练树形 DP 套路。

**关键代码 / 步骤**：
```java
class Info { boolean isFull; boolean isCBT; int height; }

Info process(Node x) {
    if (x == null) return new Info(true, true, 0);
    Info l = process(x.left), r = process(x.right);
    int height = Math.max(l.height, r.height) + 1;
    boolean isFull = l.isFull && r.isFull && l.height == r.height;
    boolean isCBT = false;
    if (l.isFull && r.isFull && l.height == r.height)            isCBT = true; // 满
    else if (l.isCBT && r.isFull && l.height == r.height + 1)    isCBT = true;
    else if (l.isFull && r.isFull && l.height == r.height + 1)   isCBT = true;
    else if (l.isFull && r.isCBT && l.height == r.height)        isCBT = true;
    return new Info(isFull, isCBT, height);
}
```

**复杂度**：时间 O(N)，空间 O(H)（H 为树高，递归栈；BFS 解法为 O(N) 队列）。

**易错点 / 面试提示**：
- BFS 解法中"有右无左"必须单独判 false，别只靠 leaf 标记。
- 树形 DP 四种 CBT 情况要枚举完整，尤其"左 CBT 右满左高 1"和"左满右 CBT 等高"两种分界。

---

### class12 / Code02_IsBST.java —— 判断一棵二叉树是否为搜索二叉树（BST）

**题目描述**：输入二叉树头节点 `head`，判断它是否为搜索二叉树（每个节点左子树都小于它、右子树都大于它，本题用严格小于/大于，即不允许重复值）。

**核心思路**：两种解法。
- 解法一（中序遍历）：BST 的中序遍历结果一定严格升序。中序收集到数组后检查是否升序。
- 解法二（树形 DP）：`Info` 含 `isBST`、`max`（子树最大值）、`min`（子树最小值）。当前节点是 BST 需满足：左右子树都是 BST，且左子树 max < 当前值、右子树 min > 当前值。注意空节点返回 null，需做空判断。
- 两种皆可，树形 DP 是套路化的标准答案。

**关键代码 / 步骤**：
```java
class Info { boolean isBST; int max; int min; }

Info process(Node x) {
    if (x == null) return null;
    Info l = process(x.left), r = process(x.right);
    int max = x.value, min = x.value;
    if (l != null) { max = Math.max(max, l.max); min = Math.min(min, l.min); }
    if (r != null) { max = Math.max(max, r.max); min = Math.min(min, r.min); }
    boolean isBST = true;
    if (l != null && (!l.isBST || l.max >= x.value)) isBST = false;
    if (r != null && (!r.isBST || r.min <= x.value)) isBST = false;
    return new Info(isBST, max, min);
}
```

**复杂度**：时间 O(N)，空间 O(H)。

**易错点 / 面试提示**：
- 空节点 Info 返回 null，使用前必须判空（与 Code01 返回非 null 的写法不同）。
- 本题用 `>=` / `<=` 表示不允许相等；若题意允许重复，需调整比较符。

---

### class12 / Code03_IsBalanced.java —— 判断一棵二叉树是否为平衡二叉树

**题目描述**：输入二叉树头节点 `head`，判断它是否为平衡二叉树（任意节点的左右子树高度差不超过 1）。

**核心思路**：树形 DP。`Info` 含 `isBalanced`、`height`。当前节点平衡的条件：左子树平衡、右子树平衡、且左右高度差绝对值 ≤ 1。解法一用一个全局 `boolean[] ans` + 提前剪枝（不平衡就返回 -1 停止）；解法二是标准 Info 套路。两者等价，推荐解法二的结构更清晰。

**关键代码 / 步骤**：
```java
class Info { boolean isBalanced; int height; }

Info process(Node x) {
    if (x == null) return new Info(true, 0);
    Info l = process(x.left), r = process(x.right);
    int height = Math.max(l.height, r.height) + 1;
    boolean isBalanced = l.isBalanced && r.isBalanced
                         && Math.abs(l.height - r.height) <= 1;
    return new Info(isBalanced, height);
}
```

**复杂度**：时间 O(N)，空间 O(H)。

**易错点 / 面试提示**：
- 这是树形 DP 最经典的入门题，Info 只需两个字段。
- 解法一用 -1 表示"已发现不平衡"提前终止，但仍是 O(N)；面试中讲清"无论左右子树长什么样都要的信息"才能套路化。

---

### class12 / Code04_IsFull.java —— 判断一棵二叉树是否为满二叉树

**题目描述**：输入二叉树头节点 `head`，判断它是否为满二叉树（高度为 h，则节点数恰为 2^h − 1）。

**核心思路**：两种解法。
- 解法一：分别递归求高度 `h` 和节点数 `n`，判断 `(1<<h)-1 == n`。
- 解法二（树形 DP）：`Info` 只含 `height` 和 `nodes`，一次遍历同时收集两者，主函数再判 `(1<<height)-1 == nodes`。
- 推荐解法二，一次遍历即可。

**关键代码 / 步骤**：
```java
class Info { int height; int nodes; }

Info process(Node x) {
    if (x == null) return new Info(0, 0);
    Info l = process(x.left), r = process(x.right);
    int height = Math.max(l.height, r.height) + 1;
    int nodes = l.nodes + r.nodes + 1;
    return new Info(height, nodes);
}
// 满二叉树判定： (1 << all.height) - 1 == all.nodes
```

**复杂度**：时间 O(N)，空间 O(H)。

**易错点 / 面试提示**：
- `1 << height` 用位运算求 2 的幂，注意 height 较大时可能溢出（本题数据范围小无碍）。
- 也可以在 Info 里直接带 `isFull` 字段递归判定，但用 height+nodes 在末尾统一判更简洁。

---

### class12 / Code05_MaxSubBSTSize.java —— 求一棵二叉树中最大搜索二叉子树的节点数

**题目描述**：输入二叉树头节点 `head`，找到其中"整棵都是 BST 的最大子树"，返回该子树的节点个数。注意是"子树"（必须包含某节点及其全部后代），不是任意拼接。

**核心思路**：树形 DP。`Info` 含 `maxBSTSubtreeSize`（子树内最大 BST 子树的大小）、`allSize`（子树总节点数）、`max`、`min`。判断"以 x 为头整棵是否为 BST"的技巧：左子树整体是 BST（即左 `maxBSTSubtreeSize == allSize`）、右子树整体是 BST、且左 max < x < 右 min。答案取三个可能：左侧答案 p1、右侧答案 p2、以 x 为头组成的整棵 BST 大小 p3 的最大值。
- 解法一是暴力（对每个节点判断以它为头是否 BST），解法二是树形 DP，推荐解法二 O(N)。

**关键代码 / 步骤**：
```java
class Info { int maxBSTSubtreeSize; int allSize; int max; int min; }

Info process(Node x) {
    if (x == null) return null;
    Info l = process(x.left), r = process(x.right);
    int max = x.value, min = x.value, allSize = 1;
    if (l != null) { max=max(max,l.max); min=min(min,l.min); allSize+=l.allSize; }
    if (r != null) { max=max(max,r.max); min=min(min,r.min); allSize+=r.allSize; }
    int p1 = l == null ? -1 : l.maxBSTSubtreeSize;
    int p2 = r == null ? -1 : r.maxBSTSubtreeSize;
    int p3 = -1;
    boolean leftBST  = l == null || l.maxBSTSubtreeSize == l.allSize;
    boolean rightBST = r == null || r.maxBSTSubtreeSize == r.allSize;
    if (leftBST && rightBST
        && (l == null || l.max < x.value)
        && (r == null || x.value < r.min)) {
        p3 = (l==null?0:l.allSize) + (r==null?0:r.allSize) + 1;
    }
    return new Info(Math.max(p1, Math.max(p2, p3)), allSize, max, min);
}
```

**复杂度**：时间 O(N)，空间 O(H)。

**易错点 / 面试提示**：
- 判断"子树整体是 BST"用 `maxBSTSubtreeSize == allSize` 这个等式，避免在 Info 里再加 `isAllBST` 字段（虽然加上更直观，源码注释里有这种写法）。
- p3 用 -1 初始化以区分"无法形成"的情况。

---

### class12 / Code06_MaxDistance.java —— 求二叉树的最大距离（树的直径）

**题目描述**：输入二叉树头节点 `head`，定义两节点距离为它们路径上经过的节点数（含两端）。返回所有节点对中的最大距离（即树的直径，按节点数计）。

**核心思路**：树形 DP。`Info` 含 `maxDistance`（子树内最大距离）、`height`（子树高度）。当前节点的最大距离取三者最大：(1) 左子树内部最大距离 p1，(2) 右子树内部最大距离 p2，(3) 经过当前节点的路径 = 左高 + 右高 + 1（p3）。
- 解法一暴力：建父指针 map，枚举所有节点对求距离（最近公共祖先法）。解法二是树形 DP，推荐 O(N)。

**关键代码 / 步骤**：
```java
class Info { int maxDistance; int height; }

Info process(Node x) {
    if (x == null) return new Info(0, 0);
    Info l = process(x.left), r = process(x.right);
    int height = Math.max(l.height, r.height) + 1;
    int p1 = l.maxDistance;
    int p2 = r.maxDistance;
    int p3 = l.height + r.height + 1;     // 路径必过 x
    return new Info(Math.max(Math.max(p1, p2), p3), height);
}
```

**复杂度**：时间 O(N)，空间 O(H)。

**易错点 / 面试提示**：
- 本题距离按"节点数"计，所以经过头节点的路径是 `左高+右高+1`；若按"边数"计则去掉 +1。
- 这是"路径是否必经过当前节点"分类讨论的经典模板，与最大路径和等题同源。

---

## class13 二叉树递归套路进阶 + 贪心入门

### class13 / Code01_IsCBT.java —— 判断完全二叉树（class12 同题强化版）

**题目描述**：与 class12/Code01 完全相同——输入二叉树头节点，判断是否为完全二叉树。

**核心思路**：同 class12/Code01。提供 BFS 解法 `isCBT1` 与树形 DP 解法 `isCBT2`。差别仅在 `isCBT2` 不再像 class12 那样先用 `isFull` 包一层判断，而是直接用 4 个 `if-else if` 把四种 CBT 情况平铺，逻辑更线性。`Info` 仍含 `isFull / isCBT / height`。

**关键代码 / 步骤**：
```java
Info process(Node x) {
    if (x == null) return new Info(true, true, 0);
    Info l = process(x.left), r = process(x.right);
    int height = Math.max(l.height, r.height) + 1;
    boolean isFull = l.isFull && r.isFull && l.height == r.height;
    boolean isCBT = false;
    if (l.isFull && r.isFull && l.height == r.height)          isCBT = true;
    else if (l.isCBT && r.isFull && l.height == r.height + 1)  isCBT = true;
    else if (l.isFull && r.isFull && l.height == r.height + 1) isCBT = true;
    else if (l.isFull && r.isCBT && l.height == r.height)      isCBT = true;
    return new Info(isFull, isCBT, height);
}
```

**复杂度**：时间 O(N)，空间 O(H)。

**易错点 / 面试提示**：
- 四种情况用 `else if` 串联，命中即可，注意第一种"满"也归入 CBT。
- 与 class12 版本对照理解：本版省去了"先判 isFull 再细分"的嵌套，结构更扁平。

---

### class13 / Code02_MaxSubBSTHead.java —— 求最大搜索二叉子树的头节点

**题目描述**：输入二叉树头节点 `head`，返回"整棵都是 BST 的最大子树"的头节点（返回的是节点本身，不是大小）。

**核心思路**：树形 DP，是 class12/Code05 的"返回头节点"版本。`Info` 含 `maxSubBSTHead`（最大 BST 子树的头）、`maxSubBSTSize`（其大小）、`min`、`max`。先在左右两边各自的答案里取较大者，再判断"以 X 为头能否整棵成 BST"：条件是 **左子树最大 BST 头恰是 X.left 且左 max < X.value**，**右子树最大 BST 头恰是 X.right 且右 min > X.value**——这等价于左右整棵都是 BST 且满足大小关系，则以 X 为头更新答案。
- 解法一暴力（自顶向下判断每个节点），解法二树形 DP，推荐 O(N)。

**关键代码 / 步骤**：
```java
class Info { Node maxSubBSTHead; int maxSubBSTSize; int min; int max; }

Info process(Node X) {
    if (X == null) return null;
    Info l = process(X.left), r = process(X.right);
    int min = X.value, max = X.value;
    Node head = null; int size = 0;
    if (l != null) { min=min(min,l.min); max=max(max,l.max);
                     head=l.maxSubBSTHead; size=l.maxSubBSTSize; }
    if (r != null) { min=min(min,r.min); max=max(max,r.max);
                     if (r.maxSubBSTSize > size) { head=r.maxSubBSTHead; size=r.maxSubBSTSize; } }
    // 以 X 为头能否整棵成 BST
    if ((l == null || (l.maxSubBSTHead == X.left  && l.max < X.value))
     && (r == null || (r.maxSubBSTHead == X.right && r.min > X.value))) {
        head = X;
        size = (l==null?0:l.maxSubBSTSize) + (r==null?0:r.maxSubBSTSize) + 1;
    }
    return new Info(head, size, min, max);
}
```

**复杂度**：时间 O(N)，空间 O(H)。

**易错点 / 面试提示**：
- 巧妙之处：用 `l.maxSubBSTHead == X.left` 判断"左子树整棵是 BST"——只有整棵都是 BST 时，左子树的最大 BST 头才会正好是 X.left。
- 空节点返回 null，使用 Info 字段前要判空。

---

### class13 / Code03_lowestAncestor.java —— 求二叉树两个节点的最低公共祖先（LCA）

**题目描述**：输入二叉树头节点 `head` 和两个节点 `o1`、`o2`（保证都在树中），返回它们的最低公共祖先节点。

**核心思路**：两种解法。
- 解法一（父指针 + 集合）：建立"子→父" map，把 o1 到根的所有祖先放入集合；再从 o2 向上走，第一个出现在集合里的就是 LCA。
- 解法二（树形 DP）：`Info` 含 `findA`、`findB`（子树内是否找到 a、b）、`ans`（已确定的答案）。从下往上：若左右子树已经返回了非空 ans 直接上传；否则若当前子树里同时 findA 且 findB，则当前节点 x 就是 LCA。
- 树形 DP 一次遍历完成，推荐解法二。

**关键代码 / 步骤**：
```java
class Info { boolean findA; boolean findB; Node ans; }

Info process(Node x, Node a, Node b) {
    if (x == null) return new Info(false, false, null);
    Info l = process(x.left, a, b), r = process(x.right, a, b);
    boolean findA = (x == a) || l.findA || r.findA;
    boolean findB = (x == b) || l.findB || r.findB;
    Node ans = null;
    if (l.ans != null) ans = l.ans;
    else if (r.ans != null) ans = r.ans;
    else if (findA && findB) ans = x;   // 第一次同时集齐 a、b 的最低节点
    return new Info(findA, findB, ans);
}
```

**复杂度**：时间 O(N)，空间 O(H)。

**易错点 / 面试提示**：
- 注意 `findA`/`findB` 的或运算要把"x 自己是不是 a/b"也算进去。
- ans 一旦在某层确定，要原样向上传递，不能被上层覆盖（先查 l.ans、r.ans 再考虑 x）。

---

### class13 / Code04_MaxHappy.java —— 派对的最大快乐值（多叉树树形 DP）

**题目描述**：公司是一棵多叉树（每个 `Employee` 有 happy 值和直接下属列表 `nexts`），boss 是根。规则：如果某员工来参加派对，则他的直接下属都不能来。求在此约束下能获得的最大快乐值总和。

**核心思路**：树形 DP（多叉树）。`Info` 含 `yes`（当前节点**来**时其子树的最大快乐）、`no`（当前节点**不来**时其子树的最大快乐）。
- `yes = x.happy + 所有下属的 no 之和`（x 来，下属都不能来）；
- `no = 所有下属 max(yes, no) 之和`（x 不来，每个下属自由选来或不来取较大）。
答案 = `max(根.yes, 根.no)`。
- 解法一用带 `up`（上级是否来）参数的暴力递归，解法二是 Info 套路，推荐解法二。

**关键代码 / 步骤**：
```java
class Info { int no; int yes; }

Info process(Employee x) {
    if (x == null) return new Info(0, 0);
    int no = 0, yes = x.happy;
    for (Employee next : x.nexts) {
        Info ni = process(next);
        no  += Math.max(ni.no, ni.yes); // x 不来，下属随意
        yes += ni.no;                   // x 来，下属必不来
    }
    return new Info(no, yes);
}
// 答案： Math.max(root.no, root.yes)
```

**复杂度**：时间 O(N)（N 为员工数），空间 O(H)。

**易错点 / 面试提示**：
- 这是"打家劫舍 III"的本质，区别在于本题是多叉树，要遍历 `nexts` 列表累加。
- 最终答案别忘了对根节点取 `max(yes, no)`，不能只取一个。

---

### class13 / Code05_LowestLexicography.java —— 拼接出字典序最小的字符串（贪心 + 排序）

**题目描述**：输入字符串数组 `strs`，把它们按某种顺序首尾拼接成一个大字符串，返回所有拼接结果中字典序最小的那个。

**核心思路**：贪心比较器。对任意两个字符串 a、b，比较 `a+b` 与 `b+a` 的字典序：若 `a+b < b+a` 则 a 应排在 b 前面。用此自定义 Comparator 对数组排序后顺序拼接即得答案。该比较器满足传递性（可证），所以排序结果即全局最优。
- 解法一是暴力全排列（用 TreeSet 取首个），解法二是贪心排序，推荐解法二 O(N log N · 字符串长度)。

**关键代码 / 步骤**：
```java
class MyComparator implements Comparator<String> {
    public int compare(String a, String b) {
        return (a + b).compareTo(b + a);   // a+b 更小则 a 在前
    }
}
Arrays.sort(strs, new MyComparator());
String res = "";
for (String s : strs) res += s;
```

**复杂度**：时间 O(N log N · L)（L 为字符串平均长度），空间 O(N) 或 O(L)。

**易错点 / 面试提示**：
- 贪心策略**不能**简单按 `a.compareTo(b)` 排序（反例："b" 与 "ba"），必须用 `a+b` vs `b+a`。
- 面试需能说明该比较器的传递性，证明贪心正确（可用对数器验证）。

---

## class14 贪心算法专题 + 并查集

### class14 / Code01_Light.java —— 路灯照亮问题（最少路灯数）

**题目描述**：输入字符串 `road`，'X' 表示墙（不能放灯、也不需照亮），'.' 表示需要被照亮的点。一盏灯放在位置 i 可照亮 i−1、i、i+1 三个位置。求照亮所有 '.' 所需的最少灯数。

**核心思路**：贪心。从左往右扫描：遇到 'X' 直接跳过；遇到 '.' 就放一盏灯（light++），然后看它右侧——若 i+1 是 'X'，则灯只能管到 i，下一步跳到 i+2；若 i+1 是 '.'，则把灯放在 i+1 处最优（能覆盖 i、i+1、i+2），下一步跳到 i+3。
- 解法一是暴力（每个 '.' 位置选择放/不放灯，结尾验证），解法二是贪心，推荐解法二 O(N)。

**关键代码 / 步骤**：
```java
int i = 0, light = 0;
while (i < str.length) {
    if (str[i] == 'X') { i++; }
    else {                       // str[i] == '.'
        light++;
        if (i + 1 == str.length) break;
        if (str[i + 1] == 'X') i += 2;   // 灯放 i，跳过 X
        else                   i += 3;   // 灯放 i+1，覆盖三格
    }
}
return light;
```

**复杂度**：时间 O(N)，空间 O(1)（贪心解法）。

**易错点 / 面试提示**：
- 关键贪心：遇到 '.' 时把灯放在"它的下一个位置"（若下一个也是 '.'），可多覆盖一格。
- 注意越界判断：i+1 到达末尾时直接 break。

---

### class14 / Code02_LessMoneySplitGold.java —— 切金条 / 哈夫曼最小代价合并

**题目描述**：一块金条要分成数组 `arr` 指定的若干段（等价于：给定 arr 中的数，每次把两个数合并成一个，代价是两数之和，合并直到只剩一个）。求把所有数合并为一个的最小总代价。

**核心思路**：哈夫曼编码思想 + 小根堆贪心。把所有数放入小根堆，每次弹出两个最小的合并，合并和累加进总代价，再把合并和放回堆，直到堆中只剩一个。每次取最小的两个合并，保证小的数被反复累加的次数最少，总代价最小。
- 解法一是暴力（枚举每次合并哪两个），解法二是小根堆贪心，推荐解法二 O(N log N)。

**关键代码 / 步骤**：
```java
PriorityQueue<Integer> pQ = new PriorityQueue<>(); // 小根堆
for (int v : arr) pQ.add(v);
int sum = 0;
while (pQ.size() > 1) {
    int cur = pQ.poll() + pQ.poll();  // 取最小两个合并
    sum += cur;
    pQ.add(cur);
}
return sum;
```

**复杂度**：时间 O(N log N)，空间 O(N)。

**易错点 / 面试提示**：
- 这是哈夫曼树的经典应用，本质是让小代价被累加更多次更不划算，所以每次合最小的两个。
- 数组为空或只有一个元素时总代价为 0，注意边界。

---

### class14 / Code03_BestArrange.java —— 会议室安排（最多会议场次）

**题目描述**：输入会议数组 `programs`，每个会议有开始时间 `start` 和结束时间 `end`，一个会议室同一时间只能进行一个会议。求最多能安排多少场会议（不重叠）。

**核心思路**：贪心——按**结束时间从早到晚**排序，依次考察每个会议，若其开始时间 ≥ 当前时间线 `timeLine`，就安排它（result++）并把 timeLine 更新为它的结束时间。结束时间越早结束越能给后面留时间，是最优选择。
- 解法一是暴力（枚举每个会议作为下一个安排），解法二是贪心排序，推荐解法二 O(N log N)。

**关键代码 / 步骤**：
```java
Arrays.sort(programs, (a, b) -> a.end - b.end);  // 结束时间升序
int timeLine = 0, result = 0;
for (Program p : programs) {
    if (timeLine <= p.start) {   // 不冲突就安排
        result++;
        timeLine = p.end;
    }
}
return result;
```

**复杂度**：时间 O(N log N)，空间 O(1)（排序外）。

**易错点 / 面试提示**：
- 贪心必须按"结束时间"排序，而非开始时间或时长，这是经典反例题。
- 判断条件用 `timeLine <= p.start`（前一会议结束时刻可作下一会议起点，依题意取等）。

---

### class14 / Code04_IPO.java —— IPO 最大化资本（双堆贪心）

**题目描述**：最多做 `K` 个项目，初始资金 `W`；每个项目有利润 `Profits[i]` 和启动所需资金 `Capital[i]`（等长）。只能做当前资金 ≥ 启动资金的项目，做完利润累加进资金。求做完最多 K 个项目后的最大总资金。（LeetCode 502）

**核心思路**：双优先队列贪心。
- `minCostQ`：按启动资金（成本）的**小根堆**，存所有还没做的项目；
- `maxProfitQ`：按利润的**大根堆**，存"当前资金够得着"的项目。
每一轮：把成本 ≤ 当前 W 的项目从成本小根堆全部解锁到利润大根堆，然后从利润大根堆弹出利润最大的去做、W 加上其利润。若利润堆空（没有项目可做）则提前结束返回 W。重复最多 K 次。

**关键代码 / 步骤**：
```java
PriorityQueue<Program> minCostQ   = new PriorityQueue<>((a,b)->a.c-b.c); // 成本小根堆
PriorityQueue<Program> maxProfitQ = new PriorityQueue<>((a,b)->b.p-a.p); // 利润大根堆
for (int i = 0; i < Profits.length; i++)
    minCostQ.add(new Program(Profits[i], Capital[i]));
for (int i = 0; i < K; i++) {
    while (!minCostQ.isEmpty() && minCostQ.peek().c <= W)
        maxProfitQ.add(minCostQ.poll());   // 解锁所有买得起的项目
    if (maxProfitQ.isEmpty()) return W;     // 没项目可做
    W += maxProfitQ.poll().p;               // 做利润最大的
}
return W;
```

**复杂度**：时间 O(N log N)，空间 O(N)。

**易错点 / 面试提示**：
- 贪心正确性：每一步在所有"能做"的项目里选利润最大的，资金单调增长所以解锁过的项目不会再被锁。
- 利润堆为空时必须提前返回，否则会卡死或越界。

---

### class14 / Code05_UnionFind.java —— 并查集（带路径压缩与按大小合并）

**题目描述**：实现并查集数据结构，支持：用一组初始值构造；`isSameSet(a,b)` 判断两元素是否同集合；`union(a,b)` 合并两集合；`sets()` 返回当前集合数量。

**核心思路**：用三张 HashMap：`nodes`（值→节点）、`parents`（节点→父节点）、`sizeMap`（代表节点→集合大小）。
- `findFather`：沿父指针一路向上找到代表（根，即父等于自身），并用栈记录路径后做**路径压缩**（把路径上所有节点的父直接指向根）。
- `union`：找两者代表，不同则按**集合大小**把小集合挂到大集合下，更新 sizeMap 并移除小集合的记录。
- `sets`：sizeMap 的 key 数即集合个数（只有代表节点保留 size 记录）。

**关键代码 / 步骤**：
```java
Node<V> findFather(Node<V> cur) {
    Stack<Node<V>> path = new Stack<>();
    while (cur != parents.get(cur)) { path.push(cur); cur = parents.get(cur); }
    while (!path.isEmpty()) parents.put(path.pop(), cur);  // 路径压缩
    return cur;
}
void union(V a, V b) {
    Node<V> aH = findFather(nodes.get(a)), bH = findFather(nodes.get(b));
    if (aH != bH) {
        int aS = sizeMap.get(aH), bS = sizeMap.get(bH);
        Node<V> big = aS >= bS ? aH : bH, small = big == aH ? bH : aH;
        parents.put(small, big);
        sizeMap.put(big, aS + bS);
        sizeMap.remove(small);   // 非代表节点不再保留 size
    }
}
```

**复杂度**：路径压缩 + 按大小合并下，单次操作近似 O(1)（均摊反阿克曼函数 α(N)）；空间 O(N)。

**易错点 / 面试提示**：
- 两大优化缺一不可：路径压缩（findFather 中回填）+ 按规模/秩合并，才能达到近 O(1)。
- `sets()` 依赖"只有代表节点在 sizeMap 中"这一不变式，union 时务必 `sizeMap.remove(small)`。
- 并查集是 Kruskal、岛屿数量、连通性等问题的核心工具。
