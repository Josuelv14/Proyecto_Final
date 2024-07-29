package model;

import java.util.*;

public class BfsSolver {

    public static class Result implements SolverResult {
        public final List<List<Point>> allPaths;
        public final List<Point> shortestPath;
        public final long time;
        public final int steps;

        public Result(List<List<Point>> allPaths, List<Point> shortestPath, long time, int steps) {
            this.allPaths = allPaths;
            this.shortestPath = shortestPath;
            this.time = time;
            this.steps = steps;
        }

        @Override
        public List<Point> getPath() {
            return shortestPath;
        }

        @Override
        public long getTime() {
            return time;
        }

        @Override
        public int getSteps() {
            return steps;
        }

        public List<List<Point>> getAllPaths() {
            return allPaths;
        }
    }

    public Result solve(Maze maze) {
        long startTime = System.currentTimeMillis();
        Queue<Point> queue = new LinkedList<>();
        Map<Point, Point> parentMap = new HashMap<>();
        List<List<Point>> allPaths = new ArrayList<>();
        List<Point> path = new ArrayList<>();
        queue.add(maze.getStart());
        parentMap.put(maze.getStart(), null);

        Point[] directions = { new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0) };

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.equals(maze.getEnd())) {
                List<Point> fullPath = new ArrayList<>();
                while (current != null) {
                    fullPath.add(0, current);
                    current = parentMap.get(current);
                }
                allPaths.add(fullPath);
                path = fullPath;  
            }
            for (Point d : directions) {
                Point next = new Point(current.x + d.x, current.y + d.y);
                if (maze.isTransitable(next) && !parentMap.containsKey(next)) {
                    queue.add(next);
                    parentMap.put(next, current);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        return new Result(allPaths, path, endTime - startTime, path.size()); 
    }

    public SolverResult solve(int[][] maze, Point start, Point end) {
        Maze mazeInstance = new Maze(maze, start, end);
        return solve(mazeInstance);
    }
}
