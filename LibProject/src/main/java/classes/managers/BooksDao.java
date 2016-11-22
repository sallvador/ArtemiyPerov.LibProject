package classes.managers;

import classes.entities.BooksEntity;

/**
 * Created by demon on 16.11.2016.
 */
public abstract class BooksDao extends Dao {
    public abstract void addByObject(BooksEntity book);
    public abstract void removeByObject(BooksEntity book);
    public abstract void mergeByObject(BooksEntity book);
}
