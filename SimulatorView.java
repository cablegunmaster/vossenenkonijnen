import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SimulatorView extends JFrame
{
	
    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    
    private JButton step1;
    private JButton step100;
    private JButton stopresume;
    private JButton simulate;
    private JMenuItem filesave;
    private JMenuItem filereset;
    private JMenuItem fileexit;
    private JMenuItem about;
    private JMenu historyview;
    private JMenuItem rabbitview;
    private JMenuItem catview;
    private JMenuItem foxview;
    private JMenuItem histoview;
    private JMenuItem circleview;
    // A statistics object computing and storing simulation information
    private FieldStats stats;
    private SimulatorModel.FieldView fieldview;
    private SimulatorModel model;

    
    /**
     * Create a view of the given width and height.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(SimulatorModel model)
    {
    	this.model = model;
        model.setStats(new FieldStats());
        stats = model.getStats();
        setTitle("Fox and Rabbit Simulation");
        
        model.setStepLabel(new JLabel(STEP_PREFIX, JLabel.CENTER));
        model.setPopulation(new JLabel(POPULATION_PREFIX, JLabel.CENTER));
        
        JPanel buttonPanel = new JPanel(new GridLayout(8, 1));
        JPanel viewPanel = new JPanel(new GridLayout(1, 1));
        step1 = new JButton("Step 1");
        step100 = new JButton("Step 100");
        simulate = new JButton("Simulate");
        stopresume = new JButton("Stop / Resume");
              
        
        buttonPanel.add(new JLabel(""));
        buttonPanel.add(step1);
        buttonPanel.add(step100);
        buttonPanel.add(stopresume);
        buttonPanel.add(simulate);
        
        setLocation(100, 50);
        
        makeMenuBar();
        
        fieldview = model.getFieldView();
        
        Container contents = getContentPane();
        contents.add(model.getStepLabel(), BorderLayout.NORTH);
        contents.add(fieldview, BorderLayout.CENTER);
        contents.add(model.getPopulationLabel(), BorderLayout.SOUTH);
        contents.add(buttonPanel, BorderLayout.WEST);
        contents.add(viewPanel, BorderLayout.EAST);        
        pack();
        contents.setVisible(true);
        setResizable(false);
        model.reset();
        
        
    }
        
    public void makeMenuBar()
    {
    	JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
            
        JMenu file = new JMenu("File");
        menubar.add(file);
        
        filereset = new JMenuItem("reset");
       
        file.add(filereset);
        
        fileexit = new JMenuItem("exit");
        
        file.add(fileexit);
        
        JMenu views = new JMenu("Views");
        menubar.add(views);
        
        historyview = new JMenu("History views");
        
        views.add(historyview);
        
        rabbitview = new JMenuItem("Rabbit"); 
        historyview.add(rabbitview);
        
        foxview = new JMenuItem("Fox"); 
        historyview.add(foxview);
        
        catview = new JMenuItem("Junglecat"); 
        historyview.add(catview);
        
        histoview = new JMenuItem("Histogram"); 
        views.add(histoview);
        
        circleview = new JMenuItem("Circlediagram"); 
        views.add(circleview);
        
        JMenu help = new JMenu("Help");
        menubar.add(help);
        
        about = new JMenuItem("About the Architects");
        
        help.add(about);

    } 
    
    // Adds an ActionListener to step1.
    public void addStep1Listener(ActionListener s1)
    {
    	step1.addActionListener(s1);
    }
 	// Adds an ActionListener to step100.
    public void addStep100Listener(ActionListener s100)
    {
    	step100.addActionListener(s100);
    }
    // Adds an ActionListener to stopresume.
    public void addStopresumeListener(ActionListener sr)
    {
    	stopresume.addActionListener(sr);
    }
    // Adds an ActionListener to reset.
    public void addResetListener(ActionListener r)
    {
    	filereset.addActionListener(r);
    }
    // Adds an ActionListener to exit.
    public void addExitListener(ActionListener e)
    {
    	fileexit.addActionListener(e);
    }
    // Adds an ActionListener to about.
    public void addAboutListener(ActionListener a)
    {
    	about.addActionListener(a);
    }
    // Adds an ActionListener to simulate.
    public void addSimulateListener(ActionListener si)
    {
    	simulate.addActionListener(si);
    }   
    // Adds an ActionListener to historyview.
    public void addRabbitHistoryviewListener(ActionListener si)
    {
    	rabbitview.addActionListener(si); 	
    }   
    public void addFoxHistoryviewListener(ActionListener si)
    {
    	foxview.addActionListener(si); 	
    }   
    public void addCatHistoryviewListener(ActionListener si)
    {
    	catview.addActionListener(si); 	
    }   
    public void addHistoviewListener(ActionListener si)
    {
    	histoview.addActionListener(si); 	
    }   
    public void addCircleviewListener(ActionListener si)
    {
    	circleview.addActionListener(si); 	
    }
}