import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.*;

public class SimulatorModel implements Runnable {
	
	// The default width for the grid.
    private static final int DEFAULT_WIDTH = 50;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 50;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;
    // The probability that a manbearpig will be created in any given grid position.
    private static final double MANBEARPIG_CREATION_PROBABILITY = 0.01;
    // The probability that a junglecat will be created in any given grid position.
    private static final double JUNGLECAT_CREATION_PROBABILITY = 0.008;
    // The probability that a HUNTER will be created in any given grid position.
    private static final double HUNTER_CREATION_PROBABILITY = 0.005;
    // A boolean to see if there is a manbearpig on the field.
    private static boolean manbearpigexists = false;
    // A variable to see how much hunters exists on the field.
    private static int hunterexists = 0;
    
	private Thread thread = null;
	private boolean run = false;
	private boolean initialrun = false;
	private boolean endlessSimulate = false;
	private int counter = 0;

	
    private FieldView fieldview;
    private HistoryView rabbithistoryview;
    private HistoryView foxhistoryview;
    private HistoryView cathistoryview;
	private boolean rabbithistorycheck = false;
	private boolean foxhistorycheck = false;
	private boolean cathistorycheck = false;
    private ArrayList<Integer> rabbithistorylist;
    private ArrayList<Integer> foxhistorylist;
    private ArrayList<Integer> junglecathistorylist;
    
    private HistoView histogramview;
    private boolean histogramcheck = false;
    
    private PieView circleview;
    private boolean circlecheck = false;
    
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;
    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;
    
    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
	
	
    // List of animals in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    private FieldStats stats; 
    // The current step of the simulation.
    private int step;
    // A map for storing colors for participants in the simulation
    private Map<Class, Color> colors;
    
    
    /**
     * Construct a simulation field with default size.
     */
    
