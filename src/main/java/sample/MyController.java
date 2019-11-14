package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import java.awt.event.ActionListener;

import javax.swing.Timer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import monster.MonsterGenerator;
import tower.TowerHandler;
//import monster.Monster;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;


public class MyController {
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

    
    private Label label1;

    private static final int ARENA_WIDTH = 480;
    private static final int ARENA_HEIGHT = 480;
    private static final int GRID_WIDTH = 40;
    private static final int GRID_HEIGHT = 40;
    private static final int MAX_H_NUM_GRID = 12;
    private static final int MAX_V_NUM_GRID = 12;
    private static final int MONSTER_SIZE = 30;

    private MonsterGenerator monsterGenerator = new MonsterGenerator();
    
    private TowerHandler towerHandler = new TowerHandler();
    
    private Grid grids[][] = new Grid[MAX_V_NUM_GRID][MAX_H_NUM_GRID]; //the grids on arena
    private int x = -1, y = 0; //where is my monster
    
    
    class Grid extends Label {
    	public Grid() {
    		super();
    	}
    }
    
    @FXML
    private void play() {
        System.out.println("Play button clicked");
        Label newLabel = new Label();
        newLabel.setLayoutX(GRID_WIDTH / 4);
        newLabel.setLayoutY(GRID_WIDTH / 4);
        newLabel.setMinWidth(GRID_WIDTH / 2);
        newLabel.setMaxWidth(GRID_WIDTH / 2);
        newLabel.setMinHeight(GRID_WIDTH / 2);
        newLabel.setMaxHeight(GRID_WIDTH / 2);
        newLabel.setStyle("-fx-border-color: black;");
        newLabel.setText("*");
        newLabel.setBackground(new Background(new BackgroundFill(Color.YELLOW,CornerRadii.EMPTY, Insets.EMPTY)));
        paneArena.getChildren().addAll(newLabel);
    }

    /**
     * A function that create the Arena
     */
    @FXML
    public void createArena() {
        if (grids[0][0] != null)
            return; //created already
        for (int i = 0; i < MAX_V_NUM_GRID; i++)
            for (int j = 0; j < MAX_H_NUM_GRID; j++) {
                Grid newLabel = new Grid();
                if (j % 2 == 0 || i == ((j + 1) / 2 % 2) * (MAX_V_NUM_GRID - 1))
                    newLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                else
                    newLabel.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                newLabel.setTranslateX(j * GRID_WIDTH);
                newLabel.setTranslateY(i * GRID_HEIGHT);
                newLabel.setMinWidth(GRID_WIDTH);
                newLabel.setMaxWidth(GRID_WIDTH);
                newLabel.setMinHeight(GRID_HEIGHT);
                newLabel.setMaxHeight(GRID_HEIGHT);
                newLabel.setStyle("-fx-border-color: black;");
                grids[i][j] = newLabel;
                System.out.println(grids[i][j].getTranslateY());
                paneArena.getChildren().addAll(newLabel);
            }

        setDragAndDrop();
//        paneArena.getChildren().addAll(laser);
    }

    @FXML
    private void nextFrame() {
        if (x == -1) {
            grids[0][0].setText("M");
            x = 0;
            return;
        }
        if (y == MAX_V_NUM_GRID - 1)
            return;
        grids[y++][x].setText("");
        grids[y][x].setText("M");
    }

