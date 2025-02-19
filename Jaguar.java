import java.util.Random;
import java.util.List;

/**
 * A simple model of a jaguar.
 * Wolves age, move, eat deer, and die.
 * 
 * @author Aryan Sanvee Vijayan
 * @version 18/02/2025
 */
public class Jaguar extends Predator
{
    // Characteristics shared by all wolves (class variables).
    // The age at which a jaguar can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a jaguar can live.
    private static final int MAX_AGE = 180;
    // The likelihood of a jaguar breeding.
    private static final double BREEDING_PROBABILITY = 0.08;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // Jaguars are primarily nocturnal, with low hunting during day.
    // The likelihood of a jaguar hunting during the night.
    private static final double NIGHT_HUNT_PROBABILITY = 0.9;
    // The likelihood of a jaguar hunting during the night.
    private static final double DAY_HUNT_PROBABILITY = 0.25;

    // Individual characteristics (instance fields).

    // The jaguar's age.
    private int age;

    /**
     * Create a jaguar. A jaguar can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the jaguar will have random age and hunger level.
     * @param location The location within the field.
     */
    public Jaguar(boolean randomAge, Location location)
    {
        super(location, Deer.class);
        if(randomAge) {
            age = randInt(MAX_AGE);
        }
        else {
            age = 0;
        }
    }

    @Override
    public String toString() {
        return "Jaguar{" +
        "age=" + age +
        ", alive=" + isAlive() +
        ", location=" + getLocation() +
        ", foodLevel=" +
        '}';
    }

    /**
     * Increase the age. This could result in the jaguar's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Check if the given animal is a prey of Jaguar.
     * @param Animal The animal to check if its a prey.
     * @return true, if the animal is a prey, otherwise false.
     */
    protected boolean isPrey(Animal animal) {
        if(animal instanceof Deer deer) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * A jaguar can breed successfully if it has reached the breeding age,
     * and luck is on its side.
     * @return true if the jaguar breeds successfully, false otherwise.
     */
    protected boolean breedSuccess()
    {
        return age >= BREEDING_AGE && randDouble() <= BREEDING_PROBABILITY;
    }

    /**
     * Create a new jaguar as offspring.
     * @param loc The location off the new offspring.
     * @return The offspring.
     */
    protected Animal offspring(Location loc) {
        Jaguar young = new Jaguar(false,loc);
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
     * Calculate whether a hunt is successful.
     * @param time The time of day/night.
     * @return true if the hunt is successful, false otherwise.
     */
    public boolean huntSuccess(Time time) {
        if (time == Time.NIGHT) {
            return randDouble() <= NIGHT_HUNT_PROBABILITY;
        } 
        else {
            return randDouble() <= DAY_HUNT_PROBABILITY;
        }
    }
}
