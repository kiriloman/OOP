package population;

import grid.Point;

import java.util.ArrayList;
import java.util.List;

public class Individual {
    private double comfort;
    private double deathTime;
    private int id, cost;
    private Point position;
    private List<Point> path;
    private List<Integer> costPath;

    public Individual(int id) {
        this.id = id;
        path = new ArrayList<>();
        costPath = new ArrayList<>();
        comfort = 0;
        cost = 0;
        position = null;
        deathTime = Integer.MAX_VALUE;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Individual)) return false;
        Individual other = (Individual) obj;
        return other.id == id;
    }

    public int getId() {
        return id;
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

    public void setPath(List<Point> path) {
        this.path = path;
    }

    public List<Point> getPath() {
        return path;
    }

    public void addToPath(Point point) {
        path.add(point);
        if (costPath.size() == 0)
            cost = 0;
        else
            cost = costPath.get(costPath.size() - 1);
    }

    public List<Integer> getCostPath(){
        return costPath;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost(){
        return cost;
    }

    public void addToCostPath(int cost) {
        costPath.add(cost);
    }

    public void setCostPath(List<Integer> costPath){
        this.costPath = costPath;
    }

    public void setDeathTime(double deathTime) {
        this.deathTime = deathTime;
    }

    public double getDeathTime() {
        return deathTime;
    }
}
