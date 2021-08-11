package university.data;

import university.business.AdminAccount;

import java.sql.*;
import java.util.ArrayList;

public class AdminAccountDB {
    private static final String URL = "jdbc:mysql://localhost:3306/university?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection connection;
    private PreparedStatement selectAllAdminAccounts;
    private PreparedStatement selectAdminAccountByAdminID;
    private PreparedStatement insertAdminAccount;
    private PreparedStatement updateAdminAccount;
    private PreparedStatement findAdminAccount;
    private PreparedStatement changePassword;
    private PreparedStatement deleteAdminAccount;

    public AdminAccountDB(){
        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            selectAllAdminAccounts = connection.prepareStatement("SELECT * FROM adminaccounts");
            selectAdminAccountByAdminID = connection.prepareStatement("SELECT * FROM adminaccounts WHERE adminID = ?");
            insertAdminAccount = connection.prepareStatement("INSERT INTO adminaccounts (adminID,password) " +
                    "VALUES (?,?)");
            updateAdminAccount = connection.prepareStatement("UPDATE adminaccounts SET adminID = ?, password = ? WHERE adminID = ?");
            findAdminAccount = connection.prepareStatement("SELECT adminID FROM adminaccounts WHERE adminID = ?");
            changePassword = connection.prepareStatement("UPDATE adminaccounts SET password = ? WHERE adminID = ?");
            deleteAdminAccount = connection.prepareStatement("DELETE FROM adminaccounts WHERE adminID = ?");

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    // select all admin accounts
    public ArrayList<AdminAccount> getAllAdminAccounts(){
        ArrayList<AdminAccount> adminAccounts = null;
        ResultSet resultSet = null;

        try {
            resultSet = selectAllAdminAccounts.executeQuery();
            adminAccounts = new ArrayList<>();

            while (resultSet.next()){
                adminAccounts.add(new AdminAccount(
                   resultSet.getString("adminID"),
                        resultSet.getString("password")
                ));
            }
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }

        return adminAccounts;
    }

    // select admin account by adminID
    public AdminAccount getAdminAccount(String adminID){
        AdminAccount adminAccount = null;
        ResultSet resultSet = null;

        try {
            selectAdminAccountByAdminID.setString(1,adminID);
            resultSet = selectAdminAccountByAdminID.executeQuery();

            if (resultSet.next()){
                adminAccount = new AdminAccount(
                        resultSet.getString("adminID"),
                        resultSet.getString("password")
                );
            }
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }

        return adminAccount;
    }
    // insert new admin account
    public boolean isAdminAccountAdded(AdminAccount adminAccount){
        boolean adminAccountInserted = false;
        ResultSet resultSet = null;
        try {
            insertAdminAccount.setString(1, adminAccount.getAdminID());
            insertAdminAccount.setString(2, adminAccount.getPassword());

            resultSet = insertAdminAccount.executeQuery();

            adminAccountInserted = resultSet.next();
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            close();
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

        return adminAccountInserted;
    }
    // update admin account
    public boolean isAdminAccountUpdated(AdminAccount adminAccount, String adminID){
        boolean adminAccountUpdated = false;
        ResultSet resultSet = null;

        try {
            updateAdminAccount.setString(1, adminAccount.getAdminID());
            updateAdminAccount.setString(2, adminAccount.getPassword());
            updateAdminAccount.setString(3,adminID);

            resultSet = updateAdminAccount.executeQuery();

            adminAccountUpdated = resultSet.next();
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            close();
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
        return adminAccountUpdated;
    }
    // find admin account or check if one exists
    public boolean isAdminAccountFound(String adminID){
        boolean adminAccountFound = false;
        ResultSet resultSet = null;

        try {
            findAdminAccount.setString(1, adminID);

            resultSet = findAdminAccount.executeQuery();

            adminAccountFound = resultSet.next();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            close();
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
        return adminAccountFound;
    }
    // change password
    public boolean isPasswordChanged( String password, String adminID){
        boolean passwordChanged = false;
        ResultSet resultSet = null;

        try {
            changePassword.setString(1,password);
            changePassword.setString(2, adminID);

            resultSet = changePassword.executeQuery();

            passwordChanged = resultSet.next();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            close();
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
        return passwordChanged;
    }
    // delete admin account
    public boolean isAdminAccountDeleted(String adminID){
        boolean adminAccountDeleted = false;
        ResultSet resultSet = null;

        try {
            deleteAdminAccount.setString(1,adminID);

            resultSet = deleteAdminAccount.executeQuery();

            adminAccountDeleted = resultSet.next();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            close();
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

        return adminAccountDeleted;
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
