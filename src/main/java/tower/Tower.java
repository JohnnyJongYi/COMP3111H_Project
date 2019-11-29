package tower;

import sample.Grid;
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
	protected int midX;
	protected int midY;
	protected Grid label;
	protected staticInterface interf;
	
	Tower(int type, int x, int y, int min, int max, Grid lbl, staticInterface f) {
		towerType = type;
		locationX = x;
		locationY = y;
		minRange = min;
		maxRange = max;
		midX = x * 40 + 20;
		midY = y * 40 + 20;
		label = lbl;
		interf = f;
	}

	public void upgrade() {
		System.out.print("Upgrading tower--->");
		int test = power;
		
		level += 1;
		power = base_power * level;
		System.out.print("power: " + power + " level: " + level);
		interf.upgradeTower(label, power, level);
		
		assert power == test + base_power : "Failed to upgrade";
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
	
	public int getMidX() {
		return midX;
	}
	
	public int getMidY() {
		return midY;
	}
	
	public void printTowerInfo() {
		System.out.println("Level : "+ level);
		System.out.println("Power : " + power);
		System.out.println("Range : " + getMinRange() + " - " + getMaxRange());
		System.out.println("Location : (" + locationX + ", " + locationY + ")");
		System.out.println("-------------------------------------------");
	}
}