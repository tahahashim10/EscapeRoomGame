package AdventureModel;

import java.util.ArrayList;
import java.util.List;

public class prisonMiniGame implements MiniGame {
    private final ArrayList<String> cluesList;

    private final ArrayList<AdventureObject> objects;
    private int currIndex;
    public ArrayList<String> questionList;
    ArrayList<String> answerList;

    public prisonMiniGame(Player player) {
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

        this.currIndex = 0;

        AdventureObject PrisonObject1 = new AdventureObject(
                "PrisonObject1", "Which man was the most famous murderer in United States History?",
                player.getCurrentRoom(),"gary ridgway");
        AdventureObject PrisonObject2 = new AdventureObject(
                "PrisonObject2", "True or false. The fingerprint on the left matches the right.",
                player.getCurrentRoom(),"true");
        AdventureObject PrisonObject3 = new AdventureObject(
                "PrisonObject3", "Decode code the word uzsnxmrjsy (Hint: it is a crime related word)",
                player.getCurrentRoom(),"punishment");

        this.objects = new ArrayList<AdventureObject>(List.of(PrisonObject1, PrisonObject2, PrisonObject3));

    }


    /**
     * Play the Prison MiniGame
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
