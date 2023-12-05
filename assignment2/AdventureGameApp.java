import AdventureModel.AdventureGame;
import javafx.application.Application;
import javafx.stage.Stage;
import views.AdventureGameView;

/**
 * Class AdventureGameApp.
 */
public class AdventureGameApp extends  Application {

    AdventureGame model;
    AdventureGameView view;

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args command line arguments passed to the application.
     *             Not used in this application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application.
     * This method is called after the JavaFX runtime is initialized.
     * Initializes the view component of the application.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
      @Override
    public void start(Stage primaryStage){
        this.view = new AdventureGameView(primaryStage);
    }

}

