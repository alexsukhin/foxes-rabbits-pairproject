import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * A general model of a prey in the simulation.
 *
 * @author Aryan Sanvee Vijayan
 * @version 02/02/2025
 */
public abstract class Prey extends Animal
{
    // Represents whether the prey is full or hungry.
    private boolean isFull;
    // Tracks how long the prey has been hungry or full (in steps).
    private int hungerTimer;
    // The number of steps before the prey goes hungry.
    private static final int FULL_STEPS = 10;
    // The number of steps before the prey dies of hunger.
    private static final int HUNGRY_STEPS = 20;
    private static final Random rand = Randomizer.getRandom();

    /**
     * Constructor for objects of class Prey
     */
    public Prey(Location location)
    {
        // initialise instance variables
        super(location);
        isFull = true;
        hungerTimer = 0;
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
            incrementHunger();

            List<Location> freeLocations = nextFieldState.getFreeAdjacentLocations(getLocation());
            checkIfInfected(nextFieldState); 

            if (isInfected() && rand.nextDouble() < 0.5) {
                setDead();
            }
            else if (canAct(weather)) {
                if(!freeLocations.isEmpty()) {
                    giveBirth(nextFieldState, freeLocations);
                }

                Location nextLocation = null;

                if (!isFull) {
                    Location plantLocation = findFood(currentField, time);
                    if (plantLocation != null) {
                        nextLocation = plantLocation;
                    }
                }

                // Try to move into a free location.
                if(nextLocation == null && !freeLocations.isEmpty() && canMove(time)) {
                    nextLocation = freeLocations.get(0);
                } 
                else if (!freeLocations.isEmpty()) {
                    nextLocation = getLocation();
                } 

                if (nextLocation != null) {
                    setLocation(nextLocation);
                    nextFieldState.placeAnimal(this, nextLocation);
                } 
                else {
                    // Overcrowding.
                    setDead();
                }
            }
            else {
                if (getLocation() != null) {
                        nextFieldState.placeAnimal(this, getLocation());
                    }
            }
        }
    }

    /**
     * Make this prey more hungry. This could result in the prey's death.
     * After eating, the prey will be full for FULL_STEPS steps.
     */
    private void incrementHunger()
    {
        if (isFull) {
            hungerTimer++;
            if (hungerTimer >= FULL_STEPS) {
                isFull = false;
                hungerTimer = 0;
            }
        } else {
            hungerTimer++;
            if (hungerTimer >= HUNGRY_STEPS) {
                setDead();
                hungerTimer = 0;
            }
        }
    }

    /**
     * Look for plants adjacent to the current location.
     * Only the first live plant is eaten.
     * @param field The field currently occupied.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(Field field, Time time)
    {
        List<Location> adjacent = field.getAdjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        Location foodLocation = null;
        while(foodLocation == null && it.hasNext()) {
            Location loc = it.next();
            Plant plant = field.getPlantAt(loc);
            if (plant instanceof LeafCell leafCell) {
                leafCell.removePlant();
                hungerTimer = 0;
                isFull = true;

            }
        }
        return foodLocation;
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
