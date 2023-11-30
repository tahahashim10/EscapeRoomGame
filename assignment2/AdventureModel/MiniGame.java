package AdventureModel;

import java.util.ArrayList;

public interface MiniGame {
   boolean playGame(String answer, int currIndex);
   ArrayList getQuestionList();

   ArrayList getAnswerList();


}
