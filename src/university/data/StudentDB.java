package university.data;

import university.business.Student;

import java.sql.*;
import java.util.ArrayList;

public class StudentDB {
    private static final String URL = "jdbc:mysql://localhost:3306/university?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection connection;
    private PreparedStatement selectAllStudents;
    private PreparedStatement selectStudentByStudentNumber;
    private PreparedStatement insertStudent;
    private PreparedStatement updateStudent;
    private PreparedStatement findStudent;
    private PreparedStatement deleteStudent;
    private PreparedStatement payFees;

    public StudentDB(){
        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            selectAllStudents = connection.prepareStatement("SELECT * FROM students");
            selectStudentByStudentNumber = connection.prepareStatement("SELECT * FROM students WHERE studentNumber = ?");
            insertStudent = connection.prepareStatement("INSERT INTO students (studentNumber,lastname,firstname,email,facultyID,feeAmount) " +
                    "VALUES (?,?,?,?,?,?)");
            updateStudent = connection.prepareStatement("UPDATE students SET lastname = ?, firstname = ?, email = ?," +
                    "WHERE studentNumber = ?");
            findStudent = connection.prepareStatement("SELECT studentNumber FROM students WHERE studentNumber = ?");
            deleteStudent = connection.prepareStatement("DELETE FROM students WHERE studentNumber = ?");
            payFees = connection.prepareStatement("UPDATE students SET feeAmount = ? WHERE studentNumber = ?");
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    // select all students
    public ArrayList<Student> getAllStudents(){
        ArrayList<Student> students = null;
        ResultSet resultSet = null;

        try {
            resultSet = selectAllStudents.executeQuery();
            students = new ArrayList<>();

            while (resultSet.next()){
                students.add(new Student(
                        resultSet.getString("studentNumber"),
                        resultSet.getString("lastname"),
                        resultSet.getString("firstname"),
                        resultSet.getString("email"),
                        resultSet.getString("facultyID"),
                        resultSet.getDouble("feeAmount")
                ));
            }
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }

        return students;
    }
    // select student by student number
    public Student getStudent(String studentNumber){
        Student student = null;
        ResultSet resultSet = null;

        try {
            selectStudentByStudentNumber.setString(1,studentNumber);
            resultSet = selectStudentByStudentNumber.executeQuery();

            if (resultSet.next()){
                student = new Student(
                        resultSet.getString("studentNumber"),
                        resultSet.getString("lastname"),
                        resultSet.getString("firstname"),
                        resultSet.getString("email"),
                        resultSet.getString("facultyID"),
                        resultSet.getDouble("feeAmount")
                );
            }
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }

        return student;
    }
    // insert student
    public boolean isStudentInserted(Student student){
        boolean studentInserted = false;
        ResultSet resultSet = null;

        try {
            insertStudent.setString(1, student.getStudentNumber());
            insertStudent.setString(2, student.getLastname());
            insertStudent.setString(3, student.getFirstname());
            insertStudent.setString(4, student.getEmail());
            insertStudent.setString(5, student.getFacultyID());
            insertStudent.setDouble(6, student.getFeeAmount());

            studentInserted = insertStudent.executeUpdate() > 0;
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return studentInserted;
    }
    // update student
    public boolean isStudentUpdated(Student student, String studentNumber){
        boolean studentUpdated = false;
        ResultSet resultSet = null;

        try {
            updateStudent.setString(1, student.getLastname());
            updateStudent.setString(2, student.getFirstname());
            updateStudent.setString(3, student.getEmail());
            updateStudent.setString(4,studentNumber);

            resultSet = updateStudent.executeQuery();

            studentUpdated = resultSet.next();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }
        return studentUpdated;
    }
    // find student
    public boolean isStudentFound(String studentNumber){
        boolean studentFound = false;
        ResultSet resultSet = null;

        try {
            findStudent.setString(1,studentNumber);

            resultSet = findStudent.executeQuery();
            studentFound = resultSet.next();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }
        return studentFound;
    }
    // delete student
    public boolean isStudentDeleted(String studentNumber){
        boolean studentDeleted = false;

        try {
            deleteStudent.setString(1,studentNumber);

            studentDeleted = deleteStudent.executeUpdate() > 0;
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

        return studentDeleted;
    }
    // pay fees
    public boolean isFeesPaid(String studentNumber, double amount){
        boolean feesPaid = false;
        ResultSet resultSet = null;

        try {
            payFees.setDouble(1, amount);
            payFees.setString(2,studentNumber);

            resultSet = payFees.executeQuery();

            feesPaid = resultSet.next();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }

        return feesPaid;
    }
    public void close(){
        try {
            connection.close();
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
















