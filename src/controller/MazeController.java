package controller;

import model.*;
import model.DfsSolver.Result;

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

    public BfsSolver.Result solveWithBfs() {
        return bfsSolver.solve(maze);
    }

    public DfsSolver.Result solveWithDfs() {
        return (Result) dfsSolver.solve(maze);
    }

    public RecursiveSolver.Result solveWithRecursive() {
        return (model.RecursiveSolver.Result) recursiveSolver.solve(maze);
    }

    public DpSolver.Result solveWithDp() {
        return (model.DpSolver.Result) dpSolver.solve(maze);
    }
}

