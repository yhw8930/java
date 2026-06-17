# Part5 复习文档：class15 - class18（并查集、图算法、递归、DP 入门）

本部分覆盖并查集（Union-Find）、图的遍历与经典图算法（BFS/DFS/拓扑排序/Kruskal/Prim/Dijkstra）、暴力递归（汉诺塔、子序列、全排列、栈逆序）以及动态规划入门（机器人走方法数、纸牌博弈）。

---

## class15 并查集与岛问题

### class15 / Code01_FriendCircles.java —— 朋友圈数量（省份数量）

**题目描述**：给定 N×N 的二维矩阵 M，M[i][j]==1 表示第 i 个人和第 j 个人互相认识（朋友关系，矩阵对称，对角线为 1）。朋友关系具有传递性（朋友的朋友也算同一个圈子）。求一共有多少个朋友圈（连通的人群集合）。返回集合数量。

**核心思路**：典型并查集应用。先为每个人建立一个独立集合，遍历矩阵上三角（j 从 i+1 开始即可，因为对称），凡是 M[i][j]==1 就把 i、j 两个集合 union 在一起。最后并查集里剩余的集合个数 sets 就是答案。这里用数组实现的并查集，包含**路径压缩**（find 时用 help 数组记录路径，扁平化挂到代表节点）和**按大小合并**（小集合挂到大集合下）两大优化。

**关键代码 / 步骤**：
```java
UnionFind uf = new UnionFind(N);
for (int i = 0; i < N; i++)
    for (int j = i + 1; j < N; j++)
        if (M[i][j] == 1) uf.union(i, j);
return uf.sets();

// find：路径压缩
private int find(int i) {
    int hi = 0;
    while (i != parent[i]) { help[hi++] = i; i = parent[i]; }
    for (hi--; hi >= 0; hi--) parent[help[hi]] = i; // 全部挂到代表节点
    return i;
}
```

**复杂度**：时间 O(N^2)（遍历矩阵，并查集操作近似 O(1)），空间 O(N)。

**易错点 / 面试提示**：
- 矩阵对称，只需遍历上三角，避免重复 union。
- union 后要维护 sets 计数（只有真正合并不同集合时 sets-- ）。
- 并查集的两大优化（路径压缩 + 按 size 合并）要能手写，是面试高频考点。

---

### class15 / Code02_NumberOfIslands.java —— 岛屿数量（LeetCode 200）

**题目描述**：给定由字符 '1'（陆地）和 '0'（水）组成的二维网格 board，上下左右相邻的 '1' 连成一片算一个岛。求岛屿数量。

**核心思路**：提供三种解法。
1. **感染法（DFS，numIslands3，推荐）**：遍历网格，遇到 '1' 就岛数 +1，并从该点 DFS 把整片相连的 '1' 全部改成 '0'（感染），避免重复计数。代码最短、常数最小。
2. **并查集（HashMap 实现，numIslands1）**：为每个 '1' 建 Dot 节点，仅向左、向上相邻的 '1' 做 union，最后返回集合数。泛型 + HashMap，可读性好但常数大。
3. **并查集（数组实现，numIslands2，推荐用于大数据）**：用一维数组模拟二维位置（index = r*col + c），路径压缩 + 按 size 合并，常数远小于 HashMap 版本。

面试首选感染法（简单），大规模或要求并查集时用数组版并查集。

**关键代码 / 步骤**：
```java
// 感染法
public static int numIslands3(char[][] board) {
    int islands = 0;
    for (int i = 0; i < board.length; i++)
        for (int j = 0; j < board[0].length; j++)
            if (board[i][j] == '1') { islands++; infect(board, i, j); }
    return islands;
}
public static void infect(char[][] b, int i, int j) {
    if (i<0||i==b.length||j<0||j==b[0].length||b[i][j]!='1') return;
    b[i][j] = 0;
    infect(b,i-1,j); infect(b,i+1,j); infect(b,i,j-1); infect(b,i,j+1);
}
```

**复杂度**：感染法时间 O(行×列)，空间 O(行×列)（递归栈最坏）。数组并查集时间近似 O(行×列)，空间 O(行×列)。

**易错点 / 面试提示**：
- 感染法会修改原矩阵（'1' 改 '0'），如不能修改需先拷贝。
- 并查集做岛问题只需向「左」和「上」两个方向 union，因为遍历顺序保证右/下还没处理。
- 单机环境感染法最优；如果数据被切分到多台机器（无法整片 DFS），需要并查集合并边界，这是并查集相对感染法的真正价值。

---

### class15 / Code03_NumberOfIslandsII.java —— 岛屿数量 II（动态加点，LeetCode 305）

**题目描述**：m×n 网格初始全是水。给定 positions 数组，依次把某些位置变为陆地。每加一个陆地后，返回当前岛屿数量。返回长度等于 positions 的结果列表。

