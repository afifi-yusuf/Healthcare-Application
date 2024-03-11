package uk.ac.ucl.model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import uk.ac.ucl.main.Column;
import uk.ac.ucl.main.DataFrame;
import uk.ac.ucl.main.DataLoader;

public class Model {
  private DataFrame dataFrame;

  public Model() {
    DataLoader dataLoader = new DataLoader();
    this.dataFrame = dataLoader.loadDataFrame("data/patients100.csv");
  }
  public List<String> getPatientNames()
  {
    Column firstname = dataFrame.getColumn("FIRST");
    Column lastname = dataFrame.getColumn("LAST");
    List<String> patientNames = new ArrayList<>();
    for (int i = 0; i < firstname.getSize(); i++)
    {
      patientNames.add(removeDigits(firstname.getRowValue(i)) + " " + removeDigits(lastname.getRowValue(i)));
    }
    return patientNames;
    //return readFile("data/patients100.csv");
  }
  public List<String> readFile(String fileName)
  {
    List<String> data = new ArrayList<>();

    try (Reader reader = new FileReader(fileName);
         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT))
    {
      for (CSVRecord csvRecord : csvParser)
      {
        // The first row of the file contains the column headers, so is not actual data.
        data.add(csvRecord.get(0));
      }
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    return data;
  }

