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

    /**
     * Constructor for objects of class Animal.
     * @param location The animal's location.
     */
    public Animal(Location location)
    {
        Random random = new Random();
        this.alive = true;
        this.location = location;
        this.female = random.nextBoolean();
    }
    
    /**
     * Act.
     * @param currentField The current state of the field.
     * @param nextFieldState The new state being built.
     */
    abstract public void act(Field currentField, Field nextFieldState);
        
    /**
     * If a male and female are in a neighbouring cell,
     * the creatures can breed
     */
    public void meet() {
        // im thinking: when two animals of the same type and different genders
        // are in neighbouring cells to each other, set a boolean 'gendersTogether'
        // to true which allows both animals to have a chance of breeding in a
        // neighbouring cell
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
