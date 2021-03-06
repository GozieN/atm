package phase2.FundStores.Asset;

import phase2.FundStores.Account;
import phase2.FundStores.Asset.Debit;
import phase2.Operators.BankAccountUser.User;
import phase2.FundStores.Debt.Credit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;


// PrepaidCredit account works like a prepaid gift card (only able to reload, balance never goes under 0, can only spend
// up to limit given + incurs $3 deduction every month)

public class PrepaidCredit extends Account implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * PrepaidCredit constructor
     * @param accountHolder
     *
     */
    public PrepaidCredit(User accountHolder) {
        //MUST SET INITIAL AMOUNT.
        super(accountHolder);
        accountType = "prepaid";
        if (balance > 0) {
            this.balance = balance;
        }
    }

    /**
     * Deduct monthly fees from prepaid card
     */
    public void monthlyFees() {
        double fee = 3;
        if (("01").equals(getLastLine().substring(0, 2))) {
            if (balance > 3) {
                setBalance(getBalance() - fee);
            } else {
                addToBill();
            }
        }
    }

    /**
     * Add transaction to bill
     * @return True if transaction is added to bill
     */
    @Override
    public boolean addToBill() {
        return true;
    }
}
