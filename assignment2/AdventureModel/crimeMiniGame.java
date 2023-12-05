package AdventureModel;
import java.util.*;

/**
 * The crimeMiniGame class represents a mini-game focused on solving crimes.
 * It implements the MiniGame interface and provides specific functionality for a crime-themed game.
 */
public class crimeMiniGame implements MiniGame {
    private final ArrayList<String> cluesList; // List of clues in the mini-game
    private final ArrayList<AdventureObject> objects; // // List of AdventureObject objects in the game
    private int currIndex; // The current index
    public ArrayList<String> questionList; // List of questions to be presented in the mini-game
    ArrayList<String> answerList; // List of correct answers corresponding to the questions

    /**
     * Constructor for crimeMiniGame. Initializes questions, answers, clues, and objects related to the game.
     * @param player The player participating in the mini-game.
     */
    public crimeMiniGame(Player player) {
        this.questionList = new ArrayList<String>(List.of(
                "Which man was the most famous murderer in United States History?",
                "True or false. The fingerprint on the left matches the right.",
                "Decode code the word uzsnxmrjsy (Hint: it is a crime related word)"
        ));

        this.answerList = new ArrayList<String>(List.of(
                "gary ridgway",
                "True",
                "Punishment"
        ));

        this.cluesList = new ArrayList<String>(List.of(
                "CrimeObject1",
                "CrimeObject2",
                "CrimeObject3"
        ));

        this.currIndex = 0;

        AdventureObject CrimeObject1 = new AdventureObject(
                "CrimeObject1", "Which man was the most famous murderer in United States History?",
                player.getCurrentRoom(),"gary ridgway");
        AdventureObject CrimeObject2 = new AdventureObject(
                "CrimeObject2", "True or false. The fingerprint on the left matches the right.",
                player.getCurrentRoom(),"true");
        AdventureObject CrimeObject3 = new AdventureObject(
                "CrimeObject3", "Decode code the word uzsnxmrjsy (Hint: it is a crime related word)",
                player.getCurrentRoom(),"punishment");

        this.objects = new ArrayList<AdventureObject>(List.of(CrimeObject1, CrimeObject2, CrimeObject3));
    }

    /**
     * Play the Crime MiniGame
     *
     * @return true if player wins the game, else false
     */
    @Override
    public boolean playGame(Player player, String answer, int index) {
        // If user answers the given question correctly
        if (answer.toUpperCase().strip().equals(answerList.get(index).toUpperCase().strip())) {
            // add the clue to their inventory and remove it from the current room
            player.addToInventory(objects.get(index));
            player.getCurrentRoom().deleteObject(objects.get(index).getName());
            this.currIndex += 1;// increment index so it goes to next question
            return true; // return true as player wins game (gets answer correct)
        }
        else {
            return false; // return false as user got answer wrong
        }
    }

    /**
     * Gets the Questions in the room
     * @return arraylist with the questions.
     */
    @Override
    public ArrayList getQuestionList() {
        return this.questionList;
    }


    /**
     * Gets the correct answers in the room
     * @return arraylist with the answers.
     */
    @Override
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
