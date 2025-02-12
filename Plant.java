
/**
 * Write a description of class Plant here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Plant extends Organism
{
    /**
     * Constructor for objects of class Plant
     */
    public Plant(Location location)
    {
        super(location);
    }

    /**
     * Act.
     * @param currentField The current state of the field.
     * @param nextFieldState The new state being built.
     */
    abstract public void act(Field currentField, Field nextFieldState);
}
