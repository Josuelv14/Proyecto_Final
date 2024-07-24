package model;

import java.util.ArrayList;
import java.util.List;

public class RecursiveSolver {
    public List<Point> solve(Maze maze) {
        List<Point> path = new ArrayList<>();
        if (findPath(maze, maze.getStart(), path)) {
            return path;
        }
        return new ArrayList<>(); // No path found
    }

    private boolean findPath(Maze maze, Point current, List<Point> path) {
        if (!maze.isTransitable(current) || path.contains(current)) {
            return false;
        }
        path.add(current);
        if (current.equals(maze.getEnd())) {
            return true;
        }
        Point[] directions = { new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0) };
        for (Point d : directions) {
            Point next = new Point(current.x + d.x, current.y + d.y);
            if (findPath(maze, next, path)) {
                return true;
            }
        }
        path.remove(path.size() - 1);
        return false;
    }
}
