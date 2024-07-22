package Vista;

import Modelo.Maze;
import Modelo.Algorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeView extends JFrame {
    private Maze maze;
    private JPanel mazePanel;
    private JButton solveButton;

    public MazeView(Maze maze) {
        this.maze = maze;
        setTitle("Maze Solver");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mazePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMaze(g);
            }
        };
        mazePanel.setPreferredSize(new Dimension(500, 500));
        add(mazePanel, BorderLayout.CENTER);

        solveButton = new JButton("Solve Maze");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveMaze();
            }
        });
        add(solveButton, BorderLayout.SOUTH);
    }

    private void drawMaze(Graphics g) {
        int[][] mazeArray = maze.getMaze();
        int cellSize = Math.min(mazePanel.getWidth() / mazeArray.length, mazePanel.getHeight() / mazeArray[0].length);
        for (int i = 0; i < mazeArray.length; i++) {
            for (int j = 0; j < mazeArray[0].length; j++) {
                if (mazeArray[i][j] == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                g.setColor(Color.GRAY);
                g.drawRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }

    private void solveMaze() {
        maze.resetVisited();
        boolean foundBFS = Algorithms.bfs(maze);
        maze.resetVisited();
        boolean foundDFS = Algorithms.dfs(maze, maze.getStartX(), maze.getStartY());
        maze.resetVisited();
        Boolean[][] memo = new Boolean[maze.getMaze().length][maze.getMaze()[0].length];
        boolean foundDP = Algorithms.dynamicProgramming(maze, maze.getStartX(), maze.getStartY(), memo);
    
        String result = String.format("BFS found a path: %b\nDFS found a path: %b\nDP found a path: %b", foundBFS, foundDFS, foundDP);
        JOptionPane.showMessageDialog(this, result);
        mazePanel.repaint();
    }
    

    public static void main(String[] args) {
        int[][] mazeArray = {
                {0, 1, 0, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0}
        };
        Maze maze = new Maze(mazeArray, 0, 0, 4, 4);
        MazeView view = new MazeView(maze);
        view.setVisible(true);
    }
}
