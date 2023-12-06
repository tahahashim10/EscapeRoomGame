package AdventureModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The zombieMinigame class represents a mini-game focused on solving crimes.
 * It implements the MiniGame interface and provides specific functionality for a zombie-themed game.
 */
public class zombieMiniGame implements MiniGame {

    private final ArrayList<String> cluesList; // List of clues in the mini-game
    private final ArrayList<AdventureObject> objects; // // List of AdventureObject objects in the game
    private int currIndex; // The current index
    public ArrayList<String> questionList; // List of questions to be presented in the mini-game
    ArrayList<String> answerList; // List of correct answers corresponding to the questions

    /**
     * Constructor for zombieMiniGame. Initializes questions, answers, clues, and objects related to the game.
     * @param player The player participating in the mini-game.
     */
    public zombieMiniGame(Player player) {

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

        this.currIndex = 0;

        AdventureObject ZombieObject1 = new AdventureObject(
                "ZombieObject1", "If the zombies move at a speed of 2.5 feet per second, and the survivors need to cross a room that is 13 feet wide, how many seconds do they have to safely cross before the zombies catch up?",
                player.getCurrentRoom(),"5.2");
        AdventureObject ZombieObject2 = new AdventureObject(
                "ZombieObject2", "What is the name of the mushroom that causes the epidemy in the show and game The Last of Us?",
                player.getCurrentRoom(),"cordyceps");
        AdventureObject ZombieObject3 = new AdventureObject(
                "ZombieObject3", "Decode the name of this popular zombie game (Resident Evil)",
                player.getCurrentRoom(),"01010010 01100101 01110011 01101001 01100100 01100101 01101110 01110100 00100000 01000101 01110110 01101001 01101100");

        this.objects = new ArrayList<AdventureObject>(List.of(ZombieObject1, ZombieObject2, ZombieObject3));
    }


    /**
     * Play the Zombie MiniGame
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
            this.currIndex += 1; // increment index so it goes to next question
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
