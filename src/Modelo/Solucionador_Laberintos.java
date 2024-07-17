package Modelo;
import java.util.*;
public class Solucionador_Laberintos {
    private int[][] maze;
    private int numRows;
    private int numCols;
    private boolean[][] visited;

    public Solucionador_Laberintos(int[][] maze) {
        this.maze = maze;
        this.numRows = maze.length;
        this.numCols = maze[0].length;
        this.visited = new boolean[numRows][numCols];
    }

    public List<int[]> findPathRecursive(int[] start, int[] end) {
        resetVisited();
        return dfsRecursive(start[0], start[1], end[0], end[1]);
    }

    private List<int[]> dfsRecursive(int row, int col, int endRow, int endCol) {
        if (row == endRow && col == endCol) {
            return Collections.singletonList(new int[]{row, col});
        }

        visited[row][col] = true;

        int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isValid(newRow, newCol)) {
                List<int[]> path = dfsRecursive(newRow, newCol, endRow, endCol);
                if (!path.isEmpty()) {
                    path.add(0, new int[]{row, col});
                    return path;
                }
            }
        }

        return new ArrayList<>();
    }

    public List<int[]> findPathDP(int[] start, int[] end) {
        resetVisited();
        Map<String, List<int[]>> cache = new HashMap<>();
        return dp(start[0], start[1], end[0], end[1], cache);
    }

    private List<int[]> dp(int row, int col, int endRow, int endCol, Map<String, List<int[]>> cache) {
        if (row == endRow && col == endCol) {
            return Collections.singletonList(new int[]{row, col});
        }

        visited[row][col] = true;

        String key = row + "," + col;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isValid(newRow, newCol)) {
                List<int[]> path = dp(newRow, newCol, endRow, endCol, cache);
                if (!path.isEmpty()) {
                    path.add(0, new int[]{row, col});
                    cache.put(key, path);
                    return path;
                }
            }
        }

        return new ArrayList<>();
    }

    public List<int[]> findPathBFS(int[] start, int[] end) {
        resetVisited();
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start[0], start[1]});
        visited[start[0]][start[1]] = true;
        Map<String, int[]> parentMap = new HashMap<>();

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];

            if (row == end[0] && col == end[1]) {
                return reconstructPath(start, end, parentMap);
            }

            int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                if (isValid(newRow, newCol) && !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;
                    queue.offer(new int[]{newRow, newCol});
                    parentMap.put(newRow + "," + newCol, current);
                }
            }
        }

        return new ArrayList<>();
    }

    public List<int[]> findPathDFS(int[] start, int[] end) {
        resetVisited();
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{start[0], start[1]});
        visited[start[0]][start[1]] = true;
        Map<String, int[]> parentMap = new HashMap<>();

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int row = current[0];
            int col = current[1];

            if (row == end[0] && col == end[1]) {
                return reconstructPath(start, end, parentMap);
            }

            int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                if (isValid(newRow, newCol) && !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;
                    stack.push(new int[]{newRow, newCol});
                    parentMap.put(newRow + "," + newCol, current);
                }
            }
        }

        return new ArrayList<>();
    }

    private List<int[]> reconstructPath(int[] start, int[] end, Map<String, int[]> parentMap) {
        List<int[]> path = new ArrayList<>();
        int[] current = new int[]{end[0], end[1]};
        while (!Arrays.equals(current, start)) {
            path.add(current);
            current = parentMap.get(current[0] + "," + current[1]);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols && maze[row][col] == 1 && !visited[row][col];
    }

    private void resetVisited() {
        for (int i = 0; i < numRows; i++) {
            Arrays.fill(visited[i], false);
        }
    }
}

