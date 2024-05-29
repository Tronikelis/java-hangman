import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ActionChooser actionChooser = new ActionChooser();
        Action action = actionChooser.choose();

        switch (action) {
            case PLAY: {
                Hangman hangman = new Hangman(action);
                hangman.init("words.txt"); // for difficulty based on words maybe
                break;
            }

            case PLAY_HARD: {
                Hangman hangman = new Hangman(action);
                hangman.init("hard_words.txt"); // for difficulty based on words maybe
                break;
            }

            case ADD_WORD: {
                AddWord addWord = new AddWord();
                addWord.init();
                break;
            }

            default:
                break;
        }
    }
}
