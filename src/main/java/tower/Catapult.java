package tower;

import java.util.ArrayList;

import monster.Monster;
import monster.MonsterGenerator;
import sample.Grid;
import sample.staticInterface;

public class Catapult extends Tower{
	protected int towerType = 3;
	protected int base_power = 10;
	protected int power = 10;
	protected int minRange2 = minRange * minRange;
	protected int maxRange2 = maxRange * maxRange;
	
	Catapult(int x, int y, Grid label, staticInterface interf) {
		super(x, y, 50, 150, label, interf);
	}
	
	public void printTowerInfo() {
		System.out.println("Tower type: Catapult");
		super.printTowerInfo();
	}
	
	public void shoot(staticInterface f)
	{
		ArrayList<Monster> monsterArray = MonsterGenerator.getMonsterArray();
		if (monsterArray.size() == 0) return;
		
		ArrayList<Monster>[][] MonstersInRange = MonsterGenerator.getMonstersInRange();
		
		int target_x = 0;
		int target_y = 0;
		int max_monster = 0;
		double target_to_end = 1000;
		int x_low = Math.max(0, midX - maxRange);
		int x_high = Math.min(479, midX + maxRange);
		
		for (int x = x_low; x <= x_high; x++) {
			int dx = Math.abs(midX - x);
			int max_dy = (int) Math.floor(Math.sqrt(maxRange2 - dx * dx));
			int y_low = Math.max(0, midY - max_dy);
			int y_high = Math.min(479, midY + max_dy);
			
			for (int y = y_low; y <= y_high; y++) { // for all pixel in range
				int dy = Math.abs(midY - y);
				
				if (MonstersInRange[x][y] != null && MonstersInRange[x][y].size() >= max_monster && dx * dx + dy * dy >= minRange2) { // find the pixel with most monster in range
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
		
		if (max_monster > 0) {
			System.out.println();
			System.out.println("Catapult shooting--->");
			System.out.println(max_monster + " monsters at (" + target_x + ", " + target_y + ")");
			
			assert Math.pow(target_x, 2) + Math.pow(target_y, 2) <= maxRange2 : "Catapult shooting out of range"; // Test range
			
			for (Monster monster : MonstersInRange[target_x][target_y]) {
				System.out.println("Damaging monster--->");
				monster.printMonsterInfo();
				monster.takedamage(1, power);
			}
			
			f.ShootCatapult(label, target_x, target_y);
			
			System.out.println("Catapult shooting done--->");
			System.out.println();
		}
	}
}