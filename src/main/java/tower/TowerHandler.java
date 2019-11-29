package tower;

import java.util.ArrayList;

import sample.Grid;
import sample.staticInterface;

public class TowerHandler {
	public static final int BASICPOWER = 10;
	public static final int ICEPOWER = 0;
	public static final int CATAPULTPOWER = 10;
	public static final int LASERPOWER = 30;
	
	protected static ArrayList<Tower> towerArray = new ArrayList<Tower>();
	protected static Tower[][] findTowerGrid =  new Tower[12][12];
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
		if (!interf.changeMoney(-20)) { // not enough gold
			interf.callAlert("Failed to build tower", "You have not enough gold");
			return false;
		}
		
		if (ART[x][y]) { // cannot build on articulation grid
			interf.callAlert("Failed to build tower", "You can't block monster to the End");
			return false;
		}
		
		System.out.println("Building tower at (" + x + ", " + y + ")");
		
		int test = towerArray.size();
		
		Tower tower = null;
		switch(type) {
			case 1 : 
				tower = new BasicTower(BASICPOWER, x, y, label, interf);
				break;
			case 2 :
				tower = new IceTower(ICEPOWER, x, y, label, interf);
				break;
			case 3 :
				tower = new Catapult(CATAPULTPOWER, x, y, label, interf);
				catapultCount++;
				break;
			case 4 :
				tower = new LaserTower(LASERPOWER, x, y, label, interf);
				break;
		}
		tower.printTowerInfo();
		
		towerArray.add(tower);
		findTowerGrid[x][y] = tower;
		
		towerGrid[x][y] = true;
		System.out.println("Tower Type: " + tower.getTowerType());
		setNOA(tower, 1);
		newTowerBuilt = true;
		
		calculateART();
		
		assert towerArray.size() == test + 1 : "Failed to build"; // Test build
		
		return true;
	}
	
	protected static void calculateART() {
		ART = new boolean[12][12]; // articulation prep
		flag = new boolean[12][12];
		time = 0;
		d = new int[12][12];
		low = new int[12][12];
		pred = new int[12][12][2];
		
		for (int i = 0; i < 11; i++) for (int j = 0; j < 11; j++) {
			pred[i][j][0] = -1;
		}

		articulation(0, 11);
		ART[0][11] = ART[11][0] = true; // cannot build on start & end grid
		
		printART();
	}
	
	protected static void articulation(int v_x, int v_y) {
		flag[v_x][v_y] = true;
		time++;
		d[v_x][v_y] = time;
		low[v_x][v_y] = d[v_x][v_y];
		
		for (int w = 0; w < 4; w++) {
			int w_x = v_x + n_x[w];
			int w_y = v_y + n_y[w];
			
			if (w_x < 0 || w_x > 11 || w_y < 0 || w_y > 11 || towerGrid[w_x][w_y]) continue; // boundary check or if there is a tower
			
			if (!flag[w_x][w_y]) {
				pred[w_x][w_y][0] = v_x;
				pred[w_x][w_y][1] = v_y;
				articulation(w_x, w_y);
				if (low[w_x][w_y] >= d[v_x][v_y]) {
					ART[v_x][v_y] = true;
					System.out.println("Articulation point on (" + v_x + ", " + v_y + ")");
				}
				low[v_x][v_y] = Math.min(low[v_x][v_y], low[w_x][w_y]);
			}
			else if (w_x != pred[v_x][v_y][0] || w_y != pred[v_x][v_y][1])
				low[v_x][v_y] = Math.min(low[v_x][v_y], d[w_x][w_y]);
		}
	}
	
	public static void printART() {
		System.out.println();
		System.out.println("Tower Array--->");
		for (Tower t: towerArray) {
			t.printTowerInfo();
		}
		System.out.println("Tower Array END--->");
		System.out.println();
		
		System.out.println("Tower grid:");
		for (int y = 0; y < 12; y++) {
			for (int x = 0; x < 12; x++) {
				if (towerGrid[x][y]) System.out.print("1 ");
				else  System.out.print("0 ");
			}
			System.out.println();
		}
		System.out.println("---------------------------------------------");
		
		System.out.println("Articulation grid:");
		for (int y = 0; y < 12; y++) {
			for (int x = 0; x < 12; x++) {
				if (ART[x][y]) System.out.print("1 ");
				else  System.out.print("0 ");
			}
			System.out.println();
		}
		System.out.println("---------------------------------------------");
		
		System.out.println("NOA grid:");
		for (int y = 0; y < 479; y += 10) {
			for (int x = 0; x < 479; x += 10) {
				System.out.print(numberOfAttack[x][y] + " ");
			}
			System.out.println();
		}
		System.out.println("---------------------------------------------");
	}
	
	protected static void setNOA(Tower tower, int a) {
		if (tower.getTowerType() == 4) return;
		
		System.out.println("Setting NOA " + a + " by " + tower.getTowerType());
		int test = numberOfAttack[tower.getLocationX()][tower.getLocationY()];
		
		int locationX = tower.getMidX();
		int locationY = tower.getMidY();
		
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
		
		if (tower.getTowerType() == 3) assert numberOfAttack[tower.getLocationX()][tower.getLocationY()] == test : "Failed to setNOA"; // Unit testing
		else assert numberOfAttack[tower.getLocationX()][tower.getLocationY()] == test + a : "Failed to setNOA";
	}
	
	public static void shootAll() {
//		System.out.println("All " + towerArray.size() + " shoot.....");
		for (Tower tower : towerArray) tower.shoot(interf);
	}
	
	public static boolean upgrade(int x, int y) {
		if (!interf.changeMoney(-20)) { // not enough gold
			interf.callAlert("Failed to upgrade tower", "You have not enough gold");
			return false;
		}
		
		findTowerGrid[x][y].upgrade();
		return true;
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
		
		for (int x = 0; x < 12; x++) for (int y = 0; y < 12; y++) {
			if (towerGrid[x][y]) invertedFlag[y][x] = false;
			else invertedFlag[y][x] = true;
		}
		
		assert invertedFlag[3][7] == towerGrid[7][3] : "Failed to convert to invertFlag"; // Unit testing if the conversion is correct
		
		return invertedFlag;
	}
	
	public static boolean catapultFound() {
		if (catapultCount == 0) return false;
		return true;
	}
	
	public static void destroy(int x, int y) {
		System.out.println("Deleting tower----------------------------------------------------------------->");
		
		int test = towerArray.size();
		
		Tower tower = findTowerGrid[x][y];
		if (tower.getTowerType() == 3) catapultCount--;
		
		towerGrid[x][y] = false;
		setNOA(tower, -1);
		newTowerBuilt = true;
		
		towerArray.remove(tower);
		findTowerGrid[x][y] = null;
		
		calculateART();
		
		assert towerArray.size() == test - 1 : "Failed to destroy"; // Test destroy
	}
}