package phase2.FundStores;

import com.sun.istack.internal.Nullable;
import phase2.FundStores.Asset.ChequingAccount;
import phase2.FundStores.Asset.Debit;
import phase2.FundStores.Asset.SavingsAccount;
import phase2.FundStores.Debt.Credit;
import phase2.Operators.BankAccountUser.PointSystemUser;
import phase2.Operators.BankAccountUser.User;
import phase2.Operators.BankWorker.BankManager;
import phase2.FundStores.ATM;


import java.io.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

public abstract class Account implements Serializable, Observer, AccountDepositable, Billable, Withdrawable {
    protected static ArrayList<Account> accountsDatabase = new ArrayList<>();
    protected static int accountNumTotal = 0;
    protected int accountNum = accountNumTotal;
    protected String holderName;
    protected String holderName2;
    protected double balance;
    public String accountType;
    protected ArrayList<User> accountUsers = new ArrayList<User>();
    public ATM atm;
    protected Object[] transactionInfoTempHolder;
    protected Stack<Object[]> history;
    protected User accountHolder;
    protected User accountHolder2;
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
        this.transactionInfoTempHolder = new Object[3];
    }

    /**
     * Overloading for Account class constructor to take in two distinct users
     * @param accountHolder first holder of account
     * @param accountHolder2 second holder of account
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
        this.transactionInfoTempHolder = new Object[3];
    }

    /**
     *
     * @return the account databse in an Array list
     */
    public ArrayList<Account> getAccountsDatabase() {return accountsDatabase;}


    /**
     * Get name of account holder
     * @return String of holder's name
     */
    public String getHolderName(){
        return holderName;
    }


    /**
     * Get number of account
     * @return Int for account's number
     */
    public int getAccountNum(){
        return accountNum;
    }

    /**
     * Set instance of ATM
     * @param a instance of ATM
     */
    public void setATM(ATM a){
        this.atm = a;
    }


    /**
     * Get type of account
     * @return String of account type
     */
    public String getAccountType() { return accountType; }

    /**
     * Withdraw money from account
     * @param amount Amount of money to be withdrawn
     * @return Boolean if withdraw is successful or not
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
            }else if (this instanceof Credit){
                balance -= amount;
            }
        }
        this.updateHistory("withdraw", amount, null);
        System.out.println("Withdrawal successful, Account: " + this.accountNum +
                " now has a decreased balance of: " + balance + "$CAD");
        if (accountHolder instanceof PointSystemUser){
            ((PointSystemUser) accountHolder).setNumPointsIncrease();}
        return true;
    }


    /**
     *Withdraw amount from account using ATM
     * @param amount Amount of money to withdraw
     */
    public boolean withdrawFromATM(int amount) {
        if (!validAmountInput(amount)){
            return false;
        }else{
            if (atm.validWithdraw(amount)) {
                atm.minus(amount);
                withdrawFromAccount(amount);
            }
            if (accountHolder instanceof PointSystemUser){
                ((PointSystemUser) accountHolder).setNumPointsIncrease();}
            return true;
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
     * Set the transaction holder
     * @param action Action made from transaction
     * @param amount Amount of money from transaction
     * @param receiver Receiver of transaction
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
        transactionInfoTempHolder = new Object[3];
    }

    /**
     * Deposit amount into account
     * @param amount Amount of money to deposit
     * @return True if money is deposited into account
     */
    public boolean depositToAccount(double amount) {
        this.balance += amount;
        this.updateHistory("cash deposit", amount, null);
        if (accountHolder instanceof PointSystemUser){
            ((PointSystemUser) accountHolder).setNumPointsIncrease();}
        System.out.println("Deposit successful, Account: " + this.accountNum +
                " now has an increased balance of: " + balance + "CAD$");
        return true;
    }

    /**
     * Deposit amount into account from cheque
     * @param amount Amount of money to deposit
     * @return True if money from cheque is deposited into account
     */
    public boolean depositChequeToAccount(double amount) {
        depositToAccount(amount);
        this.updateHistory("cheque deposit", amount, null);
        if (accountHolder instanceof PointSystemUser){
            ((PointSystemUser) accountHolder).setNumPointsIncrease();}
        return true;
    }


    /**
     * View the last action performed in this account
     * @return String of last action made in account
     */
    public String viewLastAction() {
        Object[] lastActionInfo = history.pop();
        history.push(lastActionInfo);
        if (lastActionInfo[2] == null){
            return  "Last action category: " + lastActionInfo[0] + "\n with " +
                    "an amount of: " + lastActionInfo[1];
           }
        else{
            return "Last action category: " + lastActionInfo[0] + "\n with " +
                    "an amount of: " + lastActionInfo[1] + "\n " +
                    "To account number: " + (((Account) lastActionInfo[2]).getAccountNum());
            }
    }

    /**
     * Pay the bill
     * @param amount Amount of money to withdraw from account to pay bill
     * @return True if money is withdrawn from account to pay bill
     */
    public boolean payBill(double amount) {
        withdrawFromAccount(amount);
        try {
            File file = new File("phase2/txtfiles/Outgoing.txt");
            File tempFile = new File("phase2/txtfiles/Outgoing.txt");
            BufferedReader read = new BufferedReader(new FileReader(file));
            String line = read.readLine();
            BufferedWriter write = new BufferedWriter(new FileWriter(tempFile));
            while (line != null) {
                write.write(line + "\n");
                line = read.readLine();
            }
            read.close();
            write.write(holderName + " paid a bill of " + Double.toString(amount) + "\n");
            write.close();
            boolean successful = tempFile.renameTo(file);

        } catch (Exception ex) {ex.printStackTrace();}
        this.updateHistory("bill", amount, null);
        if (accountHolder instanceof PointSystemUser){
            ((PointSystemUser) accountHolder).setNumPointsIncrease();}
        return true;}


    /**
     * Check if amount is usable by atm
     * @param amount The amount of funds being checked
     */
    public boolean validAmountInput(double amount){
        return 0 <= amount && amount %5 == 0;
    }

    /**
     * Add cash points to account
     * @return Boolean if cash points are added to account
     */
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

    /**
     * Return the owner of this account
     * @return a user, the instance of the account holder
     */
    public User getAccountHolder(){
        return accountHolder;
    }

    /**
     * Return a summary of this account
     * @return String of a summary of account including: accountholder, accounttype, account num, and amountheld
     */
    public String summarize() {
        System.out.println("Account holder(s): " + holderName +
                " " + holderName2 + "\n " +
                "Account Type:" + accountType + "\n" +
                "Account Number: " + accountNum +
                "\n Holds: " + this.getBalance() + "CAD$");
        StringBuilder sb = new StringBuilder();
        sb.append("Account holder(s): ");
        sb.append(holderName);
        sb.append(", ");
        sb.append(holderName2);
        sb.append("\n Account Type: ");
        sb.append(accountType);
        sb.append("\n Account Number: ");
        sb.append(accountNum);
        sb.append("\n Holds: ");
        sb.append(balance);
        sb.append("CAD$");
        return sb.toString();
    }

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
            System.out.println("Sorry, your last action could not be reversed because you payed a bill. We will remove this" +
                    "action from your history but you can get a list of your payed bills from the bank teller");
        }else{
            if (transferInfo[0].equals("transfer")) {
                ((Debit) transferInfo[2]).transfer((double) transferInfo[1], this);
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
     * Helper function to get last line of date.txt file
     * @return last line on file
     */
    public String getLastLine() {
        String currLine;
        String lastLine = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("phase2/txtfiles/date.txt"));

            while ((currLine = br.readLine()) != null) {
                lastLine = currLine;
            }
        } catch (IOException e) {
        }
        return lastLine;
    }

    /**
     * Update cash points
     */
    @Override
    public void update(Observable o, Object arg) {
        if (cashPoints() && (boolean) arg){
            System.out.println("Sorry, you do not have enough points to cash at the moment!");}
        else{
            System.out.println("You have successfully cashed all of your available points." +
                    " You now have " + ((PointSystemUser) accountHolder).getNumPoints() + " points.");}
    }

    public abstract boolean addToBill();
}