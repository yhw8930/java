# 算法面试复习资料 Part 2（class05 - class08）

涵盖：归并扩展应用、快速排序与荷兰国旗、比较器、堆与堆排序、加强堆及其应用、前缀树、计数排序与基数排序。

---

## class05 归并扩展应用 & 快速排序

### class05 / Code01_CountOfRangeSum.java —— 区间和的个数（Count of Range Sum）

**题目描述**：给定一个整数数组 `nums` 和两个整数 `lower`、`upper`，求有多少个区间 `[i, j]`（i <= j）满足该子数组之和落在 `[lower, upper]` 闭区间内。对应 LeetCode 327。返回满足条件的区间个数。

**核心思路**：把"子数组和"转化为前缀和差值问题：设 `sum[k]` 为前缀和，则区间 `[i..j]` 的和 = `sum[j] - sum[i-1]`，要求它在 `[lower, upper]` 内。固定右端点 `sum[j]`，等价于求有多少个左前缀 `sum[x]` 满足 `sum[j]-upper <= sum[x] <= sum[j]-lower`。利用归并排序：在 merge 阶段，左右两半各自有序，对右半每个 `arr[i]`，用滑动窗口在左半有序区间里数出落在 `[arr[i]-upper, arr[i]-lower]` 内的个数。这是"归并排序求满足某种偏序关系对数"的经典套路（与小和、逆序对同源）。注意前缀和要用 `long[]` 防止溢出。

**关键代码 / 步骤**：
```
sum[i] = 前缀和（long 防溢出）
process(L,R): 左半递归 + 右半递归 + merge 时统计跨越中点的合法对数
merge 统计部分（左半 [L..M] 有序）：
  windowL = windowR = L
  for i = M+1..R:           // 遍历右半每个前缀和
    min = arr[i] - upper
    max = arr[i] - lower
    while windowR<=M && arr[windowR] <= max: windowR++   // 右边界扩到 <=max 的下一个
    while windowL<=M && arr[windowL] <  min: windowL++   // 左边界推到第一个 >=min
    ans += windowR - windowL                              // 窗口内个数
  然后做正常的 merge 把 [L..R] 合并有序
```

**复杂度**：时间 O(N·logN)（每层 merge 的统计是 O(N) 摊还，因 windowL/windowR 单调不回退），空间 O(N)（help 数组 + 递归栈）。

**易错点 / 面试提示**：
- 前缀和必须用 `long`，否则累加会溢出。
- 统计窗口的两个指针 windowL、windowR 在同一次 merge 的 for 循环里**只增不减**，这是 O(N) 的关键，面试官常追问为什么是单调的（因为右半 `arr[i]` 递增，下界、上界也都随之非递减）。
- 别忘记区间含单个元素（`sum[L]` 本身落区间时计 1），靠递归 base case `L==R` 处理。

---

### class05 / Code02_PartitionAndQuickSort.java —— 划分函数与三个版本的快排

**题目描述**：实现数组原地排序。本文件展示 partition（单边小于等于划分）、荷兰国旗三向划分（netherlandsFlag），以及由它们构建的三个快排版本：quickSort1（1.0，普通 partition）、quickSort2（2.0，荷兰国旗）、quickSort3（3.0，随机基准 + 荷兰国旗），并用对数器验证三者一致。

**核心思路**：
- **partition**：以 `arr[R]` 为划分值，维护 `<=区` 右边界 `lessEqual`，遍历过程中遇到 `<=arr[R]` 就交换到 `<=区` 末尾扩边界，最后把基准换到中间，返回基准最终下标。划分成 `[<=X][X][>X]`。
- **netherlandsFlag（荷兰国旗）**：以 `arr[R]` 为值，维护 `<区右边界 less` 和 `>区左边界 more`，一次遍历分成 `[<X][=X][>X]` 三段，返回等于区的左右边界。相比 partition，一次能把所有等于基准的数归位，减少递归。
- **三个快排**：1.0 用 partition，每次确定一个数；2.0 用荷兰国旗，每次确定"一片"等于区；3.0 在 2.0 基础上随机选基准（把随机位置换到 R），把最坏 O(N²) 的期望降为 O(N·logN)。**推荐 3.0**（随机版荷兰国旗），工程与面试首选。

