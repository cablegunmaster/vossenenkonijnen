
import java.util.List; 
import java.util.Iterator;


/**
* A simple model of a manbearpig.
* manbearpig age, moves, eats rabbits and foxes, and dies.
* 
* @author David J. Barnes and Michael Kolling
* @version 2008.03.30
*/


public class Manbearpig extends Animal
{
		
	
	    // Characteristics for manbearpig (static fields).
	    
	    
	    // The age to which  manbearpig can live.
	    private static final int MAX_AGE = 500;	    
	    // number of steps a manbearpig can go before it has to eat again.
	    private static final int RABBIT_FOOD_VALUE = 4;
	    private static final int FOX_FOOD_VALUE = 8;
	    private static final int JUNGLECAT_FOOD_VALUE = 10;
	    private static final int HUNTER_FOOD_VALUE = 15;
	    private static final int MAX_FOOD_VALUE = 150;
	    // A shared random number generator to control breeding.
	    //private static final Random rand = Randomizer.getRandom();
	    
	    /**
	     * Create manbearpig. Manbearpig can be created as a new born (age zero
	     * and not hungry) or with a random age and food level.
	     * 
	     * @param randomAge If true, the fox will have random age and hunger level.
	     * @param field The field currently occupied.
	     * @param location The location within the field.
	     */
	    public Manbearpig(boolean randomAge, Field field, Location location)
	    {
	        super(field, location);
	            setAge(0);
	            setFoodLevel(FOX_FOOD_VALUE);
	    }
	    
	    /**
	     * This is what the fox does most of the time: it hunts for
	     * rabbits. In the process, it might breed, die of hunger,
	     * or die of old age.
	     * @param field The field currently occupied.
	     * @param newFoxes A list to add newly born foxes to.
	     */
	    public void act(List<Actor> newManbearpig)
	    {
	        incrementAge();
	        incrementHunger();
	        if(isAlive()) {           
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
	        List<Location> adjacent = field.adjacentLocations(getLocation());
	        Iterator<Location> it = adjacent.iterator();
	        while(it.hasNext()) {
	            Location where = it.next();
	            Object animal = field.getObjectAt(where);
	            if(animal instanceof Fox) {
	                Fox fox = (Fox) animal;
	                if(fox.isAlive()) { 
	                    fox.setDead();
		                setFoodLevel(getFoodLevel() + FOX_FOOD_VALUE);
		                if(getFoodLevel() > MAX_FOOD_VALUE)
		                {
		                	setFoodLevel(MAX_FOOD_VALUE);
		                }
	                    // Remove the dead fox from the field.
	                    return where;
	                }
	            }
	            if(animal instanceof Rabbit) {
	                Rabbit rabbit = (Rabbit) animal;
	                if(rabbit.isAlive()) { 
	                    rabbit.setDead();
	                    setFoodLevel(getFoodLevel() + RABBIT_FOOD_VALUE);
		                if(getFoodLevel() > MAX_FOOD_VALUE)
		                {
		                	setFoodLevel(MAX_FOOD_VALUE);
		                }
	                    // Remove the dead rabbit from the field.
	                    return where;
	                }
	            }
	            if(animal instanceof Hunter) {
	                Hunter hunter = (Hunter) animal;
	                if(hunter.isAlive()) { 
	                    hunter.setDead();
		                setFoodLevel(getFoodLevel() + HUNTER_FOOD_VALUE);
		                if(getFoodLevel() > MAX_FOOD_VALUE)
		                {
		                	setFoodLevel(MAX_FOOD_VALUE);
		                }
	                    // Remove the dead fox from the field.
	                    return where;
	                }
	            }
	            if(animal instanceof Junglecat) {
	                Junglecat junglecat = (Junglecat) animal;
	                if(junglecat.isAlive()) { 
	                    junglecat.setDead();
		                setFoodLevel(getFoodLevel() + JUNGLECAT_FOOD_VALUE);
		                if(getFoodLevel() > MAX_FOOD_VALUE)
		                {
		                	setFoodLevel(MAX_FOOD_VALUE);
		                }
	                    // Remove the dead fox from the field.
	                    return where;
	                }
	            }
	        }
	        return null;
	    }
	        
	    public int getMaxAge()
	    {
	    	return MAX_AGE;
	    }
	    
	    protected int getBreedingAge()
	    {
	    	return 0;
	    }
	    
	    protected double getBreedingProbability()
	    {
	    	return 0;
	    }
	    
	    protected int getMaxLitterSize()
	    {
	    	return 0;
	    }

	    public String getAnimal()
	    {
	    	return "Manbearpig";
	    }
}
