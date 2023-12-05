package AdventureModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The futureMiniGame class represents a mini-game focused on solving crimes.
 * It implements the MiniGame interface and provides specific functionality for a future-themed game.
 */
public class futureMiniGame implements MiniGame {
    private final ArrayList<String> cluesList; // List of clues in the mini-game
    private final ArrayList<AdventureObject> objects; // // List of AdventureObject objects in the game
    private int currIndex; // The current index
    public ArrayList<String> questionList; // List of questions to be presented in the mini-game
    ArrayList<String> answerList; // List of correct answers corresponding to the questions

    /**
     * Constructor for futureMinigame. Initializes questions, answers, clues, and objects related to the game.
     * @param player The player participating in the mini-game.
     */
    public futureMiniGame(Player player) {
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

        this.currIndex = 0;

        AdventureObject FutureObject1 = new AdventureObject(
                "FutureObject1", "Besides Earth, which is the only world in our solar system known to have liquid lakes and seas on the surface?",
                player.getCurrentRoom(),"titan");
        AdventureObject FutureObject2 = new AdventureObject(
                "FutureObject2", "Who was the first human in space? We will provide the answer (Yuri Gagarin) with unordered letters and the user will have to re-order the letters to give the right answer with ordered letters.",
                player.getCurrentRoom(),"inrgauiray");
        AdventureObject FutureObject3 = new AdventureObject(
                "FutureObject3", "NASA's Perseverance Mars rover captured \"Bettys Rock\" on June 20, 2022. The rock is named after Bettys Rock in what national park?",
                player.getCurrentRoom(),"Shenandoah");

        this.objects = new ArrayList<AdventureObject>(List.of(FutureObject1, FutureObject2, FutureObject3));
    }


    /**
     * Play the Future MiniGame
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
