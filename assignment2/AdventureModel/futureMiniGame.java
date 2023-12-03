package AdventureModel;

import java.util.ArrayList;
import java.util.List;

public class futureMiniGame implements MiniGame {
    public ArrayList<String> cluesList;
    public ArrayList<String> questionList;
    ArrayList<String> answerList;

    public futureMiniGame() {
        this.questionList = new ArrayList<String>(List.of(
                "Besides Earth, which is the only world in our solar system known to have liquid lakes and seas on " +
                        "the surface?",
                "Who was the first human in space? We will provide the answer (Yuri Gagarin) with unordered letters " +
                        "and the user will have to re-order the letters to give the right answer with ordered letters.",
                "NASA's Perseverance Mars rover captured \"Bettys Rock\" on June 20, 2022. The rock is named after " +
                        "Bettys Rock in what national park?"
        ));

        this.answerList = new ArrayList<String>(List.of(
                "titan",
                "inrgauiray",
                "shenandoah"
        ));

        this.cluesList = new ArrayList<String>(List.of(
                "FutureObject1",
                "FutureObject2",
                "FutureObject3"
        ));
    }

    /**
     * Print FutureMiniGame instructions for the user
     */
    public void giveInstructions() {
        //replace this!
        System.out.println("\nYou are playing the FUTURE room minigames that will get you access to the clue. " +
                "You must answer me the question.");
        // printing a random Question
        System.out.println("READ the following question and answer. Feel free to click on the clue images on the right.\n");
    }

    /**
     * Play the Future MiniGame
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame(Player player, String answer, int currIndex) {
        giveInstructions();
        // change println to label and display in GUI
        System.out.println(answer.toUpperCase().strip() + " " + answerList.get(currIndex).toUpperCase().strip());

        // IF ANSWER IS CORRECT
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
            System.out.println("INCORRECT! The answer to the clue was: " + answerList.get(currIndex));
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
