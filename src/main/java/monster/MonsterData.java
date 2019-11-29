package monster;
import java.io.Serializable;
//import javax.persistence.*;

//@Entity
public class MonsterData 
{
	String monsterType;
	boolean alive;
	int monsterID;
	
	MonsterData(int type, int ID)
	{
		switch(type)
		{
			case 1 : 
				monsterType = "Unicorn";
				break;
			case 2 : 
				monsterType = "Penguin";
				break;
			case 3 : 
				monsterType = "Fox";
				break;		
		}
		
		monsterID = ID;
		alive = true;
	}
	
	public int getID()
	{return monsterID;}
	
	public String getType()
	{return monsterType;}
	
	public boolean getStatus()
	{return alive;}
	
	protected void dead()
	{
		alive = false;
	}
}
