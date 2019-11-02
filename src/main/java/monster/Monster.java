package monster;

public abstract class Monster 
{
	
	private int monsterID; //index of monster in monster array 
	protected int hp;
	private int speed;
	private boolean alive;
	private int locationX;
	private int locationY;
	
	void die()
	{
		alive = false;
		monsterID = 0; 
	}
	
	void nextMove()
	{}
	
	void stronger(int time)
	{}

	
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
	
	
	
}
