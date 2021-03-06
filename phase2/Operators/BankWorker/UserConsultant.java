package phase2.Operators.BankWorker;

import phase2.FundStores.Account;
import phase2.Operators.BankAccountUser.User;
import phase2.Operators.Contract;
import sun.reflect.annotation.ExceptionProxy;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.BufferedWriter;

public class UserConsultant extends CustomerService implements Contract {
    private User personalBankAccount;
    static int numUsersConsulted = 1;
    private String currentUserBeingConsultedType;
    private BankManager BM;
    private ArrayList<String> UserAdviseHistory = new ArrayList<>();

    public UserConsultant(String username, String password){
        super(username, password);
    }

    /**
     * Display the features that this user has.
     * @return String - the features this user has!
     */
    public String viewCapabilities(){
        String s = "";
        s = "As a User Consultant for the Bank, you are able to do the following: \n" +
                "- Advise users on account creation.\n" +
                "- Message Bank Manager about complaints.\n" +
                "- Change your password at any time. \n" +
                "- Create a new account at any time. \n" +
                "- View a summary of a single account. \n" +
                "- View a summary of all your existing accounts";

        return s;
    }


    /**
     * Display the features that this user has.
     * @return String - the features this user has!
     */
    public String viewContract(){
        String s;
        s = "As a User Consultant for the Bank, " +
                "you agree not to engage in fraudulent behavior, " +
                "and agree not to abuse the Bank Manager messaging system. Click back or exit//.";
        return s;
    }

    /**
     * Set the Consultant's bank account.
     * @param personalBankAccount
     */
    public void setPersonalBankAccount(User personalBankAccount) {
        this.personalBankAccount = personalBankAccount;
    }

    /**
     * Add to the record of the account recommendations
     * @param record
     */
    public void addToUserAdviseHistory(String record){
        UserAdviseHistory.add(record);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("phase2/txtfiles/UserAdviceHistory.txt"));
            writer.write(record);
            writer.close();
        } catch (Exception ex) {ex.printStackTrace();}

    }

    /**
     * Set the bank manager
     * @param BM
     */
    public void setBM(BankManager BM) {
        this.BM = BM;
    }

    /**
     * Return the prompt for the user to input.
     * @return the users' options
     */
    public String consultantMessage(){
        return "Answer the questions below and we will determine the best fit for you! \n" +
                "We will automatically create a User account for you if your inputs are valid. \n" +
                "alternatively, click default to be a created as a user.";
    }

    // input prompts, --> create account button
    /**
     * Advise to the user base on their information.
     * @param age
     * @return A string detailing the outcome of the account creation based on user input.
     */
    public String reportUserAccountAdvice(int age, boolean inSchool){
        numUsersConsulted++;
        String s = "";
        String record = "";

        if (age < 16) {
                s = "Sorry, you must be at least 16 years of age to create an account" +
                        " with us! Come back in " + (16 - age) + " years!";
                record = "Number of users consulted: " + numUsersConsulted + "\n Account type recommendation: illegible " +
                        "for account creation.";
            }
            else if (age >= 16 && 25 >= age && inSchool) {
                currentUserBeingConsultedType = "studentPS";
                s = "Based on your information, it looks like a Student Bank Account is the " +
                        "right fit for you! We have sent your information off for approval and \n" +
                        "have created a New Student account for you!";
                record = "Number of users consulted: " + numUsersConsulted + " Account type recommendation: " +
                        currentUserBeingConsultedType;
                        BM.setUserTypeToCreate(currentUserBeingConsultedType);
            }
            else if(age > 60){
                currentUserBeingConsultedType = "retiredPS";
                s = "Based on your information, it looks like a Pension/Retirement Bank Account is the " +
                        "right fit for you! We are transferring you over to the account creation page! You are a" +
                        " member of our \"Points system,\" +\n" +
                        "which you can opt out of at any time.\"" ;
                record = "Number of users consulted: " + numUsersConsulted + " Account type recommendation: " +
                        currentUserBeingConsultedType;
                BM.setUserTypeToCreate(currentUserBeingConsultedType);
            }
            else{
                currentUserBeingConsultedType = "Standard";
                s = "Based on your information, it looks like our Standard Bank Account is the " +
                        "right fit for you! We have sent your information off for approval and \n" +
                        "have created a New Retirement account for you! You are a member of our \"Points\" system," +
                        "which you can opt out of at any time.";
                record = "Number of users consulted: " + numUsersConsulted + " Account type recommendation: " +
                        currentUserBeingConsultedType;
                        BM.setUserTypeToCreate(currentUserBeingConsultedType);
                }
        addToUserAdviseHistory(record);
        return s;
    }

    /**
     * Get the history of actions!
     * @return History of advice given
     */
    public ArrayList<String> getUserAdviseHistory() {
        return UserAdviseHistory;
    }

    /**
     * Contact the Bank Manager!
     *
     */
    public void contactBM(String message, BankManager BM){
        BM.populateInbox(message);
    }
}