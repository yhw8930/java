package LeetCode;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 给你一个 m x n 的矩阵，其中的值均为非负整数，代表二维高度图每个单元的高度，请计算图中形状最多能接多少体积的雨水。
 * <p>
 * 输入: heightMap = [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]
 * 输出: 4
 * 解释: 下雨后，雨水将会被上图蓝色的方块中。总的接雨水量为1+2+1=4
 *
 */
public class P407_接雨水II {
    /**
     * 解法一：最小堆从外围向内扩张，思路类似 Dijkstra。
     * 外围格子直接与地图外部相连，无法蓄水，因此先把它们作为初始围墙放入最小堆。
     * 每次取出当前有效高度最低的边界格子，用它向未访问的内部邻居扩展。
     * 如果邻居的地面高度低于当前围墙，可接水量就是两者差值；
     * 注水后邻居的有效高度为 max(当前围墙高度, 邻居地面高度)，再把它作为新边界加入堆。
     * 总是先处理最低边界，保证了某个内部格子首次被扩展时，已经找到它向外流水所必须跨过的最低围墙。
     * 该实现支持 null 和空矩阵返回 0，并假设各行长度相同。
     *
     * 时间复杂度：O(MN log(MN))，每个格子最多入堆和出堆一次。
     * 额外空间复杂度：O(MN)，用于最小堆和 visited 数组。
     */
    public int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length == 0 || heightMap[0].length == 0) {
            return 0;
        }

        int m = heightMap.length;
        int n = heightMap[0].length;
        boolean[][] visited = new boolean[m][n];

        // 小根堆，按高度排序，存 [行, 列, 高度]
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[2] - b[2]);

        // 第一步：把最外圈所有格子加入堆，标记为已访问
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                    heap.offer(new int[]{i, j, heightMap[i][j]});
                    visited[i][j] = true;
                }
            }
        }

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int total = 0;

        // 第二步：不断从堆顶取最矮的格子，向内扩展
        while (!heap.isEmpty()) {
            int[] cur = heap.poll();
            int curHeight = cur[2];

            for (int[] dir : dirs) {
                int ni = cur[0] + dir[0];
                int nj = cur[1] + dir[1];

                if (ni < 0 || ni >= m || nj < 0 || nj >= n || visited[ni][nj]) {
                    continue;
                }

                visited[ni][nj] = true;

                // 邻居能积的水：如果邻居比当前围墙矮，差值就是能积的水
                int neighborHeight = heightMap[ni][nj];
                if (neighborHeight < curHeight) {
                    total += curHeight - neighborHeight;
                }

                // 更新邻居的"有效高度"（相当于水填平后的高度），成为新的围墙
                heap.offer(new int[]{ni, nj, Math.max(curHeight, neighborHeight)});
            }
        }

        return total;
    }

    /**
     * 解法二：从过高的水面上界开始，用队列反复向内松弛。
     * water[i][j] 表示当前推测的最终水面高度；初始统一设为全局最高地面，作为一个安全上界。
     * 外围格子的水会直接流走，因此先把它们的水面降到自身地面高度，并加入队列。
     * 当格子 (x, y) 的水面低于邻居的当前水面时，邻居可能通过它向外排水；
     * 邻居的新水面不能低于自身地面，所以更新为 max(water[x][y], heightMap[nx][ny])。
     * 每次更新都会使某个水面上界严格降低，直到所有格子都无法继续降低；
     * 此时 water[i][j] - heightMap[i][j] 就是该格子的蓄水量。
     * 该实现假设 heightMap 非 null、至少有一行一列，且各行长度相同。
     *
     * 时间复杂度：最坏 O((MN)^2)，因为同一格子的水面可能被多次降低并重新入队。
     * 额外空间复杂度：O(MN)，用于 water 数组和队列。
     */
    public int trapRainWater2(int[][] heightMap) {
        int m = heightMap.length;
        int n = heightMap[0].length;
        int[] dirs = {-1, 0, 1, 0, -1};
        int maxHeight = 0;

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                maxHeight = Math.max(maxHeight, heightMap[i][j]);
            }
        }
        int[][] water = new int[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                water[i][j] = maxHeight;
            }
        }
        Queue<int[]> qu = new LinkedList<>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                    if (water[i][j] > heightMap[i][j]) {
                        water[i][j] = heightMap[i][j];
                        qu.offer(new int[]{i, j});
                    }
                }
            }
        }
        while (!qu.isEmpty()) {
            int[] curr = qu.poll();
            int x = curr[0];
            int y = curr[1];
            for (int i = 0; i < 4; ++i) {
                int nx = x + dirs[i], ny = y + dirs[i + 1];
                if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
                    continue;
                }
                if (water[x][y] < water[nx][ny] && water[nx][ny] > heightMap[nx][ny]) {
                    water[nx][ny] = Math.max(water[x][y], heightMap[nx][ny]);
                    qu.offer(new int[]{nx, ny});
                }
            }
        }

        int res = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                res += water[i][j] - heightMap[i][j];
            }
        }
        return res;
    }

}
