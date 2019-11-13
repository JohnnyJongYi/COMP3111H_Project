package monster;
import Coordinates.Location;
import Coordinates.OutOfArenaException;

import java.util.ArrayList;


public abstract class Monster 
{
	protected int monsterType;
	protected int monsterID; //monster ID, integer from 0 ~ MAX
	protected int time; // number of 0.5sec timestamps passed when generated
	protected int hp;
	protected int maxHP;
	protected int speed;
	protected int OriginalSpeed;
	protected boolean alive;
	protected Location loc;
	protected double distanceToEndpoint;
	
	protected boolean isSlower;
	protected int slowerStartTime;
	protected int slowerDuration;
	
	protected int[][] monsterGrid;
	protected int currentGrid;
	protected int nextGrid;
	protected ArrayList<Integer> path;
	

	
	Monster(int timestamp, int mID, int type)
	{  
		monsterType = type;
		time = timestamp;
		loc = new Location(0,479);
		monsterID = mID;
		alive = true;
		isSlower = false;
		slowerStartTime = Integer.MAX_VALUE;
		slowerDuration = 0;
		updateDistanceToEnd();
		
		monsterGrid = new int[12][12];
		for(int i = 0 ; i<=11 ; i++)
			for(int j = 0 ; j <= 11 ; j++)
				monsterGrid[i][j] = i*100 + j;
		currentGrid = monsterGrid[0][11];
		nextGrid = 0;
		path = new ArrayList<Integer>();
	}
	
	protected void stronger()
	{
		int quotient = time / 40;
		hp = hp + (10*quotient);
	}
	
	protected void die()
	{
		alive = false;
	}
	
	protected void nextMove() throws OutOfArenaException, MovedToWrongGrid
	{
		if(time == MonsterGenerator.timestamp /*tower configuration change*/)
			calculatePath(currentGrid, flagArray);//based on current grid
		
		for(int i = 0 ; i< path.size(); i++)
		{
			if(currentGrid == path.get(i))
			{
				nextGrid = path.get(i+1);
			}
		}
		
		if(currentGrid > nextGrid) //up
		{
			loc.update(0, -speed);
		}
		else // right
		{
			loc.update(speed , 0);
		}
		updateDistanceToEnd();
		
		if(monsterType == 2 && MonsterGenerator.timestamp % 10 == 0)
		{
			if(maxHP - hp >0)
			{
				// GUI for heal
				if(maxHP - hp > 5)
					hp = hp + 5;
				else
					hp = maxHP;	
			}
		}
		
		int gridAfterMove = monsterGrid[(loc.getX() / 40)][(loc.getY() / 40)];
		System.out.println(gridAfterMove);
		
		if(gridAfterMove != currentGrid)
		{
			if(nextGrid == gridAfterMove)
			{
				currentGrid = nextGrid;
				if(currentGrid == monsterGrid[11][0])
					MonsterGenerator.monsterHasReached = true;
			}
			else
			{
				MovedToWrongGrid except = new MovedToWrongGrid();
				throw except;
			}
		}
			

	}
	
	protected void calculatePath(int currentGridID, boolean [][] flagArray)
	{
		path.clear();
		int counterGrid = currentGridID;
		boolean reachedEnd = false;
		
		while(!reachedEnd)
		{
			path.add(counterGrid);
			
			int counterX = counterGrid / 100;
			int counterY = counterGrid % 100;
			
			if(counterX+1 >11 || counterY-1 <0)
				reachedEnd = true;
			else
			{
				if(flagArray[counterX+1][counterY] && flagArray[counterX][counterY-1])
				{
					//can go both up or right so choose randomly
					int direct = (int)(Math.random() * 2 + 1);
					if(direct == 1)
						counterGrid = monsterGrid[counterX+1][counterY];
					else
						counterGrid = monsterGrid[counterX][counterY-1];
				}
				else if(flagArray[counterX+1][counterY])
					counterGrid = monsterGrid[counterX+1][counterY];
				else if(flagArray[counterX][counterY-1]) 
					counterGrid = monsterGrid[counterX][counterY-1];				
			}
		}
	}
	
	protected void updateDistanceToEnd()
	{
		distanceToEndpoint = Math.sqrt((0 - loc.getY()) * (0 - loc.getY()) + (480 - loc.getX()) * (480 - loc.getX()));
	}
	
	public void takedamage(int towerType, int damage)
	{
		if(towerType == 0)
		{
			isSlower = true;
			slowerStartTime = MonsterGenerator.timestamp+1;
			slowerDuration = 20*damage;
		}
		else if(towerType == 1)
		{
			hp = hp - damage;
			if ( hp <= 0)
				die();
		}
		//reduce hp by certain amount according to tower type
	}

	void checkIfSlowerNeeded()
	{
		if(isSlower)
		{
			if(MonsterGenerator.timestamp - slowerStartTime <= slowerDuration)
			{
				speed = OriginalSpeed / 2; 
			}
			else
			{
				isSlower = false;
				slowerStartTime = Integer.MAX_VALUE;
				slowerDuration = 0;
				speed = OriginalSpeed;
			}
		}
	}
	
	public void printMonsterInfo()
	{
		switch(monsterType)
		{
			case 1 : 
				System.out.println("monster Type : Unicorn");
				break;
			case 2 : 
				System.out.println("monster Type : Penguin");
				break;
			case 3 : 
				System.out.println("monster Type : Fox");
				break;		
		}
		System.out.println("monster ID : " + monsterID);
		System.out.println("time generated : "+time);
		System.out.println("HP : "+ hp);
		System.out.println("Speed : "+ speed);
		System.out.println("IsAlive : " + alive);
		System.out.println("Location : (" + loc.getX() +", "+loc.getY()+")");
		System.out.println("-------------------------------------------");
	}
	
	
	public int getMonsterType() {
		return monsterType;
	}

	public int getMonsterID() {
		return monsterID;
	}

	public int getHp() {
		return hp;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public boolean isAlive() {
		return alive;
	}

	public Location getLoc() {
		return loc;
	}
	
	public int getLocationX()
	{return loc.getX();}
	
	public int getLocationY()
	{return loc.getY();}

	public double getDistanceToEndpoint() {
		return distanceToEndpoint;
	}
}

class MovedToWrongGrid extends Exception
{

	public MovedToWrongGrid()
	{
		super("One of the monster moved to the wrong grid");
	}
}
