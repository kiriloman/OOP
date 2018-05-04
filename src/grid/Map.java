package grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Classe Grid que nao sei como vamos usar
public class Map implements Grid {

    public int colms, rows;
    public List<List<Object>> specialCosts;
    public List<Point> obstacles;

    public static HashMap<Point, List<List<Object>>> map;

    public Map(int colms, int rows) {
        this.colms = colms;
        this.rows = rows;
        this.map = new HashMap<>();
        this.specialCosts = specialCosts;
        this.obstacles = obstacles;
    }


    /*public void createGrid() {
        for (int x = 1; x <= colms; x++) {
            for (int y = 1; y <= rows; y++) {
                ArrayList<Object> adjList = new ArrayList<Object>();


                if (x == 1 && y == 1) {
                    adjList.add(new Point(x, y + 1));
                    adjList.add(new Point(x + 1, y));
                    map.put(new Point(x, y), adjList);
                }

                if (y == 1 && x == colms) {
                    adjList.add(new Point(x, y + 1));
                    adjList.add(new Point(x - 1, y));
                    map.put(new Point(x, y), adjList);
                }

                if (y == rows && x == 1) {
                    adjList.add(new Point(x + 1, y));
                    adjList.add(new Point(x, y - 1));
                    map.put(new Point(x, y), adjList);
                }

                if (y == rows && x == colms) {
                    adjList.add(new Point(x, y - 1));
                    adjList.add(new Point(x - 1, y));
                    map.put(new Point(x, y), adjList);
                }

                if (x == 1 && ((1 < y) && (y < rows))) {
                    adjList.add(new Point(x, y + 1));
                    adjList.add(new Point(x + 1, y));
                    adjList.add(new Point(x, y - 1));
                    map.put(new Point(x, y), adjList);
                }

                if (x == colms && ((1 < y) && (y < rows))) {
                    adjList.add(new Point(x, y + 1));
                    adjList.add(new Point(x, y - 1));
                    adjList.add(new Point(x - 1, y));
                    map.put(new Point(x, y), adjList);
                }

                if (y == 1 && ((1 < x) && (x < colms))) {
                    adjList.add(new Point(x, y + 1));
                    adjList.add(new Point(x + 1, y));
                    adjList.add(new Point(x - 1, y));
                    map.put(new Point(x, y), adjList);
                }

                if (y == rows && ((1 < x) && (x < colms))) {
                    adjList.add(new Point(x + 1, y));
                    adjList.add(new Point(x, y - 1));
                    adjList.add(new Point(x - 1, y));
                    map.put(new Point(x, y), adjList);
                }

                if (((1 < x) && (x < colms)) && ((1 < y) && (y < rows))) {
                    adjList.add(new Point(x, y + 1));
                    adjList.add(new Point(x + 1, y));
                    adjList.add(new Point(x, y - 1));
                    adjList.add(new Point(x - 1, y));
                    map.put(new Point(x, y), adjList);
                }


            }
        }

    }*/


}
