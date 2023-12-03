package AdventureModel;

import AdventureModel.*;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.Objects;

public class AdventureGameController {
    AdventureGame model;

    public String interpretActionController(String command, AdventureGame game){
        this.model = game;

        String[] inputArray = model.tokenize(command); //look up synonyms
        System.out.println(inputArray[0]);


        // ADDED FOR PLAYMINIGAME(8)
        if (inputArray[0].equals("PLAY")) {
            /// ANGELA **************************************
            return "PLAY";
        }
        else if (inputArray[0].equals("ANSWER")) {
            return "ANSWER";
        }

        PassageTable motionTable = this.model.player.getCurrentRoom().getMotionTable(); //where can we move?

        if (motionTable.optionExists(inputArray[0])) {
            if (!model.movePlayer(inputArray[0])) {
                if (this.model.player.getCurrentRoom().getMotionTable().getDirection().get(0).getDestinationRoom() == 0)
                    return "GAME OVER";
                else return "FORCED";
            } //something is up here! We are dead or we won.
            return null;
        } else if(Arrays.asList(this.model.actionVerbs).contains(inputArray[0])) {
            if(inputArray[0].equals("QUIT")) { return "GAME OVER"; } //time to stop!
            else if(inputArray[0].equals("INVENTORY") && this.model.player.getInventory().size() == 0) return "INVENTORY IS EMPTY";
            else if(inputArray[0].equals("INVENTORY") && this.model.player.getInventory().size() > 0) return "THESE OBJECTS ARE IN YOUR INVENTORY:\n" + this.model.player.getInventory().toString();
            else if(inputArray[0].equals("VIEW") && inputArray.length < 2) return "THE TAKE COMMAND REQUIRES A CLUE";
            else if(inputArray[0].equals("VIEW") && inputArray.length == 2) {
                if(this.model.player.getInventory().contains(inputArray[1])){
                    return this.model.player.getObjectByName(inputArray[1]).getAnswer();
                } else {
                    return "THIS CLUE IS NOT IN YOUR INVENTORY:\n " + inputArray[1];
                }
            }
            else if(inputArray[0].equals("PASSWORD") && inputArray.length < 2) return "EMPTY PASSWORD.";
            else if(inputArray[0].equals("PASSWORD") && inputArray.length == 2) {
                if(Objects.equals(this.model.player.getCurrentRoom().getRoomPassword(), inputArray[1])){
                    if(model.player.getCurrentRoom().getRoomNumber() + 1 <= 4){
                        this.model.player = new Player(this.model.rooms.get(model.player.getCurrentRoom().getRoomNumber() + 1));
                        for (Room room : this.model.rooms.values()) {
                            room.reset();
                        }
                        return null;
                    } else {
                        this.model.movePlayer("WIN");
                        return "VICTORY";
                    }

                } else{
                    return "INCORRECT PASSWORD.";
                }
            }
        }
        return "INVALID COMMAND.";
    }
}

