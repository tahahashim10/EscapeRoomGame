package views;

import AdventureModel.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent; //you will need these!
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.event.EventHandler; //you will need this too!
import javafx.scene.AccessibleRole;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class AdventureGameView.
 *
 * This is the Class that will visualize your model.
 * You are asked to demo your visualization via a Zoom
 * recording. Place a link to your recording below.
 *
 * ZOOM LINK: https://drive.google.com/file/d/1BUlyAP659uqM48ZI7W0L4_MaM5SMfY4M/view?usp=sharing
 * PASSWORD: N/A
 */
public class AdventureGameView {

    AdventureGame model; //model of the game
    Stage stage; //stage on which all is rendered
    Button saveButton, loadButton, helpButton; //buttons
    Boolean helpToggle = false; //is help on display?

    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image
    TextField inputTextField; //for user input

    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing


    public prisonMiniGame prisonGame;
    public crimeMiniGame crimeGame;
    public zombieMiniGame zombieGame;
    public futureMiniGame futureGame;

    public int currQuestionIndex = 0;



    AdventureGameController gameController;

    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     * @param model: The model of the adventure game.
     * @param stage: The stage of the adventure game.
     */
    public AdventureGameView(AdventureGame model, Stage stage) {
        this.model = model;
        this.stage = stage;
        prisonGame = new prisonMiniGame();
        crimeGame = new crimeMiniGame();
        zombieGame = new zombieMiniGame();
        futureGame = new futureMiniGame();

        intiUI();
    }

