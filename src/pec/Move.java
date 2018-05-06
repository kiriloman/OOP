package pec;

import grid.Map;
import grid.Point;

import java.util.List;
import java.util.Random;

public class Move extends EventInd {

    public Move(double time) {
        super(time);
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
        //adiciona se o ponto a Path do host
        this.getHost().addToPath(newPosition);
        //actualiza se o costPath
        this.getHost().addToCostPath(this.getHost().getCost() + costToAdd);
    }
}
