package it.sms1920.spqs.ufit.model;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDbSingleton {
    private static FirebaseDatabase database;

    public static FirebaseDatabase getDatabase(){
        if(database==null) {
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }
        return database;
    }
}
