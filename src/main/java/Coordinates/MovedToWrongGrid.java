package Coordinates;

public class MovedToWrongGrid extends Exception
{
	public MovedToWrongGrid()
	{
		super("One of the monster moved to the wrong grid");
	}
}
