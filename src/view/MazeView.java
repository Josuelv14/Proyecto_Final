package view;

import controller.MazeController;
import model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MazeView extends JFrame {
    private static final int CELL_SIZE = 30;
    private MazeController controller;
    private int[][] mazeMatrix;
    private List<Point> path;
    private JComboBox<String> algorithmComboBox;
    private JButton solveButton;
    private JPanel mazePanel;

    public MazeView(int[][] mazeMatrix, Point start, Point end) {
        this.controller = new MazeController(mazeMatrix, start, end);
        this.mazeMatrix = mazeMatrix;
        setTitle("Maze Solver");
        setSize(mazeMatrix[0].length * CELL_SIZE + 150, mazeMatrix.length * CELL_SIZE + 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        // Create panel for maze
        mazePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMaze(g);
                drawPath(g);
            }
        };
        mazePanel.setPreferredSize(new Dimension(mazeMatrix[0].length * CELL_SIZE, mazeMatrix.length * CELL_SIZE));
        
        // Create panel for controls
        JPanel controlPanel = new JPanel();
        algorithmComboBox = new JComboBox<>(new String[]{"BFS", "DFS", "Recursive", "DP"});
        solveButton = new JButton("Solve");
        solveButton.addActionListener(new SolveButtonListener());
        controlPanel.add(new JLabel("Select Algorithm:"));
        controlPanel.add(algorithmComboBox);
        controlPanel.add(solveButton);
        
        // Add panels to frame
        setLayout(new BorderLayout());
        add(mazePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void drawMaze(Graphics g) {
        for (int row = 0; row < mazeMatrix.length; row++) {
            for (int col = 0; col < mazeMatrix[0].length; col++) {
                if (mazeMatrix[row][col] == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.GRAY);
                g.drawRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private void drawPath(Graphics g) {
        if (path != null) {
            g.setColor(Color.RED);
            for (Point p : path) {
                g.fillRect(p.y * CELL_SIZE, p.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private class SolveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            switch (selectedAlgorithm) {
                case "BFS":
                    path = controller.solveWithBfs();
                    break;
                case "DFS":
                    path = controller.solveWithDfs();
                    break;
                case "Recursive":
                    path = controller.solveWithRecursive();
                    break;
                case "DP":
                    path = controller.solveWithDp();
                    break;
            }
            mazePanel.repaint();
        }
    }

    public static void main(String[] args) {
        int[][] mazeMatrix = {
                {0, 1, 0, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 1, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 0, 1, 0}
        };
        Point start = new Point(0, 0);
        Point end = new Point(0, 4);
        MazeView mazeView = new MazeView(mazeMatrix, start, end);
        mazeView.setVisible(true);
    }
}
