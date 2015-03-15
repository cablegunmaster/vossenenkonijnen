import java.util.Iterator;
import java.util.List;
import java.util.Random;

	/**
	 * A class representing shared characteristics of animals.
	 * 
	 * @author David J. Barnes and Michael Kolling
	 * @version 2008.03.30
	 */
public class Hunter implements Actor
{
	
	

		
	    // Whether the animal is alive or not.
	    private boolean alive;
	    // The animal's field.
	    private Field field;
	    // The animal's position in the field.
	    private Location location;
	    // age of the animal.
	    private int age;
	    // The age to which a fox can live.
	    private static final int MAX_AGE = 500;
	    
	    private Random random;
	    
	    /**
	     * Create a new animal at location in field.
	     * 
	     * @param field The field currently occupied.
	     * @param location The location within the field.
	     */
	    public Hunter(Field field, Location location)
	    {
	        alive = true;
	        this.field = field;
	        age = 0;
	        setLocation(location);
	    }
	    
	    /**
	     * Make this animal act - that is: make it do
	     * whatever it wants/needs to do.
	     * @param newAnimals A list to add newly born animals to.
	     */
	    public void act(List<Actor> newHunters)
	    {
	        incrementAge();
	        //incrementHunger();
	        if(isAlive()) {           
	            // Move towards a source of food if found.
	            Location location = getLocation();
	            Location newLocation = findTarget(location);
	            if(newLocation == null) { 
	                // No food found - try to move to a free location.
	                newLocation = getField().freeAdjacentLocation(location);
		            if(newLocation != null) {
		                setLocation(newLocation);
		            }
	            }
	        }
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
	     * It is removed from the field.
	     */
	    public void setDead()
	    {
	        alive = false;
	        if(location != null) {
	            field.clear(location);
	            location = null;
	            field = null;
	        }
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
	     * Return the animal's field.
	     * @return The animal's field.
	     */
	    public Field getField()
	    {
	        return field;
	    }
	    
	    /**
	     * Place the animal at the new location in the given field.
	     * @param newLocation The animal's new location.
	     */
	    public void setLocation(Location newLocation)
	    {
	        if(location != null) {
	            field.clear(location);
	        }
	        location = newLocation;
	        field.place(this, newLocation);
	    }
	    
	    public int getAge()
	    {
	    	return age;
	    }
	    
	    public void setAge(int age)
	    {
	    	this.age = age;
	    }
	    
	   
	  
	    /**
	     * Increase the age. This could result in the animal's death.
	     */
	    public void incrementAge()
	    {
	        setAge(getAge()+1);
	        if(getAge() > getMaxAge()) {
	            setDead();
	        }
	    }
	    
	    public int getMaxAge()
	    {
	    	return MAX_AGE;
	    }	   
	   
	    
	    /**
	     * Make this fox more hungry. This could result in the fox's death.
	     */
//	    public void incrementHunger()
//	    {
//	        foodLevel--;
//	        if(foodLevel <= 0) {
//	            setDead();
//	        }
//	    }
	    
	    /**
	     * Tell the fox to look for rabbits adjacent to its current location.
	     * Only the first live rabbit is eaten.
	     * @param location Where in the field it is located.
	     * @return Where food was found, or null if it wasn't.
	     */
	    private Location findTarget(Location location)
	    {
	        Field field = getField();
	        List<Location> adjacent = field.rangedLocations(getLocation());
	        Iterator<Location> it = adjacent.iterator();
	        while(it.hasNext()) {
	            Location where = it.next();
	            Object animal = field.getObjectAt(where);
	            if(animal instanceof Fox) {
	                Fox fox = (Fox) animal;
	                if(fox.isAlive()) { 
	                    fox.setDead();
	                    // Remove the dead fox from the field.
	                    return where;
	                }
	            }
	            if(animal instanceof Rabbit) {
	                Rabbit rabbit = (Rabbit) animal;
	                if(rabbit.isAlive()) { 
	                    rabbit.setDead();
	                    // Remove the dead rabbit from the field.
	                    return where;
	                }
	            }
	            if(animal instanceof Manbearpig) {
	                Manbearpig manbearpig = (Manbearpig) animal;
	                if(manbearpig.isAlive()) {
	                	random = new Random();
	                	int tempint = random.nextInt(7);
	                	if(tempint==0)
	                	{
	                		manbearpig.setDead();
	                	}
	                    // Remove the dead manbearpig from the field.
	                    return where;
	                }
	            }
	            if(animal instanceof Junglecat) {
	                Junglecat junglecat = (Junglecat) animal;
	                if(junglecat.isAlive()) {
	                	random = new Random();
	                	junglecat.setDead();
	                    // Remove the dead manbearpig from the field.
	                    return where;
	                }
	            }
	            
	        }
	        return null;
	    }
	    
}
