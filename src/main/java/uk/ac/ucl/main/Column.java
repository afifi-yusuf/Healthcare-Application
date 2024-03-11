package uk.ac.ucl.main;

import java.util.ArrayList;

public class Column {
    private String name;
    private ArrayList<String> rows;

    public Column(String name) {
        this.name = name;
        this.rows = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return rows.size();
    }

    public String getRowValue(int index) {
        if (index >= 0 && index < rows.size()) {
            return rows.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }



    public ArrayList<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            values.add(getRowValue(i));
        }
        return values;
    }

    public void setRowValue(int index, String value) {
        if (index >= 0 && index < rows.size()) {
            rows.set(index, value);
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    public void addRowValue(String value) {
        rows.add(value);
    }
}