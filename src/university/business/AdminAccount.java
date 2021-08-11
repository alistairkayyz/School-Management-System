package university.business;

import java.io.Serializable;

public class AdminAccount implements Serializable {
    private String adminID;
    private String password;

    public AdminAccount(){}
    public AdminAccount(String adminID, String password){
        setAdminID(adminID);
        setPassword(password);
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
