import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes, Aryan Sanvee Vijayan and Michael Kölling
 * @version 02/02/2025
 */
public class Fox extends Predator
{
    // Characteristics shared by all foxes (class variables).
    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a fox can live.
    private static final int MAX_AGE = 180;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.1;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).

    // The fox's age.
    private int age;

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param location The location within the field.
     */
    public Fox(boolean randomAge, Location location)
    {
        super(location, Rabbit.class);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
    }

    @Override
    public String toString() {
        return "Fox{" +
                "age=" + age +
                ", alive=" + isAlive() +
                ", location=" + getLocation() +
                ", foodLevel=" + getFoodLevel() +
                '}';
    }

    /**
     * Increase the age. This could result in the fox's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Check if the given animal is a prey of Fox.
     * @param Animal The animal to check if its a prey.
     * @return true, if the animal is a prey, otherwise false.
     */
    protected boolean isPrey(Animal animal) {
        if(animal instanceof Rabbit rabbit) {
                return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * A fox can breed successfully if it has reached the breeding age;
     * and luck is on the fox's side.
     * @return true if the fox breeds successfully, false otherwise.
     */
    protected boolean breedSuccess()
    {
        return age >= BREEDING_AGE && rand.nextDouble() <= BREEDING_PROBABILITY;
    }
    
    /**
     * Create a new fox as offspring.
     * @param loc The location off the new offspring.
     * @return The offspring.
     */
    protected Animal offspring(Location loc) {
        Fox young = new Fox(false,loc);
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
