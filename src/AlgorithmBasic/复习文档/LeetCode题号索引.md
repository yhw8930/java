# LeetCode 题号对照索引（中文版）

> 对应 `算法逐题精讲.md` 的全部 149 节。
> **标注说明**：
> - ① **源码链接** = 左神源码注释里明确给出 LeetCode 地址，对应最确定；
> - ② **对应** = 公认等价题；
> - ③ **近似** = 同类/变体题，思路相通但不完全相同；
> - ④ **教学题** = 无直接 LeetCode 对应（牛客/剑指/纯教学），面试讲思路即可。
> - 标「🔒会员」的是 LeetCode 付费会员题。

---

## class01 排序与二分
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| SelectionSort / BubbleSort / InsertionSort | 912 | 排序数组 | ③近似 |
| BSExist | 704 | 二分查找 | ②对应 |
| BSNearLeft / BSNearRight | 34 | 在排序数组中查找元素的第一个和最后一个位置 | ③近似 |
| BSAwesome（无序找局部最小） | 162 | 寻找峰值 | ③近似 |

## class02 位运算
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| EvenTimesOddTimes（一个数奇数次） | 136 | 只出现一次的数字 | ②对应 |
| EvenTimesOddTimes（两个数奇数次） | 260 | 只出现一次的数字 III | ②对应 |
| KM（其余出现 m 次） | 137 | 只出现一次的数字 II | ③近似 |
| Swap | — | 异或交换演示 | ④教学题 |

## class03 栈、队列、链表结构
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| ReverseList | 206 | 反转链表 | ②对应 |
| DeleteGivenValue | 203 | 移除链表元素 | ②对应 |
| RingArray | 622 | 设计循环队列 | ②对应 |
| GetMinStack | 155 | 最小栈 | ②对应 |
| TwoStacksImplementQueue | 232 | 用栈实现队列 | ②对应 |
| TwoQueueImplementStack | 225 | 用队列实现栈 | ②对应 |
| DoubleEndsQueue / GetMax / HashMapAndSortedMap | — | 结构演示 | ④教学题 |

## class04 归并排序及衍生
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| MergeSort | 912 | 排序数组 | ③近似 |
| SmallSum（小和） | — | 小和问题（牛客 NC） | ④教学题 |
| ReversePair（逆序对） | 剑指 Offer 51 | 数组中的逆序对 | ②对应 |
| BiggerThanRightTwice（翻倍逆序对） | 493 | 翻转对 | ②对应 |

## class05 快排与区间和
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| CountOfRangeSum | **327** | 区间和的个数 | ①源码链接 |
| PartitionAndQuickSort（荷兰国旗） | 75 | 颜色分类 | ②对应 |
| QuickSortRecursiveAndUnrecursive | 912 | 排序数组 | ③近似 |

## class06 堆 / 优先队列
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| HeapSort | 912 | 排序数组 | ③近似 |
| SortArrayDistanceLessK | — | 几乎有序数组排序 | ④教学题 |
| Heap / Comparator | — | 手写堆 / 比较器演示 | ④教学题 |

## class07 堆的贪心应用
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| CoverMax（线段最大重叠） | — | 线段重叠（含 253 会议室思想） | ④教学题 |
| EveryStepShowBoss（实时 TopK） | — | 加强堆应用 | ④教学题 |
| HeapGreater / Inner | — | 加强堆模板 | ④教学题 |

## class08 前缀树与非比较排序
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| TrieTree（Code01/02） | 208 | 实现 Trie (前缀树) | ②对应 |
| CountSort / RadixSort | 912 | 排序数组（非比较排序） | ③近似 |

## class09 链表高频题
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| LinkedListMid | 876 | 链表的中间结点 | ②对应 |
| IsPalindromeList | 234 | 回文链表 | ②对应 |
| SmallerEqualBigger | 86 | 分隔链表 | ③近似 |
| CopyListWithRandom | 138 | 随机链表的复制 | ②对应 |

## class10 链表相交 + 二叉树遍历
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| FindFirstIntersectNode | 160 / 142 | 相交链表 / 环形链表 II | ②对应 |
| RecursiveTraversalBT | 144 / 94 / 145 | 前序 / 中序 / 后序遍历 | ②对应 |
| UnRecursiveTraversalBT | 144 / 94 / 145 | 同上（迭代版） | ②对应 |

