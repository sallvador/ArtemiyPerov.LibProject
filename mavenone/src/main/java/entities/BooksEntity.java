package entities;

/**
 * Created by demon on 08.11.2016.
 */
public class BooksEntity {
    private long bookid;
    private String bookname;
    private long istaken;

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
