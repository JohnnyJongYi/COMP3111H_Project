package tower;

import arena.Grid;
import sample.staticInterface;

public abstract class Tower {
	protected int towerType;
	protected int level = 1;
	protected int base_power;
	protected int power;
	protected int minRange;
	protected int maxRange;
	protected int locationX;
	protected int locationY;
	protected Grid label;
	
	Tower(int x, int y) {
		locationX = x;
		locationY = y;
	}

	public void upgrade() {
		level += 1;
		power = base_power * level;
	}
	
	public void shoot(staticInterface f) {}
	
	public int getTowerType() {
		return towerType;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getPower() {
		return power;
	}
	
	public int getMinRange() {
		return minRange;
	}
	
	public int getMaxRange() {
		return maxRange;
	}
	
	public int getLocationX() {
		return locationX;
	}
	
	public int getLocationY() {
		return locationY;
	}
	
	public void printTowerInfo() {
		System.out.println("Level : "+ level);
		System.out.println("Power : " + power);
		System.out.println("Range : " + getMinRange() + " - " + getMaxRange());
		System.out.println("Location : (" + locationX + ", " + locationY + ")");
		System.out.println("-------------------------------------------");
	}
}