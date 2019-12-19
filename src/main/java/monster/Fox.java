package monster;
import sample.staticInterface;

import java.util.ArrayList;
import tower.TowerHandler;
import java.util.PriorityQueue;
import java.util.Comparator;

//Faster than default 
//take route with smallest expected damage
public class Fox extends Monster 
{

	Fox(int time, int mID, int type, staticInterface interf/*, MonsterData mdata*/) 
	{
		super(time, mID, type, interf/*, mdata*/);
		hp = 40;
		OriginalSpeed = 22;
		speed = OriginalSpeed;
		//Default HP, Speed : 40, 22;
		stronger();
		maxHP = hp;
		
		monsterLabel = interf.spawnMonster(startxGrid, startyGrid, "Fox", hp);
	}
	
	int[][] NOA = new int[480][480]; // number of attack grid;
	Vertex[][] foxMovesGrid= new Vertex[12][12];
	
/*	protected void foxMovesGridGenerator()
	{
		//Test
		int [][] NOATest = 
		{
				{5,5,5,5,5,5,5,5,5,5,1,1},
				{5,5,5,5,5,5,5,1,1,1,1,5},
				{5,5,5,5,5,5,5,1,5,5,5,5},
				{5,5,5,1,1,1,1,1,5,5,5,5},
				{5,5,5,1,5,5,5,5,5,5,5,5},
				{5,5,5,1,5,5,5,5,5,5,5,5},
				{5,1,1,1,5,5,5,5,5,5,5,5},
				{1,1,5,5,5,5,5,5,5,5,5,5},
				{1,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},
				{1,1,1,1,1,1,1,1,1,1,1,1},
				{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1},
				{1,1,1,1,1,1,1,1,1,1,1,1}
		};
		
		for(int row = 0 ; row < 12 ; row++)
			for(int col = 0 ; col < 12 ; col++)
			{
				foxMovesGrid[row][col] = new Vertex(col,row,NOATest[row][col],Integer.MAX_VALUE);
			}
		for(int row = 0 ; row < 12 ; row++)
			for(int col = 0 ; col < 12 ; col++)
				if(foxMovesGrid[row][col] != null)
					foxMovesGrid[row][col].setAdjacent();
		
	}*/
	
	private void foxMovesGridGenerator()
	{	
		NOA = TowerHandler.getNOA();
		for(int row = 0 ; row < 12 ; row++)
			for(int col = 0 ; col < 12 ; col++)
			{
				if(flagArray[row][col])
				{
					int sum = 0;
					for(int r = row*40 ; r < (row+1)*40 ; r++)
						for(int c = col*40 ; c < (col+1)*40 ; c++)
							sum+=NOA[c][r];
					
					foxMovesGrid[row][col] = new Vertex(col, row, sum, Integer.MAX_VALUE);	
				}
				else
					foxMovesGrid[row][col] = null;
				
			}
		
		for(int row = 0 ; row < 12 ; row++)
			for(int col = 0 ; col < 12 ; col++)
				if(foxMovesGrid[row][col] != null)
					foxMovesGrid[row][col].setAdjacent();
	}
	
	@Override
	protected void calculatePath(int grid)
	{
		foxMovesGridGenerator();
		
		for(int row = 0 ; row < 12 ; row++)
		{
			for(int col = 0 ; col < 12 ; col++)
				if(foxMovesGrid[row][col] != null)
				System.out.print(foxMovesGrid[row][col].value+"   ");
				else
					System.out.print("nullnull   ");

			System.out.println("");
		}
					
		int counterX = grid / 100;
		int counterY = grid % 100;
		Vertex source = new Vertex();
		
		for(int row = 0 ; row < 12 ; row++)
			for(int col = 0 ; col < 12 ; col++)
			{
				if(row == counterY && col == counterX)
					source = foxMovesGrid[row][col];
			}
		
		source.dist = 0; 
	
		//Dijkstra
		PriorityQueue<Vertex> Q = new PriorityQueue<Vertex>(11,new VertexComparator());
		
		for(int row = 0 ; row < 12 ; row++)
			for(int col = 0 ; col < 12 ; col++)
			{ 
				if(foxMovesGrid[row][col] != null)
					Q.add(foxMovesGrid[row][col]);
			}
			
		System.out.println("size "+ Q.size());
		
		while(Q.size()!=0)
		{
			Vertex counter = Q.poll();
	//		System.out.print((counter.x *100 + counter.y) + "||");
			
			for(int i = 0 ; i<counter.adjacent.size();i++)
			{
				Vertex child = counter.adjacent.get(i);
//				System.out.println("++child of " + (counter.x *100 + counter.y)+" is "+(child.x *100 + child.y));
				
				if(!child.processed)
				{
					int dist = child.dist;
					int estimate = counter.dist+(int)child.value;
					
					if(estimate < dist)
					{	
						child.dist = estimate;
						Q.remove(child);
						Q.add(child);
						child.prev = counter;
					}
				}
			}
			counter.processed = true;
		}
		
		
		Vertex pathTrace = foxMovesGrid[0][11];
		while(pathTrace != null)
		{
			int gridTrace = pathTrace.x * 100 +pathTrace.y;
			path.add(gridTrace);
			pathTrace= pathTrace.prev;
		}

	}
	
	
	class Vertex
	{
		protected int dist;
		protected float value;
		protected Vertex prev;
		protected ArrayList<Vertex> adjacent;
		protected int x;
		protected int y; 
		protected boolean processed;
		
		Vertex(){}

		Vertex(int x, int y, float val, int dist)
		{
			this.x = x;
			this.y = y;
			value = val;
			this.dist = dist;
			prev = null;
			adjacent = new ArrayList<Vertex>();
			processed = false;
		}
		
		void setAdjacent() //adjacency list
		{
			if(x+1 < 12 && foxMovesGrid[y][x+1] != null)
				adjacent.add(foxMovesGrid[y][x+1]);

			if(x-1 >= 0 && foxMovesGrid[y][x-1] != null)
				adjacent.add(foxMovesGrid[y][x-1]);
			
			if(y+1 < 12 && foxMovesGrid[y+1][x]!=null)
				adjacent.add(foxMovesGrid[y+1][x]);
			
			if(y-1 >= 0  && foxMovesGrid[y-1][x]!=null)
				adjacent.add(foxMovesGrid[y-1][x]);
				
		}
		


	}
	
	class VertexComparator implements Comparator<Vertex>
	{
		public int compare (Vertex s, Vertex l)
			{return s.dist - l.dist;}
	}


}

