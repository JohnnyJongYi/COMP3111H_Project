package tower;

public class BasicTower extends Tower{
	BasicTower(int type, int ID, int x, int y)
	{
		super(type, ID, x, y);
		printTowerInfo();
	}
	
	public void shoot()
	{
		Monster* monster = null;
		int min_distance = 480 * 480;
		for(int i = 0; i < monsterCount; i++)
		{
			if monsterArray[i].distance < min_distance
			{
				monster = &(monsterArray[i]);
				min_distance = 	monsterArray[i].distance
			}
		}
		monster.damage(BasicTower, 0);
	}
}