**核心思路**：动态加点用并查集最自然。每 connect 一个新陆地：先把它自成一个集合 sets++，再尝试和上下左右四个方向已存在的陆地 union（每成功合并一次 sets--）。提供两种实现：
1. **UnionFind1（数组实现）**：用 size[index]==0 标记该格子还不是陆地；index = r*col+c 映射。当 m×n 很大时初始化数组本身就是 O(m×n)。
2. **UnionFind2（HashMap 实现，推荐当 m×n 远大于 k 时）**：用 "r_c" 字符串做 key，惰性建集合，初始化和空间都只与实际加入点数 k 有关，避免大网格的重初始化开销。

**关键代码 / 步骤**：
```java
public int connect(int r, int c) {
    int index = index(r, c);
    if (size[index] == 0) {        // 该格还不是陆地才处理
        parent[index] = index; size[index] = 1; sets++;
        union(r - 1, c, r, c); union(r + 1, c, r, c);
        union(r, c - 1, r, c); union(r, c + 1, r, c);
    }
    return sets;
}
```

**复杂度**：UnionFind1 时间 O(m×n + k)（含初始化），空间 O(m×n)。UnionFind2 时间 O(k)，空间 O(k)，与网格大小无关。

**易错点 / 面试提示**：
- union 前要做越界判断，且只 union 已经是陆地的格子（size!=0 / containsKey）。
- 同一位置可能被重复加入，要先判断是否已是陆地，避免 sets 计错。
- 当 k << m×n 时优先 HashMap 惰性实现，否则大网格初始化会成为瓶颈。

---

## class16 图的遍历与经典图算法

> 本目录用一套统一的图模板：Graph 持有 nodes（编号→Node）和 edges 集合；Node 含 value、入度 in、出度 out、邻接点 nexts、出边 edges；Edge 含 weight、from、to。GraphGenerator 把 [weight,from,to] 的边数组转成这套结构。各算法都基于此模板，面试时常先把题目给的矩阵/邻接表转成自己熟悉的模板再套算法。

### class16 / Node.java —— 图的点结构模板

**题目描述**：图算法通用的点结构。字段：value（点的值/编号）、in（入度）、out（出度）、nexts（直接邻居点列表）、edges（从该点出发的边列表）。无算法逻辑，仅数据定义。

**核心思路**：把一个点所需的所有信息聚合在一起：既有邻接点（nexts）方便遍历，又有出边（edges）方便取权重，还冗余记录入度/出度方便拓扑排序。这种「大而全」的点结构是为了让上层各种图算法都能直接复用同一套模板。

**关键代码 / 步骤**：
```java
public class Node {
    public int value, in, out;
    public ArrayList<Node> nexts;   // 邻接点
    public ArrayList<Edge> edges;   // 出边
    public Node(int value) { this.value = value; in = 0; out = 0;
        nexts = new ArrayList<>(); edges = new ArrayList<>(); }
}
```

**复杂度**：构造 O(1)，空间随邻居/边数量增长。

**易错点 / 面试提示**：
- 入度/出度需要在建图（加边）时同步维护，否则拓扑排序会错。
- 这是「有冗余」的模板，工程上未必这么写，但面试中能加速答题。

---

### class16 / Edge.java —— 图的边结构模板

**题目描述**：图的边结构。字段：weight（权重）、from（起点 Node）、to（终点 Node）。仅数据定义。

**核心思路**：边记录权重和方向（from→to）。无向图通常用两条对称的有向边表示。Kruskal、Prim、Dijkstra 都靠 weight 排序或累加。

**关键代码 / 步骤**：
```java
public class Edge {
    public int weight; public Node from; public Node to;
    public Edge(int weight, Node from, Node to) {
        this.weight = weight; this.from = from; this.to = to; }
}
```

**复杂度**：O(1)。

**易错点 / 面试提示**：
- 无向图建图时要加两条方向相反的边，且两端入度/出度都要更新。
- Comparator 比较权重时若用 `o1.weight - o2.weight`，注意权重很大时可能整型溢出（本题数据小无碍）。

---

### class16 / Graph.java —— 图结构模板

**题目描述**：整张图的结构。字段：nodes（HashMap，点编号 → Node 对象）、edges（HashSet，所有边）。仅数据定义。

**核心思路**：用 HashMap 按编号快速定位点，用 HashSet 存所有边（便于 Kruskal 全量遍历）。这是承载整张图的容器。

**关键代码 / 步骤**：
```java
public class Graph {
    public HashMap<Integer, Node> nodes;
    public HashSet<Edge> edges;
    public Graph() { nodes = new HashMap<>(); edges = new HashSet<>(); }
}
```

**复杂度**：O(1) 构造。

