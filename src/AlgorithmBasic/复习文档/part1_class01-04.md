# 算法面试复习资料 —— class01 ~ class04

> 覆盖范围：`src/class01`、`src/class02`、`src/class03`、`src/class04` 全部 .java 源文件（共 21 个文件，一题一节）。
> 主题：基础排序、二分查找、位运算技巧、链表与栈/队列结构、归并排序及其衍生题。

---

## class01 基础排序与二分查找

### class01 / Code01_SelectionSort.java —— 选择排序

**题目描述**：给定一个整型数组 `arr`，实现选择排序，把数组原地（in-place）改为升序。输入是无序数组，输出是同一个数组被排成升序。约束：需处理 `null` 或长度 < 2 的边界（直接返回）。文件里还自带对数器（`comparator` 用 `Arrays.sort`、`generateRandomArray`、`isEqual`）做正确性验证。

**核心思路**：每一轮在「未排序区间 `[i, N-1]`」里找到最小值的下标 `minIndex`，把它交换到区间最左端 `i`。第 0 轮把全局最小值放到 0 位置，第 1 轮把次小值放到 1 位置……走 N-1 轮即可。本质是「每次确定一个位置上的最终元素」。选择排序无论数据是否有序都做同样次数的比较，不具备稳定性优势。

**关键代码 / 步骤**：
```java
for (int i = 0; i < arr.length - 1; i++) {
    int minIndex = i;
    for (int j = i + 1; j < arr.length; j++) {   // 在 [i, N-1] 找最小值下标
        minIndex = arr[j] < arr[minIndex] ? j : minIndex;
    }
    swap(arr, i, minIndex);                        // 最小值换到 i 位置
}
```

**复杂度**：时间 O(N^2)（比较次数固定，与初始顺序无关）；空间 O(1)。

**易错点 / 面试提示**：
- 外层只需到 `N-2`（`i < length-1`），最后一个元素自然就位。
- 选择排序是**不稳定**排序（交换会打乱相等元素的相对顺序），面试官常拿它和插入/冒泡的稳定性对比追问。
- 这里 `swap` 用临时变量，是安全写法（与下面用异或的写法对比）。

---

### class01 / Code02_BubbleSort.java —— 冒泡排序

**题目描述**：实现冒泡排序，将整型数组原地排成升序。输入输出与选择排序相同，附带对数器验证。

**核心思路**：每一轮从左到右两两相邻比较，若左 > 右就交换，使得本轮最大值像气泡一样「冒」到区间最右端。第一轮把全局最大值放到 `N-1`，下一轮区间右界 `e` 缩减 1。走完所有轮次即有序。注意此处 `swap` 用**异或交换**，对相邻不同下标安全。

**关键代码 / 步骤**：
```java
for (int e = arr.length - 1; e > 0; e--) {   // 当前轮的右边界
    for (int i = 0; i < e; i++) {
        if (arr[i] > arr[i + 1]) {
            swap(arr, i, i + 1);             // 相邻逆序就交换
        }
    }
}
```

**复杂度**：时间 O(N^2)；空间 O(1)。

**易错点 / 面试提示**：
- 异或交换 `a=a^b; b=a^b; a=a^b;` **当 i==j（同一地址）时会把该位置清零**。这里因为只交换相邻不同下标 `i` 与 `i+1`，所以安全。
- 冒泡是**稳定**排序（只在严格大于时才交换）。
- 可加 `swapped` 标志位提前退出（本实现未加），面试可作为优化点提及。

---

### class01 / Code03_InsertionSort.java —— 插入排序

**题目描述**：实现插入排序，把整型数组原地排成升序。带对数器。

**核心思路**：维护「前缀 `[0, i]` 有序」的不变式。第 `i` 轮把 `arr[i]` 从右往左与前面的元素比较，只要前一个比它大就交换下沉，直到它落到正确位置。相当于把新牌插入已排好的手牌中。对近乎有序的数据非常快（内层很快停）。

**关键代码 / 步骤**：
```java
for (int i = 1; i < arr.length; i++) {                 // 让 [0, i] 有序
    for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
        swap(arr, j, j + 1);                           // 比前一个小就往前换
    }
}
```

**复杂度**：时间 最好 O(N)（已有序）、最坏/平均 O(N^2)（逆序）；空间 O(1)。这是三种 O(N^2) 排序里**唯一时间复杂度随数据状况变化**的。

**易错点 / 面试提示**：
- 内层循环的终止条件 `arr[j] > arr[j+1]` 与 `j >= 0` 用短路 `&&` 连在一起，顺序不能反（先判越界）。
- 插入排序是**稳定**的，且适合小规模/近乎有序数据，常作为快排/归并的小区间优化收尾。

