package AdventureModel;
import java.util.*;

public class crimeMiniGame implements MiniGame {
    private final ArrayList<String> cluesList;
    private final ArrayList<AdventureObject> objects;
    private int currIndex;
    private ArrayList<String> questionList;
    private ArrayList<String> answerList;

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
        if (answer.toUpperCase().strip().equals(answerList.get(index).toUpperCase().strip())) {
            player.addToInventory(objects.get(index));
            player.getCurrentRoom().deleteObject(objects.get(index).getName());
            this.currIndex += 1;
            return true;
        } else {
            return false;
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
