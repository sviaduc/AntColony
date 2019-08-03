package stanton.AntColony;

public class AntSimLauncher {

	public static void main(String[] args) {
		
		AntSimGUI antSimGUI = new AntSimGUI();
		antSimGUI.addSimulationEventListener(new Simulation(antSimGUI));

	}

}