---

### class01 / Code04_BSExist.java —— 二分查找：有序数组中某数是否存在

**题目描述**：给定一个**已排序**（升序）数组 `sortedArr` 和目标值 `num`，判断 `num` 是否存在，返回 `boolean`。带暴力对数器 `test`。

**核心思路**：经典二分。`L`、`R` 夹逼，取中点 `mid`，命中即返回 true；中点值大于目标往左半收缩 `R=mid-1`，否则往右半 `L=mid+1`。本实现循环条件是 `while (L < R)`（区间至少两个数时循环），跳出后再单独判断 `arr[L] == num` 处理剩下的一个数。

**关键代码 / 步骤**：
```java
while (L < R) {
    mid = L + ((R - L) >> 1);
    if (sortedArr[mid] == num) return true;
    else if (sortedArr[mid] > num) R = mid - 1;
    else L = mid + 1;
}
return sortedArr[L] == num;   // 跳出时 L==R，补判最后一个
```

**复杂度**：时间 O(logN)；空间 O(1)。

**易错点 / 面试提示**：
- 中点写成 `L + ((R - L) >> 1)` 而非 `(L+R)/2`，**防止 L+R 整型溢出**，是面试常考细节。
- 此处用 `L < R` 必须在循环外补判 `arr[L]`，否则会漏掉单元素情况；若用 `L <= R` 则不需要补判。两种写法都对，要讲清边界自洽。

---

### class01 / Code05_BSNearLeft.java —— 二分查找：找 >= value 的最左位置

**题目描述**：在升序数组 `arr` 中，找到「满足 `arr[i] >= value`」的**最左**下标；不存在则返回 -1。带暴力对数器。

**核心思路**：二分变体。每次中点若 `arr[mid] >= value`，说明它是一个候选答案，记录 `index = mid`，并继续往**左**找更靠左的候选（`R = mid - 1`）；否则往右（`L = mid + 1`）。用一个 `index` 变量贯穿记录目前找到的最左满足位置。循环用 `L <= R`。

**关键代码 / 步骤**：
```java
int index = -1;
while (L <= R) {
    int mid = L + ((R - L) >> 1);
    if (arr[mid] >= value) { index = mid; R = mid - 1; }  // 满足→记录并继续左探
    else { L = mid + 1; }
}
return index;
```

**复杂度**：时间 O(logN)；空间 O(1)。

**易错点 / 面试提示**：
- 关键在于「满足条件后不立即返回，而是记录并继续向左收缩」，这是「找边界」类二分的通用套路。
- 与 `lower_bound` 语义一致。容易写错成找到就返回，导致拿到的不是最左。

---

### class01 / Code05_BSNearRight.java —— 二分查找：找 <= value 的最右位置

**题目描述**：在升序数组 `arr` 中，找到「满足 `arr[i] <= value`」的**最右**下标；不存在返回 -1。带暴力对数器。

**核心思路**：与 NearLeft 对称。中点若 `arr[mid] <= value`，记录候选 `index = mid` 并继续向**右**找更靠右的（`L = mid + 1`）；否则向左 `R = mid - 1`。

**关键代码 / 步骤**：
```java
int index = -1;
while (L <= R) {
    int mid = L + ((R - L) >> 1);
    if (arr[mid] <= value) { index = mid; L = mid + 1; }  // 满足→记录并继续右探
    else { R = mid - 1; }
}
return index;
```

**复杂度**：时间 O(logN)；空间 O(1)。

**易错点 / 面试提示**：
- 与 NearLeft 是镜像，区别仅在比较方向（`<=`/记录后 `L=mid+1`）。面试时容易把两个方向写混。
- 这两题合起来构成「在有序数组上找某条件的左右边界」的经典模板，强烈建议背熟。

---

### class01 / Code06_BSAwesome.java —— 二分查找：无序数组找任一局部最小

**题目描述**：给定数组 `arr`，**相邻元素一定不相等**（这是隐含前提），要求返回**任意一个**局部最小值的下标。局部最小定义：`arr[0] < arr[1]`（0 处为局部最小），或 `arr[N-1] < arr[N-2]`（末尾为局部最小），或中间某 `i` 满足 `arr[i] < arr[i-1] 且 arr[i] < arr[i+1]`。空数组返回 -1。此题精髓是**在无序数组上也能用二分**。

**核心思路**：先处理两端边界：长度 1 或左端下降则返回 0；右端下降则返回 N-1。否则两端都「向内下降」，即 `arr[0] > arr[1]` 且 `arr[N-1] > arr[N-2]`，那么 `[1, N-2]` 之间必存在局部最小。二分时看中点与两侧关系：若 `arr[mid] > arr[mid-1]`（左侧更低）则往左找；若 `arr[mid] > arr[mid+1]`（右侧更低）则往右找；否则 mid 本身就是局部最小。利用「下降趋势必终结于一个谷」的单调性保证收敛。

