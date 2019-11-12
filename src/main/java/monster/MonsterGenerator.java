package monster;
import java.util.ArrayList;

import Coordinates.OutOfArenaException;
import tower.TowerHandler;

public class MonsterGenerator
{
	protected static ArrayList<Monster> monsterArray;
	protected static int monsterIDCounter;
	protected static int timestamp;
	private MonsterInRange forCatapult; 
	
	MonsterGenerator() 
	{
		monsterArray = new ArrayList<Monster>();
		monsterIDCounter = 0;
		timestamp = 0;
		forCatapult = new MonsterInRange(monsterArray);
	}
	
	public void updateMonsterEachTimestamp() throws OutOfArenaException, MovedToWrongGrid
	{
		removeDead();
		checkAnySlower();
		if(timestamp % 20 == 0)
		{generate();}
		moveAllMonsters();
		timestamp++;
		
		if(true/*catapultFound*/)
		{
			forCatapult.startSearch();
		}
	}
	
	private void moveAllMonsters() throws OutOfArenaException, MovedToWrongGrid
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
	
	protected void checkAnySlower()
	{
		for(int i = 0 ; i<monsterArray.size();i++)
		{
			monsterArray.get(i).checkIfSlowerNeeded();
		}
	}

	public ArrayList<Monster> getMonsterArray() 
	{
		return monsterArray;
	}
	
	public ArrayList<Monster>[][] getMonsterInRangeArray()
	{
		return forCatapult.getRangeArray();
	}
}


