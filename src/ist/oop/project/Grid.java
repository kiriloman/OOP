package ist.oop.project;

import java.util.List;

//Classe Grid que nao sei como vamos usar
public class Grid {
    public int colms, rows;
    public List<List<Object>> specialCosts;
    public List<Point> obstacles;

    Grid(int colms, int rows, List<List<Object>> specialCosts, List<Point> obstacles) {
        this.colms = colms;
        this.rows = rows;
        this.specialCosts = specialCosts;
        this.obstacles = obstacles;
    }
}
