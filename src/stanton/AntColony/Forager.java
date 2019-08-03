package stanton.AntColony;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Forager extends Ant {

	int ID;
	boolean isAlive;
	int lifeSpan;
	int lifeSpent;
	boolean isCarryingFood;
	ColonyNode currentLocation;
	Random random;
	Stack<ColonyNode> moveHistory = new Stack<>();
	
	public Forager() {
	
	}
	
	public Forager(ColonyNode node){
		ID = setId();
		setCurrentLocation(node);
		currentLocation.travelingTo.add(this);
		moveHistory.push(currentLocation);
		lifeSpan = 3650;
		isAlive = true;
		isCarryingFood = false;
	}


	@Override
	void dead() {
		if(isCarryingFood) {
			currentLocation.food++;
		}
		currentLocation.nodeView.hideForagerIcon();
		isAlive = false;
		super.deadAnt(ID);
	}

	@Override
	void move() {
		ColonyNode destination = null;
		ArrayList<ColonyNode> localNodes = currentLocation.getNearbyNodes();
		int highestScent = 0;
		if(isCarryingFood) {
			if(currentLocation.isEntrance==true) {
				currentLocation.food++;
				isCarryingFood = false;
				moveHistory.empty();
				moveHistory.push(currentLocation);
			}else {
				if(currentLocation.pheromone < 1000) {
					currentLocation.pheromone+=10;
				}
				destination = moveHistory.pop();
			}
		} else {
			if(!(currentLocation.isEntrance==true) & currentLocation.food > 0) {
					isCarryingFood = true;
					currentLocation.food--;
				
			}else {
				for(int i = 0; i < localNodes.size(); i++) {
					if(localNodes.get(i).pheromone > highestScent & localNodes.get(i).isVisable) {
						if(!moveHistory.isEmpty() | moveHistory.peek() != localNodes.get(i)) {
							highestScent = localNodes.get(i).pheromone;
							destination = localNodes.get(i);
						}
						
					}
				}
			}
		}
			if(highestScent == 0 | destination == null) {
				random = new Random();
				for(int i = 0; i < localNodes.size();i++) {
					destination = localNodes.get(random.nextInt(localNodes.size()));
					if(destination.isVisable){
						break;
						}
					}
			}
			moveHistory.push(destination);
			
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
