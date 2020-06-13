package it.sms1920.spqs.ufit.model;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthSingleton {
    private static FirebaseAuth firebaseAuth;

    public static FirebaseAuth getFirebaseAuth() {
        if(firebaseAuth==null)
            firebaseAuth=FirebaseAuth.getInstance();
        return firebaseAuth;
    }
}
