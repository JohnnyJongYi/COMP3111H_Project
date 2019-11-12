package monster;

public class Main // needs to be integrated into Arena
{
	public static void main(String args[]) throws InterruptedException
	{
		MonsterGenerator test = new MonsterGenerator();
		
		while(true)
		{
			test.updateMonsterEachTimestamp();
			Thread.sleep(50);
		}
	}
}
