package mainlocation;

import org.hibernate.Session;
import util.HiberSF;

import java.util.Locale;

/**
 * Created by demon on 15.11.2016.
 */
public class mainclass {
    public static void main(String[] args) {
        System.out.println("Hello, hiber!");
        Locale.setDefault(Locale.ENGLISH);
        Session sess = HiberSF.getSessionFactory().openSession();
        sess.close();
        System.out.println("Bye, hiber!");
    }
}