## class11 二叉树衍生问题
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| LevelTraversalBT | 102 | 二叉树的层序遍历 | ②对应 |
| SerializeAndReconstructTree | 297 | 二叉树的序列化与反序列化 | ②对应 |
| EncodeNaryTreeToBinaryTree | **431** 🔒 | 将 N 叉树编码为二叉树 | ①源码链接 |
| TreeMaxWidth | 662 | 二叉树最大宽度 | ②对应 |
| SuccessorNode | 剑指 Offer 8 | 二叉树的下一个节点 | ②对应 |
| PrintBinaryTree / PaperFolding | — | 打印 / 折纸问题 | ④教学题 |

## class12 二叉树树形 DP
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| IsCBT | 958 | 二叉树的完全性检验 | ②对应 |
| IsBST | 98 | 验证二叉搜索树 | ②对应 |
| IsBalanced | 110 | 平衡二叉树 | ②对应 |
| IsFull | — | 判断满二叉树 | ④教学题 |
| MaxSubBSTSize | 333 🔒 | 最大 BST 子树 | ②对应 |
| MaxDistance（直径） | 543 | 二叉树的直径 | ③近似 |

## class13 树形 DP 进阶 + 贪心入门
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| IsCBT | 958 | 二叉树的完全性检验 | ②对应 |
| MaxSubBSTHead | 333 🔒 | 最大 BST 子树 | ②对应 |
| lowestAncestor（LCA） | 236 | 二叉树的最近公共祖先 | ②对应 |
| MaxHappy（派对快乐值） | 337 | 打家劫舍 III | ③近似 |
| LowestLexicography（拼接最小字典序） | 179 | 最大数 | ③近似 |

## class14 贪心 + 并查集
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| Light（路灯） | — | 最少路灯 | ④教学题 |
| LessMoneySplitGold（切金条/哈夫曼） | 1167 🔒 | 连接棒材的最低费用 | ③近似 |
| BestArrange（会议安排） | 252 🔒 | 会议室 | ③近似 |
| IPO | 502 | IPO | ②对应 |
| UnionFind | — | 并查集模板 | ④教学题 |

## class15 并查集与岛问题
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| FriendCircles | **547** | 省份数量（原朋友圈） | ①源码链接 |
| NumberOfIslands | **200** | 岛屿数量 | ①源码链接 |
| NumberOfIslandsII | **305** 🔒 | 岛屿数量 II | ①源码链接 |

## class16 图算法
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| TopologicalOrder（各版本） | 210 / 207 | 课程表 II / 课程表 | ②对应 |
| Dijkstra | 743 | 网络延迟时间 | ③近似 |
| Kruskal / Prim（最小生成树） | 1584 | 连接所有点的最小费用 | ③近似 |
| BFS / DFS / Graph 模板 | — | 图遍历与建图模板 | ④教学题 |

## class17 暴力递归
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| Hanoi（汉诺塔） | — | 汉诺塔（剑指 Offer） | ④教学题 |
| PrintAllSubsquences（子序列） | 78 / 90 | 子集 / 子集 II | ②对应 |
| PrintAllPermutations（全排列） | 46 / 47 | 全排列 / 全排列 II | ②对应 |
| ReverseStackUsingRecursive | — | 递归逆序栈 | ④教学题 |
| Dijkstra（同 class16） | 743 | 网络延迟时间 | ③近似 |

## class18 DP 入门
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| RobotWalk（机器人走方法数） | — | 机器人走格子（教学） | ④教学题 |
| CardsInLine（拿牌博弈） | 486 | 预测赢家 | ③近似 |

## class19 从尝试到 DP
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| Knapsack（0-1 背包） | 416 | 分割等和子集（背包模型） | ③近似 |
| ConvertToLetterString（数字转字母） | 91 | 解码方法 | ②对应 |
| StickersToSpellWord | **691** | 贴纸拼词 | ①源码链接 |
| LongestCommonSubsequence | **1143** | 最长公共子序列 | ①源码链接 |

## class20 区间/棋盘 DP
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| PalindromeSubsequence | **516** | 最长回文子序列 | ①源码链接 |
| HorseJump（马跳） | — | 马跳棋盘（剑指/教学） | ④教学题 |
| Coffee（咖啡问题） | — | 洗咖啡杯（教学经典） | ④教学题 |

## class21 矩阵 / 货币 DP
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| MinPathSum | 64 | 最小路径和 | ②对应 |
| CoinsWayEveryPaperDifferent（每张不同） | — | 货币方法数（教学） | ④教学题 |
| CoinsWayNoLimit（无限张） | 518 | 零钱兑换 II | ③近似 |
| CoinsWaySameValueSamePapper（同值有限） | — | 多重背包方案数 | ④教学题 |
| BobDie（生存概率） | 688 | 骑士在棋盘上的概率 | ③近似 |

