package it.sms1920.spqs.ufit.model.firebase.auth;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthSingleton {
    private static FirebaseAuth firebaseAuth;

    private FirebaseAuthSingleton() {
    }

    public static FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null)
            firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth;
    }
}
