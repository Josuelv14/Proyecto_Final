package Vista;

import Modelo.Maze;
import Modelo.Algorithms;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MazeView extends JFrame {
    private Maze maze;
    private JPanel mazePanel;
    private JButton bfsButton;
    private JButton dfsButton;
    private JButton dpButton;
    private BufferedImage dragonBallImage;

    public MazeView(Maze maze) {
        this.maze = maze;
        setTitle("Maze Solver");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            dragonBallImage = ImageIO.read(new File(System.getProperty("user.home") + "/Desktop/esferas.jpeg"));
            if (dragonBallImage == null) {
                System.out.println("Error: La imagen no se pudo cargar.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mazePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMaze(g);
            }
        };
        mazePanel.setPreferredSize(new Dimension(500, 500));
        add(mazePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        bfsButton = new JButton("BFS");
        dfsButton = new JButton("DFS");
        dpButton = new JButton("DP");

        bfsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveMaze("BFS");
            }
        });

        dfsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveMaze("DFS");
            }
        });

        dpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveMaze("DP");
            }
        });

        buttonPanel.add(bfsButton);
        buttonPanel.add(dfsButton);
        buttonPanel.add(dpButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void drawMaze(Graphics g) {
        int[][] mazeArray = maze.getMaze();
        int cellSize = Math.min(mazePanel.getWidth() / mazeArray.length, mazePanel.getHeight() / mazeArray[0].length);
        for (int i = 0; i < mazeArray.length; i++) {
            for (int j = 0; j < mazeArray[0].length; j++) {
                if (mazeArray[i][j] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                } else {
                    if (maze.isVisited(i, j)) {
                        if (dragonBallImage != null) {
                            g.drawImage(dragonBallImage, j * cellSize, i * cellSize, cellSize, cellSize, null);
                        } else {
                            g.setColor(Color.RED); // Color de respaldo en caso de que la imagen no se cargue
                            g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                        }
                    } else {
                        g.setColor(Color.WHITE);
                        g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    }
                }
                g.setColor(Color.GRAY);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    private void solveMaze(String method) {
        maze.resetVisited();
        boolean found = false;
        switch (method) {
            case "BFS":
                found = Algorithms.bfs(maze, this);
                break;
            case "DFS":
                found = Algorithms.dfs(maze, maze.getStartX(), maze.getStartY(), this);
                break;
            case "DP":
                Boolean[][] memo = new Boolean[maze.getMaze().length][maze.getMaze()[0].length];
                found = Algorithms.dynamicProgramming(maze, maze.getStartX(), maze.getStartY(), memo, this);
                break;
        }
        JOptionPane.showMessageDialog(this, method + " found a path: " + found);
    }

    public void updateView() {
        mazePanel.repaint();
        try {
            Thread.sleep(100); // Delay for visualization
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
