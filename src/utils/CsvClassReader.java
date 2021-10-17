package utils;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CsvClassReader {

    public static Map<Integer, String> parse(){
        String strFile = "files/glass.names";
        Map<Integer, String> classes = new HashMap<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(strFile));
            String [] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                classes.put(Integer.parseInt(nextLine[0]), nextLine[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }
}