**易错点 / 面试提示**：
- nodes 用编号做 key，建图时要先判重再新建点。
- edges 用 HashSet，Kruskal 直接遍历全部边入堆。

---

### class16 / GraphGenerator.java —— 边数组转图模板

**题目描述**：输入 N×3 矩阵，每行为 [weight, from, to]，构造上面的 Graph 结构。返回 Graph。

**核心思路**：遍历每条边：from/to 点不存在则新建并放入 nodes；建 Edge；维护 from 的 nexts、out 和 edges，维护 to 的 in；把边加入 graph.edges。这是把题目原始输入「翻译」成自己模板的标准套路。

**关键代码 / 步骤**：
```java
for (int[] e : matrix) {
    int w = e[0], from = e[1], to = e[2];
    if (!graph.nodes.containsKey(from)) graph.nodes.put(from, new Node(from));
    if (!graph.nodes.containsKey(to))   graph.nodes.put(to, new Node(to));
    Node f = graph.nodes.get(from), t = graph.nodes.get(to);
    Edge ne = new Edge(w, f, t);
    f.nexts.add(t); f.out++; t.in++; f.edges.add(ne);
    graph.edges.add(ne);
}
```

**复杂度**：时间 O(M)（M 为边数），空间 O(N+M)。

**易错点 / 面试提示**：
- 此处只建有向边（from→to）。若题意是无向图，需对每条边正反各调用一次或手动加对称边。
- 面试通用思路：拿到任意图输入，先写一个 generator 转成熟悉模板，再套算法。

---

### class16 / Code01_BFS.java —— 图的宽度优先遍历

**题目描述**：从指定起点 start 出发，按宽度优先（层序）遍历图并打印每个点的值，每个点只访问一次。

**核心思路**：队列 + HashSet 去重。起点入队并标记，循环：弹出队首打印，把它所有未访问的邻居标记后入队。set 防止有环图重复访问。

**关键代码 / 步骤**：
```java
Queue<Node> queue = new LinkedList<>();
HashSet<Node> set = new HashSet<>();
queue.add(start); set.add(start);
while (!queue.isEmpty()) {
    Node cur = queue.poll();
    System.out.println(cur.value);
    for (Node next : cur.nexts)
        if (!set.contains(next)) { set.add(next); queue.add(next); }
}
```

**复杂度**：时间 O(N+M)，空间 O(N)。

**易错点 / 面试提示**：
- 入队时就标记 set，而不是出队时标记，否则同一点可能被多次入队。
- 图 BFS 必须用 set 去重（与树 BFS 不同，图可能有环）。

---

### class16 / Code02_DFS.java —— 图的深度优先遍历（栈实现）

**题目描述**：从 node 出发，按深度优先遍历图并打印每个点，每点只打印一次。

**核心思路**：用显式栈模拟递归。栈中保存当前 DFS 路径。每次看栈顶 cur 的邻居，找到第一个未访问的 next：把 cur 重新压回、再压 next、标记并打印 next、break（保证一次只深入一步）；若 cur 所有邻居都访问过则自然 pop。打印时机在「点第一次进栈」时。

**关键代码 / 步骤**：
```java
stack.add(node); set.add(node); System.out.println(node.value);
while (!stack.isEmpty()) {
    Node cur = stack.pop();
    for (Node next : cur.nexts)
        if (!set.contains(next)) {
            stack.push(cur); stack.push(next);  // cur 压回，再压 next
            set.add(next); System.out.println(next.value);
            break;                               // 只深入一步
        }
}
```

**复杂度**：时间 O(N+M)，空间 O(N)。

**易错点 / 面试提示**：
- 关键技巧：找到一个未访问邻居就把 cur 压回去再压 next 并 break，保证深度优先且能正确回溯。
- 打印在「进栈」时进行，起点要在循环外先打印。
- 递归写法更简洁，但面试常考用栈把递归改成迭代。

---

### class16 / Code03_TopologicalOrderBFS.java —— 拓扑排序（BFS / 入度法，lintcode）

**题目描述**：给定有向无环图（DirectedGraphNode 列表，每个点有 neighbors），返回一个拓扑序（任意一个合法即可）。

**核心思路**：Kahn 算法（入度 BFS）。统计每个点的入度；所有入度为 0 的点入队；循环弹出一个点加入结果，把它指向的邻居入度 -1，减到 0 的入队。最终结果即拓扑序。

**关键代码 / 步骤**：
```java
// 统计入度
for (DirectedGraphNode cur : graph)
    for (DirectedGraphNode next : cur.neighbors)
        indegreeMap.put(next, indegreeMap.get(next) + 1);
// 入度0入队
for (cur : keys) if (indegree==0) zeroQueue.add(cur);
while (!zeroQueue.isEmpty()) {
    cur = zeroQueue.poll(); ans.add(cur);
    for (next : cur.neighbors) {
        indegree--; if (indegree==0) zeroQueue.offer(next);
    }
}
```

