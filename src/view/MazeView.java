package view;

import controller.MazeController;
import model.Point;
import model.SolverResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MazeView extends JFrame {
    private static final int CELL_SIZE = 70;
    private MazeController controller;
    private int[][] mazeMatrix;
    private List<Point> path;
    private JComboBox<String> algorithmComboBox;
    private JButton solveButton;
    private JButton clearButton;
    private JButton updateMazeButton;
    private JPanel mazePanel;
    private List<Point> currentPath;
    private Timer timer;
    private JTextField rowsField;
    private JTextField colsField;
    private JTextField startXField;
    private JTextField startYField;
    private JTextField endXField;
    private JTextField endYField;
    private JLabel resultLabel;

    public MazeView(int[][] mazeMatrix, Point start, Point end) {
        this.controller = new MazeController(mazeMatrix, start, end);
        this.mazeMatrix = mazeMatrix;
        this.path = new ArrayList<>();
        this.currentPath = new ArrayList<>();
        setTitle("Maze Solver");
        setSize(mazeMatrix[0].length * CELL_SIZE + 300, mazeMatrix.length * CELL_SIZE + 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        // Panel para el laberinto
        mazePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMaze(g);
                drawPath(g);
            }
        };
        mazePanel.setPreferredSize(new Dimension(mazeMatrix[0].length * CELL_SIZE, mazeMatrix.length * CELL_SIZE));
        mazePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / CELL_SIZE;
                int row = e.getY() / CELL_SIZE;
                if (row >= 0 && row < mazeMatrix.length && col >= 0 && col < mazeMatrix[0].length) {
                    mazeMatrix[row][col] = mazeMatrix[row][col] == 1 ? 0 : 1;
                    mazePanel.repaint();
                }
            }
        });

        // Panel de controles
        JPanel controlPanel = new JPanel();
        algorithmComboBox = new JComboBox<>(new String[]{"BFS", "DFS", "Recursive", "DP"});
        solveButton = new JButton("Solve");
        solveButton.addActionListener(new SolveButtonListener());
        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ClearButtonListener());
        
        // Campos para configuración dinámica
        rowsField = new JTextField("5", 5);
        colsField = new JTextField("5", 5);
        startXField = new JTextField("0", 5);
        startYField = new JTextField("0", 5);
        endXField = new JTextField("4", 5);
        endYField = new JTextField("4", 5);
        updateMazeButton = new JButton("Update Maze");
        updateMazeButton.addActionListener(new UpdateMazeButtonListener());

        resultLabel = new JLabel("Results will be shown here");

        controlPanel.add(new JLabel("Rows:"));
        controlPanel.add(rowsField);
        controlPanel.add(new JLabel("Cols:"));
        controlPanel.add(colsField);
        controlPanel.add(new JLabel("Start Y:"));
        controlPanel.add(startXField);
        controlPanel.add(new JLabel("Start X:"));
        controlPanel.add(startYField);
        controlPanel.add(new JLabel("End X:"));
        controlPanel.add(endXField);
        controlPanel.add(new JLabel("End Y:"));
        controlPanel.add(endYField);
        controlPanel.add(updateMazeButton);
        controlPanel.add(new JLabel("Select Algorithm:"));
        controlPanel.add(algorithmComboBox);
        controlPanel.add(solveButton);
        controlPanel.add(clearButton);
        controlPanel.add(resultLabel);

        // Añadir paneles al frame
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
            if (selectedAlgorithm == null) return;

            SolverResult result = null;
            switch (selectedAlgorithm) {
                case "BFS":
                    result = controller.solveWithBfs();
                    break;
                case "DFS":
                    result = controller.solveWithDfs();
                    break;
                case "Recursive":
                    result = controller.solveWithRecursive();
                    break;
                case "DP":
                    result = controller.solveWithDp();
                    break;
            }

            if (result != null) {
                path = result.getPath();
                showPathStepByStep();
                resultLabel.setText(String.format("Time: %d ms, Steps: %d", result.getTime(), result.getSteps()));
            } else {
                resultLabel.setText("No path found.");
            }
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

    private class UpdateMazeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int rows = Integer.parseInt(rowsField.getText());
                int cols = Integer.parseInt(colsField.getText());
                int startX = Integer.parseInt(startXField.getText());
                int startY = Integer.parseInt(startYField.getText());
                int endX = Integer.parseInt(endXField.getText());
                int endY = Integer.parseInt(endYField.getText());

                mazeMatrix = new int[rows][cols];
                controller = new MazeController(mazeMatrix, new Point(startX, startY), new Point(endX, endY));
                mazePanel.setPreferredSize(new Dimension(cols * CELL_SIZE, rows * CELL_SIZE));
                mazePanel.revalidate();
                mazePanel.repaint();
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid input.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int[][] mazeMatrix = {
                    {0, 1, 0, 0, 0},
                    {0, 1, 0, 1, 0},
                    {0, 0, 0, 1, 0},
                    {0, 1, 0, 0, 0},
                    {0, 0, 0, 1, 0}
                };
                Point start = new Point(0, 0);
                Point end = new Point(4, 4);
                new MazeView(mazeMatrix, start, end).setVisible(true);
            }
        });
    }
}

