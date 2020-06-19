package it.sms1920.spqs.ufit.model.firebase.database;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDbSingleton {
    private static FirebaseDatabase instance = null;

    private FirebaseDbSingleton() {
    }

    public static FirebaseDatabase getInstance() {
        return instance;
    }

    public static void initialize(){
        if (instance == null) {
            instance = FirebaseDatabase.getInstance();
            instance.setPersistenceEnabled(true);
        }
    }
}