**复杂度**：时间 O(N+M)，空间 O(N)。

**易错点 / 面试提示**：
- 必须先把所有点初始化入度为 0，再统计，避免漏掉入度本就为 0 的点。
- 若结果点数 < 总点数，说明图有环（无拓扑序）。这是判环的常用手段。

---

### class16 / Code03_TopologicalOrderDFS1.java —— 拓扑排序（DFS / 最大深度法，lintcode）

**题目描述**：同上，DAG 求一个拓扑序。本解法用「到达的最长链深度」排序。

**核心思路**：对每个点递归求 deep = 它后续能延伸出的最长链长度（叶子点 deep=1，其余 = max(子节点 deep)+1）。用 HashMap 缓存（记忆化）避免重复计算。最后按 deep 从大到小排序，deep 大的排前面，即为拓扑序。

**关键代码 / 步骤**：
```java
Record f(DirectedGraphNode cur, HashMap order) {
    if (order.containsKey(cur)) return order.get(cur);
    int follow = 0;
    for (next : cur.neighbors) follow = Math.max(follow, f(next, order).deep);
    Record ans = new Record(cur, follow + 1);
    order.put(cur, ans); return ans;
}
// 按 deep 降序排序得到拓扑序
recordArr.sort((o1,o2) -> o2.deep - o1.deep);
```

**复杂度**：时间 O(N+M)（记忆化），排序 O(N log N)，空间 O(N)。

**易错点 / 面试提示**：
- 必须记忆化（order 缓存），否则指数级重复递归。
- 「最长链深度大的在前」是因为越靠近源头能延伸的链越长。

---

### class16 / Code03_TopologicalOrderDFS2.java —— 拓扑排序（DFS / 点次法，lintcode）

**题目描述**：同上，DAG 求一个拓扑序。本解法用「能到达的点次（含路径重复计数）」排序。

**核心思路**：对每个点递归求 nodes = 它自己 1 + 所有邻居的 nodes 之和（这里是「点次」，同一点经不同路径会被累加，所以用 long 防溢出）。点次越大，说明它越靠源头、能影响的下游越多，排在越前面。同样用 HashMap 记忆化。

**关键代码 / 步骤**：
```java
Record f(cur, order) {
    if (order.containsKey(cur)) return order.get(cur);
    long nodes = 0;
    for (next : cur.neighbors) nodes += f(next, order).nodes; // 累加点次
    Record ans = new Record(cur, nodes + 1);
    order.put(cur, ans); return ans;
}
// 按 nodes 降序排序
```

**复杂度**：时间 O(N+M)（记忆化），排序 O(N log N)，空间 O(N)。

**易错点 / 面试提示**：
- 点次会指数级增大，必须用 long，且仍可能溢出（大图慎用）；DFS1 的最大深度法更稳。
- 与 DFS1 同样依赖记忆化；三种拓扑排序（BFS入度、DFS深度、DFS点次）建议都掌握，BFS 入度法最常用且最稳。

---

### class16 / Code03_TopologySort.java —— 拓扑排序（基于 Graph 模板）

**题目描述**：给定本目录 Graph 模板表示的有向无环图，返回一个拓扑排序后的 Node 列表。

**核心思路**：与 BFS 入度法相同，只是数据结构用本目录的 Graph/Node 模板。Node 自带 in 字段（建图时已统计入度），用 inMap 记录剩余入度，入度 0 入队，弹出后把邻居入度 -1，减到 0 入队。

**关键代码 / 步骤**：
```java
for (Node node : graph.nodes.values()) {
    inMap.put(node, node.in);
    if (node.in == 0) zeroInQueue.add(node);
}
while (!zeroInQueue.isEmpty()) {
    Node cur = zeroInQueue.poll(); result.add(cur);
    for (Node next : cur.nexts) {
        inMap.put(next, inMap.get(next) - 1);
        if (inMap.get(next) == 0) zeroInQueue.add(next);
    }
}
```

**复杂度**：时间 O(N+M)，空间 O(N)。

**易错点 / 面试提示**：
- 不能直接改 Node.in，要用 inMap 复制一份剩余入度。
- 依赖建图时正确维护了 in（GraphGenerator 已处理）。

---

### class16 / Code04_Kruskal.java —— 最小生成树 Kruskal（仅无向图）

**题目描述**：给定无向连通图，求最小生成树（选若干边连通所有点且总权重最小），返回选中的边集合。

**核心思路**：贪心 + 并查集。把所有边放进小根堆按权重从小到大弹出；每弹一条边，若它的两端点不在同一集合（不构成环）就选它并 union 两端，否则丢弃。直到处理完所有边。并查集用 HashMap 实现，带路径压缩和按 size 合并。

