package tower;

import java.util.ArrayList;

import monster.Monster;
import monster.MonsterGenerator;
import sample.Grid;
import sample.staticInterface;

public class IceTower extends Tower{
	protected int towerType = 2;
	protected int base_power = 0;
	protected int power = 0;
	protected int range2 = maxRange * maxRange;
	
	IceTower(int x, int y, Grid label, staticInterface interf) {
		super(x, y, 0, 65, label, interf);
	}
	
	public void printTowerInfo() {
		System.out.println("Tower type: IceTower");
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
			System.out.println("Ice shooting target--->");
			System.out.println("Slowing monster--->");
			target.printMonsterInfo();
			
			target.takedamage(0, level); // slow the monster
			f.ShootIce(label, target.getGrid());
			
			System.out.println("Ice shooting done--->");
		}
	}
}