**关键代码 / 步骤**：
```
netherlandsFlag(arr, L, R):  // 以 arr[R] 为基准
  less = L-1; more = R; index = L
  while index < more:
    if arr[index] == arr[R]: index++
    elif arr[index] < arr[R]: swap(index++, ++less)
    else: swap(index, --more)      // 注意 index 不动
  swap(more, R)                     // 基准归位到等于区开头
  return [less+1, more]

process3(arr, L, R):
  if L>=R return
  swap(arr, L + rand*(R-L+1), R)    // 随机基准
  eq = netherlandsFlag(arr, L, R)
  process3(L, eq[0]-1); process3(eq[1]+1, R)
```

**复杂度**：1.0/2.0 最坏 O(N²)（有序或全相等且不随机时），平均 O(N·logN)；3.0 期望 O(N·logN)。空间为递归栈深度，期望 O(logN)，最坏 O(N)。

**易错点 / 面试提示**：
- 荷兰国旗里命中 `>X` 时 `swap(index, --more)` 后 **index 不能++**，因为换过来的新数还没判断。
- 2.0 相比 1.0 的优势在于"等于基准的数一次全部归位"，遇到大量重复值时优势明显。
- 3.0 随机化是把"最坏情况期望化"，面试常问为什么随机能避免 O(N²)：因为没有固定的恶意输入能持续命中坏基准。

---

### class05 / Code03_QuickSortRecursiveAndUnrecursive.java —— 快排的递归版与非递归（栈模拟）版

**题目描述**：在随机基准 + 荷兰国旗快排（3.0）的基础上，给出递归版 `quickSort1` 和用栈手动模拟递归的非递归版 `quickSort2`，对数器验证两者结果一致。

**核心思路**：递归版即标准 3.0。非递归版的关键是用一个栈保存"待处理区间"（`Op{l, r}`）来替代系统递归栈。先对整个数组做一次随机基准的荷兰国旗划分，把等于区两侧的两个子区间压栈；然后循环弹出区间，若 `l < r` 就再做一次随机划分并把新产生的左右子区间压栈，直到栈空。本质是把"函数调用栈"显式化为数据结构栈。

**关键代码 / 步骤**：
```
quickSort2(arr):
  对 [0, N-1] 随机基准 + netherlandsFlag，得等于区 [el, er]
  stack.push(Op(0, el-1)); stack.push(Op(er+1, N-1))
  while !stack.empty:
    op = stack.pop()
    if op.l < op.r:
      随机基准换到 op.r
      [el, er] = netherlandsFlag(arr, op.l, op.r)
      stack.push(Op(op.l, el-1))
      stack.push(Op(er+1, op.r))
```

**复杂度**：与递归版相同，期望时间 O(N·logN)，空间为栈中区间数，期望 O(logN)、最坏 O(N)。

**易错点 / 面试提示**：
- 非递归版的意义在于避免深递归导致的栈溢出，面试官常问"如何把递归改非递归"，答案是用栈手动保存待处理区间（状态）。
- 压栈时只需保存区间端点 `[l, r]`，不需要保存等于区（等于区已就地确定，无需再处理）。
- 入栈顺序不影响正确性（左右子区间相互独立）。

---

## class06 比较器、堆与堆排序

### class06 / Code01_Comparator.java —— 比较器（Comparator）的用法与规范

**题目描述**：演示 Java 比较器的统一规范及在排序、有序表中的应用。不是算法题，而是基础设施讲解：如何自定义对象/基础类型的排序规则。

**核心思路**：比较器规范——`compare(o1, o2)` 返回**负数**表示 o1 应排前面，**正数**表示 o2 排前面，**0** 表示无所谓。基于这个规范可任意组合排序规则（如 id 升序、id 相同则 age 降序：`o1.id != o2.id ? o1.id - o2.id : o2.age - o1.age`）。展示了 `Arrays.sort` 对数组、`List.sort` 对集合、以及 `TreeMap` 用比较器决定 key 顺序与去重（id 相同会被视为同一个 key）。

**关键代码 / 步骤**：
```
class IdShengAgeJiangOrder implements Comparator<Student> {
  compare(o1, o2) = o1.id != o2.id ? (o1.id - o2.id) : (o2.age - o1.age)
}
Arrays.sort(students, new IdShengAgeJiangOrder());
list.sort(new IdShengAgeJiangOrder());
TreeMap<Student,String> tm = new TreeMap<>((a,b) -> a.id - b.id);  // id 决定顺序与唯一性
```

**复杂度**：排序 O(N·logN)；TreeMap 增删查 O(logN)。

