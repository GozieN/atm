package phase1;

import phase1.FundHolders.ATM;
import phase1.Operators.BankManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Model {
    private BankManager BM = new BankManager("BMuser", "BMpass");
    private ATM atm = new ATM();
    private ArrayList<String> userUsernames = new ArrayList<>();
    private ArrayList<String> userPasswords = new ArrayList<>();

    // model constructor
    public Model() {
    }

    //NEED TO SET THE TRANSACTION ATM TO THIS ATM
    public ATM getAtm() {
        return atm;
    }

    //NEED TO HAVE AN OPTION TO DEPOSIT A CHEQUE - CALLS THE DEPOSIT CHEQUE METHOD IN TRANSACTIONS

    public void menuOperatorSelect() {
        // options: 1. bankmanager, 2. normal user
        System.out.println("enter 1 for bankmanager \n" +
                "enter 2 for normal user");
        Scanner optionScan = new Scanner(System.in);
        while (optionScan.hasNext()) {
            String optionIn = optionScan.next();
            if (optionIn.equals("1")) {
                // send to menuBM1
                menuBM1();
            } else if (optionIn.equals("2")) {
                // send to menuU1
                menuU1();
            } else {
                System.out.println("that is not an option \n" +
                        "enter 1 for bankmanager \n" +
                        "enter 2 for normal user");
            }
        }
    }

    public void menuBM1() {
        // options: 1. login, e. logoff and exit
        System.out.println("enter 1 to login \n" +
                "enter e to logoff and exit");
        Scanner optionScan = new Scanner(System.in);
        while (optionScan.hasNext()) {
            String optionIn = optionScan.next();
            if (optionIn.equals("1")) {
                System.out.println("enter your username");
                Scanner usernameScan = new Scanner(System.in);
                while (usernameScan.hasNext()) {
                    String usernameIn = usernameScan.next();
                    if (usernameIn.equals(this.BM.getUsername())) {
                        System.out.println("enter your password");
                        Scanner passwordScan = new Scanner(System.in);
                        while (passwordScan.hasNext()) {
                            String passwordIn = passwordScan.next();
                            if (passwordIn.equals(this.BM.getPassword())) {
                                // send to menuBM2
                                menuBM2();
                            } else {
                                System.out.println("wrong password. enter your password");
                            }
                        }
                    } else {
                        System.out.println("wrong username. enter your username");
                    }
                }
            } else if (optionIn.equals("e")) {
                System.out.println("returning to main menu");
                menuOperatorSelect();
            } else {
                System.out.println("that is not an option \n" +
                        "enter 1 to login \n" +
                        "enter e to logoff and exit");
            }
        }
    }

    public void menuU1() {
        // options: 1. login, 2. request creation of new user, e. exit
        System.out.println("enter 1 to login to existing user \n" +
                "enter 2 to request creation of new user \n" +
                "enter e to exit");
        Scanner optionScan = new Scanner(System.in);
        while (optionScan.hasNext()) {
            String optionIn = optionScan.next();
            if (optionIn.equals("1")) {
                System.out.println("enter your username");
                Scanner usernameScan = new Scanner(System.in);
                while (usernameScan.hasNext()) {
                    String usernameIn = usernameScan.next();
                    int index = -1;
                    for (String username : this.userUsernames) {
                        index += 1;
                        if (username.equals(usernameIn)) {
                            System.out.println("enter your password");
                            Scanner passwordScan = new Scanner(System.in);
                            while (passwordScan.hasNext()) {
                                String passwordIn = passwordScan.next();
                                if (passwordIn.equals(this.userPasswords.get(index))) {
                                    // send to menuU2
                                    menuU2();
                                } else {
                                    System.out.println("wrong password. enter your password");
                                }
                            }
                        } else {
                            System.out.println("wrong username. enter your username");
                        }
                    }
                }
            } else if (optionIn.equals("2")) {
                System.out.println("enter a new username");
                Scanner newUsernameScan = new Scanner(System.in);
                while (newUsernameScan.hasNext()) {
                    String newUsernameIn = newUsernameScan.next();
                    if (!(this.userUsernames.contains(newUsernameIn))) {
                        this.userUsernames.add(newUsernameIn);
                        System.out.println("enter a new password");
                        Scanner newPasswordScan = new Scanner(System.in);
                        String newPasswordIn = newPasswordScan.next();
                        this.userPasswords.add(newPasswordIn);
                        BM.createUser(newUsernameIn, newPasswordIn);
                        BM.getUsers().get(BM.getUsers().size()).setBM(BM);
                        System.out.println("your user creation request is being processed \n" +
                                "returning to main menu");
                        menuOperatorSelect();
                    } else {
                        System.out.println("this username is not available \n" +
                                "enter another new username");
                    }
                }
            } else if (optionIn.equals("e")) {
                System.out.println("returning to main menu");
                menuOperatorSelect();
            } else {
                System.out.println("that is not an option \n" +
                        "enter 1 to login to existing user \n" +
                        "enter 2 to request creation of new user \n" +
                        "enter e to exit");
            }
        }
    }

    public void menuU2() {
        // options: 1. view accounts summary, 2. perform transaction, 3. request creation of new account, e. exit
        System.out.println("enter 1 to view your accounts summary \n" +
                "enter 2 to perform a transaction \n" +
                "enter 3 to request creation of new account \n" +
                "enter e to exit");
        Scanner optionScan = new Scanner(System.in);
        while ()
    }

    public static void updateDate(String date, File f) throws IOException {
        FileWriter fw = new FileWriter(f);
        fw.write(date);
        fw.close();
    }

    public static void main(String[] args) {

        try {
            File f = new File("./src/date.txt");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddmmyyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            updateDate(dtf.format(now), f);

        } catch (IOException e) {}

        Model model2 = new Model();
        model2.menuOperatorSelect();

    }
}