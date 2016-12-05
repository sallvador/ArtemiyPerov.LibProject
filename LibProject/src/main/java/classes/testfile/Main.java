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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by demon on 15.11.2016.
 */
public class Main {

    public static void main(final String[] args) throws Exception {


        libTerminal.startTerminal();

    }
}