**易错点 / 面试提示**：
- 用 `o1 - o2` 做比较在数值很大/很小时可能整数溢出，更稳妥用 `Integer.compare`。
- TreeMap 用比较器时，比较返回 0 的元素被视为**同一个 key**（示例中所有 id 都是 4，最终只剩一个条目），这点常被坑。
- "返回负数第一个在前"是必须记牢的规范，写反就是逆序。

---

### class06 / Code02_Heap.java —— 手写大根堆（含 heapInsert / heapify）

**题目描述**：手动实现一个固定容量的大根堆 `MyMaxHeap`，支持 push、pop（弹出并返回最大值）、isEmpty、isFull；用一个朴素的 `RightMaxHeap`（每次线性扫描找最大）作为对数器验证正确性。

**核心思路**：用数组表示完全二叉树，下标 i 的父为 `(i-1)/2`，左右孩子为 `2i+1`、`2i+2`。**push**：新值放数组末尾，`heapInsert` 不断与父比较，比父大就上浮直到到顶或不再比父大。**pop**：取堆顶（最大值），把末尾元素换到堆顶并 `heapSize--`，再 `heapify` 让它下沉——每次与较大的孩子比较，比孩子小就下沉，直到没有更大的孩子或到底。

**关键代码 / 步骤**：
```
heapInsert(arr, index):                  // 上浮
  while arr[index] > arr[(index-1)/2]:
    swap(index, (index-1)/2); index = (index-1)/2

heapify(arr, index, heapSize):           // 下沉
  left = 2*index+1
  while left < heapSize:
    largest = (left+1<heapSize && arr[left+1]>arr[left]) ? left+1 : left
    largest = arr[largest] > arr[index] ? largest : index
    if largest == index: break
    swap(largest, index); index = largest; left = 2*index+1

pop(): ans = arr[0]; swap(0, --heapSize); heapify(0, heapSize); return ans
```

**复杂度**：push、pop 均 O(logN)；peek O(1)。空间 O(N)（数组）。

**易错点 / 面试提示**：
- pop 时务必"先把末尾换到堆顶再缩小 heapSize 再 heapify"，顺序错会丢元素或越界。
- heapify 里要先在左右孩子中选出较大者，再和父比较；`left+1 < heapSize` 的右孩子存在性判断不能漏。
- `(index-1)/2` 在 index=0 时得 0（自己比自己），循环自然停止，这是上浮终止的隐含条件。

---

### class06 / Code03_HeapSort.java —— 堆排序（额外空间 O(1)）

**题目描述**：实现原地堆排序 `heapSort`，把数组升序排列。额外空间 O(1)。

**核心思路**：两步。**建堆**：从最后一个位置往前对每个位置做 heapify（自底向上建堆），整体 O(N)；也可从前往后逐个 heapInsert，但那是 O(N·logN)，故注释里推荐前者。**排序**：建成大根堆后，反复把堆顶（最大值）与堆末尾交换，`heapSize--`，再对新堆顶 heapify，使次大值浮到顶；重复直到堆空，数组即升序。

**关键代码 / 步骤**：
```
heapSort(arr):
  for i = n-1 .. 0: heapify(arr, i, n)     // 自底向上建堆 O(N)
  heapSize = n
  swap(0, --heapSize)                       // 最大值放到末尾
  while heapSize > 0:
    heapify(arr, 0, heapSize)
    swap(0, --heapSize)
```

**复杂度**：建堆 O(N)（自底向上）或 O(N·logN)（逐个插入）；排序阶段 O(N·logN)。整体 O(N·logN)，额外空间 O(1)，不稳定。

**易错点 / 面试提示**：
- 自底向上建堆为何是 O(N)：底层节点多但下沉深度小，求和收敛到 O(N)，面试高频追问。
- 堆排序不稳定；空间 O(1) 是它相对归并的优势。
- 升序排序要用**大根堆**（每次把最大值丢到末尾），别记反成小根堆。

---

### class06 / Code04_SortArrayDistanceLessK.java —— 几乎有序数组排序（每个元素移动距离不超过 k）

**题目描述**：一个**几乎有序**的数组，每个元素到它排好序后的正确位置的距离不超过 k（k 较小）。要求高效排序。

