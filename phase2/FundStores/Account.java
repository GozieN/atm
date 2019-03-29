package phase2.FundStores;

import com.sun.istack.internal.Nullable;
import phase2.FundStores.Asset.ChequingAccount;
import phase2.FundStores.Asset.Debit;
import phase2.FundStores.Asset.SavingsAccount;
import phase2.FundStores.Debt.LineOfCredit;
import phase2.Operators.BankAccountUser.PointSystemUser;
import phase2.Operators.BankAccountUser.User;
import phase2.Operators.BankWorker.BankManager;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

public class Account implements Serializable, Observer {
    private static ArrayList<Account> accountsDatabase = new ArrayList<>();
    private static int accountNumTotal = 0;
    private int accountNum = accountNumTotal;
    private String holderName;
    private String holderName2;
    private double balance;
    public String accountType;
    private ArrayList<User> accountUsers = new ArrayList<User>();

    public ATM atm;
    private Object[] transactionInfoTempHolder;
    private Stack<Object[]> history;
    private User accountHolder;
    private User accountHolder2;
    public BankManager bm = new BankManager("", "");


    /**
     * Account class constructor
     * @param accountHolder holder of the account
     */
    public Account(User accountHolder) {
        accountsDatabase.add(this);
        this.accountUsers.add(accountHolder);
        history = new Stack<>();
        this.accountType = "Account";
        this.accountHolder = accountHolder;
        this.accountNumTotal++;
        this.holderName = accountHolder.getUsername();
        this.transactionInfoTempHolder = new Object[2];
    }

    /**
     * Overloading for Account class constructor to take in two distinct users
     * @param accountHolder
     */
    public Account(User accountHolder, User accountHolder2) {
        accountsDatabase.add(this);
        history = new Stack<>();
        this.accountType = "Account";
        this.accountNumTotal++;
        this.accountHolder = accountHolder;
        this.accountHolder2 = accountHolder2;
        this.accountUsers.add(accountHolder);
        this.accountUsers.add(accountHolder2);
        this.holderName = accountHolder.getUsername();
        this.holderName2 = accountHolder2.getUsername();
        this.transactionInfoTempHolder = new Object[2];

        try {
            FileOutputStream file = new FileOutputStream("pahse2/txtfiles/AccountDatabase.txt");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(accountsDatabase);
            out.close();
            file.close();
        } catch (Exception ex) {ex.printStackTrace();}
    }

    public ArrayList<Account> getAccountsDatabase() {return accountsDatabase;}

    /**
     * Get name of account holder
     * @return String of holder's name
     */
    public String getHolderName(){
        return holderName;
    }


    /**f
     * Get number of account
     * @return Int for account's number
     */
    public int getAccountNum(){
        return accountNum;
    }

    public void setATM(ATM a){
        this.atm = a;
    }


    /**
     * Get type of account
     * @return String of account type
     */
    public String getAccountType() { return accountType; }

    /**
     * Change actions performed in account history
     */
    public void undoTransaction(){
        Object[] transferInfo = history.pop();
        if (transferInfo == null){
            System.out.println("Sorry, your last action could not be reversed because you " +
                    "have yet to make a transaction");
        }
        if (transferInfo[0].equals("bill")){
            history.push(transferInfo);
            System.out.println("Sorry, your last action could not be reversed because you payed a bill.");
        }else{
            if (transferInfo[0].equals("transfer")) {
                ((Account) transferInfo[2]).transfer((double) transferInfo[1], this);
            } else if (transferInfo[0].equals("withdraw")) {
                ((Account) transferInfo[2]).depositToAccount((double) transferInfo[1]);
            }else if (transferInfo[0].equals("deposit") || transferInfo[0].equals("cheque deposit")){
                ((Account) transferInfo[2]).withdrawFromAccount((double) transferInfo[1]);
            }
            System.out.println(getHolderName() + ", The last action that you performed was a" + transferInfo[0] + "" +
                    " of amount " + transferInfo[1] + " has been reversed upon your request.");
        }
    }

    /**
     * Set balance of account
     * @param balance Total amount of money in account
     */
    public void setBalance(double balance){
        this.balance = balance;
    }

    /**
     * Get balance of account
     * @return Int for balance of account
     */
    public double getBalance(){
        return this.balance;
    }

    /**
     * Get the history of account
     * @return stack form of history
     */
    public Stack<Object[]> getHistory(){
        return this.history;
    }


    /**
     * Deposit amount into account
     * @param amount Amount of money to deposit
     */
    public boolean depositIntoATM(int amount) {
        if (!validAmountInput(amount)){
            return false;
        }else{
            atm.plus(amount);
            this.depositToAccount(amount);
            return true;
        }
    }


    /**
     *Withdraw amount from account using ATM
     * @param amount Amount of money to withdraw
     */
    public boolean withdrawFromATM(int amount) {
        if (!validAmountInput(amount)){
            return false;
        }else{
            atm.minus(amount);
            withdrawFromAccount(amount);
            if (accountHolder instanceof PointSystemUser){
                ((PointSystemUser) accountHolder).setNumPointsIncrease();}
            return true;
        }}

