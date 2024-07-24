package model;

import java.util.List;

public interface SolverResult {
    List<Point> getPath();
    long getTime();
    int getSteps();
}
