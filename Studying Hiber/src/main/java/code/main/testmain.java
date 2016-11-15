package code.main;

import code.entities.TestBookEntity;
import code.util.HiberSF;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Locale;

import static java.lang.System.exit;

/**
 * Created by demon on 08.11.2016.
 */
public class testmain {
    public static void main(String[] args) {
        System.out.println("testing hiber");
        Locale.setDefault(Locale.ENGLISH);
        Session sess = HiberSF.getSessionFactory().openSession();/*
        sess.beginTransaction();
        TestBookEntity tbe = new code.entities.TestBookEntity();
        tbe.setId(28);
        tbe.setDescription("abcd");
        tbe.setName("defg");
        sess.save(tbe);
        Transaction tr = sess.getTransaction();
        tr.commit();*/
        sess.close();
        exit(-1);
    }
}
