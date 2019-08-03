package stanton.AntColony;

import java.util.ArrayList;
import java.util.HashMap;

public class Colony {
	
	ColonyNode[][] colonyNodeArray;
	Simulation simulation;
	ColonyView colonyView;
	ColonyNodeView nodeView;
	ColonyNode colonyNode;
	
	
	HashMap<Integer,Ant> rollCall = new HashMap<>();
	
	void deadAnt(int id) {
		Ant ant = rollCall.get(id);
		rollCall.remove(id);
	}
	
	public Colony() {
		
	}
	
	public Colony(ColonyView colonyView, Simulation simulation) {
		System.out.println("The colony is born.");
		this.simulation = simulation;
		this.colonyNodeArray = new ColonyNode[27][27];
		this.colonyView = colonyView;
	}

	public ColonyView getColonyView() {
		return colonyView;
	}
	
	public void createColonyNode(ColonyNode newNode, int x, int y) {
		colonyNodeArray[x][y] = newNode;
	}

	public void updateNodes(int turn) {
		for (int row = 0; row < 27; row++) {
            for (int column = 0; column < 27; column++) {
                	colonyNodeArray[row][column].nextTurn(turn);
            }
        }		
	}
	
	public void initializeColony() {
 
        for (int i = 0; i < 27; i++) {
            for (int j = 0; j < 27; j++) {
            	 if (i == 13 && j == 13) {
            		 nodeView = new ColonyNodeView();
                 colonyNode = new ColonyNode(nodeView, i, j);
                 colonyView.addColonyNodeView(nodeView, i, j);
                 createColonyNode(colonyNode, i, j);
                 nodeView.setID(i + "," + j);
                 colonyNode.setFirstNode(this);
                 continue;
             }
                nodeView = new ColonyNodeView();
                colonyNode = new ColonyNode(nodeView, i, j);
                colonyNode.setColony(this);
               
                colonyView.addColonyNodeView(nodeView, i, j);
                createColonyNode(colonyNode, i, j);
                nodeView.setID(i + "," + j);

                if ((i == 12 && j == 12) 
            		|| (i == 12 && j == 13) 
            		|| (i == 12 && j == 14) 
            		|| (i == 13 && j == 12) 
            		|| (i == 13 && j == 13) 
            		|| (i == 13 && j == 14) 
            		|| (i == 14 && j == 12) 
            		|| (i == 14 && j == 13) 
            		|| (i == 14 && j == 14)) 
                {
                    nodeView.showNode();
                }
            }
        }
    }
	
	public ArrayList<ColonyNode> getNearbyNodes(ColonyNode node) {

        ArrayList<ColonyNode> nearbyNodes;
        nearbyNodes = new ArrayList<>();

        for(int x = node.getX() -1; x < node.getX()+2; x++) {
        		for(int y = node.getY() -1; y < node.getY()+2; y++) {
        			if((x == node.getX() && y == node.getY()) | ((x<0|y<0) | (x>26 | y>26))) {
        				continue;
        			}
        			
        			nearbyNodes.add(colonyNodeArray[x][y]);
           
            }
        }
        
        return nearbyNodes;
    }
		
}
