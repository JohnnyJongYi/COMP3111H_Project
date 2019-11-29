package sample;

import javafx.scene.Node;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javafx.concurrent.Task;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import sample.Grid;
import tower.TowerHandler;
import org.springframework.stereotype.Component;

@Component
public class MyController implements staticInterface  {
	@FXML
	private Button buttonNextFrame;

	@FXML
	private Button buttonSimulate;

	@FXML
	private Button buttonPlay;
	@FXML
	private Button buttonUp;

	@FXML
	private Button buttonDown;

	@FXML
	private Button buttonAtt;

	@FXML
	private Button buttonSlow;

	@FXML
	private Button buttonAttandSlow;

	@FXML
	private AnchorPane paneArena;

	@FXML
	private Label labelBasicTower;

	@FXML
	private Label labelIceTower;

	@FXML
	private Label labelCatapult;

	@FXML
	private Label labelLaserTower;

	@FXML
	private ImageView imageBasicTower;

	@FXML
	private ImageView imageIceTower;

	@FXML
	private ImageView imageCatapult;

	@FXML
	private ImageView imageLaserTower;

	@FXML
	private Label moneyLabel;

	private static final int GRID_WIDTH = 40;
	private static final int GRID_HEIGHT = 40;
	private static final int MAX_H_NUM_GRID = 12;
	private static final int MAX_V_NUM_GRID = 12;
	private static final int MONSTER_SIZE = 30;

	private Grid grids[][] = new Grid[MAX_V_NUM_GRID][MAX_H_NUM_GRID]; // the grids on arena

	public enum towerType {
		BASIC, ICE, CATAPULT, LASER
	};

	private towerType draggingTower;

	private int money = 10000;

	private void callAlert(String title, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.showAndWait();
	}

