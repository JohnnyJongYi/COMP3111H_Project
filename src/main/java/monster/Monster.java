package monster;

public abstract class Monster 
{
	protected int monsterType;
	protected int monsterID; //index of monster in monster array 
	protected int time; // number of every 10 seconds past since launch
	protected int hp;
	protected int speed;
	protected boolean alive;
	protected int locationX;
	protected int locationY;
	protected int distanceToEndpoint;
	
	Monster(int globalTime, int mID, int type)
	{  
		monsterType = type;
		time = globalTime;
		
		// set hp and speed according to time elapsed using globalTime
		
		locationX = 0;
		locationY = 0;
		monsterID = mID;
		alive = true;
	}
	
	protected void stronger()
	{
		if(time != 0 && time % 20 == 0)
		{
			//Every 20 seconds, new monsters will have extra 5 HP and extra 10 speed
			hp = hp + 5;
			speed = speed + 10;
		}
	}
	
	
	void die()
	{
		alive = false;
		monsterID = 0; 
	}
	
	void nextMove()
	{}
	
	public int getdistanceToEndpoint() {
		return distanceToEndpoint;
	}
	
	protected void setdistanceToEndpoint(int distance) {
		this.distanceToEndpoint = distance;
	}
	
	
	public int getMonsterID() {
		return monsterID;
	}
	public void setMonsterID(int monsterID) {
		this.monsterID = monsterID;
	}
	
	

	public int getHp() {
		return hp;
	}

	protected void setHp(int hp) {
		this.hp = hp;
	}

	public int getSpeed() {
		return speed;
	}

	protected void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isAlive() {
		return alive;
	}

	protected void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	
	

	public int getLocationX() {
		return locationX;
	}

	protected void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	protected void setLocationY(int locationY) {
		this.locationY = locationY;
	}
	
	public void takedamage(int towerType, int damage)
	{
		//reduce hp by certain amount according to tower type
		if (hp <= 0)
			die();
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
		System.out.println("Location : (" + locationX +", "+locationY+")");
		System.out.println("-------------------------------------------");
	}
	
}
