package monster;
import java.util.ArrayList;

import Coordinates.OutOfArenaException;
import tower.TowerHandler;

public class MonsterGenerator
{
	protected static  ArrayList<Monster> monsterArray;
	protected  int monsterIDCounter;
	protected static  int timestamp;
	private static MonsterInRange forCatapult; 
	protected static boolean monsterHasReached;
	
	protected static ArrayList<Monster> deadMonsters;
	
	
	public MonsterGenerator() 
	{
		monsterArray = new ArrayList<Monster>();
		monsterIDCounter = 0;
		timestamp = 0;
		forCatapult = new MonsterInRange(monsterArray);
		monsterHasReached = false;
		
		deadMonsters = new ArrayList<Monster>();
	}
	
	public void updateMonsterEachTimestamp() throws OutOfArenaException, MovedToWrongGrid
	{
		deadMonsters.clear();
		removeDead();
		checkAnySlower();
		moveAllMonsters();
		if(timestamp % 20 == 0)
		{generate();}
		timestamp++;
		
		if(TowerHandler.catapultFound())
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
	
	protected  void removeDead()
	{
		int size = monsterArray.size();
		for(int i = 0; i<size ; i++)
		{
			if(!monsterArray.get(i).isAlive())
			{
				deadMonsters.add(monsterArray.remove(i));
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

	public static ArrayList<Monster> getMonsterArray() 
	{
		return monsterArray;
	}
	
	public static ArrayList<Monster>[][] getMonstersInRange()
	{
		return forCatapult.getRangeArray();
	}
	
	public static boolean getMonsterHasReachedEnd()
	{
		return monsterHasReached;
	}
	
	public static ArrayList<Monster> getDeadMonsterArray()
	{
		return deadMonsters;
	}
}


