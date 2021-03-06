package phase2.Operators;

import phase2.FundStores.Account;

import java.util.Observable;


public class Operator extends Observable implements java.io.Serializable{
    protected String username;
    protected String password;

    /**
     * User constructor
     * @param username Username used to login to account
     * @param password Password used to login to account
     */
    public Operator(String username, String password) {
        this.username = username;
        this.password = password;
    }

//    public Operator() {}

    /**
     * Return the user's username
     * @return A string of user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the password
     * @param password Password used to login to account
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return the user's password
     * @return A string of user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the username
     * @param username Username used to login to account
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Return a summary of the accounts
     * @param account Instance of account
     */
    public String singleAccountSummary(Account account) {
        return account.summarize();
    }
}