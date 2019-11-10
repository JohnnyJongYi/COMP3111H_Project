package monster;
// heals certain amount of HP every second
public class Penguin extends Monster 
{
	private int maxHP;
	
	Penguin(int globalTime, int mID, int type) 
	{
		super(globalTime, mID, type);
		hp = 50;
		speed = 70;
		//Default HP, Speed : 50, 70
		stronger();
		maxHP = hp;
		printMonsterInfo();
	}

	void heal() throws InterruptedException
	{ 
		// heals hp by 10 every 5 seconds
		while(alive)
		{
			Thread.sleep(5000);
			if(hp+10>=maxHP)
				hp = maxHP;
			else
				hp = hp + 10;		
		}	
	}
}