	/**
	 * A function that create the Arena
	 */
	@FXML
	public void createArena() {
		if (grids[0][0] != null)
			return; // created already
		for (int i = 0; i < MAX_V_NUM_GRID; i++)
			for (int j = 0; j < MAX_H_NUM_GRID; j++) {
				Grid newLabel = new Grid();
				if (i == 0 && j == 11) {
					newLabel.setBackground(
							new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
					Image image = new Image(getClass().getResourceAsStream("/endzone.png"));
					ImageView imageView = new ImageView(image);
					imageView.setFitHeight(GRID_WIDTH);
					imageView.setFitWidth(GRID_HEIGHT);
					newLabel.setGraphic(imageView);
				} else
					newLabel.setBackground(
							new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				newLabel.setLayoutX(j * GRID_WIDTH);
				newLabel.setLayoutY(i * GRID_HEIGHT);
				newLabel.setXY(j * GRID_WIDTH, i * GRID_HEIGHT);
				newLabel.setMinWidth(GRID_WIDTH);
				newLabel.setMaxWidth(GRID_WIDTH);
				newLabel.setMinHeight(GRID_HEIGHT);
				newLabel.setMaxHeight(GRID_HEIGHT);
				newLabel.setStyle("-fx-border-color: black;");
				newLabel.setText(String.valueOf(Math.round(newLabel.getLayoutX())) + " "
						+ String.valueOf(Math.round(newLabel.getLayoutY())));
//				newLabel.setText(String.valueOf(i)+" " + String.valueOf(j));
				setDragAndDrop(newLabel);
				grids[i][j] = newLabel;

//				System.out.println(grids[i][j].getLayoutY());
				paneArena.getChildren().addAll(newLabel);
			}
//        paneArena.getChildren().addAll(laser);

		moneyLabel.setText(String.valueOf(money));

	}

	private Grid label1;
	private Label laser;

	@FXML
	public void rotate(ActionEvent event) {
//		 laser.getTransforms().add(new Rotate(20,laser.getLayoutX(),laser.getLayoutY()));//,0.0,Rotate.Z_AXIS
		laser.setRotate(laser.getRotate() + 20);
		System.out.println(laser.getLayoutX());
		System.out.println(laser.getLayoutY());
//		 System.out.println(laser.get);

	}
//

	class DragEventHandler implements EventHandler<MouseEvent> {
		private ImageView source;

		public DragEventHandler(ImageView e) {
			source = e;
		}

		@Override
		public void handle(MouseEvent event) {
			Dragboard db = source.startDragAndDrop(TransferMode.ANY);

			ClipboardContent content = new ClipboardContent();
			content.putImage(source.getImage());
			db.setContent(content);
			if (source == imageBasicTower)
				draggingTower = towerType.BASIC;
			else if (source == imageIceTower)
				draggingTower = towerType.ICE;
			else if (source == imageCatapult)
				draggingTower = towerType.CATAPULT;
			else if (source == imageLaserTower)
				draggingTower = towerType.LASER;

			event.consume();
		}
	}
	
	public void giveMoneyForKill()
	{
		money += 5;
		moneyLabel.setText(String.valueOf(money));
		
	}

	private boolean updateInfo(Grid target) {
		String message = "";
		switch (draggingTower) {
		case BASIC:
			message = message + "Basic Tower";
			if (money >= 10) {
				money -= 10;
				moneyLabel.setText(String.valueOf(money));
			} else
				return false;
			break;
		case ICE:
			message = message + "Ice Tower";
			if (money >= 20) {
				money -= 20;
				moneyLabel.setText(String.valueOf(money));
			} else
				return false;
			break;
		case CATAPULT:
			message = message + "Catapult Tower";
			if (money >= 30) {
				money -= 30;
				moneyLabel.setText(String.valueOf(money));
			} else
				return false;
			break;
		case LASER:
			message = message + "Laser Tower";
			if (money >= 40) {
				money -= 40;
				moneyLabel.setText(String.valueOf(money));
			} else
				return false;
			break;
		default:
			break;
		}
		target.setName(message);
		return true;
	}
	

	public void upgradeTower(Grid tower, double newDamage, int newLevel) {


		String message = tower.getName() + " " + String.valueOf(newLevel);
		double range1 = 0;
		double range2 = 0;
		int buildingCost = 0;
		double damage = 0;
		switch(tower.getName()) {
		case("Basic Tower"):
			range1 = 0;
			range2 = 65;
			buildingCost = 10;
			damage = 40;
			break;
		case("Ice Tower"):
			range1 = 0;
			range2 = 65;
			buildingCost = 20;
			damage = 0;
			break;
		case("Catapult Tower"):
			range1 = 50;
			range2 = 150;
			buildingCost = 30;
			damage = 30;
			break;
		case("Laser Tower"):
			range1 = 0;
			range2 = 1000;
			buildingCost = 40;
			damage = 50;
			break;
		default:
			break;

		};
		if(newLevel!=1)
			damage = newDamage;
		message = message + "\nPower : " + String.valueOf(damage);
		message = message + "\nRange : " + String.valueOf(range1) + " - " + String.valueOf(range2);
		message = message + "\nBuilding Cost : " + String.valueOf(buildingCost);
		tower.infoToolTip.setText(message);
	}
	private void buildTower(Grid target, Image image) {

		if (updateInfo(target)) {
			int type;
			double radii;
			boolean catapult = false;
			switch (draggingTower) {
			case BASIC:
				type = 1;
				radii = 65;
				break;
			case ICE:
				type =2;
				radii = 65;
				break;
			case CATAPULT:
				type =3;
				radii = 150;
				catapult = true;
				break;
			case LASER:
				type =4;
				radii = 1000;
				break;
			default:
				type = 1;
				radii = 50;
				break;
			}

			if (!TowerHandler.build(type, (int)target.getX()/40 , (int)target.getY()/40,target))
			{
				callAlert("Not Allowed","You cannot block the monsters from reaching the end zone!");
				return;
			}
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(GRID_WIDTH);
			imageView.setFitWidth(GRID_HEIGHT);
			target.setGraphic(imageView);
			target.infoToolTip = new Tooltip();
			target.setTooltip(target.infoToolTip);

			// Do the tooltip update
			upgradeTower(target,0,1);
			boolean newCatapult = catapult;
			target.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					mouseHover(target, event,radii,newCatapult);
				}
			});
			target.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					mouseExit(target);
				}
			});
			target.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					actionOnTower(target);
				}
			});
		} else
			callAlert("Insufficient resources","You don't have enough resources to build this!");

