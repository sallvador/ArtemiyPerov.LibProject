package classes.entities;

import classes.util.HiberSF;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by demon on 15.11.2016.
 */
public class BooksEntity {

    private long bookid;
    private String bookname;
    private long balance;
    private long authorid;

    protected BooksEntity(){

    }

    public BooksEntity(String bookname, String authorname){
        this.bookname = bookname;
        SessionFactory sf = HiberSF.getSessionFactory();
        Session session = sf.openSession();
        Query query = session.createQuery("from AuthorsEntity where authorname = :authorname").setString("authorname", authorname);
        AuthorsEntity author = (AuthorsEntity) query.uniqueResult();
        session.close();
        sf.close();
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

    public long getBalance() {
        return balance;
    }

    public void setBalance(long istaken) {
        this.balance = istaken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BooksEntity that = (BooksEntity) o;

        if (bookid != that.bookid) return false;
        if (balance != that.balance) return false;
        if (bookname != null ? !bookname.equals(that.bookname) : that.bookname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (bookid ^ (bookid >>> 32));
        result = 31 * result + (bookname != null ? bookname.hashCode() : 0);
        result = 31 * result + (int) (balance ^ (balance >>> 32));
        return result;
    }
}
