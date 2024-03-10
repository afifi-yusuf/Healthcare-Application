package uk.ac.ucl.main;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import java.util.*;

public class DataFrame {
    private Map<String, Column> columns;

    public DataFrame() {
        this.columns = new HashMap<>();
    }

    public void addColumn(Column column) {
        columns.put(column.getName(), column);
    }

    public List<String> getColumnNames() {
        return new ArrayList<>(columns.keySet());
    }

    public Column getColumn(String columnName) {
        return columns.get(columnName);
    }

    public int getRowCount() {
        if (columns.isEmpty()) {
            return 0;
        }
        return columns.get(columns.keySet().iterator().next()).getSize();
    }

    public String getValue(String columnName, int row) {
        Column column = columns.get(columnName);
        if (column == null) {
            throw new IllegalArgumentException("Column not found: " + columnName);
        }
        return column.getRowValue(row);
    }

    public void putValue(String columnName, int row, String value) {
        Column column = columns.get(columnName);
        if (column == null) {
            throw new IllegalArgumentException("Column not found: " + columnName);
        }
        if (row < 0 || row >= column.getSize()) {
            throw new IndexOutOfBoundsException("Invalid row index: " + row);
        }
        column.setRowValue(row, value);
    }

    public void addValue(String columnName, String value) {
        Column column = columns.get(columnName);
        if (column == null) {
            throw new IllegalArgumentException("Column not found: " + columnName);
        }
        column.addRowValue(value);
    }
    public String toCSV() {
        StringBuilder csv = new StringBuilder();

        // Append column names as header
        for (int i = 0; i < columns.size(); i++) {
            csv.append(columns.get(i).getName());
            if (i < columns.size() - 1) {
                csv.append(",");
            }
        }
        csv.append("\n");

        // Append rows
        int numRows = columns.get(0).getSize();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < columns.size(); col++) {
                csv.append(columns.get(col).getRowValue(row));
                if (col < columns.size() - 1) {
                    csv.append(",");
                }
            }
            csv.append("\n");
        }

        return csv.toString();
    }

}