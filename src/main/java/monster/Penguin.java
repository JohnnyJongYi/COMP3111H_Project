package monster;
import sample.staticInterface;
// heals certain amount of HP every second

public class Penguin extends Monster 
{
	Penguin(int time, int mID, int type, staticInterface interf) 
	{
		super(time, mID, type, interf);
		hp = 50;
		OriginalSpeed = 15;
		speed = OriginalSpeed;
		//Default HP, Speed : 50, 15
		stronger();
		maxHP = hp;
		monsterLabel = interf.spawnMonster(startx, starty, "Penguin", hp);
	}
}
