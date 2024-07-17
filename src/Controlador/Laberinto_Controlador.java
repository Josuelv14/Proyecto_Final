package Controlador;

import java.util.List;

public class Laberinto_Controlador {
    private Laberinto_Controlador mazeSolver;

    public Laberinto_Controlador(int[][] maze) {
        this.mazeSolver = new Laberinto_Controlador(maze);
    }

    public List<int[]> findPathRecursive(int[] start, int[] end) {
        return mazeSolver.findPathRecursive(start, end);
    }

    public List<int[]> findPathDP(int[] start, int[] end) {
        return mazeSolver.findPathDP(start, end);
    }

    public List<int[]> findPathBFS(int[] start, int[] end) {
        return mazeSolver.findPathBFS(start, end);
    }

    public List<int[]> findPathDFS(int[] start, int[] end) {
        return mazeSolver.findPathDFS(start, end);
    }
}

