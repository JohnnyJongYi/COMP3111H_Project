package tower;

import java.util.ArrayList;
import java.lang.Math;

import monster.Monster;
import monster.MonsterGenerator;
import sample.staticInterface;

public class LaserTower extends Tower{
	protected int towerType = 4;
	protected int base_power = 10;
	protected int power = 10;
	protected int minRange = 0;
	protected int maxRange = 680;
	
	LaserTower(int x, int y) {
		super(x, y);
		printTowerInfo();
	}
	
	public void printTowerInfo() {
		System.out.println("Tower type: Laser Tower");
		super.printTowerInfo();
	}
	
	public void shoot(staticInterface f) {
		targetX = targetY = 0;
		ArrayList<Monster> monsterArray = MonsterGenerator.getMonsterArray();
		if(monsterArray.size() == 0) return;
		
		int target = -1;
		double target_to_end = 680;
		
		for (int i = 0; i < monsterArray.size(); i++) { // loop all monster, find the monster closest to end point
			double to_end = monsterArray.get(i).getDistanceToEndpoint();
			if (to_end < target_to_end) {
				target = i;
				target_to_end = to_end;
			}
		}
		
		if (target != -1) {
			//consumes some resources
			int x = monsterArray.get(target).getLocationX(); // make a line towards the monster
			int y = monsterArray.get(target).getLocationY();
			targetX = x;
			targetY = y;
			int a = y - locationY;
			int b = locationX - x;
			int c = - a * locationX - b * locationY;
			double divider = Math.sqrt(a * a + b * b);
			
			for (int i = 0; i < monsterArray.size(); i++) { // loop all monster, damage all monster 3 px away from the line
				if (Math.abs(a * monsterArray.get(i).getLocationX() + b * monsterArray.get(i).getLocationY() + c) / divider <= 3)
					monsterArray.get(i).takedamage(1, power);
			}
		}
	}
}