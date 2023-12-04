package AdventureModel;

import java.util.ArrayList;
import java.util.List;

public class futureMiniGame implements MiniGame {
    private final ArrayList<String> cluesList;
    private final ArrayList<AdventureObject> objects;
    private int currIndex;
    private ArrayList<String> questionList;
    private ArrayList<String> answerList;


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
        if (answer.toUpperCase().strip().equals(answerList.get(index).toUpperCase().strip())) {
            player.addToInventory(objects.get(index));
            System.out.println(objects.get(index).getName());
            player.getCurrentRoom().deleteObject(objects.get(index).getName());
            System.out.println(player.getInventory());
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
