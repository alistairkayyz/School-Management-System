package university;

import university.business.*;
import university.data.*;
import university.util.Validate;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        login();
    }
    // login page
    //
    public static void login(){
        System.out.println("---------- LOGIN PAGE ----------\n");

        System.out.print("Please enter username: ");
        String username = scanner.next();
        System.out.print("Please enter password: ");
        String password = scanner.next();

        if (Validate.isAdminUserFound(username)){ // check if admin user exists
            // admin account objects
            AdminAccountDB adminAccountDB = new AdminAccountDB();

            // retrieve the admin account from the database
            AdminAccount adminAccount = adminAccountDB.getAdminAccount(username);

            if (password.equals(adminAccount.getPassword())) // check password
                adminPortal();
            else {
                System.err.println("Invalid PASSWORD. PLEASE TRY AGAIN");
                login(); // return to the login page if the password is incorrect
            }
        }
        else {
            System.err.println("Invalid USERNAME. PLEASE TRY AGAIN");
            login(); // return to the login page if the username is incorrect
        }
    }

    //logout
    public static void logout(){
        // return to the login page
        login();
    }
    /* ----------- ADMIN SECTION ---------- */
    public static void adminPortal(){
        System.out.println("\n---------- ADMIN PAGE ----------\n");

        System.out.println("""
                What would you like to do:\s
                1. Add student\s
                2. Find Student\s
                3. Display All Students\s
                4. Delete Student""");
        System.out.println("-------------------------");
        System.out.println("Enter [5] to Logout!");
        System.out.println("-------------------------");

        int choice = 0;

        // breaks out if it is a valid choice
        while (true){
            // allow user input and breaks out if it is an integer
            while (true){
                System.out.print("Please enter your integer choice: ");

                String text = scanner.next();

                if (Validate.isNumeric(text)){
                    choice = Integer.parseInt(text);
                    break;
                }
                else
                    System.err.println("Error: An INTEGER type required! Please try again");
            }

            // break out of he while look if it is the right choice
            if (choice > 0 && choice <= 5)
                break;
            else
                System.out.println("Please try [1], [2], [3], [4] or [5]");
        }

        // choose the method to go to next
        switch (choice){
            case 1 -> addStudent();
            case 2 -> findStudent();
            case 3 -> displayAllStudents();
            case 4 -> deleteStudent();
            case 5 -> logout();
        }
    }

    // insert student
    public static void addStudent(){
        Student student = new Student();
        String studentNumber;
        String lastname;
        String firstname;
        String email;

        System.out.println("\nYou are about to add a new Student: ");

        while (true){
            System.out.print("Enter four digit student number(####): ");
            studentNumber = scanner.next();

            if (Validate.isNumeric(studentNumber) && studentNumber.length() == 4){
                student.setStudentNumber(studentNumber);
                break;
            }
            else
                System.err.println("Invalid Student number. Try again!");
        }

        while (true){
            System.out.print("Enter lastname: ");
            lastname = scanner.next();

            if (Validate.isWord(lastname) && lastname.length() < 21){
                student.setLastname(lastname);
                break;
            }
            else
                System.err.println("Invalid lastname. Try again");
        }

        while (true){
            System.out.print("Enter firstname: ");
            firstname = scanner.next();

            if (Validate.isWord(firstname) && firstname.length() < 21){
                student.setFirstname(firstname);
                break;
            }
            else
                System.err.println("Invalid firstname. Try again");
        }

        while (true){
            System.out.println("Enter email address: ");
            email = scanner.next();

            if (Validate.isEmail(email) && email.length() < 51){
                student.setEmail(email);
                break;
            }
            else
                System.err.println("Invalid email Try again");
        }

        int choice;
        while (true){
            System.out.println("Choose one: \n\t1. BSc IT 2. BSc CS");

            String value = scanner.next();
            if (Validate.isNumeric(value)){
                choice = Integer.parseInt(value);
                // get faculty from the database and set facultyID and fee amount

                switch (choice){
                    case 1 -> {
                            student.setFacultyID("BSCIT");
                            student.setFeeAmount(25000);
                    }
                    case 2 -> {
                        student.setFacultyID("BSCCS");
                        student.setFeeAmount(29950);
                    }
                    default -> {
                        student.setFacultyID("NONE");
                        student.setFeeAmount(0);
                    }
                }
                break;
            }
            else
                System.err.println("Invalid value enter 1 or 2...");
        }

        StudentDB studentDB = new StudentDB();
        if (studentDB.isStudentInserted(student)){
            System.out.println("--------------------------------------");
            System.out.println("Successfully added a new Student!");
            System.out.println("--------------------------------------");
        }
        adminPortal();

    }
    // find student
    public static void findStudent(){
        String studentNumber;
        StudentDB studentDB = new StudentDB();
        System.out.println("\n------------ FIND STUDENT ------------");
        while (true){
            System.out.print("Enter the student number: ");

            studentNumber = scanner.next();

            if (Validate.isNumeric(studentNumber) && studentNumber.length() == 4){
                if (studentDB.isStudentFound(studentNumber)){
                    Student student = studentDB.getStudent(studentNumber);

                    // display the student
                    System.out.println("\n- STUDENT DETAILS");
                    System.out.print(student.toString());
                    System.out.println();
                    break;
                }
                else
                    System.err.println("Student no found! Please Try again...");
            }
            else
                System.err.println("Invalid student number. Please Try again...");
        }
        adminPortal();
    }
    // update student
    public static void displayAllStudents(){
        StudentDB studentDB = new StudentDB();

        ArrayList<Student> students = studentDB.getAllStudents();

        students.forEach(System.out::println);

        adminPortal();
    }
    // delete student
    public static void deleteStudent(){
        String studentNumber;
        StudentDB studentDB = new StudentDB();
        System.out.println("\n------------ FIND STUDENT ------------");
        while (true){
            System.out.print("Enter the student number: ");

            studentNumber = scanner.next();

            if (Validate.isNumeric(studentNumber) && studentNumber.length() == 4){

                if (studentDB.isStudentFound(studentNumber)){

                    if (studentDB.isStudentDeleted(studentNumber)){
                        System.out.println("--------------------------------------");
                        System.out.println("Successfully deleted a Student!");
                        System.out.println("--------------------------------------");
                        break;
                    }
                    else
                        System.err.println("Failed to delete the specified student!");
                }
                else
                    System.err.println("Student no found! Please Try again...");
            }
            else
                System.err.println("Invalid student number. Please Try again...");
        }
        adminPortal();
    }

}
