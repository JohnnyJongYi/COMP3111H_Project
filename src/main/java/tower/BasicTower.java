package tower;

import java.util.ArrayList;

import monster.Monster;
import monster.MonsterGenerator;
import sample.Grid;
import sample.staticInterface;

public class BasicTower extends Tower {
	protected int range2 = maxRange * maxRange;
	
	BasicTower(int x, int y, Grid label, staticInterface interf) {
		super(1, 10, x, y, 0, 65, label, interf);
	}
	
	public void printTowerInfo() {
		System.out.println("Tower type: Basic Tower");
		super.printTowerInfo();
	}
	
	public void shoot(staticInterface f) {
		ArrayList<Monster> monsterArray = MonsterGenerator.getMonsterArray();
		if (monsterArray.size() == 0) return;
		
		Monster target = null;
		double target_to_end = 1000;
		
		for (Monster monster : monsterArray) { // loop all monster, find the monster in range and closest to end point
			double to_end = monster.getDistanceToEndpoint();
			if (to_end < target_to_end && Math.pow(monster.getLocationX() - midX, 2) + Math.pow(monster.getLocationY() - midY, 2) <= range2) {
				target = monster;
				target_to_end = to_end;
			}
		}
		
		if (target != null) {
			System.out.println();
			System.out.println("Basic shooting target--->");
			System.out.println("Damaging monster " + power + "dmg--->");
			target.printMonsterInfo();
			
			assert Math.pow(target.getLocationX(), 2) + Math.pow(target.getLocationY(), 2) <= range2 : "Basic shooting out of range"; // Test range
			
			target.takedamage(1, power);
			f.ShootBasic(label, target.getGrid());
			
			System.out.println("Basic shooting done--->");
			System.out.println();
		}
	}
	
	
}