**核心思路**：因为每个元素移动距离 <= k，所以排好序后下标 i 处的元素，一定来自原数组 `[i-k, i+k]` 范围内的某个数；换言之前 k+1 个数中必含最小值。用一个**小根堆**维护一个大小约为 k+1 的滑动窗口：先把前 k 个（下标 0..k-1）入堆，然后每读入一个新数就入堆并弹出堆顶（当前最小）放到结果位；最后清空堆。堆始终只保留 k+1 个元素，所以每次堆操作是 O(logk)。

**关键代码 / 步骤**：
```
sortedArrDistanceLessK(arr, k):
  小根堆 heap
  for index = 0 .. min(n-1, k-1): heap.add(arr[index])   // 先装 k 个
  i = 0
  for ; index < n; i++, index++:
    heap.add(arr[index])
    arr[i] = heap.poll()         // 窗口最小值落位
  while !heap.isEmpty: arr[i++] = heap.poll()
```

**复杂度**：时间 O(N·logk)，空间 O(k)（堆大小）。当 k 远小于 N 时优于通用 O(N·logN) 排序。

**易错点 / 面试提示**：
- 堆大小是 k+1 不是 k：先放 k 个，再每次"放一个、弹一个"维持窗口。
- 边界 `Math.min(arr.length-1, k-1)` 防止 k 大于数组长度时越界。
- k==0 直接返回（已有序）。这是堆"维护小窗口动态最值"的典型应用。

---

## class07 加强堆（HeapGreater）及应用

### class07 / Inner.java —— 泛型装箱辅助类

**题目描述**：一个极简的泛型包装类 `Inner<T>`，把任意类型 T 包成一个对象，含一个公有字段 `value`。

**核心思路**：加强堆 `HeapGreater<T>` 要求 T 必须是**非基础类型**（因为要用 HashMap 做对象到下标的反向索引，依赖对象的身份/引用相等）。当你需要往加强堆里放基础类型（如 int）时，就用 `Inner<Integer>` 这样的包装"包一层"，让每个元素成为独立的引用对象。它就是为这个目的准备的占位/演示类。

**关键代码 / 步骤**：
```
class Inner<T> { public T value; Inner(T v){ value = v; } }
// 用法：把基础类型包成独立对象再放进加强堆
HeapGreater<Inner<Integer>> heap = ...;
```

**复杂度**：无算法，构造 O(1)。

**易错点 / 面试提示**：
- 加强堆用 `indexMap`（HashMap）反查元素下标，若直接放基础类型会因装箱缓存/相等语义导致索引冲突，所以必须包一层成独立对象。
- 关联记忆 HeapGreater 类头注释："T 一定要是非基础类型，有基础类型需求包一层"。

---

### class07 / HeapGreater.java —— 加强堆（支持任意元素 O(logN) 删除 / 修改）

**题目描述**：实现一个"加强堆"——在普通堆 push/pop/peek 基础上，额外支持：判断某元素是否在堆中（contains）、删除堆中**任意**元素（remove）、当某元素的比较属性改变后重新调整其位置（resign），以及返回所有元素。

**核心思路**：普通堆删/改任意元素是 O(N)（找不到位置）。加强堆用一个 `HashMap<T, Integer> indexMap` 维护"元素 -> 它在堆数组里的下标"的反向索引，从而能 O(1) 定位任意元素，再做 O(logN) 的上浮/下沉。**remove**：用堆末尾元素覆盖被删位置，删末尾，然后对替换上来的元素 resign（重新 heapInsert + heapify）。**resign**：对该下标同时尝试上浮和下沉（只会有一个方向真正生效）。所有 swap 操作都同步更新 indexMap。

**关键代码 / 步骤**：
```
push(obj): heap.add(obj); indexMap.put(obj, heapSize); heapInsert(heapSize++)
remove(obj):
  replace = heap[heapSize-1]; index = indexMap.get(obj)
  indexMap.remove(obj); heap.remove(--heapSize)
  if obj != replace:
    heap[index] = replace; indexMap.put(replace, index); resign(replace)
resign(obj): heapInsert(indexMap.get(obj)); heapify(indexMap.get(obj))  // 只一个方向生效
swap(i,j): 交换 heap[i]/heap[j] 同时更新 indexMap 两个下标
// 用 comp.compare(...)<0 决定上浮，可同时支持大/小根堆
```

**复杂度**：push、pop、remove、resign 均 O(logN)；contains、size、peek O(1)；getAllElements O(N)。空间 O(N)（堆数组 + indexMap）。