**关键代码 / 步骤**：
```java
if (arr.length == 1 || arr[0] < arr[1]) return 0;
if (arr[N-1] < arr[N-2]) return N - 1;
int left = 1, right = N - 2;
while (left < right) {
    int mid = (left + right) / 2;
    if (arr[mid] > arr[mid - 1])      right = mid - 1;   // 左边在下降，谷在左
    else if (arr[mid] > arr[mid + 1]) left  = mid + 1;   // 右边在下降，谷在右
    else return mid;                                     // 两侧都更高，mid 是谷
}
return left;
```

**复杂度**：时间 O(logN)；空间 O(1)。

**易错点 / 面试提示**：
- 核心论点：**二分不要求整体有序，只要能根据中点局部信息排除一半**。这是高频考点的思想题。
- 必须有「相邻不相等」前提，否则局部最小定义会失效。
- 此处 `mid = (left+right)/2` 没用防溢出写法（区间为 `[1, N-2]`，工程上仍建议统一用 `L + ((R-L)>>1)`）。

---

## class02 位运算技巧

### class02 / Code01_Swap.java —— 异或交换演示

**题目描述**：演示用异或（XOR）实现两个变量/数组元素的**不借助临时变量**的交换，并演示它的「陷阱」：当对同一个内存地址自己和自己交换时会被清零。无算法目标，是教学/演示类。

**核心思路**：异或三步交换 `a=a^b; b=a^b; a=a^b;` 利用 `x^x=0`、`x^0=x`、异或可交换结合 的性质完成交换，前提是 `a` 与 `b` 是**两块不同的内存**。`main` 里先演示 `int a,b` 交换成功，再演示 `arr[i]` 与 `arr[j]` 在 `i==j` 时（`swap(arr,0,0)`）把该位置变成 0 的 bug。

**关键代码 / 步骤**：
```java
a = a ^ b;   // a' = a^b
b = a ^ b;   // b' = (a^b)^b = a
a = a ^ b;   // a'' = (a^b)^a = b   → 完成交换
// 但若 i==j：arr[i]=arr[i]^arr[i]=0，自身被清零
```

**复杂度**：时间 O(1)；空间 O(1)。

**易错点 / 面试提示**：
- 数组里写通用 `swap(arr,i,j)` 时**必须保证 i != j**，否则异或交换会把该元素清零——这是异或交换最经典的坑。
- 异或交换不依赖加减，不会溢出，但可读性差且对同址不安全，工程上常用临时变量更稳妥。

---

### class02 / Code02_EvenTimesOddTimes.java —— 出现奇数次的数

**题目描述**：包含两个子问题。
1. `printOddTimesNum1`：数组里**只有一种数出现奇数次**，其余都出现偶数次，找出这个数。
2. `printOddTimesNum2`：数组里**恰有两种数出现奇数次**（设为 a、b，a≠b），其余偶数次，找出这两个数。
另含 `bit1counts`：统计一个整数二进制里 1 的个数。

**核心思路**：
- 问题1：全员异或，偶数次的两两抵消为 0，最终 `eor` 就是那个奇数次的数。
- 问题2：全员异或得 `eor = a ^ b`（≠0）。取 `eor` 最右侧的 1（`rightOne = eor & (-eor)`），a 和 b 在这一位上必然不同（一个为 0 一个为 1）。再遍历，只对「该位为 1」的数异或，得到 a、b 中的一个 `onlyOne`，另一个就是 `eor ^ onlyOne`。
- `bit1counts`：反复提取并清除最右的 1（`N & (~N + 1)`）来计数。

**关键代码 / 步骤**：
```java
// 问题2
int eor = 0; for (int x : arr) eor ^= x;       // eor = a^b
int rightOne = eor & (-eor);                   // 取最右的 1
int onlyOne = 0;
for (int x : arr) if ((x & rightOne) != 0) onlyOne ^= x;  // 只异或该位为1的数
System.out.println(onlyOne + " " + (eor ^ onlyOne));
```

**复杂度**：时间 O(N)；空间 O(1)。`bit1counts` 为 O(该数1的个数)。

**易错点 / 面试提示**：
- `eor & (-eor)`（等价 `eor & (~eor + 1)`）是「提取最右侧的 1」的经典咒语，务必记牢。
- 问题2能成立的关键论证：a≠b ⇒ a^b≠0 ⇒ 至少有一位不同 ⇒ 按该位把数组分成两组，a、b 必落在不同组且各组内偶数次数仍成对抵消。

