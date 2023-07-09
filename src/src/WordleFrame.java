import javax.swing.*;
import java.awt.*;

public class WordleFrame extends JFrame implements WordleView {

    private WordleModel model;
    private JButton[][] buttons;

    public WordleFrame() {
        super("Wordle");

        model = new WordleModel(getPlayerWord());
        model.addWordView(this);
        buttons =  new JButton[WordleModel.BOARD_SIZE][WordleModel.BOARD_SIZE];

        initGui();
    }

    private void initGui() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JPanel grid = new JPanel(new GridLayout(WordleModel.BOARD_SIZE, WordleModel.BOARD_SIZE));
        grid.setPreferredSize(new Dimension(600, 500));

        // Add Buttons to the frame
        for(int i = 0; i < WordleModel.BOARD_SIZE; i++){
            for(int j = 0; j < WordleModel.BOARD_SIZE; j++){
                JButton b = new JButton("");
                b.setBorder(BorderFactory.createLineBorder(Color.lightGray));
                buttons[i][j] = b;

                grid.add(b);
            }
        }

        this.add(grid, BorderLayout.NORTH);

        // Add text field to frame
        JTextField inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(600, 100));
        inputField.addActionListener(e -> {
            playGuessedWord(inputField);
        });
        this.add(inputField, BorderLayout.SOUTH);

        this.setPreferredSize(new Dimension(600, 600));
        this.pack();
        this.setVisible(true);
    }

    private void playGuessedWord(JTextField inputField) {
        String word = inputField.getText();
        if(word.length() == WordleModel.BOARD_SIZE){
            model.play(word);
        }
        inputField.setText("");
    }

    private String getPlayerWord() {
        String word = "";
        while(word.length() != WordleModel.BOARD_SIZE){
            String message = "Please enter the 5-character word  to be guessed";
            word = JOptionPane.showInputDialog(this, message);
        }
        return word;
    }


    @Override
    public void update(WordleEvent e) {
        if(e.getStatus().equals(WordleModel.GameState.WON) || e.getStatus().equals(WordleModel.GameState.LOST)){
            populateGrid(e);

            endGame(e.getStatus());
        }

        if(e.getStatus().equals(WordleModel.GameState.UNDECIDED)){
            populateGrid(e);
        }
    }

    private void endGame(WordleModel.GameState status) {
        String winMessage = "Congrats, you won!";
        String loseMessage = "Boo, you lost!";

        switch (status){
            case WON -> JOptionPane.showMessageDialog(this, winMessage);
            case LOST -> JOptionPane.showMessageDialog(this, loseMessage);
        }

        System.exit(0);
    }

    private void populateGrid(WordleEvent e) {
        String[] letters = e.getWord().split("");

        for(int i = 0; i < WordleModel.BOARD_SIZE; i++){
            buttons[e.getAttempts()][i].setText(letters[i]);
            changeColor(e.getAttempts(), i, Color.GRAY);

            if(e.getStatus().equals(WordleModel.GameState.WON)){
                changeColor(e.getAttempts(), i, Color.GREEN);
            }

            if(e.getStatus().equals(WordleModel.GameState.UNDECIDED)
                    || e.getStatus().equals(WordleModel.GameState.LOST)){
                if(e.getValidLetters().contains(letters[i].toLowerCase())){
                    changeColor(e.getAttempts(), i, Color.YELLOW);
                }

                if(e.getValidPosition().contains(i)){
                    changeColor(e.getAttempts(), i, Color.GREEN);
                }
            }
        }

    }

    private void changeColor(int attempts, int index, Color color) {
        buttons[attempts][index].setBackground(color);
        buttons[attempts][index].setOpaque(true);
        buttons[attempts][index].setBorderPainted(true);
    }

    public static void main(String[] args) {
        new WordleFrame();
    }
}
