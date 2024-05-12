import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import java.io.FileWriter;

public class AddWord {
    private void appendToDb(String data) throws IOException {
        File file = new File("words.txt");
        FileWriter fr = new FileWriter(file, true);

        fr.write("\n" + data);
        fr.close();
    }

    public void init() throws IOException {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        System.out.println("Write words to add, they can only contain english characters");
        System.out.println("Press enter without typing anything to exit");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String input = bufferedReader.readLine().trim();

        if (input.length() == 0) {
            return;
        }

        Matcher matcher = pattern.matcher(input);

        String matched = "";

        while (matcher.find()) {
            matched += matcher.group();
        }

        if (matched.compareToIgnoreCase(input) != 0) {
            System.out.println("invalid input");

            init();
            return;
        }

        System.out.println("adding: " + matched);

        appendToDb(matched);
        init();
    }
}
