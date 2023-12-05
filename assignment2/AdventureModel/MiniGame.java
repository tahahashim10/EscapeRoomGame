package AdventureModel;
import java.util.ArrayList;


/**
 * The MiniGame interface defines the structure of mini-games within an adventure game.
 * It provides methods to play the game, retrieve questions and answers, and get a clue name based on an index.
 */
public interface MiniGame {
    /**
     * Plays a mini-game round.
     *
     * @param player The player participating in the mini-game.
     * @param answer The player's answer for the current round.
     * @param currIndex The current index or round number in the mini-game.
     * @return true if the player's answer is correct, false otherwise.
     */
    boolean playGame(Player player, String answer, int currIndex);

    /**
     * Retrieves the list of questions for the mini-game.
     *
     * @return A list of questions.
     */
    ArrayList getQuestionList();

    /**
     * Retrieves the list of answers for the mini-game.
     *
     * @return A list of answers.
     */
    ArrayList getAnswerList();

    /**
     * Retrieves the name of the clue associated with a specific index in the mini-game.
     *
     * @param currIdx The index for which the clue name is to be retrieved.
     * @return The name of the clue.
     */
    String getClueName(int currIdx);
}



