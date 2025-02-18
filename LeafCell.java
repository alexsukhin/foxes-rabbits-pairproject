
/**
 * This class represents the leaf cells extending
 * from each core plant, acting as a food source
 * for prey.
 *
 * @author Alexander Sukhin
 * @version 13/02/2025
 */
public class LeafCell extends Plant
{
    // Represents the core plant which the leaf
    // cell extends from.
    private CorePlant parent;
    
    /**
     * Constructor for objects of class PlantCell
     */
    public LeafCell(Location location, CorePlant corePlant)
    {
        super(location);
        this.parent = corePlant;
    }

    /**
     * This is what the leaf cells do most of the time. It remains stationary
     * until eaten from a prey.
     * @param currentField The field currently occupied.
     * @param nextFieldState The updated field.
     */
    public void act(Field currentField, Field nextFieldState)
    {
        if (isAlive()) {
            nextFieldState.placePlant(this, getLocation());
        }
    }
    
    /**
     * Removes a leaf cell plant from the the field and
     * the list of leaf cells within the core plant.
     */
    public void removePlant()
    {
        setDead();
        parent.removeLeafCell(this);
    }
}
