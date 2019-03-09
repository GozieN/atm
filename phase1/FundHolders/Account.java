package phase1.FundHolders;

import phase1.FundTransfers.*;
import phase1.Operators.*;
import java.util.*;


public abstract class Account {
    private int accountNum;
    private String holderName;

    private double balance;
    public String accountType;
    private ArrayList<TransferOfFunds> history;

    /**
     * Account class constructor
     * @param accountNum
     * @param holderName
     * @param balance
     * @param accountType
     */
    public Account(int accountNum, String holderName, double balance, String accountType){
        history = new ArrayList<TransferOfFunds>();
        this.accountType = accountType;
        this.accountNum = accountNum;
        this.holderName = holderName;
        this.balance = balance;
    }

    /**
     * Add a transaction to the list of fund transfers
     * @param transactionInfo
     */
    public void updateHistory(TransferOfFunds transactionInfo){
        history.add(transactionInfo);
    }

    /**
     *
     * @param amount
     */
    public abstract void deposit(double amount);

    /**
     *
     * @param amount
     */
    public abstract void withdraw(double amount);

    /**
     * Set the balance of the account
     * @param balance
     */
    public void setBalance(double balance){
        this.balance = balance;
    }

    /**
     * Set the balance of the account
     * @return double
     */
    public double getBalance(){
        return this.balance;
    }

    public static class transaction {
        private int senderAccount;
        private int receiverAccount;
        private double amount;

    }
}