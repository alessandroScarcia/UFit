package it.sms1920.spqs.ufit.model.firebase.database;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDbSingleton {
    private static FirebaseDatabase instance;

    private FirebaseDbSingleton() {
    }

    public static FirebaseDatabase getInstance() {
        if (instance == null) {
            instance = FirebaseDatabase.getInstance();
            instance.setPersistenceEnabled(true);
        }
        return instance;
    }
}
