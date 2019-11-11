package Coordinates;

public class Location 
{
	private int x;
	private int y;
	
	Location(int xCoor, int yCoor)
	{
		x=xCoor;
		y=yCoor;
	}
	
	public int getX()
	{return x;}
	
	public int getY()
	{return y;}
	
	public Location getLocation()
	{return this;}
	
	public void update(int changeInX, int changeInY)
	{
		x=x+changeInX;
		y=y+changeInY;
	}
	
	public boolean isEqual(int xCoor, int yCoor)
	{
		if(xCoor == x && yCoor == y)
			return true;
		return false;
	}
}
