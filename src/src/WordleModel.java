import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordleModel {

    public static final int BOARD_SIZE = 5;
    private final String wordToBeGuessed;
    private final ArrayList<WordleView> views;
    private GameState status;
    private int attempts;

    public enum GameState {
        WON,
        UNDECIDED,
        LOST
    }

    public WordleModel(String word) {
        this.wordToBeGuessed = word;
        this.views = new ArrayList<>();
        this.status  = GameState.UNDECIDED;
        this.attempts = 0;
    }

    public void play(String wordPlayed) {
        if(wordPlayed.equals(wordToBeGuessed)){
            this.status = GameState.WON;
            updateViews(new WordleEvent(status, attempts, wordPlayed));
            return;
        }

        String[] guessWord = this.wordToBeGuessed.split("");
        String[] playedLetters = wordPlayed.split("");

        ArrayList<String> validLetters  = new ArrayList<>();
        ArrayList<Integer> validPosition = new ArrayList<>();
        List<String> guessWordAsList = Arrays.asList(guessWord);

        // Get Valid Letter
        for(String p : playedLetters){
            if(guessWordAsList.contains(p)){
                validLetters.add(p);
            }
        }

        //Get Valid Position
        for(int i = 0; i < BOARD_SIZE; i++){
            if(guessWord[i].equals(playedLetters[i])){
                validPosition.add(i);
            }
        }

        if(attempts == BOARD_SIZE - 1){
            this.status = GameState.LOST;
        }

        updateViews(new WordleEvent(status, attempts, wordPlayed, validLetters, validPosition));
        attempts++;
    }

    private void updateViews(WordleEvent wordleEvent) {
        for(WordleView v :  views){
            v.update(wordleEvent);
        }
    }

    public void addWordView(WordleFrame wordleFrame) {
        this.views.add(wordleFrame);
    }
}
