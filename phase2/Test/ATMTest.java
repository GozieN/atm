package phase2.Test;
import phase2.FundStores.*;

import java.io.FileNotFoundException;


import org.junit.Before;
import org.junit.Test;
import phase2.Operators.BankWorker.BankManager;
// import sun.jvm.hotspot.debugger.cdbg.basic.BasicNamedFieldIdentifier;

import static org.junit.Assert.assertTrue;


public class ATMTest {
    private ATM atm;
    private BankManager bm = new BankManager("new", "bm");

    @Before
    public void setUp() {atm = new ATM();}

    @Test
    public void testPlus() {

        boolean amountAfterPlus = false;

    }

    @Test
    public void testMinus(){

    }

    @Test
    public void testRestock() throws FileNotFoundException {

        boolean result = false;

        atm = new ATM(4, 1, 14, 0);

        atm.restock();

        assert(atm.getNum5Bills() >= 100);
        assert(atm.getNum10Bills() >= 100);
        assert(atm.getNum20Bills() >= 100);

        System.out.println(atm.getNum50Bills());

    }

}
