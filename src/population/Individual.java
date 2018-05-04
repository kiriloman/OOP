package population;

import grid.Point;

import java.util.List;

public class Individual {
    private double comfort;
    private int id;
    private Point position;
    private List<Point> path;

    public Individual(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Individual)) return false;
        Individual other = (Individual) obj;
        return other.id == id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double getComfort() {
        return comfort;
    }

    public void setComfort(double comfort) {
        this.comfort = comfort;
    }

    public List<Point> getPath() {
        return path;
    }

    public void addToPath(Point point) {
        path.add(point);
    }
}
