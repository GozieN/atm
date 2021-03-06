package phase2.Operators;

import phase2.FundHolders.*;

import java.util.*;

/**
 *
 */
public class User extends Operator implements java.io.Serializable{
    private static ArrayList<User> userDatabase = new ArrayList<User>();
    private static int numUsers = 0;
    private String username;
    private String password;
    private ArrayList<Account> AccountsCreated = new ArrayList<Account>();


    /**
     * User constructor
     * @param username Username used to login to account
     * @param password Password used to login to account
     */
    public User(String username, String password) {
        super(username, password);
        numUsers++;
        userDatabase.add(this);
    }


    /**
     * Change the user's password
     * @param currentPassword The current password used to login to account
     * @param newPassword Create new password for login to account
     */
    public void changePassword(String currentPassword, String newPassword) {
        if (currentPassword.equals(this.password)) {
            this.password = newPassword;
            System.out.println(username + ", your password has successfully been changed");
        } else {
            System.out.println(username + ",you have entered the wrong current password. " +
                    "unable to change password");
        }
    }


    /**
     * Return a list of the accounts of the users
     * @return ArrayList of user accounts created
     */
    public ArrayList<Account> getAccountsCreated() {
        return AccountsCreated;
    }

    /**
     * Set a list of bank accounts created
     * @param accountsCreated A list of bank accounts created
     */
    public void setAccountsCreated(ArrayList<Account> accountsCreated) {
        AccountsCreated = accountsCreated;
    }

    /**
     *Add to the accounts created
     * @param account Instance of account
     */
    public void addToAccountsCreated(Account account) {
        AccountsCreated.add(account);
    }


    /**
     * Get a summary of the user's accounts
     */
    public void viewInfo(){

        int totalDebitAmount = 0;
        int totalCreditAmount = 0;
        if (AccountsCreated == null){
            System.out.println("Nothing to view, you have not created an account yet!");
        }else{

        String s = "Account holder: " + this.username + " Report of FundHolders:";
        for(int i = 0; i < AccountsCreated.size(); i++){
            s += AccountsCreated.get(i).getAccountType() + "Number: " + AccountsCreated.get(i).getAccountNum() + "\n" +
                     "\n Current Balance:" +
                    AccountsCreated.get(i).getBalance() + " Most Recent Transactions: " + "BM GET MOSTRECENTTRANSACTION";
            if (AccountsCreated.get(i) instanceof Debit){
                totalDebitAmount += AccountsCreated.get(i).getBalance();
            }else{
                totalCreditAmount += AccountsCreated.get(i).getBalance();
            }
        }
        s += "Net Total: " + (totalDebitAmount - totalCreditAmount);
        System.out.println(s);
    }}
}
