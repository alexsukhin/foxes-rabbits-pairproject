import java.util.List;
import java.util.Random;

/**
 * A simple model of a armadillo.
 * Armadillos age, move, breed, and die.
 * 
 * @author David J. Barnes, Aryan Sanvee Vijayan and Michael Kölling
 * @version 02/02/2025
 */
public class Armadillo extends Prey
{
    // Characteristics shared by all armadillos (class variables).
    // The age at which a armadillo can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a armadillo can live.
    private static final int MAX_AGE = 30;
    // The likelihood of a armadillo breeding.
    private static final double BREEDING_PROBABILITY = 0.35;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    
    // The armadillo's age.
    private int age;

    /**
     * Create a new armadillo. A armadillo may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the armadillo will have a random age.
     * @param location The location within the field.
     */
    public Armadillo(boolean randomAge, Location location)
    {
        super(location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }

    @Override
    public String toString() {
        return "Armadillo{" +
                "age=" + age +
                ", alive=" + isAlive() +
                ", location=" + getLocation() +
                '}';
    }

    /**
     * Increase the age.
     * This could result in the armadillo's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    

    /**
     * A armadillo can breed successfully if it has reached the breeding age,
     * and luck is on its side.
     * @return true if the armadillo breeds successfully, false otherwise.
     */
    protected boolean breedSuccess()
    {
        return age >= BREEDING_AGE && rand.nextDouble() <= BREEDING_PROBABILITY;
    }
    
    /**
     * Create a new armadillo as offspring.
     * @param loc The location off the new offspring.
     * @return The offspring.
     */
    protected Animal offspring(Location loc) {
        Armadillo young = new Armadillo(false,loc);
        return young;
    }
    
    /**
     * Generate number of offspring if breeding is successful.
     * @return Number of offspring.
     */
    protected int birthNumber() {
        return rand.nextInt(MAX_LITTER_SIZE) + 1;
    }
}
