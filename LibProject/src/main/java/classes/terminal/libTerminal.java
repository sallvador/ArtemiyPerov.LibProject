package classes.terminal;

import classes.entities.AuthorsEntity;
import classes.entities.BooksEntity;
import classes.entities.HistoryEntity;
import classes.entities.UsersEntity;
import classes.managers.AuthorsManager;
import classes.managers.BooksManager;
import classes.managers.HistoryManager;
import classes.managers.UsersManager;
import classes.util.Assistant;
import classes.util.Helper;
import classes.util.HiberSF;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    static private int currState = 0;
    static private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


    public static void startTerminal()  {
        currState = 1;
        System.out.println("Welcome to the library management terminal");
        System.out.println("Type '?' for help or 'exit' to escape the statement");
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
                    String sphone = "+" + phone;
                    SessionFactory sf = HiberSF.getSessionFactory();
                    Session session = sf.openSession();
                    session.beginTransaction();
                    Query query = session.createQuery("from UsersEntity where phone = :phone").setString("phone", sphone);
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
            System.out.println("Enter password again");
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
        boolean gotcommand = false;
        System.out.println("You logged in like administrator");
        while(!gotcommand){
            currState = 1;
            System.out.println("Enter command");
            String command = null;
            try {
                command = br.readLine().trim();
            } catch (IOException e){
                System.err.println("Failed to read the command in ai procedure");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            if (command.toLowerCase().contentEquals("exit"))
                gotcommand = true;
            else if(command.contentEquals("?"))
                Helper.gethelp(currState);
            else if (command.toLowerCase().contentEquals("add a book"))
                 addBook();
            else if (command.toLowerCase().contentEquals("view book history"))
                viewBookHistory();
            else if (command.toLowerCase().contentEquals("write off a book"))
                 writeOff();
            else if (command.toLowerCase().contentEquals("remove a book"))
                removeBook();
            else if (command.toLowerCase().contentEquals("find a user"))
                findUser();
            else if (command.toLowerCase().contentEquals("user interface"))
                userInterface();
            else
                System.out.println("Incorrect command");
        }
    }

    private static void findUser(){
        System.out.println("Enter user id");
        String userID = null;
        try {
            userID = br.readLine().trim();
        } catch (IOException e){
            System.err.println("Failed to read the user id in searching user");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        if (userID.toLowerCase().contentEquals("exit"))
            return;
        Long num = null;
        try {
            num = new Long(userID);
        } catch (NumberFormatException e){
            System.out.println("Incorrect user id");
            return;
        }
        UsersEntity user = (new UsersManager()).getUserByID(num);
        if (user == null){
            System.out.println("User not found!");
            return;
        }
        List<HistoryEntity> takenBooks = (new HistoryManager()).searchBooksTakenByUser(user.getUserid());
        if (takenBooks.isEmpty()){
            System.out.println("User "+user.getFirstname()+' '+user.getLastname()+" has no borrowed books");
            return;
        }
        Map<Integer, HistoryEntity> takenBooksMap = new HashMap<Integer, HistoryEntity>();
        Integer rownum = 0;
        for (HistoryEntity hUnit: takenBooks){
            rownum++;
            takenBooksMap.put(rownum, hUnit);
        }
        System.out.println("User: "+user.getFirstname()+" "+user.getLastname());
        System.out.println("Email: "+user.getEmail());
        System.out.print("Phone: ");
        if (user.getPhone() == null)
            System.out.println("No phone");
        else
            System.out.println(user.getPhone());
        printBorrowedBooks(takenBooksMap);
        System.out.println("Do you want to return some of these books?");
        boolean gotanswer = false;
        String answer = null;
        while (!gotanswer){
            try {
                answer = br.readLine().trim();
            } catch (IOException e){
                System.err.println("Failed to read answer in book returning");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            if ((answer.toLowerCase().contentEquals("no"))||(answer.toLowerCase().contentEquals("exit")))
                return;
            else if (answer.toLowerCase().contentEquals("yes")){
                gotanswer = true;
            }
            else
                System.out.println("Incorrect command");
        }
        if (!gotanswer)
            return;
        System.out.println("Enter the number of the book in showed list");
        String bookNum = null;
        try {
            bookNum = br.readLine().trim();
        } catch (IOException e){
            System.err.println("Failed to read the book number in returning book");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        if (userID == null)
            throw new NullPointerException("No book number in returning book");
        if (userID.toLowerCase().contentEquals("exit"))
            return;
        Integer number = null;
        try {
            number = new Integer(bookNum);
        } catch (NumberFormatException e){
            System.out.println("Incorrect hUnit number");
            return;
        }
        if (!takenBooksMap.containsKey(number)){
            System.out.println("No such record in the list");
        }
        else {
            HistoryEntity record = takenBooksMap.get(number);
            record.setIsreturned(1);
            (new HistoryManager()).mergeByObject(record);
            System.out.println("Book has been returned!");
        }
    }

    private static void printBorrowedBooks(Map<Integer, HistoryEntity> takenBooksMap) {
        System.out.println("Borrowed books:");
        SessionFactory sf = HiberSF.getSessionFactory();
        Session session = sf.openSession();
        Query bookQuery = session.createQuery("from BooksEntity where bookid = :bookID");
        Query authorQuery = session.createQuery("from AuthorsEntity where authorid = :authorid");
        int size = takenBooksMap.size();
        for (int i = 1; i <= size; i++){
            System.out.println("Number: " + i);
            HistoryEntity hUnit = takenBooksMap.get(i);
            BooksEntity book = (BooksEntity) bookQuery.setLong("bookID", hUnit.getBookid()).uniqueResult();
            AuthorsEntity author = (AuthorsEntity) authorQuery.setLong("authorid", book.getAuthorid()).uniqueResult();
            System.out.println("Book \""+book.getBookname()+"\" by "+author.getAuthorname());
            System.out.println("Was taken: "+hUnit.getDatetaken());

        }
        session.close();
        sf.close();
    }

    private static void viewBookHistory(){
        System.out.println("Enter books's author exactly");
        String authorName = null;
        try {
            authorName = br.readLine().trim();
        } catch (IOException e){
            System.err.println("Failed to read the author in book history viewing");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        if (authorName == null)
            throw new NullPointerException("No author in book history viewing");
        if (authorName.toLowerCase().contentEquals("exit"))
            return;
        AuthorsEntity author = (new AuthorsManager()).GetAuthorByExactName(authorName);
        if (author == null){
            System.out.println("Author not found");
            return;
        }
        System.out.println("Enter exact book name");
        String bookName = null;
        try {
            bookName = br.readLine().trim();
        } catch (IOException e){
            System.err.println("Failed to read the book name in book history viewing");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        if (bookName == null)
            throw new NullPointerException("No book name in book history viewing");
        BooksEntity book = (new BooksManager()).getBookByExactNameAndAuthorId(bookName, author.getAuthorid());
        if (book == null){
            System.out.println("Book not found");
            return;
        }
        SessionFactory sf = HiberSF.getSessionFactory();
        Session session = sf.openSession();
        Query historyQuery = session.createQuery("from HistoryEntity where bookid = :bookid order by datetaken").setLong("bookid", book.getBookid());
        List<HistoryEntity> bookHistory = historyQuery.list();
        Query userQuery = session.createQuery("from UsersEntity where userid = :userid");
        for (HistoryEntity hUnit: bookHistory){
            UsersEntity user = (UsersEntity) userQuery.setLong("userid", hUnit.getUserid()).uniqueResult();
            System.out.println("User: "+user.getFirstname()+' '+user.getLastname());
            System.out.println("Was taken on: " + hUnit.getDatetaken());
            System.out.print("Was returned: ");
            if (hUnit.getIsreturned() == 0)
                System.out.println("No");
            else
                System.out.println("Yes");
        }
        session.close();
        sf.close();
    }

    private static void removeBook(){
        System.out.println("Enter books's author exactly");
        String authorName = null;
        try {
            authorName = br.readLine().trim();
        } catch (IOException e){
            System.err.println("Failed to read the author in book removing");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        if (authorName == null)
            throw new NullPointerException("No author in book removing");
        if (authorName.toLowerCase().contentEquals("exit"))
            return;
        AuthorsEntity author = (new AuthorsManager()).GetAuthorByExactName(authorName);
        if (author == null){
            System.out.println("Author not found");
            return;
        }
        System.out.println("Enter exact book name");
        String bookName = null;
        try {
            bookName = br.readLine().trim();
        } catch (IOException e){
            System.err.println("Failed to read the book name in book removing");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        if (bookName == null)
            throw new NullPointerException("No book name in book removing");
        BooksEntity book = (new BooksManager()).getBookByExactNameAndAuthorId(bookName, author.getAuthorid());
        if (book == null){
            System.out.println("Book not found");
            return;
        }
        (new BooksManager()).removeByObject(book);
        System.out.println("Book was removed from stock!");
    }

    private static void addBook(){
        System.out.println("Enter books's author exactly");
        String authorName = null;
        try {
            authorName = br.readLine().trim();
        } catch (IOException e){
            System.err.println("Failed to read the author in book adding");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        if (authorName == null)
            throw new NullPointerException("No author in book addition");
        if (authorName.toLowerCase().contentEquals("exit"))
            return;
        AuthorsEntity author = (new AuthorsManager()).GetAuthorByExactName(authorName);
        if (author == null){
            System.out.println("Author not found. Create?");
            boolean gotanswer = false;
            String answer = null;
            while (!gotanswer) {
                try {
                    answer = br.readLine().trim();
                } catch (IOException e) {
                    System.err.println("Failed to read the answer in author addition");
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
                if (answer == null)
                    throw new NullPointerException("No answer in author addition");
                if ((answer.toLowerCase().contentEquals("exit")) || (answer.toLowerCase().contentEquals("no")))
                    return;
                else if (answer.toLowerCase().contentEquals("yes")) {
                    (new AuthorsManager()).addByName(authorName);
                    author = (new AuthorsManager()).GetAuthorByExactName(authorName);
                    gotanswer = true;
                }
                else
                    System.out.println("Incorrect command");
            }
            if (!gotanswer)
                return;
        }
        System.out.println("Enter exact book name");
        String bookName = null;
        try {
            bookName = br.readLine().trim();
        } catch (IOException e){
            System.err.println("Failed to read the book name in book addition");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        if (bookName == null)
            throw new NullPointerException("No book name in book addition");
        BooksEntity book = (new BooksManager()).getBookByExactNameAndAuthorId(bookName, author.getAuthorid());
        if (book == null){
            book = new BooksEntity(bookName, author.getAuthorname());
            (new BooksManager()).addByObject(book);
            System.out.println("New book added!");
        }
        else {
            book.setBalance(book.getBalance()+1);
            (new BooksManager()).mergeByObject(book);
            System.out.println("Number of such books in stock: " + book.getBalance());
        }
    }

    private static void writeOff() {
        System.out.println("Enter exact book id");
        String bookID = null;
        try {
            bookID = br.readLine().trim();
        } catch (IOException e){
            System.err.println("Failed to read the book id in writing off");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        if (bookID == null)
            throw new NullPointerException("No book id in writing off");
        if (bookID.toLowerCase().contentEquals("exit"))
            return;
        Long num = null;
        try {
            num = new Long(bookID);
        } catch (NumberFormatException e){
            System.out.println("Incorrect book id");
            return;
        }
        BooksEntity book = (new BooksManager()).getBookById(num);
        if (book == null){
            System.out.println("Book not found");
            return;
        }
        if (book.getBalance() == 0){
            System.out.println("No such books in the stock");
            return;
        }
        AuthorsEntity author = (new AuthorsManager()).GetAuthorById(book.getAuthorid());
        System.out.println("Type 'yes' if you want to write off one book'"+book.getBookname()+"' by "+author.getAuthorname());
        String answer = null;
        try {
            answer = br.readLine().trim();
        } catch (IOException e){
            System.err.println("Failed to read the answer in writing off");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        if (answer.toLowerCase().contentEquals("yes")){
            book.setBalance(book.getBalance()-1);
            (new BooksManager()).mergeByObject(book);
            System.out.println("Book has been written off");
        }
    }

    private static void userInterface() {
        boolean gotcommand = false;
        System.out.println("You are logged in");
        while (!gotcommand){
            currState = 2;
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
            else if(command.contentEquals("?"))
                Helper.gethelp(currState);
            else if (command.toLowerCase().contentEquals("show my pi"))
                showPI();
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

    private static void showPI() {
        System.out.println("Your personal information:");
        System.out.println("Name: " + currUser.getFirstname());
        System.out.println("Surname: " + currUser.getLastname());
        System.out.println("ID: " + currUser.getUserid());
        System.out.println("Email: " + currUser.getEmail());
        System.out.println("Registration date: " + currUser.getRegdate());
        if (currUser.getPhone() != null)
            System.out.println("Phone: " + currUser.getPhone());
    }

    private static void showTakenBooks() {
        List<HistoryEntity> history = (new HistoryManager()).searchBooksTakenByUser(currUser.getUserid());
        if (history.isEmpty())
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
        byte criteriontype = 0;
        boolean gotcriterion = false;
        while(!gotcriterion){
            System.out.println(Assistant.bookSearchMessage);
            String criterion = null;
            try {
                criterion = br.readLine().trim();
            } catch (IOException e) {
                System.err.println("Failed to read the command in book search");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            if (criterion == null)
                throw new NullPointerException("No command in book searching");
            if (criterion.toLowerCase().contentEquals("exit"))
                gotcriterion = true;
            else if (criterion.toLowerCase().contentEquals("author")) {
                criteriontype = Assistant.AUTHORCRITERION;
                gotcriterion = true;
            }
            else if (criterion.toLowerCase().contentEquals("book name")) {
                criteriontype = Assistant.BOOKNAMECRITERION;
                gotcriterion = true;
            }
            else if (criterion.toLowerCase().contentEquals("collocation")) {
                criteriontype = Assistant.COLLOCATIONCRITERION;
                gotcriterion = true;
            }
            else
                System.out.println("Incorrect criterion");
        }
        //user entered criterion type
        if (criteriontype == 0){
            return;
        }
        System.out.println("Enter the line");
        String line = null;
        try {
            line = br.readLine().trim();
        } catch (IOException e){
            System.err.println("Failed to read the line for book search");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        if (line == null)
            throw new NullPointerException("No line in book searching");
        if (line.toLowerCase().contentEquals("exit"))
            return;
        List<BooksEntity> books = getBooksByLineAndCrType(line, criteriontype);
        if (books.isEmpty()) {
            System.out.println("No books found");
            return;
        }
        Map<Integer,BooksEntity> booksmap = new HashMap<Integer, BooksEntity>();
        Integer rownum = 0;
        for (BooksEntity book: books){
            rownum++;
            booksmap.put(rownum, book);
        }
        printSearchedBooks(booksmap);
        if (!bookability(currUser)){
            System.out.println("You are unable to take more books");
            return;
        }
        boolean posanswer = false;
        String answer = null;
        while (!posanswer) {
            System.out.println("Do you want to book some book? (Yes/No)");
            try {
                answer = br.readLine().trim();
            } catch (IOException e) {
                System.err.println("Failed to read the answer in book search");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            if (answer == null)
                throw new NullPointerException("No answer in book searching");
            if (answer.toLowerCase().contentEquals("no"))
                return;
            else if (answer.toLowerCase().contentEquals("yes"))
                posanswer = true;
            else
                System.out.println("Incorrect command");
        }
        if (posanswer)
            bookbooking(booksmap);
    }

    private static boolean bookability(UsersEntity user){
        SessionFactory sf = HiberSF.getSessionFactory();
        Session session = sf.openSession();
        Query query = session.createQuery("select count(*) from HistoryEntity where userid = :userid and isreturned = 0").setLong("userid", user.getUserid());
        Long takenbooksnum = (Long) query.uniqueResult();
        session.close();
        sf.close();
        if (takenbooksnum < 3)
            return true;
        else
            return false;
    }

    private static void bookbooking(Map<Integer,BooksEntity> booksmap){
        SessionFactory sf = HiberSF.getSessionFactory();
        Session session = sf.openSession();
        boolean finishedBooking = false;
        while (!finishedBooking){
            System.out.println("Type number of book that you want to book");
            String answer = null;
            try {
                answer = br.readLine().trim();
            } catch (IOException e) {
                System.err.println("Failed to read book number in book booking");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            if (answer.toLowerCase().contentEquals("exit"))
                return;
            Integer num = null;
            boolean correctString = true;
            try{
                num = new Integer(answer);
            } catch (NumberFormatException e){
                System.out.println("Incorrect string");
                correctString = false;
            }
            //User entered a number
            if (correctString) {
                if (!booksmap.containsKey(num)) {
                    System.out.println("Book is not in the list");
                }
                //Book is in the list, but may be unavailable
                else{
                    BooksEntity book = booksmap.get(num);
                    if(book.getBalance() == 0)
                        System.out.println("Book is unavailable now");
                    //Book can be borrowed
                    else{
                        HistoryEntity newHUnit = new HistoryEntity(book.getBookid(), currUser.getUserid());
                        (new HistoryManager()).addByObject(newHUnit);
                        book.setBalance(book.getBalance()-1);
                        (new BooksManager()).mergeByObject(book);
                        System.out.println("Book was booked");
                        finishedBooking = true;
                    }
                }
            }
        }
    }

    private static void printSearchedBooks(Map<Integer,BooksEntity> booksmap){
        SessionFactory sf = HiberSF.getSessionFactory();
        Session session = sf.openSession();
        Query query = session.createQuery("from AuthorsEntity where authorid = :authorid");
        int size = booksmap.size();
        for (int i = 1; i <= size; i++){
            System.out.println("Number: " + i);
            BooksEntity book = booksmap.get(i);
            System.out.println("Book name: " + book.getBookname());
            AuthorsEntity author = (AuthorsEntity) query.setLong("authorid", book.getAuthorid()).uniqueResult();
            System.out.println("Author: " + author.getAuthorname());
            System.out.print("Available: ");
            if (book.getBalance() == 0)
                System.out.println("No");
            else
                System.out.println("Yes");
            System.out.println();
        }
        session.close();
        sf.close();
    }

    private static List<BooksEntity> getBooksByLineAndCrType(String line, byte criteriontype) {
        List<BooksEntity> books = null;
        switch (criteriontype){
            case Assistant.AUTHORCRITERION:
                books = (new BooksManager()).getBooksByAuthor(line);
                break;
            case Assistant.BOOKNAMECRITERION:
                books = (new BooksManager()).getBooksByName(line);
                break;
            default:
                books = (new BooksManager()).getBooksByPattern(line);
                break;
        }
        return books;
    }

    private static void changeUserData() {
        boolean gotcommand = false;
        while (!gotcommand) {
            currState = 3;
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
            else if(command.contentEquals("?"))
                Helper.gethelp(currState);
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
