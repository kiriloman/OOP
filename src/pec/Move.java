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
        //Alterar cenas no Map para static
        //Alterar map para HashMap<Point, List<List<Object>>> ou parecido
       // System.out.println("Moooooving. host number " + this.getHost().getId() + " time: " + this.getTime());
        List<List<Object>> adjacentNodes = Map.map.get(this.getHost().getPosition());
        Random random = new Random();
        int randomIndex = random.nextInt(adjacentNodes.size());
        Point newPosition = (Point) adjacentNodes.get(randomIndex).get(0);
        int costToAdd = (int) adjacentNodes.get(randomIndex).get(1);
        //se o newpoint vai implicar um ciclo, removemos o ciclo
        if (this.getHost().getPath().contains(newPosition)) {
            int pos = this.getHost().getPath().indexOf(newPosition);
            int initialPathLength = this.getHost().getPath().size();
            for (int i = 0; i < initialPathLength - pos; i++) {
                this.getHost().getPath().remove(pos);
                this.getHost().getCostPath().remove(pos);
            }
        }
        //altera se a posicao do host para newposition
        this.getHost().setPosition(newPosition);
        //actualiza se o costPath
        this.getHost().addToCostPath(this.getHost().getCost() + costToAdd);
        //adiciona se o ponto a Path do host
        this.getHost().addToPath(newPosition);
        //atualiza se o comfort
        //rever isto e a criaçao doutro move (comfort certo?)
        this.getHost().setComfort(QuickMaths.calculateComfort(this.getHost()));

        Move mvs = new Move(this.getTime() + QuickMaths.moveParameter(this.getHost().getComfort()));
        mvs.setHost(this.getHost());
        mvs.addToPec();

        updateBestPath(this.getHost());
    }

    private static void updateBestPath(Individual hst) {
        if (hst.getPosition().equals(Map.finalPoint)) {
            if (!Population.finalPointHit) {
                Population.finalPointHit = true;
                Population.bestPathCost = Integer.MAX_VALUE;
                Population.bestPathComfort = -1;
                Population.bestPath = new ArrayList<>();
            }
            if (hst.getCost() < Population.bestPathCost) {
                Population.bestPathCost = hst.getCost();
                Population.bestPathComfort = hst.getComfort();
                Population.bestPath = new ArrayList<>(hst.getPath());
            }
        } else {
            if (!Population.finalPointHit) {
                if (hst.getComfort() > Population.bestPathComfort) {
                    Population.bestPathCost = hst.getCost();
                    Population.bestPathComfort = hst.getComfort();
                    Population.bestPath = new ArrayList<>(hst.getPath());
                }
            }
        }
    }
}
