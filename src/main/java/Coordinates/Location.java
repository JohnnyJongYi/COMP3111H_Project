package Coordinates;

public class Location 
{
	private int x;
	private int y;
	
	public Location(int xCoor, int yCoor)
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
	
	public void update(int changeInX, int changeInY) throws OutOfArenaException
	{
		x=x+changeInX;
		y=y+changeInY;
		
		if(x<0 || x>=480 || y<0 || y>=480)
		{
			OutOfArenaException except = new OutOfArenaException();
			throw except;
		}
	}
	
	public boolean isEqual(int xCoor, int yCoor)
	{
		if(xCoor == x && yCoor == y)
			return true;
		return false;
	}
}

class OutOfArenaException extends Exception
{

	public OutOfArenaException()
	{
		super("Actor has moved outside of the arena");
	}
}
