package tower;

import java.util.ArrayList;

import monster.Monster;
import monster.MonsterGenerator;
import sample.staticInterface;

public class BasicTower extends Tower {
	protected int towerType = 1;
	protected int base_power = 10;
	protected int power = 10;
	protected int minRange = 0;
	protected int maxRange = 65;
	protected int range2 = maxRange * maxRange;
	
	BasicTower(int x, int y) {
		super(x, y);
		printTowerInfo();
	}
	
	public void printTowerInfo() {
		System.out.println("Tower type: Basic Tower");
		super.printTowerInfo();
	}
	
	public void shoot(staticInterface f) {
		ArrayList<Monster> monsterArray = MonsterGenerator.getMonsterArray();
		if (monsterArray.size() == 0) return;
		
		Monster target = null;
		double target_to_end = 680;
		
		for (Monster monster : monsterArray) { // loop all monster, find the monster in range and closest to end point
			double to_end = monster.getDistanceToEndpoint();
			if (to_end < target_to_end && Math.pow(monster.getLocationX() - locationX, 2) + Math.pow(monster.getLocationY() - locationY, 2) <= range2) {
				target = monster;
				target_to_end = to_end;
			}
		}
		
		if (target != null) {
			target.takedamage(1, power); // shoot the monster
			f.ShootBasic(label, target.getGrid());
		}
	}
	
	
}