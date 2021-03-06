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
Begin by copying the master repository link above
Open up an existing or new intellij project
To open up the folder, click file → New → project from version control → Git → paste the repo link and open it up
Mark the file named group_0357 as sources root

2. Running the program

Ensure that BankManager.txt is empty
To  open up the class that contains the main method, find the UserInterface package and the file GUI in it.
Run the class named GUI

3. Interacting with the system
General note:
Click the on a button to perform an action. If a confirmation does not print to screen, you most likely have to exit or go back! (happens when creating user or bank account and user account)
If the bank manager is logged into their account, they can perform actions related to the existing users.
Everything to with the physical amounts that the ATM holds is dealt with "in real life" and thus does not require  login authentication (ATMMaintainer does).
All interactors of the ATM can view their capabilities! Click on (view capabilities) when the button appears.

Options:
Logging in
Bank Manager
In order to gain access to this account successfully, login with the bank manager credentials. See A.1. below.
You may click view capabilities to see what the bank manager’s roles are.

A.1
The login username for bank manager is “BMuser”; password is “BMpass”
To access a user, simply type in the user’s username, and the access key
The access key is “900” for all fields requiring an access key inside a bank manager interface


User Login
As the real life interactor with the program, and assuming that you are not the Manager of the bank,
you are given the option to login to an existing account or create a new user. See B.1 below.
You may click view capabilities to see what a user can do

B.1
Once in your account, you can view your accounts summary, request account creation or perform a transaction where
 you will be given a list of options to enact on different accounts.


Consultant Login
Type in the consultant login credentials to login as a consultant. See C.1. below.
You may click view capabilities to see what a consultant’s roles are.

C.1
The login username for a consultant is “UCuser”; password is “UCpass”


From this point on, at every stage, the real life user of the ATM can choose to “exit” which would take them back
to the login screens.

When necessary, the user will also have the option to go back!

4. Extra features:
GUI
New types of accounts (Student, gold member, pension/retirement, prepaid)
New kind of users - point system Users (student, pension, standard), goldmembers
Opt in and out of point system
Bank worker (fulfilled phase 2 specs) but also uses algorithm to advise what kind of account a user should get
Messaging system between bank manager and consultant
View signed contract and view capabilities
View summary of balance(view single vs view all)
Delete bank account + user account

Design justifications
Design patterns
Iterator pattern
User implements iterable which enables the program to iterate over a collection of users and call users’ methods respectively. A collection of users is stored in an arraylist present in the bank manager
Accounts implements iterable which allows callers of a collection of accounts to iterate over a list of accounts. All users have a list of accounts.
Observer pattern
Implemented in the User Consultant class for when a Point System user decides to cash their points
Factory pattern
BankAccount Factory - Determines what account to create
BankUserFactory - Determines the type of user to create

5. Encapsulation
We defined an operator as someone who can interact with the ATM machine through an account
Encapsulation was one of the our main priorities. On a project structure level, we divided up our classes into packages that deal with certain functionalities. This helps with extending our project as someone working on our project could find classes and methods from a small group of classes that are related to what is being searched for
The transaction class in the FundTransfers package hides the way in which transactions are made from the account class.
The create new account method hides the way in which the user adds to its list of accounts because the bank manager gets and sets users
MVC
We loosely had the MVC pattern in mind due to the the fact that our program displays information to the user.
Classes such as user and bank manager have a mix of View and Controller
The class “Model” is mainly where we bridge the controller and view of our project, however, it has a bit of “view” implemented into it as it prints options to the screen for the user to choose from
Open/Closed principle
By trying to make our project extensible for A2, we found that we designed in a way to make the program open for addition of new features/ logic. As an example, we have a bank worker subclass that enables us to make new forms of accounts. With considerations to our project being closed for modification, our program mostly contains low accessibility modifier instance variables. For example, our in the ATM class holds mostly private instance variables like “numXdollarBills”. The bank manager must alter these variables but it does so by utilizing getters and setters.

Dependency Inversion principle ** notes must be made
Because many of our classes relate to other classes in some way -especially high level classes interacting with low level classes -, and following from the Open/Closed principle, we have designed to make use of the dependency inversion principle. For example, our transactions class simply holds an instance variable of a type of account and simply calls the account’s methods via that instance. E.g. account.getBalance()

Single Responsibility Principle
Our classes were designed and implemented in a way that deals with one main idea
Our transaction class deals deals with performing a transaction
Our ATM class can only perform things that alter the amount of bills that it has
Our accounts class holds, gets and sets information to do with its variables
The user class is everything to do with the user’s bank account

Project extensibility
Bank worker superclass - with phase 2 in mind, we created this super class in order for other forms of workers to be made easily.
In bank manager, we defined an arraylist of bank managers to support the possibility of there being different bank managers

