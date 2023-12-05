import AdventureModel.AdventureGame;
import javafx.application.Application;
import javafx.stage.Stage;
import views.AdventureGameView;

import java.io.IOException;

/**
 * Class AdventureGameApp.
 */
public class AdventureGameApp extends  Application {

    AdventureGame model;
    AdventureGameView view;

    public static void main(String[] args) {
        launch(args);
    }

    /*
    * JavaFX is a Framework, and to use it we will have to
    * respect its control flow!  To start the game, we need
    * to call "launch" which will in turn call "start" ...
     */
     // modification of this constructor
      @Override
    public void start(Stage primaryStage){
        this.view = new AdventureGameView(primaryStage);
    }

}
