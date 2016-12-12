package classes.util;

/**
 * Created by demon on 12.12.2016.
 */
public class Helper {
    public static void gethelp(int currentState){
        switch (currentState){
            case 1:
            {
                System.out.println("Type 'add a book' to add a new book");
                System.out.println("Type 'view book history' to see, who has taken the book");
                System.out.println("Type 'write off a book' to delete one copy of a book from stock");
                System.out.println("Type 'remove a book' to delete all copies of the book from stock");
                System.out.println("Type 'find a user' to see the information about user");
                System.out.println("Type 'user interface' to log in like a user");
                break;
            }

            case 2:
            {
                System.out.println("Type 'show my pi' to see personal information");
                System.out.println("Type 'change user data' to change your personal information");
                System.out.println("Type 'search a book' to find some book in the library");
                System.out.println("Type 'show taken books' to see, what books you have taken");
                break;
            }

            case 3:
            {
                System.out.println("Type 'name', 'surname', 'email', 'password' or 'phone' to change the exact field");
                break;
            }
        }

    }
}
