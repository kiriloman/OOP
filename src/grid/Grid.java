package grid;

/**
 * An interface for different grids.
 */
interface Grid {
    /**
     * Generates a grid.
     */
    void createGrid();

    /**
     * Gets highest edge cost of the grid.
     * @return Highest edge cost
     */
    int getEdgesMaxCost();

    /**
     * Gets cost of an edge in the grid.
     * @param from From point
     * @param to To point
     * @return Edge cost
     */
    int getEdgeCost(Point from, Point to);
}