---

### class02 / Code03_KM.java —— 一种数出现K次、其余出现M次，找那个数

**题目描述**：给定数组 `arr` 和两个数 `k < m`，保证「**恰有一种数出现了 k 次，其它所有种类的数都恰好出现 m 次**」，找出那个出现 k 次的数；若无法满足（题目用作判错）返回 -1。`test` 是用 HashMap 计数的暴力解法，`onlyKTimes` 是位运算解法，`main` 用对数器对比两者。

**核心思路**：开一个 `int[32]` 统计所有数在每个二进制位上「1 的总出现次数」。如果某位上「1 的总数 % m != 0」，说明这一位被那个出现 k 次的数贡献了；若 `% m == k` 则答案该位为 1，否则数据非法返回 -1。最后把所有判定为 1 的位拼成答案 `ans`。还需特判 `ans == 0`（那个数可能就是 0）：统计 0 出现次数是否等于 k。`mapCreater` 建立「2^i → i」的映射，便于由 `rightOne` 反查位号。

**关键代码 / 步骤**：
```java
int[] t = new int[32];
for (int num : arr)
    while (num != 0) {
        int rightOne = num & (-num);
        t[map.get(rightOne)]++;     // 该位上的 1 计数 +1
        num ^= rightOne;
    }
int ans = 0;
for (int i = 0; i < 32; i++)
    if (t[i] % m != 0) {
        if (t[i] % m == k) ans |= (1 << i);
        else return -1;             // 既不是 m 的倍数也不是 +k，非法
    }
// ans==0 时特判：统计 0 的个数是否为 k
```

**复杂度**：时间 O(N)（32 是常数）；空间 O(1)（固定 32 大小）。暴力 `test` 为 O(N) 时间、O(N) 空间（HashMap）。推荐位运算解，空间常数级。

**易错点 / 面试提示**：
- 对每位取模 `m`：出现 m 次的数对该位贡献是 m 的整数倍，取模后归零，剩余的就是那个 k 次数的贡献。
- 最易漏的特判是**答案是 0**：此时所有位 `% m == 0`，必须单独数 0 出现的次数。
- 由 `rightOne`（2 的幂）映射回位下标，避免每位手动移位判断。

---

## class03 链表、栈与队列

### class03 / Code01_ReverseList.java —— 反转单链表 / 双链表

**题目描述**：实现两个功能：反转**单向链表**（`reverseLinkedList`）和反转**双向链表**（`reverseDoubleList`），返回新头节点。文件内定义了 `Node`（单链表）与 `DoubleNode`（双链表，含 `last`/`next`），并带对数器（用 ArrayList 的方式重做一遍校验）。

**核心思路**：三指针迭代。维护 `pre`（已反转部分的头）、`head`（当前节点）、`next`（暂存后继防止断链）。每步：存后继 → 当前指针反指 `pre` → `pre`、`head` 各前移一步。双链表多一步把 `last` 也指向 `next`。遍历结束 `pre` 即新头。

**关键代码 / 步骤**：
```java
Node pre = null, next = null;
while (head != null) {
    next = head.next;     // 1.暂存后继
    head.next = pre;      // 2.指针反转
    pre = head;           // 3.pre 前移
    head = next;          // 4.head 前移
}
return pre;
// 双链表额外：head.last = next;
```

**复杂度**：时间 O(N)；空间 O(1)。

**易错点 / 面试提示**：
- **必须先保存 `next` 再改 `head.next`**，否则链断了找不到后续节点。
- 双链表别忘了同时维护 `last` 指针。
- 也可用递归实现（隐式栈空间 O(N)），面试可对比迭代法的空间优势。

---

### class03 / Code02_DeleteGivenValue.java —— 删除链表中指定值的所有节点

**题目描述**：给定单链表头 `head` 和一个值 `num`，删除链表中所有值等于 `num` 的节点，返回新头（头部也可能要删，所以必须返回新 head）。

**核心思路**：分两步。第一步让 `head` 一路后移，跳过开头连续等于 `num` 的节点，找到第一个不需要删的节点作为新头（也可能整条都删完变成 null）。第二步用 `pre`/`cur` 双指针遍历剩余部分：`cur.value == num` 时让 `pre.next` 跨过 `cur`；否则 `pre` 前移到 `cur`。`cur` 每轮都前移。

**关键代码 / 步骤**：
```java
while (head != null && head.value == num) head = head.next;  // 处理开头要删的
Node pre = head, cur = head;
while (cur != null) {
    if (cur.value == num) pre.next = cur.next;   // 跨过 cur，pre 不动
    else                  pre = cur;             // 保留，pre 跟进
    cur = cur.next;
}
return head;
```

