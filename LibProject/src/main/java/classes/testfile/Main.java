package classes.testfile;

import classes.entities.AuthorsEntity;
import classes.entities.UsersEntity;
import org.hibernate.Session;
import classes.util.HiberSF;
import org.hibernate.cfg.Configuration;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.exit;

/**
 * Created by demon on 15.11.2016.
 */
public class Main {

    public static void main(final String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.addClass(classes.entities.UsersEntity.class);
        configuration.addResource("UsersEntity.hbm.xml");
        System.out.println("testing hiber");
        Locale.setDefault(Locale.ENGLISH);
        Session sess = HiberSF.getSessionFactory().openSession();
        /*UsersEntity user = (new UsersManager()).searchByEmail((new BufferedReader(new InputStreamReader(System.in))).readLine());
        if (user == null)
            System.out.println("Fuck");*/
        /*List<UsersEntity> result = sess.createQuery("from UsersEntity").list();
        for (UsersEntity tb : result){
            System.out.println(tb.getFirstname());
        }*/

        UsersEntity ue = new UsersEntity("abcd", "daasda", "abcd@gmail.com");
        sess.save(ue);
        sess.getTransaction().commit();

        sess.close();
        HiberSF.killSF();
        /*
        String email = (new BufferedReader(new InputStreamReader(System.in))).readLine();
        Pattern pattern = Pattern.compile("^([A-Za-z0-9]{1}[A-Za-z0-9-_.]{0,20}[A-Za-z0-9]{1})@([A-Za-z0-9][a-zA-Z0-9-]*[A-Za-z0-9].)+(ru|com|net|org)$");
        Matcher matcher = pattern.matcher(email);
        System.out.println(matcher.matches());*/
        //libTerminal.startTerminal();
    }
}