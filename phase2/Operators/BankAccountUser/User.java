package phase2.Operators.BankAccountUser;

import phase2.FundStores.*;
import phase2.FundStores.Asset.ChequingAccount;
import phase2.FundStores.Asset.Debit;
import phase2.FundStores.Asset.SavingsAccount;
import phase2.FundStores.Debt.Credit;
import phase2.Operators.BankWorker.BankManager;
import phase2.Operators.Contract;
import phase2.Operators.Operator;

import java.io.*;
import java.util.*;
import java.util.Iterator;

public class User extends Operator implements Serializable, Iterable<Account>, Contract {
    private static ArrayList<User> userDatabase = new ArrayList<User>();
    private static int numUsers = 0;
    private int numChequingAccounts = 0;
//    private String password;
//    private String username;
    private ArrayList<Account> accountsCreated;
    private int numTimesOptedIntoPointSystem = 0;
    private String userType;
    private BankManager bm;

    /**
     * User constructor
     * @param username Username used to login to account
     * @param password Password used to login to account
     */
    public User(String username, String password) {
        super(username, password);
        numUsers++;
//        this.username = super.getUsername();
//        this.password = super.getPassword();
        this.accountsCreated = new ArrayList<Account>();
        this.userType = "standard";
        if (!userDatabase.contains(this)) {
            this.userDatabase.add(this);
        }
    }

    /**
     * Get the type of the user
     * @return A string representing the type of the user
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Set the user's b,
     * @param bm, the bank manager.
     */
    public void setBm(BankManager bm) {
        this.bm = bm;
    }

    /**
     * Get the type of the user
     * @return A string representing the type of the user
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * Return the database of users.
     * @return the database of users.
     */
    public static ArrayList<User> getUserDatabase() {
        return userDatabase;
    }

    /**
     * Change the user's password
     * @param currentPassword The current password used to login to account
     * @param newPassword Create new password for login to account
     */
    public void changePassword(String currentPassword, String newPassword) {
//        User temp = this;
//        ArrayList<Account> tempAccs = this.getAccountsCreated();
//        User newUser = new User(this.getUsername(), newPassword);
//        for (Account account : tempAccs) {
//            newUser.addToAccountsCreated(account);
//        }
//        for (User user : bm.getUsers()) {
//            if (user.getUsername().equals(newUser.getUsername())) {
//                bm.deleteUser(user);
//                bm.getUsers().add(newUser);
//            }
//        }

//        String s = "";
        User newUser = new User(this.getUsername(), newPassword);
        newUser.accountsCreated.addAll(this.getAccountsCreated());

        for (User obj: bm.getUsers()) {
            if (obj.getUsername().equals(this.username) && currentPassword.equals(this.password)) {
                bm.createUser(newUser.getUsername(), newUser.getPassword());
            }
        }
    }

    /**
     * Opt into of the point system
     * @return String - the confirmation.
     */
    public String optIntoPointSystem(){
        PointSystemUser alteredUser;
        BankUserFactory b = new BankUserFactory(this.getUserType());
        alteredUser = b.determineOptInPointUserType(this);
        alteredUser.setAccountsCreated(this.accountsCreated);

        if (this.numTimesOptedIntoPointSystem > 1){
            alteredUser.setNumPoints(0);
        }
        return "You have successfully opted int of the point system! If this is your first time opting into this service, " +
                "you get an initial point balance of 50.";
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
     * Return the net total accounted for by all of the user's accounts.
     * @return the net total
     *
     */
    public double getNetTotal(){
        double totalAsset = 0;
        double totalDebt = 0;
        for (Account account: accountsCreated){
            if (account instanceof Debit){
                totalAsset += account.getBalance();
            } else if (account instanceof Credit){
                totalDebt -= account.getBalance();
            }
        } return totalAsset - totalDebt;
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
    public String viewInfo() {
    	String s;
        StringBuilder sb = new StringBuilder();

        if (this.accountsCreated.isEmpty()) {
            s = "Nothing to view, you have not created an account yet!";
            return s;
        } else {
            sb.append("Account holder: " + this.username + "\n Report of Account(s):\n");
            for(Account account:accountsCreated){
                sb.append(account.getAccountType() + "\n Number: " + account.getAccountNum() + "\n" +
                     "\n Current Balance:" +
                    account.getBalance() + "\n Most Recent Transactions: " +
                    account.viewLastAction() + "\n"); }
        sb.append("\n Net Total: " + getNetTotal());
	    }
	    return sb.toString();
    }

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

    public static void main(String[] args) {
        User u = new User("g", "g");
        System.out.println(u);
        u.optIntoPointSystem();


    }
}