**复杂度**：时间 O(N)；空间 O(1)。

**易错点 / 面试提示**：
- 头节点可能被删，所以**必须返回新的 head**，不能假设头不变。
- 删除时 `pre` 不前移（仍指向最后一个保留节点），只有保留时 `pre` 才跟到 `cur`——这是双指针删除的关键。
- 用「虚拟头节点 dummy」可统一逻辑、免去开头特判，是常见替代写法。

---

### class03 / Code03_DoubleEndsQueueToStackAndQueue.java —— 用双端队列实现栈和队列

**题目描述**：先手写一个泛型**双端队列** `DoubleEndsQueue<T>`（基于双向链表，支持头尾插入/弹出），再用它分别封装出 `MyStack`（栈）和 `MyQueue`（队列）。`main` 用对数器与 JDK 的 `Stack`/`Queue` 对比验证。

**核心思路**：双端队列提供 `addFromHead/addFromBottom/popFromHead/popFromBottom`。栈是 LIFO：从头进、从头出（`push=addFromHead`，`pop=popFromHead`）。队列是 FIFO：从头进、从尾出（`push=addFromHead`，`poll=popFromBottom`）。底层用带 `head`/`tail` 指针的双向链表，注意单元素弹出时要同时把 `head`、`tail` 置空。

**关键代码 / 步骤**：
```java
public T popFromHead() {
    if (head == null) return null;
    Node<T> cur = head;
    if (head == tail) { head = null; tail = null; }   // 只有一个元素
    else { head = head.next; cur.next = null; head.last = null; }
    return cur.value;
}
// MyStack:  push→addFromHead, pop→popFromHead     (同端进出 = LIFO)
// MyQueue:  push→addFromHead, poll→popFromBottom  (异端进出 = FIFO)
```

**复杂度**：所有操作 O(1)；空间 O(N)。

**易错点 / 面试提示**：
- 双向链表增删时**指针维护要成对**（`next`/`last` 互指），尤其单元素和空表的边界。
- 栈 = 同端进出，队列 = 异端进出，这是统一用双端队列实现两者的本质区别。

---

### class03 / Code04_RingArray.java —— 环形数组实现固定容量队列

**题目描述**：用一个固定大小的数组实现一个有容量上限 `limit` 的队列（FIFO），支持 `push`、`pop`、`isEmpty`。队列满时 push 抛异常，空时 pop 抛异常。

**核心思路**：环形缓冲。`pushi`（下次入队位置）、`polli`（下次出队位置）、`size`（当前元素数）三个变量。`push` 写入 `pushi` 后用 `nextIndex` 让指针「绕回」（到末尾回 0）；`pop` 从 `polli` 取并同样绕回。**用 `size` 而非指针关系来判断空/满**，从而避免「头尾指针相等既可能空也可能满」的经典歧义。

**关键代码 / 步骤**：
```java
private int nextIndex(int i) { return i < limit - 1 ? i + 1 : 0; }  // 绕回

public void push(int value) {
    if (size == limit) throw new RuntimeException("队列满了");
    size++; arr[pushi] = value; pushi = nextIndex(pushi);
}
public int pop() {
    if (size == 0) throw new RuntimeException("队列空了");
    size--; int ans = arr[polli]; polli = nextIndex(polli); return ans;
}
```

**复杂度**：所有操作 O(1)；空间 O(limit)。

**易错点 / 面试提示**：
- 用 `size` 计数解耦了 push/poll 两个指针，是处理「空与满都表现为 head==tail」歧义的最干净方案（另一种是浪费一格）。
- `nextIndex` 的绕回逻辑（到末尾回到 0）是环形数组的核心。

---

### class03 / Code05_GetMinStack.java —— 能在O(1)拿到最小值的栈

**题目描述**：设计一个栈，除了正常 `push`/`pop`，还能在 **O(1)** 时间返回当前栈中的最小值 `getmin`。提供两种实现 `MyStack1`、`MyStack2`。

**核心思路**：用一个辅助栈 `stackMin` 同步记录最小值。
- `MyStack1`（压缩版）：只有当新值 `<=` 当前最小值时才压入 `stackMin`；pop 时若弹出的值等于当前 min 才同步弹 `stackMin`。`stackMin` 元素较少。
- `MyStack2`（同步版）：每次 push 都往 `stackMin` 压「当前最小值」（新值与栈顶 min 取较小），pop 时两个栈都弹。逻辑更直白，但 `stackMin` 始终与 `stackData` 等高。
两者 getmin 都返回 `stackMin.peek()`。

