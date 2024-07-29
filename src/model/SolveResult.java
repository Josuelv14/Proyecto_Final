package model;

import java.util.List;

public class SolveResult {
    private List<Point> path;
    private long time;
    private int steps;

    public SolveResult(List<Point> path, long time, int steps) {
        this.path = path;
        this.time = time;
        this.steps = steps;
    }

    public List<Point> getPath() {
        return path;
    }

    public long getTime() {
        return time;
    }

    public int getSteps() {
        return steps;
    }
}