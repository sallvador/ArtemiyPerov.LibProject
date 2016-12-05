package classes.entities;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by demon on 15.11.2016.
 */
public class HistoryEntity {

    private long eventid;
    private Date datetaken;
    private Date returnto;
    private long isreturned;
    private long Bookid;
    private long Userid;

    public HistoryEntity(){

    }

    public HistoryEntity(long bookid, long userid){
        this.Bookid = bookid;
        this.Userid = userid;
        this.isreturned = 0;
        Calendar calendar = Calendar.getInstance();
        int Date = calendar.get(Calendar.DAY_OF_MONTH);
        int Month = calendar.get(Calendar.MONTH) + 1;
        int Year = calendar.get(Calendar.YEAR)-1900;
        this.datetaken = new Date(Year, Month, Date);
        this.returnto = new Date(Year, Month, Date + 15);
    }

    public long getEventid() {
        return eventid;
    }

    public void setEventid(long eventid) {
        this.eventid = eventid;
    }

    public Date getDatetaken() {
        return datetaken;
    }

    public void setDatetaken(Date datetaken) {
        this.datetaken = datetaken;
    }

    public Date getReturnto() {
        return returnto;
    }

    public void setReturnto(Date returnto) {
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
