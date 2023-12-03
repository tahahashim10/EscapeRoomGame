package AdventureModel;

import java.util.ArrayList;
import java.util.List;

public class zombieMiniGame implements MiniGame {

    public ArrayList<String> cluesList;
    public ArrayList<String> questionList;
    ArrayList<String> answerList;

    public zombieMiniGame() {

        this.questionList = new ArrayList<String>(List.of(
                "If the zombies move at a speed of 2.5 feet per second, and the survivors need to cross a room that " +
                        "is 13 feet wide, how many seconds do they have to safely cross before the zombies catch up?",
                "What is the name of the mushroom that causes the epidemy in the show and game The Last of Us?",
                "Decode the name of this popular zombie game (Resident Evil)"
        ));


        this.answerList = new ArrayList<String>(List.of(
                "5.2",
                "cordyceps",
                "01010010 01100101 01110011 01101001 01100100 01100101 01101110 01110100 00100000 01000101 01110110 01101001 01101100"
        ));

        this.cluesList = new ArrayList<String>(List.of(
                "ZombieObject1",
                "ZombieObject2",
                "ZombieObject3"
        ));
    }

    /**
     * Print ZombieMiniGame instructions for the user
     */
    public void giveInstructions() {
        //replace this!
        System.out.println("You are playing a minigame that will get you access to the clue. " +
                "You must answer me the question.");
        // printing a random Question
        System.out.println("READ the following question and answer. Feel free to click on the clue images on the right.\n");
        System.out.println("Please type 'ANSWER' first, then your response. (ex: ANSWER John Doe) \n");
    }

    /**
     * Play the Zombie MiniGame
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
        // IF ANSWER IS WRONG and keep the clue in that room.
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
