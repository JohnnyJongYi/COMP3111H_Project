package monster;
import Coordinates.*;
import tower.TowerHandler;
import sample.Grid;
import sample.staticInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;
import javax.persistence.*;

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
	
	protected boolean[][] flagArray;
	
	protected boolean isHealing;
	
	protected Grid monsterLabel;
	protected staticInterface interf;
	
	protected final int startxAlgo = 20;
	protected final int startyAlgo = 460;
	
	protected final int startxGrid = startxAlgo -15;
	protected final int startyGrid = startyAlgo - 15;
	
	MonsterData monsterData;
	
	Monster(int timestamp, int mID, int type, staticInterface interf, MonsterData data)
	{  
		
		this.interf =interf;
		monsterType = type;
		time = timestamp;
		loc = new Location(startxAlgo,startyAlgo);
		monsterID = mID;
		alive = true;
		isSlower = false;
		slowerStartTime = Integer.MAX_VALUE;
		slowerDuration = 0;
		updateDistanceToEnd();
		
		monsterGrid = new int[12][12];
		for(int i = 0 ; i<=11 ; i++)
			for(int j = 0 ; j <= 11 ; j++)
				monsterGrid[i][j] = j*100 + i;
		currentGrid = monsterGrid[11][0];
		nextGrid = currentGrid;
		path = new ArrayList<Integer>();
		
		isHealing = false;
		monsterData = data;
		
	}
	
	protected void stronger()
	{
		int quotient = time / 40;
		hp = hp + (10*quotient);
	}
	
	protected void die()
	{
		alive = false;
		interf.monsterDie(monsterLabel);
		interf.giveMoneyForKill();
	}
	
	protected void nextMove() throws OutOfArenaException, MovedToWrongGrid
	{
		int deltax = 0;
		int deltay = 0;
		
		if(time == MonsterGenerator.timestamp-1 || TowerHandler.newTowerBuilt() ) 
		{
			path.clear();
			flagArray = TowerHandler.towerGrid();
			calculatePath(currentGrid);//based on current grid
			Collections.reverse(path);
		}
		
	
//		for(int i = 0 ; i< 12; i++)
//		{for(int j = 0 ; j< 12; j++)
//			System.out.print(flagArray[i][j] + " "); 
//		System.out.println(""); }

		
		for(int i = 0 ; i< path.size(); i++)
		{
			if(currentGrid == path.get(i) && currentGrid != 1100)
			{
				nextGrid = path.get(i+1);
			}
		}
		
		
		if(nextGrid == currentGrid-1) //up
		{
			loc.update(0, -speed);
			deltay = speed;
			
		}
		else if(nextGrid == currentGrid+100 )  // right
		{
			loc.update(speed , 0);
			deltax = speed;
		}
		else if (nextGrid == currentGrid-100) //left
		{
			loc.update(-speed, 0);
			deltax = -speed;
		}
		else if (nextGrid == currentGrid +1) // down
		{
			loc.update(0, speed);
			deltay = -speed;
		}
		
		updateDistanceToEnd();
		
		
		
		if(interf == null)
			System.out.println("interf is null"); 
		else
			interf.moveMonster(monsterLabel, deltax, deltay);
		
		if(monsterType == 2 )//&& MonsterGenerator.timestamp % 10 == 0)
		{
			if(maxHP - hp >0)
			{
				isHealing = true;
				// GUI for heal
				if(maxHP - hp > 2)
				{
					hp = hp + 2;
					interf.changeHP(monsterLabel, hp);
					System.out.println("****************************************************************");
				}
				else
					hp = maxHP;	
			}
			else
				isHealing = false;
		}
		
		int gridAfterMove = monsterGrid[(loc.getY() / 40)][(loc.getX() / 40)];
		System.out.println(gridAfterMove);
		
		if(gridAfterMove != currentGrid)
		{
			if(nextGrid == gridAfterMove)
			{
				currentGrid = nextGrid;
				if(currentGrid == monsterGrid[0][11])
					MonsterGenerator.monsterHasReached = true;
			}
			else
			{
				MovedToWrongGrid except = new MovedToWrongGrid();
				throw except;
			}
		}
	}
	
	
	protected void calculatePath(int grid)
	{
		calculatePathNonFox(grid);
	}
	
	protected boolean calculatePathNonFox(int grid)
	{
		int counterX = grid / 100;
		int counterY = grid % 100;
		
		if(counterY-1 >=0 && flagArray[counterY-1][counterX]) // up
		{
			flagArray[counterY][counterX] = false;
			if(calculatePathNonFox(monsterGrid[counterY-1][counterX]))
			{
				path.add(grid);
				return true;
			}
		}
		
		if(counterX+1 <12 && flagArray[counterY][counterX+1]) // right
		{
			flagArray[counterY][counterX] = false;
			if(calculatePathNonFox(monsterGrid[counterY][counterX+1]))
			{
				path.add(grid);
				return true;
			}
		}
		
		if(counterY+1 <12 && flagArray[counterY+1][counterX]) // up
		{
			flagArray[counterY][counterX] = false;
			if(calculatePathNonFox(monsterGrid[counterY+1][counterX]))
			{
				path.add(grid);
				return true;
			}
		}
	
		if(counterX-1 >=0 && flagArray[counterY][counterX-1]) // right
		{
			flagArray[counterY][counterX] = false;
			if(calculatePathNonFox(monsterGrid[counterY][counterX-1]))
			{
				path.add(grid);
				return true;
			}
		}

	
		
		
		if(grid == 1100)
		{
			path.add(1100);
			return true;
		}
		else return false;
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
			interf.monsterSlowed(monsterLabel);
		}
		else if(towerType == 1)
		{
			hp = hp - damage;
			
		
			interf.changeHP(monsterLabel, hp);
			if ( hp <= 0)
			{
				die();
				monsterData.dead();
			}
			else
			{
				if(isSlower)
					interf.monsterAttackedAndSlowed(monsterLabel);
				else
					interf.monsterAttacked(monsterLabel);
			}
				
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
				interf.monsterNotSlowed(monsterLabel);
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
	
	public boolean getIsSlower() {
		return isSlower;
	}
	
	public boolean getIsHealing() {
		return isHealing;
	}
	
	public Grid getGrid() {
		return monsterLabel;
	}
	
}
