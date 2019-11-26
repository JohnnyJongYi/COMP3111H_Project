package tower;

import java.util.ArrayList;
import java.lang.Math;

import monster.Monster;
import monster.MonsterGenerator;
import sample.Grid;
import sample.staticInterface;

public class LaserTower extends Tower{
	protected int towerType = 4;
	protected int base_power = 10;
	protected int power = 10;
	protected int minRange = 0;
	protected int maxRange = 680;
	
	LaserTower(int x, int y, Grid label, staticInterface interf) {
		super(x, y, label, interf);
		printTowerInfo();
	}
	
	public void printTowerInfo() {
		System.out.println("Tower type: Laser Tower");
		super.printTowerInfo();
	}
	
	public void shoot(staticInterface f) {
		ArrayList<Monster> monsterArray = MonsterGenerator.getMonsterArray();
		if(monsterArray.size() == 0) return;
		
		Monster target = null;
		double target_to_end = 680;
		
		for (Monster monster : monsterArray) { // loop all monster, find the monster closest to end point
			double to_end = monster.getDistanceToEndpoint();
			if (to_end < target_to_end) {
				target = monster;
				target_to_end = to_end;
			}
		}
		
		if (target != null) {
			//consumes some resources
			int x = target.getLocationX(); // make a line towards the monster
			int y = target.getLocationY();
			int a = y - locationY;
			int b = locationX - x;
			int c = - a * locationX - b * locationY;
			double divider = Math.sqrt(a * a + b * b);
			
			for (Monster monster : monsterArray) { // loop all monster, damage all monster 3 px away from the line
				if (Math.abs(a * monster.getLocationX() + b * monster.getLocationY() + c) / divider <= 3)
					monster.takedamage(1, power);
			}
			
			f.ShootLaser(label, target.getGrid());
		}
	}
}