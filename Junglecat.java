import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Junglecat extends Animal
{
    // Characteristics shared by all foxes (static fields).
    
    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 13;
    // The age to which a fox can live.
    private static final int MAX_AGE = 95;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.10;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 7;
    private static final int FOX_FOOD_VALUE = 13;
    private static final int HUNTER_FOOD_VALUE = 20;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    private Random random;
    
    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Junglecat(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            setFoodLevel(rand.nextInt(RABBIT_FOOD_VALUE));
        }
        else {
            setAge(0);
            setFoodLevel(FOX_FOOD_VALUE);
        }
    }
    
    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newFoxes A list to add newly born foxes to.
     */
    public void act(List<Actor> newJunglecats)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newJunglecats);            
            // Move towards a source of food if found.
            Location location = getLocation();
            Location newLocation = findFood(location);
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(location);
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    
    
   
    
    /**
     * Tell the fox to look for rabbits adjacent to its current location.
     * Only the first live rabbit is eaten.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(Location location)
    {
        Field field = getField();
        List<Location> adjacent = field.leapLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    if(getFoodLevel() < RABBIT_FOOD_VALUE)
                    {
                    	setFoodLevel(RABBIT_FOOD_VALUE);
                    }
                    // Remove the dead rabbit from the field.
                    return where;
                }                
            }
            if(animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if(fox.isAlive()) {
                	random = new Random();
                	int tempint = random.nextInt(3);
                	if(tempint!=0)
                	{
                		fox.setDead();
                		if(getFoodLevel() < FOX_FOOD_VALUE)
                		{
                			setFoodLevel(FOX_FOOD_VALUE);
                		}
                	}
                    return where;
                }               
            }
            if(animal instanceof Hunter) {
                Hunter hunter = (Hunter) animal;
                if(hunter.isAlive()) {
                	random = new Random();
                		hunter.setDead();
                		if(getFoodLevel() < HUNTER_FOOD_VALUE)
                		{
                			setFoodLevel(HUNTER_FOOD_VALUE);
                		}
                    return where;
                }               
            }
        }
        return null;
    }
    
    public int getBreedingAge()
    {
    	return BREEDING_AGE;
    }
    
    public int getMaxAge()
    {
    	return MAX_AGE;
    }
    
    public int getMaxLitterSize()
    {
    	return MAX_LITTER_SIZE;
    }
    
    public double getBreedingProbability()
    {
    	return BREEDING_PROBABILITY;
    }
    
    public String getAnimal()
    {
    	return "Junglecat";
    }
    
/*    public void giveBirth(List<Actor> newAnimals)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Fox young = new Fox(false, field, loc);
            newAnimals.add(young);
        }
    } */
}