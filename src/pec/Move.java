package pec;

import grid.Map;
import grid.Point;
import population.Individual;
import population.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a move event of an Individual.
 */
public class Move extends EventInd {
    /**
     * Move constructor.
     * @param time Execution time
     * @param population Population host belongs to
     * @param pec PEC to add event to
     */
    public Move(double time, Population population, PEC pec) {
        super(time, population, pec);
    }

    /**
     * Checks if move's execution time is lower than its host's death time.
     * If so, adds move to PEC.
     */
    @Override
    public void addToPec() {
        if (this.getTime() < host.getDeathTime())
            pec.addEvent(this);
    }

    /**
     * Moves the host, updates its parameters, creates and adds a new move to PEC.
     */
    @Override
    public void execute() {
        List<List<Object>> adjacentNodes = Map.map.get(host.getPosition());
        Random random = new Random();
        int randomIndex = random.nextInt(adjacentNodes.size());
        int costToAdd = (int) adjacentNodes.get(randomIndex).get(1);
        Point newPosition = (Point) adjacentNodes.get(randomIndex).get(0);
        //se o newpoint vai implicar um ciclo, removemos o ciclo
        if (host.getPath().contains(newPosition)) {
            int pos = host.getPath().indexOf(newPosition);
            updateHost(newPosition, host.getPath().subList(0, pos + 1), host.getCostPath().subList(0, pos + 1));
        } else {
            updateHost(newPosition, costToAdd);
        }

        createAndAddNewMove();
        updateBestPath(host);
    }

    /**
     * Update host's parameters after it has moved and new position implied a cycle.
     * @param position New position
     * @param path New path
     * @param costPath New cost of the path
     */
    private void updateHost(Point position, List<Point> path, List<Integer> costPath) {
        host.setPath(path);
        host.setCostPath(costPath);
        host.setPosition(position);
        host.setCost(host.getCostPath().get(host.getCostPath().size() - 1));
        host.setComfort(quickMaths.calculateComfort(host));
    }

    /**
     * Update host's parameters after it has moved and there was no cycle implied.
     * @param position New position
     * @param costToAdd Cost of the new edge
     */
    private void updateHost(Point position, int costToAdd) {
        host.setPosition(position);
        host.addToPath(position);
        host.addToCostPath(host.getCost() + costToAdd);
        host.setComfort(quickMaths.calculateComfort(host));
    }

    /**
     * Creates a new move event with the same host.
     * Adds it to PEC.
     */
    private void createAndAddNewMove() {
        Move mvs = new Move(this.getTime() + quickMaths.moveParameter(host.getComfort()), population, pec);
        mvs.setHost(host);
        mvs.addToPec();
    }

    /**
     * Updates best path.
     * @param host Host
     */
    private void updateBestPath(Individual host) {
        if (host.getPosition().equals(population.finalPoint)) {
            if (!population.finalPointHit) {
                population.finalPointHit = true;
                population.bestPathCost = Integer.MAX_VALUE;
                population.bestPathComfort = -1;
                population.bestPath = new ArrayList<>();
            }
            if (host.getCost() < population.bestPathCost) {
                population.bestPathCost = host.getCost();
                population.bestPathComfort = host.getComfort();
                population.bestPath = new ArrayList<>(host.getPath());
            }
        } else {
            if (!population.finalPointHit) {
                if (host.getComfort() > population.bestPathComfort) {
                    population.bestPathCost = host.getCost();
                    population.bestPathComfort = host.getComfort();
                    population.bestPath = new ArrayList<>(host.getPath());
                }
            }
        }
    }
}
