package model;

import java.util.ArrayList;
import java.util.List;

public class RecursiveSolver {
    public SolveResult solve(Maze maze) {
        long startTime = System.currentTimeMillis();
        List<Point> path = new ArrayList<>();
        boolean found = solveMaze(maze, maze.getStart(), path);
        long endTime = System.currentTimeMillis();
        if (found) {
            return new SolveResult(path, endTime - startTime, path.size());
        } else {
            return new SolveResult(new ArrayList<>(), endTime - startTime, 0);
        }
    }

    private boolean solveMaze(Maze maze, Point current, List<Point> path) {
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
            if (solveMaze(maze, next, path)) {
                return true;
            }
        }
        path.remove(path.size() - 1);
        return false;
    }

    public List<List<Point>> getAllPaths(Maze maze) {
        List<List<Point>> allPaths = new ArrayList<>();
        findAllPaths(maze, maze.getStart(), new ArrayList<>(), allPaths);
        return allPaths;
    }

    private void findAllPaths(Maze maze, Point current, List<Point> path, List<List<Point>> allPaths) {
        if (!maze.isTransitable(current) || path.contains(current)) {
            return;
        }
        path.add(current);
        if (current.equals(maze.getEnd())) {
            allPaths.add(new ArrayList<>(path));
        } else {
            Point[] directions = { new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0) };
            for (Point d : directions) {
                Point next = new Point(current.x + d.x, current.y + d.y);
                findAllPaths(maze, next, path, allPaths);
            }
        }
        path.remove(path.size() - 1);
    }
}
