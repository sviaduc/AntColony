package stanton.AntColony;

import java.util.Random;

public class Queen extends Ant{
	int ID;
	boolean isAlive;
	int lifeSpan;
	int lifeSpent;
	ColonyNode currentLocation;
	
	public Queen(){

	}
	
	public Queen(ColonyNode node) {
		System.out.println("Long live the Queen");
		this.currentLocation = node;
		lifeSpan = 73000;
		isAlive = true;
		ID = setId();
		System.out.println("queen id is: " + ID);
	}

	@Override
	public void takeTurn() {
		lifeSpent++;
		if(lifeSpent > lifeSpan) {
			dead();
		}
		eat();
		Ant bala = hatchBala();
		if(bala != null) {
			super.rollCall.put(bala.ID, bala);
		}
		if(lifeSpent % 10 == 0) {
			Ant newAnt = hatchAnt();
			super.rollCall.put(newAnt.ID, newAnt);

					
		}	
	}
	
	// eat 1 unit of food every turn or die of starvation
	private void eat() {
		if(currentLocation.food <= 0) {
			System.out.println("Queen starved to death");
			dead();
		}
		currentLocation.food--;		
	}

	@Override
	void move() {
		// Remains in the same square for entire simulation	
	}

	@Override
	int getId() {
		return ID;
	}
	
	Ant hatchAnt() {
		Random random = new Random();
		int careerRoulette = random.nextInt(100);
		careerRoulette += 1;
		if(careerRoulette > 50) {
			Forager forager = new Forager(currentLocation);
			return forager;
		}
		else if(careerRoulette > 25) {
			Scout scout = new Scout(currentLocation);
			return scout;
		} 
		else {
			Soldier soldier = new Soldier(currentLocation);
			return soldier;
		}
	}

	Ant hatchBala() {
		Random random = new Random();
		int noWammies = random.nextInt(100);

		if(noWammies < 3) {
			Bala bala = new Bala(currentLocation.colony.colonyNodeArray[0][0]);
			return bala;
		}
		return null;
	}
	
	@Override
	void dead() {
		currentLocation.nodeView.hideQueenIcon();
		System.out.println("The Queen is dead");
		System.out.println("Ants in play: " + rollCall.size());
		super.deadAnt(ID);
		Simulation.endSimulation();;
	}

	@Override
	ColonyNode getCurrentLocation() {
		return currentLocation;
	}

	@Override
	void setCurrentLocation(ColonyNode node) {
		// Queen never moves	
	}
	
}

