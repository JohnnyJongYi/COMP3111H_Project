package monster;
import java.util.ArrayList;

public class MonsterGenerator
{
	protected ArrayList<Monster> monsterArray;
	protected int num;
	protected int[][] MonstersInRange;
	static protected int globalTime;
	
	MonsterGenerator() throws InterruptedException
	{
		monsterArray = new ArrayList<Monster>();
		MonstersInRange = new int[480][480];
		num = 0;
		globalTime = 0;
		generateMonster();
		
		
	}
	void generateMonster() throws InterruptedException
	{
		while(true)
		{
			Thread.sleep(1000);
			generate();
			
		}
	}
	
	public int[][] getMonstersInRange() {
		return MonstersInRange;
	}
	public void setMonstersInRange(int[][] monstersInRange) {
		MonstersInRange = monstersInRange;
	}
	
	public void generate()
	{
		
		if(globalTime % 10 == 0) //generate Monster every 10 seconds
		{
			int type = (int)(Math.random() * 3 + 1);
			// Randomly choose monster type
			switch(type)
			{
				case 1 : 
					monsterArray.add(new Unicorn(globalTime,num,type));
					num++;
					break;
				case 2 : 
					monsterArray.add(new Penguin(globalTime,num,type));
					num++;
					break;
				case 3 : 
					monsterArray.add(new Fox(globalTime,num,type));
					num++;
					break;		
			}
		}
		globalTime ++;
	}
}	