**易错点 / 面试提示**：
- 每次 swap **必须同步更新 indexMap**，否则反向索引失效，是最常见 bug。
- remove 时若删的恰好就是末尾元素（`obj == replace`），不能再 set/resign，需要特判。
- resign 同时调 heapInsert 和 heapify 是安全的：元素相对原位置只会要么上浮要么下沉，另一个方向自然不动。
- T 必须是非基础类型（见 Inner.java），因 indexMap 依赖引用身份。

---

### class07 / Code01_CoverMax.java —— 线段最多重叠数（Max Cover）

**题目描述**：给定若干条线段（每条用 `[start, end]` 表示，开区间或视作端点不计重叠），求在某个位置上最多有多少条线段同时覆盖（最大重叠数）。

**核心思路**：
- **暴力解 maxCover1**：取所有线段最小 start、最大 end，对每个 `x.5`（避开整端点）统计有多少线段覆盖它，取最大。O(N · 值域)。
- **最优解 maxCover2（堆）**：把线段按 start 升序排序，依次处理；用一个**小根堆**保存"当前还没结束的线段的 end"。处理新线段时，先把堆中所有 `end <= 当前线段 start` 的弹出（它们与当前线段不重叠），再把当前线段 end 入堆——此刻堆的大小就是"同时覆盖当前线段起点的线段数"，取过程最大值即答案。**推荐 maxCover2**。

**关键代码 / 步骤**：
```
maxCover2(lines):
  按 start 升序排序
  小根堆 heap（存 end）
  max = 0
  for line in lines:
    while !heap.empty && heap.peek() <= line.start: heap.poll()  // 弹出已结束的
    heap.add(line.end)
    max = Math.max(max, heap.size())
  return max
```

**复杂度**：暴力 O(N · 值域)；堆解 O(N·logN)，空间 O(N)。

**易错点 / 面试提示**：
- 排序按 start，堆按 end（小根堆），两个排序维度不要混。
- 弹出条件用 `heap.peek() <= line.start`（端点相接不算重叠，取决于题意定义）；重叠定义是否含端点需向面试官确认。
- 思想：把线段起点作为"事件时间扫描线"，堆维护当前存活线段，是扫描线 + 堆的经典套路。

---

### class07 / Code02_EveryStepShowBoss.java —— 每一步实时返回 Top K 客户（购买量榜单）

**题目描述**：一系列事件按时间到来，每个事件是 `(id, 购买/退货)`。规则：用户购买数 = 累计买 - 退；购买数为 0 的用户视为不存在（移出系统）；要随时维护"购买量最多的 K 个用户"作为"得奖区(daddy)"。每处理完一个事件，返回当前得奖区里所有用户的 id 列表。退货时若用户本不存在则忽略。当多个用户购买量相同时，按进入候选/得奖区的时间（enterTime）早者优先保留在得奖区。

**核心思路**：维护两个加强堆——**得奖区 daddyHeap**（小根堆，堆顶是得奖区里购买量最小者，便于和候选者 PK 替换）和**候选区 candHeap**（大根堆，堆顶是候选区购买量最大者，便于晋升）。每个事件更新该用户的 buy；若 buy 变 0 则从所在堆 remove；否则若用户已在某堆则 resign（其购买量变了，需重新排位）；新用户先尝试进得奖区，满了进候选区。每步末尾 `daddyMove`：得奖区没满就从候选区拉最大的进来；满了就比较候选区最大 buy 与得奖区最小 buy，前者更大则二者交换（更新各自 enterTime）。加强堆的 contains/remove/resign 让这一切都是 O(logN)。还有一个朴素的 `compare`（每步全量排序模拟）作对数器。

**关键代码 / 步骤**：
```
operate(time, id, buyOrRefund):
  退货且用户不存在 -> 直接 return
  更新 buy（买++ / 退--）；buy==0 -> 从 customers 移除
  if 不在任何堆:
    daddyHeap.size < limit ? 进 daddy : 进 cand   (设 enterTime=time)
  elif 在 cand: buy==0 ? cand.remove : cand.resign
  else (在 daddy): buy==0 ? daddy.remove : daddy.resign
  daddyMove(time)

daddyMove(time):
  if cand 空: return
  if daddy 未满: 从 cand.pop() 晋升进 daddy
  elif cand.peek().buy > daddy.peek().buy:   // 候选最大 > 得奖最小
    交换 daddyHeap.pop() 与 candHeap.pop()（更新 enterTime）
```
- CandidateComparator：buy 大者优先（大根堆），buy 相同 enterTime 小者优先。
- DaddyComparator：buy 小者优先（小根堆），buy 相同 enterTime 小者优先（购买量并列时让待得越久者更"稳"，先被换出）。

