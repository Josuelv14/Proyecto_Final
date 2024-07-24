package view;

import controller.MazeController;
import model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class MazeView extends JFrame {
    private static final int CELL_SIZE = 70;
    private MazeController controller;
    private int[][] mazeMatrix;
    private List<Point> path;
    private JComboBox<String> algorithmComboBox;
    private JButton solveButton;
    private JButton clearButton;
    private JPanel mazePanel;
    private List<Point> currentPath;
    private Timer timer;

    public MazeView(int[][] mazeMatrix, Point start, Point end) {
        this.controller = new MazeController(mazeMatrix, start, end);
        this.mazeMatrix = mazeMatrix;
        this.path = new ArrayList<>();
        this.currentPath = new ArrayList<>();
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
        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ClearButtonListener());
        controlPanel.add(new JLabel("Select Algorithm:"));
        controlPanel.add(algorithmComboBox);
        controlPanel.add(solveButton);
        controlPanel.add(clearButton);
        
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
        g.setColor(Color.RED);
        for (Point p : currentPath) {
            g.fillRect(p.y * CELL_SIZE, p.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    private class SolveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            path.clear();
            currentPath.clear();
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
            showPathStepByStep();
        }
    }

    private void showPathStepByStep() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        currentPath.clear();
        int delay = 100; // milliseconds
        timer = new Timer(delay, new ActionListener() {
            private int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < path.size()) {
                    currentPath.add(path.get(index));
                    mazePanel.repaint();
                    index++;
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
            currentPath.clear();
            path.clear();
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
        Point end = new Point(4, 4);
        MazeView mazeView = new MazeView(mazeMatrix, start, end);
        mazeView.setVisible(true);
    }
}
