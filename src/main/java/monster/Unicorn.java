package monster;
// more initial HP
public class Unicorn extends Monster 
{

	Unicorn(int globalTime, int mID, int type) 
	{
		super(globalTime, mID, type);
		hp = 70 ;
		speed = 80 ;
		//Default HP, Speed : 70, 80
		stronger();
		printMonsterInfo();
	}

}
