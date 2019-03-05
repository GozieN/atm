package phase1;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;


public class User extends Operator {
    private static ArrayList<User> userdatabase = new ArrayList<User>();
    private static int numusers = 0;
    private String username;
    private String password;
    private CreditCardAccount cca = null;
    private LineOfCreditAccount loca = null;
    private ChequingAccount ca = null;
    private SavingsAccount sa = null;

    // user constructor (BM use ONLY in console)
    public User(String username, String password) {
        super(username, password);
        this.username = username;
        this.password = password;
        numusers += 1;
        userdatabase.add(this);
    }

    // setpassword method (BM use ONLY in console)
    public void setpassword(String newpassword) {
        this.password = newpassword;
    }

    // changepassword method
    public String changepassword(String currentpassword, String newpassword) {
        if (currentpassword.equals(this.password)) {
            this.password = newpassword;
            return "your password has successfully been changed";
        } else {
            return "you have entered the wrong current password. unable to change password";
        }
    }

    //[Angela]
    //GOZIE - OBSERVER PATTERN
    // requestaccountcreation method
    // must interact with bankmanager to do this
    // how to implement this? maybe::
    // requestnotifier setter method in BM class, user method calls it
    // user method passes username information and account type being requested to BM setter ?
    // this information would go in output.txt and the accounts
    // would be created the next time the program is launched (the next time input.txt is read)
    // requires information going from output.txt to input.txt

    @Override
    public void viewInfo() {
        System.out.println("Account holder: " + this.username + " "
                + "DATE AND TIME " +
                "" + "Account summary:" + "Account number: " + "REFER TO ACCOUNT NUM" + " contains: (double of amount)");

//TOODO - dependency INJECTION
    }
    // user will not have to input any parameters (direct call)

    @Override
    public void viewBalance(Account account) {
        System.out.println("Account: " + account.getAccountNum() + " now has a balance of: " + account.getBalance());
    }
    // user input parameters: account num/type

    @Override
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

    @Override
    public void withdraw(int amount, Account account) {
        account.setBalance(account.getBalance() - amount);
        System.out.println("Withdrawal successful, Account: " + account.getAccountNum() +
                " now has a decreased balance of: " + account.getBalance() + "currency");
    }

    @Override
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
        //dCHECK

    }}

