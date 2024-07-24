package controller;

import model.Point;
import model.BfsSolver;
import model.DfsSolver;
import model.RecursiveSolver;
import model.DpSolver;
import model.SolverResult;

public class MazeController {
    private int[][] maze;
    private Point start;
    private Point end;

    public MazeController(int[][] maze, Point start, Point end) {
        this.maze = maze;
        this.start = start;
        this.end = end;
    }

    public SolverResult solveWithBfs() {
        BfsSolver bfsSolver = new BfsSolver();
        return bfsSolver.solve(maze, start, end);
    }

    public SolverResult solveWithDfs() {
        DfsSolver dfsSolver = new DfsSolver();
        return dfsSolver.solve(maze, start, end);
    }

    public SolverResult solveWithRecursive() {
        RecursiveSolver recursiveSolver = new RecursiveSolver();
        return recursiveSolver.solve(maze, start, end);
    }

    public SolverResult solveWithDp() {
        DpSolver dpSolver = new DpSolver();
        return dpSolver.solve(maze, start, end);
    }
}