//		Tower.build(target.getX(),target.getY(),draggingTower)

	}

	private void mouseHover(Grid target, MouseEvent event, double radii, boolean catapult) {

		Circle circle = new Circle(target.getX() + GRID_WIDTH / 2, target.getY() + GRID_HEIGHT / 2, radii);
		Shape rect;
		if(catapult)
			rect = new Circle(target.getX() + GRID_WIDTH / 2, target.getY() + GRID_HEIGHT / 2, 50.0f);
		else
			rect = new Rectangle(target.getX(), target.getY(), GRID_WIDTH, GRID_HEIGHT);
		target.radius = Shape.subtract(circle, rect);
		target.radius.setFill(Color.RED);
		target.radius.setOpacity(0.5);
		paneArena.getChildren().addAll(target.radius);
		target.infoToolTip.show(target, event.getScreenX(), event.getScreenY() + 15);
//
	}

	private void mouseExit(Grid target) {
		paneArena.getChildren().remove(target.radius);
		target.infoToolTip.hide();
	}

	private void upgradeTower(Grid tower) {
//		if(money)
		// need to implement upgrade tower logic
		System.out.println("Not enough resource to upgrade tower.");
		tower.tower.upgrade();
	}

	private void destroyTower(Grid tower) {
//		tower.setGraphic(null);

		Grid newLabel = new Grid();
		newLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		int j = (int)tower.getX()/GRID_WIDTH;
		int i = (int)tower.getY()/GRID_HEIGHT;
		newLabel.setLayoutX(j * GRID_WIDTH);
		newLabel.setLayoutY(i * GRID_HEIGHT);
		newLabel.setXY(j * GRID_WIDTH, i * GRID_HEIGHT);
		newLabel.setMinWidth(GRID_WIDTH);
		newLabel.setMaxWidth(GRID_WIDTH);
		newLabel.setMinHeight(GRID_HEIGHT);
		newLabel.setMaxHeight(GRID_HEIGHT);
		newLabel.setStyle("-fx-border-color: black;");
		newLabel.setText(String.valueOf(Math.round(newLabel.getLayoutX())) + " "
				+ String.valueOf(Math.round(newLabel.getLayoutY())));
		setDragAndDrop(newLabel);
		grids[i][j] = newLabel;

		paneArena.getChildren().remove(tower);
		paneArena.getChildren().addAll(newLabel);
		TowerHandler.destroy(j,i);
	}

	private void actionOnTower(Grid tower) {
		ButtonType upgradeButton = new ButtonType("Upgrade");
		ButtonType destroyButton = new ButtonType("Destroy the tower");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Awaiting action...");
		alert.setHeaderText("What would you like to do with this tower?");
		;
		alert.setContentText(null);
		alert.getButtonTypes().clear();
		alert.getButtonTypes().add(upgradeButton);
		alert.getButtonTypes().add(destroyButton);
		alert.getButtonTypes().add(ButtonType.CLOSE);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == upgradeButton)
			upgradeTower(tower);
		else
			destroyTower(tower);

	}

	/**
	 * A function that demo how drag and drop works
	 */
	private void setDragAndDrop(Grid target) {
//        Grid target = grids[3][3];

//        target.setText("Drop\nHere");
		ImageView source1 = imageBasicTower;
		ImageView source2 = imageIceTower;
		ImageView source3 = imageCatapult;
		ImageView source4 = imageLaserTower;
//        Image image1 = new Image(getClass().getResourceAsStream("basicTower.png"));

//        source1.setGraphic(new ImageView(image1));
		source1.setOnDragDetected(new DragEventHandler(source1));
		source2.setOnDragDetected(new DragEventHandler(source2));
		source3.setOnDragDetected(new DragEventHandler(source3));
		source4.setOnDragDetected(new DragEventHandler(source4));

		target.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {

				Dragboard db = event.getDragboard();
				boolean success = false;

				if (db.hasImage()) {
					buildTower(target, db.getImage());

					success = true;

				}
				event.setDropCompleted(success);
				event.consume();
			}
		});

		// well, you can also write anonymous class or even lambda
		// Anonymous class
		target.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {

				/*
				 * accept it only if it is not dragged from the same node and if it has a string
				 * data
				 */
				if (event.getGestureSource() != target && event.getDragboard().hasImage()) {
					/* allow for both copying and moving, whatever user chooses */
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}

				event.consume();
			}
		});

		target.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* the drag-and-drop gesture entered the target */
