package grid;

/**
 * Represents a Point of a Grid.
 */
public class Point {
    /**
     * X coordinate.
     */
    private int x;
    /**
     * Y coordinate.
     */
    private int y;

    /**
     * Point constructor.
     * @param x Coordinate
     * @param y Coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets X coordinate.
     * @return x Coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets X coordinate.
     * @param x Coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets Y coordinate.
     * @return y Coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets Y coordinate.
     * @param y Coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Represents a Point as a String (x, y).
     * @return String representation of this point
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Compares two points for equality.
     * @param object Object to compare
     * @return True if equal, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object == this) return true;
        if (!(object instanceof Point)) return false;
        Point other = (Point) object;
        return other.x == this.x && other.y == this.y;
    }

    /**
     * Defines point's hashcode.
     * @return Hash of this point
     */
    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + x;
        hash = hash * 31 + y;
        return hash;
    }
}
