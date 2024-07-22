package Modelo;

import java.util.Arrays;

public class Maze {
    private int[][] maze;
    private boolean[][] visited;
    private int startX, startY, endX, endY;

    public Maze(int[][] maze, int startX, int startY, int endX, int endY) {
        this.maze = maze;
        this.visited = new boolean[maze.length][maze[0].length];
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length;
    }

    public boolean isPassable(int x, int y) {
        return maze[x][y] == 0;
    }

    public boolean isVisited(int x, int y) {
        return visited[x][y];
    }

    public void setVisited(int x, int y, boolean value) {
        visited[x][y] = value;
    }

    public int[][] getMaze() {
        return maze;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public void resetVisited() {
        for (boolean[] row : visited) {
            Arrays.fill(row, false);
        }
    }
}
