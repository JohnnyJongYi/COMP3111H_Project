package tower;

public abstract class Tower {

	protected int towerType;
	protected int towerID; //index of tower in tower array 
	protected int level;
	protected int locationX;
	protected int locationY;
	
	public Tower(int type, int ID, int x, int y)
	{
		towerType = type;
		towerID = ID;
		level = 0;
		locationX = x;
		locationY = y;
	}
	
	public void upgrade()
	{
		level += 1;
	}
	
	public void shoot() {}
	
	public int getTowerType()
	{
		return towerType;
	}
	
	public int getTowerID()
	{
		return towerID;
	}
	
	public void setTowerID(int ID)
	{
		towerID = ID;
	}
	
	public int getLocationX()
	{
		return locationX;
	}
	
	public int getLocationY()
	{
		return locationY;
	}
	
	public void printTowerInfo()
	{
		System.out.println("tower type: " + towerType);
		System.out.println("tower ID : " + towerID);
		System.out.println("level : "+ level);
		System.out.println("Location : (" + locationX +", "+locationY+")");
		System.out.println("-------------------------------------------");
	}
}