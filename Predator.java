import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A general model of a predator in the simulation.
 *
 * @author Aryan Sanvee Vijayan
 * @version 02/02/2025
 */
public abstract class Predator extends Animal
{
    // The Predator's food level, which is increased by eating armadillos.
    private int foodLevel;
    // The food value of a single armadillo. In effect, this is the
    // number of steps a armadillo-eating predator can go before it has to eat again.
    private static final int ARMADILLO_FOOD_VALUE = 9;
    // The food value of a single deer. 
    private static final int DEER_FOOD_VALUE = 50;
    // For food value at start.
    private static final Random rand = Randomizer.getRandom();

    /**
     * Create a predator. A predator can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param prey The class of the prey of the predator.
     * @param location The location within the field.
     */
    public Predator(Location location, Class<?> prey)
    {
        super(location);
        if (prey == Armadillo.class) {
            foodLevel = rand.nextInt(ARMADILLO_FOOD_VALUE);
        }
        else if (prey == Deer.class) {
            foodLevel = rand.nextInt(DEER_FOOD_VALUE);
        }
    }

    /**
     * This is what the predator does most of the time: it hunts for
     * armadillos. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param currentField The field currently occupied.
     * @param nextFieldState The updated field.
     */
    public void act(Field currentField, Field nextFieldState, boolean night)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            List<Location> freeLocations =
                nextFieldState.getFreeAdjacentLocations(getLocation());
            if(! freeLocations.isEmpty()) {
                giveBirth(nextFieldState, freeLocations);
            }
            // Move towards a source of food if found.
            Location nextLocation = findFood(currentField, night);
            if(nextLocation == null && ! freeLocations.isEmpty()) {
                // No food found - try to move to a free location.
                nextLocation = freeLocations.remove(0);
            }
            // See if it was possible to move.
            if(nextLocation != null) {
                setLocation(nextLocation);
                nextFieldState.placeAnimal(this, nextLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Make this predator more hungry. This could result in the predator's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for prey adjacent to the current location.
     * Only the first live prey is eaten.
     * @param field The field currently occupied.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(Field field, boolean night)
    {
        List<Location> adjacent = field.getAdjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        Location foodLocation = null;
        while(foodLocation == null && it.hasNext()) {
            Location loc = it.next();
            Animal animal = field.getAnimalAt(loc);
            if(isPrey(animal) && huntSuccess(night)) {
                if(animal instanceof Armadillo armadillo) {
                    armadillo.setDead();
                    foodLevel = ARMADILLO_FOOD_VALUE;
                    foodLocation = loc;
                }
                else if(animal instanceof Deer deer) {
                    deer.setDead();
                    foodLevel = DEER_FOOD_VALUE;
                    foodLocation = loc;
                }
            }
        }
        return foodLocation;
    }

    /**
     * Get the food level of the predator.
     * @return the food level.
     */
    public int getFoodLevel() {
        return foodLevel;
    }
    
    /**
     * Check whether this predator is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param freeLocations The locations that are free in the current field.
     */
    protected void giveBirth(Field nextFieldState, List<Location> freeLocations)
    {
        // New armadillos are born into adjacent locations.
        // Get a list of adjacent free locations.
        int births = breed();
        if(births > 0) {
            for (int b = 0; b < births && !freeLocations.isEmpty(); b++) {
                Location loc = freeLocations.remove(0);
                Animal young = offspring(loc);
                nextFieldState.placeAnimal(young, loc);
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
     * This could result in the predator's death.
     */
    abstract protected void incrementAge();
    
    /**
     * Check if the given animal is a prey of the predator.
     * @param Animal The animal to check if its a prey.
     * @return true, if the animal is a prey, otherwise false.
     */
    abstract protected boolean isPrey(Animal animal);
    
    /**
     * Create a new predator as offspring.
     * @param loc The location off the new offspring.
     * @return The offspring.
     */
    abstract protected Animal offspring(Location loc);
    
    /**
     * A predator can breed successfully if it has reached the breeding age,
     * and luck is on its side.
     * @return true if the prey breeds successfully, false otherwise.
     */
    abstract protected boolean breedSuccess();
    
    /**
     * Generate number of offspring if breeding is successful.
     * @return Number of offspring.
     */
    abstract protected int birthNumber();
    
    abstract protected boolean huntSuccess(boolean isNight);
}
