package AdventureModel;

import AdventureModel.*;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.Objects;

public class AdventureGameController {
    AdventureGame model;

    /**
     * Interpret Action Controller
     * __________________________
     *
     * Interprets player commands and performs corresponding actions in the adventure game.
     *
     * @param command the player's input command to interpret.
     * @param game the AdventureGame model representing the state of the game.
     * @return a string indicating the result or outcome of the interpreted command.
     */
    public String interpretActionController(String command, AdventureGame game) {

        // Set the AdventureGame model for the controller
        this.model = game;

        // Tokenize the input command to extract individual words
        String[] inputArray = model.tokenize(command); // Look up synonyms

        // Special handling for "PLAY" and "ANSWER" commands related to playing mini-games
        if (inputArray[0].equals("PLAY")) {
            return "PLAY";
        } else if (inputArray[0].equals("ANSWER")) {
            return "ANSWER";
        }

        // Retrieve the motion table for the current room
        PassageTable motionTable = this.model.player.getCurrentRoom().getMotionTable();

        // Check if the first word of the input command corresponds to a valid motion direction
        if (motionTable.optionExists(inputArray[0])) {
            if (!model.movePlayer(inputArray[0])) {
                // Check if the player's move resulted in a game over or victory
                if (this.model.player.getCurrentRoom().getMotionTable().getDirection().get(0).getDestinationRoom() == 0) {
                    return "GAME OVER";
                } else {
                    return "FORCED";
                }
            }
            // Player successfully moved to a new room
            return null;
        } else if (Arrays.asList(this.model.actionVerbs).contains(inputArray[0])) {
            // Check if the first word of the input command is a recognized action verb

            // Handle specific actions based on the recognized action verb
            if (inputArray[0].equals("QUIT")) {
                return "GAME OVER"; // Time to stop!
            } else if (inputArray[0].equals("INVENTORY") && this.model.player.getInventory().size() == 0) {
                return "INVENTORY IS EMPTY";
            } else if (inputArray[0].equals("INVENTORY") && this.model.player.getInventory().size() > 0) {
                return "THESE OBJECTS ARE IN YOUR INVENTORY:\n" + this.model.player.getInventory().toString();
            } else if (inputArray[0].equals("VIEW") && inputArray.length < 2) {
                return "THE VIEW COMMAND REQUIRES A CLUE";
            } else if (inputArray[0].equals("VIEW") && inputArray.length == 2) {
                if (this.model.player.getInventory().contains(inputArray[1])) {
                    return this.model.player.getObjectByName(inputArray[1]).getAnswer();
                } else {
                    return "THIS CLUE IS NOT IN YOUR INVENTORY:\n " + inputArray[1];
                }
            } else if (inputArray[0].equals("PASSWORD") && inputArray.length < 2) {
                return "EMPTY PASSWORD.";
            } else if (inputArray[0].equals("PASSWORD") && inputArray.length == 2) {
                if (Objects.equals(this.model.player.getCurrentRoom().getRoomPassword(), inputArray[1])) {
                    // Check if moving to the next room or achieving victory based on correct password
                    if (model.player.getCurrentRoom().getRoomNumber() + 1 <= 4) {
                        this.model.player = new Player(this.model.rooms.get(model.player.getCurrentRoom().getRoomNumber() + 1));
                        for (Room room : this.model.rooms.values()) {
                            room.reset();
                        }
                        return null;
                    } else {
                        this.model.movePlayer("WIN");
                        return "VICTORY";
                    }
                } else {
                    return "INCORRECT PASSWORD.";
                }
            }
        }

        // Command is not recognized or invalid
        return "INVALID COMMAND.";
    }
}

