import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class HistoryView extends JFrame
{
    
    public HistoryView(ArrayList<Integer> list, String name)
    {
        Border border = LineBorder.createGrayLineBorder();
        setTitle(name+" Changelog");
        JPanel buttonPanel = new JPanel(new GridLayout(10, 10));   
        
        for(int i = 0;i<list.size();i++)
        {
        	if(list.get(i) >= 0)
        	{
        		JLabel label = new JLabel("+"+list.get(i));
        		label.setHorizontalTextPosition(JLabel.CENTER);
        		label.setVerticalTextPosition(JLabel.CENTER);
        		label.setBorder(border);
        		buttonPanel.add(label);
        	}
        	else
        	{
        		JLabel label = new JLabel(""+list.get(i));
        		label.setHorizontalTextPosition(JLabel.CENTER);
        		label.setVerticalTextPosition(JLabel.CENTER);
        		label.setBorder(border);
        		buttonPanel.add(label);
        	}
        }
        
        setLocation(800, 100);
        
        Container contents = getContentPane();
        contents.add(buttonPanel, BorderLayout.CENTER);       
        pack();
        setVisible(true);
        setResizable(false);
    }
    
    public void closeWindow()
    {
    	setVisible(false);
    }
    
//    public void update(int i)
//    {
//    	if(list.get(i) >= 0)
//    	{
//    		buttonPanel.add(new JLabel("+"+list.get(i)));
//    	}
//    	else
//    	{
//    		buttonPanel.add(new JLabel(""+list.get(i)));
//    	}
 //   }
}
