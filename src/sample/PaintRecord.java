package sample;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Paul Dennis on 1/15/2017.
 */
public class PaintRecord {

    public static final String FILE_NAME = "paint_record.json";

    private List<String> record;

    public PaintRecord () {
        record = new ArrayList<>();
    }

    public void add (String string) {
        record.add(string);
    }

    public List<String> getRecord () {
        return record;
    }

    public void writeToFile (String fileName) {
        File file = new File(fileName);
        String jsonString = this.jsonSave();
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonString);
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static PaintRecord readFromFile (String fileName) {
        File file = new File(fileName);
        try {
            Scanner fileScanner = new Scanner(file);
            String jsonFileString = fileScanner.nextLine();
            return jsonRestore(jsonFileString);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String jsonSave () {
        JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
        String jsonString = jsonSerializer.serialize(this);
        return jsonString;
    }

    public static PaintRecord jsonRestore(String jsonString) {
        JsonParser listParser = new JsonParser();
        PaintRecord record = listParser.parse(jsonString, PaintRecord.class);
        return record;
    }
}