    /**
     *Withdraw amount from account
     * @param amount Amount of money to withdraw
     */
    public boolean withdrawFromAccount(double amount) {
        if (!(balance - amount > 0) && !(this instanceof ChequingAccount) ){
            System.out.println("Sorry, you are unable to withdraw this amount from your " +
                    accountType + "try withdrawing a smaller amount or review your account " +
                    "information!");
            return false;
        }
        else if(this instanceof Debit){
            if (this instanceof ChequingAccount){
                if ((balance - amount) >= -100){
                    balance -= amount;}
            }
            else if (this instanceof SavingsAccount){
                if ((balance - amount) >= 0){
                    balance -= amount;
                }
            }
        }else if (this instanceof LineOfCredit){
            balance -= amount;
        }
        this.updateHistory("withdraw", amount, null);
        System.out.println("Withdrawal successful, Account: " + this.accountNum +
                " now has a decreased balance of: " + balance + "$CAD");
        if (accountHolder instanceof PointSystemUser){
            ((PointSystemUser) accountHolder).setNumPointsIncrease();}
        return true;}

    /**
     * Set the transaction holder
     */
    public void updateHistory(String action, double amount, @Nullable Account receiver){
        transactionInfoTempHolder[0] = action;
        transactionInfoTempHolder[1] = amount;
        transactionInfoTempHolder[2] = receiver;
        history.add(transactionInfoTempHolder);
        clearTransactionTempHolder();
    }

    /**
     * Clear the transaction holder
     */
    public void clearTransactionTempHolder(){
        for( int i = 0; i <3; i++){
            transactionInfoTempHolder[i] = null;
        }
        transactionInfoTempHolder = null;
    }

    /**
     * Deposit amount into account
     * @param amount Amount of money to deposit
     */
    public boolean depositToAccount(double amount) {
        this.balance =+amount;
        System.out.println("Deposit successful, Account: " + this.accountNum +
                " now has an increased balance of: " + balance + "CAD$");
        this.updateHistory("deposit", amount, null);
        if (accountHolder instanceof PointSystemUser){
            ((PointSystemUser) accountHolder).setNumPointsIncrease();}
        return true;
    }

    /**
     * Deposit amount into account from cheque
     * @param amount Amount of money to deposit
     */
    public boolean depositChequeToAccount(double amount) {
        depositToAccount(amount);
        this.updateHistory("cheque", amount, null);
        if (accountHolder instanceof PointSystemUser){
            ((PointSystemUser) accountHolder).setNumPointsIncrease();}
        return true;
    }

    /**
     * Transfer funds from sender to receiver
     * @param amount Amount of money to be transferred
     * @param receiverAccount Account which money will be transferred to
     */
    public boolean transfer(double amount, Account receiverAccount) {
        withdrawFromAccount(amount);
        receiverAccount.depositToAccount(amount);
        receiverAccount.updateHistory("transfer", amount, this);
        System.out.println("Your transaction to account number: " + receiverAccount.getAccountNum() + " was successful, your new balance is: " +
                receiverAccount.getBalance() + "$CAD");
        if (accountHolder instanceof PointSystemUser){
            ((PointSystemUser) accountHolder).setNumPointsIncrease();}
        return true;
    }

    /**
     * View the last action performed in this account/
     */
    public String viewLastAction() {
        Object[] lastActionInfo = history.pop();
        history.push(lastActionInfo);
        if (lastActionInfo[2] == null){
            String s = "Your most recent action fell under the category: " + lastActionInfo[0] + "\n with " +
                    "an amount of: " + lastActionInfo[1];
            return s;}
        else{
            String s ="Your most recent action fell under the category: " + lastActionInfo[0] + "\n with " +
                    "an amount of: " + lastActionInfo[1] + "\n " +
                    "To account number: " + (((Account) lastActionInfo[2]).getAccountNum());
            return s;}
    }

    /**
     * Pay the bill
     * @param amount Amount of money to withdraw from account to pay bill
     */
    public boolean payBill(double amount) {
        withdrawFromAccount(amount);
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("phase2/txtfiles/Outgoing.txt"));
            out.write(holderName + "paid a bill of " + Double.toString(amount));
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.updateHistory("bill", amount, null);
        if (accountHolder instanceof PointSystemUser){
            ((PointSystemUser) accountHolder).setNumPointsIncrease();}
        return true;}

    /**
     * check if amount is usable by atm
     * @param amount - the amount of funds being checked
     */
    public boolean validAmountInput(double amount){
        return amount%5 ==0 || amount < 0;
    }


        public boolean cashPoints(){
        if (((PointSystemUser) accountHolder).getNumPoints()  < 20){
             return false;
        }
        else{
            while (((PointSystemUser) accountHolder).getNumPoints() > 20){
                PointSystemUser ah = (PointSystemUser) accountHolder;
                ah.setNumPointsIncrease();
                ah.setNumPointsDecrease();
                balance +=  1.50;}
            return true;}
    }

    public User getAccountHolder(){
        return accountHolder;
    }


    @Override
    public void update(Observable o, Object arg) {
        if (cashPoints() && (boolean) arg){
            System.out.println("Sorry, you do not have enough points to cash at the moment!");}
        else{
            System.out.println("You have successfully cashed all of your available points." +
                    " You now have " + ((PointSystemUser) accountHolder).getNumPoints() + " points.");}
    }
    }