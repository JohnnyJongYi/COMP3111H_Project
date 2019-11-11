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
	
	public boolean build(int x, int y, int type) {
		if (false) return false; // check if can build
		
		switch(type)
		{
			case 1 : 
				towerArray.add(new BasicTower(type, num++, x, y));
				break;
			case 2 :
				towerArray.add(new IceTower(type, num++, x, y));
				break;
			case 3 : 
				towerArray.add(new Catapult(type, num++, x, y));
				break;
			case 4 :
				towerArray.add(new LaserTower(type, num++, x, y));
				break;
		}
		return true;
	}
	
	public boolean towerFound(int x, int y) {
		return towerGrid[x][y];
	}
	
	public void destroy(int ID) {
		towerGrid[towerArray.get(ID).getLocationX()][towerArray.get(ID).getLocationY()] = false;
		towerArray.set(ID, towerArray.get(num - 1));
		towerArray.remove(--num);
	}
}