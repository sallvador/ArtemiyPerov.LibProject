package classes.managers;

import classes.entities.HistoryEntity;

/**
 * Created by demon on 05.12.2016.
 */
public abstract class HistoryDao extends Dao{
    public abstract void addByObject(HistoryEntity hUnit);
    public abstract void removeByObject(HistoryEntity hUnit);
    public abstract void mergeByObject(HistoryEntity hUnit);
}
