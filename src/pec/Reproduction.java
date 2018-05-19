package pec;

import population.Individual;
import population.Population;

/**
 * Represents a reproduction event of an Individual.
 */
public class Reproduction extends EventInd {
    /**
     * Reproduction constructor.
     * @param time Execution time
     * @param population Population host belongs to
     * @param pec PEC to add event to
     */
    public Reproduction(double time, Population population, PEC pec) {
        super(time, population, pec);
    }

    /**
     * Checks if reproduction's execution time is lower than its host's death time.
     * If so, adds reproduction to PEC.
     */
    @Override
    public void addToPec() {
        if (this.getTime() < host.getDeathTime())
            pec.addEvent(this);
    }

    /**
     * Creates a child and its events and adds them to PEC.
     * Creates a new reproduction event for host and adds it to PEC.
     */
    @Override
    public void execute() {
        Individual child = createChild();
        createAndAddChildEvents(child);
        createAndAddParentReproduction();
    }

    /**
     * Creates host's child.
     * @return Host's child
     */
    private Individual createChild() {
        Individual parent = host;
        //path do child
        double parameter1 = 0.9 * ((double) parent.getPath().size());
        double parameter2 = 0.1 * parent.getComfort();
        int childPathLength = (int) Math.ceil(parameter1 + parameter2);
        Individual child = new Individual(population.nextChildId);
        for (int j = 0; j < childPathLength; j++) {
            child.addToPath(parent.getPath().get(j));
        }
        child.setPosition(child.getPath().get(childPathLength - 1));
        //costPath do child
        //tambem cost do child
        for (int j = 0; j < childPathLength; j++) {
            child.addToCostPath(parent.getCostPath().get(j));
        }
        child.setComfort(quickMaths.calculateComfort(child));
        population.addIndividual(child);
        return child;
    }

    /**
     * Creates death, move and reproduction events for given child and
     * adds them to PEC.
     * @param child Host's child
     */
    private void createAndAddChildEvents(Individual child) {
        double childComfort = child.getComfort();
        Move cMove = new Move(this.getTime() + quickMaths.moveParameter(childComfort), population, pec);
        Reproduction cReproduction = new Reproduction(this.getTime() + quickMaths.reproductionParameter(childComfort), population, pec);
        Death cDeath = new Death(this.getTime() + quickMaths.deathParameter(childComfort), population, pec);

        cMove.setHost(child);
        cReproduction.setHost(child);
        cDeath.setHost(child);

        cDeath.addToPec();
        cMove.addToPec();
        cReproduction.addToPec();
    }

    /**
     * Creates a new reproduction event with the same host and adds it to PEC.
     */
    private void createAndAddParentReproduction() {
        Reproduction pReproduction = new Reproduction(this.getTime() + quickMaths.moveParameter(host.getComfort()), population, pec);
        pReproduction.setHost(host);
        pReproduction.addToPec();
    }
}