**关键代码 / 步骤**：
```java
// MyStack1.push
if (stackMin.isEmpty() || newNum <= getmin()) stackMin.push(newNum);
stackData.push(newNum);
// MyStack1.pop
int value = stackData.pop();
if (value == getmin()) stackMin.pop();   // 只有等于 min 才同步弹
return value;
```

**复杂度**：push/pop/getmin 均 O(1)；空间 O(N)。

**易错点 / 面试提示**：
- `MyStack1` 入栈条件必须是 `<=`（不能是 `<`），否则有重复最小值时，pop 一次就把 min 弹丢了——这是最容易出错的边界。
- 两种实现都对，面试可讨论空间权衡（MyStack1 在 min 很少更新时省空间，MyStack2 实现更简单不易错）。

---

### class03 / Code06_TwoStacksImplementQueue.java —— 两个栈实现队列

**题目描述**：用两个栈实现一个队列（FIFO），支持 `add`/`poll`/`peek`。

**核心思路**：`stackPush` 专门收新元素，`stackPop` 专门出元素。倒数据规则 `pushToPop`：**仅当 `stackPop` 为空时**，把 `stackPush` 里的元素**一次性全部**倒进 `stackPop`（顺序反转两次后恢复成 FIFO 顺序）。每次 add 后调一次倒数据，每次 poll/peek 前调一次。

**关键代码 / 步骤**：
```java
private void pushToPop() {
    if (stackPop.empty()) {                 // 必须等 pop 栈空了才倒
        while (!stackPush.empty())
            stackPop.push(stackPush.pop()); // 全部一次性倒入
    }
}
public int poll() { pushToPop(); return stackPop.pop(); }
```

**复杂度**：均摊 O(1)（每个元素最多被倒一次），单次最坏 O(N)；空间 O(N)。

**易错点 / 面试提示**：
- 两条铁律：**(1) 倒数据必须 `stackPop` 为空时才倒；(2) 要倒就一次倒完**。违反任一条都会破坏 FIFO 顺序。
- 摊还分析是面试常考点：N 次操作总搬运 O(N)，故均摊 O(1)。

---

### class03 / Code07_TwoQueueImplementStack.java —— 两个队列实现栈

**题目描述**：用两个队列实现一个栈（LIFO），支持 `push`/`poll`/`peek`/`isEmpty`。

**核心思路**：`queue` 存数据，`help` 辅助。`push` 直接进 `queue`。`poll` 时把 `queue` 里除最后一个外的元素全倒进 `help`，剩下的那个就是栈顶（最后进的），弹出它，然后**交换 `queue` 与 `help` 的引用**。`peek` 同理，但取出栈顶后要再放回 `help`，再交换。

**关键代码 / 步骤**：
```java
public T poll() {
    while (queue.size() > 1) help.offer(queue.poll());  // 留最后一个
    T ans = queue.poll();                               // 它就是栈顶
    Queue<T> tmp = queue; queue = help; help = tmp;     // 交换引用
    return ans;
}
public T peek() {  // 同上，但取出后 help.offer(ans) 放回再交换
    while (queue.size() > 1) help.offer(queue.poll());
    T ans = queue.poll(); help.offer(ans);
    Queue<T> tmp = queue; queue = help; help = tmp;
    return ans;
}
```

**复杂度**：push O(1)；poll/peek O(N)；空间 O(N)。

**易错点 / 面试提示**：
- 每次出栈/查看都要遍历搬动 N-1 个元素，**交换引用**是避免来回拷贝的关键技巧。
- `peek` 与 `poll` 的区别：peek 要把那个栈顶元素重新放回 help。
- 与「两栈实现队列」对比：两栈实现队列均摊 O(1)，而两队列实现栈每次出栈都是 O(N)，效率较差，面试常对比这点。

---

### class03 / Code08_GetMax.java —— 递归求数组最大值（递归与Master公式）

**题目描述**：用**递归分治**求数组 `arr` 的最大值。是讲解递归行为与 Master（主定理）公式复杂度分析的教学题。

**核心思路**：把 `[L, R]` 从中点切两半，分别递归求左半最大、右半最大，再取较大者返回。base case：`L == R` 时区间只有一个数，直接返回。本质是分治；其递归式 `T(N) = 2*T(N/2) + O(1)`。

**关键代码 / 步骤**：
```java
int process(int[] arr, int L, int R) {
    if (L == R) return arr[L];                 // base case
    int mid = L + ((R - L) >> 1);
    int leftMax  = process(arr, L, mid);
    int rightMax = process(arr, mid + 1, R);
    return Math.max(leftMax, rightMax);
}
```

