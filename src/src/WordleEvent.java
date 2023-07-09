import java.util.ArrayList;

public class WordleEvent {
    private final WordleModel.GameState status;
    private final int attempts;
    private final String word;
    private ArrayList<String> validLetters;
    private ArrayList<Integer> validPosition;

    public WordleEvent(WordleModel.GameState status, int attempts, String wordPlayed) {
        this.status = status;
        this.attempts = attempts;
        this.word = wordPlayed;
    }

    public ArrayList<String> getValidLetters() {
        return validLetters;
    }

    public ArrayList<Integer> getValidPosition() {
        return validPosition;
    }

    public WordleEvent(WordleModel.GameState status, int attempts, String wordPlayed,
                       ArrayList<String> validLetters, ArrayList<Integer> validPosition) {
        this.status = status;
        this.attempts = attempts;
        this.word = wordPlayed;
        this.validLetters = validLetters;
        this.validPosition = validPosition;
    }

    public WordleModel.GameState getStatus() {
        return status;
    }

    public int getAttempts() {
        return attempts;
    }

    public String getWord() {
        return word.toUpperCase();
    }
}
