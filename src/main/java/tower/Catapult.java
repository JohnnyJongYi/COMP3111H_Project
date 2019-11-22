package tower;

import java.util.ArrayList;

import monster.Monster;
import monster.MonsterGenerator;

public class Catapult extends Tower{
	protected int towerType = 3;
	protected int base_power = 10;
	protected int power = 10;
	protected int minRange = 50;
	protected int maxRange = 150;
	protected int minRange2 = minRange * minRange;
	protected int maxRange2 = maxRange * maxRange;
	
	Catapult(int x, int y) {
		super(x, y);
		printTowerInfo();
	}
	
	public void printTowerInfo() {
		System.out.println("Tower type: Catapult");
		super.printTowerInfo();
	}
	
	public void shoot()
	{
		targetX = targetY = 0;
		ArrayList<Monster> monsterArray = MonsterGenerator.getMonsterArray();
		if (monsterArray.size() == 0) return;
		
		ArrayList<Monster>[][] MonstersInRange = MonsterGenerator.getMonstersInRange();
		
		int target_x = -1;
		int target_y = -1;
		int max_monster = 0;
		double target_to_end = 680;
		int x_low = Math.max(0, locationX - maxRange);
		int x_high = Math.min(480, locationX + maxRange);
		
		for (int x = x_low; x <= x_high; x++) {
			int delta_x = Math.abs(locationX - x);
			int max_delta_y = (int) Math.floor(Math.sqrt(maxRange2 - delta_x * delta_x));
			int y_low = Math.max(0, locationY - max_delta_y);
			int y_high = Math.min(480, locationY + max_delta_y);
			
			for (int y = y_low; y <= y_high; y++) { // for all pixel in range
				int delta_y = Math.abs(locationY - y);
				if (MonstersInRange[x][y].size() >= max_monster && delta_x * delta_x + delta_y * delta_y >= minRange2) { // find the pixel with most monster in range
					for (Monster m : MonstersInRange[x][y]) {
						double to_end = m.getDistanceToEndpoint();
						if (to_end < target_to_end) {
							target_x = x;
							target_y = y;
							max_monster = MonstersInRange[x][y].size();
							target_to_end = to_end;
						}
					}
				}
			}
		}
		
		if (max_monster != 0) {
			for (Monster m : MonstersInRange[target_x][target_y]) m.takedamage(1, power);
			targetX = target_x;
			targetY = target_y;
		}
	}
}