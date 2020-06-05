package it.sms1920.spqs.ufit.model.repository;

import android.content.Context;

import androidx.room.Room;

import it.sms1920.spqs.ufit.presenter.Loading;

public class Repository {
    private Loading loadingPresenter;
    private DatabaseHandler db;

    public Repository(Loading loadingPresenter) {
        this.loadingPresenter = loadingPresenter;
    }

    public void buildDatabase(Context applicationContext) {
        db = Room.databaseBuilder(
                applicationContext, DatabaseHandler.class, "ufit-database"
        ).build();
    }
}
