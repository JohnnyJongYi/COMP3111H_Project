package sample;

import Coordinates.OutOfArenaException;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import monster.MonsterGenerator;
import Coordinates.MovedToWrongGrid;
import sample.MyController;
import tower.TowerHandler;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//import sample.AccessingDataJpaApplication;

//@SpringBootApplication

//@EnableJpaRepositories
public class Main extends Application {
	
	private static long prevTime =0;
	private final static long GenerationTime = (long)1.0e9;
	private static boolean flag = true;
	
	
	public static MonsterGenerator monsterGenerator;
	private TowerHandler towerHandler;
	
	public static AnimationTimer timer = new AnimationTimer() {
		@Override
		public void handle(long now) {
			if(prevTime== 0) {
				prevTime = now;
				return;
			}
			long timeSpent = now - prevTime;
			
			if (timeSpent>GenerationTime/15 && !MonsterGenerator.getMonsterHasReachedEnd()) {
					if(flag) {
						try {
							monsterGenerator.updateMonsterEachTimestamp();
							
							if(MonsterGenerator.getMonsterHasReachedEnd())
							{
								System.out.println("______Game Over_____");
							}
								
						} catch (OutOfArenaException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (MovedToWrongGrid e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						flag = false;
					}
					else {
						TowerHandler.shootAll();
						flag = true;
					}
////				updateMonster(myController)
//				appController.Spawn(new ActionEvent());
//				System.out.println(String.valueOf(counter));
//				counter++;
				prevTime = now;
			}
			

			
		}
    };
	
	
	@SuppressWarnings("restriction")
	@Override
    public void start(Stage primaryStage) throws Exception{
		
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Tower Defence");
        primaryStage.setScene(new Scene(root, 600, 480));
        primaryStage.show();
        MyController appController = (MyController)loader.getController();
        appController.createArena();
        
        monsterGenerator = new MonsterGenerator(appController);
        towerHandler = new TowerHandler(appController);
        
        
        //appController.spawnMonster(0,450,"fox");
    }


    public static void main(String[] args) {
        launch(args);

//        MonsterGenerator.retrieveQuery();
//        MonsterGenerator.closeDataBase();
    }

    
}
