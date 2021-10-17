package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

public class CsvDataReader {
    public static ProcessResult parse(int maxLine, int columnCount) {
        try {
            //csv file containing data
            String strFile = "files/glass.data";
            CSVReader reader = new CSVReader(new FileReader(strFile));
            String [] nextLine;
            double[][] matrice = new double[maxLine][columnCount-1];
            int[] classes = new int[maxLine];
            int i = 0, j;

            while ((nextLine = reader.readNext()) != null) {
                j = 0;
                    for (String token : nextLine) {
                        if(j == columnCount-1){
                            classes[i] = Integer.parseInt(token);
                        }
                        else matrice[i][j] = Double.parseDouble(token);
                        j++;
                    }
                    i++;
                if(i == maxLine) break;
            }
            return new ProcessResult(matrice, classes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
