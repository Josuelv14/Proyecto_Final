package model;

public class Maze {
    private final int[][] maze;
    private final int rows;
    private final int cols;
    private Point start;
    private Point end;

    public Maze(int[][] maze, Point start, Point end) {
        this.maze = maze;
        this.start = start;
        this.end = end;
        this.rows = maze.length;
        this.cols = maze[0].length;
    }

    public boolean isTransitable(Point p) {
        return p.x >= 0 && p.x < rows && p.y >= 0 && p.y < cols && maze[p.x][p.y] == 0;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
