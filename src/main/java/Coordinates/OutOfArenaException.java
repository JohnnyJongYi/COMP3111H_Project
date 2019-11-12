package Coordinates;

public class OutOfArenaException extends Exception
{

	public OutOfArenaException()
	{
		super("Actor has moved outside of the arena");
	}
}
