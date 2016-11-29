package classes.entities;

import classes.util.HiberSF;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by demon on 15.11.2016.
 */
public class BooksEntity {
    private static long lastID;
    static {
        try{
            SessionFactory sf = HiberSF.getSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Query query = session.createQuery("select max(id) from BooksEntity ");
            lastID = new Long(query.uniqueResult().toString());
            session.getTransaction().commit();
            session.close();
            sf.close();
        }catch (Exception e) {
            System.err.println("Failed to get books id" + e);
            throw new ExceptionInInitializerError(e);
        }
    }
    private long bookid;
    private String bookname;
    private long istaken = 0;
    private long authorid;

    protected BooksEntity(){

    }

    public BooksEntity(String bookname, String authorname){
        this.bookid = lastID + 1;
        this.bookname = bookname;
        SessionFactory sf = HiberSF.getSessionFactory();
        Session session = sf.openSession();
        Query query = session.createQuery("from AuthorsEntity where authorname = :authorname").setString("authorname", authorname);
        AuthorsEntity author = (AuthorsEntity) query.uniqueResult();
        this.authorid = author.getAuthorid();
    }

    public long getAuthorid() {
        return authorid;
    }

    public void setAuthorid(long authorid) {
        this.authorid = authorid;
    }

    //public BooksEntity(String name, )

    public long getBookid() {
        return bookid;
    }

    public void setBookid(long bookid) {
        this.bookid = bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public long getIstaken() {
        return istaken;
    }

    public void setIstaken(long istaken) {
        this.istaken = istaken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BooksEntity that = (BooksEntity) o;

        if (bookid != that.bookid) return false;
        if (istaken != that.istaken) return false;
        if (bookname != null ? !bookname.equals(that.bookname) : that.bookname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (bookid ^ (bookid >>> 32));
        result = 31 * result + (bookname != null ? bookname.hashCode() : 0);
        result = 31 * result + (int) (istaken ^ (istaken >>> 32));
        return result;
    }
}
