package it.sms1920.spqs.ufit.model.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserStats.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    public abstract userStatsDAO userStatsDAO();
}
