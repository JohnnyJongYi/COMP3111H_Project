package monster;
// Faster than default 
// take route with smallest expected damage
public class Fox extends Monster 
{

	Fox(int time, int mID, int type) 
	{
		super(time, mID, type);
		hp = 40;
		OriginalSpeed = 22;
		speed = OriginalSpeed;
		//Default HP, Speed : 40, 22;
		stronger();
		maxHP = hp;
		printMonsterInfo();
	}

	protected void calculatePath()
	{}
}
