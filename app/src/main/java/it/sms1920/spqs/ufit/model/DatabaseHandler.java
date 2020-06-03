package it.sms1920.spqs.ufit.model;

import androidx.room.RoomDatabase;
import androidx.room.Database;

@Database(entities = {Exercise.class}, version = 1)
public abstract class DatabaseHandler extends RoomDatabase {
    public abstract ExerciseDao exerciseDao();
}