    /**
     * A function that demo how drag and drop works
     */
    private void setDragAndDrop() {
        Grid target = grids[3][3];
        System.out.println(target.getTranslateX());
        System.out.println(target.getTranslateY());
        target.setText("Drop\nHere");
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
        

        target.setOnDragDropped(new EventHandler <DragEvent>() {
        	 
        	@Override
            public void handle(DragEvent event) {
        	
             Dragboard db = event.getDragboard();
             boolean success = false;
             //System.out.println(db.getString());
             if (db.hasImage()) {
//            	 ImageView targetImageView = ((ImageView)((Group)target.getChildren().get(1)));
//                 ((ImageView)target).setGraphic(new ImageView(db.getDragView()));
            	 
            	 ImageView imageView = new ImageView(db.getImage());
                 imageView.setFitHeight(GRID_WIDTH);
                 imageView.setFitWidth(GRID_HEIGHT);
            	 target.setGraphic(imageView);
                 success = true;
             }
             event.setDropCompleted(success);
             event.consume();
        	}
        });

        //well, you can also write anonymous class or even lambda
        //Anonymous class
        target.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                System.out.println("onDragOver");

                /* accept it only if it is  not dragged from the same node
                 * and if it has a string data */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasImage()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        target.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource().getClass().getName() != "sample.MyController$Grid" &&
                        event.getDragboard().hasImage()) {
                    target.setStyle("-fx-border-color: blue;");
                }

                event.consume();
            }
        });
        //lambda
        target.setOnDragExited((event) -> {
                /* mouse moved away, remove the graphical cues */
                target.setStyle("-fx-border-color: black;");
                System.out.println("Exit");
                event.consume();
        });
        