//                System.out.println("onDragEntered");
				/* show to the user that it is an actual gesture target */
//                if (event.getGestureSource().getClass().getName() != "sample.MyController$Grid" &&
				if (event.getDragboard().hasImage()) {
					target.setStyle("-fx-border-color: blue;");
				}

				event.consume();
			}
		});
		// lambda
		target.setOnDragExited((event) -> {
			/* mouse moved away, remove the graphical cues */
			target.setStyle("-fx-border-color: black;");
			event.consume();
		});

//        target.setOnDragDropped(arg0);
	}

	// for testing

	// Threading

	// spawn monster at (0,0) according to type and returns the label

	public void MonsterAttacked(Grid tower, ArrayList<Grid> monsterList) {
//		TowerAttacking(tower);
		for (Grid monster : monsterList) {
			waitAndChangePic(label1, 1000, monster.getName(), "attacked");
			String message = tower.getName() + " @ (" + String.valueOf(tower.getX()) + ", " + String.valueOf(tower.getY())
			+ ")";
			message = message + " -> " + monster.getName() + " @ (" + String.valueOf(monster.getX()) + ", "
			+ String.valueOf(monster.getY()) + ")";
			System.out.println(message);
		}
	}

	public void MonsterAttacked(Grid tower, Grid monster, boolean slowed) {
//		TowerAttacking(tower);
		if(slowed)
			waitAndChangePic(label1, 1000, monster.getName(), "slowed");
		else
			waitAndChangePic(label1, 1000, monster.getName(), "attacked");
		String message = tower.getName() + " @ (" + String.valueOf(tower.getX()) + ", " + String.valueOf(tower.getY())
				+ ")";
		message = message + " -> " + monster.getName() + " @ (" + String.valueOf(monster.getX()) + ", "
				+ String.valueOf(monster.getY()) + ")";
		System.out.println(message);
	}

	private void TowerAttacking(Grid tower) {
//		Background oldFill = tower.getBackground();
		tower.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
		Task<Void> sleeper = new Task<Void>() {

			protected Void call() throws Exception {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				return null;
			}
		};
		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				tower.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
			}
		});
		new Thread(sleeper).start();
	}

	private static void setUpSpawnMonster(Grid monster, double HP) {
		System.out.println(monster.getName() + ": " + String.valueOf(monster.HP) + " generated");
	}

	public Grid spawnFunc(double xPosition, double yPosition, String name, double HP) {
		double height = MONSTER_SIZE;
		Grid newLabel = new Grid();
		newLabel.setName(name);
		newLabel.setLayoutX(xPosition);
		newLabel.setLayoutY(yPosition);
		newLabel.setXY(xPosition, yPosition);
		Image image = new Image(MyController.class.getResourceAsStream("/" + name + ".png"));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(30);
		imageView.setFitWidth(height);
		newLabel.setGraphic(imageView);

		return newLabel;
	}
	public Grid spawnMonster(double xPosition, double yPosition, String name,double HP) {
		Grid newLabel = spawnFunc(xPosition,yPosition,name,HP);
		paneArena.getChildren().addAll(newLabel);
//
//		switch (name) {
//		case ("fox"):
//			newLabel.HP = 50.0;
//			break;
//		case ("penguin"):
//			newLabel.HP = 100.0;
//			break;
//		case ("unicorn"):
//			newLabel.HP = 1000.0;
//			break;
//		default:
//			break;
//		}

		newLabel.HP = HP;
		newLabel.infoToolTip = new Tooltip();
		newLabel.infoToolTip.setText("HP: " + String.valueOf(newLabel.HP));
		newLabel.setTooltip(newLabel.infoToolTip);
		newLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				newLabel.infoToolTip.show(newLabel, event.getScreenX(), event.getScreenY() + 15);
			}
		});
		newLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				newLabel.infoToolTip.hide();
			}
		});
		setUpSpawnMonster(newLabel, 100);
		return newLabel;
	}

	// moves monster to point (x + deltaX, y + deltaY)
	// note: logic isn't sorted

	//
	public boolean moveMonster(Grid monster, int deltaX, int deltaY) {

		monster.setLayoutX(monster.getLayoutX() + deltaX);
		monster.setLayoutY(monster.getLayoutY() - deltaY);
		monster.setXY(monster.getLayoutX(), monster.getLayoutY());
		return true;
//		}

	}

	public void changeHP(Grid monster, int newHP) {
		monster.HP = newHP;
		monster.infoToolTip.setText(String.valueOf("HP: " + monster.HP));
	}

	@FXML
	public void moveRight() {
		moveMonster(label1, 10, 0);
	}

	@FXML
	public void moveUp() {
		moveMonster(label1, 0, 10);
	}

	private void changePic(Label label, String path) {
		Image image = new Image(getClass().getResourceAsStream("/" + path + ".png"));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(MONSTER_SIZE);
		imageView.setFitWidth(MONSTER_SIZE);
		label.setGraphic(imageView);
	}

	private void waitAndChangePic(Grid label, int seconds, String monster, String status) {
		if(label.isSlowed)
			changePic(label, monster + "_" + status + "andslowed");
		else
			changePic(label,monster + "_" + status);
		Task<Void> sleeper = new Task<Void>() {

			protected Void call() throws Exception {
				try {
					Thread.sleep(seconds);
				} catch (InterruptedException e) {
				}
				return null;
			}
		};
		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				if (label.isSlowed)
					changePic(label, monster + "_slowed");
				else
					changePic(label, monster);
			}
		});
		new Thread(sleeper).start();
	}

	@FXML
	public void Attacked() throws InterruptedException {
		MonsterAttacked(grids[3][3], label1, false);
		ShootBasic(grids[3][3],label1);
//		TowerAttacking(grids[0][0]);
	}

	public void monsterAttacked(Grid monster) {
		waitAndChangePic(monster, 1000, monster.getName(), "attacked");
	}

	@FXML
	public void AttackedAndSlowed() throws InterruptedException {
		waitAndChangePic(label1, 1000, label1.getName(), "attackedandslowed");
		ShootCatapult(grids[3][3],label1.getLayoutX() + GRID_WIDTH/2,label1.getLayoutY() + GRID_HEIGHT/2);
	}

	public void monsterAttackedAndSlowed(Grid monster) {
		waitAndChangePic(monster, 1000, monster.getName(), "attackedandslowed");

	}

	@FXML
	public void Slowed() throws InterruptedException {
		waitAndChangePic(label1, 1000, label1.getName(), "slowed");
		ShootIce(grids[3][3],label1);
	}

	public void monsterSlowed(Grid monster) {
		monster.isSlowed = true;
		changePic(monster, monster.getName() + "_slowed");
	}

	public void monsterNotSlowed(Grid monster) {
		monster.isSlowed = false;
		changePic(monster,monster.getName());
	}
	@FXML
	public void Shoot() throws InterruptedException {
//    	Label label2 = spawnMonster(label1.getLayoutX(),label1.getLayoutY()+450,"collision");
//    	System.out.println(label1.getLayoutY());
//    	Timeline timeline = imageBlinkAndRevert(label2,"collision");
//    	timeline.setOnFinished(event ->paneArena.getChildren().remove(label2));
//    	Attacked();

		Grid grid = grids[3][3];
//		System.out.println(grid.getLayoutX());
//		System.out.println(grid.getLayoutY());
		ShootLaser(grid, label1);
		TowerAttacking(grid);
		Attacked();
//		double actualY = 480-(((480 - 120) - (480-480))/2);
//		laser = spawnMonster(120/2,actualY,"laserBeam_clear",);
//		double actualY = 480-(((480 - 120) - (480-480))/2);
//		double length = Math.sqrt(Math.pow((120 ),2) + Math.pow((actualY),2));
//
//
//		laser = spawnMonster(120/2-length/2,actualY,"laserBeam_clear",length);
//			}
	}

