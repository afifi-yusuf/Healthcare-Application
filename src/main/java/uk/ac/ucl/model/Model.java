package uk.ac.ucl.model;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import uk.ac.ucl.main.Column;
import uk.ac.ucl.main.DataFrame;
import uk.ac.ucl.main.DataLoader;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

public class Model {
  private DataFrame dataFrame;

  public Model() {
    DataLoader dataLoader = new DataLoader();
    this.dataFrame = dataLoader.loadDataFrame("data/patients100.csv");
  }
  public List<String> getPatientNames()
  {
    Column firstname = this.dataFrame.getColumn("FIRST");
    Column lastname = this.dataFrame.getColumn("LAST");
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


  //search by ketword
  public List<String> searchFor(String keyword) {
    List<String> searchResults = new ArrayList<>();

    // Iterate through the data frame and search for the keyword
    for (String columnName : this.dataFrame.getColumnNames()) {
      for (int i = 0; i < this.dataFrame.getRowCount(); i++) {
        String value = this.dataFrame.getValue(columnName, i);
        if (value.contains(keyword)) {
          searchResults.add(value);
        }
      }
    }

    return searchResults;
  }

  //will later be used for youngest and oldest
  private String findExtremePerson(boolean isOldest) {
    if (this.dataFrame == null) {
      return "No data available.";
    }

    Column birthdateColumn = this.dataFrame.getColumn("BIRTHDATE");
    Column deathdateColumn = this.dataFrame.getColumn("DEATHDATE");
    Column firstNameColumn = this.dataFrame.getColumn("FIRST");
    Column lastNameColumn = this.dataFrame.getColumn("LAST");

    if (birthdateColumn == null || firstNameColumn == null || lastNameColumn == null || deathdateColumn == null) {
      return "One or more required columns not found.";
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date extremeBirthDate = null;
    String extremePersonName = "";

    for (int i = 0; i < birthdateColumn.getSize(); i++) {
      try {
        if (!deathdateColumn.getRowValue(i).isEmpty()) {
          continue; // Skip if the person is deceased
        }

        Date birthDate = dateFormat.parse(birthdateColumn.getRowValue(i));
        String firstName = removeDigits(firstNameColumn.getRowValue(i));
        String lastName = removeDigits(lastNameColumn.getRowValue(i));

        if ((isOldest && (extremeBirthDate == null || birthDate.before(extremeBirthDate))) ||
                (!isOldest && (extremeBirthDate == null || birthDate.after(extremeBirthDate)))) {
          extremeBirthDate = birthDate;
          extremePersonName = firstName + " " + lastName;
        }
      } catch (ParseException e) {
        // Handle parsing exception
        e.printStackTrace();
      }
    }

    // Calculate age based on birthdate
    if (extremeBirthDate != null) {
      int age = calculateAge(extremeBirthDate);
      String description = isOldest ? "oldest" : "youngest";
      return extremePersonName + " is the " + description + " person, born on " + dateFormat.format(extremeBirthDate) + ", and is " + age + " years old.";
    } else {
      return "No living person found in the dataset.";
    }
  }
  private int calculateAge(Date birthDate) {
    Calendar birthCalendar = Calendar.getInstance();
    birthCalendar.setTime(birthDate);
    Calendar currentCalendar = Calendar.getInstance();
    int age = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
    if (currentCalendar.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
      age--;
    }
    return age;
  }

  public String findOldestPerson() {
    return findExtremePerson(true);
  }

  public String findYoungestPerson() {
    return findExtremePerson(false);
  }


  public String getPeopleByAttribute(String attribute, String type) {
    if (this.dataFrame == null) {
      return "No data available.";
    }

    Column searchColumn = this.dataFrame.getColumn(type);
    Column firstNameColumn = this.dataFrame.getColumn("FIRST");
    Column lastNameColumn = this.dataFrame.getColumn("LAST");

    if (searchColumn == null || firstNameColumn == null || lastNameColumn == null) {
      return "One or more required columns not found.";
    }

    List<String> peopleMatchingAttribute = new ArrayList<>();
    int count = 0;

    for (int i = 0; i < searchColumn.getSize(); i++) {
      String searchValue = searchColumn.getRowValue(i);
      if (searchValue != null && searchValue.equalsIgnoreCase(attribute)) {
        String firstName = removeDigits(firstNameColumn.getRowValue(i));
        String lastName = removeDigits(lastNameColumn.getRowValue(i));
        peopleMatchingAttribute.add(firstName + " " + lastName);
        count++;
      }
    }

    StringBuilder result = new StringBuilder();
    result.append("Number of people with ").append(type).append(" '").append(attribute).append("': ").append(count).append("<br>");

    result.append("List of people: <br>");
    for (String person : peopleMatchingAttribute) {
      result.append(person).append("<br>");
    }

    return result.toString();
  }


  public String getPeopleInCity(String city) {
    return getPeopleByAttribute(city, "CITY");
  }
  public String getPeopleInState(String state) {
    return getPeopleByAttribute(state, "STATE");
  }

  public String getPeopleInEthnicity(String ethnicity){
    return getPeopleByAttribute(ethnicity, "ETHNICITY");
  }

  public int getIdIndex(String id) {
    // Find the index of the patient with the given ID
    Column idColumn = this.dataFrame.getColumn("ID");
    int rowIndex = -1;
    for (int i = 0; i < idColumn.getSize(); i++) {
      if (idColumn.getRowValue(i).equals(id)) {
        rowIndex = i;
        break;
      }
    }
    return rowIndex;
  }

  public String displayPatientDetails(String patientId) {
    if (this.dataFrame == null) {
      return "No data available.";
    }
    int rowIndex = getIdIndex(patientId);

    if (rowIndex == -1) {
      return "Patient not found.";
    }

    // Retrieve patient details from the DataFrame
    StringBuilder patientDetails = new StringBuilder();

    List<String> columnNames = this.dataFrame.getColumnNames();
    for (String columnName : columnNames) {
      if (rowIndex >= this.dataFrame.getColumn(columnName).getSize() || this.dataFrame.getColumn(columnName).getRowValue(rowIndex).isEmpty()) {
        continue;
      }
      String value = this.dataFrame.getValue(columnName, rowIndex);
      if (columnName.equals("FIRST")  || columnName.equals("LAST") || columnName.equals("MAIDEN")) // Remove digits from name columns
        value = removeDigits(value);
      patientDetails.append(columnName).append(": ").append(value).append("<br>");
    }

    return patientDetails.toString();
  }



  public String getPatientId(String fullName) {
    if (this.dataFrame == null) {
      return "No data available.";
    }

    Column idColumn = this.dataFrame.getColumn("ID");
    Column firstNameColumn = this.dataFrame.getColumn("FIRST");
    Column lastNameColumn = this.dataFrame.getColumn("LAST");

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

  public String deletePatient(String patientId) {
    if (this.dataFrame == null) {
      return "No data available.";
    }

    int rowIndex = getIdIndex(patientId);

    if (rowIndex == -1) {
      return "Patient not found.";
    }

    // Remove the patient details from the DataFrame
    for (String columnName : this.dataFrame.getColumnNames()) {
      this.dataFrame.removeValue(columnName, rowIndex);
    }

    // Write the updated DataFrame to the CSV file
    writeFile("data/patients.csv", this.dataFrame.toCSV());

    return "Successfully deleted patient.";
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

    if (patientDetails.length != columnNames.length) {
      // Handle the case where the number of details does not match
      return patientDetails.length  + " " +columnNames.length +
              " Number of patient details does not match the number of columns.\n";
    }

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
    //System.out.println("Added patient details to the DataFrame.");

    // Write the updated DataFrame to the CSV file
    writeFile("data/patients.csv", this.dataFrame.toCSV());

    return "Successfully added patient.";
  }

  public String editPatient(String patientId, String... updatedPatientDetails) {
    // Check if the DataFrame is initialized
    if (this.dataFrame == null) {
      // Handle the case where the DataFrame is not initialized
      return "DataFrame is not initialized.";
    }

    // Find the index of the patient with the given ID
    int rowIndex = getIdIndex(patientId);

    if (rowIndex == -1) {
      return "Patient not found.";
    }

    // Define column names in the same order as the input fields in the HTML form
    String[] columnNames = {"FIRST", "LAST", "BIRTHDATE", "DEATHDATE", "SSN", "DRIVERS", "PASSPORT", "PREFIX", "SUFFIX", "MAIDEN", "MARITAL", "RACE", "ETHNICITY", "GENDER", "BIRTHPLACE", "ADDRESS", "CITY", "STATE", "ZIP"};

    // Check if the number of updated patient details matches the number of columns
    if (updatedPatientDetails.length != columnNames.length) {
      // Handle the case where the number of details does not match
      return updatedPatientDetails.length + " " + columnNames.length +
              " Number of updated patient details does not match the number of columns.\n";
    }

    // Iterate over each updated patient detail and update it in the respective column
    for (int i = 0; i < updatedPatientDetails.length; i++) {
      String columnName = columnNames[i];
      String updatedDetail = updatedPatientDetails[i];

      // Check if the column exists in the DataFrame
      if (this.dataFrame.getColumnNames().contains(columnName)) {
        // Check if the updated detail is not empty
        if (!updatedDetail.isEmpty()) {
          this.dataFrame.putValue(columnName, rowIndex, updatedDetail);
        }
      } else {
        return "Column " + columnName + " does not exist in the DataFrame.";
      }
    }


    //System.out.println("Updated patient details in the DataFrame.");

    // Write the updated DataFrame to the CSV file
    writeFile("data/patients.csv", this.dataFrame.toCSV());

    return "Successfully edited patient details.";
  }


  //graphs created using java.awt.Graphics2D
  public void createAgeDistributionGraph(String filePath) {
    List<String> birthDateValues = this.dataFrame.getColumn("BIRTHDATE").getValues();
    List<String> deathDateValues = this.dataFrame.getColumn("DEATHDATE").getValues();

    int[] ageCounts = new int[111]; // Array to store count of each age (0-110)

    // Calculate age for each entry
    for (int i = 0; i < birthDateValues.size(); i++) {
      String birthDateString = birthDateValues.get(i);
      String deathDateString = deathDateValues.get(i);
      if(birthDateString == null || birthDateString.isEmpty()) continue;

      if (deathDateString == null || deathDateString.isEmpty()) {
        LocalDate birthDate = LocalDate.parse(birthDateString);
        LocalDate currentDate = LocalDate.now();

        int age = currentDate.getYear() - birthDate.getYear();
        if (age >= 0 && age <= 110) {
          ageCounts[age]++;
        }
      }
    }

    // Create a buffered image to draw the graph
    int graphWidth = 1500;
    int graphHeight = 400;
    BufferedImage image = new BufferedImage(graphWidth, graphHeight + 200, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = image.createGraphics();

    // Draw background
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, graphWidth, graphHeight);

    // Draw axis titles
    g2d.setColor(Color.BLACK);
    Font axisTitleFont = new Font("Arial", Font.BOLD, 14);
    g2d.setFont(axisTitleFont);
    g2d.drawString("Age", 730, 440); // Adjusted position for "Age"
    g2d.drawString("Frequency", 10, 270); // Adjusted position for "Frequency"

    // Draw bars for each age group
    int barWidth = 20;
    int barSpacing = 2;
    int x = 50; // Starting x-coordinate
    int maxCount = getMaxCount(ageCounts); // Find the maximum count for scaling
    for (int age = 0; age <= 110; age++) {
      if (ageCounts[age] == 0) {
        continue;
      }
      int barHeight = (int) (ageCounts[age] / (double) maxCount * (graphHeight - 100));
      g2d.setColor(Color.BLUE);
      g2d.fillRect(x, graphHeight - barHeight, barWidth, barHeight);
      // Label the bars with age
      g2d.drawString(Integer.toString(age), x, graphHeight + 15); // Adjusted position for labels
      x += barWidth + barSpacing;
    }

    // Save the graph as an image file
    try {
      ImageIO.write(image, "PNG", new File(filePath));
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Clean up resources
    g2d.dispose();
  }


  //helper function for creating charts
  private int getMaxCount(int[] counts) {
    int max = Integer.MIN_VALUE;
    for (int count : counts) {
      if (count > max) {
        max = count;
      }
    }
    return max;
  }

  public void createGenderDistributionPieChart(String filePath) {
    List<String> genderValues = this.dataFrame.getColumn("GENDER").getValues();

    // Filter out null, empty, and non-"M"/"F" values
    genderValues = genderValues.stream()
            .filter(gender -> gender != null && !gender.isEmpty() && (gender.equals("M") || gender.equals("F")))
            .toList();

    // Count occurrences of each gender
    Map<String, Integer> genderCounts = new HashMap<>();
    for (String gender : genderValues) {
      genderCounts.put(gender, genderCounts.getOrDefault(gender, 0) + 1);
    }

    // Create a buffered image to draw the pie chart
    int graphWidth = 800;
    int graphHeight = 600;
    BufferedImage image = new BufferedImage(1000, 800, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = image.createGraphics();

    // Draw background
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, graphWidth, graphHeight);

    // Calculate the total count of valid gender values ("M" and "F")
    double totalCount = genderCounts.values().stream().mapToInt(Integer::intValue).sum();

    // Draw the pie chart
    double startAngle = 0;
    for (Map.Entry<String, Integer> entry : genderCounts.entrySet()) {
      double angle = (entry.getValue() / totalCount) * 360;
      Color lightBlue = new Color(0xAD, 0xD8, 0xE6);
      Color color = entry.getKey().equals("M") ? lightBlue : Color.PINK; // Assign color based on gender
      g2d.setColor(color);
      g2d.fillArc(100, 100, 400, 400, (int) startAngle, (int) angle);
      startAngle += angle;
    }

    // Save the pie chart as an image file
    try {
      ImageIO.write(image, "PNG", new File(filePath));
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Clean up resources
    g2d.dispose();
  }

  public void createEthnicityDistributionGraph(String filePath) {
    List<String> ethnicityValues = this.dataFrame.getColumn("ETHNICITY").getValues();

    // Initialize counts for each ethnicity
    Map<String, Integer> ethnicityCounts = new HashMap<>();
    for (String ethnicity : ethnicityValues) {
      if (!ethnicity.isEmpty()) {
        ethnicityCounts.put(ethnicity, ethnicityCounts.getOrDefault(ethnicity, 0) + 1);
      }
    }

    // Create a buffered image to draw the graph
    int graphWidth = 1500;
    int graphHeight = 400;
    BufferedImage image = new BufferedImage(graphWidth, graphHeight + 200, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = image.createGraphics();

    // Draw background
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, graphWidth, graphHeight);

    // Draw axis titles
    g2d.setColor(Color.BLACK);
    Font axisTitleFont = new Font("Arial", Font.BOLD, 14);
    g2d.setFont(axisTitleFont);
    g2d.drawString("Ethnicity", 740, 450); // Adjusted position for "Ethnicity"
    g2d.drawString("Frequency", 10, 250); // Adjusted position for "Frequency"

    // Draw bars for each ethnicity
    int barWidth = 65;
    int barSpacing = 15;
    int x = 50; // Starting x-coordinate
    int maxCount = Collections.max(ethnicityCounts.values()); // Find the maximum count for scaling
    for (Map.Entry<String, Integer> entry : ethnicityCounts.entrySet()) {
      String ethnicity = entry.getKey();
      int count = entry.getValue();
      int barHeight = (int) (count / (double) maxCount * (graphHeight - 100));
      g2d.setColor(Color.BLUE); // You can adjust the color based on ethnicity if needed
      g2d.fillRect(x, graphHeight - barHeight, barWidth, barHeight);
      // Label the bars with ethnicity
      String[] parts = ethnicity.split("_");
      String firstPart = parts[0]; // First part of the ethnicity
      String secondPart = parts.length > 1 ? parts[1] : ""; // Second part (if available)
      g2d.drawString(firstPart, x, graphHeight + 15); // Draw first part
      g2d.drawString(secondPart, x, graphHeight + 30); // Draw second part (if available)
      x += barWidth + barSpacing;
    }

    // Save the graph as an image file
    try {
      ImageIO.write(image, "PNG", new File(filePath));
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Clean up resources
    g2d.dispose();
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
