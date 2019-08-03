package stanton.AntColony;

import java.util.ArrayList;
import java.util.Random;

public class Soldier extends Ant {
	int ID;
	boolean isAlive;
	int lifeSpan;
	int lifeSpent;
	ColonyNode currentLocation;
	Random random;
	
	public Soldier(){
		
	}

	public Soldier(ColonyNode node){
		setCurrentLocation(node);
		currentLocation.travelingTo.add(this);
		lifeSpan = 3650;
		isAlive = true;
		ID = setId();
	}
	
	@Override
	void dead() {
		currentLocation.nodeView.hideSoldierIcon();
		isAlive = false;
		super.deadAnt(ID);
	}

	@Override
	void move() {
		ArrayList<ColonyNode> localNodes = currentLocation.getNearbyNodes();
		ColonyNode destination = null;
		
		//Attack Mode
		boolean attackMode = false;
        for(int i = 0; i < localNodes.size();i++) {
        		if(localNodes.get(i).enemys.size() > 0 & localNodes.get(i).isVisable) {
        			attackMode = true;
        			destination = localNodes.get(i);
        		}
        }
        
        //Scout Mode
		if(!attackMode) {
			random = new Random();
			for(int i = 0; i < localNodes.size();i++) {
				destination = localNodes.get(random.nextInt(localNodes.size()));
				if(destination.isVisable){
					break;
					}
				}	
		}
	
		currentLocation.travelingAway.add(this);
		setCurrentLocation(destination);
		currentLocation.travelingTo.add(this);
	
	}


	@Override
	int getId() {
		
		return ID;
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
