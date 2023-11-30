package AdventureModel;

import java.util.ArrayList;
import java.util.List;

public class crimeMiniGame implements MiniGame{
    public ArrayList<String> questionList;
    ArrayList <String> answerList;

    public crimeMiniGame (){
        this.questionList = new ArrayList<String>(List.of("Which man was the most famous murderer in United States History?",
                "True or false. The fingerprint on the left matches the right.",
                "Decode code this word that is related to Crime. (uzsnxmrjsy)"));


        this.answerList = new ArrayList<String>(List.of("gary ridgway", "True", "Punishment"));

    }

    /**
     * Print GameTroll instructions for the user
     */
    public void giveInstructions()
    {
        //replace this!
        System.out.println("You are playing a easy game. You must answer me the question.");
        // printing a random Question
        System.out.println("READ the following question and tell me \n");
        System.out.println("What is the answer to this question? \n");
    }

    /**
     * Play the GameTroll game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame(String answer, int currIndex) {
        giveInstructions();
        System.out.println(answer.toUpperCase().strip() + "dd" + answerList.get(currIndex).toUpperCase().strip());
        if (answer.toUpperCase().strip().equals(answerList.get(currIndex).toUpperCase().strip())) {
            System.out.println("win");

            return true;
        } else {
            System.out.println("WRONG!");
            System.out.println("The correct tone was a: " + answerList.get(currIndex));
            System.out.println("Better luck next time ... this time you MAY NOT PASS!!\n");
            return false;
        }


    }
    public ArrayList getQuestionList(){
        return this.questionList;
    }


    public ArrayList getAnswerList(){
        return this.answerList;
    }





}
