import java.awt.*;

import javax.swing.*;

public class PieView extends JFrame {
	SimulatorModel model;
	FieldStats fieldstats;
	private PiePanel piepanel;
	
	public PieView(FieldStats stats) {
		fieldstats = stats;
		setTitle("Circlediagram");
		setSize(250, 285);
		setResizable(false);
		setLayout(null);
		setLocation(600, 100);
		setVisible(true);
		piepanel = new PiePanel(fieldstats);
		this.add(piepanel);	 
	}
	 
	public void painter()
	{
		 piepanel.repaint();
	}
	
    public void closeWindow()
    {
    	setVisible(false);
    }
	
	public class PiePanel extends JPanel
	{
		private Graphics g;
		private FieldStats fieldstats;
		
		public PiePanel(FieldStats stats)
		{
			fieldstats = stats;
			setSize(260,500);
			setVisible(true);
		}
		
	 public void paintComponent(Graphics g){
		 int width = 0;
		 int begin = 0;
		 int totaal = fieldstats.getTotalPopulation();
		 int[] populatie = new int[(fieldstats.getAllPopulation().length)];
		 String[] namen = new String[(fieldstats.getAllNames().length)];
		 namen = fieldstats.getAllNames();
   		 populatie = fieldstats.getAllPopulation();
		 
		 g.setColor(Color.WHITE);
		 g.fillRect(0, 0, 200, 200);
		 fieldstats.getAllPopulation();
		 for(int i = 0; i < populatie.length; i++)
		 {
			double converter = 1.0;
		 	double percentage2 = (((populatie[i]/converter) / totaal) * 100);
			int percentage = (int) percentage2;
			 width = (360 / 100)* percentage;
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
			 g.fillArc(10, 10, 180, 180, begin, width);
			 begin += width;
			 
		 }
	   }
	}
}