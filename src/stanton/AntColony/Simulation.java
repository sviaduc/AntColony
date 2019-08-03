package stanton.AntColony;

public class Simulation implements SimulationEventListener{
	
	public int turn =0;
	public AntSimGUI gui;
	public Colony colony;
	private boolean step;
	private Thread t;
	
	Simulation(AntSimGUI antSimGUI) {
		System.out.println("The simulation has begun.");
		this.gui = antSimGUI;
	    colony = new Colony(new ColonyView(27, 27), this);
	    //t = null;
	    step = true;
	    
	    antSimGUI.initGUI(colony.getColonyView());
	  
	}
	
	public void simulationEventOccurred(SimulationEvent simEvent) {

        if (simEvent.getEventType() == SimulationEvent.NORMAL_SETUP_EVENT) {
            colony.initializeColony();
        }
        else if (simEvent.getEventType() == SimulationEvent.RUN_EVENT) {
            step = false;
            
            t = new Thread() {
                public void run() {
                    nextTurn();
                }
            };
            t.start();
        }
        else if (simEvent.getEventType() == SimulationEvent.QUEEN_TEST_EVENT) {
        	   colony.colonyNodeArray[13][13].nodeView.showQueenIcon();
    
        }
        
        else if (simEvent.getEventType() == SimulationEvent.STEP_EVENT) {
            step = true;
            nextTurn();
        }
    }
	
	public void nextTurn() {
        do {
            setTurn();
            
            gui.setTime("Turn: " + String.valueOf(turn) 
            + ", Day: " + String.valueOf((turn / 10) + 1));
            
            try {
                Thread.sleep(200);
            } 
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        } while (!step);
    }
		
	public void setTurn() {
		turn++;
		colony.updateNodes(turn);	
	}
	
	public int getTurn() {
		return turn;
	}
	
	public static void endSimulation() {
		System.out.println("End of Simulation");
		System.exit(0);
	}

}
