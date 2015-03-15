import javax.swing.*;

import java.awt.*;


public class HistoView extends JFrame
{
    private HistoPanel histopanel;
    private FieldStats stats;
    
    public HistoView(FieldStats stats)
    {
        this.stats=stats;
		setTitle("Histogram");
		setSize(260, 500);
		setResizable(false);
		setLayout(null);
		setLocation(600, 100);
		setVisible(true);
        histopanel = new HistoPanel(stats);
        this.add(histopanel);
    }
    
    public void painter()
    {
    	histopanel.repaint();
    }

    public class HistoPanel extends JPanel
    {
        private Graphics g;
        private FieldStats stats;
        
    	public HistoPanel(FieldStats stats) {
    		this.stats = stats;
    		setSize(260, 500);
    		setVisible(true);
    	}
        
        public void paintComponent(Graphics g) {

        	int xloc = 0;
        	int totaal = stats.getTotalPopulation();
        	int[] populatie = new int[(stats.getAllPopulation().length)];
   		 	String[] namen = new String[(stats.getAllNames().length)];
   		 	namen = stats.getAllNames();
   		 	populatie = stats.getAllPopulation();
   		 
   		 	g.setColor(Color.WHITE);
   		 	g.fillRect(0, 0, 260, 500);
        	for(int i = 0; i < populatie.length; i++)
   		 	{
        		double converter = 1.0;
   			 	double percentage2 = (((populatie[i]/converter) / totaal) * 100);
   			 	int percentage = (int) percentage2;
   			 	if(namen[i].equals("Rabbit"))
   			 	{
   			 		g.setColor(Color.ORANGE);
   			 	}
   			 	if(namen[i].equals("Junglecat"))
   			 	{
   			 		g.setColor(Color.GREEN); 
   			 	}
   			 	if(namen[i].equals("Fox"))
   			 	{
   			 		g.setColor(Color.BLUE); 
   			 	}
   			 	if(namen[i].equals("Manbearpig"))
   			 	{
   			 		g.setColor(Color.RED);
   			 	}
   			 	if(namen[i].equals("Hunter"))
   			 	{
   			 		g.setColor(Color.BLACK); 
   			 	}
       
   			 	g.fillRect(10 + xloc * 50, 500, 40, -(percentage * 5));
   			 	xloc++;
   		 	}
        }	
    }
 
    public void closeWindow()
    {
    	setVisible(false);
    }

}
