import java.util.Random;
import java.util.List;

/**
 * A simple model of a wolf.
 * Wolves age, move, eat deer, and die.
 * 
 * @author Aryan Sanvee Vijayan
 * @version 02/02/2025
 */
public class Wolf extends Predator
{
    // Characteristics shared by all wolves (class variables).
    // The age at which a wolf can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a wolf can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a wolf breeding.
    private static final double BREEDING_PROBABILITY = 0.07;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).

    // The wolf's age.
    private int age;

    /**
     * Create a wolf. A wolf can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the wolf will have random age and hunger level.
     * @param location The location within the field.
     */
    public Wolf(boolean randomAge, Location location)
    {
        super(location, Deer.class);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
    }

    @Override
    public String toString() {
        return "Wolf{" +
                "age=" + age +
                ", alive=" + isAlive() +
                ", location=" + getLocation() +
                ", foodLevel=" + getFoodLevel() +
                '}';
    }

    /**
     * Increase the age. This could result in the wolf's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    protected boolean isPrey(Animal animal) {
        if(animal instanceof Deer deer) {
                return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * A wolf can breed successfully if it has reached the breeding age,
     * and luck is on its side.
     * @return true if the wolf breeds successfully, false otherwise.
     */
    protected boolean breedSuccess()
    {
        return age >= BREEDING_AGE && rand.nextDouble() <= BREEDING_PROBABILITY;
    }
    
    /**
     * Create a new wolf as offspring.
     * @param loc The location off the new offspring.
     * @return The offspring.
     */
    protected Animal offspring(Location loc) {
        Wolf young = new Wolf(false,loc);
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