  public List<String> searchFor(String keyword) {
    List<String> searchResults = new ArrayList<>();

    // Iterate through the data frame and search for the keyword
    for (String columnName : dataFrame.getColumnNames()) {
      for (int i = 0; i < dataFrame.getRowCount(); i++) {
        String value = dataFrame.getValue(columnName, i);
        if (value.contains(keyword)) {
          searchResults.add(value);
        }
      }
    }

    return searchResults;
  }
  public String findOldestPerson() {
    if (dataFrame == null) {
      return "No data available.";
    }

    Column birthdateColumn = dataFrame.getColumn("BIRTHDATE");
    Column deathdateColumn = dataFrame.getColumn("DEATHDATE");
    Column firstNameColumn = dataFrame.getColumn("FIRST");
    Column lastNameColumn = dataFrame.getColumn("LAST");

    if (birthdateColumn == null || firstNameColumn == null || lastNameColumn == null || deathdateColumn == null) {
      return "One or more required columns not found.";
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date oldestBirthDate = null;
    String oldestPersonName = "";

    for (int i = 0; i < birthdateColumn.getSize(); i++) {
      try {
        if (!deathdateColumn.getRowValue(i).isEmpty()) {
          continue; // Skip if the person is deceased
        }

        Date birthDate = dateFormat.parse(birthdateColumn.getRowValue(i));
        String firstName = removeDigits(firstNameColumn.getRowValue(i));
        String lastName = removeDigits(lastNameColumn.getRowValue(i));

        if (oldestBirthDate == null || birthDate.before(oldestBirthDate)) {
          oldestBirthDate = birthDate;
          oldestPersonName = firstName + " " + lastName;
        }
      } catch (ParseException e) {
        // Handle parsing exception
        e.printStackTrace();
      }
    }

    // Calculate age based on birthdate
    if (oldestBirthDate != null) {
      Calendar birthCalendar = Calendar.getInstance();
      birthCalendar.setTime(oldestBirthDate);
      Calendar currentCalendar = Calendar.getInstance();
      int age = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
      if (currentCalendar.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
        age--;
      }

      return oldestPersonName + " is the oldest person, born on " + dateFormat.format(oldestBirthDate) + ", and is " + age + " years old.";
    } else {
      return "No living person found in the dataset.";
    }
  }

  public String getPeopleInArea(String area, String type){
    if (dataFrame == null) {
      return "No data available.";
    }

    Column areaColumn = dataFrame.getColumn(type);
    Column firstNameColumn = dataFrame.getColumn("FIRST");
    Column lastNameColumn = dataFrame.getColumn("LAST");

    if (areaColumn == null || firstNameColumn == null || lastNameColumn == null) {
      return "One or more required columns not found.";
    }

    List<String> peopleInArea = new ArrayList<>();
    int count = 0;

    for (int i = 0; i < areaColumn.getSize(); i++) {
      String areaName = areaColumn.getRowValue(i);
      if (areaName != null && areaName.equalsIgnoreCase(area)) {
        String firstName = removeDigits(firstNameColumn.getRowValue(i));
        String lastName = removeDigits(lastNameColumn.getRowValue(i));
        peopleInArea.add(firstName + " " + lastName);
        count++;
      }
    }

    StringBuilder result = new StringBuilder();
    result.append("Number of people living in ").append(area).append(": ").append(count).append("<br>");
    result.append("List of people: <br>");
    for (String person : peopleInArea) {
      result.append(person).append("<br>");
    }

    return result.toString();
  }

  public String getPeopleInCity(String city) {
    return getPeopleInArea(city, "CITY");
  }
  public String getPeopleInState(String city) {
    return getPeopleInArea(city, "STATE");
  }

  public String displayPatientDetails(String patientId) {
    if (dataFrame == null) {
      return "No data available.";
    }

    // Find the index of the patient with the given ID
    Column idColumn = dataFrame.getColumn("ID");
    int rowIndex = -1;
    for (int i = 0; i < idColumn.getSize(); i++) {
      if (idColumn.getRowValue(i).equals(patientId)) {
        rowIndex = i;
        break;
      }
    }

    if (rowIndex == -1) {
      return "Patient not found.";
    }

    // Retrieve patient details from the DataFrame
    StringBuilder patientDetails = new StringBuilder();

    List<String> columnNames = dataFrame.getColumnNames();
    for (String columnName : columnNames) {
      String value = dataFrame.getValue(columnName, rowIndex);
      if (columnName.equals("FIRST")  || columnName.equals("LAST"))
        value = removeDigits(value);
      patientDetails.append(columnName).append(": ").append(value).append("<br>");
    }

    return patientDetails.toString();
  }



  public String getPatientId(String fullName) {
    if (dataFrame == null) {
      return "No data available.";
    }

    Column idColumn = dataFrame.getColumn("ID");
    Column firstNameColumn = dataFrame.getColumn("FIRST");
    Column lastNameColumn = dataFrame.getColumn("LAST");

    if (idColumn == null || firstNameColumn == null || lastNameColumn == null) {
      return "One or more required columns not found.";
    }

    // Split the full name into first and last names
    String[] parts = fullName.split(" ");
    String firstName = parts[0];
    String lastName = parts[1];

    for (int i = 0; i < firstNameColumn.getSize(); i++) {
      String currentFirstName = removeDigits(firstNameColumn.getRowValue(i));
      String currentLastName = removeDigits(lastNameColumn.getRowValue(i));

      if (currentFirstName.equals(firstName) && currentLastName.equals(lastName)) {
        return idColumn.getRowValue(i);
      }
    }

    return "Patient not found.";
  }


  public String addPatient(String... patientDetails) {
    // Check if the DataFrame is initialized
    if (this.dataFrame == null) {
      // Handle the case where the DataFrame is not initialized
      return "DataFrame is not initialized.";
    }

    // Define column names in the same order as the input fields in the HTML form
    String[] columnNames = {"ID", "FIRST", "LAST", "BIRTHDATE", "DEATHDATE", "SSN", "DRIVERS", "PASSPORT", "PREFIX", "SUFFIX", "MAIDEN", "MARITAL", "RACE", "ETHNICITY", "GENDER", "BIRTHPLACE", "ADDRESS", "CITY", "STATE", "ZIP"};

    // Check if the number of patient details matches the number of columns
    /*
    if (patientDetails.length != columnNames.length) {
      // Handle the case where the number of details does not match
      return patientDetails.length  + " " +columnNames.length +
              " Number of patient details does not match the number of columns.\n";
    }
    */


    // Iterate over each patient detail and add it to the respective column
    for (int i = 0; i < patientDetails.length; i++) {
      String columnName = columnNames[i];
      String patientDetail = patientDetails[i];
      //System.out.println("Available column names in DataFrame: " + dataFrame.getColumnNames());
      if (this.dataFrame.getColumnNames().contains(columnName)) {
        this.dataFrame.addValue(columnName, patientDetail);
      }
        else {
            return "Column " + columnName + " does not exist in the DataFrame.";
        }
    }
    System.out.println("Added patient details to the DataFrame.");

    // Write the updated DataFrame to the CSV file
    writeFile("data/patients.csv", this.dataFrame.toCSV());

    return "Successfully added patient.";
  }


  public void writeFile(String filePath, String content) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write(content);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



  // Method to remove digits from a string
  private String removeDigits(String str) {
    return str.replaceAll("\\d", "");
  }

  public DataFrame getDataFrame() {
    return this.dataFrame;
  }

}
