package classes.testfile;

import classes.entities.AuthorsEntity;
import classes.entities.BooksEntity;
import classes.entities.HistoryEntity;
import classes.entities.UsersEntity;
import classes.managers.BooksManager;
import classes.managers.HistoryManager;
import classes.managers.UsersManager;
import classes.terminal.libTerminal;
import org.hibernate.Query;
import org.hibernate.Session;
import classes.util.HiberSF;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.awt.print.Book;
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
        /*List<HistoryEntity> result = (new HistoryManager()).searchHistoryByUsersId(7);
        SessionFactory sf = HiberSF.getSessionFactory();
        Session session = sf.openSession();
        Query query = session.createQuery("from BooksEntity where bookid = :bookid");
        for (HistoryEntity hUnit: result){
            query = query.setLong("bookid", hUnit.getBookid());
            BooksEntity book = (BooksEntity) query.uniqueResult();
            System.out.println(book.getBookname());
        }*/

        libTerminal.startTerminal();

    }
}