## class22 含数学优化的 DP
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| KillMonster（杀怪概率） | — | 概率 DP + 前缀和优化（教学） | ④教学题 |
| MinCoinsNoLimit | 322 | 零钱兑换 | ②对应 |
| SplitNumber（整数不减拆分） | — | 整数划分（教学，类 343） | ④教学题 |

## class23 子集划分与 N 皇后
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| SplitSumClosed | 416 | 分割等和子集 | ③近似 |
| SplitSumClosedSizeHalf | 1049 | 最后一块石头的重量 II | ③近似 |
| NQueens | 51 / 52 | N 皇后 / N 皇后 II | ②对应 |

## class24 滑动窗口
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| SlidingWindowMaxArray | 239 | 滑动窗口最大值 | ②对应 |
| AllLessNumSubArray | — | max-min≤sum 子数组计数（教学） | ④教学题 |
| GasStation | **134** | 加油站 | ①源码链接 |
| MinCoinsOnePaper | — | 每张钱币只用一次的最少货币数 | ④教学题 |

## class25 单调栈
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| MonotonousStack | 496 / 503 | 下一个更大元素 I/II | ③近似 |
| AllTimesMinToMax | — | 累加和×最小值最大（教学） | ④教学题 |
| LargestRectangleInHistogram | **84** | 柱状图中最大的矩形 | ①源码链接 |
| MaximalRectangle | **85** | 最大矩形 | ①源码链接 |
| CountSubmatricesWithAllOnes | **1504** | 统计全 1 子矩形 | ①源码链接 |

## class26 单调栈 / 矩阵快速幂
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| SumOfSubarrayMinimums | **907** | 子数组的最小值之和 | ①源码链接 |
| FibonacciProblem（矩阵快速幂） | 509 | 斐波那契数 | ③近似 |
| ZeroLeftOneStringNumber | — | 0 左边必有 1 的 01 串计数（教学） | ④教学题 |

## class27 KMP
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| KMP | 28 | 找出字符串中第一个匹配项的下标 | ②对应 |
| TreeEqual（子树匹配） | 572 | 另一棵树的子树 | ②对应 |
| IsRotation（旋转词） | 796 | 旋转字符串 | ②对应 |

## class28 Manacher
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| Manacher | 5 | 最长回文子串 | ②对应 |
| AddShortestEnd | 214 | 最短回文串 | ③近似 |

## class29 BFPRT / 蓄水池抽样
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| FindMinKth（第 K 小） | 215 | 数组中的第 K 个最大元素 | ③近似 |
| MaxTopK | 215 / 703 | 第 K 大 / 数据流第 K 大 | ③近似 |
| ReservoirSampling（蓄水池抽样） | 382 / 398 | 链表随机节点 / 随机数索引 | ③近似 |

## class30 Morris 遍历
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| MorrisTraversal | 94 / 144 | 中序 / 前序遍历（O(1) 空间） | ②对应 |
| MinHeight | 111 | 二叉树的最小深度 | ②对应 |

## class31 线段树
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| SegmentTree | 307 | 区域和检索 - 数组可修改 | ③近似 |
| FallingSquares | 699 | 掉落的方块 | ②对应 |

## class32 树状数组 / AC 自动机
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| IndexTree（一维树状数组） | 307 | 区域和检索 - 数组可修改 | ②对应 |
| IndexTree2D | **308** 🔒 | 二维区域和检索 - 可变 | ①源码链接 |
| AC1 / AC2（AC 自动机） | — | 多模式匹配（教学） | ④教学题 |

## class33 哈希
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| Hash（SHA/MD5 演示） | — | 哈希函数演示 | ④教学题 |

## class34 资源限制类
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| ReadMe（方法论） | — | 大数据/内存受限题型方法论 | ④教学题 |

## class35-37 高级有序表
| 文件 | LeetCode | 中文题名 | 类型 |
|---|---|---|---|
| class35 AVLTreeMap | — | AVL 树模板 | ④教学题 |
| class36 SizeBalancedTreeMap | — | SB 树模板 | ④教学题 |
| class36 SkipListMap | 1206 | 设计跳表 | ②对应 |
| class37 CountofRangeSum | 327 | 区间和的个数 | ②对应 |
| class37 SlidingWindowMedian | 480 | 滑动窗口中位数 | ②对应 |
| class37 AddRemoveGetIndexGreat | — | SB 树实现高性能 List | ④教学题 |
| class37 Compare | — | 有序表对数器 | ④教学题 |

