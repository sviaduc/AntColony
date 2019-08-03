package stanton.AntColony;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColonyNode{
	
	int x, y;
	boolean isVisable;
	int food;
	int pheromone;
	ColonyNodeView nodeView;
	boolean isEntrance;
	Colony colony;
	Random random;
	

	List<Ant> friendlys;
	List<Ant> enemys;
	List<Ant> travelingTo;
	List<Ant> travelingAway;

	public ColonyNode(ColonyNodeView colonyNodeView, int x, int y) {
		nodeView = colonyNodeView;
		this.x = x;
		this.y = y;
		
		friendlys = new ArrayList<>();
		enemys = new ArrayList<>();
		travelingTo = new ArrayList<>();
		travelingAway = new ArrayList<>();
		
		pheromone = 0;
		
		isVisable = false;
		isEntrance = false;
		
		random = new Random();
		int superMarketSweep = random.nextInt(100);
		if(superMarketSweep <= 25) {
			random = new Random();
			superMarketSweep = random.nextInt(6) + 5;
			food = superMarketSweep * 100;
		} else {
			food = 0;
		}
	}
	
	public void scoutsInTheHouse() {
		for(Ant ant: friendlys) {
			if(ant instanceof Scout) {
				nodeView.showNode();
				isVisable = true;
			}
		}	
	}

	public void nextTurn(int curTurn) {

		if(curTurn % 10 == 0 & curTurn != 0) {
			if(pheromone >= 2) {
				pheromone = (pheromone/2);
			} else {
				pheromone = 0;
			}
			
		}
		
		for(Ant ant: friendlys) {
			ant.takeTurn();
		}
		
		for(Ant ant: enemys) {
			ant.takeTurn();
		}
		
		for(Ant ant: travelingTo) {
			if(ant.getClass() == Bala.class) {
				enemys.add(ant);
			} else {
				friendlys.add(ant);
			}
		}
		for(Ant ant: travelingAway) {
			if(ant.getClass() == Bala.class) {
				enemys.remove(ant);
			} else {
				friendlys.remove(ant);
			}
		}
		travelingTo.clear();
		travelingAway.clear();
		
		if((!friendlys.isEmpty()) & (!enemys.isEmpty())) {
			Ant homeTownHero;
			Ant badBoyBala = null;
			for(Ant bala: enemys) {
				badBoyBala = bala;
			}
			homeTownHero = tonightsMatchup();
			letsGetReadyToRumble(badBoyBala, homeTownHero);
		}
		updateNodeView(nodeView);
	}

	private Ant tonightsMatchup() {
		for(Ant ant: friendlys) {
			if(ant instanceof Soldier) {
				return ant;
			}
		}
		for(Ant ant: friendlys) {
			if(ant instanceof Forager | ant instanceof Scout) {
				return ant;
			}	
		}
		//Queen fights
		for(Ant ant: friendlys) {
			System.out.println("Queen is FIGHTING!");
			return ant;
		}
		return null;		
	}

	private void letsGetReadyToRumble(Ant badBoyBala, Ant homeTownHero) {
		Random random = new Random();
		int russianRoulette = random.nextInt(100);
		if(russianRoulette < 50) {
			badBoyBala.dead();
		} else {
			homeTownHero.dead();
		}
	}

    public ArrayList<ColonyNode> getNearbyNodes() {
        return colony.getNearbyNodes(this);
    }

   
	public void setFirstNode(Colony colony) {
		System.out.println("First node alive");
		this.colony = colony;
		this.isVisable = true;
		this.isEntrance = true;
		this.food = 1000;
		
		Queen queen = new Queen(this);
		colony.rollCall.put(queen.ID, queen);
		friendlys.add(queen);
		
		for(int i = 0; i < 50; i++) {
			Forager forager = new Forager(this);
			colony.rollCall.put(forager.ID, forager);
		
			if(i < 10) {
				Soldier soldier = new Soldier(this);
				colony.rollCall.put(soldier.ID, soldier);
				
			}
			if(i < 4) {
				Scout scout = new Scout(this);
				colony.rollCall.put(scout.ID, scout);
				
			}
		}
		
		nodeView.showNode();

	}
	
	public void updateNodeView(ColonyNodeView nodeView) {
		int sc,f,s,b;
		sc=f=s=b=0;
		scoutsInTheHouse();
		for(Ant ant: friendlys) {
			if(ant instanceof Queen) {
				nodeView.setQueen(isEntrance);
				nodeView.showQueenIcon();
				continue;
			}
			if(ant instanceof Forager) {
				f++;
				nodeView.setForagerCount(f);
				nodeView.showForagerIcon();
				continue;
			}
			if(ant instanceof Scout) {
				sc++;
				nodeView.setScoutCount(sc);
				nodeView.showScoutIcon();
				continue;
			}
			if(ant instanceof Soldier) {
				s++;
				nodeView.setSoldierCount(s);
				nodeView.showSoldierIcon();
			}
		}
		for(Ant ant: enemys) {
			if(ant instanceof Bala) {
				b++;
				nodeView.setBalaCount(b);
				nodeView.showBalaIcon();
			}
		}
		if(f == 0) {
			nodeView.setForagerCount(f);
			nodeView.hideForagerIcon();
		}
		if(sc == 0) {
			nodeView.setScoutCount(sc);
			nodeView.hideScoutIcon();
		}
		if(s == 0) {
			nodeView.setSoldierCount(s);
			nodeView.hideSoldierIcon();
		}
		if(b == 0) {
			nodeView.setBalaCount(b);
			nodeView.hideBalaIcon();
		}
		nodeView.setFoodAmount(food);
		nodeView.setPheromoneLevel(pheromone);
		
	}	
	
	public void setColony(Colony colony) {
		this.colony = colony;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getFood() {
        return food;
    }

    public void setFood(int foodCount) {
        food = foodCount;
    }

    public int getPheromone() {
        return pheromone;
    }
    
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ColonyNode [x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", friendlys=");
		builder.append(friendlys);
		builder.append(", enemys=");
		builder.append(enemys);
		builder.append("]");
		return builder.toString();
	}
	
}
