package monster;
// more initial HP

public class Unicorn extends Monster 
{

	Unicorn(int time, int mID, int type) 
	{
		super(time, mID, type);
		hp = 70 ;
		speed = 70 ;
		//Default HP, Speed : 70, 70
		stronger();
		maxHP = hp;
		printMonsterInfo();
	}

}
