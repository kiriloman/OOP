package pec;

import population.Individual;
import population.Population;
import simulation.QuickMaths;

public class Reproduction extends EventInd {

    public Reproduction(double time) {
        super(time);
    }

    @Override
    public void addToPec() {
        if (this.getTime() < this.getHost().getDeathTime())
            PEC.addEvent(this);
    }

    @Override
    public void execute() {
        //System.out.println("Reproduction"  + " time: " + this.getTime());
        //Population.addIndividual(new Individual(Population.nextChildId));
        Individual parent = this.getHost();

        Individual child = new Individual(Population.nextChildId);
        //path do child
        double parameter1 = (double) (((double) 0.9) * ((double) parent.getPath().size()));
        double parameter2 = (double) (((double) 0.1) * ((double) parent.getComfort()));
        int sizeChildPath = (int) Math.ceil(parameter1 + parameter2);
        //rever com sublists
        for (int j = 0; j < sizeChildPath; j++) {
            child.addToPath(parent.getPath().get(j));
        }
        //posiçao do child
        child.setPosition(child.getPath().get(sizeChildPath - 1));

        //costPath do child
        for (int j = 0; j < sizeChildPath; j++) {
            child.addToCostPath(parent.getCostPath().get(j));
        }
        //cost do child
        child.setCost(child.getCostPath().get(sizeChildPath - 1));
        //comfort do child
        double childComfort = QuickMaths.calculateComfort(child);
        child.setComfort(childComfort);
        Population.addIndividual(child);
        Move cMove = new Move(this.getTime() + QuickMaths.moveParameter(childComfort));
        Reproduction cReproduction = new Reproduction(this.getTime() + QuickMaths.reproductionParameter(childComfort));
        Death cDeath = new Death(this.getTime() + QuickMaths.deathParameter(childComfort));
        cMove.setHost(child);
        cReproduction.setHost(child);
        cDeath.setHost(child);
        cDeath.addToPec();
        cMove.addToPec();
        cReproduction.addToPec();
        //add reproduction of parent
        Reproduction pReproduction = new Reproduction(this.getTime() + QuickMaths.moveParameter(parent.getComfort()));
        pReproduction.setHost(parent);
        pReproduction.addToPec();
    }
}
