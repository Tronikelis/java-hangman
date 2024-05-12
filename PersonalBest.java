import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersonalBest {
    private String FILENAME = "./personal_best.txt";

    public PersonalBest() throws IOException {
    }

    public void set(Integer pb) throws IOException {
        Files.writeString(Path.of(FILENAME), String.valueOf(pb));
    }

    public Integer get() throws IOException {
        String data = Files.readString(Path.of(FILENAME)).trim();

        Integer pb = 0;

        try {
            pb = Integer.parseInt(data);
        } catch (Exception e) {
        }

        return pb;
    }
}
