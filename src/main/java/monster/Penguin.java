package monster;
// heals certain amount of HP every second

public class Penguin extends Monster 
{
	
	Penguin(int time, int mID, int type) 
	{
		super(time, mID, type);
		hp = 50;
		speed = 70;
		//Default HP, Speed : 50, 70
		stronger();
		maxHP = hp;
		printMonsterInfo();
	}

}