**关键代码 / 步骤**：
```java
unionFind.makeSets(graph.nodes.values());
PriorityQueue<Edge> pq = new PriorityQueue<>((a,b)->a.weight-b.weight);
for (Edge e : graph.edges) pq.add(e);
Set<Edge> result = new HashSet<>();
while (!pq.isEmpty()) {
    Edge edge = pq.poll();
    if (!unionFind.isSameSet(edge.from, edge.to)) {  // 不成环
        result.add(edge);
        unionFind.union(edge.from, edge.to);
    }
}
```

**复杂度**：时间 O(M log M)（边排序/堆），并查集近似 O(1)；空间 O(N+M)。

**易错点 / 面试提示**：
- Kruskal 以「边」为中心，靠并查集判环，适合稀疏图。
- 仅适用无向图；无向边在堆里只需出现一次。
- 必须能手写并查集（isSameSet / union / 路径压缩）。

---

### class16 / Code05_Prim.java —— 最小生成树 Prim（仅无向图）

**题目描述**：给定无向连通图，求最小生成树，返回选中边集合（primMST）；另提供邻接矩阵版返回 MST 权重和（prim）。

**核心思路**：贪心 + 小根堆，以「点」为中心。任选起点，把它的所有出边解锁入堆；每次弹出权重最小的边，若它指向的点是新点（未解锁）就选这条边、解锁该点、把该点所有出边入堆；已解锁的点跳过。外层 for 遍历所有点是为兼容非连通图（多个连通分量）。矩阵版用 distances[] 数组记录每个未访问点到已选集合的最短距离，每轮选最近的点加入。

**关键代码 / 步骤**：
```java
for (Node node : graph.nodes.values()) {
    if (!nodeSet.contains(node)) {
        nodeSet.add(node);
        for (Edge e : node.edges) pq.add(e);          // 解锁起点所有边
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            Node toNode = edge.to;
            if (!nodeSet.contains(toNode)) {           // 新点
                nodeSet.add(toNode); result.add(edge);
                for (Edge ne : toNode.edges) pq.add(ne);
            }
        }
    }
}
```

**复杂度**：堆版时间 O(M log M)，空间 O(N+M)。矩阵版时间 O(N^2)，空间 O(N)。

**易错点 / 面试提示**：
- 堆里可能有指向已选点的「过期边」，弹出后判断 to 是否新点再决定取舍。
- Prim 以点为中心，适合稠密图（尤其矩阵版 O(N^2)）；Kruskal 适合稀疏图。
- 外层 for + nodeSet 判断是为处理森林（多个连通分量），连通图其实一轮内层就够。

---

### class16 / Code06_Dijkstra.java —— 单源最短路 Dijkstra（非负权）

**题目描述**：给定带非负权的有向图和源点 from，求源点到所有可达点的最短距离，返回 Node→最短距离 的 map。

**核心思路**：两个版本。
1. **dijkstra1（朴素）**：用 distanceMap 记录已知最短距离，selectedNodes 记录已确定的点。每轮从未确定点里线性找当前距离最小的点 minNode，确定它，用它松弛所有出边（更新邻居距离），重复直到没有可选点。
2. **dijkstra2（堆优化，推荐）**：自定义 NodeHeap（加强堆），支持 addOrUpdateOrIgnore：新点加入、已在堆中的点若发现更短距离则更新并向上调整、已弹出的点忽略。每次弹出堆顶（当前最短）即确定该点最短距离，再松弛其邻居。

**关键代码 / 步骤**：
```java
// dijkstra2 主流程
NodeHeap heap = new NodeHeap(size);
heap.addOrUpdateOrIgnore(head, 0);
while (!heap.isEmpty()) {
    NodeRecord rec = heap.pop();          // 当前未确定点中最短的
    Node cur = rec.node; int distance = rec.distance;
    for (Edge edge : cur.edges)
        heap.addOrUpdateOrIgnore(edge.to, edge.weight + distance); // 松弛
    result.put(cur, distance);            // 确定 cur 的最短距离
}
```

**复杂度**：朴素版 O(N^2 + M)，空间 O(N)。堆优化版约 O(M log N)，空间 O(N)。

**易错点 / 面试提示**：
- Dijkstra 只适用于**非负权**图；有负权要用 Bellman-Ford / SPFA。
- 加强堆要维护 heapIndexMap，弹出的点标记 index=-1 表示已确定，再来更新要忽略，避免重复处理。
- 朴素版每轮线性找最小点，堆优化版用堆找最小，是性能差异关键。

---

## class17 暴力递归（含 Dijkstra 模板复用）

