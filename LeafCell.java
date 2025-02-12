
/**
 * Write a description of class PlantCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LeafCell extends Plant
{
    /**
     * Constructor for objects of class PlantCell
     */
    public LeafCell(Location location)
    {
        super(location);
    }

    /**
     * This increments the plants age.
     * @param currentField The field currently occupied.
     * @param nextFieldState The updated field.
     */
    public void act(Field currentField, Field nextFieldState)
    {
        if (isAlive()) {
            nextFieldState.placePlant(this, getLocation());
        }
    }
}
