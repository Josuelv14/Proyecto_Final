package model;

import java.util.*;

public class DpSolver {

    public static class Result implements SolverResult {
        public final List<Point> path;
        public final long time;
        public final int steps;

        public Result(List<Point> path, long time, int steps) {
            this.path = path;
            this.time = time;
            this.steps = steps;
        }

        @Override
        public List<Point> getPath() {
            return path;
        }

        @Override
        public long getTime() {
            return time;
        }

        @Override
        public int getSteps() {
            return steps;
        }
    }

    private final Map<Point, Boolean> cache = new HashMap<>();

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

    public SolverResult solve(int[][] maze, Point start, Point end) {
        Maze mazeInstance = new Maze(maze, start, end);
        long startTime = System.currentTimeMillis();
        List<Point> path = solve(mazeInstance);
        long endTime = System.currentTimeMillis();
        return new Result(path, endTime - startTime, path.size());
    }
}
