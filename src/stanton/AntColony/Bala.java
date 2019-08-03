package stanton.AntColony;

import java.util.ArrayList;
import java.util.Random;

public class Bala extends Ant {
	
	int ID;
	boolean isAlive;
	int lifeSpan;
	int lifeSpent;
	ColonyNode currentLocation;
	Random random;

	public Bala() {
		
	}
	
	public Bala(ColonyNode node) {
		setCurrentLocation(node);
		currentLocation.travelingTo.add(this);
		lifeSpan = 3650;
		isAlive = true;
		ID = setId();
	}
	@Override
	void dead() {
		currentLocation.nodeView.hideBalaIcon();
		isAlive = false;
		super.deadAnt(ID);
		
	}

	@Override
	public void takeTurn() {
		lifeSpent++;
		if(lifeSpent > lifeSpan) {
			dead();
		}
		move();
		
	}

	@Override
	void move() {
		ArrayList<ColonyNode> localNodes = currentLocation.getNearbyNodes();
		ColonyNode destination = null;
        
        // Select a random destination from the adjacent tiles, then move
		try {
			random = new Random();
			destination = localNodes.get(random.nextInt(localNodes.size()));
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
        
        
		//random move to 1 of 8 directions
		//capture location in var newNode
		currentLocation.travelingAway.add(this);
		//currentLocation = newNode
		setCurrentLocation(destination);
		currentLocation.travelingTo.add(this);
	}

	@Override
	int getId() {
		return ID;
	}

	@Override
	ColonyNode getCurrentLocation() {
		return currentLocation;
	}

	@Override
	void setCurrentLocation(ColonyNode node) {
		this.currentLocation = node;	
	}

}
