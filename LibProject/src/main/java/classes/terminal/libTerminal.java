package classes.terminal;

import classes.entities.UsersEntity;
import classes.managers.UsersManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by demon on 21.11.2016.
 */
public class libTerminal {

    static long currUser;
    static int currState;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static boolean gotcommand;

    public static void startTerminal() throws IOException {
        currState = 1;
        System.out.println("Welcome to the library management terminal");
        System.out.println("Type 'log in' to enter the system, 'sign in' to create a new user or 'exit' to leave the terminal");
        System.out.println("got it!");
        gotcommand = false;
        while (!gotcommand){
            String command = br.readLine().trim();
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
        System.out.println("Sign in");
    }

    private static void logIn() throws IOException {
        System.out.println("Enter your e-mail");
        gotcommand = false;
        while (!gotcommand){
            String command = br.readLine().trim();
            Pattern pattern = Pattern.compile("^([A-Za-z0-9]{1}[A-Za-z0-9-_.]{0,20}[A-Za-z0-9]{1})@([A-Za-z0-9][a-zA-Z0-9-]*[A-Za-z0-9].)+(ru|com|net|org)$");
            Matcher matcher = pattern.matcher(command);
            if (!matcher.matches())
                System.out.println("Invalid email format");
            UsersEntity searchedUser = (new UsersManager()).searchByEmail(command);
            if (searchedUser == null){
                System.out.println("Couldn't find user");
            }
            else
            {
                currUser = searchedUser.getUserid();
                testF();
            }
        }
    }

    private static void testF(){
        System.out.println("your next function");
    }

}
