package monster;

import java.util.ArrayList;
import java.util.Collections;

import Coordinates.OutOfArenaException;

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
	
	int[][] NOA = new int[480][480]; // number of attack grid;
	
	
	protected void markTowersInNOA()
	{
		for(int i = 0 ; i<480 ; i++)
			for(int j = 0 ; j <480 ; j++)
			{
				if (!flagArray[i/40][j/40])
					NOA[i][j] = Integer.MAX_VALUE;
			}
	}
	

}
