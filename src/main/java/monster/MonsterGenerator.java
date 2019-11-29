package monster;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import sample.staticInterface;
import Coordinates.*;
import tower.TowerHandler;

public class MonsterGenerator
{
	protected static  ArrayList<Monster> monsterArray;
	protected  int monsterIDCounter;
	protected static  int timestamp;
	private static MonsterInRange forCatapult; 
	protected static boolean monsterHasReached;
	
	protected static ArrayList<Monster> deadMonsters;
	protected staticInterface interf;
	
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/points.odb");
	static EntityManager em = emf.createEntityManager();
	
	public MonsterGenerator(staticInterface interf) 
	{
		monsterArray = new ArrayList<Monster>();
		monsterIDCounter = 0;
		timestamp = 0;
		forCatapult = new MonsterInRange(monsterArray);
		monsterHasReached = false;
		this.interf = interf;
		deadMonsters = new ArrayList<Monster>();
	}
	
	public void updateMonsterEachTimestamp() throws OutOfArenaException, MovedToWrongGrid
	{
		deadMonsters.clear();
		removeDead();
		checkAnySlower();
		moveAllMonsters();
		TowerHandler.resetNewTowerBuilt();
		if(timestamp % 20== 0)
		{generate(interf);}
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
	
	public void generate(staticInterface interf)
	{
		em.getTransaction().begin();
		int type = (int)(Math.random() * 3 + 1);
		// Randomly choose monster type
		MonsterData newMonster = null;
		switch(type)
		{
			case 1 : 
				newMonster = new MonsterData(type, monsterIDCounter);
				monsterArray.add(new Unicorn(timestamp,monsterIDCounter,type, interf,newMonster));
				monsterIDCounter++;
				em.persist(newMonster);
				break;
			case 2 : 
				newMonster = new MonsterData(type, monsterIDCounter);
				monsterArray.add(new Penguin(timestamp,monsterIDCounter,type, interf,newMonster));
				monsterIDCounter++;
				em.persist(newMonster);
				break;
			case 3 : 
				newMonster = new MonsterData(type, monsterIDCounter);
				monsterArray.add(new Fox(timestamp,monsterIDCounter,type, interf,newMonster));
				monsterIDCounter++;
				em.persist(newMonster);
				break;		
		}
		em.getTransaction().commit();
	}
	
	public static void closeDataBase()
	{	
		emf.close();
		em.clear();
	}
	
	public static void retrieveQuery()
	{	
		
		TypedQuery<MonsterData> query = em.createQuery("SELECT m FROM MonsterData m", MonsterData.class);
		List<MonsterData> results =  query.getResultList();
	
		System.out.println(results.size());
		
		for(int i = 0 ; i< results.size();i++)
		{
			if(results.get(i).getID()==0)
				System.out.println("______________________New Game______________________");
			printStatus(results.get(i).getType(), results.get(i).getID(), results.get(i).getStatus());
		}
	}
	
	
	static void printStatus(String type, int id, boolean status)
	{
		System.out.println("Monster Type : "+type);
		System.out.println("Monster ID : "+id);
		if(!status)
			System.out.println("Killed in action");
		else
			System.out.println("Survived");
		
		System.out.println("-----------------");
		
		
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


