package uk.ac.ucl.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONWriter {
    //Method to create formatted JSON file from DataFrame
    public static void writeDataFrameToJSON(DataFrame dataFrame, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            StringBuilder json = new StringBuilder();
            json.append("{\n");

            List<String> columnNames = dataFrame.getColumnNames();
            json.append("  \"data\": [\n");

            for (int i = 0; i < dataFrame.getRowCount(); i++) {
                json.append("    {\n");
                for (int j = 0; j < columnNames.size(); j++) {
                    String columnName = columnNames.get(j);
                    String value = dataFrame.getValue(columnName, i);

                    json.append("      \"").append(columnName).append("\": \"").append(value).append("\"");
                    if (j < columnNames.size() - 1) {
                        json.append(",\n");
                    } else {
                        json.append("\n");
                    }
                }
                if (i < dataFrame.getRowCount() - 1) {
                    json.append("    },\n");
                } else {
                    json.append("    }\n");
                }
            }

            json.append("  ]\n");
            json.append("}");

            fileWriter.write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
