package AdventureModel;
import views.AdventureGameView;

import java.util.Arrays;

public class AdventureGameController {
    AdventureGame model;

    public String interpretActionController(String command, AdventureGame game) {
        this.model = game;

        String[] inputArray = model.tokenize(command); //look up synonyms
        System.out.println(inputArray[0]);
        PassageTable motionTable = this.model.player.getCurrentRoom().getMotionTable(); //where can we move?
        if (inputArray[0].equals("PLAY")) {

            /// ANGELA **************************************
            return "PLAY";
        } else if (inputArray[0].equals("ANSWER")) {
            return "ANSWER";

        }


        if (motionTable.optionExists(inputArray[0])) {
                if (!model.movePlayer(inputArray[0])) {
                    if (this.model.player.getCurrentRoom().getMotionTable().getDirection().get(0).getDestinationRoom() == 0)
                        return "GAME OVER";
                    else return "FORCED";
                } //something is up here! We are dead or we won.
                return null;
            } else if (Arrays.asList(this.model.actionVerbs).contains(inputArray[0])) {
                if (inputArray[0].equals("QUIT")) {
                    return "GAME OVER";
                } //time to stop!
                else if (inputArray[0].equals("INVENTORY") && this.model.player.getInventory().size() == 0)
                    return "INVENTORY IS EMPTY";
                else if (inputArray[0].equals("INVENTORY") && this.model.player.getInventory().size() > 0)
                    return "THESE OBJECTS ARE IN YOUR INVENTORY:\n" + this.model.player.getInventory().toString();
                else if (inputArray[0].equals("TAKE") && inputArray.length < 2)
                    return "THE TAKE COMMAND REQUIRES AN OBJECT";
                else if (inputArray[0].equals("DROP") && inputArray.length < 2)
                    return "THE DROP COMMAND REQUIRES AN OBJECT";
                else if (inputArray[0].equals("TAKE") && inputArray.length == 2) {
                    if (this.model.player.getCurrentRoom().checkIfObjectInRoom(inputArray[1])) {
                        this.model.player.takeObject(inputArray[1]);
                        return "YOU HAVE TAKEN:\n " + inputArray[1];
                    } else {
                        return "THIS OBJECT IS NOT HERE:\n " + inputArray[1];
                    }
                } else if (inputArray[0].equals("DROP") && inputArray.length == 2) {
                    if (this.model.player.checkIfObjectInInventory(inputArray[1])) {
                        this.model.player.dropObject(inputArray[1]);
                        return "YOU HAVE DROPPED:\n " + inputArray[1];
                    } else {
                        return "THIS OBJECT IS NOT IN YOUR INVENTORY:\n " + inputArray[1];
                    }
                }
            }

        return "INVALID COMMAND.";
    }

}
