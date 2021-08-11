package university.business;

import java.io.Serializable;
import java.text.NumberFormat;

public class Student implements Serializable {
    private String studentNumber;
    private String lastname;
    private String firstname;
    private String email;
    private String facultyID;
    private double feeAmount;

    public Student(){}
    public Student(String studentNumber, String lastname, String firstname, String email,
                   String facultyID, double feeAmount){
        setStudentNumber(studentNumber);
        setLastname(lastname);
        setFirstname(firstname);
        setEmail(email);
        setFacultyID(facultyID);
        setFeeAmount(feeAmount);
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(String facultyID) {
        this.facultyID = facultyID;
    }

    public double getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(double feeAmount) {
        this.feeAmount = feeAmount;
    }
    public String getFeeAmountFormatted(){
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(getFeeAmount());
    }

    @Override
    public String toString() {
        return String.format("%n------------ Student Details ------------%n" +
                        "StudentNumber: %s%nLastname: %s%nFirstname: %s%nEmail: %s%n" +
                        "FacultyID: %s%nFeeAmount: %s%n" +
                        "------------------------ Ends ------------------------",
                getStudentNumber(),getLastname(),getFirstname(),getEmail(),
                getFacultyID(),getFeeAmountFormatted());
    }
}
