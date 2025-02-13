import java.util.List;
import java.util.Random;

/**
 * Common elements of foxes and rabbits.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 7.0
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's position.
    private Location location;
    // Whether the animal is male or female.
    // True for female, false for male.
    private boolean female;
    // Whether the animal is infected
    private boolean infected;
    private static final Random rand = Randomizer.getRandom();

    /**
     * Constructor for objects of class Animal.
     * @param location The animal's location.
     */
    public Animal(Location location)
    {
        Random random = new Random();
        alive = true;
        this.location = location;
        female = random.nextBoolean();
        if (rand.nextDouble() < 0.005) {
            infected = true;
        }
        else {
            infected = false;
        }
    }

    /**
     * Act.
     * @param currentField The current state of the field.
     * @param nextFieldState The new state being built.
     */
    abstract public void act(Field currentField, Field nextFieldState, Time time, Weather weather);
    
    public boolean canAct(Weather weather) {
        if (weather == Weather.CLEAR) {
            return true;
        }
        else if (weather == Weather.RAIN && rand.nextDouble() < 0.6) {
            return true;
        }
        else if (weather == Weather.CLOUDY && rand.nextDouble() < 0.8) {
            return true;
        }
        else if (weather == Weather.STORM && rand.nextDouble() < 0.4) {
            return true;
        }
        return false;
    }

    /**
     * Checks if there is a compatible mate in the adjacent cells.
     * @param field The field to check for adjacent animals.
     * @return true if a compatible mate is found, false otherwise
     */
    public boolean hasCompatibleMate(Field field) {
        List<Location> adjacentLocations = field.getAdjacentLocations(getLocation());
        for (Location loc : adjacentLocations) {
            Animal animal = field.getAnimalAt(loc);
            if ((animal != null) && (animal.getClass() == this.getClass()) 
                && (animal.isFemale() != this.isFemale())) {
                return true;
            }
        }
        return false;
    }
    
    public void checkIfInfected(Field field) {
        List<Location> adjacentLocations = field.getAdjacentLocations(getLocation());
        for (Location loc : adjacentLocations) {
            Animal animal = field.getAnimalAt(loc);
            if (animal != null && animal.isInfected()) {
                infected= true;
            }
        }
    }
    
    public boolean isInfected() {
        return infected;
    }
    
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     */
    protected void setDead()
    {
        alive = false;
        location = null;
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Set the animal's location.
     * @param location The new location.
     */
    protected void setLocation(Location location)
    {
        this.location = location;
    }

    /**
     * Check whether the animal is female or not.
     * @return true if the animal is female.
     */
    public boolean isFemale()
    {
        return female;
    }
}
