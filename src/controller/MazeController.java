package controller;

import model.*;

import java.util.List;

public class MazeController {
    private Maze maze;
    private RecursiveSolver recursiveSolver;
    private DpSolver dpSolver;
    private BfsSolver bfsSolver;
    private DfsSolver dfsSolver;

    public MazeController(int[][] mazeMatrix, Point start, Point end) {
        this.maze = new Maze(mazeMatrix, start, end);
        this.recursiveSolver = new RecursiveSolver();
        this.dpSolver = new DpSolver();
        this.bfsSolver = new BfsSolver();
        this.dfsSolver = new DfsSolver();
    }

    public List<Point> solveWithRecursive() {
        return recursiveSolver.solve(maze);
    }

    public List<Point> solveWithDp() {
        return dpSolver.solve(maze);
    }

    public List<Point> solveWithBfs() {
        return bfsSolver.solve(maze);
    }

    public List<Point> solveWithDfs() {
        return dfsSolver.solve(maze);
    }
}
