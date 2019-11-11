package monster;
import java.util.ArrayList;


public class MonsterGenerator
{
	protected ArrayList<Monster> monsterArray;
	protected int monsterIDCounter;
	protected static int timestamp;
	
	MonsterGenerator() 
	{
		monsterArray = new ArrayList<Monster>();
		monsterIDCounter = 0;
		timestamp = 0;
	}
	
	public void updateMonster()
	{
		removeDead();
		if(timestamp % 20 == 0)
		{generate();}
		moveAllMonsters();
		timestamp++;
	}
	
	private void moveAllMonsters()
	{
		for(int i = 0; i<monsterArray.size() ; i++)
			monsterArray.get(i).nextMove();
	}
	
	public void generate()
	{
		int type = (int)(Math.random() * 3 + 1);
		// Randomly choose monster type
		switch(type)
		{
			case 1 : 
				monsterArray.add(new Unicorn(timestamp,monsterIDCounter,type));
				monsterIDCounter++;
				break;
			case 2 : 
				monsterArray.add(new Penguin(timestamp,monsterIDCounter,type));
				monsterIDCounter++;
				break;
			case 3 : 
				monsterArray.add(new Fox(timestamp,monsterIDCounter,type));
				monsterIDCounter++;
				break;		
		}
		
	}
	
	protected void removeDead()
	{
		int size = monsterArray.size();
		for(int i = 0; i<size ; i++)
		{
			if(!monsterArray.get(i).isAlive())
			{
				monsterArray.remove(i);
				size--;
			}
		}
		
	}

	public ArrayList<Monster> getMonsterArray() {
		return monsterArray;
	}


}


