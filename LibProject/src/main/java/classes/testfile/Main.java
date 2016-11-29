package classes.testfile;

import classes.entities.AuthorsEntity;
import classes.entities.BooksEntity;
import classes.entities.UsersEntity;
import classes.managers.BooksManager;
import classes.terminal.libTerminal;
import org.hibernate.Query;
import org.hibernate.Session;
import classes.util.HiberSF;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by demon on 15.11.2016.
 */
public class Main {

    public static void main(final String[] args) throws Exception {
        System.out.println("testing hiber");
        Locale.setDefault(Locale.ENGLISH);
        /*SessionFactory sf = HiberSF.getSessionFactory();
        Session sess = sf.openSession();
        List<UsersEntity> result = sess.createQuery("from UsersEntity").list();
        for (UsersEntity tb : result){
            System.out.println(tb.getFirstname());
        }*/
/*
        UsersEntity newuser = new UsersEntity("Roman", "Orlov", "rorlov@gmail.com");*/
        List<BooksEntity> books = (new BooksManager()).getBooksByPattern("one two author");
        for (BooksEntity book: books){
            System.out.println(book.getBookname());
        }


        //libTerminal.startTerminal();

    }
}