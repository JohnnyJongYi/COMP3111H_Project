package monster;
import Coordinates.Location;

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

	
	Monster(int timestamp, int mID, int type)
	{  
		monsterType = type;
		time = timestamp;
		loc = new Location(0,480);
		monsterID = mID;
		alive = true;
		isSlower = false;
		slowerStartTime = Integer.MAX_VALUE;
		slowerDuration = 0;
		updateDistanceToEnd();
	}
	
	protected void stronger()
	{
		int quotient = time / 40;
		hp = hp + (10*quotient);
	}
	
	void die()
	{
		alive = false;
	}
	
	void nextMove()
	{
		
		//updatedistancetoend
		//if penguin heal hp every time
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

	public double getDistanceToEndpoint() {
		return distanceToEndpoint;
	}

	
}