> 本目录的 Node/Edge/Graph 与 class16 完全相同（图模板复用）。Code01_Dijkstra 也与 class16 的 Code06 实现一致，下面只对差异点简述。本目录重点是递归套路：先写出可信的暴力递归，再考虑改 DP。

### class17 / Node.java —— 图的点结构模板（同 class16）

**题目描述**：与 class16/Node.java 完全相同的图点结构。字段：value、in、out、nexts、edges。

**核心思路**：图算法通用点模板，聚合邻接点和出边信息，供本目录的 Dijkstra 复用。

**关键代码 / 步骤**：见 class16/Node.java，字段与构造完全一致。

**复杂度**：O(1) 构造。

**易错点 / 面试提示**：与 class16 同；这是跨章节复用的模板类。

---

### class17 / Edge.java —— 图的边结构模板（同 class16）

**题目描述**：与 class16/Edge.java 完全相同。字段：weight、from、to。

**核心思路**：边记录权重和方向，供 Dijkstra 取权松弛使用。

**关键代码 / 步骤**：见 class16/Edge.java。

**复杂度**：O(1)。

**易错点 / 面试提示**：与 class16 同。

---

### class17 / Graph.java —— 图结构模板（同 class16）

**题目描述**：与 class16/Graph.java 完全相同。字段：nodes（编号→Node）、edges（边集合）。

**核心思路**：承载整张图的容器，供本目录 Dijkstra 使用。

**关键代码 / 步骤**：见 class16/Graph.java。

**复杂度**：O(1) 构造。

**易错点 / 面试提示**：与 class16 同。

---

### class17 / Code01_Dijkstra.java —— 单源最短路 Dijkstra（同 class16 Code06）

**题目描述**：与 class16/Code06_Dijkstra.java 实现一致。非负权有向图，求源点到所有可达点最短距离，返回 Node→距离 map。

**核心思路**：同样提供朴素版 dijkstra1（线性找最小未确定点 + 松弛）和堆优化版 dijkstra2（自定义加强堆 NodeHeap，支持加入/更新/忽略，弹出堆顶确定最短距离再松弛邻居）。逻辑与 class16 完全相同，此处作为递归章节前的图算法复习。

**关键代码 / 步骤**：
```java
public void addOrUpdateOrIgnore(Node node, int distance) {
    if (inHeap(node)) {  // 已在堆中：取更小距离并上浮
        distanceMap.put(node, Math.min(distanceMap.get(node), distance));
        insertHeapify(node, heapIndexMap.get(node));
    }
    if (!isEntered(node)) {  // 从未进过堆：加入
        nodes[size] = node; heapIndexMap.put(node, size);
        distanceMap.put(node, distance); insertHeapify(node, size++);
    }
    // 已弹出（index=-1）：忽略
}
```

**复杂度**：朴素 O(N^2+M)；堆优化约 O(M log N)。空间 O(N)。

**易错点 / 面试提示**：
- 同 class16：仅非负权；加强堆的 index=-1 表示已确定要忽略。
- 三态判断（inHeap / isEntered / 已弹出）是加强堆的核心，建议手写熟练。

---

### class17 / Code02_Hanoi.java —— 汉诺塔

**题目描述**：把 N 个圆盘从左柱（left）借助中柱（mid）移到右柱（right），每次只能移一个盘，且大盘不能压在小盘上。打印完整的移动步骤。

**核心思路**：三种写法。
1. **hanoi1（六个互调函数）**：把「从某柱到某柱」拆成六个方向函数互相递归，直观体现「先把上面 n-1 移到中转、移底盘、再把 n-1 移过来」。
2. **hanoi2（通用 from/to/other，推荐）**：最经典写法。func(N, from, to, other) = func(N-1, from, other, to) → 移第 N 个 from→to → func(N-1, other, to, from)。简洁通用。
3. **hanoi3（栈模拟递归）**：用 Record（记录 base、from/to/other、finish1 是否完成第一段）显式栈模拟，把递归改迭代。

推荐 hanoi2，思路清晰且易扩展。

**关键代码 / 步骤**：
```java
public static void func(int N, String from, String to, String other) {
    if (N == 1) { System.out.println("Move 1 from " + from + " to " + to); return; }
    func(N - 1, from, other, to);                  // 上面 n-1 移到中转柱
    System.out.println("Move " + N + " from " + from + " to " + to); // 移底盘
    func(N - 1, other, to, from);                  // n-1 从中转移到目标
}
```

**复杂度**：时间 O(2^N)（移动步数 2^N - 1，本质如此），空间 O(N)（递归深度）。

**易错点 / 面试提示**：
- 第一段把 n-1 移到 other（用 to 当中转），第二段从 other 移到 to（用 from 当中转），三个参数顺序别搞反。
- 最少步数 2^N - 1，无法优化（这是问题本质，不是算法差）。
- 栈模拟版关键是 finish1 标记区分「第一段是否做完」。

