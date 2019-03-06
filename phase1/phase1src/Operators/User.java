package Operators;

import Accounts.*;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;


public class User extends Observable implements UserOperator{
    private static ArrayList<User> userdatabase = new ArrayList<User>();
    private static int numusers = 0;
    private String username;
    private String password;
    private Credit cca = null;
    private Credit loca = null;
    private ChequingAccount ca = null;
    private SavingsAccount sa = null;
    private ArrayList<Account> AccountsCreated = new ArrayList<Account>();
    private BankManager bmObserver = new BankManager();


    // user constructor (BM use ONLY in console)
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        numusers++;
        userdatabase.add(this);
    }

    // setpassword method (BM use ONLY in console)
    public void setInitialPassword(String newPassword) {
        this.password = newPassword;
    }

    // changepassword method
    public String changePassword(String currentPassword, String newPassword) {
        if (currentPassword.equals(this.password)) {
            this.password = newPassword;
            return "your password has successfully been changed";
        } else {
            return "you have entered the wrong current password. unable to change password";
        }
    }
    //[Angela]
    //GOZIE - OBSERVER PATTERN
     public void requestUserAccountCreation(Account account) {
         this.AccountsCreated.add(account);
         setChanged();
         notifyObservers(account);
         clearChanged();
     }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setObserver(BankManager observer) {
        this.bmObserver = observer;
    }

    public ArrayList<Account> getAccountsCreated() {
        return AccountsCreated;
    }

    // must interact with bankmanager to do this
    // how to implement this? maybe::
    // requestnotifier setter method in BM class, user method calls it
    // user method passes username information and account type being requested to BM setter ?
    // this information would go in output.txt and the accounts
    // would be created the next time the program is launched (the next time input.txt is read)
    // requires information going from output.txt to input.txt

    public void singleAccountSummary(Account account) {
        System.out.println("Account holder: " + this.username + " "
                + "DATE AND TIME " +
                "" + "Account summary:" + account.getAccountType(account) +"Account Number: "
                + account.getAccountNum() + " contains: " + account.getBalance() + "currency");}

    // user will not have to input any parameters (direct call)
    //CONSIDER OPTION OF THIS VIEW

    public String viewInfo(){
        int totalDebitAmount = 0;
        int totalCreditAmount = 0;

        String s = "Account holder: " + this.username + " Report of Accounts:";
        for(int i = 0; i < AccountsCreated.size(); i++){
            s += AccountsCreated.get(i).getAccountType(AccountsCreated.get(i)) + "Number: " + AccountsCreated.get(i).getAccountNum() + "\n" +
                    " created on: GETDATEOFCREATION" + "\n Current Balance:" +
                    AccountsCreated.get(i).getBalance() + " Most Recent Transaction: " + "BM GET MOSTRECENTTRANSACTION";
            if (AccountsCreated.get(i) instanceof Debit){
                 totalDebitAmount += AccountsCreated.get(i).getBalance();
            }else{
            totalCreditAmount += AccountsCreated.get(i).getBalance();}
        }
        s += "Net Total: " + (totalDebitAmount - totalCreditAmount);
        return s;
    }

    // user will not have to input any parameters (direct call)

    public void viewBalance(Account account) {
        System.out.println("Account: " + account.getAccountNum() + " has a balance of: " + account.getBalance());
    }
    // user input parameters: account num/type

    public void transfer(int amount, Account from, Account to) {
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        //[Angela]
        try {
            PrintStream originalOut = System.out;
            PrintStream fileOut = new PrintStream("/.Outgoing.txt");
            System.setOut(fileOut);
            originalOut.println("[Amount] transferred to [Receiver Account]");
            System.setOut(originalOut);
        } catch (FileNotFoundException ex) {ex.printStackTrace();}

    }

    public void withdraw(int amount, Account account) {
        account.setBalance(account.getBalance() - amount);
        System.out.println("Withdrawal successful, Account: " + account.getAccountNum() +
                " now has a decreased balance of: " + account.getBalance() + "currency");
    }

    public void deposit(int amount, Account account) {
        account.setBalance( account.getBalance() + amount);
        System.out.println("Deposit successful, Account: " + account.getAccountNum() +
                " now has an increased balance of: " + account.getBalance() + "currency");
    }

    public void eTransfer(int amount, Account from, Account to) {
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);}

    public void payBill(int amount, Account from, Account to) {
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        //CHECK specs

    }}
