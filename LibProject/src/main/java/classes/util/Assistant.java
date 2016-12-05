package classes.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by demon on 28.11.2016.
 */
public class Assistant {
    //For some reason compiler dislikes some symbols in this pattern
    //Pattern emailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    //Light version
    public static Pattern emailPattern = Pattern.compile("^([A-Za-z0-9]{1}[A-Za-z0-9-_.]{0,20}[A-Za-z0-9]{1})@([A-Za-z0-9][a-zA-Z0-9-]*[A-Za-z0-9].)+(ru|com|net|org)$");

    public static Pattern phonePattern = Pattern.compile("^[1-9][0-9]{10}$");

    public static String bookSearchMessage = "Enter search criterion: 'author' to search by author, 'book name' -  by book name, or 'collocation' to search books which name and author are similar to this word combination";

    //Types of search criteria in books searching
    public static final byte AUTHORCRITERION = 1;
    public static final byte BOOKNAMECRITERION = 2;
    public static final byte COLLOCATIONCRITERION = 3;

    public static int maxTextLength = 30;

    public static String deleteNeedlessSpaces(String string){
        return string.trim().replaceAll("[\\s]{2,}", " ");
    }

}
