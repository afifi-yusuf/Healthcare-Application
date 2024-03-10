package uk.ac.ucl.main;


import java.io.*;
import java.util.*;

public class DataLoader {
    public DataFrame loadDataFrame(String fileName) {
        DataFrame dataFrame = new DataFrame();
        List<String> columnNames = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            if ((line = br.readLine()) != null) {
                columnNames = List.of(line.split(","));
                for (String columnName : columnNames) {
                    dataFrame.addColumn(new Column(columnName));
                }
            }

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < values.length; i++) {
                    dataFrame.addValue(columnNames.get(i), values[i]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return dataFrame;
    }
}