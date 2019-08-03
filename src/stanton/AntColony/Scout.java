package stanton.AntColony;

import java.util.ArrayList;
import java.util.Random;

public class Scout extends Ant {

	int ID;
	boolean isAlive;
	int lifeSpan;
	int lifeSpent;
	ColonyNode currentLocation;
	Random random;
	
	Scout(){
		
	}
	
	Scout(ColonyNode node){
		setCurrentLocation(node);
		currentLocation.travelingTo.add(this);
		lifeSpan = 3650;
		isAlive = true;
		ID = setId();
	}

	@Override
	void move() {
		ArrayList<ColonyNode> localNodes = currentLocation.getNearbyNodes();
		ColonyNode destination = null;
        
		random = new Random();
		destination = localNodes.get(random.nextInt(localNodes.size()));

		currentLocation.travelingAway.add(this);
		setCurrentLocation(destination);
		currentLocation.travelingTo.add(this);
	}

	@Override
	int getId() {
		return ID;
	}

	@Override
	void dead() {
		currentLocation.nodeView.hideScoutIcon();
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
	ColonyNode getCurrentLocation() {
		return currentLocation;
	}

	@Override
	void setCurrentLocation(ColonyNode node) {
		this.currentLocation = node;
		
	}

}
