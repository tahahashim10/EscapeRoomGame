package AdventureModel;
import java.util.ArrayList;


public interface MiniGame {
    boolean playGame(Player player, String answer, int currIndex);
    ArrayList getQuestionList();
    ArrayList getAnswerList();

    String getClueName(int currIdx);
}



