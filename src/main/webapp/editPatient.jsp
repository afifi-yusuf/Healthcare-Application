<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Patient Data App - Edit Patient</title>
</head>
<body>
<div class="main">
    <h1>Edit Patient Details</h1>
    <form method="POST" action="/runeditpatient.html">
        <h2>Enter the ID of the patient you want to edit</h2>
        <input type="text" name="id" placeholder="Enter existing ID"/><br>
        <!-- Input fields for details to be edited -->
        <h2>Enter the new details</h2>
        <input type="text" name="first" placeholder="First name"/><br>
        <input type="text" name="last" placeholder="Last name"/><br>
        <input type="text" name="dob" placeholder="Date of birth"/><br>
        <input type="text" name="dod" placeholder="Date of death"/><br>
        <input type="text" name="ssn" placeholder="Social security number"/><br>
        <input type="text" name="drivers" placeholder="Drivers license number"/><br>
        <input type="text" name="passport" placeholder="Passport number"/><br>
        <input type="text" name="prefix" placeholder="Prefix"/><br>
        <input type="text" name="suffix" placeholder="Suffix"/><br>
        <input type="text" name="maiden" placeholder="Maiden name"/><br>
        <input type="text" name="marital" placeholder="Marital status"/><br>
        <input type="text" name="race" placeholder="Race"/><br>
        <input type="text" name="ethnicity" placeholder="Ethnicity"/><br>
        <input type="text" name="gender" placeholder="Gender"/><br>
        <input type="text" name="birthplace" placeholder="Birthplace"/><br>
        <input type="text" name="address" placeholder="Address"/><br>
        <input type="text" name="city" placeholder="City"/><br>
        <input type="text" name="state" placeholder="State"/><br>
        <input type="text" name="zip" placeholder="Zip"/><br>
        <input type="submit" value="Edit"/>
    </form>
</div>
</body>
</html>
