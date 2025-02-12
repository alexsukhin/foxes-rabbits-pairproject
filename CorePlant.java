import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class Plant here.
 *
 * @author Alexander Sukhin
 * @version 11/02/2025
 */
public class CorePlant extends Plant
{
    
    private int age;
    private int phase;
    private List<LeafCell> leafCells;
    // The age to which a plant can live.
    private static final int PHASE_2_AGE = 10;
    private static final int PHASE_3_AGE = 30;
    
    /**
     * Constructor for objects of class Plant
     */
    public CorePlant(Location location)
    {
        super(location);
        age = 0;
        leafCells = new ArrayList<>();
        phase = 1;
    }
    
    /**
     * This increments the plants age.
     * @param currentField The field currently occupied.
     * @param nextFieldState The updated field.
     */
    public void act(Field currentField, Field nextFieldState)
    {
        incrementAge();
        if (isAlive()) {            
            
            if (age == PHASE_2_AGE) {
                changePhase(2);
                handleGrowth(currentField, nextFieldState);
            } else if (age == PHASE_3_AGE) {
                changePhase(3);
                handleGrowth(currentField, nextFieldState);
            }
                
            nextFieldState.placePlant(this, getLocation());
        }
    }
    
    private void handleGrowth(Field currentField, Field nextFieldState)
    {
        int row = getLocation().row();
        int col = getLocation().col();
        int width = currentField.getWidth();
        int depth = currentField.getDepth();
        
        int minOffSet = (phase == 2) ? 0 : -1;
        
        if (phase == 3) leafCells.clear();
        
        for(int roffset = minOffSet; roffset <= 1; roffset++) {
                for(int coffset = minOffSet; coffset <= 1; coffset++) {
                    int nextRow = row + roffset;
                    int nextCol = col + coffset;
                    
                    if (nextRow >= 0 && nextRow < depth && nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                        Location newLocation = new Location(nextRow, nextCol);
                        addLeafCell(newLocation, nextFieldState);
                    }
                }
        }
    }
    
    /**
     * Increase the age.
     */
    private void incrementAge()
    {
        age++;
    }
    
    private void changePhase(int phase)
    {
        if (phase == 2 || phase == 3) {
            this.phase = phase;
        } else {
            System.out.println("Not possible!");
        }
    }
    
    /**
     * Adds a new leaf cell to the list of leaf cells and places it in the next field state.
     * @param location The location where the new leaf cell will be placed.
     * @param nextFieldState The updated field.
     */
    private void addLeafCell(Location location, Field nextFieldState) {
        LeafCell newLeafCell = new LeafCell(location);
        nextFieldState.placePlant(newLeafCell, location);
        leafCells.add(newLeafCell);
    }
    
}
