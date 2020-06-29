package it.sms1920.spqs.ufit.launcher.splashscreen;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.FirebaseDbSingleton;

public class SplashScreenPresenter implements SplashScreenContract.Presenter {

    private SplashScreenContract.View view;

    public SplashScreenPresenter(SplashScreenContract.View view, Context context) {
        this.view = view;

        Picasso instance =
                new Picasso.Builder(context)
                        .downloader(new OkHttp3Downloader(context, Integer.MAX_VALUE))
                        .build();
        instance.setIndicatorsEnabled(false);
        instance.setLoggingEnabled(true);

        Picasso.setSingletonInstance(instance);
    }

    @Override
    public void onStartApp() {
        FirebaseDbSingleton.initialize();
        startSession();
        view.startApp();
    }

    private void startSession() {
        FirebaseUser firebaseUser = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();
        if (firebaseUser == null) {
            FirebaseAuthSingleton.getFirebaseAuth().signInAnonymously();
        }

        DatabaseReference workoutPlanRef = FirebaseDbSingleton.getInstance().getReference("WorkoutPlan");
        workoutPlanRef.keepSynced(true);

        DatabaseReference exerciseSetsRef = FirebaseDbSingleton.getInstance().getReference("WorkoutPlanExerciseSets");
        exerciseSetsRef.keepSynced(true);
    }
}
