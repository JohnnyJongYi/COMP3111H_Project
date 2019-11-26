package sample;

public interface staticInterface {
		public static Grid spawnMonster(double xPosition, double yPosition, String name,double HP) {
			return (new Grid());
		}
		
		public void monsterDie(Grid monster) ;
		
		public void monsterAttacked(Grid monster);
		
		public void monsterAttackedAndSlowed(Grid monster);
		
		public void monsterSlowed(Grid monster) ;
		
		public void monsterNotSlowed(Grid monster);
}

