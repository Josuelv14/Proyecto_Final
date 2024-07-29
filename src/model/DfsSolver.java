package model;

import java.util.*;
public class DfsSolver {
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
        return new ArrayList<>(); // No path found
    }

    public List<List<Point>> getAllPaths(Maze maze) {
        List<List<Point>> allPaths = new ArrayList<>();
        // Implementa aquí el código para encontrar todos los caminos usando DFS
        return allPaths;
    }
}