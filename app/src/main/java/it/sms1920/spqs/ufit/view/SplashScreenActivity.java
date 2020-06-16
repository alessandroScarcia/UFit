package it.sms1920.spqs.ufit.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import it.sms1920.spqs.ufit.contract.iSplashScreen;
import it.sms1920.spqs.ufit.presenter.SplashScreenPresenter;

import static it.sms1920.spqs.ufit.contract.iSplashScreen.SPLASH_TIME_OUT;

public class SplashScreenActivity extends AppCompatActivity implements iSplashScreen.View {

    private SplashScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Context context = this;

        presenter = new SplashScreenPresenter(SplashScreenActivity.this, context);

        presenter.onStartApp();
    }

    @Override
    public void startApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, LauncherActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}