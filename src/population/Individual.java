package population;

// Classe Individual.
// So tem cenas basicas. É instanciado com um id.
public class Individual {
    private double comfort;
    private int id;

    Individual(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Individual)) return false;
        Individual other = (Individual) obj;
        return other.id == id;
    }
}
