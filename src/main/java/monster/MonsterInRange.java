package monster;

import java.util.ArrayList;
import java.lang.Math;

public class MonsterInRange 
{
	ArrayList<Monster> monsters;
	int [][] rangeArray;
	
	MonsterInRange(ArrayList<Monster> mon)
	{
		monsters = mon;
	}
	
	public void startSearch()
	{
		for(int m = 0 ; m<monsters.size(); m++)
		{
			int xm = monsters.get(m).getLoc().getX();
			int ym = monsters.get(m).getLoc().getY();
			
			for(int i = xm-25 ; i<= xm+25 ; i++)
			{
				if(i>=0)
				for (int j = ym-25 ; j<= ym+25 ; j++)
				{
					if(j>=0)
					if(returnDistance(i,j,xm,ym) <=25)
					{monsterSearch(i,j);}
				}
			}
		}
	}
	public void monsterSearch(int x0, int y0)
	{
		int count = 0;
		for(int r = x0-25 ; r<= x0+25 ; r++)
		{
			if(r>=0)
			for (int c = y0-25 ; c<= y0+25 ; c++)
			{
				if(c>=0)
				if(returnDistance(r,c,x0,y0) <=25)
				{
					if(isMonster(r,c))
						count++;
				}
			}
		}
		rangeArray[x0][y0] = count;
	}
	
	public boolean isMonster(int x, int y)
	{
		for(int m = 0 ; m<monsters.size(); m++)
		{
			if(monsters.get(m).getLoc().isEqual(x,y))
			return true;
		}
		return false;
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
	
	public int[][] getRangeArray()
	{
		rangeArray = new int[480][480];
		startSearch();
		return rangeArray;
	}
	
	
}