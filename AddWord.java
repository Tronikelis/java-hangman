import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class AddWord {
    public void init() throws IOException {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        System.out.println("Write words to add, they can only contain english characters");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String input = bufferedReader.readLine().trim();
        Matcher matcher = pattern.matcher(input);

        String matched = matcher.group(0).toLowerCase();

        if (matched.compareTo(input.toLowerCase()) != 0) {
            System.out.println("invalid input");

            init();
            return;
        }

        System.out.println("adding: " + matched);

    }
}