---

### class17 / Code03_PrintAllSubsquences.java —— 打印字符串所有子序列

**题目描述**：给定字符串 s，打印/返回它的所有子序列（按字符相对顺序选取若干字符，可空）。subs 返回全部（可能重复），subsNoRepeat 返回去重后的。

**核心思路**：经典「每个位置要或不要」的二叉递归。process(index, path)：到 str[index]，分两路递归——不要当前字符（path 不变）和要当前字符（path + 当前字符），index 到末尾时把 path 收集。去重版只是把收集容器从 List 换成 HashSet。

**关键代码 / 步骤**：
```java
void process1(char[] str, int index, List<String> ans, String path) {
    if (index == str.length) { ans.add(path); return; }
    process1(str, index + 1, ans, path);                       // 不要 str[index]
    process1(str, index + 1, ans, path + str[index]);          // 要 str[index]
}
```

**复杂度**：时间 O(2^N × N)（2^N 个子序列，拼接字符串 O(N)），空间 O(N) 递归深度（结果另算）。

**易错点 / 面试提示**：
- 「要 / 不要」二选一是子序列问题的标准模型。
- 去重最简单是用 HashSet 收集；更优可在递归层面剪枝（同字符的处理）。
- 包含空串（path=""），共 2^N 个。

---

### class17 / Code04_PrintAllPermutations.java —— 打印字符串所有全排列

**题目描述**：给定字符串 s，返回它的所有全排列。permutation3 额外要求去重（相同字符不产生重复排列）。

**核心思路**：三种写法。
1. **permutation1**：维护剩余字符列表 rest，每次取出一个加到 path 递归，递归后再放回（恢复现场）。
2. **permutation2（推荐）**：原地交换。g1(str, index)：从 index 到末尾，依次把每个字符交换到 index 位，递归 index+1，再换回。不需额外列表，常数更优。
3. **permutation3（去重，推荐）**：在 permutation2 基础上加 visited[256] 布尔表，同一层若该字符已被换到 index 过就跳过（分支限界剪枝），避免重复排列。

**关键代码 / 步骤**：
```java
void g2(char[] str, int index, List<String> ans) {
    if (index == str.length) { ans.add(String.valueOf(str)); return; }
    boolean[] visited = new boolean[256];          // 当前层去重
    for (int i = index; i < str.length; i++)
        if (!visited[str[i]]) {
            visited[str[i]] = true;
            swap(str, index, i);
            g2(str, index + 1, ans);
            swap(str, index, i);                   // 恢复现场
        }
}
```

**复杂度**：时间 O(N! × N)（N! 个排列，每个收集 O(N)），空间 O(N) 递归深度（visited 每层 O(1) 大小常量表）。

**易错点 / 面试提示**：
- 交换法递归后必须换回（恢复现场），否则后续分支数据被破坏。
- 去重 visited 必须在每个递归层内新建（同层去重），不能全局共用。
- 交换法比「rest 列表」法常数更优，是面试首选。

---

### class17 / Code05_ReverseStackUsingRecursive.java —— 用递归逆序一个栈（不借助额外数据结构）

**题目描述**：给定一个栈，只用递归函数把它整体逆序，不允许申请额外的栈/数组等数据结构（只能用递归系统栈）。

**核心思路**：两个递归函数配合。f(stack)：移除并返回当前栈底元素（递归到底拿到栈底，回溯时把上面的元素依次压回）。reverse(stack)：先用 f 拿出栈底元素 i，递归 reverse 剩下的栈，最后把 i 压回——这样原栈底变成新栈顶，实现整体逆序。

**关键代码 / 步骤**：
```java
public static void reverse(Stack<Integer> stack) {
    if (stack.isEmpty()) return;
    int i = f(stack);        // 取出当前栈底
    reverse(stack);          // 逆序剩余
    stack.push(i);           // 栈底压到最上 -> 整体逆序
}
// f: 移除并返回栈底元素，其余元素保持原相对顺序压回
public static int f(Stack<Integer> stack) {
    int result = stack.pop();
    if (stack.isEmpty()) return result;
    int last = f(stack);
    stack.push(result);
    return last;
}
```

**复杂度**：时间 O(N^2)（reverse 调 N 次 f，每次 f 是 O(N)），空间 O(N)（递归深度）。

**易错点 / 面试提示**：
- 难点在两个递归的配合：f 负责「抽栈底」，reverse 负责「把抽出的栈底逐个压回顶部」。
- 不能用显式额外结构，必须利用系统递归栈——这是题目核心约束。
- 是「递归解放思想」的经典训练题，理解 f 的回溯压栈过程是关键。

---

## class18 动态规划入门（暴力递归 → 记忆化 → 严格表 DP）

