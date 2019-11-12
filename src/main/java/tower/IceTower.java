package tower;

import java.util.ArrayList;

import monster.Monster;
import monster.MonsterGenerator;

public class IceTower extends Tower{
	
	protected int base_power = 0;
	protected int power = 0;
	protected int minRange = 0;
	protected int maxRange = 65;
	protected int range2 = maxRange * maxRange;
	
	IceTower(int type, int ID, int x, int y) {
		super(type, ID, x, y);
		printTowerInfo();
	}
	
	public void shoot() {
		ArrayList<Monster> monsterArray = MonsterGenerator.getMonsterArray();
		if (monsterArray.size() == 0) return;
		
		int target = -1;
		double target_to_end = 680;
		
		for (int i = 0; i < monsterArray.size(); i++) { // loop all monster, find the monster in range and closest to end point
			double to_end = monsterArray.get(i).getdistanceToEndpoint();
			if (to_end < target_to_end && Math.pow(monsterArray.get(i).getLocationX() - locationX, 2) + Math.pow(monsterArray.get(i).getLocationY() - locationY, 2) <= range2) {
				target = i;
				target_to_end = to_end;
			}
		}
		if (target != -1) monsterArray.get(target).takedamage(0, level); // slow the monster
	}
}