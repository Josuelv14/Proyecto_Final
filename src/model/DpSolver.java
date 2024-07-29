package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DpSolver {

    private Map<Point, Boolean> cache = new HashMap<>();
    private List<List<Point>> allPaths = new ArrayList<>();

    public SolveResult solve(Maze maze) {
        List<Point> path = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        if (findPath(maze, maze.getStart(), path)) {
            long endTime = System.currentTimeMillis();
            return new SolveResult(path, endTime - startTime, path.size());
        }
        long endTime = System.currentTimeMillis();
        return new SolveResult(new ArrayList<>(), endTime - startTime, 0); // No path found
    }

    public List<List<Point>> getAllPaths(Maze maze) {
        allPaths.clear();
        List<Point> path = new ArrayList<>();
        findAllPaths(maze, maze.getStart(), path);
        return allPaths;
    }

    private boolean findPath(Maze maze, Point current, List<Point> path) {
        if (!maze.isTransitable(current) || path.contains(current)) {
            return false;
        }
        if (cache.containsKey(current)) {
            return cache.get(current);
        }
        path.add(current);
        if (current.equals(maze.getEnd())) {
            return true;
        }
        Point[] directions = { new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0) };
        for (Point d : directions) {
            Point next = new Point(current.x + d.x, current.y + d.y);
            if (findPath(maze, next, path)) {
                cache.put(current, true);
                return true;
            }
        }
        path.remove(path.size() - 1);
        cache.put(current, false);
        return false;
    }

    private void findAllPaths(Maze maze, Point current, List<Point> path) {
        if (!maze.isTransitable(current) || path.contains(current)) {
            return;
        }
        path.add(current);
        if (current.equals(maze.getEnd())) {
            allPaths.add(new ArrayList<>(path));
            path.remove(path.size() - 1);
            return;
        }
        Point[] directions = { new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0) };
        for (Point d : directions) {
            Point next = new Point(current.x + d.x, current.y + d.y);
            findAllPaths(maze, next, path);
        }
        path.remove(path.size() - 1);
    }
}
