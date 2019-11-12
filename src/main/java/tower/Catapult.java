package tower;

import java.util.ArrayList;

import monster.Monster;
import monster.MonsterGenerator;

public class Catapult extends Tower{

	protected int base_power = 10;
	protected int power = 10;
	protected int minRange = 50;
	protected int maxRange = 150;
	protected int minRange2 = minRange * minRange;
	protected int maxRange2 = maxRange * maxRange;
	
	Catapult(int type, int ID, int x, int y) {
		super(type, ID, x, y);
		printTowerInfo();
	}
	
	public void shoot()
	{
		ArrayList<Monster> monsterArray = MonsterGenerator.getMonsterArray();
		if (monsterArray.size() == 0) return;
		
		ArrayList<Integer>[][] MonstersInRange = MonsterGenerator.getMonstersInRange();
		
		int target_x = -1;
		int target_y = -1;
		int max_monster = 0;
		double target_to_end = 680;
		int x_low = Math.max(0, locationX - maxRange);
		int x_high = Math.min(480, locationX + maxRange);
		
		for (int x = x_low; x <= x_high; x++) {
			int delta_y = (int) Math.floor(Math.sqrt(maxRange2 - Math.pow(Math.abs(locationX - x), 2)));
			int y_low = Math.max(0, locationY - delta_y);
			int y_high = Math.min(480, locationY + delta_y);
			
			for (int y = y_low; y <= y_high; y++) { // for all pixel in range
				if (MonstersInRange[x][y].size() >= max_monster && x * x + y * y >= minRange2) { // find the pixel with most monster in range
					ArrayList<Integer> indexArray = MonstersInRange[x][y];
					for (int i : indexArray) {
						double to_end = monsterArray.get(i).getdistanceToEndpoint();
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
		if (max_monster > 0) {
			ArrayList<Integer> indexArray = MonstersInRange[target_x][target_y];
			for (int i : indexArray) monsterArray.get(i).takedamage(1, power);
		}
	}
}