//        target.setOnDragDropped(arg0);
    }
    
    private String monster = "fox";
    
    
    //Threading
    private void wait(Label label, int seconds, String image) {
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
        	changePic(label, image);
        	System.out.println("waited");
        }
    });
    new Thread(sleeper).start();
}
    
    private void wait(int seconds, EventHandler<Event> event1) {
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
            	
            }
        });
        new Thread(sleeper).start();
    }
    // spawn monster at (0,0) according to type and returns the label
   
    
    
    public Label spawnMonster(double xPosition, double yPosition, String name) {
    	int height = 30;
    	Label newLabel = new Label();
    	newLabel.setTranslateX(xPosition);
        newLabel.setTranslateY(yPosition);
        Image image = new Image(getClass().getResourceAsStream("/" + name + ".png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(height);
        newLabel.setGraphic(imageView);
        
        paneArena.getChildren().addAll(newLabel);
    	return newLabel;
    }
    
    // moves monster to point (x + deltaX, y + deltaY)
    public boolean moveMonster(Label monster, int deltaX, int deltaY) {
    	
    	if(monster.getTranslateX() + deltaX > 480 - MONSTER_SIZE ||monster.getTranslateY() + deltaY > 480 - MONSTER_SIZE

   			||(monster.getTranslateY() == 0 && deltaY!=0) 
    		|| (monster.getTranslateX() == 0 && deltaX<0)) 
    		return false;
    	else {
    	monster.setTranslateX(monster.getTranslateX() + deltaX);
    	monster.setTranslateY(monster.getTranslateY() + deltaY);
    	System.out.println(monster.getTranslateX());
    	System.out.println(monster.getTranslateY());
    	return true;
    	}
    	
    }
    
    
    
    @FXML
    public void moveRight() {
    	moveMonster(label1,10,0);
    }
    
    @FXML
    public void moveUp() {
    	moveMonster(label1,0,-10);
    }
    
    private void changePic(Label label, String path) {
    	Image image = new Image(getClass().getResourceAsStream("/" + path + ".png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(MONSTER_SIZE);
        imageView.setFitWidth(MONSTER_SIZE);
        label.setGraphic(imageView);
    }
    
    private Timeline imageBlinkAndRevert(Label label, String name) {
    	Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.125), evt -> label.setVisible(false)),
                new KeyFrame(Duration.seconds(0.25), evt -> label.setVisible(true)));
    	timeline.setCycleCount(2);
    	timeline.play();
    	return timeline;
        }
    
    public void monsterAttacked(Label label, String monsterName,String status) {
    	changePic(label,monsterName +"_"+ status);
    	System.out.println("waiting");
    	wait(label,1000,monsterName);
    }
    
    @FXML
    public void Attacked() throws InterruptedException {
    	monsterAttacked(label1,monster,"attacked"); 	
    }

    @FXML
    public void AttackedAndSlowed() throws InterruptedException {
    	monsterAttacked(label1,monster,"attackedandslowed"); 

    }

    @FXML
    public void Slowed() throws InterruptedException {
//    	changePic(label1,monster + "_slowed");
//    	Timeline timeline = imageBlinkAndRevert(label1,monster);
//    	timeline.setOnFinished(event -> changePic(label1,monster));
    	monsterAttacked(label1,monster,"slowed"); 

    }
    
    @FXML
    public void Shoot() throws InterruptedException {
//    	Label label2 = spawnMonster(label1.getTranslateX(),label1.getTranslateY()+450,"collision");
//    	System.out.println(label1.getTranslateY());
//    	Timeline timeline = imageBlinkAndRevert(label2,"collision");
//    	timeline.setOnFinished(event ->paneArena.getChildren().remove(label2));
//    	Attacked();
    	Grid grid = grids[3][3];
    	System.out.println(grid.getTranslateX());
    	System.out.println(grid.getTranslateY());
    	ShootLaser(grid.getTranslateX(),grid.getTranslateY(),label1.getTranslateX(),label1.getTranslateY());
    }
    
    private Label laser;
//    paneArena.getChildren().addAll(laser);
    
    public void ShootLaser(double towerX, double towerY, double monsterX, double monsterY) {
    	 laser = new Label();
    	
    	ImageView imageview = new ImageView(new Image(getClass().getResourceAsStream("/laserBeam_clear.png")));
    	double length = Math.sqrt(Math.pow((towerX - GRID_WIDTH/2 - monsterX),2) + Math.pow((towerY - monsterY),2));
    	double delta_x = monsterX- towerX;
    	double delta_y = monsterY - towerY;
    	double angle = Math.atan2(delta_y, delta_x) * 180 / Math.PI;
    	
    	
    	imageview.setFitHeight(20);
    	imageview.setFitWidth(length);
    	
    	laser.setGraphic(imageview);
    	
    	laser.setRotate(angle);
    	laser.setTranslateX(towerX - GRID_WIDTH/2);
    	laser.setTranslateY(towerY);
    	
    	paneArena.getChildren().addAll(laser);

    	
    	System.out.print("Rotation is");
    	System.out.println(laser.getRotate());
    	System.out.print("laser x  is");
    	System.out.println(laser.getTranslateX());
    	System.out.print("laser y  is");
    	System.out.println(laser.getTranslateY());
    	Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                paneArena.getChildren().remove(laser);
            }
        });
        new Thread(sleeper).start();
    
//
//    	paneArena.getChildren().remove(laser);
    	
    	
    	
    	
    	
    }
    
    public void monster_die() {
    	paneArena.getChildren().remove(label1);
    } 

    @FXML
    void Spawn(ActionEvent event) {
    	monster_die();
    	label1 = this.spawnMonster(0,450,"fox");
    }
}

class DragEventHandler implements EventHandler<MouseEvent> {
    private ImageView source;
    public DragEventHandler(ImageView e) {
        source = e;
    }
    @Override
    public void handle (MouseEvent event) {
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putImage(source.getImage());
        db.setContent(content);
//        Image image1 = new Image(MyController.class.getResourceAsStream("basicTower.png"));
//        db.setDragView(image1);
       // System.out.println(db.getContentTypes());
        event.consume();
    }
}

class DragDroppedEventHandler implements EventHandler<DragEvent> {
    @Override
    public void handle(DragEvent event) {
        System.out.println("xx");
        Dragboard db = event.getDragboard();
        boolean success = false;
        //System.out.println(db.getString());
        if (db.hasImage()) {
            ((Label)event.getGestureTarget()).setGraphic(new ImageView(new Image(getClass().getResourceAsStream("basicTower.png"))));
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();

    }
}


