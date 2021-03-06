package phase1.FundHolders;

/**
 *
 */
public class Credit extends Account {
    boolean isLOC;

    /**
     * Credit class constructor
     * @param accountNum Number used to identify a specific account
     * @param holderName Name of holder of the account
     * @param balance Amount of money found in account
     * @param isLOC Checks if type of account is a Line of Credit
     */
    public Credit(int accountNum, String holderName, double balance, boolean isLOC){
        super(accountNum, holderName, balance, "CreditCardAccount");
        this.isLOC = isLOC;
        if(isLOC){
            accountType = "LineOfCredit";
        }
    }
}
