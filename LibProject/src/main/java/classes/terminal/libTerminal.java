package classes.terminal;

import classes.entities.UsersEntity;
import classes.managers.UsersManager;
import classes.util.Assistant;
import classes.util.HiberSF;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by demon on 21.11.2016.
 */
public class libTerminal {
    static {
        try {
            System.setErr(new PrintStream(new File("log.txt")));
        } catch (FileNotFoundException e) {
            System.out.println("fuck!");
        }
    }
    static long currUser;
    static int currState;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static boolean gotcommand;

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
        System.out.println("Type 'log in' to enter the system, 'sign in' to create a new user or 'exit' to leave the terminal");
        gotcommand = false;
        while (!gotcommand){
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
                gotcommand = true;
            }
            else if (command.contentEquals("sign in")) {

                    signIn();

                gotcommand = true;
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
        System.out.println("Enter your password");
        try{
            password = br.readLine();
        }catch (IOException e){
            System.err.println("Failed to read the password in signing in");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        UsersEntity user = null;
        if (phone == null)
            user = new UsersEntity(name, surname, email, password);
        else
            user = new UsersEntity(name, surname, email, phone, password);
        (new UsersManager()).addByObject(user);
        System.out.println("User created!");
        identify();
        
    }

    private static void logIn() {
        gotcommand = false;
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
                                currUser = searchedUser.getUserid();
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
            testF();
    }

    private static void testF(){
        System.out.println("your next function");
    }

}
