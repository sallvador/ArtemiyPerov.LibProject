package classes.entities;

import classes.util.HiberSF;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Time;

/**
 * Created by demon on 15.11.2016.
 */
public class HistoryEntity {
    private static long lastEventID;
    static {
        try{
            SessionFactory sf = HiberSF.getSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Query query = session.createQuery("select max(id) from HistoryEntity");
            lastEventID = new Long(query.uniqueResult().toString());
            session.getTransaction().commit();
            session.close();
            sf.close();
        }catch (Exception e) {
            System.err.println("Failed to get books id" + e);
            throw new ExceptionInInitializerError(e);
        }
    }
    private long eventid;
    private Time datetaken;
    private Time returnto;
    private long isreturned;
    private long Bookid;
    private long Userid;

    public long getEventid() {
        return eventid;
    }

    public void setEventid(long eventid) {
        this.eventid = eventid;
    }

    public Time getDatetaken() {
        return datetaken;
    }

    public void setDatetaken(Time datetaken) {
        this.datetaken = datetaken;
    }

    public Time getReturnto() {
        return returnto;
    }

    public void setReturnto(Time returnto) {
        this.returnto = returnto;
    }

    public long getIsreturned() {
        return isreturned;
    }

    public void setIsreturned(long isreturned) {
        this.isreturned = isreturned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoryEntity that = (HistoryEntity) o;

        if (eventid != that.eventid) return false;
        if (isreturned != that.isreturned) return false;
        if (datetaken != null ? !datetaken.equals(that.datetaken) : that.datetaken != null) return false;
        if (returnto != null ? !returnto.equals(that.returnto) : that.returnto != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (eventid ^ (eventid >>> 32));
        result = 31 * result + (datetaken != null ? datetaken.hashCode() : 0);
        result = 31 * result + (returnto != null ? returnto.hashCode() : 0);
        result = 31 * result + (int) (isreturned ^ (isreturned >>> 32));
        return result;
    }

    public long getBookid() {
        return Bookid;
    }

    public void setBookid(long Bookid) {
        this.Bookid = Bookid;
    }

    public long getUserid() {
        return Userid;
    }

    public void setUserid(long Userid) {
        this.Userid = Userid;
    }
}
