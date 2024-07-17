package Vista;

import java.util.List;

import Controlador.Laberinto_Controlador;

 public class Main {
    public static void main(String[] args) {
        int[][] maze = {
            {1, 0, 1, 1, 1},
            {1, 1, 1, 0, 1},
            {0, 0, 0, 1, 1},
            {0, 0, 0, 1, 0},
            {1, 1, 1, 1, 1}
        };

        Laberinto_Controlador controller = new Laberinto_Controlador(maze);
        int[] start = {0, 0};
        int[] end = {4, 4};

        List<int[]> pathRecursive = controller.findPathRecursive(start, end);
        List<int[]> pathDP = controller.findPathDP(start, end);
        List<int[]> pathBFS = controller.findPathBFS(start, end);
        List<int[]> pathDFS = controller.findPathDFS(start, end);

        System.out.println("Recursive path: " + pathRecursive);
        System.out.println("DP path: " + pathDP);
        System.out.println("BFS path: " + pathBFS);
        System.out.println("DFS path: " + pathDFS);
    }
 {
    }
}
