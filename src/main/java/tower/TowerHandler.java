package tower;

import java.util.ArrayList;

import monster.Fox;
import monster.Monster;
import monster.Penguin;
import monster.Unicorn;

public class TowerHandler {
	protected ArrayList<Tower> towerArray = new ArrayList<Tower>();
	protected int num = 0;
	protected boolean[][] towerGrid =  new boolean[480][480];
	protected int catapultCount = 0;
	
	TowerHandler() {
		for (int x = 0; x < 12; x++) for (int y = 0; y < 12; y++) towerGrid[x][y] = false;
	}
	
	public boolean build(int type, int x, int y) {
		if (x == 0 && y == 11 || x == 11 && y == 0) return false; // cannot build on start & end grid
		
		
		
		switch(type) {
			case 1 : 
				towerArray.add(new BasicTower(type, num++, x, y));
				break;
			case 2 :
				towerArray.add(new IceTower(type, num++, x, y));
				break;
			case 3 :
				towerArray.add(new Catapult(type, num++, x, y));
				catapultCount++;
				break;
			case 4 :
				towerArray.add(new LaserTower(type, num++, x, y));
				break;
		}
		towerGrid[x][y] = true;
		return true;
	}
	
	public boolean towerFound(int x, int y) {
		return towerGrid[x][y];
	}
	
	public boolean catapultFound() {
		if (catapultCount == 0) return false;
		return true;
	}
	
	public void destroy(int ID, int x, int y) {
		if (towerArray.get(ID).getTowerType() == 3) catapultCount--;
		towerArray.set(ID, towerArray.get(num - 1));
		towerArray.remove(--num);
		towerGrid[x][y] = false;
	}
}