package AdventureModel;

import java.util.ArrayList;
import java.util.List;

public class prisonMiniGame implements MiniGame {
    public ArrayList<String> cluesList;
    public ArrayList<String> questionList;
    ArrayList<String> answerList;

    public prisonMiniGame() {
        this.questionList = new ArrayList<String>(List.of(
                "What legal term is used to describe the temporary release of a prisoner before the completion of " +
                        "their sentence, often for good behavior?",
                "What is the most famous Prisoner in History?",
                "Decode this room with this punishment: ptwypzvutlua (using caesar cipher 7)"
        ));

        this.answerList = new ArrayList<String>(List.of(
                "parole",
                "robert stroud",
                "imprisonment"
        ));

        this.cluesList = new ArrayList<String>(List.of(
                "PrisonObject1",
                "PrisonObject2",
                "PrisonObject3"
        ));
    }

    /**
     * Print PrisonMiniGame instructions for the user
     */
    public void giveInstructions() {
        //replace this!
        System.out.println("You are playing a the PRISON room Minigames that will get you access to the clue. " +
                "You must answer me the question.");
        // printing a random Question
        System.out.println("READ the following question and answer. Feel free to click on the clue images on the right.\n");
        System.out.println("Please type 'ANSWER' first, then your response. (ex: ANSWER John Doe) \n");
    }

    /**
     * Play the Prison MiniGame
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame(Player player, String answer, int currIndex) {
        giveInstructions();
        // change println to label and display in GUI
        System.out.println("My Answer: " + answer.toUpperCase().strip() + " | " + answerList.get(currIndex).toUpperCase().strip());

        // if user gets the answer correct
        if (answer.toUpperCase().strip().equals(answerList.get(currIndex).toUpperCase().strip())) {

            // Add the clue to the player's inventory
            ArrayList<AdventureObject> listObjectsInRoom = player.getCurrentRoom().objectsInRoom;
            player.addToInventory(listObjectsInRoom.get(currIndex));

            // Remove the clue from the room
            player.getCurrentRoom().objectsInRoom.remove(currIndex);

            return true;
        }
        // IF ANSWER IS WRONG
        else {
            System.out.println("INCORRECT, try again...");
            return false;
        }
    }

    /**
     * Gets the Questions in the room
     * @return arraylist with the questions.
     */
    public ArrayList getQuestionList() {
        return this.questionList;
    }


    /**
     * Gets the correct answers in the room
     * @return arraylist with the answers.
     */
    public ArrayList getAnswerList() {
        return this.answerList;
    }

    /**
     * Gets the name of the clue
     * @return String with the name of the clue.
     */
    @Override
    public String getClueName (int currIdx) {
        return this.cluesList.get(currIdx);
    }
}
