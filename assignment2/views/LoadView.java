package views;

import AdventureModel.AdventureGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;


/**
 * Class LoadView.
 *
 * Loads Serialized adventure games.
 */
public class LoadView {

    private AdventureGameView adventureGameView;
    private Label selectGameLabel;
    private Button selectGameButton;
    private Button closeWindowButton;

    private ListView<String> GameList;
    private String filename = null;

    /**
     * Constructs a user interface for selecting and loading game files.
     *
     * @param adventureGameView: The main AdventureGameView object.
     */
    public LoadView(AdventureGameView adventureGameView){
        //test comment
        //note that the buttons in this view are not accessible!!
        this.adventureGameView = adventureGameView;
        selectGameLabel = new Label(String.format(""));

        GameList = new ListView<>(); //to hold all the file names

        final Stage dialog = new Stage(); //dialogue box
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(adventureGameView.stage);

        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #544072;");
        selectGameLabel.setId("CurrentGame"); // DO NOT MODIFY ID
        GameList.setId("GameList");  // DO NOT MODIFY ID
        GameList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        getFiles(GameList); //get files for file selector
        selectGameButton = new Button("Change Game");
        selectGameButton.setId("ChangeGame"); // DO NOT MODIFY ID
        AdventureGameView.makeButtonAccessible(selectGameButton, "select game", "This is the button to select a game", "Use this button to indicate a game file you would like to load.");

        closeWindowButton = new Button("Close Window");
        closeWindowButton.setId("closeWindowButton"); // DO NOT MODIFY ID
        closeWindowButton.setStyle("-fx-background-color: #0A111D; -fx-text-fill: white; -fx-font-family: 'Helvetica';");
        closeWindowButton.setPrefSize(200, 50);
        closeWindowButton.setFont(new Font(16));
        closeWindowButton.setOnAction(e -> dialog.close());
        AdventureGameView.makeButtonAccessible(closeWindowButton, "close window", "This is a button to close the load game window", "Use this button to close the load game window.");

        //on selection, do something
        selectGameButton.setOnAction(e -> {
            try {
                selectGame(selectGameLabel, GameList);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox selectGameBox = new VBox(10, selectGameLabel, GameList, selectGameButton);

        // Default styles which can be modified
        GameList.setPrefHeight(100);
        GameList.setStyle("-fx-text-fill: #CBC3E3");
        selectGameLabel.setStyle("-fx-text-fill: #e8e6e3");
        selectGameLabel.setFont(new Font(16));
        selectGameButton.setStyle("-fx-background-color: #0A111D; -fx-text-fill: white; -fx-font-family: 'Helvetica';");
        selectGameButton.setPrefSize(200, 50);
        selectGameButton.setFont(new Font(16));
        selectGameBox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(selectGameBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    /**
     * Get Files to display in the on screen ListView
     * Populate the listView attribute with .ser file names
     * Files will be located in the Games/Saved directory
     *
     * @param listView the ListView containing all the .ser files in the Games/Saved directory.
     */
    private void getFiles(ListView<String> listView) {
        File file = new File("Games/Saved");
        //how to list all files in a directory citation: https://www.geeksforgeeks.org/file-listfiles-method-in-java-with-examples/
        File [] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                //endsWith citation https://stackoverflow.com/questions/3571223/how-do-i-get-the-file-extension-of-a-file-in-java
                if (files[i].getName().endsWith(".ser")) {
                    //listview citation: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ListView.html
                    listView.getItems().add(files[i].getName());
                }
            }
        }
    }

    /**
     * Select the Game
     * Try to load a game from the Games/Saved
     * If successful, stop any articulation and put the name of the loaded file in the selectGameLabel.
     * If unsuccessful, stop any articulation and start an entirely new game from scratch.
     * In this case, change the selectGameLabel to indicate a new game has been loaded.
     *
     * @param selectGameLabel the label to use to print errors and or successes to the user.
     * @param GameList the ListView to populate
     */
    private void selectGame(Label selectGameLabel, ListView<String> GameList) throws IOException {
        //saved games will be in the Games/Saved folder!
        String selectedGame = GameList.getSelectionModel().getSelectedItem();
        try{
            //If successful, stop any articulation and put the name of the loaded file in the selectGameLabel.
            //load the game
            AdventureGame loadedGame =  loadGame("Games/Saved/" + selectedGame);
            //stop any articulation
            adventureGameView.stopArticulation();
            //create a new adventure game object given the loaded game
            adventureGameView = new AdventureGameView(loadedGame, adventureGameView.stage);
            //put the name of the loaded file in the selectGameLabel
            selectGameLabel.setText(selectedGame);

        } catch (Exception e){
            //If unsuccessful, stop any articulation and start an entirely new game from scratch.
            //In this case, change the selectGameLabel to indicate a new game has been loaded.
            //stop any articulation
            adventureGameView.stopArticulation();
            //create a new adventure game object given the current directory name after "Games"
            AdventureGame newGame = new AdventureGame(adventureGameView.model.getDirectoryName().substring(5));
            //re-initialize the adventureGameView
            adventureGameView = new AdventureGameView(newGame, adventureGameView.stage);
            //indicate a new game has been loaded
            selectGameLabel.setText("New game has been loaded.");
        }

    }

    /**
     * Load the Game from a file
     *
     * @param GameFile file to load
     * @return loaded Tetris Model
     */
    public AdventureGame loadGame(String GameFile) throws IOException, ClassNotFoundException {
        // Reading the object from a file
        FileInputStream file = null;
        ObjectInputStream in = null;
        try {
            file = new FileInputStream(GameFile);
            in = new ObjectInputStream(file);
            return (AdventureGame) in.readObject();
        } finally {
            if (in != null) {
                in.close();
                file.close();
            }
        }
    }

}




