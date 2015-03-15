import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public abstract class Animal implements Actor
{

	// A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // age of the animal.
    private int age;
    // foodlevel of the animal.
    private int foodLevel;
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
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
    abstract public void act(List<Actor> newAnimals);

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
     * An animal can breed if it has reached the breeding age.
     */
    public boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }
    
    /**
     * Retouneer de voortplantingsleeftijd van dit dier.
     * @return de voortplantingsleeftijd van dit dier.
     */
    abstract protected int getBreedingAge();
    
    abstract protected int getMaxAge();
    
    abstract protected double getBreedingProbability();
    
    abstract protected int getMaxLitterSize();
    
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
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    public int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    
    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    public void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    public void setFoodLevel(int foodLevel)
    {
    	this.foodLevel = foodLevel;
    }
    
    public int getFoodLevel()
    {
    	return foodLevel;
    }
    
    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to add newly born foxes to.
     */
    public void giveBirth(List<Actor> newAnimals)
    {
        // New foxes are born into adjacent locations.
    	// Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        Animal young = null;
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            String choice = getAnimal();
            if(choice.equals("Rabbit")){
            		young = new Rabbit(false, field, loc);
            }
            if(choice.equals("Fox")){
            		young = new Fox(false,field,loc);
            }
            if(choice.equals("Manbearpig")){
            		young = new Manbearpig(false,field,loc);
            }
            if(choice.equals("Junglecat")){
        			young = new Junglecat(false,field,loc);
        }
            
			newAnimals.add(young);
        }
    }
    
   abstract protected String getAnimal(); 
}