**复杂度**：每个事件 O(logK)（K 为榜单大小，得奖区上限；候选区也用堆），总 O(N·logK)。空间 O(用户数)。朴素 compare 每步 O(N·logN)，总 O(N²·logN)。

**易错点 / 面试提示**：
- 两个堆的比较器方向相反是设计精髓：得奖区要快速拿"最弱"的去 PK，候选区要快速拿"最强"的来晋升。
- buy 改变后必须 resign，否则堆序失效；buy 归零要 remove 且从用户表删除。
- enterTime 用于打破 buy 相同时的平局，替换/晋升时要更新它（保证"先得奖且并列者"的相对稳定性）。
- 退货导致用户不存在时直接忽略，这个前置判断不能漏。

---

## class08 前缀树与非比较排序

### class08 / Code01_TrieTree.java —— 前缀树（含一个对数器对不上的 bug 练习版）

**题目描述**：实现前缀树（Trie），支持 insert、delete、search（某单词被加入过几次）、prefixNumber（有几个加入的字符串以某前缀开头）。文件头注释明确说明"该程序对数器跑不过，bug 在哪？"——这是一个**故意留 bug** 的教学版本。

**核心思路**：Trie 每个节点记两个计数：`pass`（经过该节点的字符串数）、`end`（以该节点结尾的字符串数），孩子用数组（Node1，26 字母）或 HashMap（Node2，任意字符）。insert 沿路径建节点并 `pass++`，结尾 `end++`。search 走到末尾返回 `end`，prefixNumber 走到前缀末尾返回 `pass`。Right 类是 HashMap 暴力实现作对数器。

**bug 所在**：对比"完全正确"的 Code02_TrieTree.java，本文件的暴力对照 `Right.prefixNumber` 用的是 `count++`（按**不同字符串个数**计），而 Trie 的 prefixNumber 返回的是节点 `pass`（**总加入次数，含重复**）。当同一字符串被重复插入时，Trie 的 pass 会大于 Right 的去重计数，二者语义不一致，对数器就报 Oops。正确版 Code02 把这里改成 `count += box.get(cur)`（累加每个 key 的次数）后即一致。这是本题刻意设置的"找 bug"练习点。

**关键代码 / 步骤**：
```
// 有 bug 的对照器（与 Trie 的 pass 语义不符）
Right.prefixNumber(pre):
  count = 0
  for key in box: if key.startsWith(pre): count++   // 应为 count += box.get(key)
  return count
// Trie 的 prefixNumber 返回 pass（含重复次数）
```

**复杂度**：insert/delete/search 均 O(单词长度)；prefixNumber O(前缀长度)。空间 O(所有字符总数)。

**易错点 / 面试提示**：
- pass 与 end 的区别：pass = 多少字符串"路过"此节点（=以此为前缀的字符串数，含重复），end = 多少字符串恰好在此结尾。
- delete 前必须先 search 确认存在，否则会把不存在的路径计数减成负或 NPE。
- 本题的核心面试价值是"找出对数器对不上的原因"：对照器的 prefixNumber 计去重个数，与 Trie 的 pass（含重复次数）语义不符。

---

### class08 / Code02_TrieTree.java —— 前缀树（完全正确版）

**题目描述**：与 Code01 同样的前缀树，但是"完全正确"的版本，对数器能通过。支持 insert、delete、search、prefixNumber。提供数组版（Node1，仅小写字母 a-z）和 HashMap 版（Node2，任意字符）两种实现。

**核心思路**：和 Code01 结构完全一致（pass / end 双计数）。**正确性关键在对照实现 Right.prefixNumber 用 `count += box.get(cur)`**——按"加入总次数"累加，与 Trie 节点 `pass` 的语义（含重复）一致，所以对数器通过。两种 Trie：数组版用 `chs[i]-'a'` 做下标，省哈希开销但仅限固定字符集；HashMap 版用字符 ASCII 做 key，支持任意字符集。

