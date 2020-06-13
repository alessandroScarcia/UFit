package it.sms1920.spqs.ufit.model;

import android.content.Context;

import androidx.room.Room;

import it.sms1920.spqs.ufit.presenter.StatsPresenter;

public class Stats {
    private final StatsPresenter presenter;
    public static localDatabase localDatabase;


    public Stats(StatsPresenter presenter){
        this.presenter = presenter;
    }


    public void createDatabase(Context context){
        localDatabase = Room.databaseBuilder(context, localDatabase.class, "userStats").allowMainThreadQueries().build();

    }



}
