package grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a Grid.
 */
public class Map implements Grid {
    /**
     * Keeps track of edges with special costs and
     * its costs.
     */
    private HashMap<List<Point>, Integer> specialCosts;
    /**
     * A list of Points that are obstacles.
     */
    private List<Point> obstacles;
    /**
     * Number of columns of the Grid.
     */
    private int colms;
    /**
     * Number of rows of the Grid.
     */
    private int rows;
    /**
     * An adjacency list with a Point as a key and
     * a list of (OtherPoint, costOfTheEdge) as value.
     */
    public static HashMap<Point, List<List<Object>>> map;

    /**
     * Map constructor.
     * @param colms Number of columns
     * @param rows Number of rows
     * @param obstacles List of points that are obstacles
     * @param specialCosts Special cost zones
     */
    public Map(int colms, int rows, List<Point> obstacles, HashMap<List<Point>, Integer> specialCosts) {
        this.colms = colms;
        this.rows = rows;
        this.specialCosts = specialCosts;
        this.obstacles = obstacles;
        map = new HashMap<>();
    }

    /**
     * Finds an edge of Grid with highest cost and returns the cost.
     * @return Highest cost of an edge
     */
    @Override
    public int getEdgesMaxCost() {
        int maxCost = 1;
        for (Integer i : specialCosts.values()) {
            if (i > maxCost)
                maxCost = i;
        }
        return maxCost;
    }

    /**
     * Represents the Grid as an adjacency list.
     */
    @Override
    public void createGrid() {
        Point fromPoint, toPoint;
        List<List<Object>> adjList;
        List<Object> edge;
        for (int x = 1; x <= colms; x++) {
            for (int y = 1; y <= rows; y++) {
                fromPoint = new Point(x, y);
                // If fromPoint is not an obstacle then we create its adjacency list
                // The adjacent points are added in clockwise order beginning at top.
                if (!obstacles.contains(fromPoint)) {
                    adjList = new ArrayList<>();

                    // Checks if a point above fromPoint is not an obstacle nor is out of the grid.
                    // If so, adds to adjacency list of fromPoint
                    if (y + 1 <= rows && !obstacles.contains(new Point(x, y + 1))) {
                        toPoint = new Point(x, y + 1);
                        edge = new ArrayList<>();
                        edge.add(toPoint);
                        edge.add(getEdgeCost(fromPoint, toPoint));
                        adjList.add(edge);
                    }

                    // Checks if right point of fromPoint is not an obstacle nor is out of the grid.
                    // If so, adds to adjacency list of fromPoint
                    if (x + 1 <= colms && !obstacles.contains(new Point(x + 1, y))) {
                        toPoint = new Point(x + 1, y);
                        edge = new ArrayList<>();
                        edge.add(toPoint);
                        edge.add(getEdgeCost(fromPoint, toPoint));
                        adjList.add(edge);
                    }

                    // Checks if point below fromPoint is not an obstacle nor is out of the grid.
                    // If so, adds to adjacency list of fromPoint
                    if (y - 1 > 0 && !obstacles.contains(new Point(x, y - 1))) {
                        toPoint = new Point(x, y - 1);
                        edge = new ArrayList<>();
                        edge.add(toPoint);
                        edge.add(getEdgeCost(fromPoint, toPoint));
                        adjList.add(edge);
                    }

                    // Checks if left point of fromPoint is not an obstacle nor is out of the grid.
                    // If so, adds to adjacency list of fromPoint
                    if (x - 1 > 0 && !obstacles.contains(new Point(x - 1, y))) {
                        toPoint = new Point(x - 1, y);
                        edge = new ArrayList<>();
                        edge.add(toPoint);
                        edge.add(getEdgeCost(fromPoint, toPoint));
                        adjList.add(edge);
                    }

                    // Adds the fromPoint and its adjacency list to Grid representation.
                    map.put(fromPoint, adjList);
                }
            }
        }
    }

    /**
     * Given an edge (from, to) returns the cost of the edge.
     * @param from From point
     * @param to To point
     * @return Edge cost
     */
    @Override
    public int getEdgeCost(Point from, Point to) {
        List<Point> edge = new ArrayList<>();
        edge.add(from);
        edge.add(to);
        if (specialCosts.get(edge) != null) return specialCosts.get(edge);
        Collections.swap(edge, 0, 1);
        if (specialCosts.get(edge) != null) return specialCosts.get(edge);
        return 1;
    }
}