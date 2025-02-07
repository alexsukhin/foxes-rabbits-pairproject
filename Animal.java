import java.util.List;
import java.util.Random;

/**
 * Common elements of predators and preys.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 7.0
 */
public abstract class Animal extends Organism
{
    // Whether the animal is male or female.
    // True for female, false for male.
    private boolean female;

    /**
     * Constructor for objects of class Animal.
     * @param location The animal's location.
     */
    public Animal(Location location)
    {
        super(location);
        Random random = new Random();
        this.female = random.nextBoolean();
    }
    
    /**
     * Act.
     * @param currentField The current state of the field.
     * @param nextFieldState The new state being built.
     */
    abstract public void act(Field currentField, Field nextFieldState, Time time);
        
    /**
     * Checks if there is a compatible mate in the adjacent cells.
     * @param field The field to check for adjacent animals.
     * @return true if a compatible mate is found, false otherwise
     */
    public boolean hasCompatibleMate(Field field) {
        List<Location> adjacentLocations = field.getAdjacentLocations(getLocation());
        for (Location loc : adjacentLocations) {
            Animal animal = field.getAnimalAt(loc);
            if ((animal != null) && (animal.getClass() == this.getClass()) && (animal.isFemale() != this.isFemale())) {
                return true;
            }
        }
        return false;
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
