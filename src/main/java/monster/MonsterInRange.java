package monster;

import java.util.ArrayList;
import java.lang.Math;

public class MonsterInRange 
{
	ArrayList<Monster> monsters;
	ArrayList<Monster> [][] rangeArray;
	
	MonsterInRange(ArrayList<Monster> mon)
	{
		monsters = mon;
		rangeArray = new ArrayList[480][480]; 
	}
	
	public void startSearch()
	{
		for(int m = 0 ; m<monsters.size(); m++)
		{
			int xm = monsters.get(m).getLoc().getX();
			int ym = monsters.get(m).getLoc().getY();
			
			for(int i = xm-25 ; i<= xm+25 ; i++)
			{
				if(i>=0 && i<480)
				for (int j = ym-25 ; j<= ym+25 ; j++)
				{
					if(j>=0 && j < 480)
					if(returnDistance(i,j,xm,ym) <=25)
					{monsterSearch(i,j);}
				}
			}
		}
	}
	
	
	
	public void monsterSearch(int x0, int y0)
	{
		ArrayList<Monster> monsterInRange = new ArrayList<Monster>();
		for(int r = x0-25 ; r<= x0+25 ; r++)
		{
			if(r>=0 && r<480)
			for (int c = y0-25 ; c<= y0+25 ; c++)
			{
				if(c>=0 && c<480)
				if(returnDistance(r,c,x0,y0) <=25)
				{
					isMonster(r,c, monsterInRange);
				}
			}
		}
		rangeArray[x0][y0] = monsterInRange;
	}
	
	public void isMonster(int x, int y, ArrayList<Monster> inRange)
	{
		for(int m = 0 ; m<monsters.size(); m++)
		{
			if(monsters.get(m).getLoc().isEqual(x,y))
				inRange.add(monsters.get(m));
		}
	}
	
	public static double returnDistance(int x1, int y1, int x2, int y2)
	{
		double x1d = x1;
		double x2d = x2;
		double y1d = y1;
		double y2d = y2;
		double distance = Math.sqrt((y2d - y1d) * (y2d - y1d) + (x2d - x1d) * (x2d - x1d));
		return distance;
	}
	
	public ArrayList<Monster>[][] getRangeArray()
	{
		return rangeArray;
	}
	
	
}