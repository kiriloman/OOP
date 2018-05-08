package pec;

import grid.Map;
import grid.Point;
import population.Individual;
import population.Population;
import simulation.QuickMaths;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Move extends EventInd {

    public Move(double time) {
        super(time);
    }

    @Override
    public void addToPec() {
        if (this.getTime() < this.getHost().getDeathTime())
            PEC.addEvent(this);
    }

    @Override
    public void execute() {
        Individual host = this.getHost();
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

    private void updateHost(Point position, List<Point> path, List<Integer> costPath) {
        Individual host = this.getHost();
        host.setPath(path);
        host.setCostPath(costPath);
        host.setPosition(position);
        host.setCost(host.getCostPath().get(host.getCostPath().size() - 1));
        host.setComfort(QuickMaths.calculateComfort(host));
    }

    private void updateHost(Point position, int costToAdd) {
        Individual host = this.getHost();
        host.setPosition(position);
        host.addToPath(position);
        host.addToCostPath(host.getCost() + costToAdd);
        host.setComfort(QuickMaths.calculateComfort(host));
    }

    private void createAndAddNewMove() {
        Move mvs = new Move(this.getTime() + QuickMaths.moveParameter(this.getHost().getComfort()));
        mvs.setHost(this.getHost());
        mvs.addToPec();
    }

    private void updateBestPath(Individual host) {
        if (host.getPosition().equals(Map.getFinalPoint())) {
            if (!Population.finalPointHit) {
                Population.finalPointHit = true;
                Population.bestPathCost = Integer.MAX_VALUE;
                Population.bestPathComfort = -1;
                Population.bestPath = new ArrayList<>();
            }
            if (host.getCost() < Population.bestPathCost) {
                Population.bestPathCost = host.getCost();
                Population.bestPathComfort = host.getComfort();
                Population.bestPath = new ArrayList<>(host.getPath());
            }
        } else {
            if (!Population.finalPointHit) {
                if (host.getComfort() > Population.bestPathComfort) {
                    Population.bestPathCost = host.getCost();
                    Population.bestPathComfort = host.getComfort();
                    Population.bestPath = new ArrayList<>(host.getPath());
                }
            }
        }
    }
}
