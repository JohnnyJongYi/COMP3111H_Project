package monster;
// Faster than default 
// take route with smallest expected damage
public class Fox extends Monster 
{

	Fox(int globalTime, int mID, int type) 
	{
		super(globalTime, mID, type);
		hp = 40;
		speed = 100;
		//Default HP, Speed : 40, 100
		stronger();
		printMonsterInfo();
	}

	void nextMove() 
	{}
}
