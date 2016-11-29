package classes.entities;

import classes.util.Assistant;
import classes.util.HiberSF;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.NoSuchElementException;

/**
 * Created by demon on 15.11.2016.
 */
public class AuthorsEntity {
    private static long lastID = 0;
    static {
        try{
            SessionFactory sf = HiberSF.getSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Query query = session.createQuery("select max(id) from AuthorsEntity ");
            lastID = new Long(query.uniqueResult().toString());
            session.getTransaction().commit();
            session.close();
            sf.close();
        }catch (Exception e) {
            System.err.println("Failed to get authors id" + e);
            throw new ExceptionInInitializerError(e);
        }
    }
    private long authorid;
    private String authorname;
    protected AuthorsEntity(){

    }

    public AuthorsEntity(String name){
        if (name == null) throw new NoSuchElementException();
        name = Assistant.deleteNeedlessSpaces(name);
        if (name.length() > Assistant.maxTextLength){
            System.out.println("Name must be not longer than 30 characters");
            return;
        }
        this.authorname = name;
        this.authorid = lastID + 1;
        lastID = this.authorid;
        if (lastID == Long.MAX_VALUE){
            //will be supported later
        }

    }

    public long getAuthorid() {
        return authorid;
    }

    public void setAuthorid(long authorid) {
        this.authorid = authorid;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorsEntity that = (AuthorsEntity) o;

        if (authorid != that.authorid) return false;
        if (authorname != null ? !authorname.equals(that.authorname) : that.authorname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (authorid ^ (authorid >>> 32));
        result = 31 * result + (authorname != null ? authorname.hashCode() : 0);
        return result;
    }
}
