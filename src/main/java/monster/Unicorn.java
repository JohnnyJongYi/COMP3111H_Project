package monster;
import sample.staticInterface;
// more initial HP

public class Unicorn extends Monster 
{

	Unicorn(int time, int mID, int type, staticInterface interf/*, MonsterData mdata*/) 
	{
		super(time, mID, type, interf/*, mdata*/);
		hp = 70 ;
		OriginalSpeed = 15 ;
		speed = OriginalSpeed;
		//Default HP, Speed : 70, 15
		stronger();
		maxHP = hp;
		monsterLabel = interf.spawnMonster(startxGrid, startyGrid, "Unicorn", hp);
	}

}
