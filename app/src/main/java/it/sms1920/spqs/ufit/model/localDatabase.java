package it.sms1920.spqs.ufit.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import it.sms1920.spqs.ufit.contract.userStatsDAO;

@Database(entities = {UserStats.class}, version = 1, exportSchema = false)
public abstract class localDatabase extends RoomDatabase {

    public abstract userStatsDAO userStatsDAO();
}