**复杂度**：时间 O(N)；空间 O(logN)（递归栈深度）。由 Master 公式 `T(N)=2T(N/2)+O(N^0)`，`log_2(2)=1 > 0`，故 O(N)。

**易错点 / 面试提示**：
- 这题真正考点是**会用 Master 公式分析分治复杂度**：`T(N)=a*T(N/b)+O(N^d)`，比较 `log_b(a)` 与 `d`。
- 中点同样用 `L + ((R-L)>>1)` 防溢出。

---

### class03 / HashMapAndSortedMap.java —— 哈希表与有序表（TreeMap）演示

**题目描述**：教学/演示类，无算法题。展示 Java 中 `HashMap`/`HashSet`（无序表）与 `TreeMap`（有序表）的行为差异，以及**值传递 vs 引用传递**在哈希表 key 上的表现、`Integer` 缓存与 `==`/`equals` 的坑。

**核心思路 / 要点**：
- 哈希表对**基础类型/包装类/String 按「值」**判等去重（`19000000` 两个 Integer 装箱后 `==` 为 false，但作为 key 视为同一个）；对**自定义对象按「引用地址」**判等（两个内容相同的 `Zuo` 实例被视为不同 key）。
- `Integer` 有 `-128~127` 缓存池：`Integer e=127,f=127; e==f` 为 true，但 `100000` 则为 false；比较值要用 `.equals`。
- 哈希表增删改查均摊 **O(1)**；`TreeMap`（红黑树/AVL/SB树/跳表 实现的有序表）为 **O(logN)**，但额外支持 `firstKey/lastKey/floorKey(<=)/ceilingKey(>=)` 等有序操作。

**关键代码 / 步骤**：
```java
treeMap.floorKey(4);    // 返回 <= 4 的最大 key
treeMap.ceilingKey(4);  // 返回 >= 4 的最小 key
treeMap.firstKey();     // 最小 key
treeMap.lastKey();      // 最大 key   —— 这些都是 O(logN)
```

**复杂度**：HashMap 操作均摊 O(1)；TreeMap 操作 O(logN)。

**易错点 / 面试提示**：
- 比较两个 `Integer` 的值**一定用 `.equals`**，别用 `==`（缓存池范围外会出错）。
- 哈希表里放**自定义类**做 key 时，若想按内容去重需正确重写 `hashCode`/`equals`，否则按地址区分。
- 何时用有序表：需要范围查询/前驱后继/最值时选 TreeMap，纯增删查选 HashMap（更快）。

---

## class04 归并排序及其衍生题

### class04 / Code01_MergeSort.java —— 归并排序（递归 + 非递归）

**题目描述**：实现归并排序把数组排成升序，提供**递归版** `mergeSort1` 和**非递归（自底向上）版** `mergeSort2`，`main` 用两者互相对数验证。

**核心思路**：
- 递归版：把 `[L,R]` 二分，递归排好左右两半，再 `merge` 合并两个有序段。`merge` 用辅助数组、双指针归并，谁小拷谁。
- 非递归版：步长 `mergeSize` 从 1 开始，每轮把数组按步长两两分组合并，步长翻倍，直到覆盖全数组。需小心处理「右组不足一个完整步长」和**步长左移溢出**（`if (mergeSize > N/2) break;`）。

**关键代码 / 步骤**：
```java
// merge：合并两个有序段 [L,M] 与 [M+1,R]
int p1 = L, p2 = M + 1, i = 0; int[] help = new int[R - L + 1];
while (p1 <= M && p2 <= R) help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
while (p1 <= M) help[i++] = arr[p1++];
while (p2 <= R) help[i++] = arr[p2++];
for (i = 0; i < help.length; i++) arr[L + i] = help[i];
```

**复杂度**：时间 O(N*logN)（`T(N)=2T(N/2)+O(N)`，Master 公式 `log_2(2)=1=d` 故 N^1*logN）；空间 O(N)（辅助数组）。

**易错点 / 面试提示**：
- merge 时 `arr[p1] <= arr[p2]` 用 `<=` 优先拷左组，保证**稳定性**。
- 归并排序之所以比 O(N^2) 排序快，本质是「**没有浪费比较**」：相邻有序段合并时利用了之前排序的结果。
- 非递归版的 `mergeSize > N/2 → break` 是防止 `mergeSize<<=1` 整型溢出的关键边界。

---

### class04 / Code02_SmallSum.java —— 小和问题

**题目描述**：对数组中每个数，把它**左边所有比它小的数**累加，即为该数的「小和」；整个数组的小和为所有数小和之和。例 `[1,3,4,2,5]` 的小和为 16。返回整个数组的小和。`comparator` 是 O(N^2) 暴力。

