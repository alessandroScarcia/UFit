package it.sms1920.spqs.ufit.launcher.splashscreen;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;

public class SplashScreenPresenter implements SplashScreenContract.Presenter {

    private SplashScreenContract.View view;
    private Context context;

    public SplashScreenPresenter(SplashScreenContract.View view, Context context) {
        this.view = view;
        this.context = context;

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
        startAnonymousSession();
        view.startApp();
    }

    private void startAnonymousSession() {
        FirebaseUser firebaseUser = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();

        if (firebaseUser == null) {
            FirebaseAuthSingleton.getFirebaseAuth().signInAnonymously()
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser().getUid();
                        }
                    });
        }
    }
}
