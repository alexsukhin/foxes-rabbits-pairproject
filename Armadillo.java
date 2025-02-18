import java.util.List;
import java.util.Random;

/**
 * A simple model of a armadillo.
 * Armadillos age, move, breed, and die.
 * 
 * @author David J. Barnes, Aryan Sanvee Vijayan and Michael Kölling
 * @version 18/02/2025
 */
public class Armadillo extends Prey
{
    // Characteristics shared by all armadillos (class variables).
    // The age at which a armadillo can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a armadillo can live.
    private static final int MAX_AGE = 30;
    // The likelihood of a armadillo breeding.
    private static final double BREEDING_PROBABILITY = 0.8;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 6;

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
            age = randInt(MAX_AGE);
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
        return age >= BREEDING_AGE && randDouble() <= BREEDING_PROBABILITY;
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
        return randInt(MAX_LITTER_SIZE) + 1;
    }
    
    /**
     * Calculate if an armadillo can move at this step.
     * @param time The time of day/night.
     * @return true if the armadillo can move, false otherwise.
     */
    protected boolean canMove(Time time) {
        if (time == Time.DAY) {
            return true;
        }
        else if ((time == Time.NIGHT) && (randDouble() < 0.5)) {
            return true;
        }
        else {
            return false;
        }
    }
}