//    paneArena.getChildren().addAll(laser);

	public void flashShootUI(Node node) {
		paneArena.getChildren().addAll(node);

		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				return null;
			}
		};
		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				paneArena.getChildren().remove(node);
			}
		});
		new Thread(sleeper).start();

	}

	public void ShootBasic(Grid tower, Grid monster) {
		TowerAttacking(tower);
		Grid bam  = spawnFunc(monster.getLayoutX(),monster.getLayoutY(),"fire",100);
		flashShootUI(bam);
	}

	public void ShootCatapult(Grid tower, double locX, double locY) {
		TowerAttacking(tower);
		Circle circle = new Circle(locX,locY, 25.0f);
		circle.setOpacity(0.7);
		circle.setFill(Color.ORANGERED);
		flashShootUI(circle);
	};

	public void ShootIce(Grid tower, Grid monster) {
		TowerAttacking(tower);
		Label iceBam = spawnFunc(monster.getLayoutX(),monster.getLayoutY(),"ice",100);
		flashShootUI(iceBam);
	};

	public void ShootLaser(Grid tower, Grid monster) {
		TowerAttacking(tower);
		double towerX = tower.getX() + GRID_WIDTH / 2;
		double towerY = tower.getY() + GRID_HEIGHT / 2;
		double monsterX = monster.getX() + MONSTER_SIZE / 2;
		double monsterY = monster.getY() + MONSTER_SIZE / 2;
		Line line = new Line();
		line.setStroke(Color.RED);
		line.setStrokeWidth(6);
		double realMonsterY = 480 - monsterY;
		double realTowerY = 480 - towerY;
		double endY;
		double endX;
		if (realMonsterY > realTowerY)
			endY = 1000;
		else
			endY = -1000;

		if (towerX != monsterX) {
			double slope = (realTowerY - realMonsterY) / (towerX - monsterX);
			double bias = realTowerY - slope * towerX;
			endX = (endY - bias) / slope;
		} else
			endX = towerX;

		if(realMonsterY == realTowerY) {
			endY = realTowerY;
			if(monsterX<towerX)
				endX = -1000;
			else
				endX = 1000;
			}
		line.setStartX(towerX);
		line.setStartY(towerY);
		line.setEndX(endX);
		line.setEndY(480 - endY);
		flashShootUI(line);

//
//    	paneArena.getChildren().remove(laser);

	}

	public void monsterDie(Grid monster) {


		double height = MONSTER_SIZE;
		Grid newLabel = new Grid();
		newLabel.setLayoutX(monster.getX());
		newLabel.setLayoutY(monster.getY());
		Image image = new Image(MyController.class.getResourceAsStream("/collision.png"));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(30);
		imageView.setFitWidth(30);
		newLabel.setGraphic(imageView);
		paneArena.getChildren().remove(monster);
		paneArena.getChildren().addAll(newLabel);
		Task<Void> sleeper = new Task<Void>() {

			protected Void call() throws Exception {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				return null;
			}
		};
		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				paneArena.getChildren().remove(newLabel);
			}
		});
		new Thread(sleeper).start();

	}

	@FXML
	public void Spawn(ActionEvent event) {
//		monster_die();
		Random rand = new Random();
		int ran = rand.nextInt(3);
		String monster = "";
		switch (ran) {
		case 0:
			monster = "fox";
			break;
		case 1:
			monster = "unicorn";
			break;
		case 2:
			monster = "penguin";
			break;
		default:
			monster = "penguin";
			break;

		}
		label1 = spawnMonster(0, 450, monster,100);
		paneArena.getChildren().addAll(label1);
	}
}
