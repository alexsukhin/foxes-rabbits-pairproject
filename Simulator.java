import java.util.*;

/**
 * A simple predator-prey simulator, based on a rectangular field containing 
 * rabbits and ocelotes.
 *  
 * @author David J. Barnes, Aryan Sanvee Vijayan and Michael Kölling
 * @version 02/02/2025
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 150;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 100;
    // The probability that a ocelot will be created in any given grid position.
    private static final double OCELOT_CREATION_PROBABILITY = 0.02;
    // The probability that a armadillo will be created in any given position.
    private static final double ARMADILLO_CREATION_PROBABILITY = 0.2;    
    // The probability that a deer will be created in any given position.
    private static final double DEER_CREATION_PROBABILITY = 0.2;
    // The probability that a snake will be created in any given position.
    private static final double SNAKE_CREATION_PROBABILITY = 0.02;
    // The probability that a jaguar will be created in any given position.
    private static final double JAGUAR_CREATION_PROBABILITY = 0.02;
    // The number of steps in one day/night cycle.
    private static final int DAY_STEPS = 20;
    // The current state of day/night.
    private boolean night = false;

    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private final SimulatorView view;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be >= zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        field = new Field(depth, width);
        view = new SimulatorView(depth, width);

        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long 
     * period (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(700);
    }
    
    /**
     * Run the simulation for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        reportStats();
        for(int n = 1; n <= numSteps && field.isViable(); n++) {
            simulateOneStep();
            delay(50);         // adjust this to change execution speed
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each ocelot and armadillo.
     */
    public void simulateOneStep()
    {
        step++;
        // Use a separate Field to store the starting state of
        // the next step.
        Field nextFieldState = new Field(field.getDepth(), field.getWidth());

        List<Animal> animals = field.getAnimals();
        for (Animal anAnimal : animals) {
            anAnimal.act(field, nextFieldState, night);
        }
        
        // Replace the old state with the new one.
        field = nextFieldState;
        
        // Changes the day/time cycle every 50 steps.
        changeTime();

        reportStats();
        view.showStatus(step, night, field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        populate();
        view.showStatus(step, night, field);
    }
    
    /**
     * Randomly populate the field with ocelotes and armadillos.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= DEER_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Deer deer = new Deer(true, location);
                    field.placeAnimal(deer, location);
                }
                else if(rand.nextDouble() <= JAGUAR_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Jaguar jaguar = new Jaguar(true, location);
                    field.placeAnimal(jaguar, location);
                }
                else if(rand.nextDouble() <= SNAKE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Snake snake = new Snake(true, location);
                    field.placeAnimal(snake, location);
                }
                else if(rand.nextDouble() <= OCELOT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Ocelot ocelot = new Ocelot(true, location);
                    field.placeAnimal(ocelot, location);
                }
                else if(rand.nextDouble() <= ARMADILLO_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Armadillo armadillo = new Armadillo(true, location);
                    field.placeAnimal(armadillo, location);
                }
                // else leave the location empty.
            }
        }
    }

    /**
     * Report on the number of each type of animal in the field.
     */
    public void reportStats()
    {
        //System.out.print("Step: " + step + " ");
        field.fieldStats();
    }
    
    /**
     * Pause for a given time.
     * @param milliseconds The time to pause for, in milliseconds
     */
    private void delay(int milliseconds)
    {
        try {
            Thread.sleep(milliseconds);
        }
        catch(InterruptedException e) {
            // ignore
        }
    }
    
    /**
     * Every step cycle, checks whether we are on the 50th multiple step.
     * If we are, changes day/night cycle via flag.
     */
    private void changeTime()
    {
        if (step % DAY_STEPS == 0) {
            night = !night;
        }
    }
}
