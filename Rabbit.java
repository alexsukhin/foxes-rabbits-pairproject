import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes, Aryan Sanvee Vijayan and Michael Kölling
 * @version 02/02/2025
 */
public class Rabbit extends Prey
{
    // Characteristics shared by all rabbits (class variables).
    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 30;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.35;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    
    // The rabbit's age.
    private int age;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Location location)
    {
        super(location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }

    @Override
    public String toString() {
        return "Rabbit{" +
                "age=" + age +
                ", alive=" + isAlive() +
                ", location=" + getLocation() +
                '}';
    }

    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    

    /**
     * A rabbit can breed successfully if it has reached the breeding age,
     * and luck is on its side.
     * @return true if the rabbit breeds successfully, false otherwise.
     */
    protected boolean breedSuccess()
    {
        return age >= BREEDING_AGE && rand.nextDouble() <= BREEDING_PROBABILITY;
    }
    
    /**
     * Create a new rabbit as offspring.
     * @param loc The location off the new offspring.
     * @return The offspring.
     */
    protected Animal offspring(Location loc) {
        Rabbit young = new Rabbit(false,loc);
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
