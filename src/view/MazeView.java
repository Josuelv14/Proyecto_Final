package view;

import controller.MazeController;
import model.Point;
import model.SolveResult;

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
    private List<List<Point>> allPaths;
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
        this.allPaths = new ArrayList<>();
        setTitle("Maze Solver");
        setSize(mazeMatrix[0].length * CELL_SIZE + 300, mazeMatrix.length * CELL_SIZE + 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        mazePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMaze(g);
                drawAllPaths(g);
            }
        };
        mazePanel.setPreferredSize(new Dimension(mazeMatrix[0].length * CELL_SIZE, mazeMatrix.length * CELL_SIZE));
        mazePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / CELL_SIZE;
                int row = e.getY() / CELL_SIZE;
                if (row >= 0 && row < mazeMatrix.length && col >= 0 && col < mazeMatrix[0].length) {
                    mazeMatrix[row][col] = (mazeMatrix[row][col] == 0) ? 1 : 0;
                    mazePanel.repaint();
                }
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        algorithmComboBox = new JComboBox<>(new String[]{"BFS", "DFS", "Recursive", "DP"});
        solveButton = new JButton("Solve");
        solveButton.addActionListener(new SolveButtonListener());
        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ClearButtonListener());

        rowsField = new JTextField("5", 5);
        colsField = new JTextField("5", 5);
        startXField = new JTextField("0", 5);
        startYField = new JTextField("0", 5);
        endXField = new JTextField("4", 5);
        endYField = new JTextField("4", 5);
        updateMazeButton = new JButton("Update Maze");
        updateMazeButton.addActionListener(new UpdateMazeButtonListener());

        resultLabel = new JLabel("Results will be shown here");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        controlPanel.add(new JLabel("Rows:"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        controlPanel.add(rowsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(new JLabel("Cols:"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        controlPanel.add(colsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(new JLabel("Start X:"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        controlPanel.add(startXField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        controlPanel.add(new JLabel("Start Y:"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        controlPanel.add(startYField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        controlPanel.add(new JLabel("End X:"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        controlPanel.add(endXField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        controlPanel.add(new JLabel("End Y:"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        controlPanel.add(endYField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        controlPanel.add(updateMazeButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        controlPanel.add(algorithmComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        controlPanel.add(solveButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        controlPanel.add(clearButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        controlPanel.add(resultLabel, gbc);

        add(new JScrollPane(mazePanel), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
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

    private void drawAllPaths(Graphics g) {
        g.setColor(Color.GREEN);
        for (List<Point> path : allPaths) {
            for (Point p : path) {
                g.fillRect(p.y * CELL_SIZE, p.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        g.setColor(Color.RED);
        for (Point p : currentPath) {
            g.fillRect(p.y * CELL_SIZE, p.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    private void drawPath(Graphics g) {
        // Pintar el camino principal en rojo
        g.setColor(Color.RED);
        for (Point p : path) {
            g.fillRect(p.y * CELL_SIZE, p.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    
        // Pintar los otros caminos en verde
        g.setColor(Color.GREEN);
        for (List<Point> otherPath : allPaths) {
            if (!otherPath.equals(path)) {
                for (Point p : otherPath) {
                    g.fillRect(p.y * CELL_SIZE, p.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    private class SolveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String algorithm = (String) algorithmComboBox.getSelectedItem();
            SolveResult result = null;
    
            switch (algorithm) {
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
                currentPath = path;
                resultLabel.setText(String.format("Time: %d ms, Steps: %d", result.getTime(), result.getSteps()));
                mazePanel.repaint();
            } else {
                resultLabel.setText("No path found.");
            }
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            path.clear();
            allPaths.clear();
            resultLabel.setText("Results will be shown here");
            mazePanel.repaint();
        }
    }

    private class UpdateMazeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int rows = Integer.parseInt(rowsField.getText());
            int cols = Integer.parseInt(colsField.getText());
            mazeMatrix = new int[rows][cols];
            int startX = Integer.parseInt(startXField.getText());
            int startY = Integer.parseInt(startYField.getText());
            int endX = Integer.parseInt(endXField.getText());
            int endY = Integer.parseInt(endYField.getText());
            controller.updateMaze(mazeMatrix, new Point(startX, startY), new Point(endX, endY));
            setSize(mazeMatrix[0].length * CELL_SIZE + 300, mazeMatrix.length * CELL_SIZE + 250);
            mazePanel.setPreferredSize(new Dimension(mazeMatrix[0].length * CELL_SIZE, mazeMatrix.length * CELL_SIZE));
            mazePanel.repaint();
        }
    }
    public static void main(String[] args) {
        // Inicializar un laberinto de ejemplo 10x10
        int[][] mazeMatrix = new int[10][10];
        Point start = new Point(0, 0);
        Point end = new Point(9, 9);

        // Crear y mostrar la interfaz gráfica del laberinto
        MazeView view = new MazeView(mazeMatrix, start, end);
        view.setVisible(true);
    }
}