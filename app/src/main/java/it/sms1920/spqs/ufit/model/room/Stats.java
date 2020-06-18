package it.sms1920.spqs.ufit.model.room;

import android.content.Context;

import androidx.room.Room;

import it.sms1920.spqs.ufit.launcher.userstats.StatsPresenter;

// TODO clear LocalDatabase usage c:
public class Stats {
    private final StatsPresenter presenter;
    public static LocalDatabase localDatabase;


    public Stats(StatsPresenter presenter){
        this.presenter = presenter;
    }


    public void createDatabase(Context context){
        localDatabase = Room.databaseBuilder(context, LocalDatabase.class, "userStats").allowMainThreadQueries().build();

    }


}
