package university.util;

import university.data.AdminAccountDB;
import university.data.StudentDB;

import java.util.Objects;

public class Validate {
    public static boolean isNumeric(String text){
        return !Objects.equals(text, "") && text.matches("\\d+");
    }
    public static boolean isWord(String word){
        return !Objects.equals(word, "") && word.matches("[A-Za-z]+");
    }
    public static boolean isEmail(String email){
        return email.contains("@") && (email.endsWith(".com")
                || email.endsWith(".ac.za") || email.endsWith(".co.za"));
    }
    public static boolean isAdminUserFound(String username){
        AdminAccountDB adminAccountDB = new AdminAccountDB();
        return adminAccountDB.isAdminAccountFound(username);
    }
    public static boolean isStudentFound(String username){
        StudentDB studentAccountDB = new StudentDB();
        return studentAccountDB.isStudentFound(username);
    }
}
