package tower;

import java.util.ArrayList;

import sample.Grid;
import sample.staticInterface;

public class TowerHandler {
	protected static ArrayList<Tower> towerArray = new ArrayList<Tower>();
	protected static int num;
	protected static int[][] numberOfAttack = new int[480][480];
	protected static boolean[][] towerGrid =  new boolean[12][12];
	protected static boolean newTowerBuilt;
	protected static int catapultCount;
	protected static boolean[][] ART = new boolean[12][12]; // articulation prep
	protected static boolean[][] flag = new boolean[12][12];
	protected static int time;
	protected static int[][] d = new int[12][12];
	protected static int[][] low = new int[12][12];
	protected static int[][][] pred = new int[12][12][2];
	protected static int n_x[] = {0, 1, 0, -1};
	protected static int n_y[] = {-1, 0, 1, 0};
	protected static staticInterface interf;
	
	public TowerHandler(staticInterface f) {
		interf = f;
	}
	
	public static boolean build(int type, int x, int y, Grid label) {
		if (x == 0 && y == 11 || x == 11 && y == 0 || ART[x][y]) return false; // cannot build on start & end grid & articulation grid
		
		Tower tower = null;
		switch(type) {
			case 1 : 
				tower = new BasicTower(x, y, label, interf);
				break;
			case 2 :
				tower = new IceTower(x, y, label, interf);
				break;
			case 3 :
				tower = new Catapult(x, y, label, interf);
				catapultCount++;
				break;
			case 4 :
				tower = new LaserTower(x, y, label, interf);
				break;
		}
		
		towerArray.add(tower);
		setNOA(tower, x, y, 1);
		
		num++;
		newTowerBuilt = true;
		towerGrid[x][y] = true;
		
		ART = new boolean[12][12]; // articulation prep
		flag = new boolean[12][12];
		time = 0;
		d = new int[12][12];
		low = new int[12][12];
		pred = new int[12][12][2];
		for (int i = 0; i < 11; i++) for (int j = 0; j < 11; j++) pred[i][j][0] = -1;

		articulation(0, 11);
		
		return true;
	}
	
	protected static void articulation(int v_x, int v_y) {
		flag[v_x][v_y] = true;
		d[v_x][v_y] = ++time;
		low[v_x][v_y] = d[v_x][v_y];
		
		for (int w = 0; w < 4; w++) {
			int w_x = v_x + n_x[w];
			int w_y = v_y + n_y[w];
			if (w_x < 0 || w_x > 11 || w_y < 0 || w_y > 11) continue; // boundary check
			if (towerGrid[w_x][w_y]) continue; // skip if there is a tower on w
			
			if (!flag[w_x][w_y]) {
				pred[w_x][w_y][0] = v_x;
				pred[w_x][w_y][1] = v_y;
				articulation(w_x, w_y);
				if (low[w_x][w_y] >= d[v_x][v_y]) ART[v_x][v_y] = true;
				low[v_x][v_y] = Math.min(low[v_x][v_y], low[w_x][w_y]);
			}
			else if (w_x != pred[v_x][v_y][0] || w_y != pred[v_x][v_y][1])
				low[v_x][v_y] = Math.min(low[v_x][v_y], d[w_x][w_y]);
		}
	}
	
	protected static void setNOA(Tower tower, int locationX, int locationY, int a) {
		if (tower.getTowerType() == 4) return;
		
		int maxRange = tower.getMaxRange();
		int minRange = tower.getMinRange();
		int maxRange2 = maxRange * maxRange;
		int minRange2 = minRange * minRange;
		
		int x_low = Math.max(0, locationX - maxRange);
		int x_high = Math.min(479, locationX + maxRange);
		
		for (int x = x_low; x <= x_high; x++) {
			int dx = Math.abs(locationX - x);
			int max_dy = (int) Math.floor(Math.sqrt(maxRange2 - dx * dx));
			int y_low = Math.max(0, locationY - max_dy);
			int y_high = Math.min(479, locationY + max_dy);
			
			for (int y = y_low; y <= y_high; y++) { // for all pixel in range
				int dy = Math.abs(locationY - y);
				if (dx * dx + dy * dy >= minRange2) numberOfAttack[x][y] += a;
			}
		}
	}
	
	public static void shootAll() {
		for (Tower tower : towerArray) tower.shoot(interf);
	}
	
	public static ArrayList<Tower> getTowerArray() {
		return towerArray;
	}
	
	public static int[][] getNOA() {
		return numberOfAttack;
	}
	
	public static boolean newTowerBuilt() {
		return newTowerBuilt;
	}
	
	public static void resetNewTowerBuilt() {
		newTowerBuilt = false;
	}
	
	public static boolean[][] towerGrid() {
		boolean[][] invertedFlag = new boolean[12][12];
		
		for (int x = 0; x < 12; x++) for (int y = 0; y < 12; y++)
			if (!towerGrid[x][y]) invertedFlag[x][y] = true;
		
		return invertedFlag;
	}
	
	public static boolean catapultFound() {
		if (catapultCount == 0) return false;
		return true;
	}
	
	public static void destroy(Tower tower, int x, int y) {
		setNOA(tower, x, y, -1);
		if (tower.getTowerType() == 3) catapultCount--;
		towerArray.remove(tower);
		towerGrid[x][y] = false;
		newTowerBuilt = true;
		articulation(0, 11);
	}
}