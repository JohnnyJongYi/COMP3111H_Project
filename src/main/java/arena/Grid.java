package arena;

import javafx.scene.control.Label;

public class Grid {

	private enum actorType {
			MONSTER,
			TOWER,
			EMPTY
	}
	
	private static final int MAX_MONSTER_NUM = 9600; // total possible number of monsters on a board at a time
	
	private int actorArray[] = new int[MAX_MONSTER_NUM];
	
	private actorType type = actorType.EMPTY;
	
	private Label label;
	
	public Grid(Label _label) {
		label = _label;
	}
	
	
}
