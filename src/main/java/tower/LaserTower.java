package tower;

import java.util.ArrayList;
import java.lang.Math;

import monster.Monster;
import monster.MonsterGenerator;

public class LaserTower extends Tower{

	protected int base_power = 10;
	protected int power = 10;
	protected int minRange = 0;
	protected int maxRange = 680;
	
	LaserTower(int type, int ID, int x, int y) {
		super(type, ID, x, y);
		printTowerInfo();
	}
	
	public void shoot() {
		ArrayList<Monster> monsterArray = MonsterGenerator.getMonsterArray();
		if(monsterArray.size() == 0) return;
		
		int target = -1;
		double target_to_end = 680;
		
		for (int i = 0; i < monsterArray.size(); i++) { // loop all monster, find the monster closest to end point
			double to_end = monsterArray.get(i).getdistanceToEndpoint();
			if (to_end < target_to_end) {
				target = i;
				target_to_end = to_end;
			}
		}
		//consumes some resources
		int x = monsterArray.get(target).getLocationX(); // make a line towards the monster
		int y = monsterArray.get(target).getLocationY();
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