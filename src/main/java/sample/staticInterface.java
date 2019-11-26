package sample;

import java.util.ArrayList;

public interface staticInterface {
		public Grid spawnMonster(double xPosition, double yPosition, String name,double HP) ;
			
		public void monsterDie(Grid monster) ;
		
		public void monsterAttacked(Grid monster);
		
		public void monsterAttackedAndSlowed(Grid monster);
		
		public void monsterSlowed(Grid monster) ;
		
		public void monsterNotSlowed(Grid monster);
		
		public void changeHP(Grid monster, int newHP);
		
		public boolean moveMonster(Grid monster, int deltaX, int deltaY);
		
		public void MonsterAttacked(Grid tower, ArrayList<Grid> monsterList);
		
		public void MonsterAttacked(Grid tower, Grid monster, boolean slowed); 
		
}

