package grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Map implements Grid {
    private HashMap<List<Point>, Integer> specialCosts;
    private List<Point> obstacles;
    private static int colms, rows;
    private static Point finalPoint;
    public static HashMap<Point, List<List<Object>>> map;

    public Map(int colms, int rows, List<Point> obstacles, HashMap<List<Point>, Integer> specialCosts, Point finalPoint) {
        Map.colms = colms;
        Map.rows = rows;
        Map.finalPoint = finalPoint;
        this.specialCosts = specialCosts;
        this.obstacles = obstacles;
        map = new HashMap<>();
    }

    public int edgeMaxCost() {
        int maxCost = 0;
        for (Integer i : specialCosts.values()) {
            if (i > maxCost)
                maxCost = i;
        }
        return maxCost;
    }

    @Override
    public void createGrid() {
        Point fromPoint, toPoint;
        List<List<Object>> adjList;
        List<Object> edge;
        for (int x = 1; x <= colms; x++) {
            for (int y = 1; y <= rows; y++) {
                fromPoint = new Point(x, y);
                // se este ponto nao e um obstaculo entao vamos buscar a lista de adjacencia
                if (!obstacles.contains(fromPoint)) {
                    adjList = new ArrayList<>();

                    //ve se o ponto em cima nao ta fora da grid nem é um obstaculo
                    if (y + 1 <= rows && !obstacles.contains(new Point(x, y + 1))) {
                        toPoint = new Point(x, y + 1);
                        edge = new ArrayList<>();
                        edge.add(toPoint);
                        edge.add(getEdgeCost(fromPoint, toPoint));
                        adjList.add(edge);
                    }

                    //ve se o ponto a direita nao ta fora da grid nem é um obstaculo
                    if (x + 1 <= colms && !obstacles.contains(new Point(x + 1, y))) {
                        toPoint = new Point(x + 1, y);
                        edge = new ArrayList<>();
                        edge.add(toPoint);
                        edge.add(getEdgeCost(fromPoint, toPoint));
                        adjList.add(edge);
                    }

                    //ve se o ponto em baixo nao ta fora da grid nem é um obstaculo
                    if (y - 1 > 0 && !obstacles.contains(new Point(x, y - 1))) {
                        toPoint = new Point(x, y - 1);
                        edge = new ArrayList<>();
                        edge.add(toPoint);
                        edge.add(getEdgeCost(fromPoint, toPoint));
                        adjList.add(edge);
                    }

                    //ve se o ponto a esquerda nao ta fora da grid nem é um obstaculo
                    if (x - 1 > 0 && !obstacles.contains(new Point(x - 1, y))) {
                        toPoint = new Point(x - 1, y);
                        edge = new ArrayList<>();
                        edge.add(toPoint);
                        edge.add(getEdgeCost(fromPoint, toPoint));
                        adjList.add(edge);
                    }

                    map.put(fromPoint, adjList);
                }
            }
        }
    }

    private int getEdgeCost(Point from, Point to) {
        List<Point> edge = new ArrayList<>();
        edge.add(from);
        edge.add(to);
        if (specialCosts.get(edge) != null) return specialCosts.get(edge);
        Collections.swap(edge, 0, 1);
        if (specialCosts.get(edge) != null) return specialCosts.get(edge);
        return 1;
    }

    public static Point getFinalPoint() {
        return finalPoint;
    }
}