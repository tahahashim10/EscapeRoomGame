package AdventureModel;
import java.util.*;

public class crimeMiniGame implements MiniGame {
    private final ArrayList<String> cluesList;
    private final ArrayList<AdventureObject> objects;
    private int currIndex;
    public ArrayList<String> questionList;
    ArrayList<String> answerList;

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
     * Print CrimeMiniGame instructions for the user
     */
    public void giveInstructions() {
        //replace this!
        System.out.println("\nYou are playing CRIME room Minigames that will get you access to the clue. " +
                "You must answer the question.");
        // printing a random Question
        System.out.println("READ the following question and answer. Feel free to click on the clue images on the right for a zoom-in!");
        System.out.println("Please type 'ANSWER' first, then your response. (ex/ ANSWER John Doe) \n");
    }

    /**
     * Play the Crime MiniGame
     *
     * @return true if player wins the game, else false
     */
    @Override
    public boolean playGame(Player player, String answer, int currIdx) {
        giveInstructions();

        // Show user answer and correct answer in terminal
        System.out.println(answer.toUpperCase().strip() + " | " + answerList.get(currIdx).toUpperCase().strip());

        // IF ANSWER IS CORRECT
        if (answer.toUpperCase().strip().equals(answerList.get(currIdx).toUpperCase().strip())) {

            if (currIdx < 0 || currIdx >= player.getCurrentRoom().objectsInRoom.size()) {
                currIdx = player.getCurrentRoom().objectsInRoom.size() - 1;
                // Handle the invalid index case
                System.out.println("CurrIdx exceeds Objects in Room.");
            }
            boolean firstAnsCorrect = false;

            if (player.getCurrentRoom().objectsInRoom.get(currIdx).getName().equals("CrimeObject1")) {
                System.out.println("1st case");
                player.addToInventory(player.getCurrentRoom().objectsInRoom.get(currIdx));
                player.getCurrentRoom().objectsInRoom.remove(currIdx);
                currIdx--;
                return true;
            }
            if (player.getCurrentRoom().objectsInRoom.get(currIdx).getName().equals("CrimeObject2")) {
                System.out.println("2nd case");
                player.addToInventory(player.getCurrentRoom().objectsInRoom.get(currIdx));
                player.getCurrentRoom().objectsInRoom.remove(currIdx);
                return true;
            }
            if (player.getCurrentRoom().objectsInRoom.get(currIdx).getName().equals("CrimeObject3")) {
                System.out.println("3rd case");
                player.addToInventory(player.getCurrentRoom().objectsInRoom.get(currIdx));
                player.getCurrentRoom().objectsInRoom.remove(currIdx);
                return true;
            }
            if (firstAnsCorrect && currIdx==0) {
                player.addToInventory((player.getCurrentRoom().objectsInRoom2.get(0)));
                player.getCurrentRoom().objectsInRoom.remove(0);
                currIdx--;
                return true;
            }
            this.currIndex += 1;
            return true;
        }
        // IF ANSWER IS WRONG
        else {
            System.out.println("INCORRECT, try again...");
            currIdx--;
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
