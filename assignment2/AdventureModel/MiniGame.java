package AdventureModel;
import java.util.ArrayList;


public interface MiniGame {
    public boolean playGame(Player player, String answer, int index);
    public ArrayList getQuestionList();
    public ArrayList getAnswerList();

    public String getClueName(int currIdx);
}



