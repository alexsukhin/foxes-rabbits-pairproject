import java.util.List;
import java.util.Random;

/**
 * A general model of a prey in the simulation.
 *
 * @author Aryan Sanvee Vijayan
 * @version 02/02/2025
 */
public abstract class Prey extends Animal
{
    private static final Random rand = Randomizer.getRandom();

    /**
     * Constructor for objects of class Prey
     */
    public Prey(Location location)
    {
        // initialise instance variables
        super(location);
    }

    /**
     * This is what the prey does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param currentField The field occupied.
     * @param nextFieldState The updated field.
     */
    public void act(Field currentField, Field nextFieldState, Time time, Weather weather)
    {
        incrementAge();
        if(isAlive()) {
            List<Location> freeLocations = 
                nextFieldState.getFreeAdjacentLocations(getLocation());
            checkIfInfected(nextFieldState); 

            if (isInfected() && rand.nextDouble() < 0.5) {
                setDead();
            }
            else if (canAct(weather)){
                if(!freeLocations.isEmpty()) {
                    giveBirth(nextFieldState, freeLocations);
                }
                // Try to move into a free location.
                if(! freeLocations.isEmpty() && canMove(time)) {
                    Location nextLocation = freeLocations.get(0);
                    setLocation(nextLocation);
                    nextFieldState.placeAnimal(this, nextLocation);
                } 
                else if (! freeLocations.isEmpty()) {
                    nextFieldState.placeAnimal(this, getLocation());
                } 
                else {
                    // Overcrowding.
                    setDead();
                }
            }
            else {
                nextFieldState.placeAnimal(this, getLocation());
            }
        }
    }

    /**
     * Check whether this prey is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param freeLocations The locations that are free in the current field.
     */
    protected void giveBirth(Field nextFieldState, List<Location> freeLocations)
    {
        // New armadillos are born into adjacent locations.
        // Get a list of adjacent free locations.
        if (hasCompatibleMate(nextFieldState)) {    
            int births = breed();
            if(births > 0) {
                for (int b = 0; b < births && !freeLocations.isEmpty(); b++) {
                    Location loc = freeLocations.remove(0);
                    Animal young = offspring(loc);
                    nextFieldState.placeAnimal(young, loc);
                }
            }
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births;
        if(breedSuccess()) {
            births = birthNumber();
        }
        else {
            births = 0;
        }
        return births;
    }

    /**
     * Increase the age.
     * This could result in the prey's death.
     */
    abstract protected void incrementAge();

    /**
     * Create a new prey as offspring.
     * @param loc The location off the new offspring.
     * @return The offspring.
     */
    abstract protected Animal offspring(Location loc);

    /**
     * A prey can breed successfully if it has reached the breeding age,
     * and luck is on its side.
     * @return true if the prey breeds successfully, false otherwise.
     */
    abstract protected boolean breedSuccess();

    /**
     * Generate number of offspring if breeding is successful.
     * @return Number of offspring.
     */
    abstract protected int birthNumber();

    abstract protected boolean canMove(Time time);
}
