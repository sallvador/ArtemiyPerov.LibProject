package classes.managers;

import classes.entities.AuthorsEntity;

/**
 * Created by demon on 16.11.2016.
 */
public abstract class AuthorsDao extends Dao{
    public abstract void addByObject(AuthorsEntity auth);
    public abstract void removeByObject(AuthorsEntity auth);
    public abstract void mergeByObject(AuthorsEntity auth);
    public abstract void addByName(String name);
}
