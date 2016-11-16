package classes.managers;

import classes.entities.AuthorsEntity;
import classes.entities.UsersEntity;

/**
 * Created by demon on 16.11.2016.
 */
public abstract class UsersDao extends Dao {
    public abstract void addByObject(UsersEntity user);
    public abstract void removeByObject(UsersEntity user);
    public abstract void mergeByObject(UsersEntity user);
}
