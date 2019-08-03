package stanton.AntColony;

public abstract class Ant extends Colony {

	static int ID = 0;
	
	
	abstract void dead();
	public void takeTurn() {};
	abstract void move();
	abstract int getId();
	abstract ColonyNode getCurrentLocation();
	abstract void setCurrentLocation(ColonyNode node);
	
	int setId() {
		return ID++;
	}

}

