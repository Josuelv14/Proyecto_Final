package model;

import java.util.*;

public class BfsSolver {
    public SolveResult solve(Maze maze) {
        long startTime = System.currentTimeMillis();
        Queue<Point> queue = new LinkedList<>();
        Map<Point, Point> parentMap = new HashMap<>();
        List<Point> path = new ArrayList<>();
        queue.add(maze.getStart());
        parentMap.put(maze.getStart(), null);

        Point[] directions = { new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0) };

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.equals(maze.getEnd())) {
                while (current != null) {
                    path.add(0, current);
                    current = parentMap.get(current);
                }
                long endTime = System.currentTimeMillis();
                return new SolveResult(path, endTime - startTime, path.size());
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
        return new SolveResult(new ArrayList<>(), endTime - startTime, 0); // No path found
    }

    public List<List<Point>> getAllPaths(Maze maze) {
        List<List<Point>> allPaths = new ArrayList<>();
        // Implementa aquí el código para encontrar todos los caminos usando BFS
        return allPaths;
    }

}