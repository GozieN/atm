package phase2.Operators.BankAccountUser;

import phase2.FundStores.*;
import phase2.FundStores.Asset.ChequingAccount;
import phase2.FundStores.Asset.SavingsAccount;
import phase2.Operators.Contract;
import phase2.Operators.Operator;

import java.util.*;
import java.io.Serializable;
import java.util.Iterator;

/**
 *
 */
public class User extends Operator implements Serializable, Iterable<Account>, Contract {
    private static ArrayList<User> userDatabase;
    private static int numUsers = 0;
    private int numChequingAccounts = 0;
    private String password;
    private String username;
    private String userType;
    private ArrayList<Account> accountsCreated;


    /**
     * User constructor
     * @param username Username used to login to account
     * @param password Password used to login to account
     */
    public User(String username, String password) {
        super(username, password);
        numUsers++;
        //this.userDatabase = new ArrayList<User>(); - WOULD ERASE OLD INFO!
        userDatabase.add(this);
        this.accountsCreated = new ArrayList<Account>();
    }


    /**
     * Change the user's password
     * @param currentPassword The current password used to login to account
     * @param newPassword Create new password for login to account
     */
    public String changePassword(String currentPassword, String newPassword) {
        if (currentPassword.equals(this.password)) {
            this.password = newPassword;
            String s = username + ", your password has successfully been changed";
            return s;
        } else {
            String s = username + ",you have entered the wrong current password. " +
                    "unable to change password";
            return s;
        }
    }

    /**
     * Return the type of user that this is
     * @return a string representing the type of user
     */

    public String getUserType() {
        return userType;
    }

    /**
     * Opt into of the point system
     * @return String - the confirmation.
     */
    public String optIntoPointSystem(){
        String s = "";
        PointSystemUser alteredUser;
        alteredUser = new PointSystemUser(getUsername(), getPassword());
        alteredUser.setAccountsCreated(this.getAccountsCreated());
        this.numUsers -= 1;
        //iterate over arrayList of users and replace instance!!
        s = "You have successfully opted int of the point system! You will start with " +
                "an point balance of of 50 point.";
        return s;
    }

    /**
     * Display the features that this user has.
     * @return String - the features this user has!
     */
    public String viewCapabilities(){
        String s = "";
        s = "As a new Point System User, you are able to do the following: \n" +
                "- Request to delete your account at any point in time.\n" +
                "- Opt into being a point system user at any point in time.\n" +
                "- Change your password at any time. \n" +
                "- Create a new account at any time. \n" +
                "- View a summary of a single account. \n" +
                "- View a summary of all your existing accounts";

        return s;
    }

    /**
     * Display the features that this user has.
     * @return String - the features this user has!
     */
    public String viewContract(){
        String s;
        s = "As a new Standard User of the Bank, " +
                "you agree not to engage in fraudulent behavior, " +
                "especially when filling in information for account consultation purposes. Click next to agree.";
        return s;
    }


    /**
     * Return the number of Chequing accounts the user has to determine what we should
     * designate to be a primary account!
     * @return the number of chequing accounts the user has
     */
    public int getNumChequingAccounts(){
        return numChequingAccounts;
    }

    /**
     * Return the number of Chequing accounts the user has to determine what we should
     * designate to be a primary account!
     * @return the number of chequing accounts the user has
     */
    public void setNumChequingAccounts(){
         ++numChequingAccounts;
    }

    /**
     * Set the type of the account.
     * @param userType the type of the user.
     */
    public void setUserType(String userType){
        this.userType = userType;
    }


    /**
     * Return a list of the accounts of the users
     * @return ArrayList of user accounts created
     */
    public ArrayList<Account> getAccountsCreated() {
        return accountsCreated;
    }

    /**
     * Set a list of bank accounts created
     * @param accountsCreated A list of bank accounts created
     */
    public void setAccountsCreated(ArrayList<Account> accountsCreated) {
        this.accountsCreated = accountsCreated;
    }

    /**
     *Add to the accounts created
     * @param account Instance of account
     */
    public void addToAccountsCreated(Account account) {
        accountsCreated.add(account);
    }


    /**
     * Get a summary of the user's accounts
     */
    public String viewInfo(){

        int totalDebitAmount = 0;
        int totalCreditAmount = 0;
        if (accountsCreated == null){
            String s = "Nothing to view, you have not created an account yet!";
            return s;
        }else{

        String s = "Account holder: " + this.username + " Report of FundHolders:";
        for(int i = 0; i < accountsCreated.size(); i++){
            s += accountsCreated.get(i).getAccountType() + "Number: " + accountsCreated.get(i).getAccountNum() + "\n" +
                     "\n Current Balance:" +
                    accountsCreated.get(i).getBalance() + " Most Recent Transactions: " +
                    accountsCreated.get(i).viewLastAction();
            if (accountsCreated.get(i) instanceof ChequingAccount || accountsCreated.get(i) instanceof SavingsAccount){
                totalDebitAmount += accountsCreated.get(i).getBalance();
            }else{
                totalCreditAmount += accountsCreated.get(i).getBalance();
            }
        }
        s += "Net Total: " + (totalDebitAmount - totalCreditAmount);
        return s;
    }}

    @Override
    public Iterator<Account> iterator() {
        return accountsCreated.iterator();
    }

    class AccountIterator implements Iterator<Account> {
        int i = 0;

        @Override
        public boolean hasNext() {
            if (i >= accountsCreated.size()) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public Account next() {
            return accountsCreated.get(i++);
        }

        @Override
        public void remove() {
            accountsCreated.remove(--i);
        }
    }
}