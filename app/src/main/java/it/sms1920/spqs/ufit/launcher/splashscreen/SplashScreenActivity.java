package it.sms1920.spqs.ufit.launcher.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import it.sms1920.spqs.ufit.launcher.LauncherActivity;
import it.sms1920.spqs.ufit.launcher.R;

import static it.sms1920.spqs.ufit.launcher.splashscreen.SplashScreenContract.SPLASH_TIME_OUT;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenContract.View {

    private SplashScreenContract.Presenter presenter;

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