package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.MyController;


public class Main extends Application {
	
	private long prevTime =0;
	private final long GenerationTime = (long)1.0e9;
	private int counter = 1;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Tower Defence");
        primaryStage.setScene(new Scene(root, 600, 480));
        primaryStage.show();
        MyController appController = (MyController)loader.getController();
        appController.createArena();
        
        
        AnimationTimer timer = new AnimationTimer() {
    		@Override
    		public void handle(long now) {
    			if(prevTime== 0) {
    				prevTime = now;
    				return;
    			}
    			long timeSpent = now - prevTime;
    			
    			if (timeSpent>GenerationTime*5) {
//    				updateMonster()
    				appController.Spawn(new ActionEvent());
    				System.out.println(String.valueOf(counter));
    				counter++;
    				prevTime = now;
    			}
    			
    			
    			
    		}
        };
        timer.start();
        //appController.spawnMonster(0,450,"fox");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
