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
		
		int[][] MonstersInRange = MonsterGenerator.getMonstersInRange();
		
		int target_x = -1;
		int target_y = -1;
		int max_monster = 0;
		double target_to_end = 680;
		int x_low = Math.max(0, locationX - maxRange);
		int x_high = Math.min(480, locationX + maxRange);
		
		for (int x = x_low; x <= x_high; x++) {
			double a = Math.sqrt(maxRange2 - x * x);
			int y_low = (int) Math.max(0, - Math.ceil(a));
			int y_high = (int) Math.min(480, Math.ceil(a));
			
			for (int y = y_low; y <= y_high; y++)
			{
				
				if (MonstersInRange[x][y].size() >= max_monster && x * x + y * y >= minRange2) {
					ArrayList<Integer> indexArray = MonstersInRange[x][y];
					for (int i = 0; i < indexArray.size(); i++) {
						double to_end = monsterArray.get(indexArray.get(i)).getdistanceToEndpoint();
						if (to_end < target_to_end)
						{
							target_x = x;
							target_y = y;
							max_monster = MonstersInRange[x][y].size();
							target_to_end = to_end;
						}
					}
				}
			}
		}
		if (max_monster > 0)
		{
			ArrayList<Integer> indexArray = MonstersInRange[target_x][target_y][1];
			for (int i = 0; i < indexArray.size(); i++) {
				monsterArray.get(indexArray.get(i)).takedamage(1, power);
			}
		}
	}
}