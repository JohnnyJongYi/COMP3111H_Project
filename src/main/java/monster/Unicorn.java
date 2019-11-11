package monster;
// more initial HP

public class Unicorn extends Monster 
{

	Unicorn(int time, int mID, int type) 
	{
		super(time, mID, type);
		hp = 70 ;
		OriginalSpeed = 15 ;
		speed = OriginalSpeed;
		//Default HP, Speed : 70, 15
		stronger();
		maxHP = hp;
		printMonsterInfo();
	}

}
