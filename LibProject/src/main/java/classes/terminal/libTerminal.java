package classes.terminal;

import classes.entities.AuthorsEntity;
import classes.entities.BooksEntity;
import classes.entities.HistoryEntity;
import classes.entities.UsersEntity;
import classes.managers.BooksManager;
import classes.managers.HistoryManager;
import classes.managers.UsersManager;
import classes.util.Assistant;
import classes.util.HiberSF;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by demon on 21.11.2016.
 */
public class libTerminal {
    static {
        try {
            System.setErr(new PrintStream(new File("terminallog.txt")));
        } catch (FileNotFoundException e) {
            System.out.println("Error in creating log file");
        }
    }
    static private UsersEntity currUser;
    static private int currState;
    static private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void startTerminal()  {
        currState = 1;
        System.out.println("Welcome to the library management terminal");
        try{
            identify();
        } catch (NullPointerException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void identify() {
        boolean gotcommand = false;
        while (!gotcommand){
            System.out.println("Type 'log in' to enter the system, 'sign in' to create a new user or 'exit' to leave the terminal");
            String command = null;
            try {
                command = br.readLine().trim();
            } catch (IOException e){
                System.err.println("Failed to read the command in identifying procedure");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            if (command == null)
                throw new NullPointerException("No command in identifying");
            if (command.contentEquals("log in")) {
                logIn();

            }
            else if (command.contentEquals("sign in")) {

                    signIn();
            }
            else if (command.contentEquals("exit"))
                gotcommand = true;
            else {
                System.out.println("Incorrect command '"+command+"'");
            }
        }
    }

    private static void signIn() {
        System.out.println("Enter your name");
        String name = null;
        String surname = null;
        try {
            name = br.readLine().trim();
        } catch (IOException e){
            System.err.println("Failed to read the name in signing in");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Enter your surname");
        try{
            surname = br.readLine().trim();
        }catch (IOException e){
            System.err.println("Failed to read the surname in signing in");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        String email = null;
        String phone = null;
        String password = null;
        boolean gotemail = false;
        while (!gotemail){
            System.out.println("Enter your email");
            try {
                email = br.readLine().trim();
            }catch (IOException e){
                System.err.println("Failed to read the email in signing in");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            Matcher matcher = Assistant.emailPattern.matcher(email);
            if (!matcher.matches()){
                System.out.println("Invalid email format");
            }

            else {
                SessionFactory sf = HiberSF.getSessionFactory();
                Session session = sf.openSession();
                session.beginTransaction();
                Query query = session.createQuery("from UsersEntity where email = :email").setString("email", email);
                UsersEntity user = (UsersEntity) query.uniqueResult();
                if (user != null)
                    System.out.println("User with such email already exists!");
                else
                    gotemail = true;
                session.close();
                sf.close();
            }
        }

        boolean gotphone = false;
        while (!gotphone){
            System.out.println("Enter your phone number like \"79112586547\" or \"no phone number\" to skip this");
            try {
                phone = br.readLine().trim();
            }catch (IOException e){
                System.err.println("Failed to read the phone in signing in");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            if (phone.contentEquals("no phone number")){
                gotphone = true;
                phone = null;
            }
            else {
                if (!Assistant.phonePattern.matcher(phone).matches()) {
                    System.out.println("Invalid phone format");
                } else {
                    phone = "+" + phone;
                    SessionFactory sf = HiberSF.getSessionFactory();
                    Session session = sf.openSession();
                    session.beginTransaction();
                    Query query = session.createQuery("from UsersEntity where phone = :phone").setString("phone", phone);
                    UsersEntity user = (UsersEntity) query.uniqueResult();
                    if (user != null)
                        System.out.println("User with such phone already exists!");
                    else
                        gotphone = true;
                    session.close();
                    sf.close();
                }
            }
        }
        boolean gotpassword = false;
        while (!gotpassword){
            System.out.println("Enter your password");
            try{
                password = br.readLine();
            }catch (IOException e){
                System.err.println("Failed to read the password in signing in");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            String secondPassword = null;
            try{
                secondPassword = br.readLine();
            }catch (IOException e){
                System.err.println("Failed to read the password again in signing in");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            if (password.contentEquals(secondPassword))
                gotpassword = true;
            else
                System.out.println("passwords don't match");
        }

        UsersEntity user = null;
        if (phone == null)
            user = new UsersEntity(name, surname, email, password);
        else
            user = new UsersEntity(name, surname, email, phone, password);
        (new UsersManager()).addByObject(user);
        System.out.println("User created!");
        
    }

    private static void logIn() {
        boolean gotcommand = false;
        int isexit = 0;
        while (!gotcommand){
            System.out.println("Enter your e-mail");
            String email = null;
            try{
                email = br.readLine().trim();
            } catch (IOException e){
                System.err.println("Failed to read email from cmd during logging in");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            if (email.contentEquals("exit") == true){
                gotcommand = true;
                isexit = 1;
                break;
            }
            Matcher matcher = Assistant.emailPattern.matcher(email);
            if (!matcher.matches())
                System.out.println("Invalid email format");
            else {
                UsersEntity searchedUser = (new UsersManager()).GetUserByEmail(email);
                if (searchedUser == null) {
                    System.out.println("Couldn't find user");
                } else {
                    boolean gotpassword = false;
                    while (!gotpassword){
                        System.out.println("Enter your password");
                        String password = null;
                        try {
                            password = br.readLine();
                        } catch (IOException e){
                            System.err.println("Failed to read password from cmd during logging in");
                            System.err.println(e.getMessage());
                            e.printStackTrace();
                        }
                        if (password.contentEquals("exit"))
                        {
                            gotpassword = true;
                            gotcommand = true;
                            isexit = 1;
                            break;
                        }
                        else{
                            if (password.contentEquals(searchedUser.getPassword())){
                                currUser = searchedUser;
                                gotpassword = true;
                                gotcommand = true;
                            }
                            else
                                System.out.println("Wrong password!");
                        }
                    }
                }
            }
        }
        if (isexit == 1)
            identify();
        else
        {
            if (currUser.getCategory().contentEquals("U"))
                userInterface();
            else
                adminInterface();
        }
    }

    private static void adminInterface() {
        //TODO realise this function
        System.out.println("your admin function");
    }

    private static void userInterface() {
        boolean gotcommand = false;
        System.out.println("You are logged in");
        while (!gotcommand){
            System.out.println("Enter command");
            String command = null;
            try {
                command = br.readLine().trim();
            } catch (IOException e){
                System.err.println("Failed to read the command in ui procedure");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            if (command == null)
                throw new NullPointerException("No command in ui");
            if (command.toLowerCase().contentEquals("exit"))
                gotcommand = true;
            else if (command.toLowerCase().contentEquals("change user data"))
                changeUserData();
            else if (command.toLowerCase().contentEquals("search a book"))
                bookAbook();
            else if (command.toLowerCase().contentEquals("show taken books"))
                showTakenBooks();
            else
                System.out.println("Incorrect command");
        }
    }

    private static void showTakenBooks() {
        List<HistoryEntity> history = (new HistoryManager()).searchHistoryByUsersId(currUser.getUserid());
        if (history == null)
            System.out.println("you have no books taken");
        else {
            SessionFactory sf = HiberSF.getSessionFactory();
            Session session = sf.openSession();
            Query bookQuery = session.createQuery("from BooksEntity where bookid = :bookid");
            Query authorQuery = session.createQuery("from AuthorsEntity  where authorid = :authorid");
            for (HistoryEntity hUnit: history){
                BooksEntity book = (BooksEntity) bookQuery.setLong("bookid", hUnit.getBookid()).uniqueResult();
                AuthorsEntity author = (AuthorsEntity) authorQuery.setLong("authorid", book.getAuthorid()).uniqueResult();
                System.out.println("Author: " + author.getAuthorname());
                System.out.println("Book: " + book.getBookname());
                System.out.print("Return to: ");
                if (hUnit.getIsreturned()==1)
                    System.out.println("returned");
                else
                    System.out.println(hUnit.getReturnto());
                System.out.println();
            }
            session.close();
            sf.close();
        }
    }

    private static void bookAbook() {
        //TODO this method
    }

    private static void changeUserData() {
        boolean gotcommand = false;
        while (!gotcommand) {
            System.out.println("Which field do you want to change?");
            String command = null;
            try {
                command = br.readLine().trim();
            } catch (IOException e) {
                System.err.println("Failed to read the command in data changing procedure");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            if (command == null)
                throw new NullPointerException("No command in data changing");
            if (command.toLowerCase().contentEquals("exit"))
                gotcommand = true;
            else if (command.toLowerCase().contentEquals("name"))
                changename();
            else if (command.toLowerCase().contentEquals("surname"))
                changesurname();
            else if (command.toLowerCase().contentEquals("email"))
                changeemail();
            else if (command.toLowerCase().contentEquals("password"))
                changepassword();
            else if (command.toLowerCase().contentEquals("phone"))
                changephone();
            else System.out.println("Incorrect command");
        }
    }

    private static void changephone() {
        boolean gotphone = false;
        while (!gotphone){
            System.out.println("Enter desirable phone (11 digits)");
            String phone = null;
            try {
                phone = br.readLine().trim();
            } catch (IOException e) {
                System.err.println("Failed to read the command in changing phone");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            if (phone == null)
                throw new NullPointerException("No command in phone changing");
            if (phone.toLowerCase().contentEquals("exit"))
                gotphone = true;
                //User wants to change phone
            else{
                Matcher matcher = Assistant.phonePattern.matcher(phone);
                if (matcher.matches() == false){
                    System.out.println("Wrong phone format");
                }
                //phone format is right
                else {
                    UsersEntity existingUser = (new UsersManager()).GetUserByPhone(phone);
                    if (existingUser != null)
                        System.out.println("Phone is not available");
                    else {
                        currUser.setPhone("+" + phone);
                        (new UsersManager()).mergeByObject(currUser);
                        System.out.println("phone changed!");
                        gotphone = true;
                    }
                }
            }
        }
    }

    private static void changepassword() {
        boolean changedPassword = false;
        while (!changedPassword){
            System.out.println("Enter desirable password");
            String password = null;
            try {
                password = br.readLine().trim();
            } catch (IOException e) {
                System.err.println("Failed to read the command in changing password");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            if (password == null)
                throw new NullPointerException("No command in password changing");
            if (password.toLowerCase().contentEquals("exit"))
                changedPassword = true;
            else {
                System.out.println("Enter password again");
                String secondpassword = null;
                try {
                    secondpassword = br.readLine().trim();
                } catch (IOException e) {
                    System.err.println("Failed to read the command in changing password(2)");
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
                if (secondpassword == null)
                    throw new NullPointerException("No command in password changing(2)");
                if (!password.contentEquals(secondpassword))
                    System.out.println("passwords don't match");
                else {
                    currUser.setPassword(password);
                    (new UsersManager()).mergeByObject(currUser);
                    System.out.println("Password changed!");
                    changedPassword = true;
                }
            }
        }
    }

    private static void changeemail() {
        boolean gotemail = false;
        while (!gotemail){
            System.out.println("Enter desirable email");
            String email = null;
            try {
                email = br.readLine().trim();
            } catch (IOException e) {
                System.err.println("Failed to read the command in changing email");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            if (email == null)
                throw new NullPointerException("No command in email changing");
            if (email.toLowerCase().contentEquals("exit"))
                gotemail = true;
            //User wants to change password
            else{
                Matcher matcher = Assistant.emailPattern.matcher(email);
                if (matcher.matches() == false){
                    System.out.println("Wrong email format");
                }
                //email format is right
                else {
                    UsersEntity existingUser = (new UsersManager()).GetUserByEmail(email);
                    if (existingUser != null)
                        System.out.println("Email is not available");
                    else {
                        currUser.setEmail(email);
                        (new UsersManager()).mergeByObject(currUser);
                        System.out.println("email changed!");
                        gotemail = true;
                    }
                }
            }
        }
    }

    private static void changesurname() {
        System.out.println("Enter desirable surname");
        String command = null;
        try {
            command = br.readLine().trim();
        } catch (IOException e) {
            System.err.println("Failed to read the command in changing surname");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        if (command == null)
            throw new NullPointerException("No command in surname changing");
        if (command.toLowerCase().contentEquals("exit"))
            ;
        else {
            currUser.setLastname(command);
            (new UsersManager()).mergeByObject(currUser);
            System.out.println("Name changed!");
        }
    }

    private static void changename() {
        System.out.println("Enter desirable name");
        String command = null;
        try {
            command = br.readLine().trim();
        } catch (IOException e) {
            System.err.println("Failed to read the command in changing name");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        if (command == null)
            throw new NullPointerException("No command in name changing");
        if (command.toLowerCase().contentEquals("exit"))
            ;
        else {
            currUser.setFirstname(command);
            (new UsersManager()).mergeByObject(currUser);
            System.out.println("Name changed!");
        }
    }


}
