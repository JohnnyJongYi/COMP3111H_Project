package monster;
// Faster than default 
// take route with smallest expected damage
public class Fox extends Monster 
{

	Fox(int time, int mID, int type) 
	{
		super(time, mID, type);
		hp = 40;
		speed = 100;
		//Default HP, Speed : 40, 100
		stronger();
		maxHP = hp;
		printMonsterInfo();
	}

	void nextMove() 
	{
		//update distance to end
	}
}
