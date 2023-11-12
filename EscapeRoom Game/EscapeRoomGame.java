import escapeModel.EscapeGame;
import javafx.application.Application;
import javafx.stage.Stage;
import views.EscapeRoomView;

import java.io.IOException;

/**
 * Class AdventureGameApp.
 */
public class EscapeGameApp extends Application {

    EscapeGame model;
    EscapeGameView view;

    public static void main(String[] args) {
        launch(args);
    }

    /*
     * JavaFX is a Framework, and to use it we will have to
     * respect its control flow!  To start the game, we need
     * to call "launch" which will in turn call "start" ...
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.model = new AdventureGame("TinyGame"); //change the name of the game if you want to try something bigger!
        this.view = new AdventureGameView(model, primaryStage);
    }

}
