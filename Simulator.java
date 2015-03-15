import javax.swing.*;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    
	//public static SimulatorView simulatorview;
    
    public static void main(String[] args)
    {
    	int j = 0;
    	int h = 0;
    	String i = JOptionPane.showInputDialog(null, "Value between 1 and 250", "Insert width", 3);
    	try
    	{
    		j = NotANumberException.translate(i);
    	} catch(NotANumberException e){
    		j = 50;
    		JOptionPane.showMessageDialog(null, "Invalid input, width will be set to 50 by default.", "Invalid input", 2);
    	}
    	String k = JOptionPane.showInputDialog(null, "Value between 1 and 250", "Insert hight", 3);
    	try
    	{
    		h = NotANumberException.translate(k);
    	} catch(NotANumberException e){
    		h = 50;
    		JOptionPane.showMessageDialog(null, "Invalid input, height will be set to 50 by default.", "Invalid input", 2);
    	}
    	
    	SimulatorModel 			model  		= new SimulatorModel(h, j);
    	SimulatorView 			view   		= new SimulatorView(model);
    	SimulatorController		controller	= new SimulatorController(model,view);
    	
    	view.setVisible(true);
    }
}