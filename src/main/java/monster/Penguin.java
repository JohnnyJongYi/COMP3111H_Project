package monster;
// heals certain amount of HP every second

public class Penguin extends Monster 
{
	Penguin(int time, int mID, int type) 
	{
		super(time, mID, type);
		hp = 50;
		OriginalSpeed = 15;
		speed = OriginalSpeed;
		//Default HP, Speed : 50, 15
		stronger();
		maxHP = hp;
		printMonsterInfo();
	}
}
