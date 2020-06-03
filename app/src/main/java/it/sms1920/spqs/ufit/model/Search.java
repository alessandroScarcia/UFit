package it.sms1920.spqs.ufit.model;

import androidx.room.Room;
import it.sms1920.spqs.ufit.presenter.SearchAdapter;

public class Search {

    private final SearchAdapter presenter;
    DatabaseHandler mDB;

    public Search(SearchAdapter presenter) {
        this.presenter = presenter;
        mDB = Room.databaseBuilder(presenter.getContext(), DatabaseHandler.class, "Ufit")
                .allowMainThreadQueries() //remove this before production (using background thread is better)
                .build();
        mDB.exerciseDao().deleteAll();
        mDB.exerciseDao().insertAll(
                new Exercise("Squat", "è solo uno squat", "", "", 0),
                new Exercise("Split Squat", "è uno squat diverso", "", "", 0),
                new Exercise("Crunch", "è un coso per fare addominali", "", "", 0),
                new Exercise("Push Up", "cono cose con le braccia", "", "", 0),
                new Exercise("Bicycle Crunch", "cono cose con le gambe e braccia", "", "", 0),
                new Exercise("Diamond Push Up", "cono cose con le braccia", "", "", 0),
                new Exercise("Pull up", "cono cose con le braccia", "", "", 0));
    }

    public void askForResult(String key) {
        presenter.publishData(mDB.exerciseDao().getExercisesByName(key));
    }

}
