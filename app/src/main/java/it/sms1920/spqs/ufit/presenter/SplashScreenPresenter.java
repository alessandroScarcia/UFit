package it.sms1920.spqs.ufit.presenter;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import it.sms1920.spqs.ufit.contract.iSplashScreen;
import it.sms1920.spqs.ufit.model.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.PicassoSingleton;

public class SplashScreenPresenter implements iSplashScreen.Presenter {

    private iSplashScreen.View view;
    private Context context;

    public SplashScreenPresenter( iSplashScreen.View view, Context context) {
        this.view = view;
        this.context = context;
    }


    private void sessionAnonymously(){


        PicassoSingleton.setPicassoInstance(this.context);

        FirebaseUser firebaseUser = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();

        if(firebaseUser == null){

            FirebaseAuthSingleton.getFirebaseAuth().signInAnonymously().
                    addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser().getUid();
                }
            });

        }
    }


    @Override
    public void onStartApp() {
        sessionAnonymously();
        view.startApp();

    }
}
