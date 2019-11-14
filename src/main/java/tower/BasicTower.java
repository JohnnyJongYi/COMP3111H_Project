package tower;

import java.util.ArrayList;

import tower.TowerHandler;
import monster.Monster;
import monster.MonsterGenerator;

public class BasicTower extends Tower {
	protected int towerType = 1;
	protected int base_power = 10;
	protected int power = 10;
	protected int minRange = 0;
	protected int maxRange = 65;
	protected int range2 = maxRange * maxRange;
	
	BasicTower(int ID, int x, int y) {
		super(ID, x, y);
		TowerHandler.setNOA(locationX, locationY, maxRange, maxRange * maxRange, 0, 1);
		printTowerInfo();	
	}
	
	public void printTowerInfo() {
		System.out.println("Tower type: Basic Tower");
		super.printTowerInfo();
	}
	
	public void shoot() {
		ArrayList<Monster> monsterArray = MonsterGenerator.getMonsterArray();
		if (monsterArray.size() == 0) return;
		
		int target = -1;
		double target_to_end = 680;
		
		for (int i = 0; i < monsterArray.size(); i++) { // loop all monster, find the monster in range and closest to end point
			double to_end = monsterArray.get(i).getDistanceToEndpoint();
			if (to_end < target_to_end && Math.pow(monsterArray.get(i).getLocationX() - locationX, 2) + Math.pow(monsterArray.get(i).getLocationY() - locationY, 2) <= range2) {
				target = i;
				target_to_end = to_end;
			}
		}
		if (target != -1) monsterArray.get(target).takedamage(1, power); // shoot the monster
	}
}