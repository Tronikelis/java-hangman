import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ActionChooser actionChooser = new ActionChooser();
        Action action = actionChooser.choose();

        switch (action) {
            case PLAY:
                Hangman hangman = new Hangman();
                hangman.init("words.txt"); // for difficulty based on words maybe
                break;

            case ADD_WORD:
                AddWord addWord = new AddWord();
                addWord.init();
                break;

            case GUESS_LONG_WORD:
                Hangman hangmanLongWord = new Hangman();
                hangmanLongWord.guessLongWord("asian_difficulty_words.txt");
                break;

            default:
                break;
        }
    }
}
