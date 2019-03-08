package phase1.FundHolders;

public class ChequingAccount extends Debit {
    private boolean isPrimary;


    public ChequingAccount(int accountNum, String holderName, double balance, boolean isPrimary){
        super(accountNum, holderName, balance, "ChequingAccount");
        this.isPrimary = isPrimary;
    }

    public void withdraw(double amount){
        if ((getBalance() - amount) >= -100)
            setBalance(getBalance() - amount);
    }

}

