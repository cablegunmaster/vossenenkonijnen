import static org.junit.Assert.*;

import org.junit.Test;


public class SimulatorTest {
	
	@Test
	public void testSimulator() {
		Simulator simulator = new Simulator();
		assertNotNull("Simulator klasse is niet gevuld",simulator); 
	}

	@Test
	public void testSimulatorIntInt() {
		SimulatorModel simulatormodel = new SimulatorModel(10,10);
		assertNotNull("Simulator klasse is niet gevuld",simulatormodel); 		
	}

	@Test
	/** 
	 * maak een veld aan met een beperkt aantal konijnen.
	 * Test of ze op de goeie manier voortplanten. ??????
	 *  eerst checken hoeveel konijnen , voer step 1 uit. en test hoeveel konijnen.
	 */
	public void testMain() {
		SimulatorModel tester = new SimulatorModel(10,10);
		//SimulatorView temp = tester.getSimulatorView();
		FieldStats temp2 = tester.getStats();
		Counter tempcounter = temp2.getRabbitCounter();
		int tempcount = tempcounter.getCount();
		tester.simulate();
		Counter tempcounter2 = temp2.getRabbitCounter();
		int tempcount2 = tempcounter2.getCount();
		assertTrue("Er zijn minder konijnen dan in het begin", tempcount2 > tempcount);
	}

	@Test
	public void testRunLongSimulation() {

	}

	@Test
	public void testSimulate() {

	}

	@Test
	public void testSimulateOneStep() {
		//Simulator tester = new Simulator(10,10);
		//tester.getField();
		//tester.getSimulatorView();
		//simulator.simulateOneStep();
		//assertFalse("het aantal konijnen is niet gelijk", )
	}

	@Test
	public void testReset() {

	}

}
