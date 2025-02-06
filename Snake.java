import java.util.*;

/**
 * A simple model of a snake.
 * Snakes age, move, eat armadillos, and die.
 *
 * @author Aryan Sanvee Vijayan
 * @version 02/02/2025
 */
public class Snake extends Predator
{
    // Characteristics shared by all snakes (class variables).
    // The age at which a snake can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a snake can live.
    private static final int MAX_AGE = 200;
    // The likelihood of a snake breeding.
    private static final double BREEDING_PROBABILITY = 0.1;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 25;
    private static final double NIGHT_HUNT_PROBABILITY = 0.25;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).

    // The snake's age.
    private int age;
    
    /**
     * Create a snake. A snake can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the snake will have random age and hunger level.
     * @param location The location within the field.
     */
    public Snake(boolean randomAge, Location location)
    {
        super(location, Armadillo.class);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
    }

    @Override
    public String toString() {
        return "Snake{" +
                "age=" + age +
                ", alive=" + isAlive() +
                ", location=" + getLocation() +
                ", foodLevel=" + getFoodLevel() +
                '}';
    }

    /**
     * Increase the age. This could result in the snake's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Check if the given animal is a prey of Snake.
     * @param Animal The animal to check if its a prey.
     * @return true, if the animal is a prey, otherwise false.
     */
    protected boolean isPrey(Animal animal) {
        if (animal instanceof Armadillo armadillo) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * A Snake can breed successfully if it has reached the breeding age,
     * and luck is on it's side.
     * @return true if the snake breeds successfully1, false otherwise.
     */
    protected boolean breedSuccess()
    {
        return age >= BREEDING_AGE && rand.nextDouble() <= BREEDING_PROBABILITY;
    }
    
    /**
     * Create a new snake as offspring.
     * @param loc The location off the new offspring.
     * @return The offspring.
     */
    protected Animal offspring(Location loc) {
        Snake young = new Snake(false,loc);
        return young;
    }
    
    /**
     * Generate number of offspring if breeding is successful.
     * @return Number of offspring.
     */
    protected int birthNumber() {
        return rand.nextInt(MAX_LITTER_SIZE) + 1;
    }
    
    public boolean huntSuccess(boolean night) {
        if (night) {
            return rand.nextDouble() >= NIGHT_HUNT_PROBABILITY;
        } 
        else {
            return true;
        }
    }
}
