package views;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class SaveView.
 *
 * Saves Serialized adventure games.
 */
public class SaveView {

    static String saveFileSuccess = "Saved EscapeRoom Game!!";
    static String saveFileExistsError = "Error: File already exists";
    static String saveFileNotSerError = "Error: File must end with .ser";
    private Label saveFileErrorLabel = new Label("");
    private Label saveGameLabel = new Label(String.format("Enter name of file to save"));
    private TextField saveFileNameTextField = new TextField("");
    private Button saveGameButton = new Button("Save Game");
    private Button closeWindowButton = new Button("Close Window");


    private AdventureGameView adventureGameView;

    /**
     * Constructor
     *
     * @param adventureGameView: The main AdventureGameView object.
     */
    public SaveView(AdventureGameView adventureGameView) {
        this.adventureGameView = adventureGameView;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(adventureGameView.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #544072;");
        saveGameLabel.setId("SaveGame"); // DO NOT MODIFY ID
        saveFileErrorLabel.setId("SaveFileErrorLabel");
        saveFileNameTextField.setId("SaveFileNameTextField");
        saveGameLabel.setStyle("-fx-text-fill: #e8e6e3; -fx-font-family: 'DejaVu Sans';");
        saveGameLabel.setFont(new Font(16));
        saveFileErrorLabel.setStyle("-fx-text-fill: #e8e6e3;");
        saveFileErrorLabel.setFont(new Font(16));
        saveFileNameTextField.setStyle("-fx-text-fill: #000000;");
        saveFileNameTextField.setFont(new Font(16));

        String gameName = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + ".ser";
        saveFileNameTextField.setText(gameName);

        saveGameButton = new Button("Save board");
        saveGameButton.setId("SaveBoardButton"); // DO NOT MODIFY ID

        // high contrast color #0A111D with other text with color #FFFFFF
        // when button is hover, the fonts of the button get smaller, creating a UI effect
        saveGameButton.setStyle("-fx-background-color: #0A111D; -fx-text-fill: white; -fx-font-family: 'Helvetica';");
        saveGameButton.setPrefSize(200, 50);
        saveGameButton.setFont(new Font(16));
        AdventureGameView.makeButtonAccessible(saveGameButton, "save game", "This is a button to save the game", "Use this button to save the current game.");
        saveGameButton.setOnAction(e -> saveGame());

        closeWindowButton = new Button("Close Window");
        closeWindowButton.setId("closeWindowButton"); // DO NOT MODIFY ID
        // high contrast color #0A111D with other text with color #FFFFFF
        // when button is hover, the fonts of the button get smaller, creating a UI effect
        closeWindowButton.setStyle("-fx-background-color: #0A111D; -fx-text-fill: white; -fx-font-family: 'Helvetica';");
        closeWindowButton.setPrefSize(200, 50);
        closeWindowButton.setFont(new Font(16));
        closeWindowButton.setOnAction(e -> dialog.close());
        AdventureGameView.makeButtonAccessible(closeWindowButton, "close window", "This is a button to close the save game window", "Use this button to close the save game window.");

        VBox saveGameBox = new VBox(10, saveGameLabel, saveFileNameTextField, saveGameButton, saveFileErrorLabel, closeWindowButton);
        saveGameBox.setAlignment(Pos.CENTER);

        dialogVbox.getChildren().add(saveGameBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    /**
     * Saves the Game
     * Save the game to a serialized (binary) file.
     * Get the name of the file from saveFileNameTextField.
     * Files will be saved to the Games/Saved directory.
     * If the file already exists, set the saveFileErrorLabel to the text in saveFileExistsError
     * If the file doesn't end in .ser, set the saveFileErrorLabel to the text in saveFileNotSerError
     * Otherwise, load the file and set the saveFileErrorLabel to the text in saveFileSuccess
     */
    private void saveGame() {
        //test comment
        String fileName = saveFileNameTextField.getText();
        //endsWith citation https://stackoverflow.com/questions/3571223/how-do-i-get-the-file-extension-of-a-file-in-java
        //If the file doesn't end in .ser, set the saveFileErrorLabel to the text in saveFileNotSerError
        if(!fileName.endsWith(".ser")){
            saveFileErrorLabel.setText(saveFileNotSerError);
            return;
        }

        //create file
        File file = new File("Games/Saved");
        File [] files = file.listFiles();
        //If the file already exists, set the saveFileErrorLabel to the text in saveFileExistsError
        if(files != null){
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().equals(fileName)) {
                    saveFileErrorLabel.setText(saveFileExistsError);
                    return;
                }
            }
        }

        adventureGameView.model.saveModel(new File("Games/Saved/" + fileName));
        saveFileErrorLabel.setText(saveFileSuccess);


    }


}