**核心思路**：在**归并排序的 merge 过程中顺带统计**。换个视角：一个数 `arr[p1]` 会作为「小和」贡献给它**右边所有比它大的数**。merge 左右两个有序组时，若 `arr[p1] < arr[p2]`，则右组从 `p2` 到末尾的 `(r - p2 + 1)` 个数都比 `arr[p1]` 大，于是 `arr[p1]` 贡献 `arr[p1] * (r - p2 + 1)`。利用右组有序，一次比较就批量算出贡献，把 O(N^2) 降到 O(N*logN)。

**关键代码 / 步骤**：
```java
while (p1 <= m && p2 <= r) {
    res += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] : 0;  // 批量贡献
    help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
}
```

**复杂度**：时间 O(N*logN)；空间 O(N)。暴力 O(N^2)。

**易错点 / 面试提示**：
- 此处比较**必须用严格 `<`**（相等时先拷右组，不能算贡献也不能合并方向反），否则相等元素会重复/漏算——这是与普通归并 `<=` 的关键区别。
- 视角转换是精髓：「左边比它小的数之和」⇔「它对右边比它大的数的贡献之和」，后者才能在 merge 中批量计算。

---

### class04 / Code03_ReversePair.java —— 逆序对数量

**题目描述**：统计数组中**逆序对**的个数：满足 `i < j` 且 `arr[i] > arr[j]` 的对数。例 `[3,1,4,2]` 有 3 个。`comparator` 是 O(N^2) 暴力。

**核心思路**：仍是 merge 中统计。此实现从**右往左**归并（`p1=m`、`p2=r`，help 从尾部填）。当 `arr[p1] > arr[p2]` 时，因为右组有序，右组中从 `p2` 到 `m+1` 的所有数（共 `p2 - m` 个）都比 `arr[p1]` 小，故一次性产生 `(p2 - m)` 个逆序对；然后把较大的 `arr[p1]` 先放到 help 尾部。

**关键代码 / 步骤**：
```java
int p1 = m, p2 = r, i = help.length - 1, res = 0;
while (p1 >= L && p2 > m) {
    res += arr[p1] > arr[p2] ? (p2 - m) : 0;          // 批量逆序对
    help[i--] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];  // 大的先放尾部
}
```

**复杂度**：时间 O(N*logN)；空间 O(N)。暴力 O(N^2)。

**易错点 / 面试提示**：
- 从右往左归并是这道题统计逆序对的常见写法；正向写法也可以，但计数公式要相应调整，别混。
- 比较 `arr[p1] > arr[p2]`（严格大于）才算逆序对；相等不算。
- 与小和题是「同一套 merge 模板，换统计公式」，面试常作为归并衍生题组合考。

---

### class04 / Code04_BiggerThanRightTwice.java —— 翻倍逆序对（一个数大于右边某数的两倍）

**题目描述**：统计满足 `i < j` 且 `arr[i] > 2 * arr[j]` 的数对 `(i, j)` 的个数。例 `[6,3,2,1,0]` 共 6 个。`comparator` 是 O(N^2) 暴力。

**核心思路**：归并衍生，但**统计与合并分两段做**。在 merge 里先用**滑动窗口**统计跨组数对：左右组都有序，对左组每个 `arr[i]`，维护右组指针 `windowR`，让它一直右移到第一个不满足 `arr[i] > 2*arr[windowR]` 的位置，则满足的个数是 `windowR - (m+1)`。由于左组递增，`windowR` 只增不减（单调），所以统计是 O(N)。统计完后再做**普通的归并合并**（用 `<=`）把两组排序。

**关键代码 / 步骤**：
```java
int ans = 0, windowR = m + 1;
for (int i = L; i <= m; i++) {                       // 左组每个数（左组有序）
    while (windowR <= r && arr[i] > arr[windowR] * 2)
        windowR++;                                   // 窗口只增不减
    ans += windowR - m - 1;                          // 满足个数
}
// 之后再正常 merge 排序（不要把统计和排序混在一个比较里）
```

**复杂度**：时间 O(N*logN)；空间 O(N)。暴力 O(N^2)。

**易错点 / 面试提示**：
- **统计与合并必须分开两段**：因为判定条件是 `arr[i] > 2*arr[j]`，和排序时的 `arr[i] <= arr[j]` 比较口径不同，混在一起会算错（这是它与小和/逆序对题最大的不同点）。
- 滑动窗口 `windowR` 单调不回退，是把统计降到 O(N) 的关键；若每次从头扫则退化成 O(N^2)。
- 注意 `arr[windowR]*2` 可能溢出（本题数据范围小未处理），面试可提溢出风险，必要时改用 `long` 或 `arr[i] > 2L*arr[j]`。
