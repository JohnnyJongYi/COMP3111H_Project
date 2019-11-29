package tower;

import java.util.ArrayList;
import java.lang.Math;

import monster.Monster;
import monster.MonsterGenerator;
import sample.Grid;
import sample.staticInterface;

public class LaserTower extends Tower{
	
	LaserTower(int x, int y, Grid label, staticInterface interf) {
		super(4, 20, x, y, 0, 1000, label, interf);
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
			System.out.println();
			
			if (!f.changeMoney(-1)) {
				System.out.println("Laser lack of resources--->");
				return;//consumes some resources
			}
			
			System.out.println("Laser shooting target--->");
			int x = target.getLocationX(); // make a line towards the monster
			int y = target.getLocationY();
			int A = midX - y;
			int B = x - midX;
			int C = - A * midX - B * midX;
			double divider = Math.sqrt(A * A + B * B);
			
			for (Monster monster : monsterArray) { // loop all monster, damage all monster 3 px away from the line
				if (Math.abs(A * monster.getLocationX() + B * monster.getLocationY() + C) / divider <= 3) {
					System.out.println("Damaging monster--->");
					monster.printMonsterInfo();
					
					monster.takedamage(1, power);
				}
			}
			
			f.ShootLaser(label, target.getGrid());
			
			System.out.println("Laser shooting done--->");
			System.out.println();
		}
	}
}