**关键代码 / 步骤**：
```
insert(word): node=root; node.pass++
  for ch in word:
    path = ch-'a'; if nexts[path]==null: nexts[path]=new Node
    node=nexts[path]; node.pass++
  node.end++
search(word):  沿路径走，断路返回 0，否则返回 node.end
prefixNumber(pre): 沿前缀走，断路返回 0，否则返回 node.pass
delete(word): if search(word)!=0:
  node.pass--; 沿路径 --pass，若某节点 pass 归 0 则置 null 并 return；末尾 node.end--
Right.prefixNumber(pre): for key: if key.startsWith(pre): count += box.get(key)  // 含重复次数
```

**复杂度**：与 Code01 相同，insert/delete/search O(L)，prefixNumber O(前缀长度)。空间 O(总字符数)。

**易错点 / 面试提示**：
- 与 Code01 的唯一实质差别就在对照器 `count += box.get(cur)`，借此理解 pass 计的是"总加入次数（含重复）"。
- 数组版 vs HashMap 版的取舍：字符集小且固定用数组（快、省时间）；字符集大或未知用 HashMap（省空间、通用）。
- delete 删到中途某节点 pass 归 0 就可整棵子树剪掉并提前 return，是常见优化点。

---

### class08 / Code03_CountSort.java —— 计数排序（Count Sort）

**题目描述**：对元素为非负整数且值域较小（注释 only for 0~200）的数组进行排序。

**核心思路**：非比较排序。先找出最大值 max，开一个大小 `max+1` 的桶数组 `bucket`，遍历原数组对每个值计数 `bucket[v]++`；然后从小到大遍历桶，按计数把值依次写回原数组。利用"值即下标"实现 O(N+K) 排序，K 为值域大小。适合值域不大的整数。

**关键代码 / 步骤**：
```
countSort(arr):
  max = max(arr)
  bucket = new int[max+1]
  for v in arr: bucket[v]++
  i = 0
  for j = 0 .. max:
    while bucket[j]-- > 0: arr[i++] = j
```

**复杂度**：时间 O(N + K)，空间 O(K)，K = 最大值（值域）。本"按值回填"写法对纯整数排序结果正确（未保留原对象的相对顺序信息）。

**易错点 / 面试提示**：
- 只适用于非负整数且值域不大；值域很大时空间爆炸，不适用。
- 桶大小是 `max+1`（含 0 到 max），别少开一格。
- 面试常问"计数排序为什么能突破 O(N·logN) 下界"：因为它不是比较排序，利用了值本身作为下标。

---

### class08 / Code04_RadixSort.java —— 基数排序（Radix Sort，LSD 从低位到高位）

**题目描述**：对元素为**非负整数**的数组排序（注释 only for no-negative value）。

**核心思路**：按"位"做多轮稳定排序。先求最大值的十进制位数 `digit`。从最低位（个位，d=1）到最高位逐位排序，每一位用**计数排序**做桶分配：统计当前位每个数字 0-9 的出现次数，转成前缀和（`count[i]` 表示当前位 <= i 的数字个数），再**从右往左**遍历原数组按前缀和把每个数放到 help 的正确位置（保证稳定性），最后写回。因每一轮都稳定且从低位到高位累积，做完最高位后整体有序（LSD 基数排序）。

**关键代码 / 步骤**：
```
radixSort(arr, L, R, digit):
  for d = 1 .. digit:
    count[10] = {0}
    for i=L..R: count[ getDigit(arr[i], d) ]++
    for i=1..9: count[i] += count[i-1]              // 前缀和 -> 每个数字的右边界
    for i=R..L:                                       // 从右往左保证稳定
      j = getDigit(arr[i], d)
      help[ count[j]-1 ] = arr[i]; count[j]--
    把 help 写回 arr[L..R]
getDigit(x, d) = (x / 10^(d-1)) % 10
```

**复杂度**：时间 O(digit · (N + 10)) ≈ O(N · k)（k 为最大值位数），空间 O(N + 10)。位数固定时近似线性。

**易错点 / 面试提示**：
- 出桶必须**从右往左**遍历配合前缀和，才能保证每一轮稳定，进而整体正确；从左往右会破坏稳定性导致错误。
- count 转前缀和后表示"当前位 <= 该数字的元素个数"，即该数字桶的右边界，`help[count[j]-1]` 是放置位置，放完 `count[j]--`。
- 只支持非负整数；含负数需偏移处理。基数排序也是非比较排序，突破 O(N·logN) 下界。
- LSD（低位优先）适合定长整数；与 MSD（高位优先）的区别可被追问。
