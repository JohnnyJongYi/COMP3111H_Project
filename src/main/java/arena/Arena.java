package src.main.java.arena;

import arena.Grid;
import javafx.scene.control.Label;

public class Arena {
	
	public enum Mode {
		SIMULATE,
		PLAY
	}
	
	private static final int ARENA_WIDTH = 480;
    private static final int ARENA_HEIGHT = 480;
    private static final int GRID_WIDTH = 40;
    private static final int GRID_HEIGHT = 40;
    private static final int MAX_H_NUM_GRID = 12;
    private static final int MAX_V_NUM_GRID = 12;
    private static final int MAX_TOWER_NUM = 144; // total number of grids
    private static final int MAX_MONSTER_NUM = 9600; // total possible number of monsters on a board at a time
    
	private Grid grids[][] = new Grid[MAX_V_NUM_GRID][MAX_H_NUM_GRID]; //the grids on arena
	private int time;
	private Mode mode;
	private int resources;
	private int towers[][] = new int[MAX_V_NUM_GRID][MAX_H_NUM_GRID];
	private int monsters[] = new int[MAX_MONSTER_NUM];
	
	public Arena() {
		
	}
	public boolean canBuildTower() {
		return true;
	}
	
	public void buildTower() {
	
	}
	

	public boolean hasLost() {
		return false;
	}
	
	public void updateTowerArray() {

	}
	public void updateMonsterArray() {

	}
	public void updateArena() {

	}
	
	// spawn monster according to type 
	public void monsterSpawn()

	

}
