package Modelo;

import Vista.MazeView;

import java.util.LinkedList;
import java.util.Queue;

public class Algorithms {

    // BFS implementation
    public static boolean bfs(Maze maze, MazeView view) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{maze.getStartX(), maze.getStartY()});
        maze.setVisited(maze.getStartX(), maze.getStartY(), true);
        view.updateView();

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            if (x == maze.getEndX() && y == maze.getEndY()) {
                return true;
            }

            for (int[] direction : directions) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (maze.isWithinBounds(newX, newY) && maze.isPassable(newX, newY) && !maze.isVisited(newX, newY)) {
                    queue.add(new int[]{newX, newY});
                    maze.setVisited(newX, newY, true);
                    view.updateView();
                }
            }
        }

        return false;
    }

    // DFS implementation
    public static boolean dfs(Maze maze, int x, int y, MazeView view) {
        if (!maze.isWithinBounds(x, y) || !maze.isPassable(x, y) || maze.isVisited(x, y)) {
            return false;
        }

        if (x == maze.getEndX() && y == maze.getEndY()) {
            return true;
        }

        maze.setVisited(x, y, true);
        view.updateView();

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] direction : directions) {
            if (dfs(maze, x + direction[0], y + direction[1], view)) {
                return true;
            }
        }

        return false;
    }

    // Recursive method with memoization (Dynamic Programming)
    public static boolean dynamicProgramming(Maze maze, int x, int y, Boolean[][] memo, MazeView view) {
        if (!maze.isWithinBounds(x, y) || !maze.isPassable(x, y) || maze.isVisited(x, y)) {
            return false;
        }

        if (x == maze.getEndX() && y == maze.getEndY()) {
            return true;
        }

        if (memo[x][y] != null) {
            return memo[x][y];
        }

        maze.setVisited(x, y, true);
        view.updateView();

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] direction : directions) {
            if (dynamicProgramming(maze, x + direction[0], y + direction[1], memo, view)) {
                memo[x][y] = true;
                return true;
            }
        }

        memo[x][y] = false;
        return false;
    }
}