    /**
     * Initialize the UI
     */
    public void intiUI() {

        // setting up the stage
        this.stage.setTitle("hashimt1's Adventure Game"); //Replace <YOUR UTORID> with your UtorID

        //Inventory + Room items
        objectsInInventory.setSpacing(10);
        objectsInInventory.setAlignment(Pos.TOP_CENTER);
        objectsInRoom.setSpacing(10);
        objectsInRoom.setAlignment(Pos.TOP_CENTER);

        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));

        //Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(650);
        ColumnConstraints column3 = new ColumnConstraints(150);
        column3.setHgrow( Priority.SOMETIMES ); //let some columns grow to take any extra space
        column1.setHgrow( Priority.SOMETIMES );

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints( 550 );
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow( Priority.SOMETIMES );
        row3.setVgrow( Priority.SOMETIMES );

        gridPane.getColumnConstraints().addAll( column1 , column2 , column1 );
        gridPane.getRowConstraints().addAll( row1 , row2 , row1 );

        // Buttons
        saveButton = new Button("Save");
        saveButton.setId("Save");
        customizeButton(saveButton, 100, 50);
        makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
        addSaveEvent();

        loadButton = new Button("Load");
        loadButton.setId("Load");
        customizeButton(loadButton, 100, 50);
        makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();

        helpButton = new Button("Instructions");
        helpButton.setId("Instructions");
        customizeButton(helpButton, 200, 50);
        makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addInstructionEvent();

        HBox topButtons = new HBox();
        topButtons.getChildren().addAll(saveButton, helpButton, loadButton);
        topButtons.setSpacing(10);
        topButtons.setAlignment(Pos.CENTER);

        inputTextField = new TextField();
        inputTextField.setFont(new Font("Arial", 16));
        inputTextField.setFocusTraversable(true);

        inputTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
        inputTextField.setAccessibleRoleDescription("Text Entry Box");
        inputTextField.setAccessibleText("Enter commands in this box.");
        inputTextField.setAccessibleHelp("This is the area in which you can enter commands you would like to play.  Enter a command and hit return to continue.");
        addTextHandlingEvent(); //attach an event to this input field

        //labels for inventory and room items
        Label objLabel =  new Label("Objects in Room");
        objLabel.setAlignment(Pos.CENTER);
        objLabel.setStyle("-fx-text-fill: white;");
        objLabel.setFont(new Font("Arial", 16));

        Label invLabel =  new Label("Your Inventory");
        invLabel.setAlignment(Pos.CENTER);
        invLabel.setStyle("-fx-text-fill: white;");
        invLabel.setFont(new Font("Arial", 16));

        //add all the widgets to the GridPane
        gridPane.add( objLabel, 0, 0, 1, 1 );  // Add label
        gridPane.add( topButtons, 1, 0, 1, 1 );  // Add buttons
        gridPane.add( invLabel, 2, 0, 1, 1 );  // Add label

        Label commandLabel = new Label("What would you like to do?");
        commandLabel.setStyle("-fx-text-fill: white;");
        commandLabel.setFont(new Font("Arial", 16));

        updateScene(""); //method displays an image and whatever text is supplied
        updateItems(); //update items shows inventory and objects in rooms

        // adding the text area and submit button to a VBox
        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background-color: #000000;");
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        gridPane.add( textEntry, 0, 2, 3, 1 );

        // Render everything
        var scene = new Scene( gridPane ,  1000, 800);
        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();

    }


    /**
     * makeButtonAccessible
     * __________________________
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     *
     * @param inputButton the button to add screenreader hooks to
     * @param name ARIA name
     * @param shortString ARIA accessible text
     * @param longString ARIA accessible help text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * customizeButton
     * __________________________
     *
     * @param inputButton the button to make stylish :)
     * @param w width
     * @param h height
     */
    private void customizeButton(Button inputButton, int w, int h) {
        inputButton.setPrefSize(w, h);
        inputButton.setFont(new Font("Arial", 16));
        inputButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
    }

    /**
     * addTextHandlingEvent
     * __________________________
     * Add an event handler to the myTextField attribute
     *
     * Your event handler should respond when users
     * hits the ENTER or TAB KEY. If the user hits
     * the ENTER Key, strip white space from the
     * input to myTextField and pass the stripped
     * string to submitEvent for processing.
     *
     * If the user hits the TAB key, move the focus
     * of the scene onto any other node in the scene
     * graph by invoking requestFocus method.
     */
    private void addTextHandlingEvent() {
        //test comment
        //add your code here!
        //EventHandler citation: https://docs.oracle.com/javase/8/docs/api/java/beans/EventHandler.html
        EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if(e.getCode() == KeyCode.TAB){ //if the user hits the TAB key, invoke the requestFocus method
                    gridPane.requestFocus();
                } else if (e.getCode() == KeyCode.ENTER) { //if the user hits the enter key, strip white space from
                    //inputTextField and process event
                    submitEvent(inputTextField.getText().strip());
                    //clear the text field
                    inputTextField.setText("");
                }
            }
        };
        inputTextField.addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
    }


    /**
     * submitEvent
     * __________________________
     *
     * @param text the command that needs to be processed
     */
    private void submitEvent(String text) {

        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop

        if (text.equalsIgnoreCase("LOOK") || text.equalsIgnoreCase("L")) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription();
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (!objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            // articulateRoomDescription(); //all we want, if we are looking, is to repeat description.
            return;
        } else if (text.equalsIgnoreCase("HELP") || text.equalsIgnoreCase("H")) {
            showInstructions();
            return;
        } else if (text.equalsIgnoreCase("COMMANDS") || text.equalsIgnoreCase("C")) {
            showCommands(); //this is new!  We did not have this command in A1
            return;
        }

        //try to move!
        String output = this.model.interpretAction(text); //process the command!
        if(output == null || !output.equals("FORCED")){
            inputTextField.setDisable(false);
        }


        // ANGELA ***************
        if (output == null || (!output.equals("PLAY") && !output.equals("ANSWER")& !output.equals("GAME OVER") && !output.equals("FORCED") && !output.equals("HELP"))) {
            updateScene(output);
            updateItems();
        } else if (output.equals("GAME OVER")) {
            inputTextField.setDisable(true);
            updateScene("");
            updateItems();
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();
        } else if (output.equals("FORCED")) {
            //user should not be able to enter or type in the text box
            //citation for setDisable: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html#setDisable-boolean-
            inputTextField.setDisable(true);

            //show the current room
            updateScene("");
            //update items
            updateItems();

            //create a pause transition for 6 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(6));
            pause.setOnFinished(event -> { //once the pause is finished
                //go through the sequence of forced events
                submitEvent("FORCED");
            });
            pause.play();

        //ANGELA ***************************************
        }else if (output.equals("PLAY")){
            MiniGame currGame = this.returnMini();
            updateScene((String) currGame.getQuestionList().get(currQuestionIndex % 3));

        }else if (output.startsWith("ANSWER")){
               output = text.strip().substring(6,text.strip().length());
               System.out.println(output);
               if(prisonGame.playGame(output, currQuestionIndex)){
                   updateScene("answerCorrect");
                   currQuestionIndex += 1;
               }else{

                   updateScene("answer Incorrect, enter PLAY again to answer the question. ");


               }
           }




        }

    // return the correct miniGame
    public MiniGame returnMini(){
        if(this.model.player.getCurrentRoom().getRoomNumber() == 1){
            return (MiniGame) crimeGame;

        } else if (this.model.player.getCurrentRoom().getRoomNumber() == 2) {
            return (MiniGame) prisonGame;

        } else if (this.model.player.getCurrentRoom().getRoomNumber() == 3) {

        }else if(this.model.player.getCurrentRoom().getRoomNumber() == 4){

        }

        return (MiniGame) prisonGame;

    }





    /**
     * showCommands
     * __________________________
     *
     * update the text in the GUI (within roomDescLabel)
     * to show all the moves that are possible from the
     * current room.
     */
    private void showCommands() {
        //update the roomDescLabel text
        roomDescLabel.setText("You can move in these directions: \n\n" + model.getPlayer().getCurrentRoom().getCommands());
    }


    /**
     * updateScene
     * __________________________
     *
     * Show the current room, and print some text below it.
     * If the input parameter is not null, it will be displayed
     * below the image.
     * Otherwise, the current room description will be displayed
     * below the image.
     *
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay) {

        getRoomImage(); //get the image of the current room
        formatText(textToDisplay); //format the text to display
        roomDescLabel.setPrefWidth(500);
        roomDescLabel.setPrefHeight(500);
        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
        roomDescLabel.setWrapText(true);
        VBox roomPane = new VBox(roomImageView,roomDescLabel);
        roomPane.setPadding(new Insets(10));
        roomPane.setAlignment(Pos.TOP_CENTER);
        roomPane.setStyle("-fx-background-color: #000000;");

        gridPane.add(roomPane, 1, 1);
        stage.sizeToScene();

        //finally, articulate the description
        /*
        if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();

         */
    }

    /**
     * formatText
     * __________________________
     *
     * Format text for display.
     *
     * @param textToDisplay the text to be formatted for display.
     */
    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDesc);
        } else roomDescLabel.setText(textToDisplay);
        roomDescLabel.setStyle("-fx-text-fill: white;");
        roomDescLabel.setFont(new Font("Arial", 16));
        roomDescLabel.setAlignment(Pos.CENTER);
    }

    /**
     * getRoomImage
     * __________________________
     *
     * Get the image for the current room and place
     * it in the roomImageView
     */
    private void getRoomImage() {

        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImage = this.model.getDirectoryName() + "/room-images/" + roomNumber + ".jpg";

        Image roomImageFile = new Image(roomImage);
        roomImageView = new ImageView(roomImageFile);
        roomImageView.setPreserveRatio(true);
        roomImageView.setFitWidth(400);
        roomImageView.setFitHeight(400);

        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);
    }

    /**
     * updateItems
     * __________________________
     *
     * This method is partially completed, but you are asked to finish it off.
     *
     * The method should populate the objectsInRoom and objectsInInventory Vboxes.
     * Each Vbox should contain a collection of nodes (Buttons, ImageViews, you can decide)
     * Each node represents a different object.
     *
     * Images of each object are in the assets
     * folders of the given adventure game.
     */
    public void updateItems() {


        //write some code here to add images of objects in a given room to the objectsInRoom Vbox
        //write some code here to add images of objects in a player's inventory room to the objectsInInventory Vbox
        //please use setAccessibleText to add "alt" descriptions to your images!
        //the path to the image of any is as follows:
        //this.model.getDirectoryName() + "/objectImages/" + objectName + ".jpg";
        //write some code here to add images of objects in a given room to the objectsInRoom Vbox

        boolean flagForced = false;

        //check if there is a forced room
        for(int i = 0; i < model.getPlayer().getCurrentRoom().getMotionTable().getDirection().size(); i++){
            if(model.getPlayer().getCurrentRoom().getMotionTable().getDirection().get(i).getDirection().equals("FORCED")){
                flagForced = true;
            }
        }

        //clear all objects in room
        objectsInRoom.getChildren().clear();
        //an arraylist to store all the objects in the current room
        ArrayList<AdventureObject> listObjectsInRoom = model.getPlayer().getCurrentRoom().objectsInRoom;
        //loop through all the objects in the current room
        for(int i = 0; i < listObjectsInRoom.size(); i++){
            //citation for ImageView : https://docs.oracle.com/javase/8/javafx/api/javafx/scene/image/ImageView.html
            //create a new image for the current image we are on in the arraylist
            Image image = new Image(this.model.getDirectoryName() + "/objectImages/" + listObjectsInRoom.get(i).getName() + ".jpg");
            ImageView objectImageView = new ImageView(image);
            objectImageView.setFitWidth(100); //set its width to 100
            //citation for setFocusTraversable: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html#setFocusTraversable-boolean-
            objectImageView.setFocusTraversable(true); //allows the user to tab over the image
            //citation for setPreserveRatio: https://docs.oracle.com/javafx/2/api/javafx/scene/image/Image.html#:~:text=boolean%20isPreserveRatio()-,Indicates%20whether%20to%20preserve%20the%20aspect%20ratio%20of%20the%20original,is%20scaled%20to%20preserve%20ratio
            objectImageView.setPreserveRatio(true); //preserves the aspect ratio
            Button button = new Button("", objectImageView); //new button for the image
            //VBox citation: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html
            VBox vbox = new VBox(objectImageView);
            vbox.setAlignment(Pos.CENTER); //center it
            Label label = new Label(listObjectsInRoom.get(i).getName()); //need the label to be object name
            label.setStyle("-fx-text-fill: white;"); //set color
            label.setFont(new Font("Arial", 16)); //set font
            label.setAlignment(Pos.CENTER); //center it
            vbox.getChildren().add(label);
            button.setGraphic(vbox);

            //make button accessible
            makeButtonAccessible(button, listObjectsInRoom.get(i).getName(), "This is a button for " + listObjectsInRoom.get(i).getName(), "This is button for this object in the current room: " + listObjectsInRoom.get(i).getName());
            customizeButton(button, 100, 100);

            int tempI = i;
            EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent e) {
                    //if user enters enter, take the object and update items
                    if (e.getCode() == KeyCode.ENTER) {
                        model.getPlayer().takeObject(listObjectsInRoom.get(tempI).getName());
                        updateItems();
                    }
                }
            };
            EventHandler<MouseEvent> eventHandler2 = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    //if user left-clicks, take the object and update items
                    if(e.getButton() == MouseButton.PRIMARY){
                        model.getPlayer().takeObject(listObjectsInRoom.get(tempI).getName());
                        updateItems();
                    }
                }
            };

            //register the handlers
            button.addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler2);
            //add the button to the objects in room
            if(flagForced){
                button.setDisable(true);
            }
            objectsInRoom.getChildren().add(button);

        }

        //write some code here to add images of objects in a player's inventory room to the objectsInInventory Vbox
        //clear all objects in room
        objectsInInventory.getChildren().clear();
        //create an arraylist for all the objects in the room and loop through them
        ArrayList<String> listObjectsInInventory = model.getPlayer().getInventory();
        for(int i = 0; i < listObjectsInInventory.size(); i++){
            //create a new image for the current image we are on in the arraylist
            Image image = new Image(this.model.getDirectoryName() + "/objectImages/" + listObjectsInInventory.get(i) + ".jpg");
            ImageView objectImageView = new ImageView(image);
            objectImageView.setFitWidth(100); //set its width to 100
            objectImageView.setFocusTraversable(true); //allows the user to tab over the image
            objectImageView.setPreserveRatio(true); //preserves the aspect ratio
            Button button2 = new Button("", objectImageView); //new button for the image
            VBox vbox = new VBox(objectImageView);
            vbox.setAlignment(Pos.CENTER); //center it
            Label label = new Label(listObjectsInInventory.get(i)); //need the label to be object name
            label.setStyle("-fx-text-fill: white;"); //set color
            label.setFont(new Font("Arial", 16)); //set font
            label.setAlignment(Pos.CENTER); //center it
            vbox.getChildren().add(label);
            button2.setGraphic(vbox);

            makeButtonAccessible(button2, listObjectsInInventory.get(i), "This is a button for " + listObjectsInInventory.get(i), "This is a button for this object in the players inventory: " + listObjectsInInventory.get(i));
            customizeButton(button2, 100, 100);

            int tempI = i;
            EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent e) {
                    //if user presses enter, drop the object and update items
                    if (e.getCode() == KeyCode.ENTER) {
                        model.getPlayer().dropObject(listObjectsInInventory.get(tempI));
                        updateItems();
                    }
                }
            };
            EventHandler<MouseEvent> eventHandler2 = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    //if user left-clicks , drop the object and update items
                    if(e.getButton() == MouseButton.PRIMARY){
                        model.getPlayer().dropObject(listObjectsInInventory.get(tempI));
                        updateItems();
                    }
                }
            };

            //register the handlers
            button2.addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
            button2.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler2);

            if(flagForced){ //if forced room, disable button
                button2.setDisable(true);
            }
            //add the button to the objects in inventory
            objectsInInventory.getChildren().add(button2);
        }

        ScrollPane scO = new ScrollPane(objectsInRoom);
        scO.setPadding(new Insets(10));
        scO.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        scO.setFitToWidth(true);
        gridPane.add(scO,0,1);

        ScrollPane scI = new ScrollPane(objectsInInventory);
        scI.setFitToWidth(true);
        scI.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        gridPane.add(scI,2,1);
    }

    /**
     * Show the game instructions.
     *
     * If helpToggle is FALSE:
     * -- display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     *
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     */
    public void showInstructions() {
        if(!helpToggle){
            //REMOVE whatever nodes are within the cell beforehand!

            // Remove all nodes from the specified cell
            //list for all the nodes to remove
            List<Node> nodesToRemove = new ArrayList<>();
            for (int i = 0; i < gridPane.getChildren().size(); i++) {
                //want to remove everything at (1,1)
                if (gridPane.getRowIndex(gridPane.getChildren().get(i)) == 1 && gridPane.getColumnIndex(gridPane.getChildren().get(i)) == 1) {
                    nodesToRemove.add(gridPane.getChildren().get(i));
                }
            }
            //remove the nodes at (1,1)
            gridPane.getChildren().removeAll(nodesToRemove);

            Label label = new Label(); //create label
            label.setText(model.getInstructions()); //set the text
            label.setFont(new Font("Arial", 16)); //set the font
            label.setStyle("-fx-text-fill: white;"); //set the style and color
            //citation for setWrapText: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TextArea.html#:~:text=setWrapText,-public%20final%20void&text=setWrapText(boolean%20value)-,Sets%20the%20value%20of%20the%20property%20wrapText.,should%20wrap%20onto%20another%20line.
            label.setWrapText(true); //if text exceeds the width of text area, wrap onto another line
            label.setAlignment(Pos.CENTER); //center the text

            //ScrollPane citation: https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html
            ScrollPane sc = new ScrollPane(label);
            sc.setStyle("-fx-background: #000000; -fx-background-color:transparent;"); //transparent background
            sc.setFitToWidth(true);

            //display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
            gridPane.add(sc, 1, 1);
            //set the helpToggle to TRUE
            helpToggle = true;
        } else{

            //Again, REMOVE whatever nodes are within the cell beforehand!
            //list for all the nodes to remove
            List<Node> nodesToRemove = new ArrayList<>();
            for (int i = 0; i < gridPane.getChildren().size(); i++) {
                //want to remove everything at (1,1)
                if (GridPane.getRowIndex(gridPane.getChildren().get(i)) == 1 && GridPane.getColumnIndex(gridPane.getChildren().get(i)) == 1) {
                    nodesToRemove.add(gridPane.getChildren().get(i));
                }
            }
            //remove the nodes at (1,1)
            gridPane.getChildren().removeAll(nodesToRemove);

            //all of this is from the updateScene method
            //redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
            getRoomImage(); //get the image of the current room
            //need the description and the objects in room
            roomDescLabel.setText(model.getPlayer().getCurrentRoom().getRoomDescription() + "\n\nObjects in this room:\n" + model.getPlayer().getCurrentRoom().getObjectString());
            roomDescLabel.setPrefWidth(500); //set width to 500
            roomDescLabel.setPrefHeight(500); //set height to 500
            roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
            roomDescLabel.setWrapText(true);
            VBox roomPane = new VBox(roomImageView,roomDescLabel);
            roomPane.setPadding(new Insets(10));
            roomPane.setAlignment(Pos.TOP_CENTER);
            roomPane.setStyle("-fx-background-color: #000000;");
            gridPane.add(roomPane, 1, 1);
            stage.sizeToScene();
            //articulateRoomDescription();

            //set the helpToggle to FALSE
            helpToggle = false;
        }
    }

    /**
     * This method handles the event related to the
     * help button.
     */
    public void addInstructionEvent() {
        helpButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            showInstructions();
        });
    }

    /**
     * This method handles the event related to the
     * save button.
     */
    public void addSaveEvent() {
        saveButton.setOnAction(e -> {
            gridPane.requestFocus();
            SaveView saveView = new SaveView(this);
        });
    }

    /**
     * This method handles the event related to the
     * load button.
     */
    public void addLoadEvent() {
        loadButton.setOnAction(e -> {
            gridPane.requestFocus();
            LoadView loadView = new LoadView(this);
        });
    }


    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = this.model.getPlayer().getCurrentRoom().getRoomName();

        if (!this.model.getPlayer().getCurrentRoom().getVisited()) musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-long.mp3" ;
        else musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-short.mp3" ;
        musicFile = musicFile.replace(" ","-");

        Media sound = new Media(new File(musicFile).toURI().toString());

        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlaying = true;

    }

    /**
     * This method stops articulations
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }
}