> 本章核心套路：先写可信的暴力递归（尝试），把可变参数找出来 → 加缓存（记忆化搜索）→ 分析依赖改成严格表结构 DP。两道题都完整展示了这三步。

### class18 / Code01_RobotWalk.java —— 机器人到达目标的方法数

**题目描述**：有 1~N 共 N 个位置排成一行，机器人初始在 start 位置，必须走 K 步（每步只能向左或向右走一格；在位置 1 只能往右到 2，在位置 N 只能往左到 N-1）。求走完恰好 K 步后停在 aim 位置的方法数。参数非法返回 -1。

**核心思路**：三步进阶。
1. **ways1（暴力递归）**：process(cur, rest)= 从 cur 出发还剩 rest 步、最终停 aim 的方法数。rest==0 看 cur 是否等于 aim；cur==1 只能去 2；cur==N 只能去 N-1；否则 = 去左 + 去右。
2. **ways2（记忆化）**：可变参数是 (cur, rest)，开 dp[N+1][K+1] 缓存，初值 -1 表示没算过，算过直接返回。
3. **ways3（严格表 DP，推荐）**：dp[cur][rest] 依赖 dp[cur±1][rest-1]，即只依赖「上一列」。base：dp[aim][0]=1。按 rest 从 1 到 K 逐列填，每列先填边界 dp[1] 和 dp[N]，中间 cur 从两侧汇总。答案 dp[start][K]。

**关键代码 / 步骤**：
```java
// 严格表 DP
int[][] dp = new int[N + 1][K + 1];
dp[aim][0] = 1;
for (int rest = 1; rest <= K; rest++) {
    dp[1][rest] = dp[2][rest - 1];                       // 左边界
    for (int cur = 2; cur < N; cur++)
        dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
    dp[N][rest] = dp[N - 1][rest - 1];                   // 右边界
}
return dp[start][K];
```

**复杂度**：暴力 O(2^K)；记忆化与表 DP 均 O(N×K)，空间 O(N×K)。

**易错点 / 面试提示**：
- 位置从 1 开始，dp 数组开 [N+1][K+1]，注意下标边界。
- base case 是 dp[aim][0]=1（剩 0 步且在 aim 才算一种）。
- 边界格子（cur=1、cur=N）只有单一来源，别套用中间通式。
- 经典「暴力递归 → 记忆化 → 严格表」三步演进模板题。

---

### class18 / Code02_CardsInLine.java —— 排成一条线的纸牌博弈（先手后手最优得分）

**题目描述**：给定整型数组 arr 表示一排纸牌的分值，玩家 A、B 轮流拿牌，每次只能拿最左或最右一张，双方都绝顶聪明（都使自己最终得分最大）。求最终胜者（得分较高者）的得分。

**核心思路**：先手/后手互相博弈，用两个互相递归的函数。
- f(L,R)：在 arr[L..R] 上**先手**能拿到的最高分 = max(拿左 arr[L]+g(L+1,R), 拿右 arr[R]+g(L,R-1))。
- g(L,R)：在 arr[L..R] 上**后手**能拿到的分 = min(f(L+1,R), f(L,R-1))——因为对手先拿走一端，对手会留给你最差的局面，所以取 min。

三步进阶：win1 暴力递归；win2 记忆化（fmap/gmap 缓存，初值 -1）；win3 严格表 DP（两张表 f、g 沿对角线斜着填，f[i][i]=arr[i]，f 依赖 g 的下方/左方，g 依赖 f 的下方/左方）。答案 max(整个区间的 f, 整个区间的 g)。

**关键代码 / 步骤**：
```java
// 严格表 DP（沿对角线填）
for (int i = 0; i < N; i++) fmap[i][i] = arr[i];       // gmap[i][i]=0
for (int startCol = 1; startCol < N; startCol++) {
    int L = 0, R = startCol;
    while (R < N) {
        fmap[L][R] = Math.max(arr[L] + gmap[L + 1][R], arr[R] + gmap[L][R - 1]);
        gmap[L][R] = Math.min(fmap[L + 1][R], fmap[L][R - 1]);
        L++; R++;
    }
}
return Math.max(fmap[0][N - 1], gmap[0][N - 1]);
```

**复杂度**：暴力 O(2^N)；记忆化与表 DP 均 O(N^2)，空间 O(N^2)。

**易错点 / 面试提示**：
- 关键在理解后手取 min：对手会把最不利的局面留给你。f 与 g 相互依赖。
- base：f[i][i]=arr[i]（只剩一张先手全拿），g[i][i]=0（只剩一张后手拿不到）。
- 表 DP 要沿对角线（按区间长度递增）填，因为 [L,R] 依赖更短的区间。
- 是博弈类 DP 的模板题，先手/后手分两个函数是核心套路。

---

（全文完）
