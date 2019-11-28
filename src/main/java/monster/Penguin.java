package monster;
import sample.staticInterface;
// heals certain amount of HP every second

public class Penguin extends Monster 
{
	Penguin(int time, int mID, int type, staticInterface interf, MonsterData mdata) 
	{
		super(time, mID, type, interf, mdata);
		hp = 50;
		OriginalSpeed = 15;
		speed = OriginalSpeed;
		//Default HP, Speed : 50, 15
		stronger();
		maxHP = hp;
		monsterLabel = interf.spawnMonster(startxGrid, startyGrid, "Penguin", hp);
	}
}
