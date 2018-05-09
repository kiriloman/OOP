package population;

import grid.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Individual.
 */
public class Individual {
    /**
     * Individual's comfort.
     */
    private double comfort;
    /**
     * Individual's death time.
     */
    private double deathTime;
    /**
     * Individual's id.
     */
    private int id;
    /**
     * Individual's path cost.
     */
    private int cost;
    /**
     * Individual's position on the grid.
     */
    private Point position;
    /**
     * Individual's path.
     */
    private List<Point> path;
    /**
     * A list of costs that represents sub-path cost at each point of individual's path.
     */
    private List<Integer> costPath;

    /**
     * Individual constructor.
     * @param id Individual id
     */
    public Individual(int id) {
        this.id = id;
        path = new ArrayList<>();
        costPath = new ArrayList<>();
        comfort = 0;
        cost = 0;
        position = null;
        deathTime = Integer.MAX_VALUE;
    }

    /**
     * Compares two individuals for equality.
     * @param obj Object to compare to
     * @return True if individuals have same id, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Individual)) return false;
        Individual other = (Individual) obj;
        return other.id == id;
    }

    /**
     * Gets individual's id.
     * @return Individual's id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets individual's position on the grid.
     * @return Individual's position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Sets individual's position.
     * @param position New position
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Gets individual's comfort.
     * @return Individual's comfort
     */
    public double getComfort() {
        return comfort;
    }

    /**
     * Sets individual's comfort.
     * @param comfort New comfort
     */
    public void setComfort(double comfort) {
        this.comfort = comfort;
    }

    /**
     * Sets individual's path.
     * @param path New path
     */
    public void setPath(List<Point> path) {
        this.path = path;
    }

    /**
     * Gets individual's path.
     * @return Individual's path
     */
    public List<Point> getPath() {
        return path;
    }

    /**
     * Adds a given point to individual's path.
     * @param point Point to add to path
     */
    public void addToPath(Point point) {
        path.add(point);
    }

    /**
     * Sets individual's costPath.
     * @param costPath New costPath
     */
    public void setCostPath(List<Integer> costPath){
        this.costPath = costPath;
    }

    /**
     * Gets individual's costPath.
     * @return Individual's costPath
     */
    public List<Integer> getCostPath(){
        return costPath;
    }

    /**
     * Adds a cost to individual's costPath.
     * @param cost Cost to add
     */
    public void addToCostPath(int cost) {
        costPath.add(cost);
        setCost(cost);
    }

    /**
     * Sets individual's cost.
     * @param cost New cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Gets individual's cost.
     * @return Individual's cost
     */
    public int getCost(){
        return cost;
    }

    /**
     * Sets individual's death time.
     * @param deathTime New death time
     */
    public void setDeathTime(double deathTime) {
        this.deathTime = deathTime;
    }

    /**
     * Gets individual's death time.
     * @return Individual's death time
     */
    public double getDeathTime() {
        return deathTime;
    }
}
