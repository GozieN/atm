package phase2.Operators.BankWorker;

import phase2.FundStores.ATM;
import java.util.ArrayList;

public class ATMMaintainer extends Systems {

    private BankManager bm;

    public void setBm(BankManager bm){
        this.bm = bm;
    }

    public BankManager getBm(){
        return bm;
    }

    public void shutdownATM(){//shuts down the ATM

    }

    public void starupATM(){//starts ATM

    }

    public void FeedInATMBills(int dollarAmount, ATM atm){//
        ArrayList<Integer> numberStore = new ArrayList<>();
        String amountStringRepresentation = String.valueOf(dollarAmount);
        int amountNumDigits = amountStringRepresentation.length();
        int multiplier = 1;
        for (int i = amountNumDigits-1; i >= 0; i--) {
            numberStore.add( Character.getNumericValue(amountStringRepresentation.charAt(i)) * multiplier );
            multiplier = multiplier * 10;
        }
        for (int number : numberStore) {
            if (number % 50 == 0) {
                atm.setNum50Bills(atm.getNum50Bills() + number/50);
            } else if (number % 20 == 0) {
                atm.setNum20Bills(atm.getNum20Bills() + number/20);
            } else if (number % 10 == 0) {
                atm.setNum10Bills(atm.getNum10Bills() + number/10);
            } else if (number % 5 == 0) { // could have done else statement here, but else if is more clear
                atm.setNum5Bills(atm.getNum5Bills() + number/5);
            }
        }
        atm.restock();

    }

    public void EmptyOutATMBills(int dollarAmount, ATM atm){
        ArrayList<Integer> numberStore = new ArrayList<>();
        String amountStringRepresentation = String.valueOf(dollarAmount);
        int amountNumDigits = amountStringRepresentation.length();
        int multiplier = 1;
        for (int i = amountNumDigits-1; i >= 0; i--) {
            numberStore.add( Character.getNumericValue(amountStringRepresentation.charAt(i)) * multiplier );
            multiplier = multiplier * 10;
        }
        for (int number : numberStore) {
            if (number % 50 == 0) {
                atm.setNum50Bills(atm.getNum50Bills() - number/50);
            } else if (number % 20 == 0) {
                atm.setNum20Bills(atm.getNum20Bills() - number/20);
            } else if (number % 10 == 0) {
                atm.setNum10Bills(atm.getNum50Bills() - number/10);
            } else if (number % 5 == 0) { // could have done else statement here, but else if is more clear
                atm.setNum5Bills(atm.getNum5Bills() - number/5);
            }
        }
        atm.restock();

    }
}