    public SimulatorModel()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public SimulatorModel(int depth, int width)
    {
        if(width <= 0 || depth <= 0 || width > 250 || depth > 250) {
        	JOptionPane.showMessageDialog(null, "Invalid input, size will be set to 50x50 by default.", "Invalid input", 2);
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        rabbithistorylist = new ArrayList<Integer>();
        junglecathistorylist = new ArrayList<Integer>();
        foxhistorylist = new ArrayList<Integer>();
        fieldview = new FieldView(depth,width);
        actors = new ArrayList<Actor>();
        field = new Field(depth, width);
        setMapColor(new LinkedHashMap<Class, Color>());


        // Create a view of the state of each location in the field.
        
        setColor(Rabbit.class, Color.orange);
        setColor(Fox.class, Color.blue);
        setColor(Manbearpig.class, Color.red);
        setColor(Hunter.class, Color.black);
        setColor(Junglecat.class, Color.green);

    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * e.g. 500 steps.
     */
    public void runSimulate()
    {
    	endlessSimulate = true;
    	start();
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */

    
    public void simulate()
    {
    	endlessSimulate = false;
    	start();
    }

	public void start() {
		if(thread == null)
		{
		thread = new Thread(this);
		thread.start();
		}
	}
	
	public void run()
	{
    	run = true;
		while(run){
			simulateOneStep();
			try {
			Thread.sleep(100);
		} catch (Exception e) {}
		counter++;
        if((counter==100 && !endlessSimulate) || !isViable(field))
        {
        	counter = 0;
        	run = false;
        	thread = null;
        	if(rabbithistorycheck)
        	{
        		showRabbitHistory();
        		showRabbitHistory();
        	}
        	if(cathistorycheck)
        	{
        		showCatHistory();
        		showCatHistory();
        	}
        	if(foxhistorycheck)
        	{
        		showFoxHistory();
        		showFoxHistory();
        	}
        	
        }
	}
	}
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
    	FieldStats tempstats = getStats();
    	Counter info = tempstats.getRabbitCounter();
    	int rabbitint = info.getCount();
    	info = tempstats.getJunglecatCounter();
    	int catint = info.getCount();
    	info = tempstats.getFoxCounter();
    	int foxint = info.getCount();
    	if(!isViable(field))
        {
        	run = false;
        	thread = null;
        }else{
    	step++;
        // Provide space for newborn animals.
        List<Actor> newActors = new ArrayList<Actor>();        
        // Let all rabbits act.
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            actor.act(newActors);
            if(! actor.isAlive()) {
                it.remove();
            }
        }            
        // Add the newly born foxes and rabbits to the main lists.
        actors.addAll(newActors);

        showStatus(step, field);
        
        if(histogramview != null)
        {
        	histogramview.painter();
        }
        
        if(circleview != null)
        {
        	circleview.painter();
        }
        
    	info = tempstats.getRabbitCounter();
    	int rabbitint2 = info.getCount();
    	info = tempstats.getJunglecatCounter();
    	int catint2 = info.getCount();
    	info = tempstats.getFoxCounter();
    	int foxint2 = info.getCount();
    	if(rabbithistorylist.size() == 100)
    		{
    			rabbithistorylist.remove(0);
    			rabbithistorylist.add(rabbitint2-rabbitint);
    			foxhistorylist.remove(0);
    			foxhistorylist.add(foxint2-foxint);
    			junglecathistorylist.remove(0);
    			junglecathistorylist.add(catint2-catint);
    		}
    		else
    		{
    			rabbithistorylist.add(rabbitint2-rabbitint);
    			junglecathistorylist.add(catint2-catint);
    			foxhistorylist.add(foxint2-foxint);
    		}
        }
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        manbearpigexists = false;
        hunterexists = 0;
        actors.clear();
        populate();
		thread = null;
		run = false;
		counter = 0;
		initialrun = false;
        //view.resetStopResume();
        
        // Show the starting state in the view.
        showStatus(step, field);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    actors.add(fox);
                }
                else if(rand.nextDouble() <= JUNGLECAT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Junglecat junglecat = new Junglecat(true, field, location);
                    actors.add(junglecat);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    actors.add(rabbit);
                }
                else if(rand.nextDouble() <= MANBEARPIG_CREATION_PROBABILITY && manbearpigexists == false) {
                    Location location = new Location(row, col);
                    Manbearpig manbearpig = new Manbearpig(false, field, location);
                    actors.add(manbearpig);
                    manbearpigexists = true;
                }
                else if(rand.nextDouble() <= HUNTER_CREATION_PROBABILITY && hunterexists < 5) {
                    Location location = new Location(row, col);
                    Hunter hunter = new Hunter(field, location);
                    actors.add(hunter);
                    hunterexists++;
                }
                // else leave the location empty.
            }
        }
    }
    /**
     *  Haal het veld op zodat je kan zien wat erop staat.
     *  @return Field
     */
    public Field getField()
    {
    	return field;
    }
    
    public FieldView getFieldView()
    {
    	return fieldview;
    }
    
    /**
     * Define a color to be used for a given class of animal.
     * @param animalClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    public void setColor(Class animalClass, Color color)
    {
        colors.put(animalClass, color);
    }
    
    public void setMapColor(Map<Class, Color> map)
    {
    	colors = map;
    }
    public Map getMapColor()
    {
    	return colors;
    }
    
    public void setStats(FieldStats stats)
    {
    	this.stats = stats;
    }
    public FieldStats getStats() {
		return stats;
	}
    
    
    
    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    } 
    
    /**
     * Provide a graphical view of a rectangular field. This is 
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the field.
     * This is rather advanced GUI stuff - you can ignore this 
     * for your project if you like.
     */
    public class FieldView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldview.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }

    /**
     * Show the current status of the field.
     * @param step Which iteration step it is.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(int step, Field field)
    {
        //if(!isVisible()) {
        //   setVisible(true);
        //}
        getStepLabel().setText(STEP_PREFIX + step);
        stats.reset();
        
        fieldview.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldview.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    fieldview.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldview.repaint();
    }
    
    public void repainthud()
	{
		fieldview.repaint();
	}
    
    /**
     * @return The color to be used for a given class of animal.
     */
    private Color getColor(Class animalClass)
    {
        Color col = (Color) getMapColor().get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }
    public JLabel getStepLabel()
    {
    	return stepLabel;
    }
    public void setStepLabel(JLabel steplabel)
    {
    	stepLabel = steplabel;
    }
    public JLabel getPopulationLabel()
    {
    	return population;
    }
    public void setPopulation(JLabel population)
    {
    	this.population = population;
    }
    
    public void stopresume()
    {
    	if (run) {
    		thread = null;
    		run = false;
    		initialrun = true;
    	} else if(initialrun) {
    		start();
    		initialrun = false;
    	}
    }
    
    public void areYouSure()
    {
    	int button = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm exit", JOptionPane.YES_NO_OPTION);
		if(button != JOptionPane.YES_OPTION)
		{
			return;
		}
		System.exit(0);
    }
    
    public Thread getThread()
    {
    	return thread;
    }
    
    public void showRabbitHistory()
    {
    	if(rabbithistorycheck)
    	{
    		rabbithistorycheck = false;
    		rabbithistoryview.dispose();
    	}
    	else {
    		rabbithistorycheck = true;
    		rabbithistoryview = new HistoryView(rabbithistorylist, "Rabbit");
    	}
    }
    
    public void showFoxHistory()
    {
    	if(foxhistorycheck)
    	{
    		foxhistorycheck = false;
    		foxhistoryview.dispose();
    	}
    	else {
    		foxhistorycheck = true;
    		foxhistoryview = new HistoryView(foxhistorylist, "Fox");
    	}
    }
    
    public void showCatHistory()
    {
    	if(cathistorycheck)
    	{
    		cathistorycheck = false;
    		cathistoryview.dispose();
    	}
    	else {
    		cathistorycheck = true;
    		cathistoryview = new HistoryView(junglecathistorylist, "Junglecat");
    	}
    }
    
    public void aboutUs()
    {
    	JOptionPane.showMessageDialog(null, "<<< MADE BY >>>\n\nLeon van der Velde\nKevin de Boer\nPaul Vesseur\nJasper Lankhorst", "THE REAL GODS", 1);
    }
    
    public void showHistogram()
    {
    	if(histogramcheck)
    	{
    		histogramcheck = false;
    		histogramview.dispose();
    	}
    	else {
    		histogramcheck = true;
    		histogramview = new HistoView(stats);
    		histogramview.painter();
    	}
    }
    
    public void showCircleview()
    {
    	if(circlecheck)
    	{
    		circlecheck = false;
    		circleview.dispose();
    	}
    	else {
    		circlecheck = true;
    		circleview = new PieView(stats);
    		circleview.painter();
    	}
    }
}