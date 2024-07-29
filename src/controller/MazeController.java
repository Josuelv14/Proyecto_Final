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

    public SolveResult solveWithRecursive() {
        return recursiveSolver.solve(maze);
    }

    public SolveResult solveWithDp() {
        return dpSolver.solve(maze);
    }

    public SolveResult solveWithBfs() {
        return bfsSolver.solve(maze);
    }

    public SolveResult solveWithDfs() {
        List<Point> path = dfsSolver.solve(maze);
        return new SolveResult(path, 0, path.size()); // Considera ajustar el tiempo y pasos según sea necesario
    }
    public List<List<Point>> getAllPathsWithDfs() {
        return dfsSolver.getAllPaths(maze);
    }
    
    public List<List<Point>> getAllPathsWithBfs() {
        return bfsSolver.getAllPaths(maze);
    }
    public List<List<Point>> getAllPathsWithRecursive() {
        return recursiveSolver.getAllPaths(maze);
    }

    public List<List<Point>> getAllPathsWithDp() {
        return dpSolver.getAllPaths(maze);
    }

    public void updateMaze(int[][] mazeMatrix, Point start, Point end) {
        this.maze = new Maze(mazeMatrix, start, end);
    }
}


