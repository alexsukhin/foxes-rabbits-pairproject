import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a ocelot.
 * Ocelotes age, move, eat armadilos, and die.
 * 
 * @author David J. Barnes, Aryan Sanvee Vijayan and Michael Kölling
 * @version 02/02/2025
 */
public class Ocelot extends Predator
{
    // Characteristics shared by all ocelotes (class variables).
    // The age at which a ocelot can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a ocelot can live.
    private static final int MAX_AGE = 180;
    // The likelihood of a ocelot breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    
    private static final double NIGHT_HUNT_PROBABILITY = 0.25;
    // Individual characteristics (instance fields).

    // The ocelot's age.
    private int age;

    /**
     * Create a ocelot. A ocelot can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the ocelot will have random age and hunger level.
     * @param location The location within the field.
     */
    public Ocelot(boolean randomAge, Location location)
    {
        super(location, Armadillo.class);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
    }
    
    public boolean huntSuccess(boolean night) {
        if (night) {
            return rand.nextDouble() >= NIGHT_HUNT_PROBABILITY;
        } 
        else {
            return true;
        }
    }

    @Override
    public String toString() {
        return "Ocelot{" +
                "age=" + age +
                ", alive=" + isAlive() +
                ", location=" + getLocation() +
                ", foodLevel=" + getFoodLevel() +
                '}';
    }

    /**
     * Increase the age. This could result in the ocelot's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Check if the given animal is a prey of Ocelot.
     * @param Animal The animal to check if its a prey.
     * @return true, if the animal is a prey, otherwise false.
     */
    protected boolean isPrey(Animal animal) {
        if(animal instanceof Armadillo armadillo) {
                return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * A ocelot can breed successfully if it has reached the breeding age;
     * and luck is on the ocelot's side.
     * @return true if the ocelot breeds successfully, false otherwise.
     */
    protected boolean breedSuccess()
    {
        return age >= BREEDING_AGE && rand.nextDouble() <= BREEDING_PROBABILITY;
    }
    
    /**
     * Create a new ocelot as offspring.
     * @param loc The location off the new offspring.
     * @return The offspring.
     */
    protected Animal offspring(Location loc) {
        Ocelot young = new Ocelot(false,loc);
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
