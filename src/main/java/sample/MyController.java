package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

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

public class MyController implements staticInterface {
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
	private static AnchorPane paneArena;

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

	private int money = 100;

	private void callInsufficientResourceAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Insufficient Resources");
		alert.setContentText("You don't have enough money to build this tower");
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

	private void buildTower(Grid target, Image image) {
		if (updateInfo(target)) {
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(GRID_WIDTH);
			imageView.setFitWidth(GRID_HEIGHT);
			target.setGraphic(imageView);
			target.infoToolTip = new Tooltip();
			target.setTooltip(target.infoToolTip);

			target.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					mouseHover(target, event);
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
			callInsufficientResourceAlert();

//		Tower.build(target.getX(),target.getY(),draggingTower)

	}

	private void mouseHover(Grid target, MouseEvent event) {

		Circle circle = new Circle(target.getX() + GRID_WIDTH / 2, target.getY() + GRID_HEIGHT / 2, 50.0f);
		Rectangle rect = new Rectangle(target.getX(), target.getY(), GRID_WIDTH, GRID_HEIGHT);
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
	}

	private void destroyTower(Grid tower) {
		tower.setGraphic(null);
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
					String message = target.getName() + "\nAttack Power: 0\n" + "Building Cost: 0\n"
							+ "Shooting Range: 0\n";
					target.infoToolTip.setText(message);
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

	private void MonsterAttacked(Grid tower, Grid monster, String state) {
		TowerAttacking(tower);
		waitAndChangePic(label1, 1000, monster.getName(), state);
		String message = tower.getName() + " @ (" + String.valueOf(tower.getX()) + ", " + String.valueOf(tower.getY())
				+ ")";
		message = message + " -> " + monster.getName() + " @ (" + String.valueOf(monster.getX()) + ", "
				+ String.valueOf(monster.getY()) + ")";
		System.out.println(message);
	}

	private void TowerAttacking(Grid tower) {
		Background oldFill = tower.getBackground();
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
				tower.setBackground(oldFill);
			}
		});
		new Thread(sleeper).start();
	}

	private static void setUpSpawnMonster(Grid monster, double HP) {
		System.out.println(monster.getName() + ": " + String.valueOf(monster.HP) + " generated");
	}

	public static Grid spawnMonster(double xPosition, double yPosition, String name) {
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

		paneArena.getChildren().addAll(newLabel);

		switch (name) {
		case ("fox"):
			newLabel.HP = 50.0;
			break;
		case ("penguin"):
			newLabel.HP = 100.0;
			break;
		case ("unicorn"):
			newLabel.HP = 1000.0;
			break;
		default:
			break;
		}

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
	public static boolean moveMonster(Grid monster, int deltaX, int deltaY) {

		monster.setLayoutX(monster.getLayoutX() + deltaX);
		monster.setLayoutY(monster.getLayoutY() - deltaY);
		monster.setXY(monster.getLayoutX(), monster.getLayoutY());
		return true;
//		}

	}

	public static void changeHP(Grid monster, int newHP) {
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
		changePic(label, monster + "_" + status);
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
		MonsterAttacked(grids[3][3], label1, "attacked");
//		TowerAttacking(grids[0][0]);
	}

	public void monsterAttacked(Grid monster) {
		waitAndChangePic(monster, 1000, monster.getName(), "attacked");
	}

	@FXML
	public void AttackedAndSlowed() throws InterruptedException {
		waitAndChangePic(label1, 1000, label1.getName(), "attackedandslowed");
	}

	public void monsterAttackedAndSlowed(Grid monster) {
		waitAndChangePic(monster, 1000, monster.getName(), "attackedandslowed");
	}

	@FXML
	public void Slowed() throws InterruptedException {
		waitAndChangePic(label1, 1000, label1.getName(), "slowed");
		
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

	public void ShootLaser(Grid tower, Grid monster) {
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

		line.setStartX(towerX);
		line.setStartY(towerY);
		line.setEndX(endX);
		line.setEndY(480 - endY);
		paneArena.getChildren().addAll(line);

		Task<Void> sleeper = new Task<Void>() {
			@Override
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
				paneArena.getChildren().remove(line);
			}
		});
		new Thread(sleeper).start();

//
//    	paneArena.getChildren().remove(laser);

	}

	public void monsterDie(Grid monster) {
		changePic(monster, "collision");
		Task<Void> sleeper = new Task<Void>() {

			protected Void call() throws Exception {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
				return null;
			}
		};
		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				paneArena.getChildren().remove(monster);
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
		label1 = spawnMonster(0, 450, monster);
	}
}
