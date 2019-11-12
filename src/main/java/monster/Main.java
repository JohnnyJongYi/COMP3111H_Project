package monster;

import Coordinates.OutOfArenaException;

public class Main // needs to be integrated into Arena
{
	public static void main(String args[]) throws InterruptedException, OutOfArenaException, MovedToWrongGrid
	{
		MonsterGenerator test = new MonsterGenerator();
		
		while(true)
		{
			test.updateMonsterEachTimestamp();
			Thread.sleep(50);
		}
	}
}