---

## 按 LeetCode 题号正序速查（仅列①②对应/源码题）

| LC | 中文题名 | 对应文件 |
|---|---|---|
| 5 | 最长回文子串 | class28 Manacher |
| 28 | 找出字符串中第一个匹配项的下标 | class27 KMP |
| 46/47 | 全排列 / 全排列 II | class17 PrintAllPermutations |
| 51/52 | N 皇后 | class23 NQueens |
| 64 | 最小路径和 | class21 MinPathSum |
| 75 | 颜色分类 | class05 PartitionAndQuickSort |
| 78/90 | 子集 / 子集 II | class17 PrintAllSubsquences |
| 84 | 柱状图中最大的矩形 | class25 LargestRectangleInHistogram |
| 85 | 最大矩形 | class25 MaximalRectangle |
| 91 | 解码方法 | class19 ConvertToLetterString |
| 94/144/145 | 二叉树中/前/后序遍历 | class10 遍历, class30 Morris |
| 98 | 验证二叉搜索树 | class12 IsBST |
| 102 | 二叉树的层序遍历 | class11 LevelTraversalBT |
| 110 | 平衡二叉树 | class12 IsBalanced |
| 111 | 二叉树的最小深度 | class30 MinHeight |
| 134 | 加油站 | class24 GasStation |
| 136/260 | 只出现一次的数字 / III | class02 EvenTimesOddTimes |
| 138 | 随机链表的复制 | class09 CopyListWithRandom |
| 155 | 最小栈 | class03 GetMinStack |
| 160 | 相交链表 | class10 FindFirstIntersectNode |
| 200 | 岛屿数量 | class15 NumberOfIslands |
| 203 | 移除链表元素 | class03 DeleteGivenValue |
| 206 | 反转链表 | class03 ReverseList |
| 207/210 | 课程表 / II | class16 拓扑排序 |
| 208 | 实现 Trie 前缀树 | class08 TrieTree |
| 225/232 | 队列实现栈 / 栈实现队列 | class03 |
| 234 | 回文链表 | class09 IsPalindromeList |
| 236 | 最近公共祖先 | class13 lowestAncestor |
| 239 | 滑动窗口最大值 | class24 SlidingWindowMaxArray |
| 297 | 二叉树序列化 | class11 SerializeAndReconstructTree |
| 305 🔒 | 岛屿数量 II | class15 NumberOfIslandsII |
| 307 | 区域和检索-数组可改 | class31/32 IndexTree |
| 308 🔒 | 二维区域和检索-可变 | class32 IndexTree2D |
| 322 | 零钱兑换 | class22 MinCoinsNoLimit |
| 327 | 区间和的个数 | class05/37 CountOfRangeSum |
| 333 🔒 | 最大 BST 子树 | class12/13 MaxSubBST |
| 431 🔒 | N 叉树编码为二叉树 | class11 EncodeNaryTree |
| 480 | 滑动窗口中位数 | class37 SlidingWindowMedian |
| 502 | IPO | class14 IPO |
| 516 | 最长回文子序列 | class20 PalindromeSubsequence |
| 547 | 省份数量 | class15 FriendCircles |
| 572 | 另一棵树的子树 | class27 TreeEqual |
| 622 | 设计循环队列 | class03 RingArray |
| 691 | 贴纸拼词 | class19 StickersToSpellWord |
| 699 | 掉落的方块 | class31 FallingSquares |
| 704 | 二分查找 | class01 BSExist |
| 796 | 旋转字符串 | class27 IsRotation |
| 876 | 链表的中间结点 | class09 LinkedListMid |
| 907 | 子数组的最小值之和 | class26 SumOfSubarrayMinimums |
| 958 | 二叉树的完全性检验 | class12/13 IsCBT |
| 1143 | 最长公共子序列 | class19 LongestCommonSubsequence |
| 1206 | 设计跳表 | class36 SkipListMap |
| 1504 | 统计全 1 子矩形 | class25 CountSubmatricesWithAllOnes |
| 剑指 51 | 数组中的逆序对 | class04 ReversePair |

---

*注：标 ③近似 / ④教学题 的题目以掌握思路为主；带 🔒 的为 LeetCode 会员题。*
