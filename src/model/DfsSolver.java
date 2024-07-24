package model;

import java.util.*;

public class DfsSolver {

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

    public List<Point> solve(Maze maze) {
        Stack<Point> stack = new Stack<>();
        Map<Point, Point> parentMap = new HashMap<>();
        List<Point> path = new ArrayList<>();
        stack.push(maze.getStart());
        parentMap.put(maze.getStart(), null);

        Point[] directions = { new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0) };

        while (!stack.isEmpty()) {
            Point current = stack.pop();
            if (current.equals(maze.getEnd())) {
                while (current != null) {
                    path.add(0, current);
                    current = parentMap.get(current);
                }
                return path;
            }
            for (Point d : directions) {
                Point next = new Point(current.x + d.x, current.y + d.y);
                if (maze.isTransitable(next) && !parentMap.containsKey(next)) {
                    stack.push(next);
                    parentMap.put(next, current);
                }
            }
        }
        return path; // No path found
    }

    public SolverResult solve(int[][] maze, Point start, Point end) {
        Maze mazeInstance = new Maze(maze, start, end);
        List<Point> path = solve(mazeInstance);
        return new Result(path, 0, path.size()); // Time is set to 0 for simplicity
    }
}

