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
        Individual child = createChild();
        createAndAddChildEvents(child);
        createAndAddParentReproduction();
    }

    private Individual createChild() {
        Individual parent = this.getHost();
        //path do child
        double parameter1 = 0.9 * ((double) parent.getPath().size());
        double parameter2 = 0.1 * parent.getComfort();
        int childPathLength = (int) Math.ceil(parameter1 + parameter2);
        Individual child = new Individual(Population.nextChildId);
        for (int j = 0; j < childPathLength; j++) {
            child.addToPath(parent.getPath().get(j));
        }
        child.setPosition(child.getPath().get(childPathLength - 1));
        //costPath do child
        //tambem cost do child
        for (int j = 0; j < childPathLength; j++) {
            child.addToCostPath(parent.getCostPath().get(j));
        }
        child.setComfort(QuickMaths.calculateComfort(child));
        Population.addIndividual(child);
        return child;
    }

    private void createAndAddChildEvents(Individual child) {
        double childComfort = child.getComfort();
        Move cMove = new Move(this.getTime() + QuickMaths.moveParameter(childComfort));
        Reproduction cReproduction = new Reproduction(this.getTime() + QuickMaths.reproductionParameter(childComfort));
        Death cDeath = new Death(this.getTime() + QuickMaths.deathParameter(childComfort));

        cMove.setHost(child);
        cReproduction.setHost(child);
        cDeath.setHost(child);

        cDeath.addToPec();
        cMove.addToPec();
        cReproduction.addToPec();
    }

    private void createAndAddParentReproduction() {
        Individual host = this.getHost();
        Reproduction pReproduction = new Reproduction(this.getTime() + QuickMaths.moveParameter(host.getComfort()));
        pReproduction.setHost(host);
        pReproduction.addToPec();
    }
}
