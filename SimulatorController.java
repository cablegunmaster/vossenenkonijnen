import java.awt.event.*;

import javax.swing.*;

public class SimulatorController {

	private SimulatorModel 	model;
	private SimulatorView 	view;
	
//    private boolean stopresumecheck = true;
	
	public SimulatorController(SimulatorModel model, SimulatorView view)
	{
	
		this.model = model;
		this.view = view;
	
		view.addStep1Listener(new Step1Listener());
		view.addStep100Listener(new Step100Listener());
		view.addResetListener(new ResetListener());
		view.addExitListener(new ExitListener());
		view.addAboutListener(new AboutListener());
		view.addStopresumeListener(new StopresumeListener());
		view.addSimulateListener(new SimulateListener()); 
		view.addRabbitHistoryviewListener(new RabbitHistoryviewListener());
		view.addFoxHistoryviewListener(new FoxHistoryviewListener());
		view.addCatHistoryviewListener(new CatHistoryviewListener());
		view.addHistoviewListener(new HistoviewListener());
		view.addCircleviewListener(new CircleviewListener());
		
	}

	public class Step1Listener implements ActionListener
	{    	
			public void actionPerformed(ActionEvent e){
				if(model.getThread() == null)
				{
					model.simulateOneStep();
				}
			}
	}
	
	public class Step100Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			model.simulate();
		}
	}
	
	public class ResetListener implements ActionListener
	{
    	public void actionPerformed(ActionEvent e) {
    		model.reset();
    	}
	}
	
	public class ExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			model.areYouSure();
		}
	}
	
	public class AboutListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			model.aboutUs();
    	}
	}
	
	public class StopresumeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			model.stopresume();
		}
	}
	
	public class SimulateListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			model.runSimulate();
		}
	}
	
	public class RabbitHistoryviewListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			model.showRabbitHistory();
		}
	}
	
	public class FoxHistoryviewListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			model.showFoxHistory();
		}
	}
	
	public class CatHistoryviewListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			model.showCatHistory();
		}
	}
	
	public class HistoviewListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			model.showHistogram();
		}
	}
	
	public class CircleviewListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			model.showCircleview();
		}
	}
}