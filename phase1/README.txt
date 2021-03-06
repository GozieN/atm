Readme

Structure of file:
(To access one of these options quickly, Ctrl + f to highlight its potion in the document quickly!)
1. Cloning the repo
2. Running the program
3. Interacting with the ATM system
4. Design justifications
5. Extra features

Master repository link: https://markus.teach.cs.toronto.edu/git/csc207-2019-01/group_0357 
1. Cloning the repository
    - Copy the master repository link above
    - Open up an existing or new intellij project
    - To open up the folder, click file → New → project from version control → Git → paste the repo link and open it up
    - Mark the file named group_0357 as sources root

2. Running the program
    To  open up the class that contains the main method, click group_0357 → phase1 → Model
    Run the class named Model

3. Interacting with the ATM system
    General note
    Type in a valid key from the options listed. This allow to explore the ATM.
    If the bank manager is logged into their account, they can perform actions related to the existing users.
    Everything to with the physical amounts that the ATM holds is dealt with "in real life" and thus does not require
    login authentication.


    Options provided to interact with the ATM system:

    Logging in or creating an account
    Bank Manager
        We decided to implement our program in such a way that requires the Bank Manager to have a form of
        identification to access the ATM. This is for the purpose of keeping access to funds in the ATM secure.
        Once the Bank Manager has been granted access. So in order to gain access to this account successfully,
        type in “BMuser” for the username and “BMpass” for the password. See A.1. below.

    User Login
        As the real life operator of  the program, and assuming that you are not the Manager of the bank,
        you are given the option to login to an existing account. See B.1 below.

    Create a new account
        Note that if you create a new account, you will be sent back to the main menu point “A”
        in order for user verification to take place through the bank manager. After this occurs,
        refer to point b above.


    From this point on, at every stage, the real life user of the ATM can choose to “exit” which would them
    back to the main menu, i.e. the login screens at the top of the "Options heading" above.

    When necessary, the user will also have the option to go back, which will take them to last page!

    A.1
    The bank manager can gain access to a user’s account. In order to do this, they need to input the user’s
    username as well as the bank manager access key. (The key is “900”). From here the BM can view info of an account,
    or perform a transaction through a user → can choose the kind of transaction (except for withdraw because we
    restricted this transaction to the BM.


    B.1
    Once in your account, you can view your accounts summary, request account creation or perform a transaction
    where you will be given a list of options to enact on different accounts.


4. Design justifications:

    Encapsulation
    We defined an operator as someone who can interact with the ATM machine through an account
    Encapsulation was one of the our main priorities. On a project structure level, we divided
    up our classes into packages that deal with certain functionalities. This helps with extending
    our project as someone working on our project could find classes and methods from a small group
    of classes that are related to what is being searched for.
    The transaction class in the FundTransfers package hides the way in which transactions are
    made from the account class. The create new account method hides the way in which the user adds to its
    list of accounts because the bank manager gets and sets users

    MVC
    We loosely had the MVC pattern in mind due to the the fact that our program displays information to the user.
    Classes such as user and bank manager have a mix of View and Controller. The class “Model” is mainly where we
    bridge the controller and view of our project, however, it has a bit of “view” implemented into it as it prints
    options to the screen for the user to choose from.

    Open/Closed principle
    By trying to make our project extensible for A2, we found that we designed in a way to make the program open for
    addition of new features/ logic. As an example, we have a bank worker subclass that enables us to make new forms
    of accounts. With considerations to our project being closed for modification, our program mostly contains low
    accessibility modifier instance variables. For example, our in the ATM class holds mostly private instance variables
    like “numXdollarBills”. The bank manager must alter these variables but it does so by utilizing getters and setters.

    Dependency Inversion principle
    Because many of our classes relate to other classes in some way -especially high level classes interacting
    with low level classes -, and following from the Open/Closed principle, we have designed to make use of the
    dependency inversion principle. For example, our transactions class simply holds an instance variable of a type
    of account and simply calls the account’s methods via that instance. E.g. account.getBalance()

    Single Responsibility Principle
    Our classes were designed and implemented in a way that deals with one main idea
    Our transaction class deals deals with performing a transaction
    Our ATM class can only perform things that alter the amount of bills that it has
    Our accounts class holds, gets and sets information to do with its variables
    The user class is everything to do with the user’s bank account

    Project extensibility
    Bank worker superclass - with phase 2 in mind, we created this super class in order for other forms of
    workers to be made easily.In bank manager, we defined an arrayList of bank managers to support the possibility of
    there being different bank managers.


5. Extra features
    - Bank manager is a kind of user.
    - Bank manager can access user accounts via use of their master access key.
    -Users have the option of viewing a summary of a single account as well as all of their accounts.
    -
